public class Cabine extends Constantes {

	public Etage etage; // actuel

	public boolean porteOuverte;

	private char status; // '-' ou 'v' ou '^'

	private Passager[] tableauPassager;

	public Cabine(Etage e) {
		assert e != null;
		etage = e;
		tableauPassager = new Passager[nombreDePlacesDansLaCabine];
		porteOuverte = false;
		status = 'v';
	}

	public void afficheLaSituation() {
		System.out.print("Contenu de la cabine: ");
		for (int i = tableauPassager.length - 1; i >= 0 ; i--) {
			Passager p = tableauPassager[i];
			if (p != null) {
				p.affiche();
				System.out.print(' ');
			}
		}
		assert (status == 'v') || (status == '^') || (status == '-');
		System.out.println("\nStatus de la cabine: " + status);
	}

	public char status() {
		assert (status == 'v') || (status == '^') || (status == '-');
		return status;
	}

	public void changerStatus(char s){
		assert (s == 'v') || (s == '^') || (s == '-');
		status = s;
	}

	public void ouvrirPorte(){
		assert !this.porteOuverte;
		this.porteOuverte = true;
	}

	public boolean possedePassagerDescente(int numero) { // retourne si un passager veut descendre a l'etage numero
		assert possedePassager();
		int i = 0;
		boolean passagerDescente = false;
		while(!passagerDescente && i<nombreDePlacesDansLaCabine){
			if(tableauPassager[i]!=null){
				passagerDescente = tableauPassager[i].etageDestination().numero()==numero;
			}
			i++;
		}
		return passagerDescente;
	}

	public boolean possedePassager() {// retourne si la cabine possede des passagers
		int i = 0;
		boolean passagerPresent = false;
		while(!passagerPresent && i<nombreDePlacesDansLaCabine){
			passagerPresent = tableauPassager[i]!=null;
			i++;
		}
		return passagerPresent;
	}

	public int nbPassagerDescendant(int numEtage) {
		int res = 0;
		for(int i = 0; i < this.tableauPassager.length; i++) {
			if (tableauPassager[i]!= null) {
				if(tableauPassager[i].etageDestination().numero() == numEtage) {
					res++;
				}
			}
		}
		return res;
		
	}

	public int placesDisponibles() {
		int dispo = 0;
		for(int i = 0; i < nombreDePlacesDansLaCabine; i++) {
			if(this.tableauPassager[i]==null) {
				dispo++;
			}
		}
		return dispo;
	}

	public int faireDescendre(int numEtage, long date, Immeuble immeuble) { //Retourne le nombre de personnes descendues
		int res = 0;
		for (int i = 0; i < tableauPassager.length;i++) {
			if (tableauPassager[i] != null) {
				if(tableauPassager[i].etageDestination().numero() == numEtage) {
					long dateRes = date - tableauPassager[i].dateDepart();
					res++;
					immeuble.cumulDesTempsDeTransport += dateRes;
					tableauPassager[i] = null;
				}
			}
		}
		return res;
	}
	
	public void ajouterPassager(Passager p) {
		assert this.placesDisponibles() > 0;
		int i = 0;
		boolean ajout = false;
		while (i < tableauPassager.length && !ajout) {
			if (tableauPassager[i] == null) {
				tableauPassager[i] = p;
				ajout = true;
			} else {
				i++;
			}
		}
	}

	public int nbPassager() {
		int nb = 0;
		for(int i = 0; i<this.tableauPassager.length ; i++){
			if(this.tableauPassager[i]!=null) nb++;
		}
		return nb;
	}
	
	public void fermerPortes() {
		this.porteOuverte = false;
	}

}
