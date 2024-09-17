package com.project.InsuranceProject.views.shared;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

public class Footer extends HorizontalLayout {

    public Footer() {
        addClassName("footer");
        setWidthFull();
        getStyle().set("background-color", "#007bff");
        getStyle().set("color", "white");
        getStyle().set("padding", "10px");
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        Span copyrightText = new Span("© 2024 Insurance Company. All Rights Reserved.");
        copyrightText.getStyle().set("font-size", "14px");
        add(copyrightText);
    }
}
