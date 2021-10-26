package indexManager;

import jsonParser.Cell;
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
        HashMap<Integer, Document> col2docs = new HashMap<>();
        Document doc;
        int col;
        for(Cell cell : table.getCells().getCells()){
            col = cell.getCoordinates().getColumn();

            if(col2docs.containsKey(col)){
                doc = col2docs.get(col);
                doc.add(new StringField("el", cell.getCleanedText(), Field.Store.YES));
            }
            else{
                doc = new Document();
                doc.add(new StoredField("table", table.getId()));
                doc.add(new StringField("el", cell.getCleanedText(), Field.Store.YES));
                col2docs.put(col, doc);
            }
        }
        try {
            for(Document d : col2docs.values()){
                writer.addDocument(d);

            }

           if(this.addedTableCount == this.MAX_TABLES_NUMBER_PER_COMMIT){
                writer.commit();
                this.addedTableCount = 0;
            }

        } catch (IOException e) {
            e.printStackTrace();
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
