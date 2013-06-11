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

package org.ajax4jsf.builder.mojo;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.aja4jsf.builder.model.Resource;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.velocity.VelocityContext;

/**
 * @author Nick Belaevski
 *
 */
public abstract class AbstractCreateMojo extends AbstractCDKMojo {

	protected static final String SRC_MAIN_RESOURCES = "src/main/resources";

	protected static final Set<String> JAVA_RESERVED_WORDS;

	static{
		JAVA_RESERVED_WORDS = new HashSet<String>();
		JAVA_RESERVED_WORDS.add("abstract");
		JAVA_RESERVED_WORDS.add("assert");
		JAVA_RESERVED_WORDS.add("boolean");
		JAVA_RESERVED_WORDS.add("break");
		JAVA_RESERVED_WORDS.add("byte");
		JAVA_RESERVED_WORDS.add("case");
		JAVA_RESERVED_WORDS.add("catch");
		JAVA_RESERVED_WORDS.add("char");
		JAVA_RESERVED_WORDS.add("class");
		JAVA_RESERVED_WORDS.add("const");
		JAVA_RESERVED_WORDS.add("continue");
		JAVA_RESERVED_WORDS.add("default");
		JAVA_RESERVED_WORDS.add("do");
		JAVA_RESERVED_WORDS.add("double");
		JAVA_RESERVED_WORDS.add("else");
		JAVA_RESERVED_WORDS.add("extends");
		JAVA_RESERVED_WORDS.add("false");
		JAVA_RESERVED_WORDS.add("final");
		JAVA_RESERVED_WORDS.add("finally");
		JAVA_RESERVED_WORDS.add("float");
		JAVA_RESERVED_WORDS.add("for");
		JAVA_RESERVED_WORDS.add("goto");		
		JAVA_RESERVED_WORDS.add("if");
		JAVA_RESERVED_WORDS.add("implements");
		JAVA_RESERVED_WORDS.add("import");
		JAVA_RESERVED_WORDS.add("instanceof");
		JAVA_RESERVED_WORDS.add("int");
		JAVA_RESERVED_WORDS.add("interface");
		JAVA_RESERVED_WORDS.add("long");
		JAVA_RESERVED_WORDS.add("native");
		JAVA_RESERVED_WORDS.add("new");
		JAVA_RESERVED_WORDS.add("null");
		JAVA_RESERVED_WORDS.add("package");
		JAVA_RESERVED_WORDS.add("private");
		JAVA_RESERVED_WORDS.add("protected");
		JAVA_RESERVED_WORDS.add("public");
		JAVA_RESERVED_WORDS.add("return");
		JAVA_RESERVED_WORDS.add("short");
		JAVA_RESERVED_WORDS.add("static");
		JAVA_RESERVED_WORDS.add("strictfp");
		JAVA_RESERVED_WORDS.add("super");
		JAVA_RESERVED_WORDS.add("switch");
		JAVA_RESERVED_WORDS.add("synchronized");
		JAVA_RESERVED_WORDS.add("this");
		JAVA_RESERVED_WORDS.add("throw");
		JAVA_RESERVED_WORDS.add("throws");
		JAVA_RESERVED_WORDS.add("transient");
		JAVA_RESERVED_WORDS.add("true");
		JAVA_RESERVED_WORDS.add("try");
		JAVA_RESERVED_WORDS.add("void");
		JAVA_RESERVED_WORDS.add("volatile");
		JAVA_RESERVED_WORDS.add("while");
	}

	protected void checkAndCreateDirectory(File directory)
	throws MojoExecutionException {
		getLog().debug("Checking directory " + directory + " for existence");
		if (!directory.exists()) {
			directory.mkdirs();
			getLog().debug("Directory " + directory + " created");
		}
		if (!directory.isDirectory()) {
			throw new MojoExecutionException("Directory " + directory.getAbsolutePath() + " is not a Directory");
		}
	}

	protected void filterJarResource(Resource template, Resource skinResource, File baseDir,
			VelocityContext context) throws Exception {
		File newFile = new File(baseDir, skinResource.getPath());
		if (newFile.exists()) {
			getLog().debug("File " + newFile + " already exists. Skipping.");
		} else {
			try {
				writeParsedTemplate(template.getPath(), context, newFile);
			} catch (Exception e) {
				throw new MojoExecutionException("Unable to write file " + newFile, e);
			}
		}
	}
	
	protected boolean isValidJavaName(String name) {
		return !JAVA_RESERVED_WORDS.contains(name);
	}
	
	protected boolean isValidPackageName(String packageName) {
		if (packageName != null) {
			String[] packageNameSegments = packageName.split("\\.");
			for (String packageNameSegment : packageNameSegments) {
				if (!isValidJavaName(packageNameSegment)) {
					return false;
				}
			}
		}

		return true;
	}

}
