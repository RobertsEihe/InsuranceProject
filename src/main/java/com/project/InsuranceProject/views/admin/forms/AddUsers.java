package com.project.InsuranceProject.views.admin.forms;

import com.project.InsuranceProject.data.entity.Users;
import com.project.InsuranceProject.data.services.UserService;
import com.project.InsuranceProject.security.Roles;
import com.project.InsuranceProject.views.admin.AdminLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
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
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;

@PageTitle("Add New User")
@Route(value = "admin/add-user", layout = AdminLayout.class)
@RolesAllowed(Roles.ADMIN)
public class AddUsers extends VerticalLayout {

    private final UserService userService;
    private final TransactionTemplate transactionTemplate;
    private final PasswordEncoder passwordEncoder;
    private ComboBox<String> roleSelect;
    private FormLayout form;
    private Binder<Users> userBinder;

    @Autowired
    public AddUsers(UserService userService, TransactionTemplate transactionTemplate, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.transactionTemplate = transactionTemplate;
        this.passwordEncoder = passwordEncoder;
        setSizeFull();

        roleSelect = new ComboBox<>("Select user role");
        roleSelect.setItems(Roles.CUSTOMER, Roles.AGENT, Roles.EMPLOYEE, Roles.ADMIN);
        roleSelect.addValueChangeListener(event -> createForm());

        form = new FormLayout();
        userBinder = new Binder<>(Users.class);

        Button saveButton = new Button("Save", event -> saveUser());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(roleSelect, form, saveButton);
    }

    private void createForm() {
        form.removeAll();
        Users user = new Users();

        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        TextField name = new TextField("Name");
        EmailField email = new EmailField("Email address");
        TextField phone = new TextField("Phone");
        DatePicker dateOfBirth = new DatePicker("Date of Birth");

        form.add(username, password, name, email, phone, dateOfBirth);

        if (Roles.CUSTOMER.equals(roleSelect.getValue())) {
            TextField address = new TextField("Address");
            IntegerField loyalty = new IntegerField("Loyalty");
            TextField dlNum = new TextField("Driver's License Number");
            DatePicker dlIssueDate = new DatePicker("DL Issue Date");
            DatePicker dlExpireDate = new DatePicker("DL Expire Date");
            TextField bankAccount = new TextField("Bank Account");

            form.add(address, loyalty, dlNum, dlIssueDate, dlExpireDate, bankAccount);

            userBinder.forField(address).bind(Users::getAddress, Users::setAddress);
            userBinder.forField(loyalty).bind(Users::getLoyalty, Users::setLoyalty);
            userBinder.forField(dlNum).bind(Users::getDl_num, Users::setDl_num);
            userBinder.forField(dlIssueDate).bind(Users::getDl_issue_date, Users::setDl_issue_date);
            userBinder.forField(dlExpireDate).bind(Users::getDl_expire_Date, Users::setDl_expire_Date);
            userBinder.forField(bankAccount).bind(Users::getBank_account, Users::setBank_account);
        }

        userBinder = new Binder<>(Users.class);
        userBinder.forField(username)
                .asRequired("Username is required")
                .withValidator(
                        this::isUsernameUnique,
                        "This username is already in use"
                )
                .bind(Users::getUsername, Users::setUsername);

        userBinder.forField(password)
                .asRequired("Password is required")
                .withValidator(
                        pwd -> pwd.length() >= 8 && pwd.matches(".*[a-zA-Z].*") && pwd.matches(".*\\d.*"),
                        "Password must be at least 8 characters long and contain both letters and numbers"
                )
                .bind(Users::getPassword, Users::setPassword);

        userBinder.forField(name).asRequired("Name is required").bind(Users::getName, Users::setName);

        email.setRequiredIndicatorVisible(true);
        email.setPattern("^[^@\\s]+@[^@\\s]+$");
        email.setErrorMessage("Please enter a valid email address (must contain @)");

        userBinder.forField(email)
                .asRequired("Email is required")
                .withValidator(emailValue -> emailValue != null && !email.isInvalid(), "Invalid email format")
                .bind(Users::getEmail, Users::setEmail);

        userBinder.forField(phone).asRequired("Phone is required").bind(Users::getPhone, Users::setPhone);

        userBinder.forField(dateOfBirth)
                .asRequired("Date of Birth is required")
                .withValidator(
                        dob -> dob.isBefore(LocalDate.now()),
                        "Birthday can't be in the future"
                )
                .bind(Users::getDate_of_birth, Users::setDate_of_birth);

        userBinder.readBean(user);
    }

    private boolean isUsernameUnique(String username) {
        return !userService.isUsernameExists(username);
    }

    private void saveUser() {
        transactionTemplate.execute(status -> {
            try {
                Users user = new Users();
                userBinder.writeBean(user);
                user.setRole(roleSelect.getValue());
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                user.setFraudStatus(false);

                if (!Roles.CUSTOMER.equals(user.getRole())) {
                    user.setAddress("");
                    user.setLoyalty(0);
                    user.setDl_num("");
                    user.setDl_issue_date(null);
                    user.setDl_expire_Date(null);
                    user.setBank_account("");
                }

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