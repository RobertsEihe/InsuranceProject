package com.project.InsuranceProject.views.employee.froms;

import com.project.InsuranceProject.data.entity.Risk;
import com.project.InsuranceProject.data.services.EmployeeViewService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class RiskForm extends Dialog {

    private final EmployeeViewService employeeViewService;
    private final Runnable onSuccessCallback;

    public RiskForm(EmployeeViewService employeeViewService, Runnable onSuccessCallback) {
        this.employeeViewService = employeeViewService;
        this.onSuccessCallback = onSuccessCallback;
        setHeaderTitle("Add New Risk");

        VerticalLayout dialogLayout = new VerticalLayout();
        Select<String> newTypeField = new Select<>();
        newTypeField.setLabel("Type");
        newTypeField.setItems("House", "Vehicle", "Health");

        TextField newRiskField = new TextField("Risk");
        NumberField newRateField = new NumberField("Rate");

        dialogLayout.add(newTypeField, newRiskField, newRateField);

        Button saveButton = new Button("Save");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.getStyle().set("background-color", "green");
        saveButton.getStyle().set("color", "white");
        saveButton.addClickListener(event -> {
            if (newTypeField.isEmpty() || newRiskField.isEmpty() || newRateField.isEmpty()) {
                Notification.show("Please fill all fields", 3000, Notification.Position.MIDDLE);
                return;
            }

            Risk newRisk = new Risk(
                    newTypeField.getValue(),
                    newRiskField.getValue(),
                    newRateField.getValue()
            );

            try {
                Risk savedRisk = employeeViewService.addRisk(newRisk);
                Notification.show("Risk added successfully with ID: " + savedRisk.getRisk_id(), 3000, Notification.Position.MIDDLE);
                onSuccessCallback.run();
                close();
            } catch (Exception e) {
                Notification.show("Error adding risk: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton.addClickListener(event -> close());

        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);
        dialogLayout.add(buttonLayout);

        add(dialogLayout);
    }
}