package com.project.InsuranceProject.views.customer.claims;

import com.project.InsuranceProject.data.entity.Claim;
import com.project.InsuranceProject.data.entity.Policy;
import com.project.InsuranceProject.data.entity.Policy_risk;
import com.project.InsuranceProject.data.entity.Risk;
import com.project.InsuranceProject.data.services.ClaimService;
import com.project.InsuranceProject.data.services.PolicyRetrieveService;
import com.project.InsuranceProject.data.services.PolicyRiskService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.List;

public class ClaimForm extends Dialog {
    private final ClaimService claimService;
    private final PolicyRetrieveService policyRetrieveService;
    private final Runnable onSaveCallback;
    private final PolicyRiskService policyRiskService;

    private ComboBox<Policy> policyComboBox;
    private ComboBox<Policy_risk> riskComboBox;
    private TextField type;
    private DatePicker dateLoss;
    private NumberField amount;
    private TextField description;

    private Binder<Claim> binder;

    public ClaimForm(ClaimService claimService, PolicyRetrieveService policyRetrieveService, Runnable onSaveCallback, PolicyRiskService policyRiskService) {
        this.claimService = claimService;
        this.policyRetrieveService = policyRetrieveService;
        this.onSaveCallback = onSaveCallback;
        this.policyRiskService = policyRiskService;

        setCloseOnEsc(true);
        setCloseOnOutsideClick(false);

        createForm();
    }

    private void createForm() {
        policyComboBox = new ComboBox<>("Select Policy");
        riskComboBox = new ComboBox<>("Select Covered Risk");
        type = new TextField("Claim Type");
        dateLoss = new DatePicker("Date of Loss");
        amount = new NumberField("Amount");
        description = new TextField("Description");

        binder = new BeanValidationBinder<>(Claim.class);

        FormLayout formLayout = new FormLayout();
        formLayout.add(policyComboBox, riskComboBox, type, dateLoss, amount, description);

        binder.forField(policyComboBox).asRequired("Policy is required").bind(Claim::getPolicy, Claim::setPolicy);
        binder.forField(amount).asRequired("Amount is required")
                .bind(claim -> (double) claim.getAmount(), (claim, value) -> claim.setAmount(value.floatValue()));
        binder.bindInstanceFields(this);

        Button saveButton = new Button("Save", e -> validateAndSave());
        saveButton.getStyle().set("background-color", "green");
        saveButton.getStyle().set("color", "white");
        Button cancelButton = new Button("Cancel", e -> close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);


        add(formLayout, buttonLayout);

        setWidth("400px");
    }

    private void validateAndSave() {
        Claim claim = new Claim();
        if (binder.writeBeanIfValid(claim)) {
            claim.setDate(LocalDate.now());
            claim.setStatus("Pending"); // Set initial status
            claimService.saveClaim(claim);
            Notification.show("Claim submitted successfully");
            onSaveCallback.run(); // Trigger view update
            close();
        } else {
            Notification.show("Please correct the errors and try again.");
        }
    }

    @Override
    public void open() {
        binder.readBean(new Claim()); // Reset form with a new Claim object
        setupPolicyComboBox();
        setupRiskComboBox();
        dateLoss.setValue(null);
        amount.clear();
        description.clear();
        super.open();
    }

    private void setupPolicyComboBox() {
        String username = getCurrentUsername();
        if (username != null) {
            List<Policy> userPolicies = policyRetrieveService.getPolicyByUsername(username);
            policyComboBox.setItems(userPolicies);
            policyComboBox.setItemLabelGenerator(policy -> "Policy ID: " + policy.getPolicy_id());
        } else {
            Notification.show("Error: Unable to retrieve user information");
        }
    }

    private void setupRiskComboBox() {
        String username = getCurrentUsername();
        if (username != null) {
            //List<Policy> userPolicies = policyRetrieveService.getPolicyByUsername(username);
            List<Policy_risk> coveredRisks = policyRiskService.getPolicyRiskByUsername(username);
            System.out.println("part one done");
            riskComboBox.setItems(coveredRisks);
            System.out.println("part two done");
            //riskComboBox.setItemLabelGenerator(coveredRisk -> "Risk: " + coveredRisk.getRisk().getRisk());
            riskComboBox.setItemLabelGenerator(coveredRisk -> "Risk: " + coveredRisk.getSum_insured());
            System.out.println("part three done");
        } else {
            Notification.show("Error: Unable to retrieve user information");
        }
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }
}