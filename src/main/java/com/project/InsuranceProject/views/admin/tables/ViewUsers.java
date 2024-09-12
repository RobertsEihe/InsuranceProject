//package com.project.InsuranceProject.views.admin.tables;
//
//import com.project.InsuranceProject.data.entity.Customer;
//import com.project.InsuranceProject.data.services.UserService;
//import com.project.InsuranceProject.views.admin.AdminLayout;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.html.H2;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.router.PageTitle;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
//import jakarta.annotation.security.PermitAll;
//
//@PermitAll
//@PageTitle("View Users")
//@Route(value = "admin/view-users", layout = AdminLayout.class)
//public class ViewUsers extends VerticalLayout {
//
//    private final UserService userService;
//    private Grid<Customer> grid;
//
//    public ViewUsers(UserService userService) {
//        this.userService = userService;
//        setSizeFull();
//        setJustifyContentMode(JustifyContentMode.START);
//        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
//
//        H2 header = new H2("User List");
//        header.addClassNames(Margin.Top.LARGE, Margin.Bottom.MEDIUM);
//
//        createGrid();
//
//        add(header, grid);
//    }
//
//    private void createGrid() {
//        grid = new Grid<>(Customer.class);
//        grid.setColumns("customer_id", "name", "email", "phone", "address", "date_of_birth", "loyalty");
//        grid.getColumns().forEach(col -> col.setAutoWidth(true));
//        grid.setItems(userService.getAllCustomers());
//
//        // Add more detailed view on row click
//        grid.asSingleSelect().addValueChangeListener(event -> {
//            if (event.getValue() != null) {
//                // You can open a dialog or navigate to a detailed view here
//                // For example: UI.getCurrent().navigate(UserDetailView.class, event.getValue().getCustomer_id());
//            }
//        });
//    }
//}