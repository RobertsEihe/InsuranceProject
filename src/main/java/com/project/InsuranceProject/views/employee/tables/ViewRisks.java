package com.project.InsuranceProject.views.employee.tables;

import com.project.InsuranceProject.views.employee.EmployeeLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route(value ="employee/risks", layout = EmployeeLayout.class)
@PageTitle("View Risks")
public class ViewRisks extends VerticalLayout {

    public ViewRisks() {
        H2 header = new H2("Risk Management");
        Paragraph description = new Paragraph("This page displays all the risks associated with insurance policies.");
        add(header, description);
    }
}