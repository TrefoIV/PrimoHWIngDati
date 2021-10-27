package stats;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jsonParser.Cell;
import jsonParser.CellCollection;
import jsonParser.Table;

public class Stats {
	private int numeroTabelle;
	private int numeroTotaleColonne;
	private int numeroMedioColonne;
	private int numeroTotaleRighe;
	private int numeroMedioRighe;
	private int numeroTotaleValoriNulli;
	private int numeroMedioValoriNulli;
	private TreeMap<Integer, Integer> distribuzioneRighe;
	private TreeMap<Integer, Integer> distribuzioneColonne;
	private TreeMap<Integer, Integer> distribuzioneValoriDistinti;

	public Stats() {
		this.distribuzioneColonne = new TreeMap<Integer, Integer>();
		this.distribuzioneRighe = new TreeMap<Integer, Integer>();
		this.distribuzioneValoriDistinti = new TreeMap<Integer, Integer>();
	}


	public void analizza(Table table) {
		/*
		 * this.numeroTabelle++;
		 * 
		 * this.numeroTotaleColonne += table.getMaxDimensions().getColumn();
		 * this.numeroTotaleRighe += table.getMaxDimensions().getRow();
		 * this.valoriNulliPerTabella(table.getColumns());
		 * 
		 * int numeroColonneTabellaCorrente = table.getMaxDimensions().getColumn(); if
		 * (this.distribuzioneColonne.containsKey(numeroColonneTabellaCorrente)) { int
		 * tempColonne = this.distribuzioneColonne.get(numeroColonneTabellaCorrente);
		 * tempColonne++; this.distribuzioneColonne.put(numeroColonneTabellaCorrente,
		 * tempColonne); } else
		 * this.distribuzioneColonne.put(numeroColonneTabellaCorrente, 1);
		 * 
		 * int numeroRigheTabellaCorrente = table.getMaxDimensions().getRow(); if
		 * (this.distribuzioneRighe.containsKey(numeroRigheTabellaCorrente)) { int
		 * tempRighe = this.distribuzioneRighe.get(numeroRigheTabellaCorrente);
		 * tempRighe++; this.distribuzioneRighe.put(numeroRigheTabellaCorrente,
		 * tempRighe); } else this.distribuzioneRighe.put(numeroRigheTabellaCorrente,
		 * 1); this.distribuzioneValori(table.getColumns());
		 */

		this.numeroTabelle++;

		this.valoriNulliPerTabella(table.getColumns());

		int numeroColonneTabellaCorrente = table.getColumns().size();
		if (this.distribuzioneColonne.containsKey(numeroColonneTabellaCorrente)) {
			int tempColonne = this.distribuzioneColonne.get(numeroColonneTabellaCorrente);
			tempColonne++;
			this.distribuzioneColonne.put(numeroColonneTabellaCorrente, tempColonne);
		} else
			this.distribuzioneColonne.put(numeroColonneTabellaCorrente, 1);

		//metodo 1 per calcolare il numero delle righe
		int numeroRigheTabellaCorrente = table.getColumns().get(table.getColumns().keySet().iterator().next()).getCells().size();

		//metodo 2 per calcolare il numero delle righe (itero ma non serve)
		/*int numeroRigheTabellaCorrente = 0;

		for (Integer key : table.getColumns().keySet()) {
			numeroRigheTabellaCorrente = table.getColumns().get(key).getCells().size(); 
		}*/

		if (this.distribuzioneRighe.containsKey(numeroRigheTabellaCorrente)) {
			int tempRighe = this.distribuzioneRighe.get(numeroRigheTabellaCorrente);
			tempRighe++;
			this.distribuzioneRighe.put(numeroRigheTabellaCorrente, tempRighe);
		} else
			this.distribuzioneRighe.put(numeroRigheTabellaCorrente, 1);
		this.distribuzioneValori(table.getColumns());

		this.numeroTotaleColonne += numeroColonneTabellaCorrente;
		this.numeroTotaleRighe += numeroRigheTabellaCorrente;

	}

	public void calcoloNumeriMedi() {
		this.numeroMedioColonne = this.numeroTotaleColonne / this.numeroTabelle;
		this.numeroMedioRighe = this.numeroTotaleRighe / this.numeroTabelle;
		this.numeroMedioValoriNulli = this.numeroTotaleValoriNulli / this.numeroTabelle;
	}

	public void valoriNulliPerTabella(Map<Integer, CellCollection> columns) {
		for (int i : columns.keySet()) {
			for (Cell cell : columns.get(i).getCells()) {
				if (cell.isNULLValue())
					this.numeroTotaleValoriNulli++;
			}
		}
	}

	public void distribuzioneValori(Map<Integer, CellCollection> columns) {
		HashMap<Integer, Set<String>> valoriDistintiPerColonna = new HashMap<Integer, Set<String>>();
		// popolo una mappa associando ad ogni colonna l'insieme dei termini distinti
		for (int i : columns.keySet()) {
			for (Cell cell : columns.get(i).getCells()) {
				int mapKey = cell.getCoordinates().getColumn();
				if (valoriDistintiPerColonna.containsKey(mapKey)) {
					valoriDistintiPerColonna.get(mapKey).add(cell.getCleanedText());
				} else {
					Set<String> valoriDistinti = new HashSet<String>();
					valoriDistinti.add(cell.getCleanedText());
					valoriDistintiPerColonna.put(mapKey, valoriDistinti);
				}
			}
		}
		// itero per la mappa e conto quante colonne hanno lo stesso numero di valori
		// distinti
		for (Set<String> set : valoriDistintiPerColonna.values()) {
			if (this.distribuzioneValoriDistinti.containsKey(set.size())) {
				int tempValoriDistinti = this.distribuzioneValoriDistinti.get(set.size());
				tempValoriDistinti++;
				this.distribuzioneValoriDistinti.put(set.size(), tempValoriDistinti);
			} else
				this.distribuzioneValoriDistinti.put(set.size(), 1);
		}
	}

	public int getNumeroTabelle() {
		return numeroTabelle;
	}

	public void setNumeroTabelle(int numeroTabelle) {
		this.numeroTabelle = numeroTabelle;
	}

	public int getNumeroMedioColonne() {
		return numeroMedioColonne;
	}

	public void setNumeroMedioColonne(int numeroMedioColonne) {
		this.numeroMedioColonne = numeroMedioColonne;
	}

	public int getNumeroMedioRighe() {
		return numeroMedioRighe;
	}

	public void setNumeroMedioRighe(int numeroMedioRighe) {
		this.numeroMedioRighe = numeroMedioRighe;
	}

	public int getNumeroMedioValoriNulli() {
		return numeroMedioValoriNulli;
	}

	public void setNumeroMedioValoriNulli(int numeroMedioValoriNulli) {
		this.numeroMedioValoriNulli = numeroMedioValoriNulli;
	}

	public TreeMap<Integer, Integer> getDistribuzioneRighe() {
		return distribuzioneRighe;
	}

	public void setDistribuzioneRighe(TreeMap<Integer, Integer> distribuzioneRighe) {
		this.distribuzioneRighe = distribuzioneRighe;
	}

	public TreeMap<Integer, Integer> getDistribuzioneColonne() {
		return distribuzioneColonne;
	}

	public void setDistribuzioneColonne(TreeMap<Integer, Integer> distribuzioneColonne) {
		this.distribuzioneColonne = distribuzioneColonne;
	}

	public TreeMap<Integer, Integer> getDistribuzioneValoriDistinti() {
		return distribuzioneValoriDistinti;
	}

	public void setDistribuzioneValoriDistinti(TreeMap<Integer, Integer> distribuzioneValoriDistinti) {
		this.distribuzioneValoriDistinti = distribuzioneValoriDistinti;
	}

	@Override
	public String toString() {
		return "Stats:\nnumeroTabelle=" + numeroTabelle + "\n" + "numeroMedioColonne=" + numeroMedioColonne + "\n"
				+ "numeroMedioRighe=" + numeroMedioRighe + "\n" + "numeroMedioValoriNulli=" + numeroMedioValoriNulli
				+ "\n" + "distribuzioneRighe=" + distribuzioneRighe + "\n" + "distribuzioneColonne="
				+ distribuzioneColonne + "\n" + "distribuzioneValoriDistinti=" + distribuzioneValoriDistinti;
	}

}
