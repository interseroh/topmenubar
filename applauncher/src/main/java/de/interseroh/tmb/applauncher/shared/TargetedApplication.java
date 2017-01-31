package de.interseroh.tmb.applauncher.shared;

import java.io.Serializable;

/**
 * Created by alexadmin on 31.01.2017.
 */
public class TargetedApplication implements Serializable{

    private String name;
    private String url;
    private String iconUrl;

    public TargetedApplication() {
    }

    public TargetedApplication(String name, String url, String iconUrl) {
        this.name = name;
        this.url = url;
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
