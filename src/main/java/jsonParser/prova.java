package jsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import indexManager.IndexManager;
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
public class prova {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {

		BufferedReader br = null;
		JSONParser parser = new JSONParser();
		IndexManager indexManager = new IndexManager();

		try {

			// current line of the json file
			String sCurrentLine;

			br = new BufferedReader(new FileReader("tables.json"));

			// mapper to deserialize a json Object to a Java Object
			ObjectMapper objectMapper = new ObjectMapper();

			int numeroTabelle = 0;
			
			int numeroMedioRighe = 0;
			int numeroMedieColonne = 0;
			LocalTime start = LocalTime.now();
			// keep reading the file line by line until is null
			while ((sCurrentLine = br.readLine()) != null && numeroTabelle<10000) {
				//System.out.println("Record:\t" + sCurrentLine);

				numeroTabelle++;
				// this object will contain the current line of the file
				Object obj;
				try {
					
					// parser
					obj = parser.parse(sCurrentLine);

					// current table
					Table table = objectMapper.readValue(sCurrentLine, Table.class);
					// saving cells from current table to a collection
					CellCollection cells = objectMapper.readValue(sCurrentLine, new TypeReference<CellCollection>() {
					});

					table.setCells(cells);
					indexManager.addTable(table);

					
					//numeroMedieColonne += Integer.parseInt(table.getMaxDimensions().getColumn());
					
					//numeroMedioRighe += Integer.parseInt(table.getMaxDimensions().getRow());


					/*
					 * System.out.println(table.getId());
					 * System.out.println(table.getMaxDimensions().getRow());
					 * System.out.println(table.getMaxDimensions().getColumn());
					 * 
					 * for (Cell cel : table.getCells().getCells()) {
					 * System.out.println(cel.getCoordinates().getColumn());
					 * System.out.println(cel.getCoordinates().getRow());
					 * System.out.println(cel.getCleanedText()); }
					 */

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
//			indexManager.closeManager();
			LocalTime end = LocalTime.now();
			System.out.println(start);
			System.out.println(end);

			
			/*
			 * System.out.println("numero totale righe: " + numeroMedioRighe);
			 * System.out.println("numero medio righe: " +
			 * (int)(numeroMedioRighe/numeroTabelle));
			 * 
			 * System.out.println("numero totale colonne: " + numeroMedieColonne);
			 * System.out.println("numero medio colonne: " +
			 * (int)(numeroMedieColonne/numeroTabelle));
			 */
			//System.out.println(numeroTabelle);

			/*
			 * for (Table tab : tableCollection.getTables()) {
			 * System.out.println(tab.getId());
			 * System.out.println(tab.getMaxDimensions().getRow());
			 * System.out.println(tab.getMaxDimensions().getColumn());
			 * 
			 * }
			 */
			

		} catch (IOException e) {
			e.printStackTrace();
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
