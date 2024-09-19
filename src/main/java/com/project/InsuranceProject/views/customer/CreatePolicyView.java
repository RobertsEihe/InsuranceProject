package com.project.InsuranceProject.views.customer;

import com.project.InsuranceProject.data.entity.*;
import com.project.InsuranceProject.data.logic.PolicyLogic;
import com.project.InsuranceProject.data.services.*;
import com.project.InsuranceProject.security.Roles;
import com.project.InsuranceProject.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

@PageTitle("Create Policy")
@Route(value = "customer/createpolicy", layout = MainLayout.class)//someone forgot to add this
@RolesAllowed({Roles.ADMIN, Roles.CUSTOMER})
public class CreatePolicyView extends VerticalLayout {
    private ComboBox<String> agentComboBox;
    private ComboBox<String> insuranceTypeComboBox;
    private final AgentRetrieveService agentRetrieveService;

    private final VehicleService vehicleService;
    private final PolicyRetrieveService policyService;
    private final PolicyRiskService policyRiskService;
    private final UserService userService;
    private final RiskRetrieveService riskRetrieveService;
    private final PolicyLogic policyLogic;
    private Button confirmPolicyButton;
    Policy policy = new Policy();

    @Autowired
    public CreatePolicyView(AgentRetrieveService agentRetrieveService, VehicleService vehicleService, PolicyRetrieveService policyService, PolicyRiskService policyRiskService, UserService userService, RiskRetrieveService riskRetrieveService, PolicyLogic policyLogic) {
        this.agentRetrieveService = agentRetrieveService;
        this.vehicleService = vehicleService;
        this.policyService = policyService;
        this.policyRiskService = policyRiskService;
        this.userService = userService;
        this.riskRetrieveService = riskRetrieveService;
        this.policyLogic = policyLogic;
        createPolicyView();
    }

    private void createPolicyView() {
        H1 heading = new H1("Policy Creation Form");

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setDefaultHorizontalComponentAlignment(Alignment.AUTO);

        formLayout.add(heading);

         confirmPolicyButton = new Button("Confirm policy purchase", e -> {
            // When button is clicked, add the agent and insurance type fields
            policyLogic.confirmPolicyPurchase(policy, policy.getUsers());
            showSuccess("Policy purchased.");
        });
        confirmPolicyButton.getStyle().set("background-color", "green");
        confirmPolicyButton.getStyle().set("color", "white");

        Button submitButton = new Button("Proceed", e -> {
            // When button is clicked, add the agent and insurance type fields
        });
        submitButton.getStyle().set("background-color", "green");
        submitButton.getStyle().set("color", "white");

        showAgentAndInsuranceFields(formLayout);
        formLayout.add(submitButton);

        // Add the form layout to the main layout (VerticalLayout)
        add(formLayout);
        submitButton.addClickListener(e -> {

            confirmPolicyButton.setVisible(false);
            String insuranceType = insuranceTypeComboBox.getValue();
            if (isFormValid(agentComboBox, insuranceTypeComboBox)) {
                // Process form data
                if (insuranceType.equals("Vehicle")) {
                    createVehicleForm(formLayout);
                    //datepick(formLayout);
                    //confirmPolicyButton.setVisible(true);
                } else if (insuranceType.equals("House")) {
                    createHouseForm(formLayout);
                    datepick(formLayout);
                    confirmPolicyButton.setVisible(true);
                } else if (insuranceType.equals("Health")) {
                    createHealthForm(formLayout);
                    datepick(formLayout);
                    confirmPolicyButton.setVisible(true);
                }
                formLayout.add(confirmPolicyButton);

                showSuccess("Form submitted successfully.");
                agentComboBox.setReadOnly(true);
                insuranceTypeComboBox.setReadOnly(true);
                submitButton.setVisible(false);
            } else {
                Notification notification = Notification.show("Please fill out all required fields.");
                notification.setDuration(3000);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        confirmPolicyButton.setDisableOnClick(true);
        confirmPolicyButton.addClickListener(e -> {
            // or here I should put policy Purchased. ??
        });
    }

    private void showAgentAndInsuranceFields(VerticalLayout formLayout) {
        // Initialize the ComboBox for selecting an agent

        agentComboBox = new ComboBox<>("Select Agent");
        List<String> agentNames = agentRetrieveService.getAllAgentNames();
        agentComboBox.setItems(agentNames);
        agentComboBox.setPlaceholder("List of Agent");
        agentComboBox.setRequiredIndicatorVisible(true);
        agentComboBox.setWidth("400px");
        agentComboBox.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                agentComboBox.setInvalid(true);
                agentComboBox.setErrorMessage("Agent selection is required");
            } else {
                agentComboBox.setInvalid(false);
            }
        });
        // Initialize the ComboBox for selecting insurance type
        insuranceTypeComboBox = new ComboBox<>("Add Insurance Type");
        insuranceTypeComboBox.setItems("Vehicle", "House", "Health");
        insuranceTypeComboBox.setPlaceholder("List of Insurance Type");
        insuranceTypeComboBox.setRequiredIndicatorVisible(true);
        insuranceTypeComboBox.setWidth("400px");
        insuranceTypeComboBox.addValueChangeListener(e -> {
            if (e.getValue() == null) {
                insuranceTypeComboBox.setInvalid(true);
                insuranceTypeComboBox.setErrorMessage("Insurance Type selection is required");
            } else {
                insuranceTypeComboBox.setInvalid(false);
            }
        });

        // Add the agent and insurance type fields to the layout
        formLayout.add(agentComboBox, insuranceTypeComboBox);
    }
    @Transactional
    protected void createVehicleForm(VerticalLayout formLayout) {
        Button submitButton = new Button("Calculate premium");
        submitButton.getStyle().set("background-color", "green");
        submitButton.getStyle().set("color", "white");

        Button cancelButton = new Button("Cancel");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        //submitButton.setVisible(false);
        H3 heading = new H3("Fill out the fields below with the Required Vehicle Details :");

        Accordion accordion = new Accordion();

        TextField licenseNumberField = new TextField("Driver's License Number");
        licenseNumberField.setRequiredIndicatorVisible(true);
        licenseNumberField.setRequired(true);
        licenseNumberField.setWidth("100%");
        licenseNumberField.setErrorMessage("Driver's license number cannot be empty.");

        DatePicker issueDateField = new DatePicker("Issue Date");
        issueDateField.setRequiredIndicatorVisible(true);
        issueDateField.setRequired(true);
        issueDateField.setWidth("100%");
        issueDateField.setErrorMessage("Issue date is required.");

        DatePicker expireDateField = new DatePicker("Expire Date");
        expireDateField.setRequiredIndicatorVisible(true);
        expireDateField.setRequired(true);
        expireDateField.setWidth("100%");
        expireDateField.setErrorMessage("Expire date is required.");

        VerticalLayout personalInformationLayout = new VerticalLayout(licenseNumberField, issueDateField, expireDateField);
        personalInformationLayout.setSpacing(false);
        personalInformationLayout.setPadding(false);

        AccordionPanel personalInfoPanel = accordion.add("Driving License Details", personalInformationLayout);
        personalInfoPanel.setWidth("1000px");
        personalInfoPanel.addThemeVariants(DetailsVariant.REVERSE);
        personalInfoPanel.addThemeVariants(DetailsVariant.FILLED);

// Vehicle Details Fields
        TextField carMakeField = new TextField("Car Make");
        carMakeField.setRequiredIndicatorVisible(true);
        carMakeField.setRequired(true);
        carMakeField.setWidth("100%");
        carMakeField.setErrorMessage("Car make cannot be empty.");

        TextField carModelField = new TextField("Car Model");
        carModelField.setRequiredIndicatorVisible(true);
        carModelField.setRequired(true);
        carModelField.setWidth("100%");
        carModelField.setErrorMessage("Car model cannot be empty.");

        TextField carYearField = new TextField("Year");
        carYearField.setRequiredIndicatorVisible(true);
        carYearField.setRequired(true);
        carYearField.setWidth("100%");
        carYearField.setErrorMessage("Car year cannot be empty.");

        TextField carOdometerField = new TextField("Odometer Reading");
        carOdometerField.setRequiredIndicatorVisible(true);
        carOdometerField.setRequired(true);
        carOdometerField.setWidth("100%");
        carOdometerField.setErrorMessage("Odometer reading cannot be empty.");

        TextField marketValueField = new TextField("Current Market Value");
        marketValueField.setRequiredIndicatorVisible(true);
        marketValueField.setRequired(true);
        marketValueField.setWidth("100%");
        marketValueField.setErrorMessage("Market value cannot be empty.");

        VerticalLayout vehicleDetailsLayout = new VerticalLayout(carMakeField, carModelField, carYearField, carOdometerField, marketValueField);
        vehicleDetailsLayout.setSpacing(false);
        vehicleDetailsLayout.setPadding(false);

        AccordionPanel vehicleDetailsPanel = accordion.add("Car Details", vehicleDetailsLayout);
        vehicleDetailsPanel.setWidth("1000px");
        vehicleDetailsPanel.addThemeVariants(DetailsVariant.REVERSE);
        vehicleDetailsPanel.addThemeVariants(DetailsVariant.FILLED);

// Risk Coverage and Duration Fields
        CheckboxGroup<String> riskCheckboxGroup = new CheckboxGroup<>();
        riskCheckboxGroup.setLabel("Select Risks to Cover");
        riskCheckboxGroup.setItems("Theft", "Crash", "Flood", "Vandalism");
        riskCheckboxGroup.setRequiredIndicatorVisible(true);
        riskCheckboxGroup.setRequired(true);
        riskCheckboxGroup.setWidth("100%");
        riskCheckboxGroup.setErrorMessage("At least one risk must be selected.");

        DatePicker startDateField = new DatePicker("Start Date");
        startDateField.setMin(LocalDate.now());
        startDateField.setRequiredIndicatorVisible(true);
        startDateField.setRequired(true);
        startDateField.setWidth("100%");
        startDateField.setErrorMessage("Start date is required.");

        ComboBox<Integer> durationComboBox = new ComboBox<>("Duration (months)");
        durationComboBox.setItems(1, 3, 6, 12);
        durationComboBox.setValue(1);
        durationComboBox.setRequiredIndicatorVisible(true);
        durationComboBox.setRequired(true);
        durationComboBox.setWidth("100%");
        durationComboBox.setErrorMessage("Duration must be selected.");

        VerticalLayout riskAndDurationLayout = new VerticalLayout(riskCheckboxGroup, startDateField, durationComboBox);
        riskAndDurationLayout.setSpacing(false);
        riskAndDurationLayout.setPadding(false);

        AccordionPanel riskAndDurationPanel = accordion.add("Risks to cover and Duration", riskAndDurationLayout);
        riskAndDurationPanel.setWidth("1000px");
        riskAndDurationPanel.addThemeVariants(DetailsVariant.REVERSE);
        riskAndDurationPanel.addThemeVariants(DetailsVariant.FILLED);

        Div separator = new Div();
        separator.setHeight("2px");
        separator.setWidth("100%");
        separator.getStyle().set("background-color", "black");
        /*TextField licenseNumberField = new TextField("Driver's License Number");
        DatePicker issueDateField = new DatePicker("Issue Date");
        DatePicker expireDateField = new DatePicker("Expire Date");

        TextField carMakeField = new TextField("Car Make");
        TextField carModelField = new TextField("Car Model");
        TextField carYearField = new TextField("Year");
        TextField carOdometerField = new TextField("Odometer Reading");
        TextField marketValueField = new TextField("Current Market Value");

        CheckboxGroup<String> riskCheckboxGroup = new CheckboxGroup<>();
        riskCheckboxGroup.setLabel("Select Risks to Cover");
        riskCheckboxGroup.setItems("Theft", "Crash", "Flood", "Vandalism");

        DatePicker startDateField = new DatePicker("Start Date");
        startDateField.setMin(LocalDate.now());

        ComboBox<Integer> durationComboBox = new ComboBox<>("Duration (months)");
        durationComboBox.setItems(1, 3, 6, 12);
        durationComboBox.setValue(1);*/



        Span premiumLabel = new Span();

        submitButton.addClickListener(e -> {

            // moved to class scope
            //Policy policy = new Policy();
            boolean isValid = true;


            if (licenseNumberField.isEmpty() || issueDateField.getValue() == null || expireDateField.getValue() == null ||
                    carMakeField.isEmpty() || carModelField.isEmpty() || carYearField.isEmpty() ||
                    carOdometerField.isEmpty() || marketValueField.isEmpty() ||
                    riskCheckboxGroup.getSelectedItems().isEmpty() || startDateField.getValue() == null ||
                    durationComboBox.getValue() == null){
                       isValid = false;

                   }
            LocalDate issueDate = issueDateField.getValue();
            LocalDate expireDate = expireDateField.getValue();
            LocalDate startDate = startDateField.getValue();
            // Check if the issue date is before the expire date
            if (issueDate != null && expireDate != null && issueDate.isAfter(expireDate)) {
                Notification notification = Notification.show(" Driving License Expire date must be after issue date.");
                notification.setDuration(3000);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                isValid = false;

            }

            // Ensure the policy's start date is not before the driving license issue date
            if (startDate != null && issueDate != null && startDate.isBefore(issueDate)) {
                Notification notification = Notification.show("Policy start date cannot be before the driving license issue date.");
                notification.setDuration(3000);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                isValid = false;

            }

            // Ensure the policy's end date is before the driving license expire date
            if (expireDate != null && startDate != null && expireDate.isBefore(startDate.plusMonths(durationComboBox.getValue()))) {
                Notification notification = Notification.show("Driver's license expiry date must be after the policy end date.");
                notification.setDuration(3000);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                isValid = false;

            }


            if(isValid) {

                policy.setDuration(durationComboBox.getValue());
                policy.setEnd_date(startDateField.getValue().plusMonths(durationComboBox.getValue()));
                policy.setStart_date(startDateField.getValue());
                policy.setSum_insured(Double.parseDouble(marketValueField.getValue()));
                policy.setStatus("Q");
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String username = null;
                if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                    username = ((UserDetails) authentication.getPrincipal()).getUsername();
                }
                Optional<Users> user = userService.getUserByUsername(username);
                policy.setUsers(user.get());
                Optional<Users> agent = userService.getUserByUsername(agentComboBox.getValue());
                policy.setAgent_id(agent.get().getId());
                policyService.savePolicy(policy);


                Vehicle vehicle = new Vehicle();
                vehicle.setMake(carMakeField.getValue());
                vehicle.setModel(carModelField.getValue());
                vehicle.setYear(Integer.parseInt(carYearField.getValue()));
                vehicle.setOdd(Integer.parseInt(carOdometerField.getValue()));
                vehicle.setCurrent_value(Float.parseFloat(marketValueField.getValue()));
                vehicleService.saveVehicle(vehicle);

                Set<String> selectedRisks = riskCheckboxGroup.getValue();
                for (String riskName : selectedRisks) {
                    Optional<Risk> risk = riskRetrieveService.findByNameAndType(riskName, insuranceTypeComboBox.getValue());
                    if (risk.isPresent()) {
                        Policy_risk policyRisk = new Policy_risk();
                        policyRisk.setPolicy(policy);
                        policyRisk.setRisk(risk.get());
                        // Save each Policy_risk entry
                        policyRisk.setVehicle(vehicle);
                        policyRiskService.savePolicyRisk(policyRisk);

                    }
                }

                policyLogic.calculatePolicyPremium(policy, policy.getUsers());
                showSuccess("Data submitted");

                if (policy != null) {
                    double premium = policy.getTotalPremium();

                    if (policy.getDuration() == 1) {
                        premiumLabel.setText("Total premium: €" + String.format("%.2f", premium));
                    } else {
                        premiumLabel.setText("Total premium: €" + String.format("%.2f", premium) + " (monthly: €" +
                                String.format("%.2f", premium / policy.getDuration()) + ")");
                    }
                    //Notification.show("Premium Calculated: $" + premium);
                } else {
                    Notification.show("Please submit the policy first.");
                }
                confirmPolicyButton.setVisible(true);
                submitButton.setVisible(false);
                cancelButton.setVisible(false);
                licenseNumberField.setReadOnly(true);
                issueDateField.setReadOnly(true);
                expireDateField.setReadOnly(true);
                carMakeField.setReadOnly(true);
                carModelField.setReadOnly(true);
                carYearField.setReadOnly(true);
                carOdometerField.setReadOnly(true);
                marketValueField.setReadOnly(true);
                riskCheckboxGroup.setReadOnly(true);
                startDateField.setReadOnly(true);
                durationComboBox.setReadOnly(true);

            }
            else {
                Notification notification = Notification.show("Please fill in all required fields.");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setDuration(3000);
            }
        });

        cancelButton.addClickListener(e -> {
            UI.getCurrent().getPage().reload();
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout(submitButton, cancelButton);
        formLayout.add(separator,heading,accordion,horizontalLayout,premiumLabel);

    }

    private void createHouseForm(VerticalLayout formLayout) {
        TextField addressField = new TextField("Address");
        ComboBox<String> typeComboBox = new ComboBox<>("Type");
        typeComboBox.setItems("Apartment", "House");

        TextField yearBuiltField = new TextField("Year Built");
        TextField areaField = new TextField("Area (in sq meters)");
        ComboBox<String> materialTypeComboBox = new ComboBox<>("Material Type");
        materialTypeComboBox.setItems("Stone", "Wood");

        CheckboxGroup<String> riskCheckboxGroup = new CheckboxGroup<>();
        riskCheckboxGroup.setLabel("Select Risks to Cover");
        riskCheckboxGroup.setItems("Fire", "Flood", "Wind", "Vandalism", "Damage During Construction");

        formLayout.add(addressField, typeComboBox, yearBuiltField, areaField, materialTypeComboBox, riskCheckboxGroup);
    }

    private void createHealthForm(VerticalLayout formLayout) {
        CheckboxGroup<String> riskCheckboxGroup = new CheckboxGroup<>();
        riskCheckboxGroup.setLabel("Select Risks to Cover");
        riskCheckboxGroup.setItems("Hospitalization", "Prescription Drugs", "Over-the-Counter (OTC) Drugs", "Sports Club Subscription", "Prescription Glasses");

        formLayout.add(riskCheckboxGroup);
    }

    private void datepick(VerticalLayout formLayout) {

        DatePicker startDateField = new DatePicker("Start Date");
        startDateField.setMin(LocalDate.now());

        ComboBox<Integer> durationComboBox = new ComboBox<>("Duration (months)");
        durationComboBox.setItems(1, 3, 6, 12);
        durationComboBox.setValue(1);

        formLayout.add(startDateField, durationComboBox);
    }

    private boolean isFormValid(ComboBox<String> agentComboBox, ComboBox<String> insuranceTypeComboBox) {
        boolean isValid = true;


        if (agentComboBox.isEmpty()) {
            agentComboBox.setInvalid(true);
            isValid = false;
        } else {
            agentComboBox.setInvalid(false);
        }

        if (insuranceTypeComboBox.isEmpty()) {
            insuranceTypeComboBox.setInvalid(true);
            isValid = false;
        } else {
            insuranceTypeComboBox.setInvalid(false);
        }
        return isValid;
    }

    private void showSuccess(String messageText) {
       Notification notification = Notification.show(messageText);
        notification.setDuration(2000);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}
