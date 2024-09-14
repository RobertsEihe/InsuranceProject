package com.project.InsuranceProject.views.admin;

import com.project.InsuranceProject.security.SecurityService;
import com.project.InsuranceProject.views.admin.forms.AddUsers;
import com.project.InsuranceProject.views.admin.tables.UserManagementView;
import com.project.InsuranceProject.views.admin.forms.EditUserRoles;
import com.project.InsuranceProject.views.admin.myview.MyViewView;


import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;

import com.vaadin.flow.component.icon.VaadinIcon;


/**
 * The main view is a top-level placeholder for other views.
 */
public class AdminLayout extends AppLayout {

    private H1 viewTitle;
    private SecurityService securityService;

    public AdminLayout(SecurityService securityService) {
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
        Span appName = new Span("Admin menu");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();


        nav.addItem(new SideNavItem("View Users", UserManagementView.class, VaadinIcon.FILE.create()));
        nav.addItem(new SideNavItem("Add Users", AddUsers.class, VaadinIcon.PENCIL.create()));
        nav.addItem(new SideNavItem("View something else", MyViewView.class, VaadinIcon.CREDIT_CARD.create()));

        return nav;
    }

    private Footer createFooter() {

        return new Footer();
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
