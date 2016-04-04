import java.util.*;

public class EvenementOuverturePorteCabine extends Evenement {// moment precis où les portes de la cabine terminent de s'ouvrir

	public EvenementOuverturePorteCabine(long d) {
		super(d);
	}

	public void afficheDetails(Immeuble immeuble) {
		System.out.print("OPC");
	}

	public void traiter(Immeuble immeuble, Echeancier echeancier) {
		Cabine cabine = immeuble.cabine;
		Etage etage = cabine.etage;
		assert ! cabine.porteOuverte;
		cabine.ouvrirPorte();
		assert cabine.porteOuverte;
		long tps = tempsPourFermerLesPortes;
		//On verifie s'il y a des passagers dans l'ascenseur qui veulent descendre ou s'il y a des passagers qui veulent monter dans l'ascenseur
		boolean etagePossedePassagers = etage.possedePassager();
		boolean cabinePossedePassagers = cabine.possedePassager() && cabine.possedePassagerDescente(etage.numero());
		if (cabinePossedePassagers) {
			int nbPassagersDescendus = cabine.faireDescendre(etage.numero(), this.date, immeuble);
			tps = tps + nbPassagersDescendus*tempsPourSortirDeLaCabine;
			immeuble.nombreTotalDesPassagersSortis += nbPassagersDescendus;
		}
		if (etagePossedePassagers) {
			tps = tps + etage.faireMonterPassagers(cabine)*tempsPourEntrerDansLaCabine;
			// parcours de l'echeancier pour savoir si une personne arrive avant le FPC -- mis dans APP
			/*int nb = echeancier.nbEvenement(),i=0;
			Evenement e = echeancier.evenementIndex(i);
			boolean futurPassager = false;
			while(!futurPassager && date+tps>e.getDate() && i<nb-1){
				if(e instanceof EvenementArriveePassagerPalier && ((EvenementArriveePassagerPalier) e).getNumEtage()==etage.numero()) futurPassager = true;
				else{
					i++;
					e = echeancier.evenementIndex(i);
				}
			}
			if(futurPassager) tps += tempsPourFermerLesPortes;*/
		}
		long d = this.date + tps;
		echeancier.ajouter(new EvenementFermeturePorteCabine(d));
	}

}
