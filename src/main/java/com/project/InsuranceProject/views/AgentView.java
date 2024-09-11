package com.project.InsuranceProject.views;

import com.project.InsuranceProject.data.entity.Policy;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.ArrayList;
import java.util.List;

@PermitAll
@Route("agent-dashboard")
//@PageTitle("AgentView")
public class AgentView extends VerticalLayout {

	private final Grid<Policy> policyGrid = new Grid<>(Policy.class);
	private final List<Policy> policies = new ArrayList<>();

	public AgentView() {
		// Dummy data for testing UI
		setupDummyPolicies();

		// Setup layout
		add(new Button("Approve Selected Policies", e -> approveSelectedPolicies()));
		add(new Button("Deny Selected Policies", e -> denySelectedPolicies()));

		// Setup the policy grid
		setupPolicyGrid();

		// Add grid to layout
		add(policyGrid);
	}

	// Setup dummy policies for testing
	private void setupDummyPolicies() {
		/*policies.add(new Policy("Andrejs", "2023-01-01", "2024-01-01", 12, true, "UNDER_REVIEW", "UR"));
		policies.add(new Policy("Aleksandrs", "2023-05-01", "2024-05-01", 12, false, "UNDER_REVIEW", "UR"));
		policies.add(new Policy("Roberts", "2022-12-01", "2023-12-01", 12, true, "UNDER_REVIEW", "UR"));
		policies.add(new Policy("Madhavendra", "2022-12-01", "2023-12-01", 12, true, "UNDER_REVIEW", "UR"));
	*/
	}


	// Configure the Vaadin Grid to display policies
	private void setupPolicyGrid() {
		policyGrid.setSelectionMode(Grid.SelectionMode.MULTI);
		policyGrid.setItems(policies);
		policyGrid.addColumn(Policy::getPolicy_id).setHeader("Policy ID");
		policyGrid.addColumn(Policy::getStart_date).setHeader("Start Date");
		policyGrid.addColumn(Policy::getEndDate).setHeader("End Date");
		policyGrid.addColumn(Policy::getStatus).setHeader("Status");
	}

	// Handle approving selected policies
	private void approveSelectedPolicies() {
		List<Policy> selectedPolicies = policyGrid.getSelectedItems().stream().toList();
		if (selectedPolicies.isEmpty()) {
			Notification.show("No policies selected", 3000, Notification.Position.MIDDLE);
		} else {
			for (Policy policy : selectedPolicies) {
				policy.setUr_status("APPROVED");
			}
			policyGrid.getDataProvider().refreshAll();
			Notification.show("Selected policies approved", 3000, Notification.Position.MIDDLE);
		}
	}

	// Handle denying selected policies
	private void denySelectedPolicies() {
		List<Policy> selectedPolicies = policyGrid.getSelectedItems().stream().toList();
		if (selectedPolicies.isEmpty()) {
			Notification.show("No policies selected", 3000, Notification.Position.MIDDLE);
		} else {
			for (Policy policy : selectedPolicies) {
				policy.setUr_status("DENIED");
			}
			policyGrid.getDataProvider().refreshAll();
			Notification.show("Selected policies denied", 3000, Notification.Position.MIDDLE);
		}
	}
}
