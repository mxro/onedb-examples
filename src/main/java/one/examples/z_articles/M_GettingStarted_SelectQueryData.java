package one.examples.z_articles;

import java.util.List;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.ShutdownCallback;
import one.core.dsl.callbacks.When;
import one.core.nodes.OneTypedReference;

public class M_GettingStarted_SelectQueryData {

    /**
     * @param args
     */
    public static void main(String[] args) {
        OneJre.init("[Your API Key here]");

        // replace reference and secret with details of the realm you have
        // created!!!
        // also replace customer type node!!
        One.load(One.reference("https://u1.linnk.it/4hxdr8/query"))
                                  // ^-- replace with your created realm
                .withSecret("[your realm secret here]").and(new When.Loaded() {

                    @Override
                    public void thenDo(OneTypedReference<Object> realmRoot,
                            OneClient client) {

                        System.out.println("All Children: "
                                + One.selectFrom(realmRoot).allChildren()
                                        .in(client));

                        One.selectFrom(realmRoot)
                                .theChildren()
                                .ofType(String.class)
                                .in(client)
                                .and(new When.ChildrenSelected<OneTypedReference<String>>() {

                                    @Override
                                    public void thenDo(
                                            List<OneTypedReference<String>> values,
                                            OneClient client) {
                                        System.out.println("Found Messages:");
                                        for (OneTypedReference<String> node : values) {
                                            System.out.println("  "
                                                    + One.dereference(node).in(
                                                            client));
                                        }
                                    }

                                });

                        One.selectFrom(realmRoot)
                                .theChildren()
                                .linkingTo(
                                        One.reference("https://u1.linnk.it/zednuw/types/customer"))
                                                         // ^-- replace with your customer type 
                                .in(client)
                                .and(new When.ChildrenSelected<OneTypedReference<Object>>() {

                                    @Override
                                    public void thenDo(
                                            List<OneTypedReference<Object>> nodes,
                                            OneClient client) {
                                        System.out.println("Found Customers:");

                                        for (OneTypedReference<Object> node : nodes) {
                                            System.out.println("  "
                                                    + One.dereference(node).in(
                                                            client));
                                        }

                                    }

                                });

                        One.shutdown(client).and(new ShutdownCallback() {

                            @Override
                            public void onSuccessfullyShutdown() {
                                System.out.println("All queries completed.");
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
