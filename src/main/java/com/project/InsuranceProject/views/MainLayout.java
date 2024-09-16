package com.project.InsuranceProject.views;

import com.project.InsuranceProject.security.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
//import com.project.InsuranceProject.views.helloworld.HelloWorldView;


/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H1 viewTitle;
    private SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Button logOut = new Button("Log out", e -> securityService.logout());

        addToNavbar(true, toggle, viewTitle, logOut);
    }

    private void addDrawerContent() {
        Span appName = new Span("InsuranceProject");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());
        scroller.setClassName(LumoUtility.Padding.SMALL);
        addToDrawer(header, scroller, createFooter());
        setPrimarySection(Section.DRAWER);
    }

    private SideNav createNavigation() {


//        nav.addItem(new SideNavItem("Hello World", HelloWorldView.class, LineAwesomeIcon.GLOBE_SOLID.create()));
        SideNav nav = new SideNav();
        nav.addItem(
                new SideNavItem("Your Policies", "/Customerpolicy",
                        VaadinIcon.DASHBOARD.create()),
                new SideNavItem("Create New Policy", "/createpolicy", VaadinIcon.LIST.create()),
                new SideNavItem("Your Claims", "/",
                        VaadinIcon.RECORDS.create()));
        return nav;

    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
