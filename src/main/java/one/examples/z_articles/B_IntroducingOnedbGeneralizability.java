package one.examples.z_articles;

import java.io.Serializable;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.When;
import one.core.dsl.callbacks.results.WithRealmCreatedResult;

public class B_IntroducingOnedbGeneralizability {

    public static class Person implements Serializable {
        private static final long serialVersionUID = 1L;
    };

    public static void main(String[] args) {
        OneJre.init("[your API key here]");

        One.createRealm("nodes").and(new When.RealmCreated() {

            @Override
            public void thenDo(WithRealmCreatedResult r) {
                OneClient client = r.client();
                
                String bar = "bar";
                Integer meaning = 42;
                Person p = new Person();
                
                One.append(bar).to(r.root()).in(client);
                One.append(meaning).to(bar).in(client);
                One.append(p).to(bar).in(client);
                
                System.out.println("Created " + r.root() + ":" + r.secret());
            }
            
           

           
        });
    }
}
