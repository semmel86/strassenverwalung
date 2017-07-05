package fu.alp2.strassenverwaltung;

import java.util.ArrayList;
import java.util.List;

import fu.alp2.strassenverwaltung.exceptions.HausException;

/**
 * Klasse Haus, zur Verwaltung der Ressourcen eines Hauses (Bewohner, Wohnungen)
 * @author semmel
 *
 */
public class Haus {

	// private Attribute des Hauses
	private int nummer;
	private int baujahr;
	private List<Wohnung> wohnungen;

	
	/**
	 * �ffentlicher Konstruktor
	 * 
	 * @param kapazit�t
	 * @param baujahr
	 * @param nummer
	 */
	public Haus(int kapazit�t, int baujahr,int nummer) {
	System.out.println("| Neues Haus gebaut, Nr:"+nummer);
		this.baujahr = baujahr;
		this.nummer=nummer;
		verteileWohnungen(kapazit�t);
	}


	/**
	 * Gibt die Anzahl aller Wohnungen im Haus zur�ck
	 * 
	 * @return int Wohnungen
	 */
	public int getWohnungsanzahl() {
		return wohnungen.size();
	}

	/**
	 * Gibt die Anzahl aller unbewohnten Wohungen zur�ck.
	 * 
	 * @return int unbewohnte Wohnungen
	 */
	public int getUnbewohnteWohnungen() {
		int unbewohnteWohnungen = 0;
		// z�hle alle unbewohnten Wohnungen
		for (Wohnung wohnung : wohnungen) {
			if (wohnung.isUnbewohnt()) {
				unbewohnteWohnungen++;
			}
		}
		return unbewohnteWohnungen;
	}

	/**
	 * Gibt die Kapazit�t aller unbewohnten Wohungen zur�ck. 
	 * => Anzahl m�glicher Bewohner
	 * 
	 * @return int
	 */
	public int getKapazit�tUnbwohnterWohnungen() {
		int kapazit�t = 0;
		// berechne die Summe des Wohnraums aller Wohnungen
		for (Wohnung wohnung : wohnungen) {
			if (wohnung.isUnbewohnt()) {
				kapazit�t += wohnung.kapazit�t;
			}
		}
		return kapazit�t;
	}

	/**
	 * L�sst die gegebene Anzahl Personen aus dem Haus ausziehen.
	 * 
	 * @param personen
	 * @throws HausException 
	 */
	public void ziehtAus(int personen) throws HausException {
		System.out.println("| Ausz�ge in Haus "+this.getNummer()+" :");
		if (this.getBewohnerZahl() < personen) {
			throw new HausException("Fehler beim Auszug, es sollen mehr Bewohner ausziehen, als hier wohnen.");
		} else {
			// gehe durch alle Wohnungen und lasse so viele wie erwartet Bewohner ausziehen
			for (Wohnung wohnung : wohnungen) {
				if (!wohnung.isUnbewohnt()) {
					int personenDieAusziehen = (wohnung.bewohner < personen) ? wohnung.bewohner : personen;
					wohnung.ziehenAus(personenDieAusziehen);
					personen -= personenDieAusziehen;
					if(personen==0){
						// ende, wenn alle weg sind
						break;
					}
				}
			}
			if (personen != 0) {
				// sollte nie erreicht werden, da
				throw new HausException("Fehler beim Auszug.");
			}
		}
	}

	/**
	 * L�sst die gegebene Anzahl an Personen in das Haus einziehen.
	 * 
	 * @param personen
	 * @throws HausException 
	 */
	public void ziehtEin(int personen) throws HausException {
		System.out.println("| Einz�ge in Haus "+this.getNummer()+" :");
		for (Wohnung wohnung : wohnungen) {
			if (wohnung.isUnbewohnt()) {
				int personenDieEinziehen = (wohnung.kapazit�t < personen) ? wohnung.kapazit�t : personen;
				wohnung.ziehenEin(personenDieEinziehen);
				personen -= personenDieEinziehen;
			}
		}
		if (personen != 0) {
			throw new HausException("Fehler beim Einzug");
		}
	}

	/**
	 * Gibt einen Boolean zur�ck, der besagt ob das Haus unbewohnt ist
	 *  false => bewohnt
	 *  true => unbeohnt
	 *  
	 * @return boolean
	 */
	public boolean isUnbewohnt() {
		// Pr�fe alle Wohnungen
		for (Wohnung wohnung : wohnungen) {
			// wenn eine Wohnung bewohnt ist, ist auch das Haus bewohnt
			if (!wohnung.isUnbewohnt()) {
				return false;
			}
		}
		// alle Wohnungen sind leer
		return true;
	}

	/**
	 * Verteilt den Wohnraum eines Hauses auf Wohnungen f�r 1-6 Bewohner.
	 * 
	 * @param ben�tigterWohnraum
	 */
	private void verteileWohnungen(int ben�tigterWohnraum) {
		// initiere neue Wohnungsliste 
		wohnungen=new ArrayList<Wohnung>();
		// generiere so viele Wohnungen, bis der zu bauende Wohnraum verplant ist
		while (ben�tigterWohnraum > 0) {
				int gr�sse=(int) (1+Math.random()*6);
				
				// plane Wohnung nur, wenn sie dem Bedarf entspricht
				if (ben�tigterWohnraum >= gr�sse) {
					ben�tigterWohnraum -= gr�sse;
					wohnungen.add(new Wohnung(gr�sse));
				}else {
					// Wohnung zu gro� um Kapazit�t einzuhalten
					// tue nichts
				}
					
					
		}
		System.out.println("|-- "+wohnungen.size()+" neue Wohnungen in Haus "+this.nummer+" gebaut.");
	}

	
	/**
	 * Getter Baujahr
	 * @return int Baujahr
	 */
	public int getBaujahr() {
		return baujahr;
	}

	/**
	 * Getter Bewohner
	 * @return int Bewohner
	 */
	public int getBewohnerZahl() {
		int bewohner = 0;
		// Z�hle die Bewohner aller Wohnungen
		for (Wohnung wohnung : wohnungen) {
			bewohner += wohnung.bewohner;
		}
		return bewohner;
	}
	
	/**
	 * Getter f�r Hausnummer 
	 * @return
	 */
	public int getNummer() {	
		return this.nummer;
	}
	/**
	 * Setter f�r Hausnummer
	 * @param nummer
	 */
	public void setNummer(int nummer){
		this.nummer=nummer;
	}

	/* (non-Javadoc)
	 * �berschriebene, spezielle toString()-Methode f�r H�user
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return "Nr.: "+this.nummer+"|Bewohner: "+this.getBewohnerZahl()+"|BJ: "+this.getBaujahr();
	}
	
	// innere Klasse die die Wohnungen eines Hauses abbildet,
	// f�r Haus implementierende Klassen nicht zu sehen
	private class Wohnung {
		private int bewohner;
		private int kapazit�t;

		/**
		 * Konstruktor
		 * 
		 * @param kapazit�t
		 */
		public Wohnung(int kapazit�t) {
			this.kapazit�t = kapazit�t;
			this.bewohner = 0;
		}

		/**
		 * Gibt an ob die Wohnung bewohnt ist, oder nicht.
		 * 
		 * @return
		 */
		public boolean isUnbewohnt() {
			//so einfach kann es sein...keiner da-> true, sonst false
			return (bewohner == 0) ? true : false;
		}

		/**
		 * Pr�ft, ob die Wohnung f�r eine gegebene Anzahl Personen passt.
		 * -> Wohnung ist unbewohnt und hat genug platz == true
		 * @param personen
		 * @return
		 */
		public boolean isPassendeWohnung(int personen) {
			if (personen > this.kapazit�t) {
				// zu klein
				return false;
			} else if (bewohner > 0) {
				// bereits bewohnt
				return false;
			} else {
				// genau richtig
				return true;
			}
		}

		/**
		 * L�sste die angegebene Anzahl personen in die Wohnung ziehen.
		 * @param personen
		 * @throws HausException
		 */
		public void ziehenEin(int personen) throws HausException {
			// Checke Korrektheit
			if (isPassendeWohnung(personen)) {
				System.out.println("|--- "+personen+" Personen ziehen in eine "+this.kapazit�t+"-Raumwohnung dieses Hauses ");
				this.kapazit�t -= personen;
				this.bewohner += personen;
			
			} else {
				throw new HausException("Wohnungsgr��e und Personen passen nicht zueinander.");
			}
			
		}

		/**
		 * L�sst die Angegebene Zahl Personen aus der Wohnung ziehen.
		 * 
		 * @param personen
		 * @throws HausException
		 */
		public void ziehenAus(int personen) throws HausException {
			// Checke Korrektheit
			if (personen <= bewohner) {
				this.kapazit�t += personen;
				this.bewohner -= personen;
				System.out.println("|--- "+personen+" Personen ziehen aus einer Wohnung dieses Hauses");
			} else {
				throw new HausException("Es k�nnen nicht mehr Bewohner ausziehen als in einer Wohnung wohnen.");
			}

		}
	}

	
}
