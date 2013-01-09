package fr.free.naoj.mongoticket.client.mvp;

import java.util.List;


import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import fr.free.naoj.mongoticket.client.IClientFactory;
import fr.free.naoj.mongoticket.client.activity.event.DeploymentEvent;
import fr.free.naoj.mongoticket.client.activity.event.DeploymentHandler;
import fr.free.naoj.mongoticket.client.place.DeploymentPlace;
import fr.free.naoj.mongoticket.client.place.MainPlace;
import fr.free.naoj.mongoticket.client.place.TicketPlace;
import fr.free.naoj.mongoticket.client.view.IMainView;
import fr.free.naoj.mongoticket.client.view.MainView;
import fr.free.naoj.mongoticket.client.view.IMainView.Presenter;
import fr.free.naoj.mongoticket.shared.entity.Deployment;
import fr.free.naoj.mongoticket.shared.entity.Ticket;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class MainPresenter implements Presenter {

	private IClientFactory clientFactory = null;
	private IMainView mainView = null;
	private List<Deployment> deployments;
	private List<Ticket> tickets;
	
	public MainPresenter(IClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		
		this.mainView = new MainView();
		this.mainView.setPresenter(this);
		
		this.clientFactory.getEventBus().addHandler(DeploymentEvent.TYPE, new DeploymentHandler() {
			@Override
			public void onDeploymentsChanged(DeploymentEvent deploymentEvent) {
				log("Deployments event");
				if (deploymentEvent.getDeployments() == null) {
					if (MainPresenter.this.deployments == null) {
						MainPresenter.this.clientFactory.getService().getDeployments(null, new AsyncCallback<List<Deployment>>() {
							@Override
							public void onSuccess(List<Deployment> deployments) {
								log("Deployments found " + deployments.size());
								MainPresenter.this.deployments = deployments;
								MainPresenter.this.clientFactory.getEventBus().fireEvent(new DeploymentEvent(deployments));
							}
							
							@Override
							public void onFailure(Throwable caught) {
								
							}
						});
					} else {
						MainPresenter.this.clientFactory.getEventBus().fireEvent(new DeploymentEvent(MainPresenter.this.deployments));
					}
				}
			}
		});
	}
	
	public static native void log(String message) /*-{
		$wnd.console.log(message);
	}-*/;
	
	@Override
	public void start(final AcceptsOneWidget panel, final String filterId) {		
		clientFactory.getService().getDeployments(null, new AsyncCallback<List<Deployment>>() {
			@Override
			public void onSuccess(List<Deployment> deployments) {
				panel.setWidget(mainView);
				MainPresenter.this.deployments = deployments;
				MainPresenter.this.mainView.setDeployments(deployments, filterId);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
			}
		});
		clientFactory.getService().getTickets(filterId, new AsyncCallback<List<Ticket>>() {
			@Override
			public void onSuccess(List<Ticket> result) {
				MainPresenter.this.tickets = result;
				MainPresenter.this.mainView.setTickets(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
			}
		});
	}

	@Override
	public void goTo(Place place) {
		this.clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public void modifyDeployment(int index) {
		if (index >= 0 && index < this.deployments.size()) {
			goTo(new DeploymentPlace(this.deployments.get(index).get_id()));
		}
	}

	@Override
	public void deleteDeployment(int index) {
		if (index >= 0 && index < this.deployments.size()) {
			this.clientFactory.getService().deleteDeployment(this.deployments.get(index), new AsyncCallback<Void>() {
				@Override
				public void onFailure(Throwable caught) {
					MainPresenter.this.goTo(new MainPlace());
				}

				@Override
				public void onSuccess(Void result) {
					MainPresenter.this.goTo(new MainPlace());
				}
			});
		}
	}

	@Override
	public void modifyTicket(int index) {
		if (index >= 0 && index < this.tickets.size()) {
			goTo(new TicketPlace(this.tickets.get(index).get_id()));
		}
	}

	@Override
	public void deleteTicket(int index) {
		if (index >= 0 && index < this.tickets.size()) {
			this.clientFactory.getService().deleteTicket(this.tickets.get(index), new AsyncCallback<Void>() {
				@Override
				public void onFailure(Throwable caught) {
					MainPresenter.this.goTo(new MainPlace());
				}

				@Override
				public void onSuccess(Void result) {
					MainPresenter.this.goTo(new MainPlace());
				}
			});
		}
	}
}
