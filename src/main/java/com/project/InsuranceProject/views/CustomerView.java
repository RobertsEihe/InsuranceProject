package com.project.InsuranceProject.views;
import com.project.InsuranceProject.security.Roles;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

@PermitAll
@Route("Customerpolicy")
@RolesAllowed(Roles.CUSTOMER)
public class CustomerView extends VerticalLayout {

    private CustomerView(){
        customerpolicy();
    }

    private void customerpolicy(){
        H1 heading = new H1("Welcome to Customer Dashboard");

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        formLayout.add(heading);

        CreatePolicyView createPolicy = new CreatePolicyView();

        formLayout.add(createPolicy);
        add(formLayout);
    }

}
