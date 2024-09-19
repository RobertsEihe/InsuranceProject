package com.project.InsuranceProject.views.customer;
import com.project.InsuranceProject.data.entity.Policy;
import com.project.InsuranceProject.data.services.pdf.PDFService;
import com.project.InsuranceProject.data.services.PolicyRetrieveService;
import com.project.InsuranceProject.data.services.pdf.SavePDF;
import com.project.InsuranceProject.security.Roles;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.router.PageTitle;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.List;

@PageTitle("My Policy")
@Route(value = "customer/policy", layout = MainLayout.class)
@RolesAllowed({Roles.ADMIN, Roles.CUSTOMER})
@CssImport("./themes/chat-theme/styles.css")
public class CustomerView extends VerticalLayout {

    private final Grid<Policy> policyGrid = new Grid<>(Policy.class);
    private final PolicyRetrieveService policyRetrieveService;
    private final SavePDF savePDF;
    private final PDFService pdfService;

    @Autowired
    public CustomerView(PolicyRetrieveService policyRetrieveService, SavePDF savePDF, PDFService pdfService) {
        this.policyRetrieveService = policyRetrieveService;
        this.savePDF = savePDF;
        this.pdfService = pdfService;
        customerpolicy();
    }
    private void customerpolicy(){
        H1 heading = new H1("Customer Dashboard");
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        formLayout.add(heading);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        }

        VerticalLayout verticalLayout = new VerticalLayout();

        List<Policy> policies = policyRetrieveService.getPolicyByUsername(username);
        policyGrid.setItems(policies);
        //policyGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        policyGrid.removeAllColumns();
        policyGrid.addColumn(Policy::getPolicy_id).setHeader("Policy ID").setSortable(true).setAutoWidth(true);;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        policyGrid.addColumn(policy -> policy.getStart_date().format(formatter)).setHeader("Start Date").setAutoWidth(true);
        policyGrid.addColumn(policy -> policy.getEnd_date().format(formatter)).setHeader("End Date").setAutoWidth(true);
        policyGrid.addColumn(policy -> String.format("€ %.2f", policy.getSum_insured())).setHeader("Sum Insured").setAutoWidth(true);
        policyGrid.addColumn(policy -> String.format("€ %.2f", policy.getTotalPremium())).setHeader("Total Premium").setAutoWidth(true);
        policyGrid.addColumn(policy -> {
            List<String> riskTypes = policyRetrieveService.getRiskTypesByPolicyId(policy.getPolicy_id());
            return String.join(", ", riskTypes);
        }).setHeader("Risk Types").setAutoWidth(true);
        policyGrid.addColumn(Policy::getStatus).setHeader("Status");
        policyGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        String finalUsername = username;
        policyGrid.addComponentColumn(policy -> {

            if ("Q".equals(policy.getStatus())) {
                Button approveButton = new Button("Confirm Purchase", click -> confirmPolicy(policy, finalUsername));
                approveButton.getStyle().set("background-color", "green");
                approveButton.getStyle().set("color", "white");
                HorizontalLayout actionButtons = new HorizontalLayout(approveButton);
                return actionButtons;
            } else if("P".equals(policy.getStatus())) {
                Button approveButton = new Button("get PDF", click -> downloadPolicyPDF(policy));
                approveButton.getStyle().set("background-color", "white");
                approveButton.getStyle().set("color", "green");
                HorizontalLayout actionButtons = new HorizontalLayout(approveButton);
                return actionButtons;
            } else {
                return new HorizontalLayout();
            }
        }).setAutoWidth(true);
        Button Create_policy = new Button("Create New Policy", e -> {
            UI.getCurrent().navigate(CreatePolicyView.class);
        });
        Create_policy.getStyle().set("background-color", "green");
        Create_policy.getStyle().set("color", "white");

        verticalLayout.add(Create_policy,policyGrid);
        add(formLayout,verticalLayout);
    }

    private void confirmPolicy(Policy policy, String username) {
        policyRetrieveService.confirmPolicy(policy);
        Notification.show("Policy approved").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        updatePolicyList(username);
    }
    private void updatePolicyList(String username) {
        List<Policy> policies = policyRetrieveService.getPolicyByUsername(username);  // Fetch all policies
        policyGrid.setItems(policies);
    }
    private void downloadPolicyPDF(Policy policy){
        savePDF.savePDFtoFile(policy);
        Notification.show("File downloaded").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}

