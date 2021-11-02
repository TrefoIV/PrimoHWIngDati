package indexManagment;


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
import stats.QueryStats;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MergeListImpl {

    private List<Cell> column;
    private QueryStats queryStats;

    public void setColumn(CellCollection column){
        this.column = column.getCells();
    }

    public  Collection<Integer> executeQuery(IndexReader reader, int k_top){

        if(column == null || column.isEmpty())
            return new ArrayList<>();
        long tot_lucene_query_time = 0;
        long query_start = System.currentTimeMillis();
        IndexSearcher searcher = new IndexSearcher(reader);
        Term term;
        TopDocs docs;
        HashMap<Integer, Integer> doc2count = new HashMap<>();
        long n_elements = this.column.size();
        for (Cell c : this.column){
            term = new Term(IndexManager.ELEMENT_FIELD_TYPE, c.getCleanedText());
            try{
                long start = System.currentTimeMillis();
                docs = searcher.search(new TermQuery(term), Integer.MAX_VALUE);
                long end = System.currentTimeMillis();
                tot_lucene_query_time += end - start;
            }catch (IOException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
            for (ScoreDoc doc : docs.scoreDocs) {
                this.putCountMap(doc.doc, doc2count);
            }
        }


        TreeSet<Integer> orderedResult = new TreeSet<>(new DocsComparator(doc2count));
        orderedResult.addAll(doc2count.keySet());
        long query_end = System.currentTimeMillis();
        this.queryStats = new QueryStats(tot_lucene_query_time, ((double) tot_lucene_query_time/n_elements), query_end-query_start);
        return this.extractTop(orderedResult, k_top);

    }


    public Collection<Integer> executeQueryNoDuplicates(IndexReader reader, int k_top){

        if(column == null || column.isEmpty())
            return new ArrayList<>();
        long tot_lucene_query_time = 0;
        long query_start = System.currentTimeMillis();
        IndexSearcher searcher = new IndexSearcher(reader);

        HashMap<String, Integer> termCounts = new HashMap<>();
        for(Cell c : this.column){
            int v = termCounts.getOrDefault(c.getCleanedText(), 0);
            termCounts.put(c.getCleanedText(), v+1);
        }
        HashSet<Cell> cellSet = new HashSet<>(this.column);
        long n_elements = cellSet.size();
        Term term;
        TopDocs docs;
        HashMap<Integer, Integer> doc2count = new HashMap<>();

        for (Cell c : cellSet){
            term = new Term(IndexManager.ELEMENT_FIELD_TYPE, c.getCleanedText());
            try{
                long start = System.currentTimeMillis();
                docs = searcher.search(new TermQuery(term), Integer.MAX_VALUE);
                long end = System.currentTimeMillis();
                tot_lucene_query_time += end - start;
            }catch (IOException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
            for (ScoreDoc doc : docs.scoreDocs) {
                int id = doc.doc;
                int v = doc2count.getOrDefault(id, 0);
                doc2count.put(id,v + termCounts.get(c.getCleanedText()));

            }
        }


        TreeSet<Integer> orderedResult = new TreeSet<>(new DocsComparator(doc2count));
        orderedResult.addAll(doc2count.keySet());
        long query_end = System.currentTimeMillis();
        this.queryStats = new QueryStats(tot_lucene_query_time, ((double) tot_lucene_query_time/n_elements), query_end-query_start);
        return this.extractTop(orderedResult, k_top);
    }


    private void putCountMap(int id, HashMap<Integer, Integer> doc2count){
        if(doc2count == null) return;
        int v = doc2count.getOrDefault(id, 0);
        doc2count.put(id,v +1);

    }


    private Collection<Integer> extractTop(NavigableSet<Integer> set, int k_top){
        Collection<Integer> top_docs = new ArrayList<>();
        int i = 0;
        while(!set.isEmpty() && i < k_top) {
            i++;
            top_docs.add(set.pollFirst());
        }

        return top_docs;
    }

    public QueryStats getQueryStats() {
        return queryStats;
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
