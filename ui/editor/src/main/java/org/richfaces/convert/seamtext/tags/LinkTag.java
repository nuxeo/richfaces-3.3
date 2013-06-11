package org.richfaces.convert.seamtext.tags;

class LinkTag extends HtmlTag {
    
    private static final long serialVersionUID = -6944275891941825069L;
    private Boolean haveHtml = false;
    
    public LinkTag() {
        super(A);
    }
    
    @Override
    public void appendBody(HtmlTag tag) {
        super.appendBody(tag);
        
        haveHtml = true;
    }
    
    @Override
    public String print() {
        if (haveHtml) {
            return printPlain();
        }
        
        return super.print();
    }
    
    @Override
    public String printStart() {
        return "[";
    }

    @Override
    public String printEnd() {
        String s = getAttribute("href");
        if (s == null) {
            s = "/";
        }
        return "=>"+ s + ']';
    }

    @Override
    public String printStartSuffix() {
        return "";
    }
}
