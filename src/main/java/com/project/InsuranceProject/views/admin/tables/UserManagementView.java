package com.project.InsuranceProject.views.admin.tables;

import com.project.InsuranceProject.data.entity.Agent;
import com.project.InsuranceProject.data.entity.Customer;
import com.project.InsuranceProject.data.services.AgentService;
import com.project.InsuranceProject.data.services.UserService;
import com.project.InsuranceProject.views.admin.AdminLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@PageTitle("User Management")
@Route(value = "admin/user-management", layout = AdminLayout.class)
public class UserManagementView extends VerticalLayout {

    private final UserService userService;
    private final AgentService agentService;
    private Grid<Customer> customerGrid;
    private Grid<Agent> agentGrid;
    private Div contentContainer;

    public UserManagementView(UserService userService, AgentService agentService) {
        this.userService = userService;
        this.agentService = agentService;
        setSizeFull();

        Tab customersTab = new Tab("Customers");
        Tab agentsTab = new Tab("Agents");
        Tabs tabs = new Tabs(customersTab, agentsTab);
        tabs.addSelectedChangeListener(event -> setContent(event.getSelectedTab()));

        contentContainer = new Div();
        contentContainer.setSizeFull();

        add(tabs, contentContainer);
        setContent(customersTab);
    }

    private void setContent(Tab tab) {
        contentContainer.removeAll();
        if (tab.getLabel().equals("Customers")) {
            contentContainer.add(createCustomerGrid());
        } else if (tab.getLabel().equals("Agents")) {
            contentContainer.add(createAgentGrid());
        }
    }

    private Grid<Customer> createCustomerGrid() {
        customerGrid = new Grid<>(Customer.class);
        customerGrid.setColumns("customer_id", "name", "email", "phone", "address", "date_of_birth", "loyalty");
        customerGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        customerGrid.setItems(userService.getAllCustomers());
        return customerGrid;
    }

    private Grid<Agent> createAgentGrid() {
        agentGrid = new Grid<>(Agent.class);
        agentGrid.setColumns("agent_id", "name", "date_of_birth");
        agentGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        agentGrid.setItems(agentService.getAllAgents());
        agentGrid.getColumnByKey("agent_id").setHeader("ID");
        agentGrid.getColumnByKey("name").setHeader("Name");
        agentGrid.getColumnByKey("date_of_birth").setHeader("Date of Birth");

        return agentGrid;
    }
}