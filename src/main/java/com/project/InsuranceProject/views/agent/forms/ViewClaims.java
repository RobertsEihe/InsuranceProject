package com.project.InsuranceProject.views.agent.forms;

import com.project.InsuranceProject.data.entity.Claim;
import com.project.InsuranceProject.data.services.ClaimService;
import com.project.InsuranceProject.security.Roles;
import com.project.InsuranceProject.views.agent.AgentLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "agent/claims", layout = AgentLayout.class)
@RolesAllowed({Roles.AGENT, Roles.ADMIN})
@PermitAll
@PageTitle("Agent Claims")
public class ViewClaims extends VerticalLayout {

	public ViewClaims(ClaimService claimService) {
		H2 header = new H2("Claim Overview");
		add(header);

		Grid<Claim> grid = new Grid<>(Claim.class);
		grid.setItems(claimService.getAllClaims());
		grid.setColumns("claim_id", "type", "date_loss", "date", "amount", "description");

		add(grid);
		setSizeFull();
	}
}
