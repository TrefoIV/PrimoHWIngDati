package jsonParser;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSetter;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Coordinates that = (Coordinates) o;
		return row == that.row && column == that.column;
	}

	@Override
	public int hashCode() {
		return Objects.hash(row, column);
	}
}
