package com.project.InsuranceProject.views.employee.tables;

import com.project.InsuranceProject.data.entity.Risk;
import com.project.InsuranceProject.data.services.EmployeeViewService;
import com.project.InsuranceProject.security.Roles;
import com.project.InsuranceProject.views.employee.EmployeeLayout;
import com.project.InsuranceProject.views.employee.froms.RiskForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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
    private RiskForm riskForm;

    public ViewRisks(EmployeeViewService employeeViewService) {
        this.employeeViewService = employeeViewService;
        setSizeFull();
        createTopBar();
        createRiskGrid();
        createRiskForm();
        add(riskGrid);
        updateGrid();
    }

    private void createTopBar() {
        HorizontalLayout topBar = new HorizontalLayout();
        topBar.setWidthFull();
        topBar.setJustifyContentMode(JustifyContentMode.BETWEEN);
        topBar.setAlignItems(Alignment.BASELINE);

        createTypeFilter();
        createAddRiskButton();

        topBar.add(typeFilter, createAddRiskButton());
        add(topBar);
    }

    private void createTypeFilter() {
        typeFilter = new Select<>();
        typeFilter.setLabel("Filter by Type");
        typeFilter.setItems("All", "House", "Vehicle", "Health");
        typeFilter.setValue("All");
        typeFilter.addValueChangeListener(event -> updateGrid());
    }

    private Button createAddRiskButton() {
        Button addRiskButton = new Button("Add Risk");
        addRiskButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addRiskButton.addClickListener(event -> riskForm.open());
        return addRiskButton;
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
            editButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            editButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                riskGrid.getEditor().editItem(risk);
            });

            Button saveButton = new Button("Save");
            saveButton.getStyle().set("background-color", "green");
            saveButton.getStyle().set("color", "white");
            saveButton.addClickListener(e -> {
                try {
                    editor.save();
                } catch (Exception ex) {
                    Notification.show("Error updating risk: " + ex.getMessage(), 3000, Notification.Position.MIDDLE);
                }
            });

            Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_ICON);
            deleteButton.addClickListener(e -> confirmDelete(risk));

            buttons.add(editButton, saveButton, deleteButton);
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

    private void createRiskForm() {
        riskForm = new RiskForm(employeeViewService, this::updateGrid);
    }

    private void confirmDelete(Risk risk) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Confirm Delete");
        dialog.setText("Are you sure you want to delete this risk?");

        dialog.setCancelable(true);
        dialog.setCancelText("Cancel");

        dialog.setConfirmText("Delete");
        dialog.setConfirmButtonTheme("error primary");

        dialog.addConfirmListener(event -> deleteRisk(risk));

        dialog.open();
    }

    private void deleteRisk(Risk risk) {
        try {
            employeeViewService.deleteRisk(risk.getRisk_id());
            Notification.show("Risk deleted successfully", 3000, Notification.Position.MIDDLE);
            updateGrid();
        } catch (Exception e) {
            Notification.show("Error deleting risk: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
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