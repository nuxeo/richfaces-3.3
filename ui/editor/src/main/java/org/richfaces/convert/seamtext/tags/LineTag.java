package org.richfaces.convert.seamtext.tags;

class LineTag extends HtmlTag {
    private static final long serialVersionUID = 6972613670825989225L;

    private String startTag;
    protected boolean isHtml = false;
    
    protected LineTag(String name) {
        super(name);
    }

    public LineTag(String name, String startTag) {
        super(name);
        setStartTag(startTag);
    }

    @Override
    public String printStart() {
        return startTag;
    }

    @Override
    public String printPlain() {
        isHtml = true;
        return super.printPlain();
    }
    
    @Override
    protected void appendChildTag(StringBuilder res, HtmlTag child) {
        if (child instanceof LineTag) {
            res.append(((LineTag)child).printPlain());
        } else {
            res.append(child);
        }
    }

    @Override
    public String printEnd() {
        return "\n";
    }

    public String getStartTag() {
        return startTag;
    }

    public void setStartTag(String startTag) {
        this.startTag = startTag;
    }
}

