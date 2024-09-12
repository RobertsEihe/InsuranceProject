package com.project.InsuranceProject.views.employee.tables;

import com.project.InsuranceProject.data.entity.Payment;
import com.project.InsuranceProject.data.services.EmployeeViewService;
import com.project.InsuranceProject.views.employee.EmployeeLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route(value ="employee/payments", layout = EmployeeLayout.class)
@PageTitle("View Payments")
public class ViewPayments extends VerticalLayout {

    public ViewPayments(EmployeeViewService employeeViewService) {
        H2 header = new H2("Payment Overview");
        add(header);

        Grid<Payment> grid = new Grid<>(Payment.class);
        grid.setItems(employeeViewService.getAllPayments());
        grid.setColumns("payment_id", "payment_date", "payment_type", "amount");

        add(grid);
        setSizeFull();
    }
}