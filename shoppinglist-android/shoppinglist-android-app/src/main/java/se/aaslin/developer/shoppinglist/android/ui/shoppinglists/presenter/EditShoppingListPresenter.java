package se.aaslin.developer.shoppinglist.android.ui.shoppinglists.presenter;

import java.util.List;

import se.aaslin.developer.roboeventbus.RoboEventBus;
import se.aaslin.developer.roboeventbus.RoboRegistration;
import se.aaslin.developer.shoppinglist.R;
import se.aaslin.developer.shoppinglist.android.app.mvp.AsyncCallback;
import se.aaslin.developer.shoppinglist.android.app.mvp.Display;
import se.aaslin.developer.shoppinglist.android.app.mvp.IsView;
import se.aaslin.developer.shoppinglist.android.app.mvp.Presenter;
import se.aaslin.developer.shoppinglist.android.back.dto.ShoppingListDTO;
import se.aaslin.developer.shoppinglist.android.back.service.AuthenticationService;
import se.aaslin.developer.shoppinglist.android.back.service.ShoppingListServiceAsync;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.ShoppingListsPlace;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.event.RemoveShoppingListEvent;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.event.RemoveShoppingListEventHandler;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.event.SaveShoppingListEvent;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.event.SaveShoppingListEventHandler;
import se.aaslin.developer.shoppinglist.android.ui.shoppinglists.view.EditShoppingListElementView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.inject.Inject;

public class EditShoppingListPresenter extends Presenter {
	public interface View extends IsView {

		EditText getName();

		ImageButton getAddMemberButton();

		void bindListAdapter(ArrayAdapter<String> adapter);

		void addMember(String name);

		void removeMember(String user);
		
		void showLoadingSpinner();
		
		void disableLoadingSpinner();
	}

	public interface ListElement extends Display {

		void setName(String text);

		void setStatus(String text);

		ImageButton getRemoveButton();
	}

	public interface Model {

		ShoppingListDTO getShoppingListDTO();

		List<String> getAllUsers();

		List<String> getAvailableUsers();
	}

	@Inject	AuthenticationService authenticationService;

	View view;
	Model model;
	ShoppingListServiceAsync srv;
	Activity activity;

	LayoutInflater inflater;
	RoboEventBus eventBus;
	
	RoboRegistration saveRegistration;
	RoboRegistration removeRegistration;

	public EditShoppingListPresenter(View view, Model model, ShoppingListServiceAsync srv, Activity activity) {
		this.view = view;
		this.model = model;
		this.srv = srv;
		this.activity = activity;
		inflater = LayoutInflater.from(activity);
		eventBus = RoboEventBus.getInstance();
	}

	@Override
	protected void onCreate() {
		initList();
	}

	@Override
	protected void onBind() {
		view.getName().addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				model.getShoppingListDTO().setChanged(true);
				model.getShoppingListDTO().setName(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		view.bindListAdapter(new ArrayAdapter<String>(activity, -1) {

			@Override
			public android.view.View getView(final int position, android.view.View convertView, ViewGroup parent) {
				return createMemberListElement(position, convertView, getItem(position));
			}
		});

		view.getAddMemberButton().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(android.view.View v) {
				showMemberPopup();
			}
		});

		saveRegistration = eventBus.addHandler(SaveShoppingListEvent.TYPE, new SaveShoppingListEventHandler() {

			@Override
			public void onSave() {
				save();
			}
		});
		
		removeRegistration = eventBus.addHandler(RemoveShoppingListEvent.TYPE, new RemoveShoppingListEventHandler() {
			
			@Override
			public void onRemove() {
				remove();
			}
		});
	}

	@Override
	protected void onDestroy() {
		removeRegistration.removeHandler();
		saveRegistration.removeHandler();
	}

	private void initList() {
		int index = model.getAllUsers().indexOf(authenticationService.getUsername());
		String owner = model.getAllUsers().remove(index);
		view.addMember(owner);

		model.getAvailableUsers().clear();
		model.getAvailableUsers().addAll(model.getAllUsers());
		List<String> members = model.getShoppingListDTO().getMembers();
		for (String member : members) {
			model.getAvailableUsers().remove(member);
			view.addMember(member);
		}

		view.getName().setText(model.getShoppingListDTO().getName());
		if (model.getShoppingListDTO().getName().length() == 0) {
			view.getName().setFocusable(true);
		}
	}

	private void save() {
		if (model.getShoppingListDTO().isChanged()) {
			view.showLoadingSpinner();
			srv.saveShoppingList(model.getShoppingListDTO(), new AsyncCallback<Void>() {

				@Override
				public void onSuccess(Void result) {
					view.disableLoadingSpinner();
					new ShoppingListsPlace().moveTo(activity, Intent.FLAG_ACTIVITY_CLEAR_TOP);
				}

				@Override
				public void onFailure(Throwable caught) {
					view.disableLoadingSpinner();
					caught.printStackTrace();
					Toast.makeText(activity, caught.getMessage(), Toast.LENGTH_LONG).show();
				}
			});
		} else {
			new ShoppingListsPlace().moveTo(activity, Intent.FLAG_ACTIVITY_CLEAR_TOP);
		}
	}

	private void remove() {			
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(activity.getResources().getString(R.string.disposeList));
		builder.setCancelable(false);
		builder.setPositiveButton(activity.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				view.showLoadingSpinner();
				if (model.getShoppingListDTO().isFromDB()) {
					srv.removeShoppingList(model.getShoppingListDTO(), new AsyncCallback<Void>() {
						
						@Override
						public void onSuccess(Void result) {
							view.disableLoadingSpinner();
							new ShoppingListsPlace().moveTo(activity, Intent.FLAG_ACTIVITY_CLEAR_TOP);
						}
						
						@Override
						public void onFailure(Throwable caught) {
							view.disableLoadingSpinner();
							caught.printStackTrace();
							Toast.makeText(activity, caught.getMessage(), Toast.LENGTH_LONG).show();
						}
					});
				} else {
					new ShoppingListsPlace().moveTo(activity, Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
	
	private android.view.View createMemberListElement(final int position, android.view.View convertView, String user) {
		ListElement element = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.edit_shoppinglist_listelement, null);
			element = new EditShoppingListElementView();
			element.initView(convertView);
			convertView.setTag(element);
		} else {
			element = (ListElement) convertView.getTag();
		}

		if (position == 0) {
			element.setName(user);
			element.setStatus(activity.getResources().getString(R.string.owner));
			element.getRemoveButton().setVisibility(android.view.View.INVISIBLE);
		} else {
			element.setName(user);
			element.setStatus(activity.getResources().getString(R.string.member));
			element.getRemoveButton().setVisibility(android.view.View.VISIBLE);
			element.getRemoveButton().setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(android.view.View v) {
					String user = model.getShoppingListDTO().getMembers().remove(position - 1);
					model.getAvailableUsers().add(user);
					view.removeMember(user);
					view.getAddMemberButton().setEnabled(true);
				}
			});
		}

		return convertView;
	}

	private void showMemberPopup() {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(activity.getResources().getString(R.string.add_member));
		builder.setItems(model.getAvailableUsers().toArray(new String[model.getAvailableUsers().size()]), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String user = model.getAvailableUsers().remove(which);
				model.getShoppingListDTO().getMembers().add(user);
				model.getShoppingListDTO().setChanged(true);
				view.addMember(user);
				if (model.getAvailableUsers().size() == 0) {
					view.getAddMemberButton().setEnabled(false);
				}
			}
		});
		builder.create().show();
	}
}
