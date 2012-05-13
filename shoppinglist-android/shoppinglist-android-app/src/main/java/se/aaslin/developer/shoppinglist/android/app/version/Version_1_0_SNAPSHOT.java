package se.aaslin.developer.shoppinglist.android.app.version;


public class Version_1_0_SNAPSHOT extends UpdateScript {

	@Override
	protected void onPreUpgrade() {
	}

	@Override
	protected void onUpgrade() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPostUpgrade() {
	}
}