package com.project.InsuranceProject.data.services;
import com.project.InsuranceProject.data.entity.Users;
import com.project.InsuranceProject.data.repositories.UserRepository;
import com.project.InsuranceProject.security.Roles;
import com.project.InsuranceProject.views.shared.RegisterForm;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@PermitAll
public class RegisterFormBinder {
    private RegisterForm registerForm;
    private boolean enablePasswordValidation;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterFormBinder(RegisterForm registerForm, UserService userService, PasswordEncoder passwordEncoder) {
        this.registerForm = registerForm;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    public void addBindingAndValidation() {
        BeanValidationBinder<Users> binder = new BeanValidationBinder<>(Users.class);
        binder.bindInstanceFields(registerForm);

        binder.forField(registerForm.getPasswordField())
                .withValidator(this::passwordValidator).bind("password");

        registerForm.getPasswordConfirmField().addValueChangeListener(e -> {
            enablePasswordValidation = true;
            binder.validate();
        });

        binder.forField(registerForm.getNameField())
                .withValidator(this::nameValidator).bind("name");

        binder.forField(registerForm.getEmailField())
                .withValidator(this::emailValidator).bind("email");

        binder.forField(registerForm.getAddressField())
                .withValidator(this::addressValidator).bind("address");

        binder.forField(registerForm.getDateOfBirthField())
                .withValidator(this::dateOfBirthValidator).bind("date_of_birth");

        binder.setStatusLabel(registerForm.getErrorMessageField());

        registerForm.getSubmitButton().addClickListener(event -> {
            try {
                Users user = new Users();

                binder.writeBean(user);

                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                user.setRole(Roles.CUSTOMER);
                user.setUsername(user.getEmail());

                userService.saveUser(user);
                showSuccess();
            } catch (ValidationException exception) {
                // Error messages are already shown, hre we could show additional messages if we want, do logging, etc.
            }
        });
    }


    private ValidationResult passwordValidator(String pass1, ValueContext ctx) {

        if (pass1 == null || pass1.length() < 4) {
            return ValidationResult.error("Password should be at least 8 characters long.");
        }

        String passwordPattern = "^(?=.*[a-zA-Z])(?=.*\\d).+$";
        if (!pass1.matches(passwordPattern)) {
            return ValidationResult.error("Password must contain letters and numbers");
        }

        if (!enablePasswordValidation) {
            enablePasswordValidation = true;
            return ValidationResult.ok();
        }

        String pass2 = registerForm.getPasswordConfirmField().getValue();

        if (pass1 != null && pass1.equals(pass2)) {
            return ValidationResult.ok();
        }

        return ValidationResult.error("Passwords do not match");
    }

    private ValidationResult nameValidator(String name, ValueContext ctx) {
        if (name == null) {
            return ValidationResult.error("Please provide name");
        }

        return ValidationResult.ok();
    }

    private ValidationResult emailValidator(String email, ValueContext ctx) {
        if (email == null || email.isEmpty()) {
            return ValidationResult.error("Please provide email");
        }
        // Simple email format regex (a@a.a)
        String emailRegex = "^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,}$";
        if (!email.matches(emailRegex)) {
            return ValidationResult.error("Invalid email format (example: name@surname.com)");
        }

        if (userService.isUsernameExists(email)) {
            return ValidationResult.error("Email is already registered");
        }

        return ValidationResult.ok();
    }

    private ValidationResult addressValidator(String address, ValueContext ctx) {
        if (address == null) {
            return ValidationResult.error("Please provide address");
        }

        return ValidationResult.ok();
    }

    private ValidationResult dateOfBirthValidator(LocalDate date, ValueContext ctx) {
        if (date == null) {
            return ValidationResult.error("Please provide date of birth");
        }

        return ValidationResult.ok();
    }

    private void showSuccess() {
        Notification notification =
                Notification.show("Data saved, welcome ");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        // Here ywe can redirect user to login screen automatically
    }
}
