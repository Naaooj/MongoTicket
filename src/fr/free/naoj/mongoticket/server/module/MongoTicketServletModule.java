package fr.free.naoj.mongoticket.server.module;

import com.google.inject.servlet.ServletModule;

import fr.free.naoj.mongoticket.server.TicketServiceImpl;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class MongoTicketServletModule extends ServletModule {

	@Override
	protected void configureServlets() {
		serve("/*").with(TicketServiceImpl.class);
	}
}
