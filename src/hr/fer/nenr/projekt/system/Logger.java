package hr.fer.nenr.projekt.system;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Logger {

	private BufferedWriter writeBuf;
	private final String DEFAULTFILE = "D:\\log.txt";
	
	public Logger(){
		new Logger(DEFAULTFILE);
	}
	
	public Logger(String filename){
		try {
			writeBuf = new BufferedWriter(new FileWriter(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void write(String text){
		try {
			writeBuf.write(text);
			writeBuf.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			writeBuf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}