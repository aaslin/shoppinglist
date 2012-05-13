package se.aaslin.developer.shoppinglist.android.ui.shoppinglists.view;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.mvp.Display;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.presenter.ShoppingListsPresenter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
public class ShoppingListsView implements ShoppingListsPresenter.View {
	public static class ShoppingListsViewAdapter extends BaseAdapter {
		public interface ListElement extends Display {
			
			void setName(String name);
			
			void setInfo(String info);
		}
		
		private final List<ShoppingListDTO> shoppingLists = new ArrayList<ShoppingListDTO>();
		private LayoutInflater inflater;
		
		public ShoppingListsViewAdapter(Context context) {
			this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		public void clear() {
			shoppingLists.clear();
		}
		
		public void add(ShoppingListDTO element) {
			shoppingLists.add(element);
		}

		@Override
		public int getCount() {
			return shoppingLists.size();
		}

		@Override
		public Object getItem(int pos) {
			return shoppingLists.get(pos);
		}

		@Override
		public long getItemId(int pos) {
			return pos;
		}

		@Override
		public View getView(int pos, View contentView, ViewGroup viewGroup) {
			if (contentView == null) {
				contentView = inflater.inflate(R.layout.shoppinglists_listelement, null);
			}
			ListElement element = new ShoppingListsListElementView();
			element.initView(contentView);

			ShoppingListDTO listDTO = shoppingLists.get(pos);
			element.setName(listDTO.getName());
			element.setInfo(listDTO.getOwnerUserName());
			
			return contentView;
		}
	}
	
	private Context context;
	private ShoppingListsViewAdapter adapter;
	
	@InjectView(R.id.listView) ListView listView;
	@InjectView(R.id.progressBarPanel) View progressBarPanel;
	
	public ShoppingListsView(Context context) {
		this.context = context;
	}

	@Override
	public void initView(View view) {
		InjectionUtils.injectViews(this, view);
		
		adapter = new ShoppingListsViewAdapter(context);
		listView.setAdapter(adapter);
	}

	@Override
	public void addLists(List<ShoppingListDTO> listDTOs) {
		adapter.clear();
		for (ShoppingListDTO listDTO : listDTOs) {
			adapter.add(listDTO);
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
