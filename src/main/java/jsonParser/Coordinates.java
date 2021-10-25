package jsonParser;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSetter;

/*
 * Coordinates represents the coordinates of the cell in a table
 */

public class Coordinates {
	private String row;
	private String column;

	@JsonCreator
	public Coordinates(@JsonProperty("row") String row, @JsonProperty("column") String column) {
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
