package fr.free.naoj.mongoticket.client;


import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

import fr.free.naoj.mongoticket.client.view.IDeploymentView;
import fr.free.naoj.mongoticket.client.view.IMainView;
import fr.free.naoj.mongoticket.client.view.ITicketView;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public interface IClientFactory {

	public EventBus getEventBus();
	
	public PlaceController getPlaceController();
	
	public TicketServiceAsync getService();
	
	public IMainView.Presenter getMainPresenter();
	
	public IDeploymentView getDeploymentView();
	
	public ITicketView getTicketView();
}
