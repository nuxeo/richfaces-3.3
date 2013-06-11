package org.richfaces.convert.seamtext.tags;

class OrderedListTag extends UnorderedListTag {
    private static final long serialVersionUID = 1L;

    public OrderedListTag() {
        super(OL);
    }
    
    @Override
    protected void appendChildTag(StringBuilder res, HtmlTag child) {
        if (LI.equals(child.getName())) {
            res.append(TagFactory.SEAM_HASH).append(child.print());
        } else {
            res.append(child);
        }
    }
}
