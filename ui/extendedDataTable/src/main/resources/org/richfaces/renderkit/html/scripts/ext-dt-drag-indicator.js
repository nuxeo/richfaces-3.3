ExtDragIndicator = {

    init: function(event) {
        var ieVersion = RichFaces.getIEVersion();
        ExtDragIndicator.isIE6 = (ieVersion && ieVersion < 7);
    },

    setContent: function(name, single, params) {
        Element.clearChildren(this);

        var p = DnD.getDnDDefaultParams(this);
        
        if (!p) {
            p = {};
        }

        if (params) {
            Object.extend(p, params);
        }
        
        if (!p['marker']) {
            if (p[name]) {
                p['marker'] = p[name];
            } else {
                p['marker'] = this.markers[name];
            }
        }

        var parts;

        if (single) {
            parts = this.indicatorTemplates['single'];
        } else {
            parts = this.indicatorTemplates['multi'];
        }
        
        new Insertion.Top(this, parts.invoke('getContent', p).join(''));
        
        if (ExtDragIndicator.isIE6) {
            this.initIFrame();
        }
    },

    show: function() {
        if (!this.floatedToBody) {
            if (!this.realParent) {
                this.realParent = this.parentNode;
                this._nextSibling = this.nextSibling;
            }
            this.realParent.removeChild(this);
            document.body.appendChild(this);
            this.floatedToBody = true;
        }
        Element.show(this);
        this.style.position = 'absolute';
    },

    hide: function() {
        Element.hide(this);
        this.style.position = '';
        this.offsets = undefined;
        this.leave();
        if (this.floatedToBody && this.realParent) {
            document.body.removeChild(this);
            if (this._nextSibling) {
                this.realParent.insertBefore(this, this._nextSibling);
            } else {
                this.realParent.appendChild(this);
            }
            this.floatedToBody = false;
        }
    },

    position: function(x, y) {
        if (!this.offsets) {
            Element.show(this);
            this.style.position = 'absolute';
        }       
        Element.setStyle(this, {"left": x + "px", "top": y + "px"});
    },

    accept: function() {
        Element.removeClassName(this, 'drgind_default');
        Element.removeClassName(this, 'drgind_reject');
        Element.addClassName(this, 'drgind_accept');

        var acceptClass = this.getAcceptClass();
        if (acceptClass) {
            Element.addClassName(this, acceptClass);
        }
    },

    reject: function() {
        Element.removeClassName(this, 'drgind_default');
        Element.removeClassName(this, 'drgind_accept');
        Element.addClassName(this, 'drgind_reject');

        var rejectClass = this.getRejectClass();
        if (rejectClass) {
            Element.addClassName(this, rejectClass);
        }
    },

    leave: function() {
        Element.removeClassName(this, 'drgind_accept');
        //Element.removeClassName(this, 'drgind_reject');
        //Element.addClassName(this, 'drgind_default');
        Element.removeClassName(this, 'drgind_default');
        Element.addClassName(this, 'drgind_reject');

        var acceptClass = this.getAcceptClass();
        var rejectClass = this.getRejectClass();
        if (acceptClass) {
            Element.removeClassName(this, acceptClass);
        }
        if (rejectClass) {
            Element.removeClassName(this, rejectClass);
        }
    },

    getAcceptClass: function() {
        return this.ils_acceptClass;
    },

    getRejectClass: function() {
        return this.ils_rejectClass;
    },
    
    initIFrame: function() {
        var iframe = document.createElement("iframe");
        Element.addClassName(iframe, 'rich-dragindicator-iframe');
        this.insertBefore(iframe, this.firstChild);
        var table = iframe.nextSibling;
        iframe.style.width = table.offsetWidth + "px";
        iframe.style.height = table.offsetHeight + "px";
    }
};

function createExtDragIndicator(elt, acceptClass, rejectClass) {
    Object.extend(elt, ExtDragIndicator);
    elt.init();
    
    elt.ils_acceptClass = acceptClass;
    elt.ils_rejectClass = rejectClass;
}


