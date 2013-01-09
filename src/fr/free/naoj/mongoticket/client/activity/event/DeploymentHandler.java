package fr.free.naoj.mongoticket.client.activity.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public interface DeploymentHandler extends EventHandler {
	void onDeploymentsChanged(DeploymentEvent deploymentEvent);
}
