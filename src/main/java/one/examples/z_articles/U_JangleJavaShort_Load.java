package one.examples.z_articles;

import one.client.jre.OneJre;
import one.core.domain.OneClient;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.WhenLoaded;
import one.core.dsl.callbacks.WhenShutdown;
import one.core.dsl.callbacks.results.WithLoadResult;
import one.core.nodes.OneNode;

public class U_JangleJavaShort_Load {
	public static void main(final String[] args) {

		final CoreDsl dsl = OneJre.init();

		final OneClient client = dsl.createClient();

		dsl.load("[your seed]/pizzaParlor").withSecret("[your secret]")
				.in(client).and(new WhenLoaded() {

					@Override
					public void thenDo(final WithLoadResult<Object> lr) {

						final OneNode servings = dsl.append("servings")
								.to(lr.loadedNode()).atAddress("./servings")
								.in(client);

						dsl.append("Pizza!").to(servings).in(client);

						System.out.println("Servings defined.");

						dsl.shutdown(client).and(WhenShutdown.DO_NOTHING);
					}
				});

	}
}
