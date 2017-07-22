package de.interseroh.tmb.profile.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

/**
 * @author Ingo Düppe (CROWDCODE)
 */
public class ProfileWebAppGinModul extends AbstractGinModule {
    @Override
    protected void configure() {
        // Bind the SimpleEventBus as Singleton
        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
    }
}
