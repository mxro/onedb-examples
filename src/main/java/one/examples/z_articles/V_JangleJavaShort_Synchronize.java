package one.examples.z_articles;

import one.client.jre.OneJre;
import one.core.domain.OneClient;
import one.core.dsl.CoreDsl;
import one.core.dsl.callbacks.WhenChildrenSelected;
import one.core.dsl.callbacks.WhenCommitted;
import one.core.dsl.callbacks.WhenLoaded;
import one.core.dsl.callbacks.WhenNodeChanged;
import one.core.dsl.callbacks.WhenShutdown;
import one.core.dsl.callbacks.results.WithChildrenSelectedResult;
import one.core.dsl.callbacks.results.WithLoadResult;
import one.core.dsl.callbacks.results.WithNodeChangedContext;
import one.core.dsl.grammars.MonitorNodeGrammar.Interval;
import one.core.nodes.OneTypedReference;

public class V_JangleJavaShort_Synchronize {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {

		new Thread(new Producer()).start();

		new Consumer().run();
	}

	static class Producer implements Runnable {

		final CoreDsl dsl = OneJre.init();
		final OneClient client = dsl.createClient();

		@Override
		public void run() {
			try {
				Thread.sleep(600);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Making pizza ...");
			dsl.load("[your seed]/pizzaParlor/servings")
					.withSecret("[your secret]").in(client)
					.and(new WhenLoaded() {

						@Override
						public void thenDo(final WithLoadResult<Object> sr) {

							dsl.append(new String("Pizza!"))
									.to(sr.loadedNode()).in(client);

							dsl.commit(client).and(WhenCommitted.DO_NOTHING);

							System.out.println("... pizza made!");
							run();

						}

					});

		}

	}

	static class Consumer implements Runnable {

		private volatile int eaten = 0;

		@Override
		public void run() {
			final CoreDsl dsl = OneJre.init();
			final OneClient client = dsl.createClient();

			dsl.monitor(dsl.reference("[your seed]/pizzaParlor/servings"))
					.inInterval(Interval.FAST).withSecret("[your secret]")
					.in(client).and(new WhenNodeChanged() {

						@Override
						public void thenDo(final WithNodeChangedContext context) {

							checkForPizza(dsl, client, context);

						}

					});
		}

		private void checkForPizza(final CoreDsl dsl, final OneClient client,
				final WithNodeChangedContext context) {
			dsl.selectFrom(context.changedNode()).theChildren()
					.ofType(String.class).in(client)
					.and(new WhenChildrenSelected<OneTypedReference<String>>() {

						@Override
						public void thenDo(
								final WithChildrenSelectedResult<OneTypedReference<String>> csr) {

							for (final OneTypedReference<String> pizza : csr
									.children()) {

								if (!dsl.dereference(pizza).in(client)
										.equals("Pizza!")) {
									continue;
								}

								dsl.replace(pizza)
										.with("Yummy (" + eaten + ")!")
										.in(client);
								eaten++;
								System.out.println("Ate " + eaten
										+ " Pizza(s)!");
							}

							dsl.commit(client).and(WhenCommitted.DO_NOTHING);

							if (eaten > 10) {
								System.out.println("Had enough pizza.");
								context.monitor().stop(WhenShutdown.DO_NOTHING);
								System.exit(0);
							}
						}
					});
		}

	}

}
