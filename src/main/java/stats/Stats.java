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
	private TreeMap<Integer, Integer> percentualeValoriNulliTabella;
	private TreeMap<Integer, Integer> percentualeValoriDistintiColonna;
	// tiene conto del numero di valori nulli ogni volta che si vede una tabella
	// (azzerata ad ogni iterazione)
	private int valoriNulliPerTabella;
	private int maxNumeroRigheTabellaCorrente;
	private boolean salvare0;
	private boolean salvare25;
	private boolean salvare50;
	private boolean salvare75;

	public Stats() {
		this.salvare0 = false;
		this.salvare25 = false;
		this.salvare50 = false;
		this.salvare75 = false;
		this.distribuzioneColonne = new TreeMap<Integer, Integer>();
		this.distribuzioneRighe = new TreeMap<Integer, Integer>();
		this.distribuzioneValoriDistinti = new TreeMap<Integer, Integer>();
		this.percentualeValoriNulliTabella = new TreeMap<Integer, Integer>();
		this.percentualeValoriDistintiColonna = new TreeMap<Integer, Integer>();
	}

	public void analizza(Table table) {

		this.numeroTabelle++;

		this.valoriNulliPerTabella(table.getColumns());

		int numeroColonneTabellaCorrente = table.getColumns().size();
		if (this.distribuzioneColonne.containsKey(numeroColonneTabellaCorrente)) {
			int tempColonne = this.distribuzioneColonne.get(numeroColonneTabellaCorrente);
			tempColonne++;
			this.distribuzioneColonne.put(numeroColonneTabellaCorrente, tempColonne);
		} else
			this.distribuzioneColonne.put(numeroColonneTabellaCorrente, 1);

		// metodo per calcolare il numero delle righe - una tab potrebbe avere righe di
		// lunghezza diversa data l'eliminazione dei campi empty
		int numeroRigheTabellaCorrente = 0;
		this.maxNumeroRigheTabellaCorrente = 0;
		for (Integer key : table.getColumns().keySet()) {
			numeroRigheTabellaCorrente = table.getColumns().get(key).getCells().size();
			if (maxNumeroRigheTabellaCorrente < numeroRigheTabellaCorrente) {
				maxNumeroRigheTabellaCorrente = numeroRigheTabellaCorrente;
			}
		}

		if (this.distribuzioneRighe.containsKey(maxNumeroRigheTabellaCorrente)) {
			int tempRighe = this.distribuzioneRighe.get(maxNumeroRigheTabellaCorrente);
			tempRighe++;
			this.distribuzioneRighe.put(maxNumeroRigheTabellaCorrente, tempRighe);
		} else
			this.distribuzioneRighe.put(maxNumeroRigheTabellaCorrente, 1);
		this.distribuzioneValori(table.getColumns());

		this.numeroTotaleColonne += numeroColonneTabellaCorrente;
		this.numeroTotaleRighe += maxNumeroRigheTabellaCorrente;
		this.percentualeValoriNulliTabella(table);
		this.valoriNulliPerTabella = 0;

	}

	public void calcoloNumeriMedi() {
		this.numeroMedioColonne = this.numeroTotaleColonne / this.numeroTabelle;
		this.numeroMedioRighe = this.numeroTotaleRighe / this.numeroTabelle;
		this.numeroMedioValoriNulli = this.numeroTotaleValoriNulli / this.numeroTabelle;
	}

	public void valoriNulliPerTabella(Map<Integer, CellCollection> columns) {
		for (int i : columns.keySet()) {
			for (Cell cell : columns.get(i).getCells()) {
				if (cell.isNULLValue()) {
					this.numeroTotaleValoriNulli++;
					this.valoriNulliPerTabella++;
				}
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
		double dimensioneColonna = (double) columns.get(0).getCells().size();

		int percDistinti = (int) ( ( ((double)valoriDistintiPerColonna.get(0).size()) / dimensioneColonna) * 100 );

		if (percDistinti == 0)
			salvare0 = true;
		else if (percDistinti == 25)
			salvare25 = true;
		else if (percDistinti == 50)
			salvare50 = true;
		else if (percDistinti == 75)
			salvare75 = true;


		for (Set<String> i : valoriDistintiPerColonna.values()) {
			double nValoriDistinti = (double) i.size();
			percDistinti = (int) ((nValoriDistinti / dimensioneColonna) * 100);
			int v = this.percentualeValoriDistintiColonna.getOrDefault(percDistinti, 0);
			this.percentualeValoriDistintiColonna.put(percDistinti, v + 1);

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

	public void percentualeValoriNulliTabella(Table table) {
		double valNull = (double) this.valoriNulliPerTabella;
		double cellTot = (double) (table.getColumns().size() * this.maxNumeroRigheTabellaCorrente);
		int tempPerc = (int) ((valNull / cellTot) * 100);
		// System.out.println(tempPerc);
		if (this.percentualeValoriNulliTabella.get(tempPerc) == null) {
			this.percentualeValoriNulliTabella.put(tempPerc, 1);
		} else {
			int tempNtab = this.percentualeValoriNulliTabella.get(tempPerc);
			tempNtab++;
			this.percentualeValoriNulliTabella.put(tempPerc, tempNtab);
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

	public int getNumeroTotaleColonne() {
		return numeroTotaleColonne;
	}

	public void setNumeroTotaleColonne(int numeroTotaleColonne) {
		this.numeroTotaleColonne = numeroTotaleColonne;
	}

	public int getNumeroTotaleRighe() {
		return numeroTotaleRighe;
	}

	public void setNumeroTotaleRighe(int numeroTotaleRighe) {
		this.numeroTotaleRighe = numeroTotaleRighe;
	}

	public int getNumeroTotaleValoriNulli() {
		return numeroTotaleValoriNulli;
	}

	public void setNumeroTotaleValoriNulli(int numeroTotaleValoriNulli) {
		this.numeroTotaleValoriNulli = numeroTotaleValoriNulli;
	}

	public TreeMap<Integer, Integer> getPercentualeValoriNulliTabella() {
		return percentualeValoriNulliTabella;
	}

	public void setPercentualeValoriNulliTabella(TreeMap<Integer, Integer> percentualeValoriNulliTabella) {
		this.percentualeValoriNulliTabella = percentualeValoriNulliTabella;
	}

	public TreeMap<Integer, Integer> getPercentualeValoriDistintiColonna() {
		return percentualeValoriDistintiColonna;
	}

	public void setPercentualeValoriDistintiColonna(TreeMap<Integer, Integer> percentualeValoriDistintiColonna) {
		this.percentualeValoriDistintiColonna = percentualeValoriDistintiColonna;
	}

	public int getValoriNulliPerTabella() {
		return valoriNulliPerTabella;
	}

	public void setValoriNulliPerTabella(int valoriNulliPerTabella) {
		this.valoriNulliPerTabella = valoriNulliPerTabella;
	}

	public int getMaxNumeroRigheTabellaCorrente() {
		return maxNumeroRigheTabellaCorrente;
	}

	public void setMaxNumeroRigheTabellaCorrente(int maxNumeroRigheTabellaCorrente) {
		this.maxNumeroRigheTabellaCorrente = maxNumeroRigheTabellaCorrente;
	}

	public boolean isSalvare0() {
		return salvare0;
	}

	public void setSalvare0(boolean salvare0) {
		this.salvare0 = salvare0;
	}

	public boolean isSalvare25() {
		return salvare25;
	}

	public void setSalvare25(boolean salvare25) {
		this.salvare25 = salvare25;
	}

	public boolean isSalvare50() {
		return salvare50;
	}

	public void setSalvare50(boolean salvare50) {
		this.salvare50 = salvare50;
	}

	public boolean isSalvare75() {
		return salvare75;
	}

	public void setSalvare75(boolean salvare75) {
		this.salvare75 = salvare75;
	}

	@Override
	public String toString() {
		/*
		 * return "Stats [numeroTabelle=" + numeroTabelle + ", numeroTotaleColonne=" +
		 * numeroTotaleColonne + ", numeroMedioColonne=" + numeroMedioColonne +
		 * ", numeroTotaleRighe=" + numeroTotaleRighe + ", numeroMedioRighe=" +
		 * numeroMedioRighe + ", numeroTotaleValoriNulli=" + numeroTotaleValoriNulli +
		 * ", numeroMedioValoriNulli=" + numeroMedioValoriNulli +
		 * ", distribuzioneRighe=" + distribuzioneRighe + ", distribuzioneColonne=" +
		 * distribuzioneColonne + ", distribuzioneValoriDistinti=" +
		 * distribuzioneValoriDistinti + ", percentualeValoriNulliTabella=" +
		 * percentualeValoriNulliTabella + "]";
		 */
		return "percentualeValoriDistinti\n" + this.percentualeValoriDistintiColonna;
	}

}
