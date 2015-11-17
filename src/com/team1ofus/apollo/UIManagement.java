package com.team1ofus.apollo;

public class UIManagement {
	ApolloUI window;
	PaintTool paintTool;
	public UIManagement() {
		initialize();
	}
	void initialize() {
		window = new ApolloUI();
		
		paintTool = new PaintTool();
	}
	
}
