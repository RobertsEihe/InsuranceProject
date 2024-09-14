package com.project.InsuranceProject.views.admin.tables;

import com.project.InsuranceProject.data.entity.Users;
import com.project.InsuranceProject.data.services.UserService;
import com.project.InsuranceProject.security.Roles;
import com.project.InsuranceProject.views.admin.AdminLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@PageTitle("User Management")
@Route(value = "admin/user-management", layout = AdminLayout.class)
@PermitAll
@RolesAllowed(Roles.ADMIN)
public class UserManagementView extends VerticalLayout {

    private final UserService userService;
    private Grid<Users> userGrid;
    private Select<String> roleFilter;

    public UserManagementView(UserService userService) {
        this.userService = userService;
        setSizeFull();

        createRoleFilter();
        createUserGrid();

        add(roleFilter, userGrid);
    }

    private void createRoleFilter() {
        roleFilter = new Select<>();
        roleFilter.setLabel("Filter by Role");
        roleFilter.setItems("All", "Customer", "Agent", "Employee", "Admin");
        roleFilter.setValue("All");
        roleFilter.addValueChangeListener(event -> updateGrid());
    }

    private void createUserGrid() {
        userGrid = new Grid<>(Users.class);
        userGrid.setColumns("id", "username", "name", "email", "phone", "address", "date_of_birth", "role");
        userGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        userGrid.getColumnByKey("id").setHeader("ID");
        userGrid.getColumnByKey("username").setHeader("Username");
        userGrid.getColumnByKey("name").setHeader("Name");
        userGrid.getColumnByKey("email").setHeader("Email");
        userGrid.getColumnByKey("phone").setHeader("Phone");
        userGrid.getColumnByKey("address").setHeader("Address");
        userGrid.getColumnByKey("date_of_birth").setHeader("Date of Birth");
        userGrid.getColumnByKey("role").setHeader("Role");
        userGrid.addColumn(user -> "CUSTOMER".equals(user.getRole()) ? user.getLoyalty() : "-")
                .setHeader("Loyalty Points")
                .setKey("loyalty");

        updateGrid();
    }

    private void updateGrid() {
        List<Users> users;
        String selectedRole = roleFilter.getValue();

        if ("All".equals(selectedRole)) {
            users = userService.getAllUsers();
        } else {
            users = userService.getUsersByRole(selectedRole);
        }

        userGrid.setItems(users);
    }
}