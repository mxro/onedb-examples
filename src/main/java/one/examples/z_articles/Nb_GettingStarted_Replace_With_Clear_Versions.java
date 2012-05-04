package one.examples.z_articles;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.ShutdownCallback;
import one.core.dsl.callbacks.When;
import one.core.nodes.OneNode;
import one.core.nodes.OneValue;

public class Nb_GettingStarted_Replace_With_Clear_Versions {

    /**
     * @param args
     */
    public static void main(String[] args) {
        OneJre.init("v15hyv30dhoz0zz8z19dspsyyetmfrpj");
        One.createRealm("replaceAndClear").and(new When.RealmCreated() {

            @Override
            public void thenDo(final OneClient client, final OneNode realmRoot,
                   final String secret, final String partnerSecret) {

                final OneNode value = One.append(1).to(realmRoot)
                        .atAddress("./value").in(client);

                for (int i = 2; i <= 100; i++) {
                    One.replace(value).with(One.newNode(i).at(value.getId()))
                            .in(client);
                }

                // the node ./value will now have 100 distinct versions.

                One.commit(client).and(new When.Committed() {

                    @Override
                    public void thenDo(OneClient arg0) {
                        // now all versions will be uploaded to the onedb cloud

                        // to clear the versions:
                        One.clearVersions(value).andKeepOnServer(5).in(client).and(new When.VersionsCleared() {
                            
                            @Override
                            public void thenDo(int arg0, OneClient arg1) {
                                System.out.println("Versions cleared");
                                
                                One.shutdown(client).and(new ShutdownCallback() {
                                    
                                    @Override
                                    public void onSuccessfullyShutdown() {
                                        System.out.println("Client closed");
                                    }
                                });
                            }
                        });
                    }
                });

            }
        });
    }

}
