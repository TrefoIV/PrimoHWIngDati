package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import indexManager.IndexManager;
import jsonParser.CellCollection;
import jsonParser.Table;
import stats.Stats;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//Queta è una classe inutile di prova
public class IndexerMain {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {

		BufferedReader br = null;
		JSONParser parser = new JSONParser();
		IndexManager indexManager = new IndexManager();
		Stats statistiche = new Stats();
		Table table;
		File queryJson = new File("queryJson.json");

		try {

			// current line of the json file
			String sCurrentLine;

			br = new BufferedReader(new FileReader("tables.json"));

			// mapper to deserialize a json Object to a Java Object
			ObjectMapper objectMapper = new ObjectMapper();

			int numeroTabelle = 0;
			int numeroTabTemp = 0;

			FileWriter myWriter = new FileWriter(queryJson);

			// keep reading the file line by line until is null
			while ((sCurrentLine = br.readLine()) != null && numeroTabTemp < 10) {
				// System.out.println("Record:\t" + sCurrentLine);

				numeroTabelle++;
				numeroTabTemp++;
				table = objectMapper.readValue(sCurrentLine, Table.class);
				// saving cells from current table to a collection
				CellCollection cells = objectMapper.readValue(sCurrentLine, new TypeReference<CellCollection>() {
				});

				table.setColumns(cells);
				if (numeroTabelle == 4) {

					numeroTabelle = 0;

					myWriter.append(sCurrentLine);
					myWriter.append("\n");

				}
				indexManager.addTable(table);

				statistiche.analizza(table);

			}
			statistiche.calcoloNumeriMedi();
			indexManager.closeManager();

			myWriter.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {

			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
