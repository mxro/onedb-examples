package one.examples.z_articles;

import one.client.jre.OneJre;
import one.common.One;
import one.core.dsl.callbacks.When;
import one.core.dsl.callbacks.results.WithLoadResult;

public class G_GettingStarted_Load {

    public static void main(String[] args) {
        OneJre.init("[your API key here]");

        One.load(One.reference("[your realm root address here]"))
                .withSecret("[access token for your realm here]")
                .and(new When.Loaded() {

                    @Override
                    public void thenDo(WithLoadResult<Object> lr) {
                        Object realmRoot = One.dereference(lr.loadedNode()).in(
                                lr.client());

                        System.out.println("Node reference: " + lr.loadedNode());
                        System.out.println("Resolved node: " + realmRoot);
                    }
                });
    }
}
