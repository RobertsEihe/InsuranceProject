package com.project.InsuranceProject.views.LandingpageUI;

import com.project.InsuranceProject.views.LoginView;
import com.project.InsuranceProject.views.registerUser.RegisterView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class AppLayoutNavbar extends AppLayout {
    public AppLayoutNavbar() {
        H1 title = new H1("MyApp");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("left", "var(--lumo-space-l)").set("margin", "0")
                .set("position", "absolute");

        Button Login = new Button("Login", e -> {
            UI.getCurrent().navigate(LoginView.class);
        });
        Button Register = new Button("Register", e -> {
            UI.getCurrent().navigate(RegisterView.class);
        });

        HorizontalLayout navigation = getNavigation();
        navigation.getElement();

        addToNavbar(title, navigation,Login,Register);
    }
    private HorizontalLayout getNavigation() {
        HorizontalLayout navigation = new HorizontalLayout();
        navigation.addClassNames(LumoUtility.JustifyContent.CENTER,
                LumoUtility.Gap.SMALL, LumoUtility.Height.MEDIUM,
                LumoUtility.Width.FULL);
        //navigation.add(createLink("Dashboard"), createLink("Orders"),
               // createLink("Customers"), createLink("Products"));
        return navigation;
    }
}
