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
	private int testKapazität = 10;
	private int zuzug = 10;
	private int auszug = 5;
	private int hausnummer1 = 1;
	private int hausnummer2 = 2;
	private int grundstücke = 5;
	private int maxHausKapazität = 20;

	@Test
	public void testHaus() {
		Haus testHaus = new Haus(testKapazität, testJahr, hausnummer1);
		TestCase.assertEquals(testJahr, testHaus.getBaujahr());
		TestCase.assertEquals(0, testHaus.getBewohnerZahl());
		TestCase.assertEquals(testKapazität, testHaus.getKapazitätUnbwohnterWohnungen());
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
		TestCase.assertEquals(testKapazität - zuzug, testHaus.getKapazitätUnbwohnterWohnungen());

		try {
			testHaus.ziehtAus(auszug);
		} catch (HausException e) {
			e.printStackTrace();
		}
		TestCase.assertEquals(zuzug - auszug, testHaus.getBewohnerZahl());
	}

	@Test
	public void testHausException() {
		Haus testHaus = new Haus(testKapazität, testJahr, hausnummer1);
		// ExceptionTests

	}

	@Test
	public void testStrasse() {
		Strasse strasse = new Strasse(grundstücke, maxHausKapazität);
		
		TestCase.assertEquals(grundstücke,strasse.getFreiegrundstücke());
		TestCase.assertEquals(0,strasse.getHäuser().size());

		try {
			strasse.addBewohner(zuzug, testJahr);
		} catch (HausException | StrassenException e) {
			e.printStackTrace();
		}
		TestCase.assertTrue(grundstücke>strasse.getFreiegrundstücke());
		TestCase.assertTrue(0<strasse.getHäuser().size());
	
		int häuser=strasse.getHäuser().size();
		try {
			strasse.addHaus(new Haus(testKapazität, testJahr, hausnummer2));
		} catch (StrassenException e) {
			e.printStackTrace();
		}
		TestCase.assertEquals(häuser+1,strasse.getHäuser().size());
		try {
			strasse.reißeAlteHäuserAb(2);
		} catch (StrassenException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TestCase.assertEquals(häuser,strasse.getHäuser().size());
		
		
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
