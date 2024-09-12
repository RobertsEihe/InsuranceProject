package com.project.InsuranceProject.views.employee.tables;

import com.project.InsuranceProject.views.employee.EmployeeLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route(value ="employee/policies", layout = EmployeeLayout.class)
@PageTitle("View Policies")
public class ViewPolicies extends VerticalLayout {

    public ViewPolicies() {
        H2 header = new H2("Policy Overview");
        Paragraph description = new Paragraph("Here you can view and manage all insurance policies.");
        add(header, description);
    }
}