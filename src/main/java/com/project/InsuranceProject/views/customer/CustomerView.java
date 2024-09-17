package com.project.InsuranceProject.views.customer;
import com.project.InsuranceProject.data.entity.Policy;
import com.project.InsuranceProject.data.services.PolicyRetrieveService;
import com.project.InsuranceProject.security.Roles;
import com.project.InsuranceProject.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Route(value = "customer/policy", layout = MainLayout.class)
@RolesAllowed({Roles.ADMIN, Roles.CUSTOMER})
@CssImport("./themes/chat-theme/styles.css")
public class CustomerView extends VerticalLayout {

    private final Grid<Policy> policyGrid = new Grid<>(Policy.class);
    private final PolicyRetrieveService policyRetrieveService;

    @Autowired
    public CustomerView(PolicyRetrieveService policyRetrieveService) {
        this.policyRetrieveService = policyRetrieveService;
        customerpolicy();
    }
    private void customerpolicy(){
        H1 heading = new H1("Welcome to Customer Dashboard");

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        formLayout.add(heading);



        //policyGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        policyGrid.removeAllColumns();
        policyGrid.addColumn(Policy::getPolicy_id).setHeader("Policy ID");
        policyGrid.addColumn(Policy::getStart_date).setHeader("Start Date");
        policyGrid.addColumn(Policy::getEnd_date).setHeader("End Date");
        policyGrid.addColumn(Policy::getStatus).setHeader("Status");
        policyGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        formLayout.add(policyGrid);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        }

        List<Policy> policies = policyRetrieveService.getPolicyByUsername(username);
        policyGrid.setItems(policies);

        Button Create_policy = new Button("Create New Policy", e -> {
            UI.getCurrent().navigate(CreatePolicyView.class);
        });
        Create_policy.addClassNames("align-right");
        //CreatePolicyView createPolicy = new CreatePolicyView();
        //formLayout.add(createPolicy);
        add(formLayout,Create_policy);
    }

}

