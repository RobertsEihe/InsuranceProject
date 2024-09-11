package com.project.InsuranceProject.views;

import com.project.InsuranceProject.data.entity.Policy;
import com.project.InsuranceProject.data.services.AgentPolicyService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@PermitAll
@Route("agent-dashboard")
public class AgentView extends VerticalLayout {

	private final Grid<Policy> policyGrid = new Grid<>(Policy.class);
	private final AgentPolicyService agentService;

	@Autowired
	public AgentView(AgentPolicyService agentService) {
		this.agentService = agentService;
		// Layout setup
		add(new Button("Approve Selected Policies", e -> approveSelectedPolicies()));
		add(new Button("Deny Selected Policies", e -> denySelectedPolicies()));

		// Setup the policy grid
		setupPolicyGrid();

		// Load real policies from the database
		loadPendingPolicies();
	}

	// Configure the Vaadin Grid to display policies
	private void setupPolicyGrid() {
		policyGrid.setSelectionMode(Grid.SelectionMode.MULTI);
		policyGrid.addColumn(Policy::getPolicy_id).setHeader("Policy ID");
		policyGrid.addColumn(Policy::getStart_date).setHeader("Start Date");
		policyGrid.addColumn(Policy::getEnd_date).setHeader("End Date");
		policyGrid.addColumn(Policy::getStatus).setHeader("Status");
		add(policyGrid);
	}

	// Load real policies from the database (those in "Under Review" state)
	private void loadPendingPolicies() {
		List<Policy> policies = agentService.getPoliciesUnderReview();
		policyGrid.setItems(policies);
	}

	// Handle approving selected policies
	private void approveSelectedPolicies() {
		List<Policy> selectedPolicies = policyGrid.getSelectedItems().stream().toList();
		if (selectedPolicies.isEmpty()) {
			Notification.show("No policies selected", 3000, Notification.Position.MIDDLE);
		} else {
			for (Policy policy : selectedPolicies) {
				agentService.approvePolicy(policy.getPolicy_id(), 1L); // Pass the agent's ID (assuming agent ID is 1)
			}
			Notification.show("Selected policies approved", 3000, Notification.Position.MIDDLE);
			loadPendingPolicies(); // Refresh the grid
		}
	}

	// Handle denying selected policies
	private void denySelectedPolicies() {
		List<Policy> selectedPolicies = policyGrid.getSelectedItems().stream().toList();
		if (selectedPolicies.isEmpty()) {
			Notification.show("No policies selected", 3000, Notification.Position.MIDDLE);
		} else {
			for (Policy policy : selectedPolicies) {
				agentService.denyPolicy(policy.getPolicy_id(), 1L); // Pass the agent's ID (assuming agent ID is 1)
			}
			Notification.show("Selected policies denied", 3000, Notification.Position.MIDDLE);
			loadPendingPolicies(); // Refresh the grid
		}
	}
}