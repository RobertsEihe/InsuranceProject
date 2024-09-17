package com.project.InsuranceProject.views.customer;

import com.project.InsuranceProject.data.entity.*;
import com.project.InsuranceProject.data.services.*;
import com.project.InsuranceProject.security.Roles;
import com.project.InsuranceProject.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.H1;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDate;
import java.util.*;


@Route(value = "customer/createpolicy", layout = MainLayout.class)//someone for got to add this
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

    @Autowired
    public CreatePolicyView(AgentRetrieveService agentRetrieveService, VehicleService vehicleService, PolicyRetrieveService policyService, PolicyRiskService policyRiskService, UserService userService, RiskRetrieveService riskRetrieveService) {
        this.agentRetrieveService = agentRetrieveService;
        this.vehicleService = vehicleService;
        this.policyService = policyService;
        this.policyRiskService = policyRiskService;
        this.userService = userService;
        this.riskRetrieveService = riskRetrieveService;
        createPolicyView();
    }

    private void createPolicyView() {
        H1 heading = new H1("To create a New Policy....Press the button below");

        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        formLayout.add(heading);

        Button PremiumCalc = new Button("Calculate Premium", e -> {
            // When button is clicked, add the agent and insurance type fields
           Notification.show("Details Submitted");
        });

        Button submitButton = new Button("Proceed", e -> {
            // When button is clicked, add the agent and insurance type fields
            agentComboBox.setReadOnly(true);
            insuranceTypeComboBox.setReadOnly(true);
        });

        Button createPolicyButton = new Button("Create Policy", e -> {
            // When button is clicked, add the agent and insurance type fields
            showAgentAndInsuranceFields(formLayout);
            formLayout.add(submitButton);

        });

        createPolicyButton.setDisableOnClick(true);
        // Add the Create Policy button to the layout
        formLayout.add(createPolicyButton);
        // Add the form layout to the main layout (VerticalLayout)
        add(formLayout);
        submitButton.addClickListener(e -> {
            submitButton.setVisible(false);
            createPolicyButton.setVisible(false);
            PremiumCalc.setVisible(false);
            String insuranceType = insuranceTypeComboBox.getValue();
            if (isFormValid(agentComboBox, insuranceTypeComboBox)) {
                // Process form data
                if (insuranceType.equals("Vehicle")) {
                    createVehicleForm(formLayout);
                    //datepick(formLayout);
                    PremiumCalc.setVisible(true);
                } else if (insuranceType.equals("House")) {
                    createHouseForm(formLayout);
                    datepick(formLayout);
                    PremiumCalc.setVisible(true);
                } else if (insuranceType.equals("Health")) {
                    createHealthForm(formLayout);
                    datepick(formLayout);
                    PremiumCalc.setVisible(true);
                }
                formLayout.add(PremiumCalc);

                Notification.show("Form submitted successfully.");
            } else {
                Notification.show("Please fill out all required fields.", 3000, Notification.Position.MIDDLE);
            }
        });
        PremiumCalc.setDisableOnClick(true);
        PremiumCalc.addClickListener(e -> {

        });
    }

    private void showAgentAndInsuranceFields(VerticalLayout formLayout) {
        // Initialize the ComboBox for selecting an agent

        agentComboBox = new ComboBox<>("Select Agent");
        List<String> agentNames = agentRetrieveService.getAllAgentNames();
        agentComboBox.setItems(agentNames);
        agentComboBox.setRequiredIndicatorVisible(true);
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
        insuranceTypeComboBox.setRequiredIndicatorVisible(true);
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
        TextField licenseNumberField = new TextField("Driver's License Number");
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
        durationComboBox.setValue(1);


        Button submitButton = new Button("Submit", event -> {

            Policy policy = new Policy();
            policy.setDuration(durationComboBox.getValue());
            policy.setEnd_date(startDateField.getValue().plusMonths(durationComboBox.getValue()));
            policy.setStart_date(startDateField.getValue());
            policy.setSum_insured(Double.parseDouble(marketValueField.getValue()));
            policy.setStatus("NEW");
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
                Optional<Risk> risk = riskRetrieveService.findByNameAndType(riskName,insuranceTypeComboBox.getValue());
                if (risk.isPresent()) {
                    Policy_risk policyRisk = new Policy_risk();
                    policyRisk.setPolicy(policy);
                    policyRisk.setRisk(risk.get());
                    // Save each Policy_risk entry
                    policyRisk.setVehicle(vehicle);
                    policyRiskService.savePolicyRisk(policyRisk);

                }
            }


        });



        formLayout.add(licenseNumberField, issueDateField, expireDateField, carMakeField, carModelField, carYearField,
                carOdometerField, marketValueField, riskCheckboxGroup,startDateField, durationComboBox, submitButton);

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
}
