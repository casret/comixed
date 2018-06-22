package org.comixed.library.model;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class OPDSAcquisitionFeed implements OPDSFeed {
    private String id;
    private String title;
    private ZonedDateTime updated;
    private List<OPDSEntry> entries;
    private List<OPDSLink> links;

    public OPDSAcquisitionFeed(String selfUrl, String title, Iterable<Comic> comics)
    {
        this.id = "urn:uuid:" + UUID.randomUUID();
        this.entries = StreamSupport.stream(comics.spliterator(), true)
            .map(comic -> new OPDSEntry(comic))
            .collect(Collectors.toList());
        this.title = title;
        this.updated = ZonedDateTime.now().withFixedOffsetZone();
        this.links = Arrays.asList(
                new OPDSLink("application/atom+xml; profile=opds-catalog; kind=acquisition", "self", selfUrl),
                new OPDSLink("application/atom+xml; profile=opds-catalog; kind=navigation", "start", "/api/opds")
                );
    }

    public String getId()
    {
        return id;
    }

    public List<OPDSEntry> getEntries()
    {
        return entries;
    }

    public List<OPDSLink> getLinks()
    {
        return links;
    }

    public ZonedDateTime getUpdated()
    {
        return updated;
    }

    public String getTitle()
    {
        return title;
    }
}
