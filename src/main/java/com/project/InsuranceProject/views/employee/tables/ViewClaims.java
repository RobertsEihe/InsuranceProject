package com.project.InsuranceProject.views.employee.tables;

import com.project.InsuranceProject.data.entity.Claim;
import com.project.InsuranceProject.data.services.EmployeeViewService;
import com.project.InsuranceProject.views.employee.EmployeeLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route(value ="employee/claims", layout = EmployeeLayout.class)
@PageTitle("View Claims")
public class ViewClaims extends VerticalLayout {

    public ViewClaims(EmployeeViewService employeeViewService) {
        H2 header = new H2("Claim Overview");
        add(header);

        Grid<Claim> grid = new Grid<>(Claim.class);
        grid.setItems(employeeViewService.getAllClaims());
        grid.setColumns("claim_id", "type", "date_loss", "date", "amount", "description");

        add(grid);
        setSizeFull();
    }
}