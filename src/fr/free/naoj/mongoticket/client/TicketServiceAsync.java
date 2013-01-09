package fr.free.naoj.mongoticket.client;

import java.util.List;


import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.free.naoj.mongoticket.shared.entity.Deployment;
import fr.free.naoj.mongoticket.shared.entity.Ticket;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public interface TicketServiceAsync {
	void getTicket(String ticketId, AsyncCallback<Ticket> callback);
	
	void getTickets(String filterId, AsyncCallback<List<Ticket>> callback);
	
	void deleteTicket(Ticket ticket, AsyncCallback<Void> callback);
	
	void saveTicket(Ticket ticket, AsyncCallback<Void> callback);
	
	void getDeployment(String deploymentId, AsyncCallback<Deployment> callback);
	
	void getDeployments(String filterId, AsyncCallback<List<Deployment>> callback);
	
	void deleteDeployment(Deployment deployment, AsyncCallback<Void> callback);
	
	void saveDeployment(Deployment deployment, AsyncCallback<Void> callback);
}
