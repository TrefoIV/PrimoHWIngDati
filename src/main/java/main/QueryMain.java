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

		try {

			String sCurrentLine;
			int numeroTabTemp = 0;

			br = new BufferedReader(new FileReader("tables.json"));
			ObjectMapper objectMapper = new ObjectMapper();

			while ((sCurrentLine = br.readLine()) != null && numeroTabTemp < 2) {
				// System.out.println("Record:\t" + sCurrentLine);

				numeroTabTemp++;
				// this object will contain the current line of the file

				// current table
				table = objectMapper.readValue(sCurrentLine, Table.class);
				// saving cells from current table to a collection
				CellCollection cells = objectMapper.readValue(sCurrentLine, new TypeReference<CellCollection>() {
				});

				table.setCells(cells);

			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
