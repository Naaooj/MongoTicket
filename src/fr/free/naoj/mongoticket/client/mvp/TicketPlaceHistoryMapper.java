package fr.free.naoj.mongoticket.client.mvp;


import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import fr.free.naoj.mongoticket.client.place.DeploymentPlace;
import fr.free.naoj.mongoticket.client.place.MainPlace;
import fr.free.naoj.mongoticket.client.place.TicketPlace;

@WithTokenizers({MainPlace.Tokenizer.class, DeploymentPlace.Tokenizer.class, TicketPlace.Tokenizer.class})
public interface TicketPlaceHistoryMapper extends PlaceHistoryMapper {

}
