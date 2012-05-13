package one.examples.z_articles;

import one.client.jre.OneJre;
import one.common.One;
import one.core.dsl.callbacks.When;
import one.core.dsl.callbacks.results.WithRealmCreatedResult;

public class A_IntroducingOnedb {

    public static void main(String[] args) {
        OneJre.init("[your API key here]");
        One.createRealm("foo").and(new When.RealmCreated() {

            @Override
            public void thenDo(WithRealmCreatedResult r) {
                One.append("bar").to(r.root()).in(r.client());

                System.out.println("Created " + r.root() + ":" + r.secret());
            }
        });
    }

}
