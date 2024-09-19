package com.project.InsuranceProject.views.shared;

import com.project.InsuranceProject.data.entity.Users;
import com.project.InsuranceProject.data.services.UserRetrievalService;
import com.project.InsuranceProject.security.SecurityService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@CssImport("./styles/shared-styles.css")
public class AppLayoutNavbar extends AppLayout {
    private final SecurityService securityService;
    private final UserRetrievalService userRetrievalService;

    public AppLayoutNavbar(SecurityService securityService, UserRetrievalService userRetrievalService) {
        this.securityService = securityService;
        this.userRetrievalService = userRetrievalService;
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
        HorizontalLayout rightNav = new HorizontalLayout();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
             Optional<Users> user = userRetrievalService.getUserByUsername(username);
             String name = user.map(Users::getName).orElse(username);
            Avatar avatarName = new Avatar(name);
            avatarName.addClassName("Avatar");
            Span usernametext = new Span("Welcome "+name);
            Button logOut = new Button("Log out", e -> securityService.logout());
            logOut.addThemeVariants(ButtonVariant.LUMO_ERROR);
            rightNav.add(avatarName,usernametext,logOut);
        }
        else {
            Button signInButton = new Button("Sign In", e -> UI.getCurrent().navigate("login"));
            Button signUpButton = new Button("Sign Up", e -> UI.getCurrent().navigate("register"));
            signUpButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            rightNav.add(signInButton, signUpButton);
        }


        rightNav.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        rightNav.setSpacing(true);

        return rightNav;
    }

}