
public class Parameter {
	private String soort;
	private String parameter;
	public Parameter(String soort, String parameter) {
		super();
		this.soort = soort;
		this.parameter = parameter;
	}
	
	public Parameter() {
		
	}

	public String getSoort() {
		return soort;
	}

	public void setSoort(String soort) {
		this.soort = soort;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
}
