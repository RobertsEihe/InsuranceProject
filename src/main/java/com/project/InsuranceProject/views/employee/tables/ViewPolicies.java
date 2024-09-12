package com.project.InsuranceProject.views.employee.tables;

import com.project.InsuranceProject.data.entity.Policy;
import com.project.InsuranceProject.data.services.EmployeeViewService;
import com.project.InsuranceProject.views.employee.EmployeeLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route(value ="employee/policies", layout = EmployeeLayout.class)
@PageTitle("View Policies")
public class ViewPolicies extends VerticalLayout {

    private final EmployeeViewService employeeViewService;

    public ViewPolicies(EmployeeViewService employeeViewService) {
        this.employeeViewService = employeeViewService;

        H2 header = new H2("Policy Overview");
        add(header);

        Grid<Policy> grid = new Grid<>(Policy.class);
        grid.setItems(employeeViewService.getAllPolicies());
        grid.setColumns("policy_id", "start_date", "end_date", "duration", "aut_renewal", "status", "ur_status");

        add(grid);
        setSizeFull();
    }
}