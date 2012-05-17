package se.aaslin.developer.shoppinglist.android.ui.shoppinglists.view;

import java.util.List;

import roboguice.inject.InjectView;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.presenter.ShoppingListsPresenter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
public class ShoppingListsView implements ShoppingListsPresenter.View {
	
	private static final int layout = R.layout.shoppinglists;

	private View view;
	private ArrayAdapter<ShoppingListDTO> adapter;
	
	@InjectView(R.id.listView) ListView listView;
	@InjectView(R.id.progressBarPanel) View progressBarPanel;
	
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
	public void showLoadingSpinner() {
		progressBarPanel.setVisibility(View.VISIBLE);
		listView.setVisibility(View.GONE);
	}

	@Override
	public void disableLoadingSpinner() {
		progressBarPanel.setVisibility(View.GONE);
		listView.setVisibility(View.VISIBLE);
	}

	@Override
	public void bindListAdapter(ArrayAdapter<ShoppingListDTO> adapter) {
		this.adapter = adapter;
		listView.setAdapter(adapter);
	}

	@Override
	public void addList(ShoppingListDTO list) {
		adapter.add(list);
	}

	@Override
	public void removeList(ShoppingListDTO list) {
		adapter.remove(list);
	}
}
