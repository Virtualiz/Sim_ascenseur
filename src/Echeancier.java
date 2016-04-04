import java.util.LinkedList;

public class Echeancier extends Constantes {
	// La liste triee chronologiquement des evenements du simulateur.

	private LinkedList<Evenement> listeEvenements;
	// La liste triee des evenements.

	public Echeancier() {
		listeEvenements = new LinkedList<Evenement>();
	}

	public boolean estVide() {
		return listeEvenements.isEmpty();
	}

	public void ajouter(Evenement e) {
		int pos = 0;
		while (pos < listeEvenements.size()) {
			if (listeEvenements.get(pos).date >= e.date) {
				listeEvenements.add(pos, e);
				return;
			} else {
				pos++;
			}
		}
		listeEvenements.add(pos, e);
	}

	public Evenement retourneEtEnlevePremier() {
		Evenement e = listeEvenements.getFirst();
		listeEvenements.removeFirst();
		return e;
	}

	public void afficheLaSituation(Immeuble ascenseur) {
		System.out.print("Echeancier = ");
		int index = 0;
		while (index < listeEvenements.size()) {
			listeEvenements.get(index).affiche(ascenseur);
			index++;
			if (index < listeEvenements.size()) {
				System.out.print(',');
			}
		}
		System.out.println("");
	}
	
	public int nbEvenement(){
		return listeEvenements.size();
	}
	
	public Evenement evenementIndex(int i){
		return listeEvenements.get(i);
	}

	public void decalerFPC() {
		int nb = this.nbEvenement(),i=0;
		Evenement e;
		
		while(i<nb-1){
			e = this.evenementIndex(i);
			if(e instanceof EvenementFermeturePorteCabine){
				e.date+=tempsPourEntrerDansLaCabine;
				//reorganiser echeancier
				listeEvenements.sort(new EvenementComparator());
				break;
			}
			else{
				i++;
			}
		}
		
	}

}
