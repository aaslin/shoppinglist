package se.aaslin.developer.shoppinglist.client.info.view;

import roboguice.inject.InjectView;
import se.aaslin.developer.robomvp.view.RoboComposite;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.client.info.presenter.InfoPresenter;
import android.widget.Button;
import android.widget.TextView;

public class InfoView extends RoboComposite implements InfoPresenter.ViewDisplay{

	@InjectView(R.id.info) TextView info;
	@InjectView(R.id.infoButton) Button infoButton;
	
	@Override
	public TextView getInfo() {
		return info;
	}

	@Override
	public Button getInfoButton() {
		return infoButton;
	}
}
