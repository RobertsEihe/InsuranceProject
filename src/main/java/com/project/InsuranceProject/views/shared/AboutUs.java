package com.project.InsuranceProject.views.shared;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@Route(value = "about-us", layout = AppLayoutNavbar.class)
@PageTitle("About Us")
@CssImport("./styles/shared-styles.css")
@AnonymousAllowed
public class AboutUs extends VerticalLayout {

    public AboutUs() {
        addClassName("css-selector");
        setSizeFull();
        setSpacing(true);
        setPadding(true);

        add(createHeader());
        add(createCompanyHistory());
        add(createMissionStatement());
        add(createTeamSection());
    }

    private VerticalLayout createHeader() {
        VerticalLayout header = new VerticalLayout();
        header.setAlignItems(Alignment.CENTER);

        H2 title = new H2("About Insurance Project");
        Paragraph intro = new Paragraph("Your trusted partner in insurance solutions since 2024.");

        header.add(title, intro);
        return header;
    }

    private HorizontalLayout createCompanyHistory() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();

        VerticalLayout textContent = new VerticalLayout();
        H3 historyTitle = new H3("Our History");
        Paragraph historyText = new Paragraph("Founded in 2024, Insurance Project has grown from a small dream project to almost working insurance solution. Our journey is marked by a commitment to innovation and customer satisfaction.");

        textContent.add(historyTitle, historyText);

        Image historyImage = new Image("images/insurance.png", "Company History");
        historyImage.setWidth("400px");

        layout.add(textContent, historyImage);
        return layout;
    }

    private VerticalLayout createMissionStatement() {
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.CENTER);

        H3 missionTitle = new H3("Our Mission");
        Paragraph missionText = new Paragraph("To provide peace of mind through innovative insurance solutions, ensuring the financial security and well-being of our clients in an ever-changing world.");

        layout.add(missionTitle, missionText);
        return layout;
    }

    private HorizontalLayout createTeamSection() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();

        layout.add(createTeamMember("Roberts Eihe", "Developer / Product owner", "images/testavatar.png"));
        layout.add(createTeamMember("Aleksandrs Frīdenbergs", "Developer", "images/testavatar.png"));
        layout.add(createTeamMember("Andrejs Kopņins", "Developer", "images/testavatar.png"));
        layout.add(createTeamMember("Madhavendra Singh", "Developer", "images/testavatar.png"));

        return layout;
    }

    private VerticalLayout createTeamMember(String name, String position, String imageUrl) {
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.CENTER);

        Image memberImage = new Image(imageUrl, name);
        memberImage.setWidth("150px");
        memberImage.getStyle().set("border-radius", "50%");

        H3 memberName = new H3(name);
        Paragraph memberPosition = new Paragraph(position);

        layout.add(memberImage, memberName, memberPosition);
        return layout;
    }
}