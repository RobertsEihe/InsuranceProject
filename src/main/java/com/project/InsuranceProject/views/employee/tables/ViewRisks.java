package com.project.InsuranceProject.views.employee.tables;

import com.project.InsuranceProject.data.entity.Risk;
import com.project.InsuranceProject.data.services.EmployeeViewService;
import com.project.InsuranceProject.security.Roles;
import com.project.InsuranceProject.views.employee.EmployeeLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

@RolesAllowed({Roles.ADMIN,Roles.EMPLOYEE})
@Route(value ="employee/risks", layout = EmployeeLayout.class)
@PageTitle("View Risks")
public class ViewRisks extends VerticalLayout {

    public ViewRisks(EmployeeViewService employeeViewService) {
        H2 header = new H2("Risk Overview");
        add(header);

        Grid<Risk> grid = new Grid<>(Risk.class);
        grid.setItems(employeeViewService.getAllRisks());
        grid.setColumns("risk_id", "type", "risk", "rate");

        add(grid);
        setSizeFull();
    }
}