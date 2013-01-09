package fr.free.naoj.mongoticket.client;

import java.util.List;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import fr.free.naoj.mongoticket.shared.entity.Deployment;
import fr.free.naoj.mongoticket.shared.entity.Ticket;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
@RemoteServiceRelativePath("ticket")
public interface TicketService extends RemoteService {
	Ticket getTicket(String ticketId);
	
	List<Ticket> getTickets(String filterId);
	
	void deleteTicket(Ticket ticket);
	
	void saveTicket(Ticket ticket);
	
	Deployment getDeployment(String deploymentId);
	
	List<Deployment> getDeployments(String filterId);
	
	void deleteDeployment(Deployment deployment);
	
	void saveDeployment(Deployment deployment);
}
