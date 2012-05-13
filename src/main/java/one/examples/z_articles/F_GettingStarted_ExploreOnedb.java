package one.examples.z_articles;

import one.client.jre.OneJre;
import one.common.One;
import one.core.dsl.callbacks.ShutdownCallback;
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

                One.shutdown(r.client()).and(new ShutdownCallback() {

                    @Override
                    public void onSuccessfullyShutdown() {
                        System.out.println("Session is shut down.");
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
