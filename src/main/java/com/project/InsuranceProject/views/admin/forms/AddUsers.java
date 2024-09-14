package com.project.InsuranceProject.views.admin.forms;

import com.project.InsuranceProject.data.entity.Users;
import com.project.InsuranceProject.data.services.UserService;
import com.project.InsuranceProject.security.Roles;
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
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

@PermitAll
@PageTitle("Add New User")
@Route(value = "admin/add-user", layout = AdminLayout.class)
@RolesAllowed(Roles.ADMIN)
public class AddUsers extends VerticalLayout {

    private final UserService userService;
    private final TransactionTemplate transactionTemplate;
    private ComboBox<String> roleSelect;
    private FormLayout form;
    private Binder<Users> userBinder;

    @Autowired
    public AddUsers(UserService userService, TransactionTemplate transactionTemplate) {
        this.userService = userService;
        this.transactionTemplate = transactionTemplate;
        setSizeFull();

        H2 header = new H2("Add New User");
        roleSelect = new ComboBox<>("Select user role");
        roleSelect.setItems("CUSTOMER", "AGENT", "EMPLOYEE", "ADMIN");
        roleSelect.addValueChangeListener(event -> createForm());

        form = new FormLayout();
        userBinder = new Binder<>(Users.class);

        Button saveButton = new Button("Save", event -> saveUser());

        add(header, roleSelect, form, saveButton);
    }

    private void createForm() {
        form.removeAll();
        Users user = new Users();

        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
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

        form.add(username, password, name, email, phone, address, dateOfBirth, loyalty, dlNum, dlIssueDate, dlExpireDate, bankAccount);

        userBinder = new Binder<>(Users.class);
        userBinder.forField(username).asRequired("Username is required").bind(Users::getUsername, Users::setUsername);
        userBinder.forField(password).asRequired("Password is required").bind(Users::getPassword, Users::setPassword);
        userBinder.forField(name).bind(Users::getName, Users::setName);
        userBinder.forField(email).bind(Users::getEmail, Users::setEmail);
        userBinder.forField(phone).bind(Users::getPhone, Users::setPhone);
        userBinder.forField(address).bind(Users::getAddress, Users::setAddress);
        userBinder.forField(dateOfBirth).bind(Users::getDate_of_birth, Users::setDate_of_birth);
        userBinder.forField(loyalty).bind(Users::getLoyalty, Users::setLoyalty);
        userBinder.forField(dlNum).bind(Users::getDl_num, Users::setDl_num);
        userBinder.forField(dlIssueDate).bind(Users::getDl_issue_date, Users::setDl_issue_date);
        userBinder.forField(dlExpireDate).bind(Users::getDl_expire_Date, Users::setDl_expire_Date);
        userBinder.forField(bankAccount).bind(Users::getBank_account, Users::setBank_account);

        userBinder.readBean(user);
    }

    private void saveUser() {
        transactionTemplate.execute(status -> {
            try {
                Users user = new Users();
                userBinder.writeBean(user);
                user.setRole(roleSelect.getValue());
                Users savedUser = userService.saveUser(user);
                Notification.show(savedUser.getRole() + " saved successfully");
                clearForm();
            } catch (ValidationException e) {
                Notification.show("Please check the form for errors: " + e.getMessage());
            }
            return null;
        });
    }

    private void clearForm() {
        roleSelect.clear();
        form.removeAll();
        userBinder = new Binder<>(Users.class);
    }
}