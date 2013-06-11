<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<html>
	<head>
		<title>RichFaces dataTable. Article Examples</title>
	</head>
	<body>
		<f:view>
			<h:form>
				<h:panelGrid columns="1">
				<h:commandLink value="Example #1. Simple Table" action="example1" />
				<h:commandLink value="Example #2. Using rich:column and rich:columnGroup" action="example2" />
				<h:commandLink value="Example #3. Look-n-Feel Customization with Predefined Classes"  action="example3" />
				<h:commandLink value="Example #4. Look-n-Feel Customization. Using Classes and Styles"  action="example4" />
				<h:commandLink value="Example #5. Sorting Rows." action="example5"/>
				<h:commandLink value="Example #10. Ajax Update"  action="example10" />

				</h:panelGrid>
			</h:form>
		</f:view>
	</body>	
</html>  
