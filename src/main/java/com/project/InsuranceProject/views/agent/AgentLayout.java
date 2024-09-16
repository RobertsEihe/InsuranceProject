package com.project.InsuranceProject.views.agent;

import com.project.InsuranceProject.security.SecurityService;
import com.project.InsuranceProject.views.agent.forms.ViewPolicies;
import com.project.InsuranceProject.views.agent.forms.ViewClaims;
import com.project.InsuranceProject.views.agent.tables.AgentView;  // Import AgentView
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
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Agent Dashboard")
@RolesAllowed({"AGENT", "ADMIN"})
public class AgentLayout extends AppLayout {

	private H1 viewTitle;
	private final SecurityService securityService;

	public AgentLayout(SecurityService securityService) {
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

		// Consistent with AdminLayout - using the same Navbar structure
		addToNavbar(true, toggle, viewTitle, logOut);
	}

	private void addDrawerContent() {
		Span appName = new Span("Agent Menu");
		appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);
		Header header = new Header(appName);

		Scroller scroller = new Scroller(createNavigation());

		addToDrawer(header, scroller, createFooter());
	}

	private SideNav createNavigation() {
		SideNav nav = new SideNav();

		// Adding consistent icons and navigation
		nav.addItem(new SideNavItem("Agent View", AgentView.class, VaadinIcon.HOME.create())); // "Agent View" button
		nav.addItem(new SideNavItem("View Policies", ViewPolicies.class, VaadinIcon.FILE_TEXT_O.create()));
		nav.addItem(new SideNavItem("View Claims", ViewClaims.class, VaadinIcon.CLIPBOARD_TEXT.create()));

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
