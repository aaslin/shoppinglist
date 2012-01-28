package se.aaslin.developer.shoppinglist.client.info.presenter;

import java.util.Random;

import se.aaslin.developer.roboeventbus.RoboEventBus;
import se.aaslin.developer.robomvp.annotation.InjectDisplay;
import se.aaslin.developer.robomvp.presenter.RoboPresenter;
import se.aaslin.developer.robomvp.view.RoboDisplay;
import se.aaslin.developer.shoppinglist.event.login.LoginEvent;
import se.aaslin.developer.shoppinglist.event.login.LoginEventHandler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class InfoPresenter extends RoboPresenter {
	public interface ViewDisplay extends RoboDisplay {

		TextView getInfo();

		Button getInfoButton();
	}

	@InjectDisplay
	ViewDisplay display;

	@Override
	protected void onBind() {
		RoboEventBus.getInstance().addHandler(LoginEvent.TYPE, new LoginEventHandler() {

			@Override
			public void postInfo(LoginEvent event) {
				TextView info = display.getInfo();
				if(event.isSucceded()){
					info.setText("Connecttion with server");
				}else{
					info.setText("No Connection with server");
				}
			}
		});

		display.getInfoButton().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String text = new StringBuilder().append("Hello ").append(new Random().nextInt(10)).toString();
				display.getInfo().setText(text);
			}
		});
	}
}
