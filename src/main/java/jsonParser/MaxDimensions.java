package jsonParser;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSetter;


@JsonIgnoreProperties({ "_id", "id", "className", "cells", "beginIndex", "endIndex", "referenceContext", "type", "classe", "headersCleaned", "keyColumn" })

/*
 * MaxDimensions represents the number of rows and columns of a single table
 */

public class MaxDimensions {
	private String row;
	private String column;

	@JsonCreator
	public MaxDimensions(@JsonProperty("row") String row, @JsonProperty("column") String column) {
		this.row = row;
		this.column = column;
	}

	public String getRow() {
		return row;
	}

	@JsonSetter("row")
	public void setRow(String row) {
		this.row = row;
	}

	public String getColumn() {
		return column;
	}

	@JsonSetter("column")
	public void setColumn(String column) {
		this.column = column;
	}
}
