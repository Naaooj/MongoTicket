package fr.free.naoj.mongoticket.client;


import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;

import fr.free.naoj.mongoticket.client.mvp.TicketActivityMapper;
import fr.free.naoj.mongoticket.client.mvp.TicketPlaceHistoryMapper;
import fr.free.naoj.mongoticket.client.place.MainPlace;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class MongoTicket implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		IClientFactory factory = GWT.create(IClientFactory.class);
		EventBus eventBus = factory.getEventBus();
		PlaceController placeController = factory.getPlaceController();
		
		SimplePanel container = new SimplePanel();
		
		ActivityMapper activityMapper = new TicketActivityMapper(factory);
		ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
		activityManager.setDisplay(container);
		
		TicketPlaceHistoryMapper historyMapper = GWT.create(TicketPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, new MainPlace());
		
		RootPanel.get().add(container);
		historyHandler.handleCurrentHistory();
	}
}
