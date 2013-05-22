package ro.utcluj.dto;

public class TablePropDTO {

	private String	propName;

	private String	propType;

	private boolean	isNullable;

	public String getPropName() {
		return this.propName;
	}

	public void setPropName(final String propName) {
		this.propName = propName;
	}

	public String getPropType() {
		return this.propType;
	}

	public void setPropType(final String propType) {
		this.propType = propType;
	}

	public boolean isNullable() {
		return this.isNullable;
	}

	public void setNullable(final boolean isNullable) {
		this.isNullable = isNullable;
	}

	@Override
	public String toString() {
		return this.propName + " " + this.propType + " " + this.isNullable;
	}
}
