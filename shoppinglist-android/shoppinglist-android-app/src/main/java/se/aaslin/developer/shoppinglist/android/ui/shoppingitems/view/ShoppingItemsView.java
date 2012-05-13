package se.aaslin.developer.shoppinglist.android.ui.shoppingitems.view;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.mvp.Display;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.presenter.ShoppingItemsPresenter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class ShoppingItemsView implements ShoppingItemsPresenter.ViewDisplay {
	public static class ShoppingItemsViewAdapter extends BaseAdapter {
		public interface ListElement extends Display {
			
			void setName(String name);
			
			void setInfo(String info);
		}
		
		private final List<ShoppingItemDTO> shoppingItems = new ArrayList<ShoppingItemDTO>();
		private LayoutInflater inflater;
		
		public ShoppingItemsViewAdapter(Context context) {
			this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		public void clear() {
			shoppingItems.clear();
		}
		
		public void add(ShoppingItemDTO element) {
			shoppingItems.add(element);
		}

		@Override
		public int getCount() {
			return shoppingItems.size();
		}

		@Override
		public Object getItem(int pos) {
			return shoppingItems.get(pos);
		}

		@Override
		public long getItemId(int pos) {
			return pos;
		}

		@Override
		public View getView(int pos, View contentView, ViewGroup viewGroup) {
			if (contentView == null) {
				contentView = inflater.inflate(R.layout.shoppingitems_listelement, null);
			}
			ListElement element = new ShoppingItemsListElementView();
			element.initView(contentView);

			ShoppingItemDTO listDTO = shoppingItems.get(pos);
			element.setName(listDTO.getName());
			element.setInfo(listDTO.getAmount());
			
			return contentView;
		}
	}
	
	private Context context;
	private ShoppingItemsViewAdapter adapter;

	@InjectView(R.id.listView) ListView listView;
	@InjectView(R.id.progressBarPanel) View progressBarPanel;
	
	public ShoppingItemsView(Context context) {
		this.context = context;
	}

	@Override
	public void initView(View view) {
		InjectionUtils.injectViews(this, view);
		
		adapter = new ShoppingItemsViewAdapter(context);
		listView.setAdapter(adapter);
	}

	@Override
	public void addItems(List<ShoppingItemDTO> itemDTOs) {
		adapter.clear();
		for (ShoppingItemDTO itemDTO : itemDTOs) {
			adapter.add(itemDTO);
		}
		adapter.notifyDataSetInvalidated();
	}

	@Override
	public ListView getListView() {
		return listView;
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
}
