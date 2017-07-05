package fu.alp2.strassenverwaltung;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import fu.alp2.strassenverwaltung.exceptions.HausException;
import fu.alp2.strassenverwaltung.exceptions.StrassenException;

public class StrassenVerwaltung {

	// private Attribute um die Simulation zu Konfigurieren
	final private static int MAX_ALTER = 5;
	final private static int MAX_GRUNDST�CKE = 10;
	final private static int MAX_BEWOHNER_JE_HAUS = 20;
	final private static int START_JAHR= 2017;
	final private static Strasse STRASSE=new Strasse(MAX_GRUNDST�CKE,MAX_BEWOHNER_JE_HAUS);

	public static void main(String[] args) {
		// scanner f�r die Benutzereingabe �ber die Konsole
		Scanner sc = new Scanner(System.in);
		
		// Frage wie lange die Simulation dauern soll
		System.out.println("Wieviele Jahre sollen simuliert werden?");
		int jahre = sc.nextInt();
		
		// simuliere einzelne Jahre
		for (int i = 0; i < jahre; i++) {
			System.out.println(STRASSE);
			// ermittle Migration
			System.out.println("Wieviele Bewohner sollen dieses Jahr ein/ausziehen:");
			int migration = sc.nextInt();
			// simuliere Ver�nderung
			simuliereJahr(migration,i);
		}
		System.out.println("Simulation Beendet!");
	}

	
/**
 * Pr�ft ob es alte H�user in der Strasse gibt, die abgerissen werden sollten
 * @param haeuser
 * @param jahr
 */
private static void pr�feAufAlteH�user(HashMap<Integer, Haus> haeuser,int jahr ) {
	List<Haus> abzurei�en=new ArrayList<Haus>();
		for(int hausnummer:haeuser.keySet()){
			Haus haus= haeuser.get(hausnummer);
			if((haus.getBaujahr()+MAX_ALTER<jahr) && haus.isUnbewohnt()){
				abzurei�en.add(haeuser.get(hausnummer));
			}
		}
		for(Haus haus:abzurei�en){
			try {
				STRASSE.rei�eAlteH�userAb(haus.getNummer());
			} catch (StrassenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}



/**
 * Simuliert ein Jahr
 * 
 * @param migration
 * @param vergangeneJahre
 */
private static void simuliereJahr(int migration, int vergangeneJahre){
	System.out.println("#################### Jahr: "+(START_JAHR+vergangeneJahre)+" ###################");
	try{
	
	if (migration > 0) {
		int nichtEingezogen=STRASSE.addBewohner(migration, START_JAHR+vergangeneJahre);
		if(nichtEingezogen!=0){
			System.err.println("Einzug fehlgeschlagen, Zuzug gr��er als Anzahl Bewohner");
		}
	} else {
		int nichtAusgezogen=STRASSE.removeBewohner((-1) * migration);	
		if(nichtAusgezogen!=0){
			System.err.println("Auszug fehlgeschlagen, Wegzug gr��er als Anzahl Bewohner");
		
		}
	}
	pr�feAufAlteH�user(STRASSE.getH�user(),START_JAHR+vergangeneJahre);
	} catch (HausException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (StrassenException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	// rei�e alten m�ll ab
		
		
	}
	
}
