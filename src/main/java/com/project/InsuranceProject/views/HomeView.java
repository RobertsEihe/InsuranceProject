package com.project.InsuranceProject.views;
import com.project.InsuranceProject.views.LandingpageUI.AppLayoutNavbar;
import com.project.InsuranceProject.views.registerUser.RegisterView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
@PermitAll
@Route("/")
@CssImport("./themes/chat-theme/styles.css")
public class HomeView extends VerticalLayout {

    public HomeView() { homeview();}

    private void homeview(){

        VerticalLayout formLayoutv = new VerticalLayout();
        H1 heading = new H1("Welcome to Max Insurance");
        heading.setClassName("H1");
        formLayoutv.setDefaultHorizontalComponentAlignment(Alignment.CENTER);;



       AppLayoutNavbar appLayoutNavbar = new AppLayoutNavbar();
        formLayoutv.add(appLayoutNavbar);
        formLayoutv.setMargin(true);
        formLayoutv.add(heading);

        Div mainSection = new Div();
        mainSection.setClassName("mainsection"); // Class for background image and styling
        mainSection.getStyle().set("background-color", "green");
        // Text Content
        /*VerticalLayout textSection = new VerticalLayout();
        H1 heading1 = new H1("Take The Worry Out Of Life With Insurance Protection");
        Paragraph subHeading = new Paragraph("Lorem ipsum dolor sit amet, consectetur adipiscing elit...");
        Button getStartedButton = new Button("Get Started");
        Button contactButton = new Button("Contact Us");

        textSection.add(heading1, subHeading, getStartedButton, contactButton);
        textSection.setClassName("text-section"); // Class for text positioning
        textSection.setSpacing(true);*/

        //mainSection.add(textSection);
       // mainSection.setHeight("400px"); // Set the height of the section


        add(formLayoutv,mainSection);
        //add(formLayouth);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);


    }



}
