package skinning;

import java.util.ArrayList;
import java.util.EnumMap;

import javax.faces.model.SelectItem;

public class Skinning {
	private ArrayList<SelectItem> list = new ArrayList<SelectItem>();
	private ArrayList<SelectItem> enableStyle = new ArrayList<SelectItem>();
	private ArrayList<SelectItem> disabledStyle = new ArrayList<SelectItem>();
	private final int MAX_SIZE = 5;
	private String selectSkinning = "none";
	private String skinning = "disable";
	private String skinningClass = "disable";
	private String commandButton = "rich-button";
	private String commandLink = "rich-link";
	private String dataTable = "rich-isindex";
	private String inputSecret = "inputSecret";
	private String inputSecretStyleClass = "rich-input";
	private String inputTextStyleClass = "rich-input";
	private String inputText = "inputText";
	private String inputTextarea = "inputTextarea\nRichFaces";
	private String inputTextareaStyleClass = "rich-textarea";
	private String outputLink = "rich-link";
	private String outputText = "rich-link";
	private String selectBooleanCheckboxStyleClass = "rich-select";
	private boolean selectBooleanCheckbox1 = true;
	private boolean selectBooleanCheckbox2 = false;
	private String selectManyCheckbox = "rich-select";
	private String selectManyListbox = "rich-select";
	private String selectManyListBox = "rich-select";
	private String selectManyMenu = "rich-select";
	private String selectOneListbox = "rich-select";
	private String selectOneMenu = "rich-select";
	private String selectOneRadio = "rich-select";
	
	public Skinning() {
		for(int i = 0; i < MAX_SIZE; i++){
			list.add(new SelectItem("item " + i));
		}
	}

	public String getCommandButton() {
		return commandButton;
	}

	public void setCommandButton(String commandButton) {
		this.commandButton = commandButton;
	}

	public String getCommandLink() {
		return commandLink;
	}

	public void setOmmandLink(String commandLink) {
		this.commandLink = commandLink;
	}

	public String getDataTable() {
		return dataTable;
	}

	public void setDataTable(String dataTable) {
		this.dataTable = dataTable;
	}

	public String getInputSecret() {
		return inputSecret;
	}

	public void setInputSecret(String inputSecret) {
		this.inputSecret = inputSecret;
	}

	public String getInputTextStyleClass() {
		return inputTextStyleClass;
	}

	public void setInputTextStyleClass(String inputTextStyleClass) {
		this.inputTextStyleClass = inputTextStyleClass;
	}

	public String getInputTextarea() {
		return inputTextarea;
	}

	public void setInputTextarea(String inputTextarea) {
		this.inputTextarea = inputTextarea;
	}

	public String getOutputLink() {
		return outputLink;
	}

	public void setOutputLink(String outputLink) {
		this.outputLink = outputLink;
	}

	public String getOutputText() {
		return outputText;
	}

	public void setOutputText(String outputText) {
		this.outputText = outputText;
	}

	public String getSelectBooleanCheckboxStyleClass() {
		return selectBooleanCheckboxStyleClass;
	}

	public void setSelectBooleanCheckboxStyleClass(String selectBooleanCheckboxStyleClass) {
		this.selectBooleanCheckboxStyleClass = selectBooleanCheckboxStyleClass;
	}

	public String getSelectManyListBox() {
		return selectManyListBox;
	}

	public void setSelectManyListBox(String selectManyListBox) {
		this.selectManyListBox = selectManyListBox;
	}

	public String getSelectManyMenu() {
		return selectManyMenu;
	}

	public void setSelectManyMenu(String selectManyMenu) {
		this.selectManyMenu = selectManyMenu;
	}

	public String getSelectOneListbox() {
		return selectOneListbox;
	}

	public void setSelectOneListbox(String selectOneListbox) {
		this.selectOneListbox = selectOneListbox;
	}

	public String getSelectOneMenu() {
		return selectOneMenu;
	}

	public void setSelectOneMenu(String selectOneMenu) {
		this.selectOneMenu = selectOneMenu;
	}

	public String getSelectOneRadio() {
		return selectOneRadio;
	}

	public void setSelectOneRadio(String selectOneRadio) {
		this.selectOneRadio = selectOneRadio;
	}

	public String getSelectManyCheckbox() {
		return selectManyCheckbox;
	}

	public void setSelectManyCheckbox(String selectManyCheckbox) {
		this.selectManyCheckbox = selectManyCheckbox;
	}

	public void setCommandLink(String commandLink) {
		this.commandLink = commandLink;
	}

	public String getSelectSkinning() {
		return selectSkinning;
	}

	public String getSkinning() {
		return skinning;
	}

	public void setSkinning(String skinning) {
		this.skinning = skinning;
	}

	public String getSkinningClass() {
		return skinningClass;
	}

	public void setSkinningClass(String skinningClass) {
		this.skinningClass = skinningClass;
	}

	public void setSelectSkinning(String selectSkinning) {
		if (selectSkinning.equals("SKINNING")) { 
			setSkinning("enable");
		} else if (selectSkinning.equals("SKINNING_CLASSES")) {
			setSkinning("disable");
			setSkinningClass("enable");
		} else {
			setSkinning("disable");
			setSkinningClass("disable");
		}
		this.selectSkinning = selectSkinning;
	}

	/**
	 * @return the list
	 */
	public ArrayList<SelectItem> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(ArrayList<SelectItem> list) {
		this.list = list;
	}

	public String getInputText() {
		return inputText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}

	public ArrayList<SelectItem> getEnableStyle() {
		return enableStyle;
	}

	public void setEnableStyle(ArrayList<SelectItem> enableStyle) {
		this.enableStyle = enableStyle;
	}

	public ArrayList<SelectItem> getDisabledStyle() {
		return disabledStyle;
	}

	public void setDisabledStyle(ArrayList<SelectItem> disabledStyle) {
		this.disabledStyle = disabledStyle;
	}

	public String getInputSecretStyleClass() {
		return inputSecretStyleClass;
	}

	public void setInputSecretStyleClass(String inputSecretStyleClass) {
		this.inputSecretStyleClass = inputSecretStyleClass;
	}

	public String getInputTextareaStyleClass() {
		return inputTextareaStyleClass;
	}

	public void setInputTextareaStyleClass(String inputTextareaStyleClass) {
		this.inputTextareaStyleClass = inputTextareaStyleClass;
	}

	public boolean getSelectBooleanCheckbox1() {
		return selectBooleanCheckbox1;
	}

	public void setSelectBooleanCheckbox1(boolean selectBooleanCheckbox1) {
		this.selectBooleanCheckbox1 = selectBooleanCheckbox1;
	}

	public boolean getSelectBooleanCheckbox2() {
		return selectBooleanCheckbox2;
	}

	public void setSelectBooleanCheckbox2(boolean selectBooleanCheckbox2) {
		this.selectBooleanCheckbox2 = selectBooleanCheckbox2;
	}

	public String getSelectManyListbox() {
		return selectManyListbox;
	}

	public void setSelectManyListbox(String selectManyListbox) {
		this.selectManyListbox = selectManyListbox;
	}
}
