package com.project.InsuranceProject.views.shared;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class AppLayoutNavbar extends AppLayout {

    public AppLayoutNavbar() {
        createHeader();
    }

    private void createHeader() {
        H1 logo = new H1("Insurance Project");
        logo.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM
        );

        HorizontalLayout header = new HorizontalLayout(logo, createNavigation());
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM
        );
        header.getStyle().set("background-color", "#E8E8E8");

        addToNavbar(header);
    }

    private HorizontalLayout createNavigation() {
        HorizontalLayout navigation = new HorizontalLayout();
        navigation.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        navigation.setWidthFull();
        navigation.setHeight("100%");

        navigation.add(
                createNavButton("Home", ""),
                createNavButton("Login", "login"),
                createNavButton("Register", "register"),
                createNavButton("About Us", "about-us")
        );

        return navigation;
    }

    private Div createNavButton(String text, String route) {
        Button button = new Button(text, e -> UI.getCurrent().navigate(route));
        button.getStyle()
                .set("background", "transparent")
                .set("color", "black")
                .set("border", "none")
                .set("cursor", "pointer")
                .set("font-size", "16px")
                .set("padding", "0 15px");

        Div buttonContainer = new Div(button);
        buttonContainer.getStyle()
                .set("display", "flex")
                .set("align-items", "center")
                .set("height", "100%")
                .set("transition", "background-color 0.3s")
                .set("padding-right","5px");

        buttonContainer.addClassName("nav-button-container");

        // Add hover effect
        buttonContainer.getElement().addEventListener("mouseover", e ->
                buttonContainer.getStyle().set("background-color", "rgba(255,255,255,0.1)"));
        buttonContainer.getElement().addEventListener("mouseout", e ->
                buttonContainer.getStyle().set("background-color", "transparent"));

        return buttonContainer;
    }
}