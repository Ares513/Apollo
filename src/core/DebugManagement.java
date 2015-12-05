package core;

import java.text.NumberFormat;
import java.util.Date;

public class DebugManagement {
	
	public static void writeLineToLog(SEVERITY_LEVEL severity, String message) {
		Date d = new Date();
		System.out.println("[ " + d + "] " + severity.toString() + ": " + message);
		DebugConsole.addLine("[ " + d + "] " + severity.toString() + ": " + message);
			 	
	}
	public static void writeNotificationToLog(String message) {
		Date d = new Date();	
		
		if(BootstrapperConstants.WRITE_NOTIFICATIONS) {
			
			
			DebugConsole.addLine("[ " + d + "] " + "NOTIFICATION" + ": " + message);
			System.out.println("[ " + d + "] " + "NOTIFICATION" + ": " + message);
		}
		
	}
	/*
	 * Prints metrics to the console.
	 */
	public static void metrics() {
		Runtime runtime = Runtime.getRuntime();

		NumberFormat format = NumberFormat.getInstance();

		StringBuilder sb = new StringBuilder();
		long maxMemory = runtime.maxMemory();
		long allocatedMemory = runtime.totalMemory();
		long freeMemory = runtime.freeMemory();

		writeNotificationToLog("free memory: " + format.format(freeMemory / 1024) + "<br/>");
		writeNotificationToLog("allocated memory: " + format.format(allocatedMemory / 1024) + "<br/>");
		writeNotificationToLog("max memory: " + format.format(maxMemory / 1024) + "<br/>");
		writeNotificationToLog("total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024) + "<br/>");
	}
}
