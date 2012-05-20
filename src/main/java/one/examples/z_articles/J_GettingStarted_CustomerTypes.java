package one.examples.z_articles;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.When;
import one.core.dsl.callbacks.results.WithRealmCreatedResult;

public class J_GettingStarted_CustomerTypes {

    public static void main(String[] args) {
        OneJre.init("[Your API Key here]");

        One.createRealm("types").and(new When.RealmCreated() {

            @Override
            public void thenDo(WithRealmCreatedResult r) {
                Object typesRoot = r.root();

                Object addressType = One.append("an Address").to(typesRoot)
                        .atAddress("./address").in(r.client());

                Object customerType = One.append("a Customer").to(typesRoot)
                        .atAddress("./customer").in(r.client());

                System.out.println("Address type: " + addressType);
                System.out.println("Customer type: " + customerType);
                System.out.println("Types realm " + r.root() + ":" + r.secret());

                shutdown(r.client());
            }

            private void shutdown(OneClient client) {
                One.shutdown(client).and(new When.Shutdown() {

                    @Override
                    public void thenDo() {
                        System.out.println("Client successfully shut down.");
                    }
                });
            }

        });

    }

}
