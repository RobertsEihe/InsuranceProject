
package com.project.InsuranceProject.views.shared;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@Route(value = "login", layout = AppLayoutNavbar.class)
@PageTitle("LoginView")
@AnonymousAllowed
@CssImport("./styles/shared-styles.css")
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    private LoginForm login = new LoginForm();

    public LoginView() {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");



        //Anchor registerLink = new Anchor("register", "Sign up");
        add(
                new H1("Insurance System"),
                login
                //registerLink
        );

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")){
            login.setError(true);
        }
    }

    public void LoginValidation() {
        // Use LoginOverlay if you want a more advanced login popup (optional)
        LoginOverlay loginOverlay = new LoginOverlay();
        loginOverlay.setError(true);
        add(loginOverlay);
        loginOverlay.setOpened(true);

        // Prevent the example from stealing focus when browsing the documentation
        loginOverlay.getElement().setAttribute("no-autofocus", "");
    }
}
