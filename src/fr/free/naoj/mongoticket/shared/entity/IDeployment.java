package fr.free.naoj.mongoticket.shared.entity;

import java.util.Date;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public interface IDeployment extends IEntity {
	public int getNumber();
	
	public void setNumber(int number);
	
	public Date getDeployedDate();
	
	public void setDeployedDate(Date deployedDate);
	
	public boolean isDeployed();
	
	public void setDeployed(boolean deployed);
}
