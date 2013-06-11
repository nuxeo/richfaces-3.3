<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<html>
	<head>
		<title></title>
	</head>
	<body>
		<f:view>
			<h:form>
				<h:selectOneRadio binding="#{skinBean.component}" />
				<h:commandLink action="#{skinBean.change}" value="set skin" />
			</h:form>

			<div class="rich-container">
				<fieldset>
					<legend>Inside rich-container</legend>
					<button>Test</button>
					<input type="Button" value="Test">
					<input type="Checkbox">
					<input type="File">
					<input type="Radio">
					<input type="Text" value="Test">
					<select size="1">
						<option>Test</option>
					</select>
					
					<select multiple>
						<option>Test</option>
						<option>Test1</option>
						<option>Test2</option>
						<option>Test3</option>
					</select>
					
					
					<br><br>
					<textarea>text</textarea>
					
					<keygen />
					<br><br>
					<isindex />
					
					<a href="#1">Link 1</a>
					<br />
					<a href="#2">Link 2</a>
					
				</fieldset>
			</div>


			<div>
				<fieldset>
					<legend>Without rich-container</legend>
					<button>Test</button>
					<input type="Button" value="Test">
					<input type="Checkbox">
					<input type="File">
					<input type="Radio">
					<input type="Text" value="Test">
					<select size="1">
						<option>Test</option>
					</select>
					
					<select multiple>
						<option>Test</option>
						<option>Test1</option>
						<option>Test2</option>
						<option>Test3</option>
					</select>
					
					
					<br><br>
					<textarea>text</textarea>
					
					<keygen />
					<br><br>
					<isindex />
					
					<a href="#1">Link 1</a>
					<br />
					<a href="#2">Link 2</a>
					
				</fieldset>
			</div>
		</f:view>
	</body>	
</html>  
