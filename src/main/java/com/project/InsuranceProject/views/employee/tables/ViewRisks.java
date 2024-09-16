package com.project.InsuranceProject.views.employee.tables;

import com.project.InsuranceProject.data.entity.Risk;
import com.project.InsuranceProject.data.services.EmployeeViewService;
import com.project.InsuranceProject.security.Roles;
import com.project.InsuranceProject.views.employee.EmployeeLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@RolesAllowed({Roles.ADMIN, Roles.EMPLOYEE})
@Route(value = "employee/risk-management", layout = EmployeeLayout.class)
@PageTitle("Risk Management")
public class ViewRisks extends VerticalLayout {

    private final EmployeeViewService employeeViewService;
    private Grid<Risk> riskGrid;
    private Select<String> typeFilter;

    public ViewRisks(EmployeeViewService employeeViewService) {
        this.employeeViewService = employeeViewService;
        setSizeFull();

        H2 header = new H2("Risk Management");
        add(header);

        createTypeFilter();
        createRiskGrid();
        createAddRiskForm();
        add(typeFilter, riskGrid);
    }

    private void createTypeFilter() {
        typeFilter = new Select<>();
        typeFilter.setLabel("Filter by Type");
        typeFilter.setItems("All", "House", "Vehicle", "Health");
        typeFilter.setValue("All");
        typeFilter.addValueChangeListener(event -> updateGrid());
    }

    private void createRiskGrid() {
        riskGrid = new Grid<>(Risk.class);
        riskGrid.setColumns("risk_id");

        Editor<Risk> editor = riskGrid.getEditor();
        Binder<Risk> binder = new Binder<>(Risk.class);
        editor.setBinder(binder);
        editor.setBuffered(true);

        TextField typeField = new TextField();
        binder.forField(typeField).bind(Risk::getType, Risk::setType);
        Grid.Column<Risk> typeColumn = riskGrid.addColumn(Risk::getType).setHeader("Type").setEditorComponent(typeField);

        TextField riskField = new TextField();
        binder.forField(riskField).bind(Risk::getRisk, Risk::setRisk);
        Grid.Column<Risk> riskColumn = riskGrid.addColumn(Risk::getRisk).setHeader("Risk").setEditorComponent(riskField);

        NumberField rateField = new NumberField();
        binder.forField(rateField).bind(Risk::getRate, Risk::setRate);
        Grid.Column<Risk> rateColumn = riskGrid.addColumn(Risk::getRate).setHeader("Rate").setEditorComponent(rateField);

        riskGrid.addComponentColumn(risk -> {
            HorizontalLayout buttons = new HorizontalLayout();

            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                riskGrid.getEditor().editItem(risk);
            });

            Button saveButton = new Button("Save");
            saveButton.addClickListener(e -> {
                try {
                    risk.setType(typeField.getValue());
                    risk.setRisk(riskField.getValue());
                    risk.setRate(rateField.getValue());

                    employeeViewService.updateRisk(risk);
                    Notification.show("Risk updated successfully", 3000, Notification.Position.MIDDLE);
                    updateGrid();
                } catch (Exception ex) {
                    Notification.show("Error updating risk: " + ex.getMessage(), 3000, Notification.Position.MIDDLE);
                }
            });

            buttons.add(editButton, saveButton);
            return buttons;
        }).setHeader("Actions").setWidth("300px").setFlexGrow(0);

        editor.addSaveListener(event -> {
            Risk risk = event.getItem();
            try {
                employeeViewService.updateRisk(risk);
                Notification.show("Risk updated successfully", 3000, Notification.Position.MIDDLE);
                updateGrid();
            } catch (Exception e) {
                Notification.show("Error updating risk: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });

        riskGrid.getColumns().forEach(col -> col.setAutoWidth(true).setSortable(true));
    }

    private void createAddRiskForm() {
        HorizontalLayout formLayout = new HorizontalLayout();

        Select<String> newTypeField = new Select<>();
        newTypeField.setLabel("Type");
        newTypeField.setItems("House", "Vehicle", "Health");

        TextField newRiskField = new TextField("Risk");
        NumberField newRateField = new NumberField("Rate");

        Button addButton = new Button("Add Risk");
        formLayout.add(newTypeField, newRiskField, newRateField, addButton);

        addButton.addClickListener(event -> {
            if (newTypeField.isEmpty() || newRiskField.isEmpty() || newRateField.isEmpty()) {
                Notification.show("Please fill all fields", 3000, Notification.Position.MIDDLE);
                return;
            }

            Risk newRisk = new Risk();
            newRisk.setType(newTypeField.getValue());
            newRisk.setRisk(newRiskField.getValue());
            newRisk.setRate(newRateField.getValue());

            try {
                employeeViewService.addRisk(newRisk);
                Notification.show("Risk added successfully", 3000, Notification.Position.MIDDLE);
                updateGrid();
                newTypeField.clear();
                newRiskField.clear();
                newRateField.clear();
            } catch (Exception e) {
                Notification.show("Error adding risk: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });

        add(formLayout);
    }

    private void updateGrid() {
        try {
            String selectedType = typeFilter.getValue();
            if ("All".equals(selectedType)) {
                riskGrid.setItems(employeeViewService.getAllRisks());
            } else {
                riskGrid.setItems(employeeViewService.getRisksByType(selectedType));
            }
        } catch (Exception e) {
            Notification.show("Error loading risks: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }
}