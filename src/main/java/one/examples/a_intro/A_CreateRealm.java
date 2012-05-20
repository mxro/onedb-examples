package one.examples.a_intro;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.When;
import one.core.dsl.callbacks.results.WithRealmCreatedResult;

public class A_CreateRealm {

    public static void main(String[] args) {

        OneJre.init("[your api key here]"); // insert your onedb API key here.

        One.createRealm("foo").and(new When.RealmCreated() {

            @Override
            public void thenDo(WithRealmCreatedResult cr) {
                System.out.println("Realm has been created.\n");
                System.out.println("Realm root: " + cr.root());
                System.out.println("Realm secret: " + cr.secret());

                shutdown(cr.client());
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
