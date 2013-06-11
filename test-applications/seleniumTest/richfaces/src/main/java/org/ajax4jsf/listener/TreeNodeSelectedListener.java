package org.ajax4jsf.listener;

import javax.faces.event.AbortProcessingException;

import org.ajax4jsf.autotest.bean.AutoTestBean;
import org.ajax4jsf.util.FacesUtils;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.event.NodeSelectedListener;

public class TreeNodeSelectedListener implements NodeSelectedListener {

    public static final String NS_LISTENER = "NSLISTENER";

    /**
     * @see NodeSelectedListener#processSelection(NodeSelectedEvent)
     */
    public void processSelection(NodeSelectedEvent nodeSelectedEvent) throws AbortProcessingException {
        AutoTestBean bean = (AutoTestBean) FacesUtils.getFacesBean(AutoTestBean.AUTOTEST_BEAN_NAME);
        if (bean != null) {
            bean.addMessage(NS_LISTENER);
        }
    }

}
