package one.examples.features;

import java.util.LinkedList;
import java.util.List;

import one.client.jre.OneJre;
import one.core.domain.OneClient;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.WhenChildrenSelected;
import one.core.dsl.callbacks.WhenSeeded;
import one.core.dsl.callbacks.WhenShutdown;
import one.core.dsl.callbacks.results.WithChildrenSelectedResult;
import one.core.dsl.callbacks.results.WithSeedResult;
import one.core.nodes.OneTypedReference;

public class VersatileDataRepresentation_JavaList {

	public static void main(final String[] args) {
		createListAsJavaList();
	}

	public static void createListAsJavaList() {
		// initialize engine
		final CoreDsl dsl = OneJre.init();

		// create a client to hold local data replication
		final OneClient c = dsl.createClient();

		// create a new 'seed' data node in the cloud
		dsl.seed(c, new WhenSeeded() {

			@Override
			public void thenDo(final WithSeedResult sr) {

				// create a plain Java list
				final List<String> myFriends = new LinkedList<String>();
				myFriends.add("Peter");
				myFriends.add("Paul");
				myFriends.add("Petra");

				// append the list to the created 'seed' data node
				dsl.append(myFriends).to(sr.seedNode()).in(c);

				// finalize local client context & upload all data
				dsl.shutdown(c).and(new WhenShutdown() {

					@Override
					public void thenDo() {

						System.out
								.println("Friends data written. Wait for load ...");
						loadListAsJavaList(sr.seedNode().getId(),
								sr.accessToken());
					}

				});

			}
		});
	}

	@SuppressWarnings("rawtypes")
	private static void loadListAsJavaList(final String friendsNodeUri,
			final String friendNodeSecret) {
		// initialize engine for testing to load data
		final CoreDsl dsl = OneJre.init();

		// create client for testing to load data
		final OneClient c = dsl.createClient();

		// select all nodes from the previously created seed node, which are of
		// the type LinkedList.
		dsl.selectFrom(dsl.reference(friendsNodeUri)).theChildren()
				.withSecret(friendNodeSecret).ofType(LinkedList.class).in(c)
				.and(new WhenChildrenSelected<OneTypedReference<LinkedList>>() {

					@Override
					public void thenDo(
							final WithChildrenSelectedResult<OneTypedReference<LinkedList>> cr) {

						assert cr.children().size() == 1;

						// select by default returns a list of references, the
						// reference must be resolved in order to access the
						// LinkedList
						final List<?> myFriends = dsl.dereference(
								cr.children().get(0)).in(c);

						System.out.println("My friends in a Java List: "
								+ myFriends);
						System.out.println("Stored in: " + friendsNodeUri);

						dsl.shutdown(c).and(WhenShutdown.DO_NOTHING);

					}
				});

	}

}
