package controller.classifications;

import java.util.ArrayList;

public class ThreeClass {
	public ThreeClass() {
		
	}
	
	public float[] classify(ArrayList<ArrayList<Float>> points) {
		ArrayList<ArrayList<Float>> C1 = new ArrayList<>();
		ArrayList<ArrayList<Float>> C2 = new ArrayList<>();
		ArrayList<ArrayList<Float>> C3 = new ArrayList<>();
		float[] categories = new float[points.size()];
		for(int i=0;i<points.size();i++) {
			boolean condOk = false;
			float x1 = points.get(i).get(0);
			float x2 = points.get(i).get(1);
			boolean cond1 = (float) (Math.pow((x1-0.5), 2) + Math.pow((x2-0.5), 2)) < 0.2;
			boolean cond2 = (float) (Math.pow((x1+0.5), 2) + Math.pow((x2+0.5), 2)) < 0.2;
			boolean cond3 = (float) (Math.pow((x1-0.5), 2) + Math.pow((x2+0.5), 2)) < 0.2;
			boolean cond4 = (float) (Math.pow((x1+0.5), 2) + Math.pow((x2-0.5), 2)) < 0.2;
			if(cond1 || cond4) {
				if(x2 > 0.5) {
					C1.add(points.get(i));
					categories[i] = 1;
					condOk = true;
				}else if(x2 < 0.5){
					C2.add(points.get(i));
					categories[i] = 2;
					condOk = true;
				}
			}
			
			if(cond2 || cond3) {
				if(x2 > -0.5) {
					C1.add(points.get(i));
					categories[i] = 1;
					condOk = true;
				}else if(x2 < -0.5){
					C2.add(points.get(i));
					categories[i] = 2;
					condOk = true;
				}
			}
			
			if(!condOk) {
				C3.add(points.get(i));
				categories[i] = 3;
			}

		}
		
		return categories;
	}
}
