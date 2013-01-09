package fr.free.naoj.mongoticket.client.view;

import java.util.Date;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public interface IDeploymentView extends IsWidget {

	public void setPresenter(Presenter listener);
	
	public void setEdition(boolean edition);
	
	public void setDeploymentDate(Date date);
	
	public void setDeployed(boolean deployed);
	
	public interface Presenter {
		public void goTo(Place place);
		
		public void setDeploymentDate(Date date);

		public void setDeployed(boolean deployed);
		
		public void save();
	}
}
