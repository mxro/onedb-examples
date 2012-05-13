package one.examples.z_articles;

import one.common.One;
import one.core.dsl.callbacks.When;
import one.core.dsl.callbacks.results.WithRealmCreatedResult;
import one.test.jre.OneTestJre;

public class C_IntroductionOnedbTestability {

    public static void main(String[] args) {
        OneTestJre.init(); // was: OneJre.init("[your API key]");
        One.createRealm("foo").and(new When.RealmCreated() {

            @Override
            public void thenDo(WithRealmCreatedResult r) {
                One.append("bar").to(r.root()).in(r.client());

                System.out.println("Created " + r.root() + ":" + r.secret());
            }

        });
    }

}
