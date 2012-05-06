package one.examples.z_articles;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.ShutdownCallback;
import one.core.dsl.callbacks.When;
import one.core.nodes.OneNode;

public class K_GettingStarted_CustomerDefinition {
    
    public static void main(String[] args) {
        OneJre.init("[Your API Key here]");

        One.createRealm("bob").and(new When.RealmCreated() {

            @Override
            public void thenDo(OneClient client, OneNode realmRoot,
                    String accessSecret, String partnerSecret) {

                System.out.println("Created " + realmRoot + ":" + accessSecret);

                // -- reference types
                Object addressType = One
                        .reference("https://u1.linnk.it/zednuw/types/address"); // <--
                                                                                // replace
                                                                                // with
                                                                                // yours!
                Object customerType = One
                        .reference("https://u1.linnk.it/zednuw/types/customer"); // <--
                                                                                 // replace
                                                                                 // with
                                                                                 // yours!

                // -- build data
                Object bob = realmRoot;
                One.append(customerType).to(bob).in(client);
                One.append("Bob").to(bob).in(client);

                String addressValue = "26 Short Av";
                One.append(addressValue).to(bob).in(client);
                One.append(addressType).to(addressValue).in(client);

                System.out.println("will upload data ...");

                One.shutdown(client).and(new ShutdownCallback() {

                    @Override
                    public void onSuccessfullyShutdown() {
                        System.out.println("... all data uploaded.");
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
