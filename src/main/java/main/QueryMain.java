package main;


import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import jsonParser.CellCollection;
import jsonParser.Table;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class QueryMain {

	public static void main(String[] args) {

		BufferedReader br = null;
		JSONParser parser = new JSONParser();
		Table table;

		File queryJson = new File("queryJson.json");

		try {

			String sCurrentLine;
			int numeroTabelle = 0;
			int numeroTabTemp = 0;

			br = new BufferedReader(new FileReader("tables.json"));
			ObjectMapper objectMapper = new ObjectMapper();
			FileWriter myWriter = new FileWriter(queryJson);



			while ((sCurrentLine = br.readLine()) != null && numeroTabTemp<101000) {
				//System.out.println("Record:\t" + sCurrentLine);

				numeroTabTemp++;
				numeroTabelle++;
				// this object will contain the current line of the file
				Object obj;
				if(numeroTabelle == 50000) {

					numeroTabelle=0;

					try {

						obj = parser.parse(sCurrentLine);

						// current table
						table = objectMapper.readValue(sCurrentLine, Table.class);
						// saving cells from current table to a collection
						CellCollection cells = objectMapper.readValue(sCurrentLine, new TypeReference<CellCollection>() {
						});

						table.setCells(cells);

						myWriter.append(sCurrentLine);
						myWriter.append("\n");

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			myWriter.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
