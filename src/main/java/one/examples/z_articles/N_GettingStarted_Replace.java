package one.examples.z_articles;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.When;
import one.core.dsl.callbacks.results.WithRealmCreatedResult;
import one.core.nodes.OneNode;
import one.core.nodes.OneTypedReference;

public class N_GettingStarted_Replace {

    /**
     * @param args
     */
    public static void main(String[] args) {
        OneJre.init("[Your API Key here]");
        // OR OneTestJre.init();

        One.createRealm("update").and(new When.RealmCreated() {

            @Override
            public void thenDo(WithRealmCreatedResult r) {

                OneClient client = r.client();
                OneNode realmRoot = r.root();
                
                // node with external address
                String phase1 = "phase1";
                One.append(phase1).to(realmRoot).in(client);

                Object phase2 = "phase2";
                One.replace(phase1).with(phase2).in(client);

                
                // node with internal address
                OneNode phase1Node = One.append("phase1").to(realmRoot)
                        .atAddress("./p1").in(client);

                One.replace(phase1Node)
                        .with(One.newNode("phase2").at(phase1Node.getId()))
                        .in(client);
                
                for (OneTypedReference<Object> childId : One.selectFrom(realmRoot).allChildren()
                        .in(client)) {
                    System.out.println(One.dereference(One.reference(childId))
                            .in(client));
                }

                
               
                One.shutdown(client).and(new When.Shutdown() {
                    
                    @Override
                    public void thenDo() {
                        System.out.println("all uploaded");
                    }
                });
                        
                   
            }
            
            

            
        });
    }

}
