package indexManagment;

import jsonParser.CellCollection;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

public class QueryManager {

    private MergeListImpl mergeListImpl;
    private IndexReader reader;

    public QueryManager(){
        this.mergeListImpl = new MergeListImpl();
        try {
            Path path = Paths.get("index");
            Directory dir = FSDirectory.open(path);
            this.reader = DirectoryReader.open(dir);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setQueryColumn(CellCollection column){
        this.mergeListImpl.setColumn(column);
    }
    public Collection<Integer> executeQuery(int top_k){
        return this.mergeListImpl.executeQuery(this.reader, top_k);
    }
    public Collection<Integer> executeQueryNoDuplicates(int top_k){return this.mergeListImpl.executeQueryNoDuplicates(this.reader, top_k);}

    public Document getDocumentById(Integer id){
        Document result = null;
        try {
            result = this.reader.document(id);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  result;
    }
}
