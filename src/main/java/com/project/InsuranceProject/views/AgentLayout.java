package com.project.InsuranceProject.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;

//@RolesAllowed("ROLE_AGENT")  // Restrict this layout to Agents
@PermitAll
@AnonymousAllowed
public class AgentLayout extends AppLayout {

	public AgentLayout() {
		createHeader();
	}

	private void createHeader() {
		H1 logo = new H1("Agent Dashboard");
		logo.addClassNames("text-l", "m-m");

		RouterLink policiesLink = new RouterLink("Policies", AgentView.class);
		RouterLink claimsLink = new RouterLink("Claims", AgentView.class);

		HorizontalLayout header = new HorizontalLayout(logo, policiesLink, claimsLink);
		header.setWidthFull();
		header.expand(logo);
		header.setDefaultVerticalComponentAlignment(Alignment.CENTER);

		addToNavbar(header);
	}
}
