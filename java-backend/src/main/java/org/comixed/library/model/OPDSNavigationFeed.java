package org.comixed.library.model;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class OPDSNavigationFeed implements OPDSFeed {
    private String id;
    private String title;
    private ZonedDateTime updated;
    private List<OPDSEntry> entries;
    private List<OPDSLink> links;

    public OPDSNavigationFeed() {
        this.id = "urn:uuid:" + UUID.randomUUID();
        this.title = "ComixEd catalog";
        this.updated = ZonedDateTime.now().withFixedOffsetZone();
        this.links = Arrays.asList(
                new OPDSLink("application/atom+xml; profile=opds-catalog; kind=navigation", "self", "/api/opds"),
                new OPDSLink("application/atom+xml; profile=opds-catalog; kind=navigation", "start", "/api/opds")
                );

        List<String> noAuthor = Arrays.asList("None");
        this.entries = Arrays.asList(
                new OPDSEntry("All comics", "All comics as a flat list", noAuthor, Arrays.asList(
                        new OPDSLink("application/atom+xml;profile=opds-catalog;kind=acquisition", "subsection", "/api/opds/all?mediaType=atom")
                        )),
                new OPDSEntry("Unread comics", "All unread comics sorted by recency", noAuthor, Arrays.asList(
                        new OPDSLink("application/atom+xml;profile=opds-catalog;kind=acquisition", "http://opds-spec.org/sort/new", "/api/opds/unread?mediaType=atom")
                        ))
                );

   }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ZonedDateTime getUpdated() {
        return updated;
    }

    public List<OPDSLink> getLinks()
    {
        return this.links;
    }

    public List<OPDSEntry> getEntries()
    {
        return this.entries;
    }
}
