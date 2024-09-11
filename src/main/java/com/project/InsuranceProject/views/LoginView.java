
package com.project.InsuranceProject.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

// ApplicationServiceInitListener


@Route("login")
@PageTitle("LoginView")
//@CssImport("./styles/shared-styles.css")
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    private LoginForm login = new LoginForm();

    public LoginView() {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");


        add(
                new H1("Insurance System"),
                login
        );

        login.addLoginListener(event -> {
            // Redirect to the desired route after successful login
            UI.getCurrent().navigate("/hello");  // Change "/hello" to your desired route
        });

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
}
