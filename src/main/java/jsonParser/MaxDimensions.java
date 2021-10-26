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
	private int row;
	private int column;

	@JsonCreator
	public MaxDimensions(@JsonProperty("row") int row, @JsonProperty("column") int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	@JsonSetter("row")
	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	@JsonSetter("column")
	public void setColumn(int column) {
		this.column = column;
	}
}
