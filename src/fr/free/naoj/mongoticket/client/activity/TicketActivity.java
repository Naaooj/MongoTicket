package fr.free.naoj.mongoticket.client.activity;

import java.util.Date;
import java.util.List;


import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

import fr.free.naoj.mongoticket.client.IClientFactory;
import fr.free.naoj.mongoticket.client.activity.event.DeploymentEvent;
import fr.free.naoj.mongoticket.client.activity.event.DeploymentHandler;
import fr.free.naoj.mongoticket.client.place.MainPlace;
import fr.free.naoj.mongoticket.client.place.TicketPlace;
import fr.free.naoj.mongoticket.client.view.ITicketView;
import fr.free.naoj.mongoticket.client.view.ITicketView.Presenter;
import fr.free.naoj.mongoticket.shared.entity.Deployment;
import fr.free.naoj.mongoticket.shared.entity.Ticket;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class TicketActivity extends AbstractActivity implements Presenter {

	private String ticketId;
	private IClientFactory clientFactory = null;
	private Ticket ticket = null;
	private HandlerRegistration registration = null;
	private List<Deployment> deployments;
	
	public TicketActivity(TicketPlace place, IClientFactory clientFactory) {
		this.ticketId = place.getId();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		final ITicketView view = clientFactory.getTicketView();
		
		if (this.ticketId == null) {
			this.ticket = new Ticket();
			start(panel, view);
		} else {
			view.setEdition(true);
			// Il faut rechercher l'objet existant
			this.clientFactory.getService().getTicket(this.ticketId, new AsyncCallback<Ticket>() {
				@Override
				public void onSuccess(Ticket result) {
					TicketActivity.this.ticket = result != null ? result : new Ticket();
					start(panel, view);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					
				}
			});
		}
		
		registration = this.clientFactory.getEventBus().addHandler(DeploymentEvent.TYPE, new DeploymentHandler() {
			@Override
			public void onDeploymentsChanged(DeploymentEvent deploymentEvent) {
				log("Deployments event ticket activity");
				if (deploymentEvent.getDeployments() != null) {
					TicketActivity.this.deployments = deploymentEvent.getDeployments();
					setDeployment(0);
					TicketActivity.this.clientFactory.getTicketView().setDeployments(deploymentEvent.getDeployments());
				}
			}
		});
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				TicketActivity.this.clientFactory.getEventBus().fireEvent(new DeploymentEvent());
			}
		});
	}
	
	@Override
	public void onStop() {
		registration.removeHandler();
		registration = null;
	}

	public static native void log(String message) /*-{
		$wnd.console.log(message);
	}-*/;
	
	private void start(AcceptsOneWidget panel, ITicketView view) {
		view.setPresenter(this);

		view.setDeployment(this.ticket.getDeployment());
		view.setNumber(this.ticket.getNumber());
		view.setHref(this.ticket.getHref());
		view.setDelivered(this.ticket.getDelivered());
		
		panel.setWidget(view);
	}

	@Override
	public void goTo(Place place) {
		this.clientFactory.getPlaceController().goTo(place);
	}

	@Override
	public void save() {
		this.clientFactory.getService().saveTicket(this.ticket, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				TicketActivity.this.clientFactory.getPlaceController().goTo(new MainPlace(MainPlace.TOKEN_REFRESH));
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
			}
		});
	}

	@Override
	public void setDeployment(int index) {
		if (!this.deployments.isEmpty()) {
			this.ticket.setDeployment(this.deployments.get(index));
		}
	}

	@Override
	public void setNumber(int number) {
		this.ticket.setNumber(number);
	}

	@Override
	public void setHref(String href) {
		this.ticket.setHref(href);
	}

	@Override
	public void setDelivered(Date delivered) {
		this.ticket.setDelivered(delivered);
	}
}
