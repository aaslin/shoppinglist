package se.aaslin.developer.shoppinglist.android.ui.shoppinglists.view;

import roboguice.inject.InjectView;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.presenter.EditShoppingListPresenter.ListElement;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class EditShoppingListElementView implements ListElement {
	
	@InjectView(R.id.member_name) TextView name;
	@InjectView(R.id.member_status) TextView status;
	@InjectView(R.id.member_remove) ImageButton remove;

	@Override
	public void setName(String text) {
		name.setText(text);
	}

	@Override
	public void setStatus(String text) {
		status.setText(text);
	}

	@Override
	public ImageButton getRemoveButton() {
		return remove;
	}

	@Override
	public void initView(View view) {
		InjectionUtils.injectViews(this, view);
	}
}
