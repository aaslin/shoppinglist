package se.aaslin.developer.shoppinglist.android.ui.shoppinglists.view;

import roboguice.inject.InjectView;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.presenter.EditShoppingListPresenter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class EditShoppingListView implements EditShoppingListPresenter.View {
	
 	private static final int layout = R.layout.edit_shoppinglist;	
	
	private View view;
	private ArrayAdapter<String> adapter;
	
	@InjectView(R.id.editText) EditText editText;
	@InjectView(R.id.listView) ListView listView;
	@InjectView(R.id.addMember) ImageButton addMemeber;
	
	@Override
	public void initView(Context context) {
		view = LayoutInflater.from(context).inflate(layout, null);
		InjectionUtils.injectViews(this, view);

		listView.setSelector(android.R.color.transparent);
	}

	@Override
	public View getView() {
		return view;
	}

	@Override
	public EditText getName() {
		return editText;
	}

	@Override
	public ImageButton getAddMemberButton() {
		return addMemeber;
	}

	@Override
	public void bindListAdapter(ArrayAdapter<String> adapter) {
		this.adapter = adapter;
		listView.setAdapter(adapter);
	}
	
	@Override
	public void addMember(String name) {
		adapter.add(name);
	}

	@Override
	public void removeMember(String user) {
		adapter.remove(user);
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
