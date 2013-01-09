package fr.free.naoj.mongoticket.server.entity;

import java.util.Date;

import fr.free.naoj.mongoticket.shared.entity.Deployment;
import fr.free.naoj.mongoticket.shared.entity.ITicket;
import fr.free.naoj.mongoticket.shared.entity.Ticket;


/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class TicketEntity extends MongoEntity<Ticket> implements ITicket {

	private static final long serialVersionUID = -3692529935288283413L;

	@MongoField(key="deployment", relation=DeploymentEntity.class)
	private DeploymentEntity deployment = null;
	
	@MongoField(key="number")
	private int number = 0;
	
	@MongoField(key="href")
	private String href = null;
	
	@MongoField(key="delivered")
	private Date delivered = null;
	
	public TicketEntity() {
		super();
	}

	@Override
	public Deployment getDeployment() {
		return this.deployment != null ? this.deployment.convertTo() : null;
	}

	@Override
	public void setDeployment(Deployment deployment) {
		DeploymentEntity entity = new DeploymentEntity();
		entity.convertFrom(deployment);
		this.deployment = entity;
	}

	@Override
	public int getNumber() {
		return number;
	}

	@Override
	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String getHref() {
		return href;
	}

	@Override
	public void setHref(String href) {
		this.href = href;
	}

	@Override
	public Date getDelivered() {
		return delivered;
	}

	@Override
	public void setDelivered(Date delivered) {
		this.delivered = delivered;
	}

	@Override
	public Ticket createNew() {
		return new Ticket();
	}
}
