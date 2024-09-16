package com.project.InsuranceProject.views.agent.forms;

import com.project.InsuranceProject.data.entity.Policy;
import com.project.InsuranceProject.data.services.PolicyService;
import com.project.InsuranceProject.views.agent.AgentLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route(value = "agent/policies", layout = AgentLayout.class)
@PageTitle("View Policies")
public class ViewPolicies extends VerticalLayout {

	public ViewPolicies(PolicyService policyService) {
		H2 header = new H2("Policy Overview");
		add(header);

		Grid<Policy> grid = new Grid<>(Policy.class);
		grid.setColumns("policy_id", "start_date", "end_date", "status", "urStatus"); // Make sure these match the property names in your entity
		grid.setItems(policyService.getAllPolicies()); // Make sure the correct method is called to fetch the data


		add(grid);
		setSizeFull();
	}
}
