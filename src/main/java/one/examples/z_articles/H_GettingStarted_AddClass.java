package one.examples.z_articles;

import java.io.Serializable;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.ShutdownCallback;
import one.core.dsl.callbacks.When;
import one.core.nodes.OneNode;

public class H_GettingStarted_AddClass {
    
    public static class Customer implements Serializable {

        private static final long serialVersionUID = 1L;

        public String name;
        public String address;
    }

    public static void main(String[] args) {
        OneJre.init("[Your API Key here]");

        One.createRealm("ops").and(new When.RealmCreated() {

            @Override
            public void thenDo(OneClient client, OneNode realmRoot,
                    String accessSecret, String partnerSecret) {
                Customer bob = new Customer();
                bob.name = "Bob";
                bob.address = "26 Short Av";

                One.append(bob).to(realmRoot).in(client);

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
