package se.aaslin.developer.shoppinglist.client.content.dashboard.view;

import se.aaslin.developer.shoppinglist.client.content.dashboard.presenter.DashboardPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class DashboardView extends Composite implements DashboardPresenter.ViewDisplay {
	public interface DashboardViewUIBinder extends UiBinder<HTMLPanel, DashboardView> {
	}
	
	private DashboardViewUIBinder uiBinder = GWT.create(DashboardViewUIBinder.class);
	
	public DashboardView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	

	@Override
	public Widget getViewAsWidget() {
		return this;
	}
}
