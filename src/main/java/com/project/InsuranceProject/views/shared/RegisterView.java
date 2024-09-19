package com.project.InsuranceProject.views.shared;
import com.project.InsuranceProject.data.services.RegisterFormBinder;
import com.project.InsuranceProject.data.services.UserService;
import com.project.InsuranceProject.views.shared.RegisterForm;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Route(value= "register", layout = AppLayoutNavbar.class)
@PermitAll
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

    private final UserService userService;

    @Autowired
    public RegisterView(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        RegisterForm registerForm = new RegisterForm();

        setHorizontalComponentAlignment(Alignment.CENTER, registerForm);

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        //Anchor loginLink = new Anchor("login", "Sign in");

        add(registerForm
               // loginLink
        );

        RegisterFormBinder registerFormBinder = new RegisterFormBinder(registerForm, userService, passwordEncoder);
        registerFormBinder.addBindingAndValidation();

    }

}
