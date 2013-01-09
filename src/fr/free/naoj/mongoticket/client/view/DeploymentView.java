package fr.free.naoj.mongoticket.client.view;

import java.util.Date;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

import fr.free.naoj.mongoticket.client.place.MainPlace;

/**
 * <p></p>
 * 
 * @author Johann Bernez
 */
public class DeploymentView extends Composite implements IDeploymentView {

	private Presenter listener;
	private HTML viewTitle;
	private DatePicker deploymentDatePicker;
	private CheckBox deployedCheckBox;
	private Button saveButton;
	
	public DeploymentView() {
		VerticalPanel container = new VerticalPanel();
		FlexTable table = new FlexTable();
		
		Anchor back = new Anchor("Retour");
		
		int rowIndex = 0;
		
		// Titre : création/edition
		viewTitle = new HTML();
		table.setWidget(rowIndex, 0, viewTitle);
		table.getFlexCellFormatter().setColSpan(rowIndex, 0, 2);
		table.getCellFormatter().setHorizontalAlignment(rowIndex++, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		// Date de déploiement
		deploymentDatePicker = new DatePicker();
		deploymentDatePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				listener.setDeploymentDate(event.getValue());
			}
		});
		table.setWidget(rowIndex, 0, new HTML("Date prévue de déploiement"));
		table.setWidget(rowIndex++, 1, deploymentDatePicker);
		
		// Flag deployée ou pas
		deployedCheckBox = new CheckBox();
		table.setWidget(rowIndex, 0, new HTML("Deployé"));
		table.setWidget(rowIndex++, 1, deployedCheckBox);
		
		// Bouton de validation
		saveButton= new Button("Enregistrer");
		table.setWidget(rowIndex, 0, saveButton);
		table.getFlexCellFormatter().setColSpan(rowIndex, 0, 2);
		table.getCellFormatter().setHorizontalAlignment(rowIndex++, 0, HasHorizontalAlignment.ALIGN_CENTER);
		
		// Création des handlers
		back.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DeploymentView.this.listener.goTo(new MainPlace());
			}
		});
		deploymentDatePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				DeploymentView.this.listener.setDeploymentDate(event.getValue());
			}
		});
		deployedCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				DeploymentView.this.listener.setDeployed(event.getValue());
			}
		});
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DeploymentView.this.listener.save();
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
		viewTitle.setHTML(edition ? "Modification d'un déploiement" : "Création d'un dépploiement");
	}

	@Override
	public void setDeploymentDate(Date date) {
		deploymentDatePicker.setValue(date);
	}

	@Override
	public void setDeployed(boolean deployed) {
		deployedCheckBox.setValue(deployed);
	}
}
