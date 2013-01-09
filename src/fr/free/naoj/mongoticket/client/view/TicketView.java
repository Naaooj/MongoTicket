package fr.free.naoj.mongoticket.client.view;

import java.util.Date;
import java.util.List;


import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

import fr.free.naoj.mongoticket.client.place.MainPlace;
import fr.free.naoj.mongoticket.shared.entity.Deployment;

/**
 * <p></p>
 *
 * @author Johann Bernez
 */
public class TicketView extends Composite implements ITicketView {

	private Presenter listener = null;
	
	private HTML viewTitle;
	private Button saveButton;
	
	private ListBox deployments = null;
	private TextBox ticketNumber = null;
	private TextBox href = null;
	private DatePicker delivered = null; 
	
	public TicketView() {
		VerticalPanel container = new VerticalPanel();
		FlexTable table = new FlexTable();
		
		Anchor back = new Anchor("Retour");
		
		int rowIndex = 0;
		
		// Titre : création/edition
		viewTitle = new HTML();
		table.setWidget(rowIndex, 0, viewTitle);
		table.getFlexCellFormatter().setColSpan(rowIndex, 0, 2);
		table.getCellFormatter().setHorizontalAlignment(rowIndex++, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		deployments = new ListBox();
		deployments.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				TicketView.this.listener.setDeployment(TicketView.this.deployments.getSelectedIndex());
			}
		});
		table.setWidget(rowIndex, 0, new HTML("Déploiement"));
		table.setWidget(rowIndex++, 1, deployments);
		
		ticketNumber = new TextBox();
		ticketNumber.addKeyPressHandler(new KeyPressHandler() {
		    @Override
		    public void onKeyPress(KeyPressEvent event) {
		    	switch (event.getCharCode()) { 
		            case KeyCodes.KEY_LEFT:
		            case KeyCodes.KEY_DOWN:
		            case KeyCodes.KEY_RIGHT:
		            case KeyCodes.KEY_UP:
		            case KeyCodes.KEY_BACKSPACE:
		            case KeyCodes.KEY_DELETE:
		                return; 
		        } 
		        if (!Character.isDigit(event.getCharCode())) {
		        	event.preventDefault();
		        }
		    }
		});
		ticketNumber.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				TicketView.this.listener.setNumber(!"".equals(event.getValue()) ? Integer.parseInt(event.getValue()) : -1);
			}
		});
		table.setWidget(rowIndex, 0, new HTML("Numéro"));
		table.setWidget(rowIndex++, 1, ticketNumber);
		
		href = new TextBox();
		href.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				TicketView.this.listener.setHref(event.getValue());
			}
		});
		table.setWidget(rowIndex, 0, new HTML("Lien"));
		table.setWidget(rowIndex++, 1, href);
		
		delivered = new DatePicker();
		delivered.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				TicketView.this.listener.setDelivered(event.getValue());
			}
		});
		table.setWidget(rowIndex, 0, new HTML("Commité"));
		table.setWidget(rowIndex++, 1, delivered);
		
		// Bouton de validation
		saveButton= new Button("Enregistrer");
		table.setWidget(rowIndex, 0, saveButton);
		table.getFlexCellFormatter().setColSpan(rowIndex, 0, 2);
		table.getCellFormatter().setHorizontalAlignment(rowIndex++, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		// Création des handlers
		back.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TicketView.this.listener.goTo(new MainPlace());
			}
		});
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TicketView.this.listener.save();
			}
		});
		
		container.add(back);
		container.add(table);
		
		initWidget(container);
	}
	
	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

	@Override
	public void setEdition(boolean edition) {
		viewTitle.setHTML(edition ? "Modification d'un billet" : "Création d'un billet");
	}

	@Override
	public void setDeployments(List<Deployment> deployments) {
		this.deployments.clear();
		if (deployments != null) {
			if (deployments.size() > 5) {
				this.deployments.setVisibleItemCount(5);
			}
			DateTimeFormat dtf = DateTimeFormat.getFormat("dd/MM/yyyy");
			
			for (Deployment d : deployments) {
				this.deployments.addItem(dtf.format(d.getDeployedDate()), d.get_id());
			}
		}
	}

	@Override
	public void setDeployment(Deployment deployment) {
		if (deployment != null) {
			for (int i = 0; i < deployments.getItemCount(); ++i) {
				if (deployments.getValue(i).equals(deployment.get_id())) {
					deployments.setSelectedIndex(i);
					break;
				}
			}
		}
	}

	@Override
	public void setNumber(int number) {
		if (number >= 0) {
			this.ticketNumber.setValue(Integer.toString(number));
		} else {
			this.ticketNumber.setValue("");
		}
	}

	@Override
	public void setHref(String href) {
		this.href.setValue(href);
	}

	@Override
	public void setDelivered(Date delivered) {
		this.delivered.setValue(delivered);
	}
}
