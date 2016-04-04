public class EvenementPassageCabinePalier extends Evenement {// moment precis ou la cabine passe devant les portes d'un etage

	private Etage etage;

	public EvenementPassageCabinePalier(long d, Etage e) {
		super(d);
		etage = e;
	}

	public void afficheDetails(Immeuble immeuble) {
		System.out.print("PCP ");
		System.out.print(etage.numero());
	}

	public void traiter(Immeuble immeuble, Echeancier echeancier) {
		Cabine cabine = immeuble.cabine;
		assert !cabine.porteOuverte;
		assert cabine.status()!='-';
		cabine.etage = this.etage; // l'etage ou se trouve la cabine au moment de l'evenement est l'etage de l'evenement
		// il faut verifier :
		// si un passager attend a l'etage. Si mode parfait, test etage destination<etage quand status == 'v', sinon destination >etage
		// si un passager de la cabine a pour destination l'etage : ouveture des portes.
		// pas 192, Status de la cabine v alors que qqun attend en 5 pour v, la cabine doit monter la chercher (si cas où cabine s'arrete car vide, vérifier que après dans sens cabine la personne veut aller dans le même sens
		boolean OPC = false;
		if(cabine.possedePassager() && cabine.possedePassagerDescente(etage.numero())){//cabine possedePassager
			OPC = true;
		}else{
			if(this.etage.arretCabine(cabine)){
				OPC = true;
				// mettre a jour le status de la cabine.
			}else{ 

			}
		}
		if(OPC){// si on doit ouvrir les portes
			long date = this.date;
			date += Constantes.tempsPourOuvrirLesPortes;
			echeancier.ajouter(new EvenementOuverturePorteCabine(date));
			if(etage.numero()==immeuble.etageLePlusHaut().numero()){// si tout en haut
				cabine.changerStatus('v');
			}else{
				if(etage.numero()==immeuble.etageLePlusBas().numero()){// si tout en bas
					cabine.changerStatus('^');
				}
				else{// si ni tout en haut ni tout en bas
					if(!cabine.possedePassager()){// si cabine vide, l'arret est du a un passager qui attend
						cabine.changerStatus(etage.passagerIndex(0).sens());
					}
				}
			}
		}else{//Sinon generer le prochain PCP

			/*if(!cabine.possedePassager()){
				//verifier les etages (prio a ceux qui sont au dessus si ^, dessous sinon)
				int i,borneInf = immeuble.etageLePlusBas().numero(), borneSup=immeuble.etageLePlusHaut().numero();;
				if(cabine.status() == '^'){
					i = immeuble.etageLePlusHaut().numero();
				}else{
					i = immeuble.etageLePlusBas().numero();
				}
				boolean personnePresente = false;
				while(!personnePresente && i<=borneSup && i>= borneInf){
					personnePresente = immeuble.etage(i).possedePassager();
					if(cabine.status()=='^') i--;
					else i++;
				}
				
				
			}else{*/
				
					
				
				if(cabine.status() == '^'){
					//cas extreme
					if(etage.numero()==immeuble.etageLePlusHaut().numero()){
						cabine.changerStatus('v');
						echeancier.ajouter(new EvenementPassageCabinePalier(this.date+Constantes.tempsPourBougerLaCabineDUnEtage,immeuble.etage(this.etage.numero()-1)));
					}else{
						echeancier.ajouter(new EvenementPassageCabinePalier(this.date+Constantes.tempsPourBougerLaCabineDUnEtage,immeuble.etage(this.etage.numero()+1)));
					}
				}else{// cas cabine descendante
					//cas extreme
					if(etage.numero()==immeuble.etageLePlusBas().numero()){
						cabine.changerStatus('^');
						echeancier.ajouter(new EvenementPassageCabinePalier(this.date+Constantes.tempsPourBougerLaCabineDUnEtage,immeuble.etage(this.etage.numero()+1)));
					}else{
						echeancier.ajouter(new EvenementPassageCabinePalier(this.date+Constantes.tempsPourBougerLaCabineDUnEtage,immeuble.etage(this.etage.numero()-1)));
					}
				}
			/*}*/
		}
		if(OPC){// revoir le status de la cabine, elle prend le sens de la personne qui entre si elle etait vide
			if(!etage.possedePassager() && (!cabine.possedePassager() || cabine.nbPassager()-cabine.nbPassagerDescendant(etage.numero())==0)){// si la cabine se retrouve vide
				//on cherche si quelqu'un doit etre cherche
				
				int i,borneInf = immeuble.etageLePlusBas().numero(), borneSup=immeuble.etageLePlusHaut().numero();;
				if(cabine.status() == '^'){
					i = immeuble.etageLePlusHaut().numero();
				}else{
					i = immeuble.etageLePlusBas().numero();
				}
				boolean personnePresente = false;
				while(!personnePresente && i<=borneSup && i>= borneInf){
					personnePresente = immeuble.etage(i).possedePassager();
					if(cabine.status()=='^') i--;
					else i++;
				}
				if(i>etage.numero()) cabine.changerStatus('^');
				else if(i<etage.numero()) cabine.changerStatus('v');
				
			}
		}
	}
}
