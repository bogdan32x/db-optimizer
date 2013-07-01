package ro.utcluj.dto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "tableProp")
public class TableProp {

	@Field(value = "pn")
	private String	propName;

	@Field(value = "pt")
	private String	propType;

	@Field(value = "in")
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
