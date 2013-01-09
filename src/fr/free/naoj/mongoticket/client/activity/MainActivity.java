package fr.free.naoj.mongoticket.client.activity;


import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import fr.free.naoj.mongoticket.client.IClientFactory;
import fr.free.naoj.mongoticket.client.place.MainPlace;
import fr.free.naoj.mongoticket.client.view.IMainView.Presenter;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class MainActivity extends AbstractActivity {

	private IClientFactory clientFactory;
	private String filterId = null;
	
	public MainActivity(MainPlace place, IClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		
		if (place.getUrlToken() != null && place.getUrlToken().startsWith(MainPlace.TOKEN_FILTER+":")) {
			String[] tokens = place.getUrlToken().split(":");
			if (tokens.length == 2) {
				this.filterId = tokens[1];
			}
		}
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {		
		Presenter presenter = clientFactory.getMainPresenter();
		presenter.start(panel, filterId);
	}
}
