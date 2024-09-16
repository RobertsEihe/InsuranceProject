package com.project.InsuranceProject.data.services;
import com.project.InsuranceProject.data.entity.Users;
import com.project.InsuranceProject.security.Roles;
import com.project.InsuranceProject.views.registerUser.RegisterForm;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

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

        binder.setStatusLabel(registerForm.getErrorMessageField());

        registerForm.getSubmitButton().addClickListener(event -> {
            try {
                Users user = new Users();

                binder.writeBean(user);

                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                user.setRole(Roles.AGENT);

                userService.saveUser(user);
                showSuccess();
            } catch (ValidationException exception) {
                // Error messages are already shown, hre we could show additional messages if we want, do logging, etc.
            }
        });
    }


    private ValidationResult passwordValidator(String pass1, ValueContext ctx) {

        if (pass1 == null || pass1.length() < 4) {
            return ValidationResult.error("Password should be at least 8 characters long");
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

    private void showSuccess() {
        Notification notification =
                Notification.show("Data saved, welcome ");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        // Here ywe can redirect user to login screen automatically
    }
}
