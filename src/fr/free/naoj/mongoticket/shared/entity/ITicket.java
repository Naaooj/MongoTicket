package fr.free.naoj.mongoticket.shared.entity;

import java.util.Date;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public interface ITicket extends IEntity {
	public Deployment getDeployment();

	public void setDeployment(Deployment deploymentId);
	
	public int getNumber();

	public void setNumber(int number);

	public String getHref();

	public void setHref(String href);

	public Date getDelivered();

	public void setDelivered(Date delivered);
}
