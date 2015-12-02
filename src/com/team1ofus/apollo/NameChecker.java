package com.team1ofus.apollo;

import java.util.ArrayList;


public class NameChecker {
	private char[] AcceptableLetters = new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M'
	                                                ,'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	
	private char[] AcceptableFloors = new char[]{'1','2','3','4','5','6','B','S','G','A'};
	
	private char[] AcceptableIdentifiers = new char[]{'D','R','E'}; //D = door, R = stairs, E = elevators
	private boolean type = false;
	private String stringToCheck = null;
	private int numStringSegments = 0;
	
	//The constructor
	public NameChecker(){ 
	}
	public NameChecker(String stringToCheck,boolean type){
		this.stringToCheck = stringToCheck;
		this.type = type;
	}
	
	//This is where the name that is typed is checked to see if it follows our naming convention 
	public boolean isNameValid(){
		String[] stringSegments = this.stringToCheck.split("_");
		int stringArraySize = stringSegments.length;
		
		int numActualSegs = stringSegments.length;
		
		if(this.type == false){   //false is Cell Name
			numStringSegments = 2;
			if(stringToCheck.equals("World")){
				return true;
			}
			if(stringArraySize != numStringSegments){ 
				return false;
			}
			boolean numcheck = numberOfSegmentCheck(numActualSegs,numStringSegments);
			boolean cellName = cellNameCheck(stringSegments[0]);
			
			boolean cellNum = cellFloorCheck(stringSegments[1]);
						if(numcheck && cellName && cellNum){
				System.out.println("Cell Name is good!");
				return true;
			}
			else { 
			return false;
			} 
		}
		else{ //This checks entry point and entry point references for their name
			numStringSegments = 6;
			if(stringArraySize != numStringSegments){
				System.out.println("Input is invalid: there are not an appropriate amount of segments");
				return false;
			}
			boolean numcheck = numberOfSegmentCheck(numActualSegs,numStringSegments);
			boolean cell1Name = cellNameCheck(stringSegments[0]);
			boolean cell1Num = cellFloorCheck(stringSegments[1]);
			boolean cell2Name = cellNameCheck(stringSegments[2]);
			boolean cell2Num = cellFloorCheck(stringSegments[3]);
			boolean ID = identifierCheck(stringSegments[4]);
			boolean IDnum = checkIDNumber(stringSegments[5]);
			if(numcheck && cell1Name && cell1Num && cell2Name && cell2Num && ID && IDnum){
				System.out.println("EntryPt is good!");
				return true;
			}
			return false;
		}
		
		
	}
	
	//Helper Function to check if the number of segments is valid 
	public boolean numberOfSegmentCheck(int desiredSegs,int actualSegs){
		if(desiredSegs != actualSegs){
			System.out.println("Not a valid string name: wrong number of string segments");
			return false;
		}
		else{ 
		return true;
		}
	}
	
	//Helper Function to check if a Cell name is valid
	public boolean cellNameCheck(String cellName){
		int cellNameCharCounter = 0;
		if(cellName.length() != 2){        //All cell names will always be 2 characters
			System.out.println("Not a valid string name: Cell name is more than 2 char");
			return false;
		}
		for(int i = 0; i < 2; i++){
			for( int j = 0; j<AcceptableLetters.length;j++){
				if(cellName.charAt(i) == AcceptableLetters[j]){
					cellNameCharCounter++;													
				}
			}			
		}
		if(cellNameCharCounter == 2){
			return true;
		}
		System.out.println("Not a valid string name: Cell name has invalid characters");
		return false;
	}
	
	//This is a helper function that will check if a Cell floor value is valid
	public boolean cellFloorCheck(String cellNameVal){
		int cellNameValCharCounter = 0;
		if(cellNameVal.length() != 1){
			System.out.println("Not a valid string name: floor value too many chars");
			return false;
		}
		for(int i = 0; i < 1; i++){
			for(int j = 0; j <AcceptableFloors.length;j++){
				if(cellNameVal.charAt(i) == AcceptableFloors[j]){
					cellNameValCharCounter++;
				}
			}
		}
		if(cellNameValCharCounter == 1){
			return true;
		}
		System.out.println("Not a valid string name: unacceptable floor value");
		return false;		
	}
	
	public boolean identifierCheck(String identifierVal){
		if(identifierVal.length() != 1){
			System.out.println("Not a valid string name: identifier has wrong amount of chars");
			return false;
		}
		for(int i = 0; i<AcceptableIdentifiers.length;i++){
			if(identifierVal.charAt(i)== AcceptableIdentifiers[i]){
				return true;
			}
			return false;
		}
		return false;
		
		
	}
	
	public boolean checkIDNumber(String idNumber){
		Integer idNumberVal = 0;

		//Will check to see if idNumberVal is able to be parsed as an int
		try{
			idNumberVal = Integer.parseInt(idNumber);
		} catch(NumberFormatException e){
			return false;
		}
		if(idNumberVal > 100 || idNumberVal < 0){
			System.out.println("Not a valid string name: identifier number is invalid");
			return false;
			
		}
		return true;
	}
	
}
