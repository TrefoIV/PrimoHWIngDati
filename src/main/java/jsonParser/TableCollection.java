package jsonParser;

import java.util.List;

public class TableCollection {
	private List<Table> tables;
	
	public TableCollection(List<Table> tables) {
		this.tables = tables;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}



}
