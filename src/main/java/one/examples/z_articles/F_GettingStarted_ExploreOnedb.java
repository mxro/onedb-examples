package one.examples.z_articles;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.ShutdownCallback;
import one.core.dsl.callbacks.When;
import one.core.nodes.OneNode;

public class F_GettingStarted_ExploreOnedb {
   
    public static void main(String[] args) {

        OneJre.init("[Your API key here]");
        
        One.createRealm("exploration").and(new When.RealmCreated() {

            @Override
            public void thenDo(OneClient client, OneNode realmRoot,
                    String secret, String partnerSecret) {
                System.out.println("realmRoot: "+realmRoot);
                System.out.println("secret: "+secret);

                One.shutdown(client).and(new ShutdownCallback() {
                    
                    @Override
                    public void onSuccessfullyShutdown() {
                        System.out.println("Session is shut down.");
                    }

                    @Override
                    public void onFailure(Throwable arg0) {
                        throw new RuntimeException(arg0);
                    }
                    
                });
                
            }
        });

    }
    
}
