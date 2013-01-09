package fr.free.naoj.mongoticket.client.activity;

import java.util.Date;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import fr.free.naoj.mongoticket.client.IClientFactory;
import fr.free.naoj.mongoticket.client.place.DeploymentPlace;
import fr.free.naoj.mongoticket.client.place.MainPlace;
import fr.free.naoj.mongoticket.client.view.IDeploymentView;
import fr.free.naoj.mongoticket.client.view.IDeploymentView.Presenter;
import fr.free.naoj.mongoticket.shared.entity.Deployment;


/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class DeploymentActivity extends AbstractActivity implements Presenter {

	private String deploymentId;
	private IClientFactory clientFactory;
	private Deployment deployment;
	
	public DeploymentActivity(DeploymentPlace place, IClientFactory clientFactory) {
		this.deploymentId = place.getId();
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		final IDeploymentView deploymentView = clientFactory.getDeploymentView();
		
		if (this.deploymentId != null) {
			deploymentView.setEdition(true);
			// Il faut rechercher l'objet existant
			this.clientFactory.getService().getDeployment(this.deploymentId, new AsyncCallback<Deployment>() {
				@Override
				public void onSuccess(Deployment result) {
					DeploymentActivity.this.deployment = result != null ? result : new Deployment();
					start(panel, deploymentView);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					
				}
			});
		} else {
			// On suppose être en création
			deployment = new Deployment();
			deploymentView.setEdition(false);
			start(panel, deploymentView);
		}
	}
	
	private void start(AcceptsOneWidget panel, IDeploymentView view) {
		view.setPresenter(this);
		
		view.setDeploymentDate(this.deployment.getDeployedDate());
		view.setDeployed(this.deployment.isDeployed());
		
		panel.setWidget(view.asWidget());
	}

	@Override
	public void goTo(Place place) {
		this.clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public void setDeploymentDate(Date date) {
		this.deployment.setDeployedDate(date);
	}

	@Override
	public void setDeployed(boolean deployed) {
		this.deployment.setDeployed(deployed);
	}

	@Override
	public void save() {
		this.clientFactory.getService().saveDeployment(this.deployment, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(Void result) {
				DeploymentActivity.this.goTo(new MainPlace(MainPlace.TOKEN_REFRESH));
			}
		});
	}
}
