package com.ktk.duka.mygoal.views.status;

import com.ktk.duka.mygoal.views.utils.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Route(value = StatusView.NAME, layout = MainLayout.class)
public class StatusView extends VerticalLayout implements HasDynamicTitle {
    public static final String NAME="status";
    public static final String MY_STATUS="myStatus";
    public static final String TEAM_STATUS="teamStatus";

    private VerticalLayout content;
    private UserStatusComponent userStatusComponent;
    private TeamStatusComponent teamStatusComponent;
    private Tab teamStatus;
    private Tab userStatus;

    public StatusView() {
        this.setup();
    }

    private void setup() {
        this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        buildTabs();
    }

    private void buildTabs() {
        userStatus = new Tab(
                VaadinIcon.BAR_CHART.create(),
                new Span(UI.getCurrent().getTranslation(String.join(".", StatusView.class.getName(), MY_STATUS)))
        );
        teamStatus = new Tab(
                VaadinIcon.GROUP.create(),
                new Span(UI.getCurrent().getTranslation(String.join(".", StatusView.class.getName(), TEAM_STATUS)))
        );

        for(Tab tab : List.of(userStatus, teamStatus)){
            tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        }
        content = new VerticalLayout();
        content.setSpacing(true);

        Tabs tabs = new Tabs(userStatus, teamStatus);
        tabs.addSelectedChangeListener(event -> setContent(event.getSelectedTab()));
        setContent(tabs.getSelectedTab());

        this.add(tabs, content);
    }

    private void setContent(Tab selectedTab) {
        content.removeAll();

        if(selectedTab.equals(userStatus)){
            add(userStatusComponent);
        }else if(selectedTab.equals(teamStatus)){
            add(teamStatusComponent);
        }
    }

    @Override
    public String getPageTitle() {
        return UI.getCurrent().getTranslation(String.join(".", MainLayout.class.getName(), NAME));
    }
}
