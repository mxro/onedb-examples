package one.examples.z_articles;

import java.io.Serializable;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.When;
import one.core.dsl.callbacks.results.WithRealmCreatedResult;

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
            public void thenDo(WithRealmCreatedResult r) {
                Customer bob = new Customer();
                bob.name = "Bob";
                bob.address = "26 Short Av";

                One.append(bob).to(r.root()).in(r.client());

                System.out.println("Created " + r.root() + ":" + r.secret());

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
