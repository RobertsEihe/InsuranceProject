package com.project.InsuranceProject.views.employee.tables;

import com.project.InsuranceProject.views.employee.EmployeeLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route(value ="employee/payments", layout = EmployeeLayout.class)
@PageTitle("View Payments")
public class ViewPayments extends VerticalLayout {

    public ViewPayments() {
        H2 header = new H2("Payment Records");
        Paragraph description = new Paragraph("Track and manage all payment transactions here.");
        add(header, description);
    }
}