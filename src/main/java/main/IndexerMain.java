package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import indexManagment.IndexManager;
import jsonParser.CellCollection;
import jsonParser.Table;
import stats.Stats;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.parser.JSONParser;

//Queta è una classe inutile di prova
public class IndexerMain {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		boolean salvare0 = true;
		boolean salvare25 = true;
		boolean salvare50 = true;
		boolean salvare75 = true;
		BufferedReader br = null;
		JSONParser parser = new JSONParser();
		IndexManager indexManager = new IndexManager();
		Stats statistiche = new Stats();
		Table table;
		File queryJson = new File("queryJson.json");
		long start = System.currentTimeMillis();
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
			while ((sCurrentLine = br.readLine()) != null) {
				// System.out.println("Record:\t" + sCurrentLine);

				numeroTabelle++;
				numeroTabTemp++;
				table = objectMapper.readValue(sCurrentLine, Table.class);
				// saving cells from current table to a collection
				CellCollection cells = objectMapper.readValue(sCurrentLine, new TypeReference<CellCollection>() {
				});

				table.setColumns(cells);
				statistiche.analizza(table);
				if ((table.getMaxDimensions().getRow() > 100) && (table.getMaxDimensions().getRow() < 300) && statistiche.isSalvare0() && salvare0) {
					System.out.println("Hi 0  "+ numeroTabTemp);
					myWriter.append(sCurrentLine);
					myWriter.append("\n");
					salvare0 = false;
				}
				else if ((table.getMaxDimensions().getRow() > 100) && (table.getMaxDimensions().getRow() < 300) && statistiche.isSalvare25() && salvare25) {
					System.out.println("Hi 25 "+ numeroTabTemp);
					myWriter.append(sCurrentLine);
					myWriter.append("\n");
					salvare25 = false;
				}
				else if ((table.getMaxDimensions().getRow() > 100) && (table.getMaxDimensions().getRow() < 300) && statistiche.isSalvare50() && salvare50) {
					System.out.println("Hi 50 "+ numeroTabTemp);
					myWriter.append(sCurrentLine);
					myWriter.append("\n");
					salvare50 = false;
				}
				else if ((table.getMaxDimensions().getRow() > 100) && (table.getMaxDimensions().getRow() < 300) && statistiche.isSalvare75() && salvare75) {
					System.out.println("Hi 75 "+ numeroTabTemp);
					myWriter.append(sCurrentLine);
					myWriter.append("\n");
					salvare75 = false;
				}
				/*
				 * if (numeroTabelle == 50000) {
				 * 
				 * System.out.println("Sono ancora vivo\n");
				 * 
				 * numeroTabelle = 0;
				 * 
				 * myWriter.append(sCurrentLine); myWriter.append("\n");
				 * 
				 * } else {
				 * 
				 * indexManager.addTable(table); }
				 */

			}
			statistiche.calcoloNumeriMedi();
			//indexManager.closeManager();

			myWriter.close();
			long end = System.currentTimeMillis();
			System.out.println(end - start);
			System.out.println(statistiche.toString());
			System.out.println("Finito!\n");

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
