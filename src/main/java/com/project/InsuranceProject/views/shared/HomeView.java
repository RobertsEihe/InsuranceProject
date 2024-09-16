package com.project.InsuranceProject.views.shared;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route(value = "", layout = AppLayoutNavbar.class)
@CssImport("./styles/shared-styles.css")
public class HomeView extends VerticalLayout {

    public HomeView() {
        addClassName("css-selector");
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        // Main Content Layout
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.setSpacing(false);
        mainLayout.setPadding(true);
        mainLayout.setAlignItems(Alignment.CENTER);
        mainLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        // Cards Layout
        HorizontalLayout cardsLayout = new HorizontalLayout();
        cardsLayout.setWidthFull();
        cardsLayout.setHeight("300px");  // Adjust this value as needed
        cardsLayout.setPadding(false);
        cardsLayout.setSpacing(true);
        cardsLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        cardsLayout.add(
                createCard("Insurance", "images/house.jpg", "Secure your future with our comprehensive insurance plans."),
                createCard("Health", "images/health.jpg", "Your health is our priority. Explore our health services."),
                createCard("Vehicle", "images/car.png", "Get the best deals on vehicle insurance and services.")
        );

        // Get Started Button
        Button getStartedButton = new Button("Get Started");
        getStartedButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);
        getStartedButton.getStyle().set("background-color", "#E6F3FF");
        getStartedButton.getStyle().set("color", "#000000");
        getStartedButton.getStyle().set("font-size", "20px");
        getStartedButton.getStyle().set("padding", "15px 30px");
        getStartedButton.getStyle().set("margin-top", "20px");

        // Add click listener to navigate to register page
        getStartedButton.addClickListener(e -> UI.getCurrent().navigate("register"));

        HorizontalLayout buttonWrapper = new HorizontalLayout(getStartedButton);
        buttonWrapper.setWidthFull();
        buttonWrapper.setJustifyContentMode(JustifyContentMode.CENTER);

        // Add components to the main layout
        mainLayout.add(cardsLayout, buttonWrapper);

        add(mainLayout);
        setSizeFull();
    }

    private VerticalLayout createCard(String title, String imageUrl, String description) {
        VerticalLayout cardLayout = new VerticalLayout();
        cardLayout.setWidth("300px");  // Fixed width for cards
        cardLayout.setHeight("100%");
        cardLayout.setPadding(false);
        cardLayout.setSpacing(false);
        cardLayout.getStyle().set("border", "1px solid #e0e0e0");
        cardLayout.getStyle().set("border-radius", "8px");
        cardLayout.getStyle().set("overflow", "hidden");
        cardLayout.getStyle().set("box-shadow", "0 2px 5px rgba(0, 0, 0, 0.1)");
        cardLayout.getStyle().set(
                "background-color",
                "#E8E8E8"
        );

        // Card Image
        Image cardImage = new Image(imageUrl, title + " Image");
        cardImage.setWidth("100%");
        cardImage.setHeight("150px");  // Reduced height
        cardImage.getStyle().set("object-fit", "cover");

        // Card Content
        VerticalLayout cardContent = new VerticalLayout();
        cardContent.setPadding(true);
        cardContent.setSpacing(false);

        Span cardTitle = new Span(title);
        cardTitle.getStyle().set("font-weight", "bold");
        cardTitle.getStyle().set("font-size", "16px");  // Reduced font size

        Span cardDescription = new Span(description);
        cardDescription.getStyle().set("color", "#666666");
        cardDescription.getStyle().set("font-size", "12px");  // Reduced font size

        cardContent.add(cardTitle, cardDescription);
        cardLayout.add(cardImage, cardContent);

        return cardLayout;
    }
}