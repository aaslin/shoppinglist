package se.aaslin.developer.shoppinglist.android.ui.shoppingitems.presenter;

import com.google.inject.Inject;

import se.aaslin.developer.roboeventbus.RoboEventBus;
import se.aaslin.developer.roboeventbus.RoboRegistration;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.exception.HttpException;
import se.aaslin.developer.shoppinglist.android.app.mvp.AsyncCallback;
import se.aaslin.developer.shoppinglist.android.app.mvp.IsView;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingItemDTO;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.back.service.AuthenticationService;
import se.aaslin.developer.shoppinglist.android.back.service.ShoppingListServiceAsync;
import se.aaslin.developer.shoppinglist.android.ui.common.Notification;
import se.aaslin.developer.shoppinglist.android.ui.common.Notification.Type;
import se.aaslin.developer.shoppinglist.android.ui.login.LoginPlace;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.ShoppingItemsPlace;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.event.RemoveShoppingItemEvent;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.event.RemoveShoppingItemEventHandler;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.event.SaveShoppingItemEvent;
import se.aaslin.developer.shoppinglist.android.ui.shoppingitems.event.SaveShoppingItemEventHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

public class EditShoppingItemPresenter extends Presenter {
	public interface View extends IsView {
		
		EditText getName();
		
		EditText getAmount();
		
		EditText getComment();
		
		void showLoadingSpinner();
		
		void disableLoadingSpinner();
	}
	
	public interface Model {
		
		ShoppingListDTO getShoppingListDTO();
		
		ShoppingItemDTO getShoppingItemDTO();
	}
	
	@Inject AuthenticationService authenticationService;
	
	View view;
	Model model;
	ShoppingListServiceAsync srv;
	Activity activity;

	RoboEventBus eventBus;
	RoboRegistration saveRegistration;
	RoboRegistration removeRegistration;
	
	public EditShoppingItemPresenter(View view, Model model, ShoppingListServiceAsync srv, Activity activity) {
		this.view = view;
		this.model = model;
		this.srv = srv;
		this.activity = activity;
		
		eventBus = RoboEventBus.getInstance();
	}
	
	@Override
	protected void onCreate() {
		initFields();
	}

	@Override
	protected void onBind() {
		view.getName().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence seq, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				model.getShoppingItemDTO().setName(s.toString());
				model.getShoppingItemDTO().setChanged(true);
			}
		});
		
		view.getAmount().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence seq, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {	
				model.getShoppingItemDTO().setAmount(s.toString());
				model.getShoppingItemDTO().setChanged(true);
			}
		});
		
		view.getComment().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence seq, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {	
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				model.getShoppingItemDTO().setComment(s.toString());
				model.getShoppingItemDTO().setChanged(true);
			}
		});
		
		removeRegistration = eventBus.addHandler(RemoveShoppingItemEvent.TYPE, new RemoveShoppingItemEventHandler() {
			
			@Override
			public void onRemove() {
				remove();
			}
		});
		
		saveRegistration = eventBus.addHandler(SaveShoppingItemEvent.TYPE, new SaveShoppingItemEventHandler() {
			
			@Override
			public void onSave() {
				save();
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		removeRegistration.removeHandler();
		saveRegistration.removeHandler();
	}

	private void initFields() {
		view.getName().setText(model.getShoppingItemDTO().getName());
		view.getAmount().setText(model.getShoppingItemDTO().getAmount());
		view.getComment().setText(model.getShoppingItemDTO().getComment());
	}

	private void remove() {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(activity.getResources().getString(R.string.disposeItem));
		builder.setCancelable(false);
		builder.setPositiveButton(activity.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				view.showLoadingSpinner();
				if (model.getShoppingItemDTO().isFromDB()) {
					srv.removeShoppingItem(model.getShoppingItemDTO(), new AsyncCallback<Void>() {
						
						@Override
						public void onSuccess(Void result) {
							view.disableLoadingSpinner();
							Type type = Type.REMOVED;
							String name = model.getShoppingItemDTO().getName();
							String username = authenticationService.getUsername();
							Notification notification = new Notification(type, name, username);
							new ShoppingItemsPlace(model.getShoppingListDTO(), notification).moveTo(activity, Intent.FLAG_ACTIVITY_CLEAR_TOP);
						}
						
						@Override
						public void onFailure(Throwable caught) {
							view.disableLoadingSpinner();
							view.disableLoadingSpinner();
							if (caught instanceof HttpException) {
								handleHttpException(caught);
							} else {
								caught.printStackTrace();
								Toast.makeText(activity, caught.getMessage(), Toast.LENGTH_LONG).show();
							}
						}
					});
				} else {
					new ShoppingItemsPlace(model.getShoppingListDTO()).moveTo(activity, Intent.FLAG_ACTIVITY_CLEAR_TOP);
				}
			}
		});
		builder.setNegativeButton(activity.getResources().getString(R.string.no),  new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		builder.create().show();
	}

	private void save() {
		if (model.getShoppingItemDTO().isChanged() || model.getShoppingItemDTO().isFromDB()) {
			view.showLoadingSpinner();
			srv.saveShoppingItem(model.getShoppingListDTO().getID(), model.getShoppingItemDTO(), new AsyncCallback<Void>() {

				@Override
				public void onSuccess(Void result) {
					view.disableLoadingSpinner();
					Type type = model.getShoppingItemDTO().isFromDB() ? Type.UPDATED : Type.ADDED;
					String name = model.getShoppingItemDTO().getName();
					String username = authenticationService.getUsername();
					Notification notification = new Notification(type, name, username);
					new ShoppingItemsPlace(model.getShoppingListDTO(), notification).moveTo(activity, Intent.FLAG_ACTIVITY_CLEAR_TOP);
				}

				@Override
				public void onFailure(Throwable caught) {
					view.disableLoadingSpinner();
					view.disableLoadingSpinner();
					if (caught instanceof HttpException) {
						handleHttpException(caught);
					} else {
						caught.printStackTrace();
						Toast.makeText(activity, caught.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
			});
		}
	}
	
	private void handleHttpException(Throwable caught) {
		HttpException e = (HttpException) caught;
		if (e.getStatusCode() == 403) {
			new LoginPlace().moveTo(activity);
		} else if (e.getStatusCode() == 410) {
			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setMessage(activity.getResources().getString(R.string.itemNotFound));
			builder.setCancelable(false);
			builder.setNeutralButton(activity.getResources().getString(R.string.close), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					new ShoppingItemsPlace(model.getShoppingListDTO()).moveTo(activity, Intent.FLAG_ACTIVITY_CLEAR_TOP);
				}
			});
		}
	}
}
