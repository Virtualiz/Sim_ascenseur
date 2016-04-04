public class EvenementArriveePassagerPalier extends Evenement {// moment precis où un passager arrive devant les portes d'un palier

	private Etage etageDeDepart;

	public EvenementArriveePassagerPalier(long d, Etage edd) {
		super(d);
		assert edd != null;
		etageDeDepart = edd;
	}

	public void afficheDetails(Immeuble immeuble) {
		System.out.print("APP ");
		System.out.print(etageDeDepart.numero());
	}

	public void traiter(Immeuble immeuble, Echeancier echeancier) {
		assert etageDeDepart != null;
		Passager p = new Passager (date, etageDeDepart, immeuble);
		Etage e = immeuble.etage(etageDeDepart.numero());
		e.ajouter(p);
		Cabine cabine = immeuble.cabine;
		if(cabine.etage==etageDeDepart && cabine.porteOuverte && p.sens()==cabine.status()){
			echeancier.decalerFPC();
			//faire entrer le passager
			e.faireMonterPassagers(immeuble.cabine);
		}
		long d = this.date + e.arriveeSuivante();
		assert d > this.date;
		echeancier.ajouter(new EvenementArriveePassagerPalier(d, etageDeDepart));
	}
	
	public int getNumEtage(){
		return etageDeDepart.numero();
	}
}
