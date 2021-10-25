package jsonParser;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSetter;

@JsonIgnoreProperties({ "_id", "id", "className", "innerHTML", "isHeader", "Rows", "beginIndex", "endIndex",
		"referenceContext", "classe", "maxDimensions", "headersCleaned", "keyColumn" })

/*
 * Cell represents a cell of a table
 */

public class Cell {
	private String type;
	private Coordinates coordinates;
	private String cleanedText;

	@JsonCreator
	public Cell(@JsonProperty("type") String type, @JsonProperty("Coordinates") Coordinates coordinates,
			@JsonProperty("cleanedText") String cleanedText) {
		this.type = type;
		this.coordinates = coordinates;
		this.cleanedText = cleanedText;
	}

	public String getType() {
		return type;
	}

	@JsonSetter("type")
	public void setType(String type) {
		this.type = type;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	@JsonSetter("Coordinates")
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public String getCleanedText() {
		return cleanedText;
	}

	@JsonSetter("cleanedText")
	public void setCleanedText(String cleanedText) {
		this.cleanedText = cleanedText;
	}

}
