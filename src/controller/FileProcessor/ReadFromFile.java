package controller.FileProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFromFile {
	public static ArrayList<ArrayList<Float>> parse(String fileName){
		try {
		      File myObj = new File(fileName);
		      Scanner myReader = new Scanner(myObj);
		      ArrayList<ArrayList<Float>> points = new ArrayList<>();
		      ArrayList<Float> tmpPoint;
		      
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        tmpPoint = new ArrayList<>();
		        tmpPoint.add(Float.parseFloat(data.split(",", 2)[0]));
		        tmpPoint.add(Float.parseFloat(data.split(",", 2)[1].strip()));
		        points.add(tmpPoint);
		      }
		      
		      myReader.close();
		      return points;
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
		return null;
		
	}

}
