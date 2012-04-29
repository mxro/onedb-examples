package one.examples.z_articles;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.ShutdownCallback;
import one.core.dsl.callbacks.When;
import one.core.nodes.OneNode;

public class L_GettingStarted_SelectCreateData {

    public static void main(String[] args) {
        OneJre.init("[Your API Key Here]");

        One.createRealm("query").and(new When.RealmCreated() {
            @Override
            public void thenDo(OneClient client, OneNode realmRoot,
                    String realmSecret, String partnerSecret) {

                System.out.println("Created " + realmRoot + ":" + realmSecret);

                One.append("This is a test realm").to(realmRoot).in(client);
                Object bob = One.append("bob").to(realmRoot).atAddress("./bob")
                        .in(client);
                 
                One.append(
                        One.reference("[your customer type here]"))
                        .to(bob).in(client);

                One.shutdown(client).and(new ShutdownCallback() {

                    @Override
                    public void onSuccessfullyShutdown() {
                        System.out.println("All nodes uploaded to server.");
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
