package general;

import org.richfaces.VersionBean; 

public class General {

	private String componentPage;
	private String _vers = VersionBean.Version._versionInfo;
	private String vers = _vers.substring(0, _vers.indexOf("SVN"));
	
	public General() {
		componentPage = "/main/componentsList.jsp";
	}

	public String getComponentPage() {
		return componentPage;
	}

	public void setComponentPage(String componentPage) {
		this.componentPage = componentPage;
	}

	public String getVers() {
		return vers;
	}
}
