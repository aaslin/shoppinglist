package se.aaslin.developer.shoppinglist.client.info.presenter;

import java.util.Random;

import se.aaslin.developer.robomvp.annotation.InjectDisplay;
import se.aaslin.developer.robomvp.presenter.RoboPresenter;
import se.aaslin.developer.robomvp.view.RoboDisplay;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class InfoPresenter extends RoboPresenter {
	public interface ViewDisplay extends RoboDisplay {
		
		TextView getInfo();
		
		Button getInfoButton();
	}

	@InjectDisplay ViewDisplay display;
	
	@Override
	protected void onBind() {
		display.getInfoButton().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String text = new StringBuilder().append("Hello ").append(new Random().nextInt(10)).toString();
				display.getInfo().setText(text);
			}
		});
	}
}
