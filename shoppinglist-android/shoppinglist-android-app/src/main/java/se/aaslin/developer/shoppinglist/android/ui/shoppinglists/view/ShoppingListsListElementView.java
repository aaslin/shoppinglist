package se.aaslin.developer.shoppinglist.android.ui.shoppinglists.view;

import roboguice.inject.InjectView;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.presenter.ShoppingListsPresenter;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ShoppingListsListElementView implements ShoppingListsPresenter.ListElement {
	
	@InjectView(R.id.nameTextView) TextView name;
	@InjectView(R.id.infoTextView) TextView info;
	@InjectView(R.id.more) ImageButton more;
	@InjectView(R.id.edit) ImageButton edit;
	
	@Override
	public void initView(View view) {
		InjectionUtils.injectViews(this, view);
	}
	
	@Override
	public void setName(String text) {
		name.setText(text);
	}
	
	@Override
	public void setInfo(String text) {
		info.setText(text);
	}

	@Override
	public ImageButton getEditButton() {
		return edit;
	}

	@Override
	public ImageButton getMoreButton() {
		return more;
	}
}
