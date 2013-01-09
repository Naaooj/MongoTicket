package fr.free.naoj.mongoticket.shared.entity;

import java.util.Date;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class Deployment implements IDeployment {

	private static final long serialVersionUID = 7508872684129672019L;

	private String _id;
	
	private int number;
	
	private Date deployedDate;
	
	private boolean deployed;
	
	public Deployment() {
		
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
	public int getNumber() {
		return number;
	}

	@Override
	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public Date getDeployedDate() {
		return deployedDate;
	}

	@Override
	public void setDeployedDate(Date deployedDate) {
		this.deployedDate = deployedDate;
	}

	@Override
	public boolean isDeployed() {
		return deployed;
	}

	@Override
	public void setDeployed(boolean deployed) {
		this.deployed = deployed;
	}
}
