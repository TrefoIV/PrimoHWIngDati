package jsonParser;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSetter;

/*
 * Coordinates represents the coordinates of the cell in a table
 */

public class Coordinates {
	private int row;
	private int column;

	@JsonCreator
	public Coordinates(@JsonProperty("row") int row, @JsonProperty("column") int column) {
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
