import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.*;
import java.io.*;
import java.util.Random;

import javax.swing.JPanel;

import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.statistics.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
/*
 public class HistogramExample {
       public static void main(String[] args) {
       
   }
 }
 */
public class Histogram {
	
	public Histogram(){
		
	     
	}
	
	public JPanel plotGraph(double[] red, double[] green, double[] blue)
	{
		JPanel panel = new JPanel();
		String plotTitle = "Histogram"; 
		String xaxis = "RGB";
		String yaxis = "Frequency"; 
	//	int number = 255;
		HistogramDataset dataset = new HistogramDataset();
		//dataset.setType(HistogramType.S);
	

		dataset.addSeries("Histogram",red,256);
		dataset.addSeries("Histogram",blue,256);
		dataset.addSeries("Histogram",green,256);
		
		
		
		
		PlotOrientation orientation = PlotOrientation.VERTICAL;
		
		boolean show = false; 
		boolean toolTips = false;
		boolean urls = false; 
		JFreeChart chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, 
		            dataset, orientation, show, toolTips, urls);
		
		final XYPlot plot = chart.getXYPlot();
        plot.setDomainAxis(new NumberAxis("RGB"));
        plot.getRenderer().setSeriesPaint(0, Color.RED);
        plot.getRenderer().setSeriesPaint(1, Color.GREEN);
        plot.getRenderer().setSeriesPaint(2, Color.BLUE);
        
        ChartPanel chartPanel = new ChartPanel(chart);
        ///chartPanel.createChartPrintJob();
        
		int width = 500;
		int height = 300; 
		
		return chartPanel;
		/*
	    try {
	    ChartUtilities.saveChartAsPNG(new File("histogram.PNG"), chart, width, height);
	} catch (IOException e) {}
	*/
 }
		

		
	}
	


