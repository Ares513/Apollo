package com.team1ofus.apollo;

public class UIManagement {
	ApolloUI window;
	PaintTool painttool;
	public UIManagement() {
		initialize();
	}
	void initialize() {
		window = new ApolloUI();
		painttool = new PaintTool();
	}
	
}
