package se.aaslin.developer.shoppinglist.android.ui.shoppingitems.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import roboguice.inject.InjectView;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.presenter.EditShoppingItemPresenter;

public class EditShoppingItemView implements EditShoppingItemPresenter.View {

	private static final int layout = R.layout.edit_shoppingitem;
	
	private android.view.View view;
	
	@InjectView(R.id.name) EditText name;
	@InjectView(R.id.amount) EditText amount;
	@InjectView(R.id.comment) EditText commnet;
	
	@Override
	public void initView(Context context) {
		view = LayoutInflater.from(context).inflate(layout, null);
		InjectionUtils.injectViews(this, view);
	}

	@Override
	public View getView() {
		return view;
	}

	@Override
	public EditText getName() {
		return name;
	}

	@Override
	public EditText getAmount() {
		return amount;
	}

	@Override
	public EditText getComment() {
		return commnet;
	}

	@Override
	public void showLoadingSpinner() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disableLoadingSpinner() {
		// TODO Auto-generated method stub
		
	}
}
