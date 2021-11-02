package indexManagment;

import jsonParser.Cell;
import jsonParser.CellCollection;
import jsonParser.Table;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class IndexManager {

    public static final String ELEMENT_FIELD_TYPE = "element";
    public static final String TABLE_ID_FIELD_TYPE = "table";
    private static final int MAX_TABLES_NUMBER_PER_COMMIT = 10000;
    private IndexWriter writer;
    private int addedTableCount;


    public IndexManager(){
        Path path = Paths.get("index");
        this.addedTableCount = 0;
        try {
            Directory dir = FSDirectory.open(path);
            IndexWriterConfig config = new IndexWriterConfig();
//            config.setCodec(new SimpleTextCodec());
            this.writer = new IndexWriter(dir, config);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addTable(Table table){
        if(table == null) return;
        this.addedTableCount++;
        Document doc;

        for(CellCollection column : table.getColumns().values()){
            doc = new Document();
            doc.add(new StoredField(IndexManager.TABLE_ID_FIELD_TYPE, table.getId()));
            for(Cell cell : column.getCells()){
                if(cell.isNULLValue() || cell.getHeader()) continue;
                doc.add(new StringField(IndexManager.ELEMENT_FIELD_TYPE, cell.getCleanedText(), Field.Store.YES));
            }
            try {
                this.writer.addDocument(doc);
                if(this.addedTableCount == this.MAX_TABLES_NUMBER_PER_COMMIT){
                    writer.commit();
                    this.addedTableCount = 0;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

    public void closeManager(){
        if(this.addedTableCount != 0) {
            try {
                this.writer.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
