
package org.comixed.library.model;

import java.time.ZonedDateTime;

public class OPDSLink
{
    public OPDSLink(String type, String rel, String href)
    {
        this.type = type;
        this.rel = rel;
        this.href = href;
    }

    private String type;
    private String rel;
    private String href;

    public String getType()
    {
        return type;
    }

    public String getRel()
    {
        return rel;
    }

    public String getHRef()
    {
        return href;
    }
}
