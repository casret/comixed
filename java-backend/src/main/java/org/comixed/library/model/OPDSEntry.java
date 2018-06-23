
package org.comixed.library.model;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OPDSEntry
{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String id;
    private String title;
    private ZonedDateTime updated;
    private List<OPDSLink> links;
    private String content;
    private List<String> authors;

    public OPDSEntry(Comic comic)
    {
        this.id = comic.getId().toString();
        if (comic.getCoverDate() != null)
        {
            this.updated = this._convertDate(comic.getCoverDate());
        }
        else
        {
            this.updated = this._convertDate(comic.getDateAdded());
        }
        this.title = comic.getSeries() + " " + comic.getVolume() + " " + comic.getIssueNumber();
        this.content = comic.getTitle() + " " + comic.getSummary();
        // TODO: we should add authors to comics
        this.authors = new ArrayList<>();

        // FIXME: Is there some sort of router interface we can
        // use to build urls
        String urlPrefix = "/api/comics/" + comic.getId();
        String urlSafeFilename;
        try
        {
            urlSafeFilename = URLEncoder.encode(comic.getFilenameName(), StandardCharsets.UTF_8.toString());
        }
        catch (java.io.UnsupportedEncodingException ex)
        {
            urlSafeFilename = comic.getFilenameName();
        }

        this.links = Arrays.asList(new OPDSLink("image/jpeg", "http://opds-spec.org/image",
                                                urlPrefix + "/pages/0/content"),
                                   new OPDSLink("image/jpeg", "http://opds-spec.org/image/thumbnail",
                                                urlPrefix + "/pages/0/content"),
                                   new OPDSLink(comic.getArchiveType().getMediaType(),
                                                "http://opds-spec.org/acquisition",
                                                urlPrefix + "/download/" + urlSafeFilename));
    }

    public OPDSEntry(String title, String content, List<String> authors, List<OPDSLink> links)
    {
        this.id = "urn:uuid:" + UUID.randomUUID();
        this.updated = ZonedDateTime.now().withFixedOffsetZone();

        this.title = title;
        this.content = content;
        this.authors = authors;
        this.links = links;
    }

    private ZonedDateTime _convertDate(Date date)
    {
        // Yep, this is a bunch of ugly because Date is actually a java.sql.Date
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate()
                      .atStartOfDay(ZoneId.systemDefault()).withFixedOffsetZone();
    }

    public List<String> getAuthors()
    {
        return this.authors;
    }

    public String getContent()
    {
        return this.content;
    }

    public String getId()
    {
        return this.id;
    }

    public List<OPDSLink> getLinks()
    {
        return this.links;
    }

    public String getTitle()
    {
        return this.title;
    }

    public ZonedDateTime getUpdated()
    {
        return this.updated;
    }
}
