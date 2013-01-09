package fr.free.naoj.mongoticket.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class DeploymentPlace extends Place {

	public static final String TOKEN_CREATE = "create";
	
	private String id;
	
	public DeploymentPlace(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public static class Tokenizer implements PlaceTokenizer<DeploymentPlace> {

		@Override
		public DeploymentPlace getPlace(String token) {
			return new DeploymentPlace("".equals(token) || "null".equals(token) ? TOKEN_CREATE : token);
		}

		@Override
		public String getToken(DeploymentPlace place) {
			return place.getId() == null ? TOKEN_CREATE : place.getId();
		}
	}
}
