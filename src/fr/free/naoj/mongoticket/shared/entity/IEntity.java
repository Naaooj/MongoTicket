package fr.free.naoj.mongoticket.shared.entity;

import java.io.Serializable;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public interface IEntity extends Serializable {

	public String get_id();
	
	public void set_id(String _id);
}
