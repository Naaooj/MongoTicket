package fr.free.naoj.mongoticket.client.mvp;


import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import fr.free.naoj.mongoticket.client.IClientFactory;
import fr.free.naoj.mongoticket.client.activity.DeploymentActivity;
import fr.free.naoj.mongoticket.client.activity.MainActivity;
import fr.free.naoj.mongoticket.client.activity.TicketActivity;
import fr.free.naoj.mongoticket.client.place.DeploymentPlace;
import fr.free.naoj.mongoticket.client.place.MainPlace;
import fr.free.naoj.mongoticket.client.place.TicketPlace;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class TicketActivityMapper implements ActivityMapper {

	private IClientFactory clientFactory;
	
	public TicketActivityMapper(IClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}
	
	@Override
	public Activity getActivity(Place place) {
		if (place instanceof DeploymentPlace) {
			return new DeploymentActivity((DeploymentPlace) place, clientFactory);
		}
		
		if (place instanceof TicketPlace) {
			return new TicketActivity((TicketPlace) place, clientFactory);
		}
		
		return new MainActivity((MainPlace) place, clientFactory);
	}

}
