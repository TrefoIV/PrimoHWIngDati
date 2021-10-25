package jsonParser;

import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSetter;

@JsonIgnoreProperties({ "_id", "className", "beginIndex", "cells", "endIndex", "referenceContext", "type", "classe", "headersCleaned", "keyColumn" })

/*
 * Table represents a table of the json file
 */

public class Table {
	private MaxDimensions maxDimensions;
	private CellCollection cells;
	private String id;

	@JsonCreator
	public Table(@JsonProperty("maxDimensions") MaxDimensions maxDimensions, @JsonProperty("id") String id) {
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

	public CellCollection getCells() {
		return cells;
	}

	@JsonSetter("cells")
	public void setCells(CellCollection cells) {
		this.cells = cells;
	}

	public String getId() {
		return id;
	}

	@JsonSetter("id")
	public void setId(String id) {
		this.id = id;
	}
	
	

}
