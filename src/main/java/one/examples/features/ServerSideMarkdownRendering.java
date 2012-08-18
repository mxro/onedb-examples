package one.examples.features;

import one.client.jre.OneJre;
import one.core.domain.OneClient;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.WhenSeeded;
import one.core.dsl.callbacks.WhenShutdown;
import one.core.dsl.callbacks.results.WithSeedResult;
import one.core.nodes.OneValue;

/**
 * Server-side rendering of Markdown formatted plain text nodes. <br/>
 * <br/>
 * Introduced in 0.0.6.
 * 
 * @author Max
 * 
 */
public class ServerSideMarkdownRendering {

	public static void main(final String[] args) {

		final CoreDsl dsl = OneJre.init();

		final OneClient client = dsl.createClient();

		dsl.seed(client, new WhenSeeded() {

			@Override
			public void thenDo(final WithSeedResult sr) {

				final String markdownText = "# Hello, World\n\nof **markdown**!<button>Test</button>";

				final OneValue<String> markdownNode = dsl.append(markdownText)
						.to(sr.seedNode()).atAddress("./md").in(client);

				// this marker tells the server to render the nodes contents
				// using markdown.
				dsl.append(
						dsl.reference("https://u1.linnk.it/6wbnoq/Types/isMarkdown"))
						.to(markdownNode).in(client);

				System.out.println("See rendered markdown node at  : "
						+ markdownNode.getId().replaceAll("https", "http")
						+ ".value.html");

				System.out.println("See plain text markdown node at: "
						+ markdownNode.getId().replaceAll("https", "http")
						+ ".value.md");

				System.out.println("Secret: " + sr.accessToken());

				dsl.shutdown(client).and(WhenShutdown.DO_NOTHING);

			}
		});

	}
}
