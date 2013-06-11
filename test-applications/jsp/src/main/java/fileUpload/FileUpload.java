package fileUpload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.richfaces.component.html.HtmlFileUpload;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import util.componentInfo.ComponentInfo;

public class FileUpload {
	private boolean disabled;
	private boolean autoclear;
	private boolean rendered;
	private boolean required;
	private boolean immediateUpload;
	private String acceptedTypes;
	private String requiredMessage;
	private String listHeight;
	private String listWidth;
	private String align;
	private String bindLabel;	
	private String addButtonClass;
	private String addButtonClassDisabled;
	private String cancelButtonClass;
	private String cancelButtonClassDisabled;
	private String cleanButtonClass;
	private String cleanButtonClassDisabled;
	private String fileEntryClass;
	private String fileEntryControlClass;
	private String fileEntryControlClassDisabled;
	private String fileEntryClassDisabled;
	private String uploadButtonClass;
	private String uploadButtonClassDisabled;
	private String uploadListClass;
	private String uploadListClassDisabled;
	private String changedLabel;
	private String allowFlash;	
	private List data;
	private Integer maxFilesQuantity;
	private HtmlFileUpload myFileUpload;
	private boolean noDuplicate;
	private boolean ajaxSingle;
	private boolean immediate;
	private int uploadsAvailable;
	
	public int getUploadsAvailable() {
		return uploadsAvailable;
	}

	public void setUploadsAvailable(int uploadsAvailable) {
		this.uploadsAvailable = uploadsAvailable;
	}

	public void addHtmlFileUpload(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(myFileUpload);
	}
	
	public boolean isImmediate() {
		return immediate;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}

	public String getBindLabel() {
		return bindLabel;
	}

	public void setBindLabel(String bindLabel) {
		this.bindLabel = bindLabel;
	}

	public HtmlFileUpload getMyFileUpload() {
		return myFileUpload;
	}

	public void setMyFileUpload(HtmlFileUpload myFileUpload) {
		this.myFileUpload = myFileUpload;
	}

	public FileUpload() { 
		data = new ArrayList();
		disabled = false;
		autoclear = false;
		rendered = true;
		required = false;
		immediateUpload = false;
		acceptedTypes = "*";
		requiredMessage = "requiredMessage";
		listHeight = "200px";
		listWidth = "400px";
		maxFilesQuantity = 3;
		align = "left";
		bindLabel = "not ready";
		changedLabel = "not ready";
		addButtonClass="test";
		addButtonClassDisabled="test";
		cancelButtonClass="test";
		cancelButtonClassDisabled="test";
		cleanButtonClass="test";
		cleanButtonClassDisabled="test";
		fileEntryClass="test";
		fileEntryControlClass="test";
		fileEntryControlClassDisabled="test";
		fileEntryClassDisabled="test";
		uploadButtonClass="test";
		uploadButtonClassDisabled="test";
		uploadListClass="test";
		uploadListClassDisabled="test";
		noDuplicate = false;
		allowFlash = "false";
		ajaxSingle = false;
		immediate = false;
		uploadsAvailable = 5;
	}

	public void fileUploadListener(UploadEvent event) throws Exception{
		 UploadItem upload = event.getUploadItem();
		if (upload.isTempFile()) {
	    	File file = upload.getFile();
	    	System.out.println("FileUpload.fileUploadListener()");
	    } else {
	    	ByteArrayOutputStream b = new ByteArrayOutputStream();
	    	b.write(upload.getData());
	    }
		System.out.println("FileUpload.fileUploadListener()");
	}
	
	public void listener(UploadEvent event) throws Exception {

		try {
		UploadItem item = event.getUploadItem();
		System.out.println("File : " + item.getFileName() + " was uploaded");
		uploadsAvailable--;

		} catch (Exception e) {
		e.printStackTrace();
		}

		} 

	public void checkBinding(ActionEvent actionEvent){
		FacesContext context = FacesContext.getCurrentInstance();
		bindLabel = myFileUpload.getClientId(context);
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

	public List getData() {
		for(int i = 0; i < data.size(); i++)
			System.out.println(data.get(i));
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getAddButtonClass() {
		return addButtonClass;
	}

	public void setAddButtonClass(String addButtonClass) {
		this.addButtonClass = addButtonClass;
	}

	public String getAddButtonClassDisabled() {
		return addButtonClassDisabled;
	}

	public void setAddButtonClassDisabled(String addButtonClassDisabled) {
		this.addButtonClassDisabled = addButtonClassDisabled;
	}

	public String getCancelButtonClass() {
		return cancelButtonClass;
	}

	public void setCancelButtonClass(String cancelButtonClass) {
		this.cancelButtonClass = cancelButtonClass;
	}

	public String getCancelButtonClassDisabled() {
		return cancelButtonClassDisabled;
	}

	public void setCancelButtonClassDisabled(String cancelButtonClassDisabled) {
		this.cancelButtonClassDisabled = cancelButtonClassDisabled;
	}

	public String getCleanButtonClass() {
		return cleanButtonClass;
	}

	public void setCleanButtonClass(String cleanButtonClass) {
		this.cleanButtonClass = cleanButtonClass;
	}

	public String getCleanButtonClassDisabled() {
		return cleanButtonClassDisabled;
	}

	public void setCleanButtonClassDisabled(String cleanButtonClassDisabled) {
		this.cleanButtonClassDisabled = cleanButtonClassDisabled;
	}

	public String getFileEntryClass() {
		return fileEntryClass;
	}

	public void setFileEntryClass(String fileEntryClass) {
		this.fileEntryClass = fileEntryClass;
	}

	public String getFileEntryControlClass() {
		return fileEntryControlClass;
	}

	public void setFileEntryControlClass(String fileEntryControlClass) {
		this.fileEntryControlClass = fileEntryControlClass;
	}

	public String getFileEntryControlClassDisabled() {
		return fileEntryControlClassDisabled;
	}

	public void setFileEntryControlClassDisabled(
			String fileEntryControlClassDisabled) {
		this.fileEntryControlClassDisabled = fileEntryControlClassDisabled;
	}

	public String getFileEntryClassDisabled() {
		return fileEntryClassDisabled;
	}

	public void setFileEntryClassDisabled(String fileEntryClassDisabled) {
		this.fileEntryClassDisabled = fileEntryClassDisabled;
	}

	public String getUploadButtonClass() {
		return uploadButtonClass;
	}

	public void setUploadButtonClass(String uploadButtonClass) {
		this.uploadButtonClass = uploadButtonClass;
	}

	public String getUploadButtonClassDisabled() {
		return uploadButtonClassDisabled;
	}

	public void setUploadButtonClassDisabled(String uploadButtonClassDisabled) {
		this.uploadButtonClassDisabled = uploadButtonClassDisabled;
	}

	public String getUploadListClass() {
		return uploadListClass;
	}

	public void setUploadListClass(String uploadListClass) {
		this.uploadListClass = uploadListClass;
	}

	public String getUploadListClassDisabled() {
		return uploadListClassDisabled;
	}

	public void setUploadListClassDisabled(String uploadListClassDisabled) {
		this.uploadListClassDisabled = uploadListClassDisabled;
	}

	public boolean isImmediateUpload() {
		return immediateUpload;
	}

	public void setImmediateUpload(boolean immediateUpload) {
		this.immediateUpload = immediateUpload;
	}

	public boolean isNoDuplicate() {
		return noDuplicate;
	}

	public void setNoDuplicate(boolean noDuplicate) {
		this.noDuplicate = noDuplicate;
	}
	public void valueChangeListener(ValueChangeEvent valueChangeEvent){
		changedLabel = myFileUpload.getUploadData().toString();
	}

	public String getChangedLabel() {
		return changedLabel;
	}

	public void setChangedLabel(String changedLabel) {
		this.changedLabel = changedLabel;
	}

	public String getAllowFlash() {
		return allowFlash;
	}

	public void setAllowFlash(String allowFlash) {
		this.allowFlash = allowFlash;
	}

	public boolean isAjaxSingle() {
		return ajaxSingle;
	}

	public void setAjaxSingle(boolean ajaxSingle) {
		this.ajaxSingle = ajaxSingle;
	}
}
