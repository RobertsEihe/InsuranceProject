package com.project.InsuranceProject.views.agent.forms;

import com.project.InsuranceProject.data.entity.Policy;
import com.project.InsuranceProject.data.services.PolicyService;
import com.project.InsuranceProject.security.Roles;
import com.project.InsuranceProject.views.agent.AgentLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@PermitAll
@PageTitle("View Policies")
@Route(value ="agent/policies", layout = AgentLayout.class)
@RolesAllowed({Roles.AGENT, Roles.ADMIN})
public class ViewPolicies extends VerticalLayout {

	private final PolicyService policyService;

	@Autowired
	public ViewPolicies(PolicyService policyService) {
		this.policyService = policyService;

		H2 header = new H2("Policy Overview");
		add(header);

		Grid<Policy> grid = new Grid<>(Policy.class);
		grid.setItems(policyService.getAllPolicies());  // Fetch all policies instead
		grid.setColumns("policy_id", "start_date", "end_date", "duration", "status", "urStatus");

		add(grid);
		setSizeFull();
	}
}
