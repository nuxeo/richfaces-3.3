<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/fileUpload"
	prefix="fu"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/progressBar" prefix="progressBar" %>
<html>
<head>
<title></title>
<style>
	.list {background-color: white;}
	.list_disabled { background-color: #f1f1f1;}
	.entry {color: Brown; font-weight: bold;}
	.entry_disabled { background-color: #f1f1f1; color: gray; }
	.entry_control {text-decoration: none; color: Brown; font-weight: bold;}
	.entry_control_disabled { background-color: #f1f1f1; color: #BCDEFF; text-decoration: none; color: gray;}

</style>
</head>
<body>
<f:view>

<h:form>
           <h:selectOneRadio binding="#{skinBean.component}" />
           <h:commandLink action="#{skinBean.change}" value="set skin" />
	</h:form>
 
<h:form>
<table cellpadding="0" cellspacing="0" style="width: 500px">
<tr><td>Files Types:</td><td>
<h:inputText value="#{bean.fileTypes}"></h:inputText></td></tr>
<tr><td>Max Files Count:</td><td>
<h:inputText value="#{bean.maxFiles}"></h:inputText></td></tr>
<tr><td>Upload List Width:</td><td>
<h:inputText value="#{bean.width}"></h:inputText></td></tr>
<tr><td>Upload List Height:</td><td>
<h:inputText id="bonus" value="#{bean.height}"></h:inputText></td></tr>
</table><br/>
<a4j:commandButton reRender="upload1" value="Accept"></a4j:commandButton>
</h:form>

<h:form>
<fu:fileUpload 
		id="upload1"
		uploadData="#{bean.data}" 
		fileUploadListener="#{bean.listener}" 
		listWidth="#{bean.width}" 
		listHeight="#{bean.height}"
		maxFilesQuantity="#{bean.maxFiles}"
		acceptedTypes="#{bean.fileTypes}"
		allowFlash="auto">
	<f:facet name="label">
		<h:outputText value="{_KB}KB from {KB}KB uploaded --- {mm}:{ss}"></h:outputText>
	</f:facet>
</fu:fileUpload><br/><br/><br/>
<a4j:commandLink style="font-weight: bold; width: 200px;" value="Show files uploaded:" reRender="files_list"></a4j:commandLink><br/><br/>
<h:dataTable value="#{bean.fileList}" var="file" id="files_list" style="width: 500px">
	<h:column>
	<h:outputText value="#{file.fileName}"></h:outputText>	
	</h:column>
</h:dataTable>

<br/><br/><br/>
<input type="button" onclick="$(this.form.id + ':upload1').component.enable();" value="Enable" />
<input type="button" onclick="$(this.form.id + ':upload1').component.disable();" value="Disable" /><br/>
</h:form>
</f:view>
</body>
</html>
