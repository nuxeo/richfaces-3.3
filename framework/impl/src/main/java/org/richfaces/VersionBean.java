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

package org.richfaces; 

/**
 * Vendor and version information for A4J project
 * @author asmirnov@exadel.com (latest modification by $Author: nbelaevski $)
 * @version $Revision: 14228 $ $Date: 2009-05-14 18:56:51 +0200 (Thu, 14 May 2009) $
 *
 */
public class VersionBean {

	public static final String VENDOR = "richfaces.org";
	public static final int MAJOR_VERSION = 3;
	public static final int MINOR_VERSION = 3;
	public static final String PROJECT_NAME = "Jboss Richfaces";
	
	/**
	 * Revision version, must be auto modified by  CVS 
	 */
	
	public static final String REVISION = "1.GA" ;
	public static final String SCM_REVISION = "	SVN $Revision: 14228 $ $Date: 2009-05-14 18:56:51 +0200 (Thu, 14 May 2009) $";//$Revision: 14228 $ $Date: 2009-05-14 18:56:51 +0200 (Thu, 14 May 2009) $";
	public static final Version _version = new Version();
	
	public String getVendor() {
		return VENDOR;
	}
	
	public Version getVersion() {
		return _version;
	}

	public String getProjectName() {
		return PROJECT_NAME;
	}
	
	/**
	 * Class for incapsulate version info.
	 * @author asmirnov@exadel.com (latest modification by $Author: nbelaevski $)
	 * @version $Revision: 14228 $ $Date: 2009-05-14 18:56:51 +0200 (Thu, 14 May 2009) $
	 *
	 */
	public static class Version {
		
		public static final String _versionInfo = "v."+MAJOR_VERSION+"."+MINOR_VERSION+"."+REVISION+SCM_REVISION;

		public int getMajor() {
			return MAJOR_VERSION;
		}

		public int getMinor() {
			return MINOR_VERSION;
		}
		
		public String getRevision() {
			return REVISION;
		}
/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return _versionInfo;
		}
		
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		// TODO Auto-generated method stub
		return getProjectName() + " by "+getVendor()+", version "+getVersion().toString();
	}
}
