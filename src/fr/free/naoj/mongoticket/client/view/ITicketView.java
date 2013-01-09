package fr.free.naoj.mongoticket.client.view;

import java.util.Date;
import java.util.List;


import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

import fr.free.naoj.mongoticket.shared.entity.Deployment;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public interface ITicketView extends IsWidget {

	public void setPresenter(Presenter listener);
	
	public void setEdition(boolean edition);
	
	public void setDeployments(List<Deployment> deployments);
	
	public void setDeployment(Deployment deployment);
	
	public void setNumber(int number);
	
	public void setHref(String href);
	
	public void setDelivered(Date delivered);
	
	public interface Presenter {
		public void goTo(Place place);
		
		public void setDeployment(int index);
		
		public void setNumber(int number);
		
		public void setHref(String href);
		
		public void setDelivered(Date delivered);
		
		public void save();
	}
}
