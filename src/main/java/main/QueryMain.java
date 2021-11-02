package main;

// https://www.youtube.com/watch?v=eT3BFzSD6YY

import indexManagment.IndexManager;
import indexManagment.QueryManager;
import jsonParser.Cell;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import jsonParser.CellCollection;
import jsonParser.Table;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

public class QueryMain {

	public static void main(String[] args) {

		BufferedReader br;
		Table table;
		QueryManager queryManager = new QueryManager();

		try {
			String sCurrentLine;
			int numeroTabTemp = 0;

			br = new BufferedReader(new FileReader("queryJson.json"));
			ObjectMapper objectMapper = new ObjectMapper();

			while ((sCurrentLine = br.readLine()) != null /*&& numeroTabTemp < 2*/) {
				// System.out.println("Record:\t" + sCurrentLine);

				numeroTabTemp++;

				System.out.println("============================================================================\n============================================================================");

				// current table
				table = objectMapper.readValue(sCurrentLine, Table.class);
				// saving cells from current table to a collection
				CellCollection cells = objectMapper.readValue(sCurrentLine, new TypeReference<CellCollection>() {});
				table.setColumns(cells);

				CellCollection column = table.getColumns().get(0);
				//Skip GARGANTUA
				if (column.getCells().size() > 6000)
						continue;

				double percValoriDist = ( ((double) new HashSet<Cell>(column.getCells()).size() )/( (double) column.getCells().size()) )*100;

				System.out.println("############################################### Executing Query for Column : ###############################################");
				System.out.print(table.getId() + ": ");
				for(Cell c : column.getCells()){
					System.out.print(c.getCleanedText() + "\t");
				}
				System.out.println("\n" + new HashSet<Cell>(column.getCells()).size());
				System.out.println(column.getCells().size());
				System.out.println("\n % valori totali: " + column.getCells().size());
				System.out.println("\n % valori distinti: " + percValoriDist + "\n");

				queryManager.setQueryColumn(column);

				Collection<Integer> docs;


				docs= queryManager.executeQuery(10);

				System.out.println(queryManager.getStats());

				QueryMain.printQueryResults(docs, queryManager);


				docs = queryManager.executeQueryNoDuplicates(10);
				System.out.println(queryManager.getStats());
				QueryMain.printQueryResults(docs, queryManager);



			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	private static void printQueryResults(Collection<Integer> docs, QueryManager queryManager){
		Document doc;
		for(Integer docID : docs){

			doc = queryManager.getDocumentById(docID);
			IndexableField f = doc.getField(IndexManager.TABLE_ID_FIELD_TYPE);
			System.out.print(f.stringValue()  + ": ");

			for(IndexableField field : doc.getFields(IndexManager.ELEMENT_FIELD_TYPE)){
				System.out.print(field.stringValue() + "\t");
			}
			System.out.println();
		}
	}
}
