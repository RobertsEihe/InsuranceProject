package com.project.InsuranceProject.views.customer.claims;

import com.project.InsuranceProject.data.entity.Claim;
import com.project.InsuranceProject.data.entity.Policy;
import com.project.InsuranceProject.data.entity.Policy_risk;
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
        riskComboBox.setEnabled(false);
        dateLoss = new DatePicker("Date of Loss");
        amount = new NumberField("Amount");
        description = new TextField("Description");

        binder = new BeanValidationBinder<>(Claim.class);

        FormLayout formLayout = new FormLayout();
        formLayout.add(policyComboBox, riskComboBox, dateLoss, amount, description);

        binder.forField(policyComboBox).asRequired("Policy is required").bind(Claim::getPolicy, Claim::setPolicy);
        // Remove the binding for riskComboBox from the binder
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

        policyComboBox.addValueChangeListener(event -> {
            Policy selectedPolicy = event.getValue();
            if (selectedPolicy != null) {
                riskComboBox.setEnabled(true);
                setupRiskComboBox(selectedPolicy);
            } else {
                riskComboBox.clear();
                riskComboBox.setEnabled(false);
            }
        });
    }

    private void validateAndSave() {
        Claim claim = new Claim();
        if (binder.writeBeanIfValid(claim)) {
            Policy_risk selectedRisk = riskComboBox.getValue();
            if (selectedRisk != null) {
                claim.setType(selectedRisk.getRisk().getRisk());
            }
            claim.setDate(LocalDate.now());
            claim.setStatus("Pending");
            claimService.saveClaim(claim);
            Notification.show("Claim submitted successfully");
            onSaveCallback.run();
            close();
        } else {
            Notification.show("Please correct the errors and try again.");
        }
    }

    @Override
    public void open() {
        binder.readBean(new Claim());
        setupPolicyComboBox();
        dateLoss.setValue(null);
        amount.clear();
        description.clear();
        riskComboBox.clear();
        riskComboBox.setEnabled(false);
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

    private void setupRiskComboBox(Policy policy) {
        String username = getCurrentUsername();
        if (username != null) {
            List<Policy_risk> coveredRisks = policyRiskService.findByPolicyID(policy.getPolicy_id());
            riskComboBox.setItems(coveredRisks);
            riskComboBox.setItemLabelGenerator(coveredRisk -> coveredRisk.getRisk().getRisk());
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

/*
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
        riskComboBox.setEnabled(false);
        dateLoss = new DatePicker("Date of Loss");
        amount = new NumberField("Amount");
        description = new TextField("Description");

        binder = new BeanValidationBinder<>(Claim.class);

        FormLayout formLayout = new FormLayout();
        formLayout.add(policyComboBox, riskComboBox, dateLoss, amount, description);

        binder.forField(policyComboBox).asRequired("Policy is required").bind(Claim::getPolicy, Claim::setPolicy);
//        binder.forField(riskComboBox).asRequired("Policy is required").bind(Claim::getType, Claim::setType);
//        binder.forField(riskComboBox).bind(
//                claim -> claim.getType(),  // getter
//                (claim, value) -> claim.setType(value)  // setter
//        );


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

        policyComboBox.addValueChangeListener(event -> {
            Policy selectedPolicy = event.getValue();
            if (selectedPolicy != null) {
                riskComboBox.setEnabled(true);
                setupRiskComboBox(selectedPolicy);
            } else {
                riskComboBox.clear();
                riskComboBox.setEnabled(false);
            }
        });
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

    private void setupRiskComboBox(Policy policy) {
        String username = getCurrentUsername();
        if (username != null) {
            List<Policy_risk> coveredRisks = policyRiskService.findByPolicyID(policy.getPolicy_id());
            riskComboBox.setItems(coveredRisks);
            riskComboBox.setItemLabelGenerator(coveredRisk -> coveredRisk.getRisk().getRisk());
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

*/