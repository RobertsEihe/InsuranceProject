
package com.project.InsuranceProject.views.shared;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@Route(value = "login", layout = AppLayoutNavbar.class)
@PageTitle("LoginView")
@AnonymousAllowed
//@CssImport("./styles/shared-styles.css")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm loginForm;

    public LoginView() {
        loginForm = new LoginForm();
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm.setForgotPasswordButtonVisible(false);
        customizeLoginForm();


        loginForm.setAction("login");

        add(new H1("Insurance System"), loginForm);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
            loginForm.setError(true);
        }
    }

    private void customizeLoginForm() {
        // Change the label of the username field to "Email"
        //loginForm.setUsernameLabel("Email");

        // Customize the Login button text and other labels using LoginI18n
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.getForm().setTitle("Sign In");
        i18n.getForm().setSubmit("Sign In"); // Change login button text to "Sign In"
        i18n.getForm().setUsername("Email"); // Change username field label to "Email"
        i18n.getForm().setPassword("Password"); // Keep password label the same
        loginForm.setI18n(i18n); // Apply the custom i18n settings
    }

}

