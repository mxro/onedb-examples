package one.examples.z_articles;

import one.client.jre.OneJre;
import one.core.domain.OneClient;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.WhenSeeded;
import one.core.dsl.callbacks.WhenShutdown;
import one.core.dsl.callbacks.results.WithSeedResult;
import one.core.nodes.OneNode;

public class T_JangleJavaShort_Upload {

	public static void main(final String[] args) {

		final CoreDsl dsl = OneJre.init();

		final OneClient client = dsl.createClient();

		dsl.seed(client, new WhenSeeded() {

			@Override
			public void thenDo(final WithSeedResult sr) {

				final OneNode parlor = dsl.append("My Pizza Parlor")
						.to(sr.seedNode()).atAddress("./pizzaParlor")
						.in(client);

				dsl.append("Awsome Pizza!").to(parlor).in(client);

				System.out.println("Pizza Parlor Created:");
				System.out.println("at node      : " + parlor.getId());
				System.out.println("access secret: " + sr.accessToken());

				dsl.shutdown(client).and(WhenShutdown.DO_NOTHING);
			}
		});

	}

}
