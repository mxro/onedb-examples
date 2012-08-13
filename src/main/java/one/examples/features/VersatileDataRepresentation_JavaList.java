package one.examples.features;

import java.util.LinkedList;
import java.util.List;

import one.client.jre.OneJre;
import one.core.domain.OneClient;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.WhenChildrenSelected;
import one.core.dsl.callbacks.WhenLoaded;
import one.core.dsl.callbacks.WhenSeeded;
import one.core.dsl.callbacks.WhenShutdown;
import one.core.dsl.callbacks.results.WithChildrenSelectedResult;
import one.core.dsl.callbacks.results.WithLoadResult;
import one.core.dsl.callbacks.results.WithSeedResult;
import one.core.nodes.OneTypedReference;

public class VersatileDataRepresentation_JavaList {

	public static void main(final String[] args) {

		createListAsJavaList();

	}

	public static void createListAsJavaList() {
		final CoreDsl dsl = OneJre.init();

		final OneClient c = dsl.createClient();

		dsl.seed(c, new WhenSeeded() {

			@Override
			public void thenDo(final WithSeedResult sr) {

				final List<String> myFriends = new LinkedList<String>();
				myFriends.add("Peter");
				myFriends.add("Paul");
				myFriends.add("Petra");

				dsl.append(myFriends).to(sr.seedNode()).in(c);

				dsl.shutdown(c).and(new WhenShutdown() {

					@Override
					public void thenDo() {
						loadListAsJavaList(sr.seedNode().getId(),
								sr.accessToken());
					}

				});

			}
		});
	}

	private static void loadListAsJavaList(final String dataNode,
			final String accessToken) {
		final CoreDsl dsl = OneJre.init();

		final OneClient c = dsl.createClient();

		dsl.load(dataNode).withSecret(accessToken).and(new WhenLoaded() {

			@SuppressWarnings("rawtypes")
			@Override
			public void thenDo(final WithLoadResult<Object> lr) {

				dsl.selectFrom(lr.loadedNode())
						.theChildren()
						.ofType(LinkedList.class)
						.in(c)
						.and(new WhenChildrenSelected<OneTypedReference<LinkedList>>() {

							@Override
							public void thenDo(
									final WithChildrenSelectedResult<OneTypedReference<LinkedList>> cr) {

								assert cr.children().size() == 1;

								final List<?> myFriends = dsl.dereference(
										cr.children().get(0)).in(c);

								System.out
										.println("My friends in a Java List: "
												+ myFriends);

								dsl.shutdown(c).and(WhenShutdown.DO_NOTHING);

							}
						});

			}
		});
	}

}
