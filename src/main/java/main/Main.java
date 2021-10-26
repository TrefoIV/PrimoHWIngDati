package main;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.LeafReader;
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

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
