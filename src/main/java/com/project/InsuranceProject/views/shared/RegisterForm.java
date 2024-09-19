package com.project.InsuranceProject.views.shared;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;
import java.util.stream.Stream;

public class RegisterForm extends FormLayout {

    private H1 title;
    private TextField name;
    private TextField email;
    private PasswordField password;
    private PasswordField passwordConfirm;
    private TextField address;
    private DatePicker date_of_birth;
    private Span errorMessageField;
    private Button submitButton;

    private Span emailInfo;
    private Span passwordInfo;
    private Span passwordConfirmInfo;


    public RegisterForm() {
        title = new H1("Sign up");
        name = new TextField("Full name");
        email = new TextField("Email");
        address = new TextField("Address");
        date_of_birth = new DatePicker("Date of birth");
        date_of_birth.setMax(LocalDate.now());

        password = new PasswordField("Password");
        passwordConfirm = new PasswordField("Confirm password");

        emailInfo = new Span();
        passwordInfo = new Span();
        passwordConfirmInfo = new Span();

        Stream.of(emailInfo, passwordInfo, passwordConfirmInfo)
                .forEach(info -> info.getStyle().set("color", "gray").set("font-size", "12px").set("margin-left", "5px"));

        addFocusListeners();


        setRequiredIndicatorVisible(name, email, address, date_of_birth, password,
                passwordConfirm);

        errorMessageField = new Span();

        submitButton = new Button("Sign up");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(title, name, email, emailInfo, address, date_of_birth, password, passwordInfo,
                passwordConfirm, passwordConfirmInfo, errorMessageField,
                submitButton);

        setMaxWidth("500px");
        setResponsiveSteps(
                new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP));

        setColspan(title, 2);
        setColspan(name, 2);
        setColspan(email, 2);
        setColspan(emailInfo, 2);
        setColspan(address, 2);
        setColspan(date_of_birth, 2);
        setColspan(errorMessageField, 2);
        setColspan(submitButton, 2);
    }

    private void addFocusListeners() {

        email.addFocusListener(e -> emailInfo.setText("Enter a valid email address (example: name@surname.com)"));
        email.addBlurListener(e -> emailInfo.setText(""));

        password.addFocusListener(e -> passwordInfo.setText("Password must be at least 8 characters long and should consist of numbers and letters."));
        password.addBlurListener(e -> passwordInfo.setText(""));

        passwordConfirm.addFocusListener(e -> passwordConfirmInfo.setText("Re-enter the password to confirm."));
        passwordConfirm.addBlurListener(e -> passwordConfirmInfo.setText(""));
    }

    public PasswordField getPasswordField() { return password; }

    public PasswordField getPasswordConfirmField() { return passwordConfirm; }

    public Span getErrorMessageField() { return errorMessageField; }

    public Button getSubmitButton() { return submitButton; }

    public TextField getNameField() {return name;}

    public TextField getEmailField() {return email;}

    public TextField getAddressField() {return address;}

    public DatePicker getDateOfBirthField() {return date_of_birth;}

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }
}
