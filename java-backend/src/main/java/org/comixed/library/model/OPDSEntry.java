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

public class OPDSEntry
{
    public String id;
    public String title;
    public ZonedDateTime updated;
    public List<OPDSLink> links;
    public String content;
    public List<String> authors;

    public OPDSEntry(String title, String content,
            List<String> authors, List<OPDSLink> links) {
        this.id = "urn:uuid:" + UUID.randomUUID();
        this.updated = ZonedDateTime.now().withFixedOffsetZone();

        this.title = title;
        this.content = content;
        this.authors = authors;
        this.links = links;
    }

    public OPDSEntry(Comic comic) {
        id = comic.getId().toString();
        if (comic.getCoverDate() != null) {
            updated = _convertDate(comic.getCoverDate());
        } else {
            updated = _convertDate(comic.getDateAdded());
        }
        title = comic.getSeries() + " " + comic.getVolume() + " " + comic.getIssueNumber();
        content = comic.getTitle() + " " + comic.getSummary();
        // TODO: we should add authors to comics
        authors = new ArrayList<String>();

        // FIXME: Is there some sort of router interface we can
        // use to build urls
        String urlPrefix = "/api/comics/" + comic.getId();
        String urlSafeFilename;
        try {
            urlSafeFilename = URLEncoder.encode(comic.getFilenameName(), StandardCharsets.UTF_8.toString());
        } catch (java.io.UnsupportedEncodingException ex) {
            urlSafeFilename = comic.getFilenameName();
        }

        links = Arrays.asList(
                new OPDSLink("image/jpeg", "http://opds-spec.org/image", urlPrefix + "/pages/0/content"),
                new OPDSLink("image/jpeg", "http://opds-spec.org/image/thumbnail",  urlPrefix + "/pages/0/content"),
                new OPDSLink("application/octet-stream", "http://opds-spec.org/acquisition",
                    urlPrefix + "/download/" + urlSafeFilename)
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

    public List<OPDSLink> getLinks() {
        return links;
    }

    public String getContent() {
        return content;
    }

    public List<String> getAuthors() {
        return authors;
    }

    private ZonedDateTime _convertDate(Date date)
    {
        // Yep, this is a bunch of ugly because Date is actually a java.sql.Date
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate()
            .atStartOfDay(ZoneId.systemDefault()).withFixedOffsetZone();
    }
}

