package fileUpload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.io.InputStream;

import org.richfaces.component.html.HtmlFileUpload;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import util.componentInfo.ComponentInfo;

public class FileUpload {
	private boolean disabled;
	private boolean autoclear;
	private boolean rendered;
	private boolean required;
	private String acceptedTypes;
	private String requiredMessage;
	private String listHeight;
	private String listWidth;
	private ArrayList data;
	private Integer maxFilesQuantity;
	private UploadItem upload;
	private HtmlFileUpload htmlFileUpload = null;
	private String allowFlash;

	public String getAllowFlash() {
		return allowFlash;
	}

	public void setAllowFlash(String allowFlash) {
		this.allowFlash = allowFlash;
	}

	public HtmlFileUpload getHtmlFileUpload() {
		return htmlFileUpload;
	}

	public void setHtmlFileUpload(HtmlFileUpload htmlFileUpload) {
		this.htmlFileUpload = htmlFileUpload;
	}
	
	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlFileUpload);
		return null;
	}

	public FileUpload() {
		data = new ArrayList();
		disabled = false;
		autoclear = false;
		rendered = true;
		required = false;
		acceptedTypes = "*";
		requiredMessage = "requiredMessage";
		listHeight = "200px";
		listWidth = "150px";
		maxFilesQuantity = 3;
		upload = null;
		allowFlash = "false";
	}

	public void fileUploadListener(UploadEvent event) throws Exception{
		 upload = event.getUploadItem();
		if (upload.isTempFile()) {
	    	File file = upload.getFile();	    	
	    } else {
	    	ByteArrayOutputStream b = new ByteArrayOutputStream();
	    	b.write(upload.getData());
	    }
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isAutoclear() {
		return autoclear;
	}

	public void setAutoclear(boolean autoclear) {
		this.autoclear = autoclear;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getRequiredMessage() {
		return requiredMessage;
	}

	public void setRequiredMessage(String requiredMessage) {
		this.requiredMessage = requiredMessage;
	}

	public Integer getMaxFilesQuantity() {
		return maxFilesQuantity;
	}

	public void setMaxFilesQuantity(Integer maxFilesQuantity) {
		this.maxFilesQuantity = maxFilesQuantity;
	}

	public String getListHeight() {
		return listHeight;
	}

	public void setListHeight(String listHeight) {
		this.listHeight = listHeight;
	}

	public String getListWidth() {
		return listWidth;
	}

	public void setListWidth(String listWidth) {
		this.listWidth = listWidth;
	}

	public String getAcceptedTypes() {
		return acceptedTypes;
	}

	public void setAcceptedTypes(String acceptedTypes) {
		this.acceptedTypes = acceptedTypes;
	}

	public ArrayList getData() {
		return data;
	}

	public void setData(ArrayList data) {
		this.data = data;
	}

	public UploadItem getUpload() {
		return upload;
	}

	public void setUpload(UploadItem upload) {
		this.upload = upload;
	}
}
