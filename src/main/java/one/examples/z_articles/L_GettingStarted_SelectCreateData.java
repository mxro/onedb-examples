package one.examples.z_articles;

import one.client.jre.OneJre;
import one.common.One;
import one.core.dsl.callbacks.When;
import one.core.dsl.callbacks.results.WithRealmCreatedResult;

public class L_GettingStarted_SelectCreateData {

    public static void main(String[] args) {
        // remember to replace api and references to nodes!!!
        
        OneJre.init("[Your API Key Here]");

        One.createRealm("query").and(new When.RealmCreated() {
            
            @Override
            public void thenDo(WithRealmCreatedResult r) {
                System.out.println("Created " + r.root() + ":" + r.secret());

                One.append("This is a test realm").to(r.root()).in(r.client());
                Object bob = One.append("bob").to(r.root()).atAddress("./bob")
                        .in(r.client());
                 
                One.append(
                        One.reference("https://u1.linnk.it/zednuw/types/customer"))
                                       // ^-- replace with your customer type
                        .to(bob).in(r.client());

                One.shutdown(r.client()).and(new When.Shutdown() {
                    
                    @Override
                    public void thenDo() {
                        System.out.println("All nodes uploaded to server.");
                    }
                });
                
               
            }
            
           


        });

    }

}
