package com.ktk.duka.mygoal.views.utils;

import com.ktk.duka.mygoal.service.utils.CrudEntity;
import com.ktk.duka.mygoal.service.utils.CrudService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.*;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.PageableDataProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CrudComponent<F, E extends CrudEntity<E, ?>> extends VerticalLayout implements InitializingBean {

    @Getter(AccessLevel.PROTECTED)
    private CrudService<F, E, ?> crudService;
    private Grid<E> grid;
    private ConfigurableFilterDataProvider<E, Void, F> gridDataProvider;

    public CrudComponent(CrudService<F, E, ?> crudService) {
        this.crudService = crudService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setSpacing(true);
        this.setWidthFull();
        this.setHeightFull();
        this.setMargin(false);
        this.setPadding(false);

        add(setPageTitle(), createGrid());

    }

    private Component setPageTitle() {
        return ComponentUtils.buildPageTitle(UI.getCurrent().getTranslation(crudService.getEntityClass().getName()));
    }

    private Grid<E> createGrid() {
        grid = new Grid<>(crudService.getEntityClass(), false);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_COLUMN_BORDERS);
        grid.setSizeFull();
        grid.setPageSize(50);
        grid.setMultiSort(true);
        grid.setVerticalScrollingEnabled(true);
        grid.setDataProvider(gridDataProvider = createDataProvider().withConfigurableFilter());

        setupGridColumns(grid);

        grid.getColumns().forEach(column -> column.setHeader(UI.getCurrent().getTranslation(String.join(".", crudService.getEntityClass().getName(), column.getKey()))));
        return grid;
    }

    protected abstract void setupGridColumns(Grid<E> grid);

    private DataProvider<E, F> createDataProvider() {
        return new PageableDataProvider<>() {
            @Override
            protected Page<E> fetchFromBackEnd(Query<E, F> query, Pageable pageable) {
                return crudService.fetch(pageable);
            }

            @Override
            protected List<QuerySortOrder> getDefaultSortOrders() {
                return Collections.emptyList();
            }

            @Override
            protected int sizeInBackEnd(Query<E, F> query) {
                return (int) crudService.count();
            }
        };
    }

    protected void setDefaultSorting(String... propertyName) {
        List<GridSortOrder<E>> sort = Arrays.stream(propertyName).map(name -> new GridSortOrder<E>(grid.getColumnByKey(name), SortDirection.ASCENDING)).collect(Collectors.toList());
        grid.sort(sort);
    }

    private Component setupFilter() {

        return null;
    }
}
