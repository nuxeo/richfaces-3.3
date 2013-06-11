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

package org.ajax4jsf.builder.maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.ajax4jsf.builder.config.ParsingException;
import org.ajax4jsf.builder.xml.XMLBody;
import org.ajax4jsf.builder.xml.XMLBodyMerge;
import org.ajax4jsf.builder.xml.XPathComparator;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.DirectoryScanner;

/**
 * Class is used to locate and merge several xml files
 * @author Maksim Kaszynski
 *
 */
public class MavenXMLMerge {
	
	class DirectoryItem {
		private File baseDir;
		private String [] includes;
		public DirectoryItem(File baseDir, String[] includes) {
			super();
			this.baseDir = baseDir;
			this.includes = includes;
		}
		
	}
	
	private boolean namespaceAware = false;
	
	private Log log;
	
	private String xPath;
	
	private String keyXPath;
	
	private String [] sortBy = {};
	
	private List<DirectoryItem> directories = new ArrayList<DirectoryItem>();
	
	public MavenXMLMerge() {
	}
	
	public void addDirectory(File dir, String [] includes) {
		directories.add(new DirectoryItem(dir, includes));
	}
	
	public void setSortBy(String ... sortBy) {
		this.sortBy = sortBy;
	}

	public void performMerge(XMLMergeCallback callback) throws MojoExecutionException {
		XMLBodyMerge merge = new XMLBodyMerge(xPath, keyXPath);
		
		for (DirectoryItem item : directories) {
			DirectoryScanner ds = new DirectoryScanner();
			ds.setFollowSymlinks(true);
			ds.setBasedir(item.baseDir);
			ds.setIncludes(item.includes);
			ds.addDefaultExcludes();
			ds.scan();
			String[] files = ds.getIncludedFiles();
			for (String file : files) {
				File sourceFile = new File(item.baseDir, file);
				if (log != null){
					log.info("Process file " + sourceFile.getPath());
				}
				try {
					XMLBody configBody = new XMLBody();
					configBody.loadXML(new FileInputStream(sourceFile), namespaceAware);
					merge.add(configBody);
					
				} catch (FileNotFoundException e) {
					throw new MojoExecutionException("Could't read  file "
							+ sourceFile.getPath(), e);
				} catch (ParsingException e) {
					throw new MojoExecutionException(
							"Error parsing config file "
									+ sourceFile.getPath(), e);
				}

			}
		}
		
		if (sortBy != null) {
			merge.sort(new XPathComparator(sortBy));
		}
		
		try {
			callback.onMergeComplete(merge.getContent());
		} catch (Exception e) {
			throw new MojoExecutionException("Exception uring XML merge", e);
		}
	}

	public void setNamespaceAware(boolean namespaceAware) {
		this.namespaceAware = namespaceAware;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public void setXPath(String path) {
		xPath = path;
	}

	public void setKeyXPath(String keyXPath) {
		this.keyXPath = keyXPath;
	}
}
