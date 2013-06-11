/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

package org.ajax4jsf.builder.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.ajax4jsf.builder.config.BuilderConfig;
import org.ajax4jsf.builder.config.PropertyBean;
import org.ajax4jsf.builder.config.TagBean;
import org.ajax4jsf.builder.config.ValidatorBean;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;


/**
 * Class implement functionality for generate jsp tags for validators
 * inner element of {@link org.ajax4jsf.builder.ant.JSFGeneratorTask}
 * use in ant build.xml :
 * &lt;jsfgenerator ... &gt;
 *     ....
 *     &lt;tags ... /&gt;
 *     ....
 * &lt;/jsfgenerator&gt;
 * attributes:
 * {@see com.exadel.vcp.builder.ant.InnerGenerator}
 *
 */
public class ValidatorTagGenerator extends InnerGenerator {

	private static final String TAG_TEMPLATE_NAME = "validatorTag.vm";

	private String _package = null;

	/**
	 * @return Returns the package.
	 */
	public String getPackage() {
		return _package;
	}
	/**
	 * @param package1 The package to set.
	 */
	public void setPackage(String package1) {
		_package = package1;
	}
	/**
	 * @param task
	 */
	public ValidatorTagGenerator(JSFGeneratorConfiguration task, Logger log) {
		super(task,log);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.exadel.vcp.builder.ant.InnerGenerator#createFiles(com.exadel.vcp.builder.config.BuilderConfig)
	 */
	public void createFiles(BuilderConfig config) throws GeneratorException {
		VelocityContext context = new VelocityContext();
		context.put("generator",this);
		Template template = getTemplate();
		// Put common properties
		for (Iterator iter = config.getValidators().iterator(); iter.hasNext();) {
			ValidatorBean validator = (ValidatorBean) iter.next();
			TagBean tag = validator.getTag();
			if ( null !=  tag && null !=tag.getClassname() && tag.isGenerate()) {
				info("Create tag class file "+validator.getClassname());
				context.put("validator", validator);
				
				context.put("tag", validator.getTag());
				context.put("package", validator.getTag().getPackageName());
				Set<String> importClasses = new HashSet<String>();
				for (Iterator it = validator.getProperties().iterator(); it
						.hasNext();) {
					PropertyBean property = (PropertyBean) it.next();
					// For non-primitive types, add import declaration.
					if (!property.isSimpleType() && !property.isHidden()) {
						importClasses.add(property.getClassname());
					}
				}
				importClasses.add(validator.getTag().getSuperclass());
				context.put("imports", importClasses);
				String resultPath = validator.getTag().getClassname().replace('.', '/')
						+ ".java";
				File javaFile = new File(getDestDir(), resultPath);
				File javaDir = javaFile.getParentFile();
				if (!javaDir.exists()) {
					javaDir.mkdirs();
				}
				try {
					if (javaFile.exists()) {
						javaFile.delete();
					}
					Writer out = new BufferedWriter(new FileWriter(javaFile));
					template.merge(context, out);
					out.flush();
					out.close();
				} catch (Exception e) {
					throw new GeneratorException(
							"Error generating JSP Tag Java file for validator " + validator.getName(), e);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.exadel.vcp.builder.ant.InnerGenerator#getDefaultTemplateName()
	 */
	protected String getDefaultTemplateName() {
		// TODO Auto-generated method stub
		return TAG_TEMPLATE_NAME;
	}

}
