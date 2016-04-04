public class Immeuble extends Constantes {

	private Etage[] tableauDesEtages;

	public Cabine cabine; // de l'ascenseur.

	private Etage niveauDuSol; // le niveau 0.

	public long cumulDesTempsDeTransport = 0;

	public long nombreTotalDesPassagersSortis = 0;

	public Etage etageLePlusBas() {
		Etage res = tableauDesEtages[0];
		assert res != null;
		return res ;
	}

	public Etage etageLePlusHaut() {
		Etage res = tableauDesEtages[tableauDesEtages.length - 1];
		assert res != null;
		return res;
	}

	public Etage niveauDuSol() {
		assert etageLePlusHaut().numero() >= niveauDuSol.numero();
		assert etageLePlusBas().numero() <= niveauDuSol.numero();
		assert niveauDuSol != null;
		return niveauDuSol;
	}

	public Immeuble(Echeancier echeancier) {
		Etage e = null;
		tableauDesEtages = new Etage[8];
		int n = -2;
		for (int i = 0; i < tableauDesEtages.length; i++) {
			int fa = 41; // Pour le niveau 0 en dixieme de secondes:
				if (n != 0) {
					fa = fa * (tableauDesEtages.length - 1);
				}
				e = new Etage(n, fa);
				tableauDesEtages[i] = e;
				if (n == 0) {
					niveauDuSol = e;
				}
				n++;
		}
		for (int i = 0; i < tableauDesEtages.length; i++) {
			Etage etage = tableauDesEtages[i];
			long date = etage.arriveeSuivante();
			echeancier.ajouter(new EvenementArriveePassagerPalier(date, etage));
		}
		Etage ed = tableauDesEtages[tableauDesEtages.length / 2];
		cabine = new Cabine(ed);
		echeancier.ajouter(new EvenementPassageCabinePalier(tempsPourBougerLaCabineDUnEtage*2, etage(ed.numero() - 1)));
	}

	public void afficheLaSituation() {
		System.out.print("Immeuble (mode ");
		if (isModeParfait()) {
			System.out.print("parfait");
		} else {
			System.out.print("infernal");
		}
		System.out.println("):");
		int i = etageLePlusHaut().numero();
		while (i >= etageLePlusBas().numero()) {
			etage(i).afficheLaSituation(this);
			i--;
		}
		cabine.afficheLaSituation();
		System.out.println("Cumul des temps de transport: " + cumulDesTempsDeTransport);
		System.out.println("Nombre total des passagers sortis: " + nombreTotalDesPassagersSortis);
		System.out.println("Ratio cumul temps / nb passagers : " +
				(nombreTotalDesPassagersSortis == 0 ? 0 : (cumulDesTempsDeTransport / nombreTotalDesPassagersSortis)));
	}

	public Etage etage(int i) {
		assert etageLePlusBas().numero() <= i : "ERREUR trop haut";
		assert etageLePlusHaut().numero() >= i : "ERREUR trop bas";
		Etage res = tableauDesEtages[i - etageLePlusBas().numero()];
		assert res.numero() == i;
		return res;
	}

	public int nbEtages() {
		int res = tableauDesEtages.length;
		assert res == (etageLePlusHaut().numero() - etageLePlusBas().numero() + 1);
		return res;
	}
}
