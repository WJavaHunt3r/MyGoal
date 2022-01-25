package com.ktk.duka.mygoal.service.utils;

import com.vaadin.flow.i18n.I18NProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Component
@Slf4j
public class TranslationProvider implements I18NProvider {

    private static final Locale LOCALE_HU = new Locale("hu");
    private static final Locale LOCALE_EN = new Locale("en");
    private static final String BUNDLE_NAME = "messages";

    @Override
    public List<Locale> getProvidedLocales() {
        return List.of(LOCALE_HU, LOCALE_EN);
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
        try {
            return MessageFormat.format(bundle.getString(key), params);
        } catch (MissingResourceException | IllegalArgumentException exception) {
            log.warn("An error occured during key translation.");
        }
        return String.format("!{%s}!", key);
    }
}
