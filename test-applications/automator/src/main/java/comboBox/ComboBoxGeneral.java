package comboBox;

import general.DrawGrids;

import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import org.richfaces.component.html.HtmlComboBox;
import parser.Attribute;
import parser.AttributesList;
import parser.Status;
import parser.TLDParser;
import phaseTracker.PhaseTracker;

public class ComboBoxGeneral {

	private ArrayList<SelectItem> selectItems;
	private HtmlComboBox comboBox;
	private HtmlPanelGrid panelGrid;

	// list of attributes
	private boolean immediate;
	private boolean required;
	private String align;
	private String validatorMessage;
	private String converterMessage;
	private SelectItem value;

	private boolean statusValidator = false;
	private String phaseValidator = "UNDEFINED";
	private String validatorMessageTest = "";

	private boolean statusValueChangeListener = false;
	private String phaseValueChangeListener = "UNDEFINED";

	private boolean statusConverterGetAsObject = false;
	private boolean statusConverterGetAsString = false;
	private String phaseConverterGetAsObject = "UNDEFINED";
	private String phaseConverterGetAsString = "UNDEFINED";
	private String converterMessageTest = "";

	private TLDParser tldParser = new TLDParser("comboBox");
	private AttributesList attrs = tldParser.getAllAttributes();
	private ArrayList<Attribute> generalAttrs = attrs.getCommonAttributes();

	public ComboBoxGeneral() {
		immediate = true;
		required = true;
		align = "right";
		validatorMessage = "validator test message!";
		converterMessage = "converter test message!";

		selectItems = new ArrayList<SelectItem>();
		for (int i = 0; i < 10; i++) {
			selectItems.add(new SelectItem("selectItem " + i));
		}
	}

	public void testGeneralAttributes(ActionEvent e) {
		this.validatorCheck();
		this.alignCheck();
		this.validatorMessageCheck();
		this.bindingCheck();
		this.valueChangeListenerCheck();
		this.converterCheck();
		this.converterMessageCheck();

		DrawGrids.showResultGrid(panelGrid, generalAttrs);
	}

	public void valueChangeListener(ValueChangeEvent e) {
		statusValueChangeListener = true;
		phaseValueChangeListener = PhaseTracker.currentPhase.toString();
	}

	private void valueChangeListenerCheck() {
		int index = generalAttrs.indexOf(new Attribute("valueChangeListener"));
		if (index == -1) {
			generalAttrs.add(new Attribute("valueChangeListener", "", "",
					Status.IMPLEMENTED));
		} else {
			Attribute attr = generalAttrs.get(index);
			if (statusValueChangeListener) {
				if ((phaseValueChangeListener.equals("PROCESS_VALIDATIONS 3"))
						&& (!immediate)
						|| (phaseValueChangeListener
								.equals("APPLY_REQUEST_VALUES 2"))
						&& (immediate)) {
					attr.setStatus(Status.PASSED);
				} else {
					attr.setStatus(Status.FAILED);
					attr
							.setDescription("ValueChangeListener was triggered on incorrect phase");
				}
			} else {
				attr.setStatus(Status.FAILED);
				attr.setDescription("ValueChangeListener was not triggered");
			}
		}
	}

	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		statusValidator = true;
		phaseValidator = PhaseTracker.currentPhase.toString();

		if (value != null) {
			SelectItem st = (SelectItem) value;
			if (st.getLabel().equals("ValidatorCheck")) {
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Validation error",
						"Incorrect input"));
			}
		}
	}

	private void validatorCheck() {
		int index = generalAttrs.indexOf(new Attribute("validator"));
		if (index == -1) {
			generalAttrs
					.add(new Attribute(
							"validator",
							"void validator(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)",
							"", Status.IMPLEMENTED));
		} else {
			Attribute attr = generalAttrs.get(index);
			if (statusValidator) {
				if ((phaseValidator.equals("PROCESS_VALIDATIONS 3"))
						&& (!immediate)
						|| (phaseValidator.equals("APPLY_REQUEST_VALUES 2"))
						&& (immediate)) {
					attr.setStatus(Status.PASSED);
				} else {
					attr.setStatus(Status.FAILED);
					attr
							.setDescription("Validator was triggered on incorrect phase");
				}
			} else {
				attr.setStatus(Status.FAILED);
				attr.setDescription("Validator was not triggered");
			}
		}
	}

	public Converter getConvert() {
		return new Converter() {
			public Object getAsObject(FacesContext context,
					UIComponent component, String newValue)
					throws ConverterException {

				statusConverterGetAsObject = true;
				phaseConverterGetAsObject = PhaseTracker.currentPhase
						.toString();
				if (newValue.equals("ConverterCheck"))
					throw new ConverterException(new FacesMessage(
							FacesMessage.SEVERITY_ERROR, "Converter error",
							"Error while convert to Object"));

				return new SelectItem(newValue);
			}

			public String getAsString(FacesContext context,
					UIComponent component, Object value)
					throws ConverterException {

				statusConverterGetAsString = true;
				phaseConverterGetAsString = PhaseTracker.currentPhase
						.toString();
				if (false)
					throw new ConverterException(new FacesMessage(
							FacesMessage.SEVERITY_ERROR, "Converter error",
							"Error while convert to String"));

				String result = "";
				if (value != null) {
					if (value instanceof SelectItem) {
						SelectItem st = (SelectItem) value;
						result = st.getLabel();
					} else {
						result = value.toString();
					}
				}

				return result;
			}
		};
	}

	private void converterCheck() {
		int index = generalAttrs.indexOf(new Attribute("converter"));
		if (index == -1) {
			generalAttrs.add(new Attribute("converter", "", "",
					Status.IMPLEMENTED));
		} else {
			Attribute attr = generalAttrs.get(index);
			if ((statusConverterGetAsObject) && (statusConverterGetAsString)) {
				if (((phaseConverterGetAsObject.equals("PROCESS_VALIDATIONS 3"))
						&& (!immediate) || (phaseValueChangeListener
						.equals("APPLY_REQUEST_VALUES 2"))
						&& (immediate))
						&& (phaseConverterGetAsString
								.equals("RENDER_RESPONSE 6"))) {
					attr.setStatus(Status.PASSED);
				} else {
					attr.setStatus(Status.FAILED);
					attr
							.setDescription("Converter was triggered on incorrect phase");
				}
			} else {
				attr.setStatus(Status.FAILED);
				attr.setDescription("Converter was not triggered");
			}
		}
	}

	private void converterMessageCheck() {
		int index = generalAttrs.indexOf(new Attribute("converterMessage"));
		if (index == -1) {
			generalAttrs.add(new Attribute("converterMessage", "", "",
					Status.IMPLEMENTED));
		} else {
			Attribute attr = generalAttrs.get(index);

			if (converterMessageTest.equals("")) {
				attr.setStatus(Status.IMPLEMENTED);
			} else {
				if (converterMessageTest.indexOf(converterMessage) != -1) {
					attr.setStatus(Status.PASSED);
				} else {
					attr.setStatus(Status.FAILED);
				}
			}
		}
	}

	private void alignCheck() {
		int index = generalAttrs.indexOf(new Attribute("align"));
		if (index == -1) {
			generalAttrs
					.add(new Attribute("align", "", "", Status.IMPLEMENTED));
		} else {
			Attribute attr = generalAttrs.get(index);

			attr.setStatus(Status.PASSED);
		}
	}

	private void bindingCheck() {
		int index = generalAttrs.indexOf(new Attribute("binding"));
		if (index == -1) {
			generalAttrs.add(new Attribute("binding", "", "",
					Status.IMPLEMENTED));
		} else {
			Attribute attr = generalAttrs.get(index);
			if (comboBox != null) {
				attr.setStatus(Status.PASSED);
			} else {
				attr.setStatus(Status.FAILED);
			}
		}
	}

	private void validatorMessageCheck() {
		int index = generalAttrs.indexOf(new Attribute("validatorMessage"));
		if (index == -1) {
			generalAttrs.add(new Attribute("validatorMessage", "", "",
					Status.IMPLEMENTED));
		} else {
			Attribute attr = generalAttrs.get(index);

			if (validatorMessageTest.equals("")) {
				attr.setStatus(Status.IMPLEMENTED);
			} else {
				if (validatorMessageTest.indexOf(validatorMessage) != -1) {
					attr.setStatus(Status.PASSED);
				} else {
					attr.setStatus(Status.FAILED);
				}
			}
		}
	}

	public HtmlComboBox getComboBox() {
		return comboBox;
	}

	public void setComboBox(HtmlComboBox comboBox) {
		this.comboBox = comboBox;
	}

	public ArrayList<SelectItem> getSelectItems() {
		return selectItems;
	}

	public HtmlPanelGrid getPanelGrid() {
		return panelGrid;
	}

	public void setPanelGrid(HtmlPanelGrid panelGrid) {
		this.panelGrid = panelGrid;
	}

	public boolean isImmediate() {
		return immediate;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public void setSelectItems(ArrayList<SelectItem> selectItems) {
		this.selectItems = selectItems;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getValidatorMessage() {
		return validatorMessage;
	}

	public void setValidatorMessage(String validatorMessage) {
		this.validatorMessage = validatorMessage;
	}

	public String getValidatorMessageTest() {
		return validatorMessageTest;
	}

	public void setValidatorMessageTest(String validatorMessageTest) {
		this.validatorMessageTest = validatorMessageTest;
	}

	public SelectItem getValue() {
		return value;
	}

	public void setValue(SelectItem value) {
		this.value = value;
	}

	public String getConverterMessage() {
		return converterMessage;
	}

	public void setConverterMessage(String converterMessage) {
		this.converterMessage = converterMessage;
	}

	public String getConverterMessageTest() {
		return converterMessageTest;
	}

	public void setConverterMessageTest(String converterMessageTest) {
		this.converterMessageTest = converterMessageTest;
	}
}