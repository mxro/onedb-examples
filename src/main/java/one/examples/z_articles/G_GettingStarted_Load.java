package one.examples.z_articles;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.When;
import one.core.nodes.OneTypedReference;

public class G_GettingStarted_Load {
    
    public static void main(String[] args) {
        OneJre.init("[your API key here]");
        
        One.load(One.reference("[your realm root address here]"))
           .withSecret("[access token for your realm here]")
           .and(new When.Loaded() {
            
            @Override
            public void thenDo(OneTypedReference<Object> loadedNode, OneClient client) {
                Object realmRoot = One.dereference(loadedNode).in(client);
                
                System.out.println("Node reference: "+loadedNode);
                System.out.println("Resolved node: "+realmRoot);
            }
        });
    }
}
