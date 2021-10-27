package jsonParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSetter;


@JsonIgnoreProperties({ "_id", "id", "className", "innerHTML", "isHeader", "Rows", "beginIndex", "endIndex", "referenceContext", "classe", "maxDimensions",
	"headersCleaned", "keyColumn", "type" })

/*
 * CellCollection represents the set of cell compising a table
 */

public class CellCollection implements Serializable {
	private List<Cell> cells;
	
	
	@JsonCreator
	public CellCollection(@JsonProperty("cells")List<Cell> cells) {
		this.cells = cells;
	}
	
	public CellCollection() {
		this.cells = new ArrayList<>();
	}

	public List<Cell> getCells() {
		return cells;
	}
	
	@JsonSetter("cells")
	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}
	
	
}
