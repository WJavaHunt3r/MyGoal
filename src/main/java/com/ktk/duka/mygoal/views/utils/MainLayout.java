package com.ktk.duka.mygoal.views.utils;

import com.ktk.duka.mygoal.config.BusinessContextConfiguration;
import com.ktk.duka.mygoal.security.SecurityService;
import com.ktk.duka.mygoal.service.transaction.Transaction;
import com.ktk.duka.mygoal.service.transactionitems.TransactionItem;
import com.ktk.duka.mygoal.service.user.User;
import com.ktk.duka.mygoal.service.utils.TranslationProvider;
import com.ktk.duka.mygoal.views.login.LoginView;
import com.ktk.duka.mygoal.views.logout.LogoutView;
import com.ktk.duka.mygoal.views.status.StatusView;
import com.ktk.duka.mygoal.views.transactionitems.TransactionItemsView;
import com.ktk.duka.mygoal.views.transactions.TransactionView;
import com.ktk.duka.mygoal.views.user.UserView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * The main view is a top-level placeholder for other views.
 */
@SpringComponent
@PWA(name = "my-goal", shortName = "my-goal", enableInstallPrompt = false)
@Theme(themeFolder = "my-goal", variant = Lumo.LIGHT)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MainLayout extends AppLayout implements BeforeEnterObserver, PageConfigurator {

    private final Map<Class<? extends Component>, Tab> navigation = new LinkedHashMap<>();

    @Autowired
    private BusinessContextConfiguration config;
    @Autowired
    private TranslationProvider translationProvider;
    @Autowired
    private SecurityService securityService;

    private Tabs tabs;

    @PostConstruct
    public void init(){
        tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        initNavigation(List.of(
                MenuItem.builder()
                        .route(UserView.NAME)
                        .view(UserView.class)
                        .build(),
                MenuItem.builder()
                        .route(StatusView.NAME)
                        .view(StatusView.class)
                        .build(),
                MenuItem.builder()
                        .route(TransactionView.NAME)
                        .view(TransactionView.class)
                        .build(),
                MenuItem.builder()
                        .route(TransactionItemsView.NAME)
                        .view(TransactionItemsView.class)
                        .build(),
                MenuItem.builder()
                        .route(LogoutView.NAME)
                        .view(LogoutView.class)
                        .build()
        ));
        addToDrawer(tabs);
        addToNavbar(buildDrawerToggle(), buildHeaderLayout());
    }

    private Component buildHeaderLayout() {
        HorizontalLayout layout = new HorizontalLayout(
                new HorizontalLayout(buildUserText()),
                new HorizontalLayout(buildLogoutIcon())
        );

        layout.setSizeFull();
        layout.setMargin(false);
        layout.setPadding(true);
        layout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        return layout;
    }

    private void initNavigation(List<MenuItem> menuItems) {
        Optional.ofNullable(menuItems).orElse(Collections.emptyList()).forEach((menuItem) -> {

            RouterLink link = new RouterLink();
            link.setRoute(menuItem.getView());
            link.add(new Span(new Span(UI.getCurrent().getTranslation(String.join(".", MainLayout.class.getName(),
                    menuItem.getRoute())))));

            Tab tab = new Tab(link);
            tab.setVisible(securityService.accessGranted(menuItem.getView()));

            navigation.put(menuItem.getView(), tab);
            tabs.add(tab);
        });
    }

    private Component buildUserText() {
        H5 label = new H5(securityService.getAuthenticatedUser().map(User::getFullName).orElse(""));
        label.getStyle().set("margin-top", "4px");
        return label;
    }

    private Component buildLogoutIcon() {
        Icon logoutIcon = new Icon(VaadinIcon.SIGN_OUT);
        logoutIcon.addClickListener(iconClickEvent -> {
            UI.getCurrent().navigate(LoginView.class);
            securityService.logout();
        });
//        ComboBox<Locale> field = new ComboBox<>();
//        field.setRequired(true);
//        field.setItemLabelGenerator(Locale::getLanguage);
//        field.setItems(translationProvider.getProvidedLocales());
//        field.setValue(UI.getCurrent().getSession().getLocale());
//        field.setWidth("6rem");
//        field.addValueChangeListener(event -> {
//            if (event.getValue() != null) {
//                UI.getCurrent().getSession().setLocale(event.getValue());
//                UI.getCurrent().getPage().reload();
//            }
//        });

        return logoutIcon;
    }

    private Component buildDrawerToggle() {
        Image logo = new Image(config.getLogoPath(), config.getAppName());
        logo.setWidth("35px");
        logo.getStyle().set("margin", "0rem");
        logo.getStyle().set("padding", "0rem");

        DrawerToggle drawerToggle = new DrawerToggle();
        drawerToggle.setIcon(logo);

//        HorizontalLayout layout = new HorizontalLayout(drawerToggle);
//        layout.setWidthFull();
//        layout.setSpacing(true);
//        layout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        return drawerToggle;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();

    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        tabs.setSelectedTab(navigation.get(beforeEnterEvent.getNavigationTarget()));
    }

    @Override
    public void configurePage(InitialPageSettings initialPageSettings) {
        Map<String, String> attributes = new LinkedHashMap<>();
        attributes.put("rel", "shortcut icon");
        initialPageSettings.addLink("icons/favicon.ico", attributes);
    }

    @Builder
    private static class MenuItem {
        @Getter
        private String route;
        @Getter
        private Class<? extends Component> view;
    }
}
