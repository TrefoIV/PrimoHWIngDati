package indexManager;


import jsonParser.Cell;
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
import java.util.*;

public class MergeListImpl {

    private List<Cell> column;

    public void setColumn(CellCollection column){
        this.column = column.getCells();
    }

    public  Collection<Integer> executeQuery(){

        if(column == null || column.isEmpty())
            return new ArrayList<>();
        IndexReader reader;
        try {
            Path path = Paths.get("index");
            Directory dir = FSDirectory.open(path);
            reader = DirectoryReader.open(dir);
        }catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        IndexSearcher searcher = new IndexSearcher(reader);
        Term term = new Term(IndexManager.ELEMENT_FIELD_TYPE, this.column.remove(0).getCleanedText());
        TopDocs docs;
        try{
            docs = searcher.search(new TermQuery(term), Integer.MAX_VALUE);
        }catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        HashMap<Integer, Integer> doc2count = new HashMap<>((int) docs.totalHits.value*2);
        for (ScoreDoc doc : docs.scoreDocs) {
            this.putCountMap(doc, doc2count);
        }
        for (Cell c : this.column){
            term = new Term(IndexManager.ELEMENT_FIELD_TYPE, c.getCleanedText());
            try{
                docs = searcher.search(new TermQuery(term), Integer.MAX_VALUE);
            }catch (IOException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
            for (ScoreDoc doc : docs.scoreDocs) {
                this.putCountMap(doc, doc2count);
            }
        }


        TreeSet<Integer> orderedResult = new TreeSet<>(new DocsComparator(doc2count));
        orderedResult.addAll(doc2count.keySet());



        return orderedResult;
    }

    private void putCountMap(ScoreDoc doc, HashMap<Integer, Integer> doc2count){
        if(doc2count == null) return;
        int docID = doc.doc;
        if(doc2count.containsKey(docID))
            doc2count.put(docID, doc2count.get(docID) +1);
        else
            doc2count.put(docID, 1);

    }

    private class DocsComparator implements Comparator<Integer> {
        private final HashMap<Integer, Integer> docs2count;

        public DocsComparator(HashMap<Integer, Integer> docs2count){
            this.docs2count = docs2count;
        }
        @Override
        public int compare(Integer doc1, Integer doc2) {
            return Integer.compare(this.docs2count.get(doc2), this.docs2count.get(doc1));
        }
    }
}
