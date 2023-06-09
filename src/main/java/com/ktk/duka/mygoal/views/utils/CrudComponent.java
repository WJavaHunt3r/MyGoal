package com.ktk.duka.mygoal.views.utils;

import com.ktk.duka.mygoal.enums.Role;
import com.ktk.duka.mygoal.service.utils.CrudEntity;
import com.ktk.duka.mygoal.service.utils.CrudService;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.*;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.PageableDataProvider;

import javax.swing.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class CrudComponent<F, E extends CrudEntity<E, ?>> extends VerticalLayout implements InitializingBean {

    @Getter(AccessLevel.PROTECTED)
    private CrudService<F, E, ?> crudService;
    @Getter(AccessLevel.PROTECTED)
    private Binder<F> filterFormBinder;
    @Getter(AccessLevel.PROTECTED)
    private Binder<E> formFieldBinder;
    private Grid<E> grid;
    private CrudEntity<E, ?> selected;
    private ConfigurableFilterDataProvider<E, Void, F> gridDataProvider;

    public CrudComponent(CrudService<F, E, ?> crudService) {
        this.crudService = crudService;
        this.filterFormBinder = new Binder<F>(crudService.getFilterClass());
        this.filterFormBinder.setBean(crudService.createFilter());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setSpacing(true);
        this.setWidthFull();
        this.setHeightFull();
        this.setMargin(false);
        this.setPadding(false);

        add(setPageTitle(), setupFilter(), createGrid());
    }

    private Component setPageTitle() {
        return ComponentUtils.buildPageTitle(UI.getCurrent().getTranslation(crudService.getEntityClass().getName()));
    }

    private Grid<E> createGrid() {
        grid = new Grid<>(crudService.getEntityClass(), false);
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT, GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);
        grid.setSizeFull();
        grid.setPageSize(50);
        grid.setMultiSort(true);
        grid.setVerticalScrollingEnabled(true);
        grid.setDataProvider(gridDataProvider = createDataProvider().withConfigurableFilter());
        setupGridColumns(grid);
        Object bean = (F) filterFormBinder.getBean();
        gridDataProvider.setFilter(filterFormBinder.getBean());
        grid.getColumns().forEach(column -> column.setHeader(UI.getCurrent().getTranslation(String.join(".", crudService.getEntityClass().getName(), column.getKey()))));
        return grid;
    }

    protected abstract void setupGridColumns(Grid<E> grid);

    private DataProvider<E, F> createDataProvider() {
        return new PageableDataProvider<>() {
            @Override
            protected Page<E> fetchFromBackEnd(Query<E, F> query, Pageable pageable) {
                return crudService.fetchByQuery(query.getFilter().orElse(crudService.createFilter()), pageable);
            }

            @Override
            protected List<QuerySortOrder> getDefaultSortOrders() {
                return Collections.emptyList();
            }

            @Override
            protected int sizeInBackEnd(Query<E, F> query) {
                return (int) crudService.countByQuery(query.getFilter().orElse(crudService.createFilter()));
            }
        };
    }

    protected void setDefaultSorting(String... propertyName) {
        List<GridSortOrder<E>> sort = Arrays.stream(propertyName).map(name -> new GridSortOrder<E>(grid.getColumnByKey(name), SortDirection.ASCENDING)).collect(Collectors.toList());
        grid.sort(sort);
    }

    private Component setupFilter() {


        bindFields(this::createFilterFieldFor, filterFormBinder);
        List<String> fields = setupFilterFields();

        FormLayout layout = new FormLayout();
        VerticalLayout filterLayout = new VerticalLayout(layout);
        fields.forEach(propId -> filterFormBinder.getBinding(propId).map(binding -> (Component) binding.getField()).ifPresent(layout::add));

        layout.add(createCrudAction(true, buttonClickEvent -> refresh()));

        return filterLayout;
    }

    public void bindFields(Function<String, HasValue<?, ?>> fieldBuilder, Binder<?> binder) {
        List.of(BeanUtils.getPropertyDescriptors(binder.getBean().getClass())).forEach(descriptor -> {
            HasValue<?, ?> field = fieldBuilder.apply(descriptor.getName());
            if (field instanceof Component) {
                binder.bind(field, descriptor.getName());
            }
        });
    }

    protected void refresh() {
        if (filterFormBinder != null && filterFormBinder.isValid()) {
            gridDataProvider.setFilter(filterFormBinder.getBean());
        }
        grid.getSelectionModel().deselectAll();
    }

    protected abstract List<String> setupFilterFields();

    protected HasValue<?, ?> createFilterFieldFor(String property){
        return null;
    }

    protected DatePicker buildDateField(String property){
        DatePicker datePicker = new DatePicker(ComponentUtils.getTranslation(String.join(".", crudService.getEntityClass().getName(), property)));
        DatePicker.DatePickerI18n datePickerI18n = new DatePicker.DatePickerI18n();
        datePickerI18n.setMonthNames(ComponentUtils.HU_MONTH_NAMES);
        datePickerI18n.setWeekdays(ComponentUtils.HU_WEEK_DAYS);
        datePickerI18n.setWeekdaysShort(ComponentUtils.HU_WEEK_DAY_SHORTS);
        datePickerI18n.setWeek(ComponentUtils.HU_WEEK);
        datePickerI18n.setToday(ComponentUtils.HU_TODAY);
        datePickerI18n.setCancel(ComponentUtils.HU_CANCEL);
        datePickerI18n.setDateFormat("yyyy-MM-dd");
        datePicker.setI18n(datePickerI18n);
        datePicker.setInitialPosition(LocalDate.now());
        datePicker.setValue(LocalDate.now());
        return datePicker;
    }

    protected ComboBox<Role> buildUserSelectField(String property){
        ComboBox<Role> comboBox =  new ComboBox<Role>(ComponentUtils.getTranslation(String.join(".", crudService.getEntityClass().getName(), property)));
        comboBox.setItems(Role.values());
        comboBox.setClearButtonVisible(true);
        return comboBox;
    }

    protected ComboBox<Boolean> buildYesNoField(String property){
        ComboBox<Boolean> comboBox = new ComboBox<>(ComponentUtils.getTranslation(String.join(".", crudService.getEntityClass().getName(), property)));
        comboBox.setItems(true, false);
        comboBox.setItemLabelGenerator(bool -> {return ComponentUtils.getTranslation(bool ? "base_text_yes" : "base_text_no" );});
        comboBox.setClearButtonVisible(true);
        return comboBox;
    }

    public static Button createCrudAction(boolean enabled, ComponentEventListener<ClickEvent<Button>> listener) {
        Button button = new Button();
        button.setEnabled(enabled);
        button.setIcon(new Icon(VaadinIcon.REFRESH));
        button.addClickListener(listener);
        return button;
    }
}
