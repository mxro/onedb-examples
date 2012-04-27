package one.examples.z_articles;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.ShutdownCallback;
import one.core.dsl.callbacks.When;
import one.core.nodes.OneNode;

public class J_GettingStarted_CustomerTypes {
    
    public static void main(String[] args) {
        OneJre.init("[Your API Key here]");

        One.createRealm("types").and(new When.RealmCreated() {

            @Override
            public void thenDo(OneClient client, OneNode realmRoot,
                    String accessSecret, String partnerSecret) {

                System.out.println("Created " + realmRoot + ":" + accessSecret);

                Object addressType = One.append("an Address").to(realmRoot)
                        .atAddress("./address").in(client);

                Object customerType = One.append("a Customer").to(realmRoot)
                        .atAddress("./customer").in(client);

                System.out.println("Address type: " + addressType);
                System.out.println("Customer type: " + customerType);

                One.shutdown(client).and(new ShutdownCallback() {

                    @Override
                    public void onSuccessfullyShutdown() {
                        System.out.println("Types created successfully.");
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
