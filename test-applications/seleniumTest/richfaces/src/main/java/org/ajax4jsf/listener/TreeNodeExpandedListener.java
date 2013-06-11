package org.ajax4jsf.listener;

import javax.faces.event.AbortProcessingException;

import org.ajax4jsf.autotest.bean.AutoTestBean;
import org.ajax4jsf.util.FacesUtils;
import org.richfaces.event.NodeExpandedEvent;
import org.richfaces.event.NodeExpandedListener;

public class TreeNodeExpandedListener implements NodeExpandedListener {

    public static final String NE_LISTENER = "NELISTENER";

    /**
     * @see NodeExpandedListener#processExpansion(NodeExpandedEvent)
     */
    public void processExpansion(NodeExpandedEvent nodeExpandedEvent) throws AbortProcessingException {
        AutoTestBean bean = (AutoTestBean) FacesUtils.getFacesBean(AutoTestBean.AUTOTEST_BEAN_NAME);
        if (bean != null) {
            bean.addMessage(NE_LISTENER);
        }
    }

}
