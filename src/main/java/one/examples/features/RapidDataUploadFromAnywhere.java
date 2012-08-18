package one.examples.features;

import one.client.jre.OneJre;
import one.core.domain.OneClient;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.WhenSeeded;
import one.core.dsl.callbacks.WhenShutdown;
import one.core.dsl.callbacks.results.WithSeedResult;

/**
 * This example illustrates the seed operation, which allows to store data in
 * the cloud from any application with minimal setup time.
 * 
 * @author Max
 * 
 */
public class RapidDataUploadFromAnywhere {

	public static void main(final String[] args) {

		final CoreDsl dsl = OneJre.init();

		final OneClient client = dsl.createClient();

		dsl.seed(client, new WhenSeeded() {

			@Override
			public void thenDo(final WithSeedResult sr) {

				final String data = dsl
						.append("Hello, this data is in the cloud!")
						.to(sr.seedNode()).in(client);

				dsl.append(112).to(sr.seedNode()).in(client);

				System.out.println("Data uploaded");

				final String httpLink = dsl.reference((Object) data).in(client)
						.getId().replaceFirst("https", "http");

				System.out.println("  NODE: " + sr.seedNode().getId());
				System.out.println("  JSON: " + httpLink + ".value.json");
				System.out.println("  XML : " + httpLink + ".value.xml");
				System.out.println("  HTML: " + httpLink + ".node.html");

				dsl.shutdown(client).and(WhenShutdown.DO_NOTHING);

			}
		});

	}

}
