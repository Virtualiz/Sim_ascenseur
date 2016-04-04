import java.util.ArrayList;
import java.util.Iterator;

public class Etage extends Constantes {

	private int numero; // de l'Etage pour l'usager

	private LoiDePoisson poissonFrequenceArrivee; // dans l'Etage

	private ArrayList<Passager> listePassagersEtage = new ArrayList<Passager>();

	public Etage(int n, int fa) {
		numero = n;
		int germe = n << 2;
		if (germe <= 0) {
			germe = -germe + 1;
		}
		poissonFrequenceArrivee = new LoiDePoisson(germe, fa);
	}

	public void afficheLaSituation(Immeuble immeuble) {
		if (numero() >= 0) {
			System.out.print(' ');
		}
		System.out.print(numero());
		if (this == immeuble.cabine.etage) {
			System.out.print(" C ");
			if (immeuble.cabine.porteOuverte) {
				System.out.print("[  ]: ");
			} else {
				System.out.print(" [] : ");
			}
		} else {
			System.out.print("   ");
			System.out.print(" [] : ");
		}
		int i = 0;
		boolean stop = listePassagersEtage.size() == 0;
		while (!stop) {
			if (i >= listePassagersEtage.size()) {
				stop = true;
			} else if (i > 6) {
				stop = true;
				System.out.print("...(");
				System.out.print(listePassagersEtage.size());
				System.out.print(')');
			} else {
				listePassagersEtage.get(i).affiche();
				i++;
				if (i < listePassagersEtage.size()) {
					System.out.print(", ");
				}
			}
		}
		System.out.print('\n');
	}

	public int numero() {
		return this.numero;
	}

	public void ajouter(Passager passager) {
		assert passager != null;
		listePassagersEtage.add(passager);
	}

	public long arriveeSuivante() {
		return poissonFrequenceArrivee.suivant();
	}
	
	public boolean arretCabine(Cabine cabine) {// methode permettant de savoir si des personnes attendent l'ascenseur
		boolean arret = false;
		if(!cabine.possedePassager()){ // si la cabine est vide, elle s'arrete quand qqun attend
			arret = !this.listePassagersEtage.isEmpty();
		}else{// si elle est pas vide
			if(cabine.possedePassagerDescente(this.numero)){// si qqun a pour destination cet etage.
				arret = true;
			}else{// si personne n'a pour destination cet etage.
				if(Constantes.isModeParfait()){// arret si le passager qui attend a un souhait correspondant au status cabine (v ou ^)
					switch(cabine.status()){
					case '^':
						//il faut trouver si un passager qui attend a l'etage veut aller a un etage sup.
						for(Passager p : this.listePassagersEtage){
							if(p.numeroDestination()>this.numero){
								arret = true; 
								break;
							}
						}
						break;
					case 'v':
						//il faut trouver si un passager qui attend a l'etage veut aller a un etage inf.
						for(Passager p : this.listePassagersEtage){
							if(p.numeroDestination()<this.numero){
								arret = true; 
								break;
							}
						}
						break;
					}
				}else{
					arret = true;// arret si passager qui attend
				}
			}
		}
		return arret;
	}
	
	public boolean possedePassager(){
		return !this.listePassagersEtage.isEmpty();
	}
	
	public int nbPassagersMontant(char st) {
		int res = 0;
		for(Passager pass : this.listePassagersEtage) {
			if (pass.sens() == st) {
				res++;
			}
		}
		return res;
	}

	public int nbPassagers() {
		return this.listePassagersEtage.size();
	}
	
	public Passager passagerIndex(int i){
		assert this.listePassagersEtage.get(i) != null;
		return this.listePassagersEtage.get(i);
	}
	
	public int faireMonterPassagers(Cabine cabine){
		int i = 0;
		int nbPassagersEntrant = 0;
		if(isModeParfait()){
			while(i < this.listePassagersEtage.size()) {
				Passager p = this.listePassagersEtage.get(i);
				if(p.sens() == cabine.status() && cabine.placesDisponibles()>0){
					cabine.ajouterPassager(p);
					this.listePassagersEtage.remove(i);
					nbPassagersEntrant++;
				} else i++;
			}
		}else{
			while(i < this.listePassagersEtage.size()) {
				Passager p = this.listePassagersEtage.get(i);
				if(cabine.placesDisponibles()>0){
					cabine.ajouterPassager(p);
					this.listePassagersEtage.remove(p);
					nbPassagersEntrant++;
				} else i++;
			}
		}
		return nbPassagersEntrant;
	}
}
