package one.examples.z_articles;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.ShutdownCallback;
import one.core.dsl.callbacks.WhenRealmCreated;
import one.core.dsl.callbacks.results.WithRealmCreatedResult;
import one.core.nodes.OneNode;

public class O_GettingStarted_Remove {

    public static void main(String[] args) {
        OneJre.init("[Your API key here]");
        // OR OneTestJre.init();
        
        One.createRealm("remove").and(new WhenRealmCreated() {

            @Override
            public void thenDo(final WithRealmCreatedResult r) {
                OneClient client = r.client();
                OneNode realmRoot = r.root();
                
                // remove connection AND node
                OneNode toBeRemovedNode = One.append("to be removed")
                        .to(realmRoot).atAddress("./toBeRemoved").in(client);
                One.remove(One.reference(toBeRemovedNode)).fromNode(realmRoot)
                        .in(client);

                // remove ONLY connection
                OneNode toBeKeptNode = One.append("to be kept").to(realmRoot)
                        .atAddress("./toBeKept").in(client);

                OneNode anotherNode = One.append("another node").to(realmRoot)
                        .atAddress("./anotherNode").in(client);

                One.append(toBeKeptNode).to(anotherNode).in(client);
                One.remove(toBeKeptNode).fromNode(anotherNode).in(client);

                One.shutdown(client).and(new ShutdownCallback() {

                    @Override
                    public void onSuccessfullyShutdown() {
                        System.out.println("all uploaded for " + r.root()
                                + ":" + r.secret());
                    }
                });
            }
        });
    }

}
