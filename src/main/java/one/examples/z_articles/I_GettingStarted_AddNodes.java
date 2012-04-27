package one.examples.z_articles;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.ShutdownCallback;
import one.core.dsl.callbacks.When;
import one.core.nodes.OneNode;

public class I_GettingStarted_AddNodes {
    
    public static void main(String[] args) {
        OneJre.init("[Your API Key here]");

        One.createRealm("ops").and(new When.RealmCreated() {

            @Override
            public void thenDo(OneClient client, OneNode realmRoot,
                    String accessSecret, String partnerSecret) {

                Object bob = realmRoot;

                One.append("Bob").to(bob).in(client);

                String addressValue = "26 Short Av";
                One.append(addressValue).to(bob).in(client);
                One.append("an Address").to(addressValue).in(client);

                One.append("a Customer").to(bob).in(client);

                System.out.println("Created " + realmRoot + ":" + accessSecret);

                One.shutdown(client).and(new ShutdownCallback() {

                    @Override
                    public void onSuccessfullyShutdown() {
                        // all ok
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
