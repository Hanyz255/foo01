/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foo01.ui;

import com.foo01.mock.MockSource;
import com.foo01.mock.Reservation;
import com.foo01.mock.Source;
import com.vaadin.addon.touchkit.ui.DatePicker;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Select;
import com.vaadin.ui.Slider;
import com.vaadin.ui.VerticalLayout;
import java.util.List;

/**
 *
 * @author Jan Konečný
 */
public class ReservationDetailView extends NavigationView {

    private final Reservation reservation;

    @Override
    protected final void onBecomingVisible() {
        getNavigationBar().setCaption(reservation.getDescription());
        final VerticalComponentGroup content = new VerticalComponentGroup();

        CheckBox checkbox = new CheckBox("Schvaleno", true);
        DatePicker datefield = new DatePicker("od");
        List<Source> sourcesList = MockSource.mockSources();
        ComboBox combobox = new ComboBox("Zdroje");
        combobox.setFilteringMode(FilteringMode.OFF);
        combobox.setInputPrompt("neco vyber");
        combobox.setTextInputAllowed(false);
        combobox.setItemCaptionMode(ItemCaptionMode.INDEX);
        combobox.setIcon(FontAwesome.ANGLE_DOWN);
        combobox.addItems(sourcesList);
        
        NativeSelect select = new NativeSelect("Select");
        select.setNullSelectionAllowed(false);
        select.removeAllItems();
        select.addItems("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune");
                
        Slider vertslider = new Slider("Pocet", 1, 10);
        vertslider.setOrientation(SliderOrientation.HORIZONTAL);
        vertslider.setValue(Double.valueOf(1));
        
        content.addComponent(checkbox);
        content.addComponent(datefield);
        content.addComponent(combobox);
        content.addComponent(vertslider);
        content.addComponent(select);
        
        setContent(content);
    }

    public ReservationDetailView(Reservation r) {
        reservation = r;
        this.onBecomingVisible();

    }

}
