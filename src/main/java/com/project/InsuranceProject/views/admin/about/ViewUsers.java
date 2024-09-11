package com.project.InsuranceProject.views.admin.about;

import com.project.InsuranceProject.views.admin.AdminLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

@PageTitle("View users")
@Route(value = "admin/view-users", layout = AdminLayout.class)
public class ViewUsers extends VerticalLayout {

    public ViewUsers() {
        setSpacing(false);

        Image img = new Image("https://codeskulptor-demos.commondatastorage.googleapis.com/GalaxyInvaders/back07.jpg", "placeholder plant");
        img.setWidth("200px");
        add(img);

        H2 header = new H2("This place intentionally left empty");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header);
        add(new Paragraph("It’s a place where you can grow your own UI 🤗"));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
