package se.aaslin.developer.shoppinglist.android.ui.shoppingitems.view;

import roboguice.inject.InjectView;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.presenter.ShoppingItemsPresenter;

public class ShoppingItemsListElementView implements ShoppingItemsPresenter.ListElement{

	@InjectView(R.id.name) TextView name;
	@InjectView(R.id.amount) TextView amount;
	@InjectView(R.id.edit) ImageButton edit;
	@InjectView(R.id.info) ImageButton info;
	
	@Override
	public void initView(View view) {
		InjectionUtils.injectViews(this, view);
	}
	
	@Override
	public void setName(String text) {
		name.setText(text);
	}
	
	@Override
	public void setAmount(String text) {
		amount.setText(text);
	}

	@Override
	public ImageButton getEditButton() {
		return edit;
	}

	@Override
	public ImageButton getInfoButton() {
		return info;
	}
}
