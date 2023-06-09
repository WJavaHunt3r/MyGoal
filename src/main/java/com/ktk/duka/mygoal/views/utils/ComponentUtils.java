package com.ktk.duka.mygoal.views.utils;

import com.ktk.duka.mygoal.views.login.LoginView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Collections;
import java.util.List;

public class ComponentUtils {

    static List<String> HU_MONTH_NAMES = List.of(
            "Január",
            "Február",
            "Március",
            "Április",
            "Május",
            "Június",
            "Július",
            "Augusztus",
            "Szeptember",
            "Október",
            "November",
            "December"
            );

    static List<String> HU_WEEK_DAYS = List.of("Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat", "Vasárnap");
    static List<String> HU_WEEK_DAY_SHORTS = List.of("H", "K", "Sze", "Cs", "P", "Szo", "V");
    static String HU_WEEK = "Hét";
    static String HU_TODAY = "Ma";
    static String HU_CANCEL = "Mégsem";

    public static LoginI18n loginFormTranslationProvider(){
        LoginI18n translation = LoginI18n.createDefault();
        translation.setHeader(new LoginI18n.Header());

        translation.getHeader().setTitle(translateLoginForm("headerTitle"));
        translation.getHeader().setDescription(translateLoginForm("headerDescription"));
        translation.getForm().setTitle(translateLoginForm("title"));
        translation.getForm().setPassword(translateLoginForm("password"));
        translation.getForm().setForgotPassword(translateLoginForm("forgotPassword"));
        translation.getForm().setUsername(translateLoginForm("username"));
        translation.getForm().setSubmit(translateLoginForm("submit"));
        translation.getErrorMessage().setMessage(translateLoginForm("errorMessageTitle"));
        translation.getErrorMessage().setTitle(translateLoginForm("errorMessageDescription"));
        translation.setAdditionalInformation("");
        return translation;
    }

    private static String translateLoginForm(String captionIdPostfix) {
        return getTranslation(String.join(".", LoginView.class.getName(), captionIdPostfix));
    }

    public static Component primaryIcon(Icon icon) {
        icon.setColor("var(--lumo-primary-color)");
        HorizontalLayout layout = new HorizontalLayout(icon);
        layout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        return layout;
    }

    public static Icon contextMenuIcon(Icon icon) {
        icon.getStyle().set("color", "var(--lumo-primary-color)");
        icon.getStyle().set("width", "18px");
        icon.getStyle().set("height", "18px");
        return icon;
    }

    public static Component buildPageTitle(String title) {
        H3 text = new H3(title);
        text.getStyle().set("margin", "0rem");

        VerticalLayout layout = new VerticalLayout(text);
        layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.setPadding(false);

        return layout;
    }

    public static String getTranslation(String messageCode){
        return UI.getCurrent().getTranslation(messageCode);
    }
}
