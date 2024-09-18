package com.project.InsuranceProject.views.agent.tables;

import com.project.InsuranceProject.data.entity.Claim;
import com.project.InsuranceProject.data.entity.Policy;
import com.project.InsuranceProject.data.services.ClaimService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ViewClaims extends Dialog {
	private Grid<Claim> claimGrid;
	private final ClaimService claimService;

	public ViewClaims(Policy policy, ClaimService claimService) {
		this.claimService = claimService;
		setWidth("70vw");
		setHeight("70vh");
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		H3 header = new H3("Claims for Policy ID: " + policy.getPolicy_id());
		layout.add(header);

		claimGrid = new Grid<>(Claim.class);
		configureClaimGrid();
		claimGrid.setItems(policy.getClaims());
		layout.add(claimGrid);
		Button closeButton = new Button("Close", e -> close());
		layout.add(closeButton);

		add(layout);
	}

	private void configureClaimGrid() {
		claimGrid.removeAllColumns();
		claimGrid.addColumn(Claim::getClaim_id).setHeader("Claim ID");
		claimGrid.addColumn(Claim::getType).setHeader("Type");
		claimGrid.addColumn(Claim::getDate_loss).setHeader("Date of Loss");
		claimGrid.addColumn(Claim::getDate).setHeader("Claim Date");
		claimGrid.addColumn(Claim::getAmount).setHeader("Amount");
		claimGrid.addColumn(Claim::getStatus).setHeader("Status");

		claimGrid.addComponentColumn(claim -> {
			Button approveButton = new Button("Approve", click -> approveClaim(claim));
			Button denyButton = new Button("Deny", click -> denyClaim(claim));
			denyButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
			approveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
			return new HorizontalLayout(approveButton, denyButton);
		}).setHeader("Actions");
	}
	private void approveClaim(Claim claim) {
		claimService.approveClaim(claim);
		Notification.show("Claim approved").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		claimGrid.getDataProvider().refreshItem(claim);
	}
	private void denyClaim(Claim claim) {
		claimService.denyClaim(claim);
		Notification.show("Claim denied").addThemeVariants(NotificationVariant.LUMO_ERROR);
		claimGrid.getDataProvider().refreshItem(claim);
	}
}
