package com.project.InsuranceProject.views.employee.tables;

import com.project.InsuranceProject.views.admin.AdminLayout;
import com.project.InsuranceProject.views.employee.EmployeeLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route(value = "employee/claims", layout = EmployeeLayout.class)
@PageTitle("View Claims")
public class ViewClaims extends VerticalLayout {

    public ViewClaims() {
        H2 header = new H2("Claims Processing");
        Paragraph description = new Paragraph("Manage and process insurance claims in this section.");
        add(header, description);
    }
}