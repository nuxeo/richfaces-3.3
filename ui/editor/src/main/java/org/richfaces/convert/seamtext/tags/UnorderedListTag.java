package org.richfaces.convert.seamtext.tags;

class UnorderedListTag extends LineTag {
    private static final long serialVersionUID = 1L;

    public UnorderedListTag() {
        super(UL);
    }
    
    protected UnorderedListTag(String name) {
        super(name);
    }
    
    @Override
    public String printStart() {
        return "";
    }
    
    @Override
    public String printEnd() {
        return "\n";
    }
    
    
    
    @Override
    protected void appendChildTag(StringBuilder res, HtmlTag child) {
        if (LI.equals(child.getName()) && !isHtml) {
            res.append(TagFactory.SEAM_EQ).append(child.print());
        } else {
            super.appendChildTag(res, child);
        }
    }

    @Override
    public void appendBody(String str) {
        // Do nothing.
    }
}
