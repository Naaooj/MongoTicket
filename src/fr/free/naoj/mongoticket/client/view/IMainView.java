package fr.free.naoj.mongoticket.client.view;

import java.util.List;


import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

import fr.free.naoj.mongoticket.shared.entity.Deployment;
import fr.free.naoj.mongoticket.shared.entity.Ticket;

/**
 * <p>View that displays the list of deployments and associated tickets.</p>
 * 
 * @author Johann Bernez
 */
public interface IMainView extends IsWidget {

	void setDeployments(List<Deployment> deployments, String filterId);
	
	void setTickets(List<Ticket> tickets);
	
	void setPresenter(Presenter presenter);
	
	public interface Presenter {
		void start(AcceptsOneWidget panel, String filterId);
		
		void goTo(Place place);
		
		void modifyDeployment(int index);
		
		void deleteDeployment(int index);
		
		void modifyTicket(int index);
		
		void deleteTicket(int index);
	}
}
