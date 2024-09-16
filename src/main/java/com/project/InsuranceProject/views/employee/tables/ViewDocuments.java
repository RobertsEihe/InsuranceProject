package com.project.InsuranceProject.views.employee.tables;

import com.project.InsuranceProject.data.entity.Document;
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
@Route(value ="employee/documents", layout = EmployeeLayout.class)
@PageTitle("View Documents")
public class ViewDocuments extends VerticalLayout {

    public ViewDocuments(EmployeeViewService employeeViewService) {
        H2 header = new H2("Document Overview");
        add(header);

        Grid<Document> grid = new Grid<>(Document.class);
        grid.setItems(employeeViewService.getAllDocuments());
        grid.setColumns("document_id", "document");

        add(grid);
        setSizeFull();
    }
}