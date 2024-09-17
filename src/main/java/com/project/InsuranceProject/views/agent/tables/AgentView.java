package com.project.InsuranceProject.views.agent.tables;

import com.project.InsuranceProject.data.entity.Policy;
import com.project.InsuranceProject.data.services.PolicyService;
import com.project.InsuranceProject.security.Roles;
import com.project.InsuranceProject.views.agent.AgentLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "agent", layout = AgentLayout.class)
@RolesAllowed({Roles.AGENT, Roles.ADMIN})
@PermitAll
@PageTitle("Agent View")
public class AgentView extends VerticalLayout {

	private final PolicyService policyService;
	private Grid<Policy> policyGrid = new Grid<>(Policy.class);
	private TextField searchField = new TextField("Search Policies");

	@Autowired
	public AgentView(PolicyService policyService) {
		this.policyService = policyService;
		setSizeFull();

		configurePolicyGrid();
		add(createSearchLayout(), policyGrid);
		updatePolicyList();
	}

	private HorizontalLayout createSearchLayout() {
		Button searchButton = new Button("Search", click -> updatePolicyList());
		searchField.setPlaceholder("Search by customer ID, name, type...");
		HorizontalLayout searchLayout = new HorizontalLayout(searchField, searchButton);
		searchLayout.setWidthFull();
		searchLayout.setAlignItems(Alignment.CENTER);
		return searchLayout;
	}

	private void configurePolicyGrid() {
		policyGrid.addColumn(Policy::getStart_date).setHeader("Start Date");
		policyGrid.addColumn(Policy::getEnd_date).setHeader("End Date");
		policyGrid.addColumn(Policy::getStatus).setHeader("Status");

		policyGrid.addComponentColumn(policy -> {
			Button approveButton = new Button("Approve", click -> approvePolicy(policy));
			Button denyButton = new Button("Deny", click -> denyPolicy(policy));
			HorizontalLayout actionButtons = new HorizontalLayout(approveButton, denyButton);
			return actionButtons;
		}).setHeader("Actions");

		policyGrid.setSizeFull();
	}

	private void updatePolicyList() {
		List<Policy> policies = policyService.getAllPolicies();  // Fetch all policies
		policyGrid.setItems(policies);
	}

	private void approvePolicy(Policy policy) {
		policyService.approvePolicy(policy);
		Notification.show("Policy approved").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		updatePolicyList();
	}

	private void denyPolicy(Policy policy) {
		policyService.denyPolicy(policy);
		Notification.show("Policy denied").addThemeVariants(NotificationVariant.LUMO_ERROR);
		updatePolicyList();
	}
}
