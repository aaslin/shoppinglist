package se.aaslin.developer.shoppinglist.android.ui.shoppingitems.view;

import roboguice.inject.InjectView;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.presenter.ShoppingItemsPresenter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShoppingItemsView implements ShoppingItemsPresenter.View {

	private final static int layout = R.layout.shoppingitems;

	private View view;
	private ArrayAdapter<ShoppingItemDTO> adapter;

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
	public void bindListAdapter(ArrayAdapter<ShoppingItemDTO> adapter) {
		this.adapter = adapter;
		listView.setAdapter(adapter);
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
	public void addItem(ShoppingItemDTO item) {
		adapter.add(item);
	}

	@Override
	public void removeList(ShoppingItemDTO item) {
		adapter.remove(item);
	}
}
