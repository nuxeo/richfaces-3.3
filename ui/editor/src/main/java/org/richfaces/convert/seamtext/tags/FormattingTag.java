package org.richfaces.convert.seamtext.tags;

class FormattingTag extends HtmlTag {
    private static final long serialVersionUID = 2688496380368279023L;

    private String seamTag;
    private Boolean formating;
    
    public FormattingTag(String tagName, String seamTag) {
        super(tagName);
        this.seamTag = seamTag;
    }

    @Override
    public void appendBody(HtmlTag tag) {
        super.appendBody(tag);
        
        if (!(tag instanceof FormattingTag)) {
            formating = false;
        }
    }

    @Override
    public String print() {
        if (isFormating()) {
            return super.print();
        }
        
        return printPlain();
    }
    
    @Override
    public String printEnd() {
        return seamTag;
    }

    @Override
    public String printStart() {
        return seamTag;
    }

    public boolean isFormating() {
        if (formating != null) {
            return formating;
        }

        for (Object child : body) {
            if (child instanceof FormattingTag) {
                FormattingTag formattingChild = (FormattingTag) child;
                if (!formattingChild.isFormating()) {
                    formating = false;
                    return false;
                }
            } else if (child instanceof HtmlTag) {
                throw new IllegalStateException(
                        "It is imposible, in this case we must have formating = false");
            }
        }
        
        formating = true;
        return true;
    }

    public String getSeamTag() {
        return seamTag;
    }

    public void setSeamTag(String seamTag) {
        this.seamTag = seamTag;
    }
}
