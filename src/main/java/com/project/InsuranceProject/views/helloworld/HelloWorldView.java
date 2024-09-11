package com.project.InsuranceProject.views.helloworld;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.project.InsuranceProject.views.MainLayout;

@PageTitle("Hello World")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HelloWorldView extends VerticalLayout {
    private TextField name;
    private Button sayHello;

    public HelloWorldView() {
        // Create TextField and Button for the Hello World message
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
        sayHello.addClickShortcut(Key.ENTER);

        // Horizontal Layout for the Hello World components
        HorizontalLayout helloLayout = new HorizontalLayout(name, sayHello);
        helloLayout.setMargin(true);
        helloLayout.setVerticalComponentAlignment(Alignment.END, name, sayHello);

        // Add components to the layout
        add(helloLayout);
    }
}