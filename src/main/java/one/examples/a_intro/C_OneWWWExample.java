package one.examples.a_intro;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.WhenRealmCreated;
import one.core.dsl.callbacks.WhenShutdown;
import one.core.dsl.callbacks.results.WithRealmCreatedResult;
import one.core.nodes.OneNode;

/**
 * Annotated example to upload simple web page using onedb.
 * 
 * @author Max
 *
 */
public class C_OneWWWExample {

    /**
     * The address of a predefined node in the onedb cloud. This node at this
     * address is used to indicate that a node, which points to this node
     * should be rendered as plain HTMl document.
     */
    public final static String htmlPageMarker = "https://admin1.linnk.it/types/v01/isHtmlValue";

    /**
     * A simple html template used to define the documentation node.
     */
    public final static String htmlTemplate = "<html>"
            + "<head><title>%ELEM%</title></head>"
            + "<body>%ELEM% is quite important: "
            + "<a href=\"%PATH%\">open</a></body>" + "</html>";

    /**
     * <p>
     * This application uploads a node 'element1' to the onedb cloud.
     * </p>
     * <p>
     * The node is annotated with a simple documentation node rendered in plain
     * HTML.
     * </p>
     */
    public static void main(String[] args) {
        // The onedb engine needs to be initialized with an onedb API
        // key. You can obtain an API key from www.onedb.de.
        //
        // Replace [Your API Key] with your API key.
        OneJre.init("[Your API Key]");

        // All nodes in onedb are stored in 'realms'.
        // The following statement creates a new empty realm
        // with the name 'elems'.
        One.createRealm("elems").and(new WhenRealmCreated() {

            @Override
            public void thenDo(final WithRealmCreatedResult r) {
                // Clients provide a powerful local cache in interacting
                // with the onedb cloud.
                //
                // All operations are executed against this cache and
                // only when the client is 'shutdown' or 'committed'
                // will the changes be synchronized with the onedb
                // cloud
                //
                // The following statement assigns the client session
                // create while creating the realm to a local helper
                // variable.
                OneClient c = r.client();

                // The following statement appends a new node with
                // value "element1" to the root node of the created
                // realm.
                //
                // The node will be inserted with the address 'element1'
                // relative to the URI of the root node.
                OneNode element1 = One.append("element1").to(r.root())
                        .atAddress("./element1").in(c);

                // The following statements append a node to the newly
                // defined node 'element1'.
                //
                // The appended node holds a text representing a
                // HTML document.
                //
                // A 'marker' node is added to the node to indicate
                // that the node should be rendered as plain HTML.
                String docHtml = htmlTemplate;
                docHtml = docHtml.replace("%ELEM%", "Element 1");
                docHtml = docHtml.replace("%PATH%", "../element1");
                OneNode doc = One.append(docHtml).to(element1)
                        .atAddress("./doc").in(c);
                One.append(One.reference(htmlPageMarker)).to(doc).in(c);

                // The following statement adds a public read token
                // marker node to the root of the created realm.
                //
                // Adding this public read token enables everyone
                // to view but not edit the created node.
                One.append(One.newNode().asPublicReadToken()).to(r.root())
                        .in(c);

                // Since clients might start up background threads, every
                // used client in onedb must be shut down to free
                // the clients resources.
                //
                // Shutting down the client will also trigger a 
                // synchronization of the local changes with
                // the onedb cloud.
                One.shutdown(c).and(new WhenShutdown() {

                    @Override
                    public void thenDo() {
                        // The following statements will print the URI of
                        // the newly create realm as well as the access
                        // secret for this realm to stdout.
                        //
                        // The secret enables to modify the realm at
                        // a later point in time.
                        System.out.println("Uploaded all nodes for realm: "
                                + r.root());
                        System.out.println("Private secret: " + r.secret());
                    }
                });

            }
        });

    }

}
