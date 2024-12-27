package controller.FileProcessor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteToFile {
	
	public static void write(ArrayList<ArrayList<Float>> points) {
		try {
		      FileWriter myWriter = new FileWriter("points.txt");
		      
		      for(int i = 0;i<points.size();i++) {
		    	  myWriter.write(points.get(i).get(0) + ", " + points.get(i).get(1) + "\n");
		      }
		      
		      myWriter.close();
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}

}
