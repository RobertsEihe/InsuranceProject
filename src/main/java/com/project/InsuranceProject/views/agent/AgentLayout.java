package com.project.InsuranceProject.views.agent;
import com.project.InsuranceProject.security.SecurityService;
import com.project.InsuranceProject.views.agent.tables.AgentView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.component.icon.VaadinIcon;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = null;
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			username = ((UserDetails) authentication.getPrincipal()).getUsername();
		}
		Avatar avatarName = new Avatar(username);
		avatarName.setColorIndex(0);
		Span user = new Span("Welcome "+username+"!");
		Button logOut = new Button("Log out", e -> securityService.logout());
		logOut.addThemeVariants(ButtonVariant.LUMO_ERROR);
		HorizontalLayout headerLayout = new HorizontalLayout(toggle,viewTitle,avatarName,user,logOut);
		headerLayout.setWidthFull();
		headerLayout.setPadding(true);
		headerLayout.setSpacing(true);
		headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
		headerLayout.expand(viewTitle);
		logOut.getStyle().set("margin-right", "10px");

		addToNavbar(headerLayout);
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


		nav.addItem(new SideNavItem("View Agent Policies", AgentView.class, VaadinIcon.HOME.create())); // "Agent View" button

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
