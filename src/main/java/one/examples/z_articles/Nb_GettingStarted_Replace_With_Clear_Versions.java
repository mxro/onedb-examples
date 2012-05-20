package one.examples.z_articles;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.When;
import one.core.dsl.callbacks.results.WithCommittedResult;
import one.core.dsl.callbacks.results.WithRealmCreatedResult;
import one.core.dsl.callbacks.results.WithVersionsClearedResult;
import one.core.nodes.OneNode;

public class Nb_GettingStarted_Replace_With_Clear_Versions {

    /**
     * @param args
     */
    public static void main(String[] args) {
        OneJre.init("[Your API key here]");

        One.createRealm("replaceAndClear").and(new When.RealmCreated() {

            @Override
            public void thenDo(final WithRealmCreatedResult r) {
                final OneClient client = r.client();
                final OneNode realmRoot = r.root();

                final OneNode value = One.append(1).to(realmRoot)
                        .atAddress("./value").in(client);

                for (int i = 2; i <= 100; i++) {
                    One.replace(value).with(One.newNode(i).at(value.getId()))
                            .in(client);
                }

                // the node ./value will now have 100 distinct versions.

                One.commit(client).and(new When.Committed() {

                    @Override
                    public void thenDo(WithCommittedResult cr) {
                        // now all versions will be uploaded to the onedb cloud

                        // to clear the versions:
                        One.clearVersions(value).andKeepOnServer(5).in(client)
                                .and(new When.VersionsCleared() {

                                    @Override
                                    public void thenDo(
                                            WithVersionsClearedResult arg0) {
                                        System.out.println("Versions cleared");

                                        One.shutdown(client).and(
                                                new When.Shutdown() {
                                                    
                                                    @Override
                                                    public void thenDo() {
                                                        System.out
                                                        .println("Client closed for "
                                                                + r.root()
                                                                + ":"
                                                                + r.secret());
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
