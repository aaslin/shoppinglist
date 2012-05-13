package se.aaslin.developer.shoppinglist.android.back.service;

import se.aaslin.developer.shoppinglist.android.app.mvp.AsyncCallback;

public interface InstallerServiceAsync {

	void runInstallProcedure(AsyncCallback<Void> callback);
}
