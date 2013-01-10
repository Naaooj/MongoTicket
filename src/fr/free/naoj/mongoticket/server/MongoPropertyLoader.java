package fr.free.naoj.mongoticket.server;

import fr.free.naoj.mongoticket.shared.exception.InternalException;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public interface MongoPropertyLoader {
	
	static String HOSTNAME = "hostname";
	static String DATABASE = "database";
	static String USERNAME = "username";
	static String PASSWORD = "password";

	void load() throws InternalException;
	
	String getProperty(String name);
}
