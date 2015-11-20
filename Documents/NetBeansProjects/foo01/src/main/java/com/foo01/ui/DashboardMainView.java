package com.foo01.ui;

import com.foo01.components.ReservationComponent;
import com.foo01.mock.MockReservation;
import com.foo01.mock.MockSource;
import com.foo01.mock.Reservation;
import com.foo01.mock.Source;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import java.util.List;

/**
 *
 * @author konecny
 */
@SuppressWarnings("serial")
public class DashboardMainView extends NavigationView {

    /*
     public DashboardMainView() {
     super("", new VerticalLayout());
     }*/
    public DashboardMainView() {
        getNavigationBar().setCaption("Dashboard");
        //setCaption("Dashboard");

        final VerticalLayout content = new VerticalLayout();
        content.setMargin(true); // Enables margins around whole component (layout)
        content.setSpacing(true); // Enables spacing between components within the layout
        //content.setSizeFull(); // Use entire window

        restoreLeftNavbarMenuButton();

        List<Source> sourcesList = MockSource.mockSources();
        for (Source s : sourcesList) {
            List<Reservation> reservationList = MockReservation.getReservationForSource(s);
            if (!reservationList.isEmpty()) {
                Label sourceDescriptionLabel = new Label(s.getName());
                sourceDescriptionLabel.addStyleName("dashboardLabel");
                content.addComponent(sourceDescriptionLabel);
            }
            for (Reservation r : reservationList) {
                content.addComponent(ReservationComponent.build(r));
            }
        }

        Button addButton = new Button();
        addButton.setIcon(FontAwesome.PLUS);
        addButton.setWidth("42px");
        addButton.setHeight("42px");
        addButton.addStyleName("float-button");
        addButton.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                getNavigationManager().navigateTo(new FormView("Plus button form"));
            }
        });
        content.addComponent(addButton);

        setContent(content);
    }

    private void restoreLeftNavbarMenuButton() {
        Button leftNavbarMenuButton = new Button("");
        leftNavbarMenuButton.setIcon(FontAwesome.BARS, "Menu");
        leftNavbarMenuButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Menu button clicked", Notification.Type.HUMANIZED_MESSAGE);
            }
        });
        getNavigationBar().setLeftComponent(leftNavbarMenuButton);
    }
;
}
