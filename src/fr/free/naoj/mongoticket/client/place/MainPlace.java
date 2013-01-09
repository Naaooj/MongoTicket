package fr.free.naoj.mongoticket.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class MainPlace extends Place {

	public static final String TOKEN_HOME = "home";
	public static final String TOKEN_REFRESH = "refresh";
	public static final String TOKEN_FILTER = "filter";
	
	private String urlToken;
	
	public MainPlace() {
		
	}
	
	public MainPlace(String urlToken) {
		this.urlToken = urlToken;
	}
	
	public String getUrlToken() {
		return urlToken;
	}

	public static class Tokenizer implements PlaceTokenizer<MainPlace> {

		@Override
		public MainPlace getPlace(String token) {
			return "".equals(token) || "null".equals(token) ? new MainPlace(TOKEN_HOME) : new MainPlace(token);
		}

		@Override
		public String getToken(MainPlace place) {
			return place.getUrlToken() == null ? TOKEN_HOME : place.getUrlToken();
		}
	}
}
