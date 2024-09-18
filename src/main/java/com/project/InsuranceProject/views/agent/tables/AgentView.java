package com.project.InsuranceProject.views.agent.tables;

import com.project.InsuranceProject.data.entity.Policy;
import com.project.InsuranceProject.data.entity.Users;
import com.project.InsuranceProject.data.services.ClaimService;
import com.project.InsuranceProject.data.services.PolicyRetrieveService;
import com.project.InsuranceProject.data.services.UserService;
import com.project.InsuranceProject.security.Roles;
import com.project.InsuranceProject.views.agent.AgentLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

@Route(value = "agent", layout = AgentLayout.class)
@RolesAllowed({Roles.AGENT, Roles.ADMIN})
@PageTitle("Agent View")
public class AgentView extends VerticalLayout {

	private final PolicyRetrieveService policyRetrieveService;
	private final UserService userService;
	private final ClaimService claimService;
	private Grid<Policy> policyGrid = new Grid<>(Policy.class);

	@Autowired
	public AgentView(PolicyRetrieveService policyRetrieveService, UserService userService, ClaimService claimService) {
		this.policyRetrieveService = policyRetrieveService;
		this.userService = userService;
		this.claimService = claimService;

		setSizeFull();
		configurePolicyGrid();
		add(policyGrid);
		updatePolicyList();
	}

	private void configurePolicyGrid() {
		policyGrid.removeAllColumns();
		policyGrid.addColumn(Policy::getPolicy_id).setHeader("Policy ID").setWidth("100px");
		policyGrid.addColumn(policy -> policy.getUsers() != null ? policy.getUsers().getUsername() : "N/A")
				.setHeader("Customer").setWidth("150px");
		policyGrid.addColumn(Policy::getStart_date).setHeader("Start Date").setWidth("120px");
		policyGrid.addColumn(Policy::getEnd_date).setHeader("End Date").setWidth("120px");
		policyGrid.addColumn(Policy::getStatus).setHeader("Status").setWidth("100px");
		policyGrid.addColumn(Policy::getUrStatus).setHeader("UR Status").setWidth("100px");
		policyGrid.addColumn(Policy::getTotalPremium).setHeader("Total Premium").setWidth("120px");

		policyGrid.addComponentColumn(policy -> {
			Button approveButton = new Button("Approve", click -> approvePolicy(policy));
			Button denyButton = new Button("Deny", click -> denyPolicy(policy));
			Button viewClaimsButton = new Button("View Claims", click -> viewClaims(policy));
			return new HorizontalLayout(approveButton, denyButton, viewClaimsButton);
		}).setHeader("Actions").setWidth("300px");

		policyGrid.setSizeFull();
	}

	private void updatePolicyList() {
		Long agentId = getCurrentUserId();
		if (agentId != null) {
			List<Policy> policies = policyRetrieveService.getPoliciesByAgentId(agentId);
			policyGrid.setItems(policies);
		} else {
			Notification.show("Unable to fetch policies. User not found.")
					.addThemeVariants(NotificationVariant.LUMO_ERROR);
		}
	}

	private void approvePolicy(Policy policy) {
		policyRetrieveService.approvePolicy(policy);
		Notification.show("Policy approved").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		updatePolicyList();
	}

	private void denyPolicy(Policy policy) {
		policyRetrieveService.denyPolicy(policy);
		Notification.show("Policy denied").addThemeVariants(NotificationVariant.LUMO_ERROR);
		updatePolicyList();
	}

	private void viewClaims(Policy policy) {
		ViewClaims claimsView = new ViewClaims(policy, claimService);
		claimsView.open();
	}

	private Long getCurrentUserId() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails userDetails) {
			Optional<Users> user = userService.getUserByUsername(userDetails.getUsername());
			return user.map(Users::getId).orElse(null);
		}
		return null;
	}
}
