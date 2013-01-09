package fr.free.naoj.mongoticket.server.entity;

import java.util.Date;

import fr.free.naoj.mongoticket.shared.entity.Deployment;
import fr.free.naoj.mongoticket.shared.entity.IDeployment;


/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class DeploymentEntity extends MongoEntity<Deployment> implements IDeployment {
	
	private static final long serialVersionUID = -4551555303593331512L;

	@MongoField(key="number")
	private int number;
	
	@MongoField(key="deployedDate")
	private Date deployedDate;
	
	@MongoField(key="deployed")
	private boolean deployed;
	
	public DeploymentEntity() {
		super();
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public Date getDeployedDate() {
		return deployedDate;
	}
	
	public void setDeployedDate(Date deployedDate) {
		this.deployedDate = deployedDate;
	}
	
	public boolean isDeployed() {
		return deployed;
	}
	
	public void setDeployed(boolean deployed) {
		this.deployed = deployed;
	}

	@Override
	public Deployment createNew() {
		return new Deployment();
	}
}
