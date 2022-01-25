package com.ktk.duka.mygoal.config;

import com.ktk.duka.mygoal.service.utils.TranslationProvider;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Getter
@Setter
@ConfigurationProperties(prefix = "business.context")
public class BusinessContextConfiguration {

    @Autowired
    private TranslationProvider translationProvider;

    private String environment;
    private String logoPath;
    private String defaultLocaleCode;
    private String defaultDateTimePattern;
    private String appName;

    public Optional<Locale> getDefaultLocale() {
        return translationProvider.getProvidedLocales().stream().filter(locale -> locale.getLanguage().equals(defaultLocaleCode))
                .findFirst();
    }
}


