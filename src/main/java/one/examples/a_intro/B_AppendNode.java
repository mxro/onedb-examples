package one.examples.a_intro;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.When;
import one.core.dsl.callbacks.results.WithCommittedResult;
import one.core.dsl.callbacks.results.WithRealmCreatedResult;
import one.core.nodes.OneValue;

public class B_AppendNode {

	public static void main(String[] args) {
		OneJre.init("[your api key here]"); // insert your onedb API key here.

		One.createRealm("foo").and(new When.RealmCreated() {

		    @Override
            public void thenDo(WithRealmCreatedResult cr) {
		        System.out.println("Realm has been created:");
                System.out.println("  Root: " + cr.root()); 
                System.out.println("  Secret: " + cr.secret());

                String appendedObject = One.append("text").to(cr.root())
                        .in(cr.client());
                System.out.println("Appended: "+appendedObject); // Appended: text
                
                OneValue<Integer> appendedValue = One.append(120).to(cr.root())
                        .atAddress("./number").in(cr.client());
                System.out.println("Appended: "+appendedValue); // Appended: One.value(120).at(...)
                
                cr.client().one().commit(cr.client()).and(new When.Committed() {
                    
                   

                    @Override
                    public void thenDo(WithCommittedResult _cr) {
                        shutdown(_cr.client());
                    }
                });
            }
		    
			

		    private void shutdown(OneClient client) {
                One.shutdown(client).and(new When.Shutdown() {

                    @Override
                    public void thenDo() {
                        System.out.println("Client successfully shut down.");
                    }
                });
            }

            

		});
	}

}
