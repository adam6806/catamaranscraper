package com.github.adam6806.catamaranscraper.boatsite;

import java.util.ArrayList;
import java.util.List;

public class BoatSiteFactory {

    private TestBoatSite testBoatSite;
    private List<BoatSite> boatSites;

    public static final String TEST = "test";

    public BoatSiteFactory() {
        testBoatSite = new TestBoatSite();
        boatSites = new ArrayList<>();
        boatSites.add(testBoatSite);
    }

    public BoatSite getBoatSite(String boatSite) {
        switch (boatSite) {
            case TEST: return testBoatSite;
            default: throw new UnsupportedOperationException("This boat site does not exist. Please use constants available in BoatSiteFactoryClass");
        }
    }

    public List<BoatSite> getBoatSites() {
        return boatSites;
    }
}
