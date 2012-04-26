package one.examples.z_articles;

import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.When;
import one.core.nodes.OneNode;
import one.test.jre.OneTestJre;

public class C_IntroductionOnedbTestability {
    
    public static void main(String[] args) {
        OneTestJre.init();
        One.createRealm("foo").and(new When.RealmCreated() {

            @Override
            public void thenDo(OneClient client, OneNode root, String secret,
                    String partnerSecret) {
                One.append("bar").to(root).in(client);

                System.out.println("Created " + root + ":" + secret);
            }
        });
    }
    
}
