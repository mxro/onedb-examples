package one.examples.features;

import one.client.jre.OneJre;
import one.core.domain.OneClient;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.WhenSeeded;
import one.core.dsl.callbacks.WhenShutdown;
import one.core.dsl.callbacks.results.WithSeedResult;
import one.core.nodes.OneValue;

/**
 * Server-side assembly of embedded text nodes. <br/>
 * <br/>
 * Introduced in 0.0.6.
 * 
 * @author Max
 * 
 */
public class EmbeddingTextNodes {

	public static void main(final String[] args) {

		final CoreDsl dsl = OneJre.init();

		final OneClient client = dsl.createClient();

		dsl.seed(client, new WhenSeeded() {

			@Override
			public void thenDo(final WithSeedResult sr) {

				final String embedMe = "<p>I am embedded, dude!</p>";

				final OneValue<String> embedMeNode = dsl.append(embedMe)
						.to(sr.seedNode()).atAddress("./embedMe").in(client);

				final String assembled = "<p>Look, what's coming next:</p>"
						+ "<!-- one.embedText(\"" + embedMeNode.getId()
						+ "\") -->";

				final OneValue<String> assembledNode = dsl.append(assembled)
						.to(sr.seedNode()).atAddress("./assembled").in(client);

				// assure assembled node is rendered in html
				dsl.append(
						dsl.reference("https://admin1.linnk.it/types/v01/isHtmlValue"))
						.to(assembledNode).in(client);

				// assure templates are evaluated for assembled node
				dsl.append(
						dsl.reference("https://u1.linnk.it/6wbnoq/Types/aTemplate"))
						.to(assembledNode).in(client);

				System.out.println("Assembled node created: "
						+ assembledNode.getId().replaceFirst("https", "http")
						+ ".value.html");

				dsl.shutdown(client).and(WhenShutdown.DO_NOTHING);

			}
		});

	}

}
