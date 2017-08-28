package com.github.adam6806.catamaranscraper.boatsite;

import java.util.ArrayList;
import java.util.List;

public class BoatSiteFactory {

    public static final String YACHT_WORLD = "yachtWorld";
    private YachtWorldBoatSite yachtWorldBoatSite = new YachtWorldBoatSite();
    private List<BoatSite> boatSites = new ArrayList<>();

    public BoatSiteFactory() {
        boatSites.add(yachtWorldBoatSite);
    }

    public BoatSite getBoatSite(String boatSite) {
        switch (boatSite) {
            case YACHT_WORLD:
                return yachtWorldBoatSite;
            default:
                throw new UnsupportedOperationException("This boat site does not exist. Please use constants available in BoatSiteFactoryClass");
        }
    }

    public List<BoatSite> getBoatSites() {
        return boatSites;
    }
}
