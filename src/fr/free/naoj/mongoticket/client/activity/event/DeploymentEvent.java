package fr.free.naoj.mongoticket.client.activity.event;

import java.util.List;


import com.google.gwt.event.shared.GwtEvent;

import fr.free.naoj.mongoticket.shared.entity.Deployment;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class DeploymentEvent extends GwtEvent<DeploymentHandler> {

	public static Type<DeploymentHandler> TYPE = new Type<DeploymentHandler>();
	
	private List<Deployment> deployments = null;
	
	public DeploymentEvent() {
		
	}
	
	public DeploymentEvent(List<Deployment> deployments) {
		this.deployments = deployments;
	}
	
	public List<Deployment> getDeployments() {
		return deployments;
	}

	@Override
	public Type<DeploymentHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DeploymentHandler handler) {
		handler.onDeploymentsChanged(this);
	}
}
