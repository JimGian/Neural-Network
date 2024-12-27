package visualizer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ScatterPlot extends JFrame{
	private static final long serialVersionUID = 1L;

	public ScatterPlot() {

	}

	  public ScatterPlot(String title, HashMap<String, ArrayList<ArrayList<Float>>> data) {
	    super(title);
	    
	    XYDataset dataset = createDataset(data);

	    JFreeChart chart = ChartFactory.createScatterPlot(
	    	new TreeSet<String>(data.keySet()).toString(),
	        "X-Axis", "Y-Axis", dataset, PlotOrientation.VERTICAL, false, false,
            false);

	    XYPlot plot = (XYPlot)chart.getPlot();
	    plot.setBackgroundPaint(new Color(255,228,196));
	       
	    ChartPanel panel = new ChartPanel(chart);
	    setContentPane(panel);
	  }
	  
	  private XYDataset createDataset(HashMap<String, ArrayList<ArrayList<Float>>> data) {
		    XYSeriesCollection dataset = new XYSeriesCollection();
		    
		    for(String key: new TreeSet<String>(data.keySet())) {
		    	XYSeries tmpSeries = new XYSeries(key);
		    	for(int i = 0;i < data.get(key).size();i++) {
		    		tmpSeries.add(data.get(key).get(i).get(0), data.get(key).get(i).get(1));
		    	}
		    	dataset.addSeries(tmpSeries);
		    }
		    
		    return dataset;
	  }
}
