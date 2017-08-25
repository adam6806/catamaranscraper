package com.github.adam6806.catamaranscraper.boatsite;

import java.util.ArrayList;
import java.util.List;

public class BoatSiteFactory {

    private ExampleBoatSite exampleBoatSite;

    public static final String EXAMPLE = "example";

    public BoatSiteFactory() {
        exampleBoatSite = new ExampleBoatSite();
    }

    public BoatSite getBoatSite(String boatSite) {
        switch (boatSite) {
            case EXAMPLE: return exampleBoatSite;
            default: throw new UnsupportedOperationException("This boat site does not exist. Please use constants available in BoatSiteFactoryClass");
        }
    }

    public List<BoatSite> getBoatSites() {
        ArrayList<BoatSite> boatSites = new ArrayList<>();
        boatSites.add(exampleBoatSite);
        return boatSites;
    }
}
