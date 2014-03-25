package ai_assignment_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class inputThread extends Thread {
	
	public static boolean newInput = false;
	public static String newInputString = "";

	public boolean checkInput(){
		boolean ret = newInput;
		newInput = false;
		return ret;
	}
	
	public String getInput(){
		return newInputString;
	}
	
	public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Please Enter Node");
		while (true){
		try {
			String inputString = reader.readLine();
			System.out.println("Input : " + inputString);
			newInputString = inputString;
			newInput = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	}
}
