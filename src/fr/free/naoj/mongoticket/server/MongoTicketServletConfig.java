package fr.free.naoj.mongoticket.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import fr.free.naoj.mongoticket.server.module.MongoTicketServiceModule;
import fr.free.naoj.mongoticket.server.module.MongoTicketServletModule;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class MongoTicketServletConfig extends GuiceServletContextListener {
	
	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new MongoTicketServletModule(), new MongoTicketServiceModule());
	}
}
