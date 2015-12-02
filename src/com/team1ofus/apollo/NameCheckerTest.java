package com.team1ofus.apollo;

import static org.junit.Assert.*;

import org.junit.Test;

public class NameCheckerTest {

	@Test
	public void cellNameCheck() {
		System.out.println("");
		System.out.println("Should catch error in cell name");
		NameChecker testName = new NameChecker("AK1_1", false);
		assertEquals(testName.isNameValid(),false);
	}
	
	@Test
	public void cellNameCheck2() {
		System.out.println("");
		System.out.println("Should catch error in cell floor value");
		NameChecker testName = new NameChecker("AK_M", false);
		assertEquals(testName.isNameValid(),false);
	}
	
	@Test
	public void cellNameCheck3() {
		System.out.println("");
		System.out.println("Should catch error in cell name and floor value");
		NameChecker testName = new NameChecker("AK1_M", false);
		assertEquals(testName.isNameValid(),false);
	}
	
	@Test
	public void cellNameCheck4() {
		System.out.println("");
		System.out.println("Should catch error in cell name and floor value with special characters");
		NameChecker testName = new NameChecker("A1_@", false);
		assertEquals(testName.isNameValid(),false);
	}
	
	@Test
	public void cellNameCheck5() {
		System.out.println("");
		System.out.println("Should check cell name and return true");
		NameChecker testName = new NameChecker("AK_G", false);
		assertEquals(testName.isNameValid(),true);
	}
	
	@Test
	public void cellNameCheck6() {
		System.out.println("");
		System.out.println("Should check if appropriate amount of deliminators");
		NameChecker testName = new NameChecker("AK", false);
		assertEquals(testName.isNameValid(),false);
	}
	
	@Test
	public void entryPtCheck() {
		System.out.println("");
		System.out.println("Should check entryPt and return true");
		NameChecker testName = new NameChecker("AK_1_CM_1_D_1", true);
		assertEquals(testName.isNameValid(),true);
	}
	
	@Test
	public void entryPtCheck2() {
		System.out.println("");
		System.out.println("Should check entryPt and throw cell name error");
		NameChecker testName = new NameChecker("AK2_1_CM_1_D_1", true);
		assertEquals(testName.isNameValid(),false);
	}
	
	@Test
	public void entryPtCheck3() {
		System.out.println("");
		System.out.println("Should check entryPt and throw floor val error");
		NameChecker testName = new NameChecker("AK_66_CM_1_D_1", true);
		assertEquals(testName.isNameValid(),false);
	}
	
	@Test
	public void entryPtCheck4() {
		System.out.println("");
		System.out.println("Should check entryPt and throw cell name error(for 2nd cell)");
		NameChecker testName = new NameChecker("AK_1_C3_1_D_1", true);
		assertEquals(testName.isNameValid(),false);
	}
	
	@Test
	public void entryPtCheck5() {
		System.out.println("");
		System.out.println("Should check entryPt and throw floor number error");
		NameChecker testName = new NameChecker("AK_1_CM_*2_D_1", true);
		assertEquals(testName.isNameValid(),false);
	}
	
	@Test
	public void entryPtCheck6() {
		System.out.println("");
		System.out.println("entryPtCheck6 Test");
		System.out.println("Should check entryPt and identifier error");
		NameChecker testName = new NameChecker("AK_1_CM_1_3_1", true);
		assertEquals(testName.isNameValid(),false);
	}
	
	@Test
	public void entryPtCheck7() {
		System.out.println("");
		System.out.println("entryPtCheck7 Test");
		System.out.println("Should check entryPt and throw room number exception for identifier");
		NameChecker testName = new NameChecker("AK_1_CM_1_D_!", true);
		assertEquals(testName.isNameValid(),false);
	}
	
	@Test
	public void entryPtCheck8() {
		System.out.println("");
		System.out.println("entryPtCheck8 Test");
		System.out.println("Should check entryPt and throw room number exception for identifier with a large number");
		NameChecker testName = new NameChecker("AK_1_CM_1_D_101", true);
		assertEquals(testName.isNameValid(),false);
	}
	
	@Test
	public void entryPtCheck9() {
		System.out.println("");
		System.out.println("entryPtCheck9 Test");
		System.out.println("Should check entryPt and throw delimator error");
		NameChecker testName = new NameChecker("AK_1_CM_1_D_1_2_2", true);
		assertEquals(testName.isNameValid(),false);
	}
	
	@Test
	public void entryPtCheck10() {
		System.out.println("");
		System.out.println("entryPtCheck10 Test");
		System.out.println("Should check entryPt and throw delimator error round 2");
		NameChecker testName = new NameChecker("AK", true);
		assertEquals(testName.isNameValid(),false);
	}

}
