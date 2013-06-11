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

package org.ajax4jsf.builder.ant;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Ant.Reference;
import org.apache.tools.ant.types.Path;

/**
 * @author Maksim Kaszynski
 *
 */
public class VCPVersionTask extends Task {

	private String versionProperty = "EXADEL_VCP_VERSION";
	private String vendorProperty = "EXADEL_VCP_VENDOR";
	private File output;
	
	/**
	 * Full java class name for version bean
	 */
	private String _versionBean;
	
	/**
	 * Classpath for load version bean.
	 */
	private Path _classpath;


	/**
	 * @return Returns the classpath.
	 */
	public Path getClasspath() {
		return _classpath;
	}

	/**
	 * @param classpath The classpath to set.
	 */
	public void setClasspath(Path classpath) {
		if(null == _classpath) {
			_classpath = classpath;
		} else {
			_classpath.add(classpath);
		}
	}

	public Path createClasspath() {
		Path classpath = new Path(getProject());
		if(null == _classpath) {
			_classpath = classpath;
		} else {
			_classpath.add(classpath);
		}
		return classpath;
	}
	
	public void setClasspathRef(Reference ref) {
		Object refObj = ref.getReferencedObject();
		if (refObj instanceof Path) {
			setClasspath( (Path) refObj );			
		}
	}

	/**
	 * @return Returns the versionBean.
	 */
	public String getVersionBean() {
		return this._versionBean;
	}

	/**
	 * @param versionBean The versionBean to set.
	 */
	public void setVersionBean(String versionBean) {
		this._versionBean = versionBean;
	}

	public File getOutput() {
		return output;
	}
	public void setOutput(File outputFile) {
		this.output = outputFile;
	}
	
	
	public void execute() throws BuildException {
		String cvsLabel = null;		
		try {
			AntClassLoader classLoader = getProject().createClassLoader(
					getClasspath());
			Class beanClass = classLoader.loadClass(getVersionBean());

			Object chameleonBean = beanClass.newInstance();

			String version = BeanUtils.getProperty(chameleonBean, "version");
			version = version.replaceAll("\\$|(Date(.*))", "").trim();
			cvsLabel = version.replaceAll("\\W", "_");
			getProject().setProperty(getVendorProperty(),
					BeanUtils.getProperty(chameleonBean, "vendor"));
			getProject().setProperty(getVersionProperty(), version);
			System.out.println(getProject().getProperty(getVersionProperty()));
			System.out.println(cvsLabel);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			getProject().fireBuildFinished(e);
		}
		try {
			if(output.exists()){
				output.delete();
			}
				FileWriter fileWriter = new FileWriter(output);
				fileWriter.write(cvsLabel);
				fileWriter.flush();
				fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			getProject().fireBuildFinished(e);
		}
	
	}
	public String getVendorProperty() {
		return vendorProperty;
	}
	public void setVendorProperty(String vendorProperty) {
		this.vendorProperty = vendorProperty;
	}
	public String getVersionProperty() {
		return versionProperty;
	}
	public void setVersionProperty(String versionProperty) {
		this.versionProperty = versionProperty;
	}

	

	
}
