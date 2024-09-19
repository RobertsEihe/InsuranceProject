package com.project.InsuranceProject.views.admin.tables;

import com.project.InsuranceProject.data.entity.Users;
import com.project.InsuranceProject.data.services.UserService;
import com.project.InsuranceProject.security.Roles;
import com.project.InsuranceProject.views.admin.AdminLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.time.format.DateTimeFormatter;
import java.util.List;

@PageTitle("User Management")
@Route(value = "admin/user-management", layout = AdminLayout.class)
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
        roleFilter.setItems("All", Roles.CUSTOMER, Roles.AGENT, Roles.EMPLOYEE, Roles.ADMIN);
        roleFilter.setValue("All");
        roleFilter.addValueChangeListener(event -> updateGrid());
    }

    private void createUserGrid() {
        userGrid = new Grid<>(Users.class);
        userGrid.setColumns("id", "username", "name", "email");
        userGrid.addComponentColumn(user -> {
            Select<String> roleSelect = new Select<>();
            roleSelect.setItems(Roles.CUSTOMER, Roles.AGENT, Roles.EMPLOYEE, Roles.ADMIN);
            roleSelect.setValue(user.getRole());
            roleSelect.addValueChangeListener(event -> user.setRole(event.getValue()));
            return roleSelect;
        }).setHeader("Role").setKey("role");
        userGrid.addColumn(user -> Roles.CUSTOMER.equals(user.getRole()) ? user.getLoyalty() : "-")
                .setHeader("Loyalty Points")
                .setKey("loyalty");

        userGrid.addComponentColumn(user -> {
            Checkbox fraudCheckbox = new Checkbox(user.getFraudStatus());
            fraudCheckbox.addValueChangeListener(event -> user.setFraudStatus(event.getValue()));
            return fraudCheckbox;
        }).setHeader("Fraud Status").setKey("fraudStatus");
        userGrid.addComponentColumn(user -> {
            Button saveButton = new Button("Save");
            saveButton.getStyle().set("background-color", "green");
            saveButton.getStyle().set("color", "white");
            saveButton.addClickListener(event -> {
                try {
                    userService.updateUser(user);
                    Notification.show("User updated successfully", 3000, Notification.Position.MIDDLE);
                } catch (Exception e) {
                    Notification.show("Error updating user: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
                }
            });
            return saveButton;
        }).setHeader("Actions").setKey("actions");
        userGrid.getColumns().forEach(col -> col.setAutoWidth(true).setSortable(true));
        updateGrid();
    }
    private void updateGrid() {
        try {
            List<Users> users;
            String selectedRole = roleFilter.getValue();
            if ("All".equals(selectedRole)) {
                users = userService.getAllUsers();
            } else {
                users = userService.getUsersByRole(selectedRole);
            }
            userGrid.setItems(users);
        } catch (Exception e) {
            Notification.show("Error loading users: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }
}