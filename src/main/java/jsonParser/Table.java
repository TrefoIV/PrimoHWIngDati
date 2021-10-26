package jsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSetter;

@JsonIgnoreProperties({ "id", "className", "beginIndex", "cells", "endIndex", "referenceContext", "type", "classe", "headersCleaned", "keyColumn" })

/*
 * Table represents a table of the json file
 */

public class Table {
	private MaxDimensions maxDimensions;
	private Map<Integer, CellCollection> columns;
	private String id;

	@JsonCreator
	public Table(@JsonProperty("maxDimensions") MaxDimensions maxDimensions, @JsonProperty("_id") String id) {
		this.maxDimensions = maxDimensions;
		this.id = id;
	}
	

	public MaxDimensions getMaxDimensions() {
		return maxDimensions;
	}

	@JsonSetter("maxDimensions")
	public void setMaxDimensions(MaxDimensions maxDimensions) {
		this.maxDimensions = maxDimensions;
	}
	
	public void setColumns(CellCollection cells) {
		this.columns = new TreeMap<>();
		for(Cell cell : cells.getCells()) {
			int columnTemp = cell.getCoordinates().getColumn();
			if(this.columns.containsKey(columnTemp)) {
				this.columns.get(columnTemp).getCells().add(cell);
			}
			else {
				CellCollection column = new CellCollection();
				column.getCells().add(cell);
				this.columns.put(columnTemp, column);
			}
		}
	}
	
	public Map<Integer, CellCollection> getColumns(){
		return this.columns;
	}

	public String getId() {
		return id;
	}

	@JsonSetter("_id")
	public void setId(String id) {
		this.id = id;
	}
	
	
	

}
