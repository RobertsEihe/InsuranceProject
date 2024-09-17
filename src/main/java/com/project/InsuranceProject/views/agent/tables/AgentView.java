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
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Route(value = "agent", layout = AgentLayout.class)
@RolesAllowed({Roles.AGENT, Roles.ADMIN})
@PermitAll
@PageTitle("Agent View")
public class AgentView extends VerticalLayout {

	private final PolicyService policyService;
	private Grid<Policy> policyGrid = new Grid<>();
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
		// Display the Policy ID
		policyGrid.addColumn(Policy::getPolicy_id)
				.setHeader("Policy ID")
				.setKey("policy_id")
				.setWidth("70px");  // Set width

		// Display the Duration
		policyGrid.addColumn(Policy::getDuration)
				.setHeader("Duration")
				.setKey("duration")
				.setWidth("70px");  // Set width

		// Display Username
		policyGrid.addColumn(policy -> policy.getUsers() != null ? policy.getUsers().getUsername() : "N/A")
				.setHeader("Username")
				.setKey("username")
				.setWidth("150px");  // Set width

		// Display Email
		policyGrid.addColumn(policy -> policy.getUsers() != null ? policy.getUsers().getEmail() : "N/A")
				.setHeader("Email")
				.setKey("email")
				.setWidth("200px");  // Set width

		// Display Phone
		policyGrid.addColumn(policy -> policy.getUsers() != null ? policy.getUsers().getPhone() : "N/A")
				.setHeader("Phone")
				.setKey("phone")
				.setWidth("150px");  // Set width

		// Display Start Date
		policyGrid.addColumn(policy -> policy.getStart_date() != null ?
						policy.getStart_date().format(DateTimeFormatter.ISO_LOCAL_DATE) : "N/A")
				.setHeader("Start Date")
				.setKey("start_date")
				.setWidth("150px");  // Set width

		// Display End Date
		policyGrid.addColumn(policy -> policy.getEnd_date() != null ?
						policy.getEnd_date().format(DateTimeFormatter.ISO_LOCAL_DATE) : "N/A")
				.setHeader("End Date")
				.setKey("end_date")
				.setWidth("150px");  // Set width

		// Display Status
		policyGrid.addColumn(Policy::getStatus)
				.setHeader("Status")
				.setKey("status")
				.setWidth("70px");  // Set width

		// Display Claims
		policyGrid.addColumn(policy -> {
					if (policy.getClaims() != null && !policy.getClaims().isEmpty()) {
						return policy.getClaims().stream()
								.map(claim -> "" + claim.getClaim_id())
								.reduce((claim1, claim2) -> claim1 + ", " + claim2)  // Concatenate claim IDs
								.orElse("No Claims");
					} else {
						return "No Claims";
					}
				}).setHeader("Claims")
				.setWidth("100px");  // Set width

		// Add action buttons for approving/denying policies
		policyGrid.addComponentColumn(policy -> {
					Button approveButton = new Button("Approve", click -> approvePolicy(policy));
					Button denyButton = new Button("Deny", click -> denyPolicy(policy));
					HorizontalLayout actionButtons = new HorizontalLayout(approveButton, denyButton);
					return actionButtons;
				}).setHeader("Actions")
				.setWidth("180px");  // Set width

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
