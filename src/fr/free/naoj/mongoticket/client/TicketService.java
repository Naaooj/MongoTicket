package fr.free.naoj.mongoticket.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import fr.free.naoj.mongoticket.shared.entity.Deployment;
import fr.free.naoj.mongoticket.shared.entity.Ticket;
import fr.free.naoj.mongoticket.shared.exception.InternalException;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
@RemoteServiceRelativePath("ticket")
public interface TicketService extends RemoteService {
	Ticket getTicket(String ticketId) throws InternalException;
	
	List<Ticket> getTickets(String filterId) throws InternalException;
	
	void deleteTicket(Ticket ticket) throws InternalException;
	
	void saveTicket(Ticket ticket) throws InternalException;
	
	Deployment getDeployment(String deploymentId) throws InternalException;
	
	List<Deployment> getDeployments(String filterId) throws InternalException;
	
	void deleteDeployment(Deployment deployment) throws InternalException;
	
	void saveDeployment(Deployment deployment) throws InternalException;
}
