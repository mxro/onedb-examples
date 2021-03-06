package one.examples.z_articles;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.When;
import one.core.dsl.callbacks.results.WithRealmCreatedResult;

public class K_GettingStarted_CustomerDefinition {
    
    public static void main(String[] args) {
        OneJre.init("[Your API Key here]");

        One.createRealm("bob").and(new When.RealmCreated() {

            @Override
            public void thenDo(WithRealmCreatedResult r) {
                
                System.out.println("Created " + r.root() + ":" + r.secret());

                // -- reference types
                Object addressType = One
                        .reference("https://u1.linnk.it/zednuw/types/address"); // <--
                                                                                // replace
                                                                                // with
                                                                                // yours!
                Object customerType = One
                        .reference("https://u1.linnk.it/zednuw/types/customer"); // <--
                                                                                 // replace
                                                                                 // with
                                                                                 // yours!

                // -- build data
                OneClient client = r.client();
                Object bob = r.root();
                One.append(customerType).to(bob).in(client);
                One.append("Bob").to(bob).in(client);

                String addressValue = "26 Short Av";
                One.append(addressValue).to(bob).in(client);
                One.append(addressType).to(addressValue).in(client);

                System.out.println("will upload data ...");

                One.shutdown(client).and(new When.Shutdown() {

                    @Override
                    public void thenDo() {
                        System.out.println("... all data uploaded.");
                    }
                });
                
               
            }
            
           

           
        });
    }
    
}
