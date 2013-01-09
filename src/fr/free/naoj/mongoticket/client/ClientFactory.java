package fr.free.naoj.mongoticket.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

import fr.free.naoj.mongoticket.client.mvp.MainPresenter;
import fr.free.naoj.mongoticket.client.view.DeploymentView;
import fr.free.naoj.mongoticket.client.view.IDeploymentView;
import fr.free.naoj.mongoticket.client.view.IMainView;
import fr.free.naoj.mongoticket.client.view.ITicketView;
import fr.free.naoj.mongoticket.client.view.TicketView;
import fr.free.naoj.mongoticket.client.view.IMainView.Presenter;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class ClientFactory implements IClientFactory {

	private TicketServiceAsync service = GWT.create(TicketService.class);
	private EventBus eventBus = new SimpleEventBus();
	private PlaceController placeController = new PlaceController(eventBus);
	private IMainView.Presenter mainPresenter = new MainPresenter(this);
	private IDeploymentView deploymentView = new DeploymentView();
	private ITicketView ticketView = new TicketView();
	
	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public Presenter getMainPresenter() {
		return mainPresenter;
	}

	@Override
	public IDeploymentView getDeploymentView() {
		return deploymentView;
	}

	@Override
	public TicketServiceAsync getService() {
		return service;
	}

	@Override
	public ITicketView getTicketView() {
		return ticketView;
	}
}
