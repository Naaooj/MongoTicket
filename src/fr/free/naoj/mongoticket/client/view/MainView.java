package fr.free.naoj.mongoticket.client.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.free.naoj.mongoticket.client.place.DeploymentPlace;
import fr.free.naoj.mongoticket.client.place.MainPlace;
import fr.free.naoj.mongoticket.client.place.TicketPlace;
import fr.free.naoj.mongoticket.shared.entity.Deployment;
import fr.free.naoj.mongoticket.shared.entity.Ticket;

/**
 * <p></p>
 * 
 * @author Johann Bernez
 */
public class MainView extends Composite implements IMainView {

	interface MyUiBinder extends UiBinder<Widget, MainView> {};
	
	private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	interface MainStyle extends CssResource {
		String table();
		String header();
	}
	
	private FlexTable tableDeployments;
	private FlexTable tableTickets;
	
	private Presenter presenter;
	
	@UiField MainStyle style;
	
	@UiField VerticalPanel container;
	@UiField Grid grid;
	@UiField ListBox deployments;
	@UiField Anchor addDeployment;
	@UiField Anchor addTicket;
	
	public MainView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		deployments.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String value = MainView.this.deployments.getValue(MainView.this.deployments.getSelectedIndex());
				MainView.this.presenter.goTo(new MainPlace(value != null ? MainPlace.TOKEN_FILTER + ":" + value : MainPlace.TOKEN_REFRESH));
			}
		});
		
		ClickHandler anchorHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (event.getSource() == addTicket) {
					presenter.goTo(new TicketPlace(null));
				} else if (event.getSource() == addDeployment) {
					presenter.goTo(new DeploymentPlace(null));
				}
			}
		};
		addTicket.addClickHandler(anchorHandler);
		addDeployment.addClickHandler(anchorHandler);
		
		grid.getCellFormatter().getElement(1, 0).setAttribute("colspan", "2");
		
		tableDeployments = new FlexTable();
		tableDeployments.getElement().addClassName(style.table());
		tableDeployments.getRowFormatter().addStyleName(0, style.header());
		tableDeployments.setText(0, 0, "#");
		tableDeployments.setWidget(0, 1, new HTML("Date"));
		tableDeployments.setWidget(0, 2, new HTML("Déployé"));
		tableDeployments.setWidget(0, 3, new HTML(""));
		tableDeployments.setWidget(0, 4, new HTML(""));
		
		
		tableTickets = new FlexTable();
		tableTickets.getElement().addClassName(style.table());
		tableTickets.getRowFormatter().addStyleName(0, style.header());
		tableTickets.setWidget(0, 0, new HTML("#"));
		tableTickets.setWidget(0, 1, new HTML("Déploiement"));
		tableTickets.setWidget(0, 2, new HTML("Ticket"));
		tableTickets.setWidget(0, 3, new HTML("Date commit"));
		tableTickets.setWidget(0, 4, new HTML(""));
		tableTickets.setWidget(0, 5, new HTML(""));
		
		tableDeployments.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Cell c = tableDeployments.getCellForEvent(event);
				if (c.getRowIndex() > 0) {
					if (c.getCellIndex() == 3) {
						MainView.this.presenter.modifyDeployment(c.getRowIndex() - 1);
						return;
					}
					if (c.getCellIndex() == 4) {
						MainView.this.presenter.deleteDeployment(c.getRowIndex() - 1);
						return;
					}
				}
			}
		});
		tableTickets.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Cell c = tableTickets.getCellForEvent(event);
				if (c.getRowIndex() > 0) {
					if (c.getCellIndex() == 4) {
						MainView.this.presenter.modifyTicket(c.getRowIndex() - 1);
						return;
					}
					if (c.getCellIndex() == 5) {
						MainView.this.presenter.deleteTicket(c.getRowIndex() - 1);
						return;
					}
				}
			}
		});

		container.add(tableDeployments);
		container.add(new HTML("&nbsp;"));
		container.add(tableTickets);
	}
	
	@Override
	public void setDeployments(List<Deployment> deployments, String filterId) {
		clearDeployments();
		
		if (deployments != null) {
			if (deployments.size() > 5) {
				this.deployments.setVisibleItemCount(5);
			}
			DateTimeFormat dtf = DateTimeFormat.getFormat("dd/MM/yyyy");
			
			int index = 1;
			String value = null;
			Anchor modify;
			Anchor delete;
			
			this.deployments.addItem("");

			for (Deployment d : deployments) {
				value = d.getDeployedDate() != null ? dtf.format(d.getDeployedDate()) : "...";
				this.deployments.addItem(value, d.get_id());
				
				if (d.get_id().equals(filterId)) {
					this.deployments.setSelectedIndex(this.deployments.getItemCount() - 1);
				}
				
				if (filterId == null || d.get_id().equals(filterId)) {
					modify = new Anchor("Modifier");
					delete = new Anchor("Supprimer");
					
					this.tableDeployments.setText(index, 0, Integer.toString(index));
					this.tableDeployments.setText(index, 1, value);
					this.tableDeployments.setText(index, 2, d.isDeployed() ? "Deployé" : "Pas déployé");
					this.tableDeployments.setWidget(index, 3, modify);
					this.tableDeployments.setWidget(index, 4, delete);
					index++;
				}
			}
			
		} else {
			this.deployments.setVisibleItemCount(0);
		}
	}
	
	@Override
	public void setTickets(List<Ticket> tickets) {
		clearTickets();
		
		if (tickets != null) {
			DateTimeFormat dtf = DateTimeFormat.getFormat("dd/MM/yyyy");
			
			int index = 1;

			Anchor modify;
			Anchor delete;
			
			for (Ticket t : tickets) {				
				modify = new Anchor("Modifier");
				delete = new Anchor("Supprimer");
				
				this.tableTickets.setText(index, 0, Integer.toString(index));
				this.tableTickets.setText(index, 1, t.getDeployment() != null && t.getDeployment().getDeployedDate() != null ? dtf.format(t.getDeployment().getDeployedDate()) : "");
				this.tableTickets.setWidget(index, 2, t.getHref() != null ? new Anchor(Integer.toString(t.getNumber()), t.getHref(), "_blank") : new HTML(Integer.toString(t.getNumber())));
				this.tableTickets.setText(index, 3, t.getDelivered() != null ? dtf.format(t.getDelivered()) : "");
				this.tableTickets.setWidget(index, 4, modify);
				this.tableTickets.setWidget(index, 5, delete);
				index++;
			}
			
		} else {
			this.deployments.setVisibleItemCount(0);
		}
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	private void clearDeployments() {
		this.deployments.clear();
		for (int i = tableDeployments.getRowCount() - 1; i > 0; --i) {
			this.tableDeployments.removeRow(i);
		}
	}
	
	private void clearTickets() {
		for (int i = tableTickets.getRowCount() - 1; i > 0; --i) {
			this.tableTickets.removeRow(i);
		}
	}
}
