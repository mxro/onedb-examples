package one.examples.a_intro;

import one.client.jre.OneJre;
import one.common.One;
import one.core.domain.OneClient;
import one.core.dsl.callbacks.ShutdownCallback;
import one.core.dsl.callbacks.When;
import one.core.dsl.callbacks.results.WithRealmCreatedResult;

public class A_CreateRealm {

	public static void main(String[] args) {

		OneJre.init("[your api key here]"); // insert your onedb API key here.

		One.createRealm("foo").and(new When.RealmCreated() {

		    @Override
            public void thenDo(WithRealmCreatedResult cr) {
		        System.out.println("Realm has been created.\n");
                System.out.println("Realm root: " + cr.root());
                System.out.println("Realm secret: " + cr.secret());
                
                shutdown(cr.client());
            }
		    
			

			private void shutdown(OneClient arg0) {
				One.shutdown(arg0).and(new ShutdownCallback() {

					@Override
					public void onSuccessfullyShutdown() {
						System.out.println("Client successfully shut down.");
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
