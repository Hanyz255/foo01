/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foo01.components;

import com.foo01.foo01TouchKitUI;
import com.foo01.mock.Reservation;
import com.foo01.ui.FormView;
import com.foo01.ui.ReservationDetailView;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 *
 * @author konecny
 */
public class ReservationComponent extends HorizontalLayout {

    public static Component build(final Reservation r) {
        final HorizontalLayout reservationComponentLayout = new HorizontalLayout();

        final VerticalLayout datesLayout = new VerticalLayout();
        Label beginningDate = new Label((new SimpleDateFormat("E dd.MM.",Locale.getDefault()))
                .format(r.getBeginning().getTime()));
        Label endingDate = new Label((new SimpleDateFormat("E dd.MM.",Locale.getDefault()))
                .format(r.getEnding().getTime()));
        beginningDate.addStyleName("datelabel");
        endingDate.addStyleName("datelabel");
        datesLayout.addComponent(beginningDate);
        datesLayout.addComponent(endingDate);
        datesLayout.setSpacing(true);
        reservationComponentLayout.addComponent(datesLayout);

        Label reservationInfoLabel = new Label(r.getUser() + "</br></br>" + r.getDescription(), ContentMode.HTML);
        reservationInfoLabel.addStyleName("informationlabel");
        reservationComponentLayout.addComponent(reservationInfoLabel);

        Label rightArrowIconLabel = new Label("</br>" + FontAwesome.CHEVRON_RIGHT.getHtml(), ContentMode.HTML);
        rightArrowIconLabel.addStyleName("rightArrowIconLabel");
        reservationComponentLayout.addComponent(rightArrowIconLabel);

        reservationComponentLayout.setSpacing(true);
        reservationComponentLayout.setWidth("100%");
        datesLayout.setWidth("85px");
        reservationInfoLabel.setSizeFull();
        rightArrowIconLabel.setHeight("100%");
        rightArrowIconLabel.setWidth("20px");
        reservationComponentLayout.setExpandRatio(reservationInfoLabel, 1.0f);

        reservationComponentLayout.addLayoutClickListener(new LayoutClickListener() {
            @Override
            public void layoutClick(LayoutClickEvent event) {
                //System.out.println(event.getClickedComponent().getParent().getId());
                ((foo01TouchKitUI) UI.getCurrent()).NAVIGATIONMANAGER
                        .navigateTo(new ReservationDetailView(r));                
            }
        });

        return reservationComponentLayout;
    }
}
