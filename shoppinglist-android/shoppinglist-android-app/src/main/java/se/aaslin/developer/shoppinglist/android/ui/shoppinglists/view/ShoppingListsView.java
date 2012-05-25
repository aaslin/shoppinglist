package se.aaslin.developer.shoppinglist.android.ui.shoppinglists.view;

import java.util.Timer;
import java.util.TimerTask;

import roboguice.inject.InjectView;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.util.InjectionUtils;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.ui.common.Notification;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.presenter.ShoppingListsPresenter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
public class ShoppingListsView implements ShoppingListsPresenter.View {
	
	private static final int layout = R.layout.shoppinglists;

	private View view;
	private ArrayAdapter<ShoppingListDTO> adapter;
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

	@Override
	public ImageButton getRemoveNotificationButton() {
		return removeNotification;
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
