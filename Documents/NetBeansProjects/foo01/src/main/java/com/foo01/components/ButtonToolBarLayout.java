/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foo01.components;

import com.foo01.foo01TouchKitUI;
import com.foo01.ui.ReservationView;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

/**
 *
 * @author Jan Konečný
 */
public class ButtonToolBarLayout extends HorizontalLayout {

    private Button leftButton;
    private Button rightButton;

    public ButtonToolBarLayout(final Object caller) {
        setUpLayout();

        //caller = ReservationView
        if (ReservationView.class == caller.getClass()) {
            ReservationView rdv = (ReservationView) caller;
            rdv.getReservation().getSource();

            //button Smazat
            leftButton.setCaption("SMAZAT");
            leftButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {

                    ConfirmationDialog dialog = new ConfirmationDialog(
                            "Zamítnout rezervaci?",
                            "Opravdu zamítnout rezervaci?",
                            new Button.ClickListener() {

                                @Override
                                public void buttonClick(Button.ClickEvent bevent) {
                                    /*
                                     try {
                                     ((ErzetaUI) UI.getCurrent()).getReservationManager().validateReservation(((ErzetaUI) UI.getCurrent()).getUser(), (Reservation) object, false);
                                     } catch (BusinessException e) {
                                     // TODO Auto-generated catch block
                                     e.printStackTrace();
                                     }
                                     */
                                    ((foo01TouchKitUI) UI.getCurrent()).NAVIGATIONMANAGER.navigateBack();
                                    Notification.show("Rezervace byla zamítnuta");
                                }
                            }, false);
                    dialog.showRelativeTo(leftButton);
                    //Notification.show("DELETE!!!");
                }
            });

            rightButton.setCaption("ULOŽIT");

        } else {
            System.err.println("Wrong caller");
        }
    }

    private void setUpLayout() {
        this.setStyleName("buttonToolBarLayout");
        this.setWidth("100%");

        leftButton = new Button();
        rightButton = new Button();

        leftButton.setWidth(null);
        this.addComponent(leftButton);

        Label separator = new Label();
        this.addComponent(separator);

        rightButton.setWidth(null);
        this.addComponent(rightButton);
        this.setExpandRatio(separator, 1.0f);
    }
}
