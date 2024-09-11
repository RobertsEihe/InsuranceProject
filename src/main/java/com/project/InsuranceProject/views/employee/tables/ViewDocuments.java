package com.project.InsuranceProject.views.employee.tables;

import com.project.InsuranceProject.views.employee.EmployeeLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route(value ="employee/documents", layout = EmployeeLayout.class)
@PageTitle("View Documents")
public class ViewDocuments extends VerticalLayout {

    public ViewDocuments() {
        H2 header = new H2("Document Repository");
        Paragraph description = new Paragraph("Access and manage all insurance-related documents in this section.");
        add(header, description);
    }
}