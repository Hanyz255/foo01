/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foo01.ui;

import com.foo01.mock.MockSource;
import com.foo01.mock.Reservation;
import com.foo01.mock.Source;
import com.vaadin.addon.touchkit.gwt.client.vcom.DatePickerState;
import com.vaadin.addon.touchkit.gwt.client.vcom.DatePickerState.Resolution;
import com.vaadin.addon.touchkit.ui.DatePicker;
import com.vaadin.addon.touchkit.ui.HorizontalButtonGroup;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.Toolbar;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Property;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.Field.ValueChangeEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Select;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Jan Konečný
 */
public class ReservationDetailView extends NavigationView {

    private final Reservation reservation;

    @Override
    protected final void onBecomingVisible() {
        getNavigationBar().setCaption("Detail Rezervace");
        Toolbar tb = new Toolbar();
        tb.setCaption("Hello");
        tb.addComponent(new Button("testujeme"));
        getNavigationManager().addComponent(tb);
        
        final VerticalComponentGroup content = new VerticalComponentGroup();
        content.addComponent(tb);
        HorizontalButtonGroup buttons = new HorizontalButtonGroup();
        buttons.addComponent(new Button("OK"));
        buttons.addComponent(new Button("Cancel"));
        content.addComponent(buttons);

        List<Source> sourcesList = MockSource.mockSources();
//        ComboBox combobox = new ComboBox("Zdroje");
//        combobox.setFilteringMode(FilteringMode.OFF);
//        combobox.setInputPrompt("neco vyber");
//        combobox.setTextInputAllowed(false);
//        combobox.setItemCaptionMode(ItemCaptionMode.INDEX);
//        combobox.setIcon(FontAwesome.ANGLE_DOWN);
//        combobox.addItems(sourcesList);
//        content.addComponent(combobox);
        NativeSelect select = new NativeSelect("Select");
        select.setNullSelectionAllowed(false);
        select.addItems(sourcesList);
//        Collection<?> list;
//        list = select.getItemIds();
        for (Source s : sourcesList) {
            if (reservation.getSource().equals(s)) {
                select.select(s);
                break;
            }
        }

        Label name = new Label(reservation.getUser());

        DatePicker dateFrom = new DatePicker("od");
        dateFrom.setValue(reservation.getBeginning());
        dateFrom.setUseNative(true);
        dateFrom.setResolution(Resolution.TIME);

        DatePicker dateTo = new DatePicker("do");
        dateTo.setValue(reservation.getEnding());
        dateTo.setUseNative(true);
        dateTo.setResolution(Resolution.TIME);

        final Slider horslider = new Slider("Pocet", 1, 10);
        horslider.setOrientation(SliderOrientation.HORIZONTAL);
        horslider.setValue(Double.valueOf(1));
        horslider.getState();
        horslider.getValue();
        horslider.setImmediate(true);

        final Label horvalur = new Label();
        horvalur.setValue(String.valueOf(horslider.getValue()));

        horslider.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                double value = horslider.getValue();

                horvalur.setValue(String.valueOf(value));
            }
        });

        CheckBox checkbox = new CheckBox("Schvaleno", true);

        TextArea description = new TextArea();
        description.setWidth("100%");
        description.setImmediate(false);
        description.setCaption("Popis");
        description.setValue(reservation.getDescription());
        //description.setRequired(true);
        description.setRequiredError("Popis musí být zadán!");
        description.setNullRepresentation("");
        description.setReadOnly(true);
//        description.setRows(0);
        HorizontalLayout temp = new HorizontalLayout();
        temp.setWidth("100%");
        temp.addComponent(description);

        content.addComponent(select);
        content.addComponent(name);
        content.addComponent(dateFrom);
        content.addComponent(dateTo);
        content.addComponent(horslider);
        content.addComponent(horvalur);
        content.addComponent(checkbox);
        content.addComponent(temp);

        setContent(content);
    }

    /**
     *
     * @param r
     */
    public ReservationDetailView(Reservation r) {
        reservation = r;
    }

}
