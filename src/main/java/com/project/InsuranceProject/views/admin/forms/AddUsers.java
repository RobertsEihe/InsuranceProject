package com.project.InsuranceProject.views.admin.forms;

import com.project.InsuranceProject.data.entity.Agent;
import com.project.InsuranceProject.data.entity.Customer;
import com.project.InsuranceProject.data.services.UserService;
import com.project.InsuranceProject.data.services.AgentService;
import com.project.InsuranceProject.views.admin.AdminLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

@PermitAll
@PageTitle("Add New User")
@Route(value = "admin/add-user", layout = AdminLayout.class)
public class AddUsers extends VerticalLayout {

    private final UserService userService;
    private final AgentService agentService;
    private final TransactionTemplate transactionTemplate;
    private ComboBox<String> userTypeSelect;
    private FormLayout form;
    private Binder<Customer> customerBinder;
    private Binder<Agent> agentBinder;

    @Autowired
    public AddUsers(UserService userService, AgentService agentService, TransactionTemplate transactionTemplate) {
        this.userService = userService;
        this.agentService = agentService;
        this.transactionTemplate = transactionTemplate;
        setSizeFull();

        H2 header = new H2("Add New User");
        userTypeSelect = new ComboBox<>("Select user type");
        userTypeSelect.setItems("Customer", "Agent");
        userTypeSelect.addValueChangeListener(event -> {
            form.removeAll();
            createForm(event.getValue());
        });

        form = new FormLayout();
        customerBinder = new Binder<>(Customer.class);
        agentBinder = new Binder<>(Agent.class);

        Button saveButton = new Button("Save", event -> saveUser());

        add(header, userTypeSelect, form, saveButton);
    }

    private void createForm(String userType) {
        if ("Customer".equals(userType)) {
            createCustomerForm();
        } else if ("Agent".equals(userType)) {
            createAgentForm();
        }
    }

    private void createCustomerForm() {
        Customer customer = new Customer();

        TextField name = new TextField("Name");
        EmailField email = new EmailField("Email");
        TextField phone = new TextField("Phone");
        TextField address = new TextField("Address");
        DatePicker dateOfBirth = new DatePicker("Date of Birth");
        IntegerField loyalty = new IntegerField("Loyalty");
        TextField dlNum = new TextField("Driver's License Number");
        DatePicker dlIssueDate = new DatePicker("DL Issue Date");
        DatePicker dlExpireDate = new DatePicker("DL Expire Date");
        TextField bankAccount = new TextField("Bank Account");

        form.add(name, email, phone, address, dateOfBirth, loyalty, dlNum, dlIssueDate, dlExpireDate, bankAccount);

        customerBinder = new Binder<>(Customer.class);
        customerBinder.forField(name).asRequired("Name is required").bind(Customer::getName, Customer::setName);
        customerBinder.forField(email).asRequired("Email is required").bind(Customer::getEmail, Customer::setEmail);
        customerBinder.forField(phone).asRequired("Phone is required").bind(Customer::getPhone, Customer::setPhone);
        customerBinder.forField(address).asRequired("Address is required").bind(Customer::getAddress, Customer::setAddress);
        customerBinder.forField(dateOfBirth).asRequired("Date of Birth is required").bind(Customer::getDate_of_birth, Customer::setDate_of_birth);
        customerBinder.forField(loyalty).asRequired("Loyalty is required").bind(Customer::getLoyalty, Customer::setLoyalty);
        customerBinder.forField(dlNum).asRequired("Driver's License Number is required").bind(Customer::getDl_num, Customer::setDl_num);
        customerBinder.forField(dlIssueDate).asRequired("DL Issue Date is required").bind(Customer::getDl_issue_date, Customer::setDl_issue_date);
        customerBinder.forField(dlExpireDate).asRequired("DL Expire Date is required").bind(Customer::getDl_expire_Date, Customer::setDl_expire_Date);
        customerBinder.forField(bankAccount).asRequired("Bank Account is required").bind(Customer::getBank_account, Customer::setBank_account);

        customerBinder.readBean(customer);
    }

    private void createAgentForm() {
        Agent agent = new Agent();

        TextField name = new TextField("Name");
        DatePicker dateOfBirth = new DatePicker("Date of Birth");

        form.add(name, dateOfBirth);

        agentBinder = new Binder<>(Agent.class);
        agentBinder.forField(name).asRequired("Name is required").bind(Agent::getName, Agent::setName);
        agentBinder.forField(dateOfBirth).asRequired("Date of Birth is required").bind(Agent::getDate_of_birth, Agent::setDate_of_birth);

        agentBinder.readBean(agent);
    }

    private void saveUser() {
        transactionTemplate.execute(status -> {
            try {
                if ("Customer".equals(userTypeSelect.getValue())) {
                    Customer customer = new Customer();
                    customerBinder.writeBean(customer);
                    userService.saveCustomer(customer);
                    Notification.show("Customer saved successfully");
                    clearForm();
                } else if ("Agent".equals(userTypeSelect.getValue())) {
                    Agent agent = new Agent();
                    agentBinder.writeBean(agent);
                    agentService.saveAgent(agent);
                    Notification.show("Agent saved successfully");
                    clearForm();
                }
            } catch (ValidationException e) {
                Notification.show("Please check the form for errors: " + e.getMessage());
            }
            return null;
        });
    }

    private void clearForm() {
        userTypeSelect.clear();
        form.removeAll();
        customerBinder = new Binder<>(Customer.class);
        agentBinder = new Binder<>(Agent.class);
    }
}