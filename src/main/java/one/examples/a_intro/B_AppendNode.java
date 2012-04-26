package one.examples.a_intro;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.ShutdownCallback;
import one.core.dsl.callbacks.When;
import one.core.nodes.OneNode;
import one.core.nodes.OneValue;

public class B_AppendNode {

	public static void main(String[] args) {
		OneJre.init("[your api key here]"); // insert your onedb API key here.

		One.createRealm("foo").and(new When.RealmCreated() {

			@Override
			public void thenDo(OneClient client, OneNode rootNode,
					String secret, String partnerSecret) {
				System.out.println("Realm has been created:");
				System.out.println("  Root: " + rootNode); 
				System.out.println("  Secret: " + secret);

				String appendedObject = One.append("text").to(rootNode)
						.in(client);
				System.out.println("Appended: "+appendedObject); // Appended: text
				
				OneValue<Integer> appendedValue = One.append(120).to(rootNode)
						.atAddress("./number").in(client);
				System.out.println("Appended: "+appendedValue); // Appended: One.value(120).at(...)
				
				client.one().commit(client).and(new When.Committed() {
					
					@Override
					public void thenDo(OneClient client) {
						shutdown(client);
					}
				});
				
			}

			private void shutdown(OneClient arg0) {
				One.shutdown(arg0).and(new ShutdownCallback() {

					@Override
					public void onSuccessfullyShutdown() {
						System.out.println("Client successfully shut down.");
					}

					@Override
					public void onFailure(Throwable arg0) {
						throw new RuntimeException(arg0);
					}
				});
			}

		});
	}

}
