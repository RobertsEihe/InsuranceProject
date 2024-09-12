package com.project.InsuranceProject.views;
import com.project.InsuranceProject.views.LandingpageUI.AppLayoutNavbar;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;
@PermitAll
@Route("Home")
public class HomeView extends VerticalLayout {

    public HomeView() { homeview();}

    private void homeview(){
        VerticalLayout formLayoutv = new VerticalLayout();

        H1 heading = new H1("Welcome to Max Insurance");
        formLayoutv.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        Button Login = new Button("Login", e -> {
            UI.getCurrent().navigate(LoginView.class);
        });
        Button Register = new Button("Register", e -> {
            UI.getCurrent().navigate(LoginView.class);
        });

        HorizontalLayout formLayouth = new HorizontalLayout(Login,Register);
        formLayouth.setJustifyContentMode(JustifyContentMode.CENTER);

        AppLayoutNavbar appLayoutNavbar = new AppLayoutNavbar();
        formLayouth.add(appLayoutNavbar);
        formLayoutv.add(heading);
        add(formLayoutv);
        add(formLayouth);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }



}
