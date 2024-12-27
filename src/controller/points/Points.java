package controller.points;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import controller.FileProcessor.WriteToFile;

public class Points {
	public Points() {
		
	}
	
	public ArrayList<ArrayList<Float>> generatePoints(int pointsNum, double minX, double maxX, double minY, double maxY) {
		ArrayList<ArrayList<Float>> points = new ArrayList<>();
		
		Random randomFloat = new Random();
		
		for(int i=0;i<pointsNum;i++) {
			ArrayList<Float> tmpPoint = new ArrayList<>();
			tmpPoint.add((float) (randomFloat.nextFloat() * (maxX - minX) + minX));
			tmpPoint.add((float) (randomFloat.nextFloat() * (maxY - minY) + minY));
			points.add(tmpPoint);
		}
		
		WriteToFile.write(points);
		return points;
	}
	
	public HashMap<String, ArrayList<ArrayList<Float>>> pointsToTrainTest(ArrayList<ArrayList<Float>> points) {
		ArrayList<ArrayList<Float>> train = new ArrayList<>();
		ArrayList<ArrayList<Float>> test = new ArrayList<>();
		
		for(int i = 0;i<points.size()/2;i++) {
			train.add(points.get(i));
			test.add(points.get(points.size()-1-i));
		}
		
		return new HashMap<String, ArrayList<ArrayList<Float>>>() {{
		    put("train", train);
		    put("test", test);
		}};
	}
	
	public static float[] getX(ArrayList<ArrayList<Float>> points) {
		float[] x = new float[points.size()];
		
		for(int i = 0;i<points.size();i++) {
			x[i] = points.get(i).get(0);
		}
		
		return x;
	}
	
	public static float[] getY(ArrayList<ArrayList<Float>> points) {
		float[] y = new float[points.size()];
		
		for(int i = 0;i<points.size();i++) {
			y[i] = points.get(i).get(1);
		}
		
		return y;
	}
}
