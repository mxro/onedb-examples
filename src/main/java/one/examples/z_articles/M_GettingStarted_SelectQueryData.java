package one.examples.z_articles;

import one.client.jre.OneJre;
import one.common.One;
import one.core.dsl.callbacks.ShutdownCallback;
import one.core.dsl.callbacks.When;
import one.core.dsl.callbacks.results.WithChildrenSelectedResult;
import one.core.dsl.callbacks.results.WithLoadResult;
import one.core.nodes.OneTypedReference;

public class M_GettingStarted_SelectQueryData {

    /**
     * @param args
     */
    public static void main(String[] args) {
        OneJre.init("[Your API Key here]");

        // replace reference and secret with details of the realm you have created!!!
        // also replace customer type node!!
        One.load(One.reference("https://u1.linnk.it/4hxdr8/query"))
                                         // ^-- replace with your created realm
                .withSecret("[your realm secret here]").and(new When.Loaded() {

                    @Override
                    public void thenDo(WithLoadResult<Object> lr) {
                        System.out.println("All Children: "
                                + One.selectFrom(lr.loadedNode()).allChildren()
                                        .in(lr.client()));

                        One.selectFrom(lr.loadedNode())
                                .theChildren()
                                .ofType(String.class)
                                .in(lr.client())
                                .and(new When.ChildrenSelected<OneTypedReference<String>>() {

                                    @Override
                                    public void thenDo(
                                            WithChildrenSelectedResult<OneTypedReference<String>> sr) {
                                        System.out.println("Found Messages:");
                                        for (OneTypedReference<String> node : sr
                                                .children()) {
                                            System.out.println("  "
                                                    + One.dereference(node).in(
                                                            sr.client()));
                                        }
                                    }

                                });

                        One.selectFrom(lr.loadedNode())
                                .theChildren()
                                .linkingTo(
                                        One.reference("https://u1.linnk.it/zednuw/types/customer"))
                                                                // ^-- replace with your customer type
                                .in(lr.client())
                                .and(new When.ChildrenSelected<OneTypedReference<Object>>() {

                                    @Override
                                    public void thenDo(
                                            WithChildrenSelectedResult<OneTypedReference<Object>> sr) {
                                        System.out.println("Found Customers:");

                                        for (OneTypedReference<Object> node : sr
                                                .children()) {
                                            System.out.println("  "
                                                    + One.dereference(node).in(
                                                            sr.client()));
                                        }
                                    }

                                });

                        One.shutdown(lr.client()).and(new ShutdownCallback() {

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
