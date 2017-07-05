package fu.alp2.strassenverwaltung;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import fu.alp2.strassenverwaltung.exceptions.HausException;
import fu.alp2.strassenverwaltung.exceptions.StrassenException;

/**
 * Klasse zur Verwaltung der Ressourcen einer Strasse (=>H�user)
 * @author semmel
 *
 */
public class Strasse {
	// private Attribute
	private HashMap<Integer, Haus> h�user;
	// Anzahl Grundst�cke definiert die m�glichen Hausnummern von
	// 1-[Grundst�cke]
	private int grundst�cke;
	private int maxHausGroesse;
	
	/**
	 * Getter H�user
	 * @return
	 */
	public HashMap<Integer, Haus> getH�user() {
		return h�user;
	}

	
	/**
	 * Konstruktor
	 * @param grundstuecke
	 * @param maxHausGroesse
	 */
	public Strasse(int grundstuecke,int maxHausGroesse) {
		this.h�user = new HashMap<Integer, Haus>();
		this.grundst�cke = grundstuecke;
		this.maxHausGroesse=maxHausGroesse;
	}

	/**
	 * Gibt das Haus zur passenden Hausnummer zur�ck.
	 * 
	 * @param nummer
	 * @return
	 * @throws StrassenException
	 */
	public Haus getHaus(int nummer) throws StrassenException {
		// gibt es die Hausnummer �berhaupt?
		if (h�user.containsKey(nummer)) {
			// wenn ja, return
			return this.h�user.get(nummer);
		} else {
			// sonst werfe Exception
			throw new StrassenException("Nummer ist nicht im Stra�enzug vorhanden");
		}
	}

	/**
	 * Gibt die Anzahl unbebauter Grunst�cke zur�ck
	 * 
	 * @return
	 */
	public int getFreiegrundst�cke() {
		return grundst�cke - h�user.size();
	}

	/**
	 * Baut ein neues Haus, wenn noch Platz in der Strasse ist.
	 * Die Hausnummer wird nach der Zuteilung des Grundst�ckes gesetzt.
	 * 
	 * @param haus
	 * @throws StrassenException
	 */
	public void addHaus(Haus haus) throws StrassenException {
		if (h�user.size() <= grundst�cke) {
			for (int i = 1; i <= grundst�cke; i++) {
				if (!this.h�user.containsKey(i)) {
					haus.setNummer(i);
					this.h�user.put(i, haus);
					break;
				}
			}

		} else {
			throw new StrassenException("Fehler beim Hausbau, es sind keine Grundst�cke mehr Frei!");
		}
	}

	/**
	 * L�sst die angegebene Anzahl bewohner in der Strasse einziehen, solange
	 * Wohnraum frei ist.
	 * 
	 * @param bewohner
	 * @param jahr
	 * @throws HausException
	 * @throws StrassenException
	 */
	public int addBewohner(int bewohner, int jahr) throws HausException, StrassenException {
		if (!(bewohner == 0)) {
			// verteile personen auf bestehende H�user
			for (int hausnummer : h�user.keySet()) {
				// wenn noch Platz im Haus ist
				if (h�user.get(hausnummer).getUnbewohnteWohnungen() > 0) {
					// ermittle wieviel Platz
					int neueBewohner = (h�user.get(hausnummer).getKapazit�tUnbwohnterWohnungen() > bewohner) ? bewohner
							: h�user.get(hausnummer).getKapazit�tUnbwohnterWohnungen();
					// lass bewohner einziehen
					bewohner = bewohner - neueBewohner;
					h�user.get(hausnummer).ziehtEin(neueBewohner);
					// ende, wenn alle Verteilt sind
					if (bewohner == 0)
						break;
				}
			}
			// nicht alle Bverteilt -> baue neue H�user in der Strasse
			if (!(bewohner == 0)) {
				// gehe �ber alle Grundst�cke und baue auf freien neue H�user
				for (int i = 1; i < grundst�cke + 1; i++) {
					if (!h�user.containsKey(i)) {
						int groesse = (bewohner > maxHausGroesse) ? maxHausGroesse : bewohner;
						h�user.put(i, new Haus((groesse), jahr, i));
						bewohner = this.addBewohner(bewohner, jahr);
						break;

					}
				}
			}
		}
		return bewohner;
	}
	
 /**
  * L�sst die angegebene Anzahl Personen aus der Strasse wegziehen.
  * 
 * @param bewohner
 * @throws HausException
 * @throws StrassenException 
 */
public int removeBewohner(int bewohner) throws HausException, StrassenException{
	// gehe durch alle H�user
			for (int hausnummer : h�user.keySet()) {
				// wenn bewohnt
				if (!h�user.get(hausnummer).isUnbewohnt()) {
					// lass so viel wie n�tig/m�glich ausziehen
					int ausziehend = (h�user.get(hausnummer).getBewohnerZahl() > bewohner) ? bewohner
							: h�user.get(hausnummer).getBewohnerZahl();
					bewohner = bewohner - ausziehend;
					h�user.get(hausnummer).ziehtAus(ausziehend);
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
	public void rei�eAlteH�userAb(int nummer) throws StrassenException {
		// pr�fe ob leer
		if (h�user.get(nummer).isUnbewohnt()) {
			h�user.remove(nummer);
			System.out.println("| Haus Nr."+nummer+" abgerissen.");
		} else {
			throw new StrassenException("Es sollte ein bewohntes Haus abgerissen werden, LEBENSGEFAHR!");
		}
	}
	
	/*
	 * Spezielle toString()-Methode f�r die Strasse:
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
			int freieGrundst�cke = this.grundst�cke;
			for (int hausnummer : this.h�user.keySet()) {

				frei += this.getHaus(hausnummer).getKapazit�tUnbwohnterWohnungen();
				bewohnt += this.getHaus(hausnummer).getBewohnerZahl();
				freieGrundst�cke--;
			}

			// print,zur Information des Nutzers
			sb.append("\n\n�bersicht: " + bewohnt
					+ " Bewohner\n"+frei+" Kapazit�t gebauter H�usern\n"
					+ freieGrundst�cke + " freie Grundst�cke\n"+
					+ this.getFreiegrundst�cke()*this.maxHausGroesse+ " m�glicher Zuzug\n\n");
		} catch (StrassenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=1;i<grundst�cke+1;i++){
			
			if(!(i%2==0)){
				// gerade = linke Strassenh�lfte
				if(h�user.containsKey(i)){sb.append("\t\t["+h�user.get(i).toString()+"]\t=| : |=");}
				else{sb.append("\t\tunbebaut \t\t\t=| : |=");}
			}else{
				// rechte strassenh�lfte
				if(h�user.containsKey(i)){sb.append("\t["+h�user.get(i).toString()+"]\n");}
				else{sb.append("\tunbebaut\n");}
			}
			//if(haeuser.containsKey(i))sb.append("\t\t["+haeuser.get(i).toString()+"]");
		}
		return sb.toString();	
	}
}
