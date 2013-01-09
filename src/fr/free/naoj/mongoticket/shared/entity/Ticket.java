package fr.free.naoj.mongoticket.shared.entity;

import java.util.Date;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class Ticket implements ITicket {

	private static final long serialVersionUID = -3432208426278634297L;

	private Deployment deployment;
	
	private String _id;
	
	private int number = -1;
	
	private String href = null;
	
	private Date delivered = null;
	
	public Ticket() {
		
	}

	@Override
	public String get_id() {
		return _id;
	}

	@Override
	public void set_id(String _id) {
		this._id = _id;
	}

	@Override
	public Deployment getDeployment() {
		return this.deployment;
	}

	@Override
	public void setDeployment(Deployment deployment) {
		this.deployment = deployment;
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
	public int getNumber() {
		return number;
	}

	@Override
	public void setNumber(int number) {
		this.number = number;
	}
}
