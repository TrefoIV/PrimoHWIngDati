package indexManager;


import jsonParser.CellCollection;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.TreeMap;

public class MergeListImpl {

    private CellCollection column;

    public void setColumn(CellCollection column){
        this.column = column;
    }

    public  Collection<Integer> executeQuery(){

        try {
            Path path = Paths.get("index");
            Directory dir = FSDirectory.open(path);
            IndexReader r = DirectoryReader.open(dir);
            IndexSearcher searcher = new IndexSearcher(r);

            Term term = new Term(IndexManager.ELEMENT_FIELD_TYPE, "John Victor Mackay");


            TopDocs docs = searcher.search(new TermQuery(term), Integer.MAX_VALUE);
            for (ScoreDoc doc : docs.scoreDocs) {
                int docid = doc.doc;
                System.out.println(docid);
            }
            

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
