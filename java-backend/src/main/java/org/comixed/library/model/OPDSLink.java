
package org.comixed.library.model;

public class OPDSLink
{
    private String type;

    private String rel;
    private String href;

    public OPDSLink(String type, String rel, String href)
    {
        this.type = type;
        this.rel = rel;
        this.href = href;
    }

    public String getHRef()
    {
        return this.href;
    }

    public String getRel()
    {
        return this.rel;
    }

    public String getType()
    {
        return this.type;
    }
}
