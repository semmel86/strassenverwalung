package fu.alp2.strassenverwaltung.test;

import org.junit.Test;

import fu.alp2.strassenverwaltung.Haus;
import fu.alp2.strassenverwaltung.Strasse;
import fu.alp2.strassenverwaltung.StrassenVerwaltung;
import fu.alp2.strassenverwaltung.exceptions.HausException;
import fu.alp2.strassenverwaltung.exceptions.StrassenException;
import junit.framework.TestCase;

public class UnitTest {

	private int testJahr = 2017;
	private int testKapazit�t = 10;
	private int zuzug = 10;
	private int auszug = 5;
	private int hausnummer1 = 1;
	private int hausnummer2 = 2;
	private int grundst�cke = 5;
	private int maxHausKapazit�t = 20;

	@Test
	public void testHaus() {
		Haus testHaus = new Haus(testKapazit�t, testJahr, hausnummer1);
		TestCase.assertEquals(testJahr, testHaus.getBaujahr());
		TestCase.assertEquals(0, testHaus.getBewohnerZahl());
		TestCase.assertEquals(testKapazit�t, testHaus.getKapazit�tUnbwohnterWohnungen());
		TestCase.assertEquals(hausnummer1, testHaus.getNummer());
		TestCase.assertEquals(testHaus.getWohnungsanzahl(), testHaus.getUnbewohnteWohnungen());
		TestCase.assertEquals(true, testHaus.isUnbewohnt());

		testHaus.setNummer(hausnummer2);
		TestCase.assertEquals(hausnummer2, testHaus.getNummer());

		try {
			testHaus.ziehtEin(zuzug);
		} catch (HausException e) {
			e.printStackTrace();
		}

		TestCase.assertEquals(zuzug, testHaus.getBewohnerZahl());
		TestCase.assertEquals(testKapazit�t - zuzug, testHaus.getKapazit�tUnbwohnterWohnungen());

		try {
			testHaus.ziehtAus(auszug);
		} catch (HausException e) {
			e.printStackTrace();
		}
		TestCase.assertEquals(zuzug - auszug, testHaus.getBewohnerZahl());
	}

	@Test
	public void testHausException() {
		Haus testHaus = new Haus(testKapazit�t, testJahr, hausnummer1);
		// ExceptionTests

	}

	@Test
	public void testStrasse() {
		Strasse strasse = new Strasse(grundst�cke, maxHausKapazit�t);
		
		TestCase.assertEquals(grundst�cke,strasse.getFreiegrundst�cke());
		TestCase.assertEquals(0,strasse.getH�user().size());

		try {
			strasse.addBewohner(zuzug, testJahr);
		} catch (HausException | StrassenException e) {
			e.printStackTrace();
		}
		TestCase.assertTrue(grundst�cke>strasse.getFreiegrundst�cke());
		TestCase.assertTrue(0<strasse.getH�user().size());
	
		int h�user=strasse.getH�user().size();
		try {
			strasse.addHaus(new Haus(testKapazit�t, testJahr, hausnummer2));
		} catch (StrassenException e) {
			e.printStackTrace();
		}
		TestCase.assertEquals(h�user+1,strasse.getH�user().size());
		try {
			strasse.rei�eAlteH�userAb(2);
		} catch (StrassenException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TestCase.assertEquals(h�user,strasse.getH�user().size());
		
		
		try {
			strasse.removeBewohner(auszug);
		} catch (HausException | StrassenException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testStrassenVerwaltung(){
		StrassenVerwaltung s=new StrassenVerwaltung();
		
		
		
	}
}
