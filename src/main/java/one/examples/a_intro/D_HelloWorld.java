package one.examples.a_intro;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.WhenRealmCreated;
import one.core.dsl.callbacks.WhenShutdown;
import one.core.dsl.callbacks.results.WithRealmCreatedResult;
import one.core.nodes.OneNode;

public class D_HelloWorld {

    /**
     * @param args
     */
    public static void main(String[] args) {
    OneJre.init("[Your API key here]");
    
    One.createRealm("hello").and(new WhenRealmCreated() {
        
        @Override
        public void thenDo(WithRealmCreatedResult r) {
            System.out.println("Created db ["+r.root()+"]");
            System.out.println("Private access secret: ["+r.secret()+"]");
            
            OneNode dbRoot = r.root();
            OneClient client = r.client();
            
            String engHello = "Hello, world!";
            One.append(engHello).to(dbRoot).in(client);
            One.append("is English").to(engHello).in(client);
            
            String gerHello = "Hallo, Welt!";
            One.append(gerHello).to(dbRoot).in(client);
            One.append("is German").to(gerHello).in(client);
            
            // Appending a public read token allows everyone to read
            // (but not write) the db root node and its children.
            One.append(One.newNode().asPublicReadToken()).to(dbRoot).in(client);
            
            One.shutdown(client).and(new WhenShutdown() {
                
                @Override
                public void thenDo() {
                    System.out.println("All nodes synchronized.");
                }
            });
            
        }
    });

    }

}
