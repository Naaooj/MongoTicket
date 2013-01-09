package fr.free.naoj.mongoticket.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class TicketPlace extends Place {

	public static final String TOKEN_CREATE = "create";
	
	private String id;
	
	public TicketPlace(String id) {
		this.id = id;
	}
	
	public String getId() {
		return TOKEN_CREATE.equals(id) ? null : id;
	}
	
	public static class Tokenizer implements PlaceTokenizer<TicketPlace> {

		@Override
		public TicketPlace getPlace(String token) {
			return new TicketPlace("".equals(token) || "null".equals(token) ? TOKEN_CREATE : token);
		}

		@Override
		public String getToken(TicketPlace place) {
			return place.getId() == null ? TOKEN_CREATE : place.getId();
		}
	}
}
