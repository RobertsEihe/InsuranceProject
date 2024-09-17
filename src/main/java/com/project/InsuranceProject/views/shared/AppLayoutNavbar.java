package com.project.InsuranceProject.views.shared;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;

@CssImport("./styles/shared-styles.css")
public class AppLayoutNavbar extends AppLayout {

    public AppLayoutNavbar() {

        HorizontalLayout leftNav = createLeftNav();
        HorizontalLayout rightNav = createRightNav();
        HorizontalLayout header = new HorizontalLayout(leftNav, rightNav);
        header.setWidthFull();
        header.getStyle().set("background-color", "rgba(0, 0, 0, 0)");
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setPadding(true);

        addToNavbar(header);
    }

    private HorizontalLayout createLeftNav() {
        Image logo = new Image("images/Logo1.png", "Company Logo");
        logo.setHeight("40px");

        RouterLink homeLink = new RouterLink("Home", HomeView.class);
        RouterLink aboutUsLink = new RouterLink("About Us", AboutUs.class);

        HorizontalLayout leftNav = new HorizontalLayout(logo, homeLink, aboutUsLink);
        leftNav.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        leftNav.setSpacing(true);


        return leftNav;
    }

    private HorizontalLayout createRightNav() {
        Button signInButton = new Button("Sign In", e -> UI.getCurrent().navigate("login"));
        Button signUpButton = new Button("Sign Up", e -> UI.getCurrent().navigate("register"));
        signUpButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout rightNav = new HorizontalLayout(signInButton, signUpButton);
        rightNav.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        rightNav.setSpacing(true);

        return rightNav;
    }

}