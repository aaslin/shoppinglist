package se.aaslin.developer.shoppinglist.android.ui.shoppingitems.view;

import java.util.Timer;
import java.util.TimerTask;

import roboguice.inject.InjectView;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.presenter.ShoppingItemsPresenter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class ShoppingItemsView implements ShoppingItemsPresenter.View {

	private final static int layout = R.layout.shoppingitems;

	private View view;
	private ArrayAdapter<ShoppingItemDTO> adapter;
	private Context context;
	
	@InjectView(R.id.listView) ListView listView;
	@InjectView(R.id.progressBarPanel) View progressBarPanel;
	@InjectView(R.id.notificationRemove) ImageButton removeNotification;
	@InjectView(R.id.notification) View notification;
	@InjectView(R.id.notificationItem) TextView notificationItem;
	@InjectView(R.id.notificationText) TextView notificationText;
	
	@Override
	public void initView(Context context) {
		this.context = context;
		
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

	@Override
	public void showNotification(String item, String text, int color) {
		notificationItem.setText(item);
		notificationText.setText(text);
		notification.setBackgroundColor(color);
		
		Animation notificationAnim = AnimationUtils.loadAnimation(context, R.anim.notification_animation_show);
		notification.setVisibility(View.VISIBLE);
		notification.startAnimation(notificationAnim);
	}

	@Override
	public ImageButton getRemoveNotificationButton() {
		return removeNotification;
	}

	@Override
	public void hideNotification() {
		Animation notificationAnim = AnimationUtils.loadAnimation(context, R.anim.notification_animation_hide);
		notification.setVisibility(View.VISIBLE);
		notification.startAnimation(notificationAnim);
		
		TimerTask hide = new TimerTask() {
			
			@Override
			public void run() {
				notification.post(new Runnable() {
					
					@Override
					public void run() {
						notification.setVisibility(View.GONE);
					}
				});
			}
		};
		new Timer().schedule(hide, 1000);
	}
}
