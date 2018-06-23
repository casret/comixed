
package org.comixed.library.model;

import java.time.ZonedDateTime;
import java.util.List;

public interface OPDSFeed
{
    public List<OPDSEntry> getEntries();

    public String getId();

    public List<OPDSLink> getLinks();

    public String getTitle();

    public ZonedDateTime getUpdated();
}
