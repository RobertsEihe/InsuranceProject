package com.project.InsuranceProject.views.registerUser;
import com.project.InsuranceProject.data.entity.Users;
import com.project.InsuranceProject.data.repositories.UserRepository;
import com.project.InsuranceProject.data.services.RegisterFormBinder;
import com.project.InsuranceProject.data.services.UserService;
import com.project.InsuranceProject.views.registerUser.RegisterForm;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Route("register")
@PermitAll
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

    private final UserService userService;

    @Autowired
    public RegisterView(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        RegisterForm registerForm = new RegisterForm();

        setHorizontalComponentAlignment(Alignment.CENTER, registerForm);

        add(registerForm);

        RegisterFormBinder registerFormBinder = new RegisterFormBinder(registerForm, userService, passwordEncoder);
        registerFormBinder.addBindingAndValidation();

    }

}
