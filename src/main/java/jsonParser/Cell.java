package jsonParser;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSetter;

@JsonIgnoreProperties({ "_id", "id", "className", "innerHTML", "Rows", "beginIndex", "endIndex", "referenceContext",
		"classe", "maxDimensions", "headersCleaned", "keyColumn" })

/*
 * Cell represents a cell of a table
 */

public class Cell {
	private Boolean isHeader;
	private String type;
	private Coordinates coordinates;
	private String cleanedText;

	@JsonCreator
	public Cell(@JsonProperty("type") String type, @JsonProperty("Coordinates") Coordinates coordinates,
			@JsonProperty("cleanedText") String cleanedText, @JsonProperty("isHeader") Boolean isHeader) {
		this.type = type;
		this.coordinates = coordinates;
		this.cleanedText = cleanedText.toLowerCase();
		this.isHeader = isHeader;
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

	public Boolean getHeader() {
		return isHeader;
	}

	@JsonSetter("isHeader")
	public void setHeader(Boolean isHeader) {
		this.isHeader = isHeader;
	}

}
