package com.project.InsuranceProject.data.services;
import com.project.InsuranceProject.data.entity.Users;
import com.project.InsuranceProject.security.Roles;
import com.project.InsuranceProject.views.registerUser.RegisterForm;
//import com.project.InsuranceProject.data.services.UserDetails;
import com.project.InsuranceProject.data.services.UserService;

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

    /**
     * Flag for disabling first run for password validation
     */
    private boolean enablePasswordValidation;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterFormBinder(RegisterForm registerForm, UserService userService, PasswordEncoder passwordEncoder) {
        this.registerForm = registerForm;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Method to add the data binding and validation logics
     * to the registration form
     */
    public void addBindingAndValidation() {
        //BeanValidationBinder<UserDetails> binder = new BeanValidationBinder<>(UserDetails.class);
        BeanValidationBinder<Users> binder = new BeanValidationBinder<>(Users.class);
        binder.bindInstanceFields(registerForm);

        // A custom validator for password fields
        binder.forField(registerForm.getPasswordField())
                .withValidator(this::passwordValidator).bind("password");

        // The second password field is not connected to the Binder, but we
        // want the binder to re-check the password validator when the field
        // value changes. The easiest way is just to do that manually.
        registerForm.getPasswordConfirmField().addValueChangeListener(e -> {
            // The user has modified the second field, now we can validate and show errors.
            // See passwordValidator() for how this flag is used.
            enablePasswordValidation = true;

            binder.validate();
        });

        // Set the label where bean-level error messages go
        binder.setStatusLabel(registerForm.getErrorMessageField());

        // And finally the submit button
        registerForm.getSubmitButton().addClickListener(event -> {
            try {
                // Create empty bean to store the details into
                //UserDetails userBean = new UserDetails();
                Users user = new Users();

                // Run validators and write the values to the bean
                binder.writeBean(user);

                // Typically, you would here call backend to store the bean
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                user.setRole(Roles.USER);
                userService.saveUser(user);
                // Show success message if everything went well
                showSuccess();
            } catch (ValidationException exception) {
                // validation errors are already visible for each field,
                // and bean-level errors are shown in the status label.
                // We could show additional messages here if we want, do logging, etc.
            }
        });
    }

    /**
     * Method to validate that:
     * <p>
     * 1) Password is at least 8 characters long
     * <p>
     * 2) Values in both fields match each other
     */
    private ValidationResult passwordValidator(String pass1, ValueContext ctx) {
        /*
         * Just a simple length check. A real version should check for password
         * complexity as well!
         */

        if (pass1 == null || pass1.length() < 4) {
            return ValidationResult.error("Password should be at least 8 characters long");
        }

        if (!enablePasswordValidation) {
            // user hasn't visited the field yet, so don't validate just yet, but next time.
            enablePasswordValidation = true;
            return ValidationResult.ok();
        }

        String pass2 = registerForm.getPasswordConfirmField().getValue();

        if (pass1 != null && pass1.equals(pass2)) {
            return ValidationResult.ok();
        }

        return ValidationResult.error("Passwords do not match");
    }

//    private Users mapToUsersEntity(UserDetails userBean) {
//        Users user = new Users();
//        user.setFullName(userBean.getFirstName()); // Assuming you have full name in UserDetails
//        user.setEmail(userBean.getEmail());
//        user.setPassword(userBean.getPassword()); // Make sure to hash the password before storing
//        user.setAllowMarketing(userBean.isAllowMarketing());
//
//        // Map other fields from UserDetails to Users as needed
//
//        return user;
//    }

    /**
     * We call this method when form submission has succeeded
     */
    private void showSuccess() {
        Notification notification =
                Notification.show("Data saved, welcome ");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        // Here you'd typically redirect the user to another view
    }
}
