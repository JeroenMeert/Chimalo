
public class Parameter {
	private String soort;
	private String parameter;
	private Object object;
	
	public Parameter(String soort, String parameter) {
		super();
		this.soort = soort;
		this.parameter = parameter;
	}
	
	public Parameter(Object object, String soort) {
		super();
		this.soort = soort;
		this.object = object;
	}

	public Parameter(Object o) {
		object = o;
	}

	public Object getObject() {
		return object;
	}



	public void setObject(Object object) {
		this.object = object;
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
