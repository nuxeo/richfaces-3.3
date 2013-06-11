package org.richfaces.convert.seamtext.tags;

public class EmptyHtmlTag extends HtmlTag {

    private static final long serialVersionUID = 6885598850897971629L;

    EmptyHtmlTag(String name) {
        super(name);
    }
    
    @Override
    public String print() {
        final StringBuilder builder = new StringBuilder();
        builder.append('<').append(getName());

        if (!getAttributes().isEmpty()) {
            builder.append(' ').append(printAttributes());
        }
        builder.append("/>");

        return builder.toString();
    }

    @Override
    public String printPlain() {
        return print();
    }
}
