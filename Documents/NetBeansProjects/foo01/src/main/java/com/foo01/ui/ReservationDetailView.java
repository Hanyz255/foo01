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
import com.vaadin.addon.touchkit.ui.Switch;
import com.vaadin.addon.touchkit.ui.Toolbar;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Property;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
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
        
        
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setStyleName("buttonToolBarLayout");
        buttonsLayout.setWidth("100%");

        Button deleteButton = new Button();
        deleteButton.setCaption("Smazat");
        deleteButton.setWidth(null);
        buttonsLayout.addComponent(deleteButton);
        //buttonsLayout.setComponentAlignment(deleteButton, Alignment.MIDDLE_LEFT);

        Label plug = new Label();
        buttonsLayout.addComponent(plug);        
        
        // Add userinfo
        Button saveButton = new Button();
        saveButton.setCaption("Uložit");
        saveButton.setWidth(null);
        buttonsLayout.addComponent(saveButton);
        buttonsLayout.setExpandRatio(plug, 1.0f);
        //buttonsLayout.setComponentAlignment(saveButton, Alignment.MIDDLE_RIGHT);
        
        
//        Toolbar tb = new Toolbar();
//        setToolbar(tb);
             
        
        
        
//        HorizontalButtonGroup buttons = new HorizontalButtonGroup();
//        buttons.addComponent(new Button("OK"));
//        buttons.addComponent(new Button("Cancel"));
//        content.addComponent(buttons);

        List<Source> sourcesList = MockSource.mockSources();
//        ComboBox combobox = new ComboBox("Zdroje");
//        combobox.setFilteringMode(FilteringMode.OFF);
//        combobox.setInputPrompt("neco vyber");
//        combobox.setTextInputAllowed(false);
//        combobox.setItemCaptionMode(ItemCaptionMode.INDEX);
//        combobox.setIcon(FontAwesome.ANGLE_DOWN);
//        combobox.addItems(sourcesList);
//        content.addComponent(combobox);
        
        HorizontalLayout sourceAndNameLayout = new HorizontalLayout();
        sourceAndNameLayout.setWidth("100%");
        
        NativeSelect select = new NativeSelect();
        select.setNullSelectionAllowed(false);
        System.out.println(Page.getCurrent().getBrowserWindowWidth());
        String width = Page.getCurrent().getBrowserWindowWidth()/2.5 +"px";
        select.setWidth(width);
        select.addItems(sourcesList);
//        Collection<?> list;
//        list = select.getItemIds();
        for (Source s : sourcesList) {
            if (reservation.getSource().equals(s)) {
                select.select(s);
                break;
            }
        }
        sourceAndNameLayout.addComponent(select);
        
        Label plug2 = new Label();
        sourceAndNameLayout.addComponent(plug2);

        Label name = new Label(reservation.getUser());
        name.setWidth(null);        
        sourceAndNameLayout.addComponent(name);      
        sourceAndNameLayout.setExpandRatio(plug2, 1.0f);
        
        DatePicker dateFrom = new DatePicker();
        dateFrom.setValue(reservation.getBeginning());
        dateFrom.setUseNative(true);
        dateFrom.setResolution(Resolution.TIME);
        dateFrom.setWidth("100%");

        DatePicker dateTo = new DatePicker();
        dateTo.setValue(reservation.getEnding());
        dateTo.setUseNative(true);
        dateTo.setResolution(Resolution.TIME);
        dateTo.setWidth("100%");

        
        HorizontalLayout sliderLayout = new HorizontalLayout();
        sliderLayout.setWidth("100%");
        sliderLayout.setSpacing(true);

        Label sliderCaption = new Label("Počet");
        sliderCaption.setWidth(null); 
        sliderLayout.addComponent(sliderCaption);
        
        final Label horvalur = new Label();
        horvalur.setWidth(null); 
        sliderLayout.addComponent(horvalur); 
        
        final Slider horslider = new Slider(1, 10);
        horslider.setOrientation(SliderOrientation.HORIZONTAL);
        horslider.setValue(Double.valueOf(1));
        horslider.getState();
        horslider.getValue();
        horslider.setImmediate(true);
        horslider.setWidth("100%");
        sliderLayout.addComponent(horslider);  
        sliderLayout.setExpandRatio(horslider, 1.0f);
        
        horvalur.setValue(String.valueOf(horslider.getValue()));

        horslider.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                double value = horslider.getValue();

                horvalur.setValue(String.valueOf(value));
            }
        });
        
        HorizontalLayout switchLayout = new HorizontalLayout();
        switchLayout.addComponent(new Label("Schváleno:"));
        switchLayout.setSpacing(true);
        Switch checkbox = new Switch(null, true);
        switchLayout.addComponent(checkbox);
        
        TextArea description = new TextArea();
        description.setWidth("100%");
        description.setImmediate(false);
        description.setCaption("Popis:");
        description.setValue(reservation.getDescription());
        //description.setRequired(true);
        description.setRequiredError("Popis musí být zadán!");
        description.setNullRepresentation("");
        description.setReadOnly(true);
//        description.setRows(0);

        final VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        content.setSpacing(true);
        content.setStyleName(width);
        content.addComponent(buttonsLayout);
        content.addComponent(sourceAndNameLayout);
//        content.addComponent(select);
//        content.addComponent(name);
        content.addComponent(dateFrom);
        content.addComponent(dateTo);         
        
        content.addComponent(sliderLayout);
//        content.addComponent(horslider);
//        content.addComponent(horvalur);
//        content.addComponent(checkbox);
        content.addComponent(switchLayout);
        content.addComponent(description);
        

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
