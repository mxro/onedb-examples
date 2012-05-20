package one.examples.z_articles;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.When;
import one.core.dsl.callbacks.results.WithRealmCreatedResult;

public class F_GettingStarted_ExploreOnedb {

    public static void main(String[] args) {

        OneJre.init("[Your API key here]");

        One.createRealm("exploration").and(new When.RealmCreated() {

            @Override
            public void thenDo(WithRealmCreatedResult r) {
                System.out.println("realmRoot: " + r.root());
                System.out.println("secret: " + r.secret());

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
