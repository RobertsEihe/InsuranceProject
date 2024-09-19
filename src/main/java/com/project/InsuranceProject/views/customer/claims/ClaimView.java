package com.project.InsuranceProject.views.customer.claims;

import com.project.InsuranceProject.data.entity.Claim;
import com.project.InsuranceProject.data.services.ClaimService;
import com.project.InsuranceProject.data.services.PolicyRetrieveService;
import com.project.InsuranceProject.data.services.PolicyRiskService;
import com.project.InsuranceProject.security.Roles;
import com.project.InsuranceProject.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Route(value = "customer/claims", layout = MainLayout.class)
@RolesAllowed({Roles.CUSTOMER, Roles.ADMIN})
@PageTitle("My Claims")
public class ClaimView extends VerticalLayout {

    private final Grid<Claim> grid = new Grid<>(Claim.class);
    private final ClaimService claimService;
    private final PolicyRetrieveService policyRetrieveService;
    private final ClaimForm claimForm;
    private final PolicyRiskService policyRiskService;

    public ClaimView(ClaimService claimService, PolicyRetrieveService policyRetrieveService, PolicyRiskService policyRiskService) {
        this.claimService = claimService;
        this.policyRetrieveService = policyRetrieveService;
        this.policyRiskService = policyRiskService;
        this.claimForm = new ClaimForm(claimService, policyRetrieveService, this::updateList, policyRiskService);

        addClassName("claim-view");
        setSizeFull();
        configureGrid();

        Button addClaimButton = new Button("Add New Claim", e -> claimForm.open());

        HorizontalLayout toolbar = new HorizontalLayout(addClaimButton);

        add(toolbar, grid);

        updateList();
    }

    private void configureGrid() {
        grid.addClassNames("claim-grid");
        grid.setSizeFull();
        grid.setColumns("claim_id", "type", "date", "amount", "status");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void updateList() {
        grid.setItems(claimService.getClaimsByUsername(getCurrentUsername()));
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }
}