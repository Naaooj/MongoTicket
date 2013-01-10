package fr.free.naoj.mongoticket.server.module;

import com.google.inject.AbstractModule;

import fr.free.naoj.mongoticket.server.MongoPropertyLoader;
import fr.free.naoj.mongoticket.server.impl.MongoPropertyLoaderImpl;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class MongoTicketServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(MongoPropertyLoader.class).to(MongoPropertyLoaderImpl.class);
	}
}
