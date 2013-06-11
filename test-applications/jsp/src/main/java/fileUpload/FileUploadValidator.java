package fileUpload;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.richfaces.model.UploadItem;


public class FileUploadValidator implements Validator {

	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		
		UploadItem upload = (UploadItem)value;
				
		if (upload.isTempFile()) {
	    	File file = upload.getFile();
	    	String name = file.getName();
	    	System.out.println("fileName: " + name);
	    	if (name == "ExadelMinsk.avi") {
				throw new ValidatorException(new FacesMessage("Test validator: ExadelMinsk.avi file is restricted!"));
			}
	    } else {
	    	ByteArrayOutputStream b = new ByteArrayOutputStream();
	    	try {
				b.write(upload.getData());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	}

}
