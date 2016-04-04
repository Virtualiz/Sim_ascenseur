public class EvenementFermeturePorteCabine extends Evenement { //moment precis où les portes terminent de se fermer

	public EvenementFermeturePorteCabine(long d) {
		super(d);
	}

	public void afficheDetails(Immeuble immeuble) {
		System.out.print("FPC");
	}

	public void traiter(Immeuble immeuble, Echeancier echeancier) {
		Cabine cabine = immeuble.cabine;
		assert cabine.porteOuverte;
		Etage etage = cabine.etage;
		Etage newEtage;
		if(cabine.status() == '^') {
			if (etage.numero() == immeuble.etageLePlusHaut().numero()) {
				cabine.changerStatus('v');
				newEtage = immeuble.etage(etage.numero()-1);
			} else newEtage = immeuble.etage(etage.numero()+1);
		} else {
			if (etage.numero() == immeuble.etageLePlusBas().numero()) {
				cabine.changerStatus('^');
				newEtage = immeuble.etage(etage.numero()+1);
			} else newEtage = immeuble.etage(etage.numero()-1);
		}
		cabine.fermerPortes();
		echeancier.ajouter(new EvenementPassageCabinePalier(this.date + tempsPourBougerLaCabineDUnEtage, newEtage));
		assert ! cabine.porteOuverte;
	}

}
