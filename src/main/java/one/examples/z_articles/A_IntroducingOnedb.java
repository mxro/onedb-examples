package one.examples.z_articles;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.When;
import one.core.nodes.OneNode;

public class A_IntroducingOnedb {

    public static void main(String[] args) {
        OneJre.init("[your API key here]");
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
