//package com.project.InsuranceProject.views.admin.tables;
//
//import com.project.InsuranceProject.data.entity.Agent;
//import com.project.InsuranceProject.data.services.AgentService;
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
//@PageTitle("View Agents")
//@Route(value = "admin/view-agents", layout = AdminLayout.class)
//public class ViewAgents extends VerticalLayout {
//
//    private final AgentService agentService;
//    private Grid<Agent> grid;
//
//    public ViewAgents(AgentService agentService) {
//        this.agentService = agentService;
//        setSizeFull();
//        setJustifyContentMode(JustifyContentMode.START);
//        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
//
//        H2 header = new H2("Agent List");
//        header.addClassNames(Margin.Top.LARGE, Margin.Bottom.MEDIUM);
//
//        createGrid();
//
//        add(header, grid);
//    }
//
//    private void createGrid() {
//        grid = new Grid<>(Agent.class);
//        grid.setColumns("agent_id", "name", "date_of_birth");
//        grid.getColumns().forEach(col -> col.setAutoWidth(true));
//        grid.setItems(agentService.getAllAgents());
//
//        // Add more detailed view on row click
//        grid.asSingleSelect().addValueChangeListener(event -> {
//            if (event.getValue() != null) {
//                // You can open a dialog or navigate to a detailed view here
//                // For example: UI.getCurrent().navigate(AgentDetailView.class, event.getValue().getAgent_id());
//            }
//        });
//
//        // Add a column to show the number of policies for each agent
//        grid.addColumn(agent -> agent.getPolicies().size()).setHeader("Number of Policies");
//    }
//}