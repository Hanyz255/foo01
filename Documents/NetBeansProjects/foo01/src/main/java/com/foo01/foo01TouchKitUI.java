package com.foo01;

import com.foo01.ui.*;

import com.vaadin.addon.touchkit.annotations.CacheManifestEnabled;
import com.vaadin.addon.touchkit.annotations.OfflineModeEnabled;
import com.vaadin.addon.touchkit.ui.NavigationManager;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * The UI's "main" class
 */
@SuppressWarnings("serial")
@Widgetset("com.foo01.gwt.foo01WidgetSet")
@Theme("mytheme")
// Cache static application files so as the application can be started
// and run even when the network is down.
@CacheManifestEnabled
// Switch to the OfflineMode client UI when the server is unreachable
@OfflineModeEnabled
// Make the server retain UI state whenever the browser reloads the app
@PreserveOnRefresh
public class foo01TouchKitUI extends UI {
    
    public NavigationManager NAVIGATIONMANAGER;
    
    @Override
    protected void init(VaadinRequest request) {
        Page.getCurrent().setTitle("Erzeta");
        NAVIGATIONMANAGER = new NavigationManager();
        NAVIGATIONMANAGER.setCurrentComponent(new DashboardMainView());
        setContent(NAVIGATIONMANAGER);        
    }    
}

