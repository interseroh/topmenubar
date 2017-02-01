package de.interseroh.tmb.applauncher.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import de.interseroh.tmb.applauncher.client.common.ErrorFormatter;

import javax.inject.Inject;

/**
 * Created by alexadmin on 30.01.2017.
 */
public class ApplauncherPanelView extends Composite {

    private EventBus eventBus;
    private ErrorFormatter errorFormatter;
    interface ApplauncherPanelViewUiBinder extends UiBinder<Widget, ApplauncherPanelView> {
    }

    private static ApplauncherPanelViewUiBinder uiBinder = GWT
            .create(ApplauncherPanelViewUiBinder.class);

    //@Inject
    public ApplauncherPanelView(){
        this.eventBus = eventBus;
        this.errorFormatter = errorFormatter;
        initWidget(uiBinder.createAndBindUi(this));
    }
}
