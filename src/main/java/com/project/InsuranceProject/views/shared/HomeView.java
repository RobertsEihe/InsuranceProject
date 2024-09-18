package com.project.InsuranceProject.views.shared;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@Route(value = "", layout = AppLayoutNavbar.class)
@CssImport("./styles/shared-styles.css")
@AnonymousAllowed
public class HomeView extends VerticalLayout {

    public HomeView() {
        addClassName("css-selector");
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        HorizontalLayout contentLayout = new HorizontalLayout();
        contentLayout.setSizeFull();

        Image characterImage = new Image("/images/suitman.png", "Character Image");
        characterImage.setMaxHeight("80vh");
        characterImage.setWidth("auto");
        characterImage.getStyle().set("object-fit", "contain");

        FlexLayout imageContainer = new FlexLayout(characterImage);
        imageContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        imageContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        imageContainer.setSizeFull();

        VerticalLayout textLayout = new VerticalLayout();
        textLayout.setPadding(true);
        textLayout.setSpacing(true);
        textLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        Span heading = new Span("Insurance");
        heading.getStyle().set("font-size", "36px").set("font-weight", "bold");

        Span description = new Span("We offer a variety of insurance services to help secure your future. "
                + "From health and vehicle insurance to life coverage, we've got you covered.");
        description.getStyle().set("font-size", "18px").set("color", "#666666");

        Button getStartedButton = new Button("Get Started");
        getStartedButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getStartedButton.getStyle().set("background-color", "#007bff").set("color", "white");

        textLayout.add(heading, description, getStartedButton);

        contentLayout.add(imageContainer, textLayout);
        contentLayout.setFlexGrow(1, imageContainer);
        contentLayout.setFlexGrow(1, textLayout);

        Footer footer = new Footer();

        add(contentLayout);
        setFlexGrow(1, contentLayout);  // Ensures content layout grows to take up available space
        add(footer);
    }
}
