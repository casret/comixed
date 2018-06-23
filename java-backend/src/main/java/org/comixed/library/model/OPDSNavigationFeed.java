
package org.comixed.library.model;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class OPDSNavigationFeed implements
                                OPDSFeed
{
    private String id;
    private String title;
    private ZonedDateTime updated;
    private List<OPDSEntry> entries;
    private List<OPDSLink> links;

    public OPDSNavigationFeed(String title)
    {
        this.title = title;
        this.id = "urn:uuid:" + UUID.randomUUID();
        this.updated = ZonedDateTime.now().withFixedOffsetZone();
        this.links = Arrays.asList(new OPDSLink("application/atom+xml; profile=opds-catalog; kind=navigation", "self",
                                                "/api/opds"),
                                   new OPDSLink("application/atom+xml; profile=opds-catalog; kind=navigation", "start",
                                                "/api/opds"));

        List<String> noAuthor = Arrays.asList("None");
        this.entries = Arrays.asList(new OPDSEntry("All comics", "All comics as a flat list", noAuthor,
                                                   Arrays.asList(new OPDSLink("application/atom+xml;profile=opds-catalog;kind=acquisition",
                                                                              "subsection",
                                                                              "/api/opds/all?mediaType=atom"))),
                                     new OPDSEntry("Unread comics", "All unread comics sorted by recency", noAuthor,
                                                   Arrays.asList(new OPDSLink("application/atom+xml;profile=opds-catalog;kind=acquisition",
                                                                              "http://opds-spec.org/sort/new",
                                                                              "/api/opds/unread?mediaType=atom"))));

    }

    @Override
    public List<OPDSEntry> getEntries()
    {
        return this.entries;
    }

    @Override
    public String getId()
    {
        return this.id;
    }

    @Override
    public List<OPDSLink> getLinks()
    {
        return this.links;
    }

    @Override
    public String getTitle()
    {
        return this.title;
    }

    @Override
    public ZonedDateTime getUpdated()
    {
        return this.updated;
    }

}
