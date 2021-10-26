package main;

import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.json.simple.parser.JSONParser;

import indexManager.IndexManager;
import jsonParser.Table;
import stats.Stats;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {

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

/*				PostingsEnum postingsEnum = leafReader.postings(term, PostingsEnum.ALL);
				while (postingsEnum.nextDoc() != DocIdSetIterator.NO_MORE_DOCS) {
					int freq = postingsEnum.freq();
					System.out.printf("term: %s, freq: %d,", term.toString(), freq);
					while (freq > 0) {
						System.out.printf(" nextPosition: %d,", postingsEnum.nextPosition());
						System.out.printf(" startOffset: %d, endOffset: %d",
								postingsEnum.startOffset(), postingsEnum.endOffset());
						freq--;
					}
					System.out.println();
				}*/

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
