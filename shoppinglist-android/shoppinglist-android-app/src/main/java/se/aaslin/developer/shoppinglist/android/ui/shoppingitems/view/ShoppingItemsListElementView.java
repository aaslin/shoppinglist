package se.aaslin.developer.shoppinglist.android.ui.shoppingitems.view;

import roboguice.inject.InjectView;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import android.view.View;
import android.widget.TextView;

public class ShoppingItemsListElementView implements ShoppingItemsView.ShoppingItemsViewAdapter.ListElement{

	@InjectView(R.id.nameTextView) TextView name;
	@InjectView(R.id.infoTextView) TextView info;
	
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
}
