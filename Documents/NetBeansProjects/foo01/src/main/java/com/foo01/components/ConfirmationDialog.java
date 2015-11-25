package com.foo01.components;

import com.foo01.foo01TouchKitUI;
import com.vaadin.addon.touchkit.ui.HorizontalButtonGroup;
import com.vaadin.addon.touchkit.ui.Popover;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ConfirmationDialog extends Popover {
	
	public ConfirmationDialog(String caption, String text, ClickListener yesListener, final boolean navigateBack) {
		VerticalLayout layout = new VerticalLayout();
		Label label = new Label(caption);
		layout.addComponent(label);
		Label question = new Label(text);
		layout.addComponent(question);
		
		HorizontalButtonGroup group = new HorizontalButtonGroup();
		Button yesButton = new Button();
		yesButton.setCaption("Ano");
		yesButton.addClickListener(yesListener);
		yesButton.addClickListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				close();
			}
		});
		group.addComponent(yesButton);

		Button noButton = new Button();
		noButton.setCaption("Ne");
		noButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				if (navigateBack) {
					((foo01TouchKitUI)UI.getCurrent()).NAVIGATIONMANAGER.navigateBack();
				}
				close();
			}
		});
		group.addComponent(noButton);
		layout.addComponent(group);
		setContent(layout);
	}
}
