package se.aaslin.developer.shoppinglist.client.content.shoppinglists.presenter;

import java.util.List;

import se.aaslin.developer.shoppinglist.client.common.Display;
import se.aaslin.developer.shoppinglist.client.common.Presenter;
import se.aaslin.developer.shoppinglist.client.content.shoppinglists.service.ShoppingListGridServiceAsync;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.shared.dto.ShoppingListDTO;

import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;

public class ShoppingListGridPresenter implements Presenter {
	public interface ViewDisplay extends Display {

		DisclosurePanel addShoppingList(int row, String name);

		void clearShoppingList();

	}

	ShoppingListGridServiceAsync srv;
	ViewDisplay display;

	public ShoppingListGridPresenter(ShoppingListGridServiceAsync srv, ViewDisplay display) {
		this.srv = srv;
		this.display = display;
		bind();
	}

	@Override
	public void initContainer(HasWidgets container) {
		container.add(display.getViewAsWidget());
	}

	private void bind() {
		srv.getShoppingLists(new AsyncCallback<List<ShoppingListDTO>>() {

			@Override
			public void onSuccess(List<ShoppingListDTO> result) {
				repaintShoppingList(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}

	private void repaintShoppingList(List<ShoppingListDTO> shoppingListDTOs) {
		display.clearShoppingList();
		int i = 0;
		for (final ShoppingListDTO dto : shoppingListDTOs) {
			final DisclosurePanel disclosurePanel = display.addShoppingList(i++, dto.getName());
			disclosurePanel.addOpenHandler(new OpenHandler<DisclosurePanel>() {
				
				@Override
				public void onOpen(OpenEvent<DisclosurePanel> event) {
					srv.getShoppingItems(dto.getID(), new AsyncCallback<List<ShoppingItemDTO>>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert(caught.getMessage());
						}

						@Override
						public void onSuccess(List<ShoppingItemDTO> result) {
							Grid grid = new Grid(1, 2);
							grid.setWidget(0, 0, new Label("Name"));
							grid.setWidget(0, 1, new Label("Amount"));
							int row = 1;
							for(ShoppingItemDTO dto : result){
								grid.resize(row + 1, 2);
								grid.setWidget(row, 0, new Label(dto.getName()));
								grid.setWidget(row, 1, new Label(dto.getAmount()));
								row++;
							}
							disclosurePanel.add(grid);
						}
					});
				}
			});
		}
	}

}
