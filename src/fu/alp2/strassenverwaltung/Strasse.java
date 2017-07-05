package fu.alp2.strassenverwaltung;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import fu.alp2.strassenverwaltung.exceptions.HausException;
import fu.alp2.strassenverwaltung.exceptions.StrassenException;

/**
 * Klasse zur Verwaltung der Ressourcen einer Strasse (=>Häuser)
 * @author semmel
 *
 */
public class Strasse {
	// private Attribute
	private HashMap<Integer, Haus> häuser;
	// Anzahl Grundstücke definiert die möglichen Hausnummern von
	// 1-[Grundstücke]
	private int grundstücke;
	private int maxHausGroesse;
	
	/**
	 * Getter Häuser
	 * @return
	 */
	public HashMap<Integer, Haus> getHäuser() {
		return häuser;
	}

	
	/**
	 * Konstruktor
	 * @param grundstuecke
	 * @param maxHausGroesse
	 */
	public Strasse(int grundstuecke,int maxHausGroesse) {
		this.häuser = new HashMap<Integer, Haus>();
		this.grundstücke = grundstuecke;
		this.maxHausGroesse=maxHausGroesse;
	}

	/**
	 * Gibt das Haus zur passenden Hausnummer zurück.
	 * 
	 * @param nummer
	 * @return
	 * @throws StrassenException
	 */
	public Haus getHaus(int nummer) throws StrassenException {
		// gibt es die Hausnummer überhaupt?
		if (häuser.containsKey(nummer)) {
			// wenn ja, return
			return this.häuser.get(nummer);
		} else {
			// sonst werfe Exception
			throw new StrassenException("Nummer ist nicht im Straßenzug vorhanden");
		}
	}

	/**
	 * Gibt die Anzahl unbebauter Grunstücke zurück
	 * 
	 * @return
	 */
	public int getFreiegrundstücke() {
		return grundstücke - häuser.size();
	}

	/**
	 * Baut ein neues Haus, wenn noch Platz in der Strasse ist.
	 * Die Hausnummer wird nach der Zuteilung des Grundstückes gesetzt.
	 * 
	 * @param haus
	 * @throws StrassenException
	 */
	public void addHaus(Haus haus) throws StrassenException {
		if (häuser.size() <= grundstücke) {
			for (int i = 1; i <= grundstücke; i++) {
				if (!this.häuser.containsKey(i)) {
					haus.setNummer(i);
					this.häuser.put(i, haus);
					break;
				}
			}

		} else {
			throw new StrassenException("Fehler beim Hausbau, es sind keine Grundstücke mehr Frei!");
		}
	}

	/**
	 * Lässt die angegebene Anzahl bewohner in der Strasse einziehen, solange
	 * Wohnraum frei ist.
	 * 
	 * @param bewohner
	 * @param jahr
	 * @throws HausException
	 * @throws StrassenException
	 */
	public int addBewohner(int bewohner, int jahr) throws HausException, StrassenException {
		if (!(bewohner == 0)) {
			// verteile personen auf bestehende Häuser
			for (int hausnummer : häuser.keySet()) {
				// wenn noch Platz im Haus ist
				if (häuser.get(hausnummer).getUnbewohnteWohnungen() > 0) {
					// ermittle wieviel Platz
					int neueBewohner = (häuser.get(hausnummer).getKapazitätUnbwohnterWohnungen() > bewohner) ? bewohner
							: häuser.get(hausnummer).getKapazitätUnbwohnterWohnungen();
					// lass bewohner einziehen
					bewohner = bewohner - neueBewohner;
					häuser.get(hausnummer).ziehtEin(neueBewohner);
					// ende, wenn alle Verteilt sind
					if (bewohner == 0)
						break;
				}
			}
			// nicht alle Bverteilt -> baue neue Häuser in der Strasse
			if (!(bewohner == 0)) {
				// gehe über alle Grundstücke und baue auf freien neue Häuser
				for (int i = 1; i < grundstücke + 1; i++) {
					if (!häuser.containsKey(i)) {
						int groesse = (bewohner > maxHausGroesse) ? maxHausGroesse : bewohner;
						häuser.put(i, new Haus((groesse), jahr, i));
						bewohner = this.addBewohner(bewohner, jahr);
						break;

					}
				}
			}
		}
		return bewohner;
	}
	
 /**
  * Lässt die angegebene Anzahl Personen aus der Strasse wegziehen.
  * 
 * @param bewohner
 * @throws HausException
 * @throws StrassenException 
 */
public int removeBewohner(int bewohner) throws HausException, StrassenException{
	// gehe durch alle Häuser
			for (int hausnummer : häuser.keySet()) {
				// wenn bewohnt
				if (!häuser.get(hausnummer).isUnbewohnt()) {
					// lass so viel wie nötig/möglich ausziehen
					int ausziehend = (häuser.get(hausnummer).getBewohnerZahl() > bewohner) ? bewohner
							: häuser.get(hausnummer).getBewohnerZahl();
					bewohner = bewohner - ausziehend;
					häuser.get(hausnummer).ziehtAus(ausziehend);
					// ende, wenn alle Verteilt sind
					if (bewohner == 0)
						break;
				}
			}return bewohner;
 }

	/**
	 * Entfernt das Haus aus der Strasse
	 * 
	 * @param nummer
	 * @throws StrassenException
	 */
	public void reißeAlteHäuserAb(int nummer) throws StrassenException {
		// prüfe ob leer
		if (häuser.get(nummer).isUnbewohnt()) {
			häuser.remove(nummer);
			System.out.println("| Haus Nr."+nummer+" abgerissen.");
		} else {
			throw new StrassenException("Es sollte ein bewohntes Haus abgerissen werden, LEBENSGEFAHR!");
		}
	}
	
	/*
	 * Spezielle toString()-Methode für die Strasse:
	 * Example:
	 * 			Haus.toString() =| : |= empty
	 */
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append("#################### Strasse ###################\n\n");
		
		try {
			// ermittle Simulationsparameter
			int frei = 0;
			int bewohnt = 0;
			int freieGrundstücke = this.grundstücke;
			for (int hausnummer : this.häuser.keySet()) {

				frei += this.getHaus(hausnummer).getKapazitätUnbwohnterWohnungen();
				bewohnt += this.getHaus(hausnummer).getBewohnerZahl();
				freieGrundstücke--;
			}

			// print,zur Information des Nutzers
			sb.append("\n\nÜbersicht: " + bewohnt
					+ " Bewohner\n"+frei+" Kapazität gebauter Häusern\n"
					+ freieGrundstücke + " freie Grundstücke\n"+
					+ this.getFreiegrundstücke()*this.maxHausGroesse+ " möglicher Zuzug\n\n");
		} catch (StrassenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=1;i<grundstücke+1;i++){
			
			if(!(i%2==0)){
				// gerade = linke Strassenhälfte
				if(häuser.containsKey(i)){sb.append("\t\t["+häuser.get(i).toString()+"]\t=| : |=");}
				else{sb.append("\t\tunbebaut \t\t\t=| : |=");}
			}else{
				// rechte strassenhälfte
				if(häuser.containsKey(i)){sb.append("\t["+häuser.get(i).toString()+"]\n");}
				else{sb.append("\tunbebaut\n");}
			}
			//if(haeuser.containsKey(i))sb.append("\t\t["+haeuser.get(i).toString()+"]");
		}
		return sb.toString();	
	}
}
