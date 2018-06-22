
package org.comixed.library.model;

import java.time.ZonedDateTime;
import java.util.List;

public interface OPDSFeed
{
    public String getId();

    public String getTitle();

    public ZonedDateTime getUpdated();

    public List<OPDSLink> getLinks();

    public List<OPDSEntry> getEntries();
}
