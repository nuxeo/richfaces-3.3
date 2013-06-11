package org.richfaces.convert.seamtext.tags;

import static org.richfaces.convert.seamtext.tags.HtmlTag.*;

import java.util.*;


/**
 * @user: akolonitsky
 * Date: Mar 25, 2009
 */
public class TagFactory {
    
    public static final String SEAM_PLUS = "+";
    public static final String SEAM_MONOSPACE = "|";
    public static final String SEAM_TWIDDLE = "~";
    public static final String SEAM_HASH = "#";
    public static final String SEAM_HAT = "^";
    public static final String SEAM_STAR = "*";
    public static final String SEAM_UNDERSCORE = "_";
    public static final String SEAM_EQ = "=";
    public static final String SEAM_BACKTICK = "`";
    public static final String SEAM_DOUBLEQUOTE = "\"";
    public static final String SEAM_LINK_START = "[";
    public static final String SEAM_LINK_END = "]";
    
    /**
     * Collection of special Seam Tags
     * */
    private static final Collection<HtmlTag> TAGS = Arrays.asList(
            new RootTag(),
            new EmptyHtmlTag(HR),
            new EmptyHtmlTag(BR),
            new EmptyHtmlTag(IMG),
            new EmptyHtmlTag(AREA),
            new EmptyHtmlTag(COL),
            
            new LineTag(H1, "+ "),
            new LineTag(H2, "++ "),
            new LineTag(H3, "+++ "),
            new LineTag(H4, "++++ "),
            new LineTag(LI, "") {
                
                @Override
                public String printBody() {
                    if (isBodyEmpty() && !isHtml) {
                        return " ";
                    }
                    
                    if (!body.isEmpty() && body.get(0) instanceof HtmlTag) {
                        body.add(0, " ");
                    }
                    
                    return super.printBody();
                }
            },
            
            new HtmlTag(P) {
                @Override
                public String printStart() {
                    return "";
                }

                @Override
                protected String printBody() {
                    while (isBreakLineChild(body.getLast())) {
                        body.removeLast();
                    }
                    
                    return super.printBody();
                }

                @Override
                public String printEnd() {
                    return "\n\n"; 
                }

                @Override
                public String printStartSuffix() {
                    return "";
                }
            }, 
            
            new FormattingTag(I,   SEAM_STAR),
            new FormattingTag(DEL, SEAM_TWIDDLE),
            new FormattingTag(SUP, SEAM_HAT),
            new FormattingTag(U,   SEAM_UNDERSCORE),
            new FormattingTag(TT,  SEAM_MONOSPACE) {

                @Override
                public boolean isFormating() {
                    return true;
                }
            },
            
//            getNewTagDefinition(PRE, SEAM_BACKTICK,    SEAM_BACKTICK),
            getNewTagDefinition(Q,   SEAM_DOUBLEQUOTE, SEAM_DOUBLEQUOTE),
            
            new LinkTag(),
            new UnorderedListTag(),
            new OrderedListTag()
    );
                
    private static final Map<String, HtmlTag> TAGS_MAP = new HashMap<String, HtmlTag>(TAGS.size());
    static {
        for (HtmlTag tag : TAGS) {
            TAGS_MAP.put(tag.getName(), tag);
        }
    }

    /**
     * Create subclass for Tag class with overrided printStart, printEnd methods
     * */
    private static HtmlTag getNewTagDefinition(final String tagName, final String startTag, final String endTag) {
        return new HtmlTag(tagName) {
            @Override
            public String printStart() {
                return startTag;
            }

            @Override
            public String printEnd() {
                return endTag; 
            }

            @Override
            public String printStartSuffix() {
                return "";
            }
        };
    }

    public HtmlTag getInstance(String tagName) {
        return getInstance(tagName, null); 
    }

    public HtmlTag getInstance(String tagName, Map<String, String> attr) {
        HtmlTag tag = TAGS_MAP.get(tagName);
        if (tag == null) {
            tag = new HtmlTag(tagName);
        } else {
            tag = tag.cloneTag();
        }
        tag.setAttributes(attr);

        return tag;
    }
}
