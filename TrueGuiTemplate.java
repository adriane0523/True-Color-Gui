import java.awt.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.*;
import java.awt.event.*;  // to use listener interfaces


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import java.util.ArrayList;
import java.text.DecimalFormat;



public class TrueGuiTemplate extends JPanel
 {
	static final int MAX_PIXELS = 300;
	static final int MIN_PIXELS = 0;
	static final int INIT_PIXELS = 50;

	private JFrame frame;
	private  JFrame radiusFrame;
	private JFrame histogramFrame;
	private JFrame dataTable1;
	private Color currentColor;
	private String currentColorValue = "";
	private BufferedImage image, oldImage, newImage1;
	private CanvasPanel canvas = new CanvasPanel();
	private JPanel menuPanel;
	private JButton importButton, exportButton, calculate, clearRadius;
	private JRadioButton trueGreen, trueBlack, trueRed, trueBlue, pixelRadius, switchPhoto, newPhoto;
	private JMenuBar menuBar = new JMenuBar();
    private JMenuItem exportItem, importItem, processImage, imageDeviation;
	private int xCoordinate, yCoordinate;
	private int xCoordinateGreen = -1, yCoordinateGreen = -1;
	private int xCoordinateBlack = -1, yCoordinateBlack = -1;
	private int xCoordinateRed = -1, yCoordinateRed = -1;
	private int xCoordinateBlue = -1, yCoordinateBlue= -1;
	private int xCoordinateRadius = -1, yCoordinateRadius = -1;
	private int selectedRadius = 50;
	public boolean coordinateCheckGreen = false;
	public boolean coordinateCheckBlack = false;
	public boolean coordinateCheckRed = false;
	public boolean coordinateCheckBlue = false;
	private String path = "";
	private JLabel label;
	private boolean validTrueValues = true;
	private boolean radiusSelect = false;
	private BufferedImage newImage;

	private double totalAvgR =0.0;
	private double totalAvgG = 0.0;
	private double totalAvgB=0.0;
	double deviationR = 0.0;
	double deviationG = 0.0;
	double deviationB = 0.0;
	//private static JFileChooser chooser;
    //private static String choosertitle;
	
	DataTable table = new DataTable();



 	public TrueGuiTemplate(){
		// create a JFrame object
		frame = new JFrame("Testing Frame");
		radiusFrame = new JFrame("Get Deviation/Mean");
		histogramFrame = new JFrame ("Histogram");
		dataTable1 = new JFrame("DataTable");
	


		// when a user click the close button at the upper left corner,
		// the window will close and the program will be terminated
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container content = frame.getContentPane();



		menuPanel = new JPanel();
		//importButton = new JButton("Import");
		//exportButton = new JButton("Export");
		JMenu fileMenu = new JMenu("File");
		JMenu trueMenu = new JMenu("True Values");
		JMenu imageMenu = new JMenu("Image");
		exportItem = new JMenuItem("Export Image");
		importItem = new JMenuItem("Import Image");
		imageDeviation = new JMenuItem("Get Deviation/Mean");
		trueGreen = new JRadioButton("True Green");
		trueBlack = new JRadioButton("True Black");
		trueRed = new JRadioButton("True Red");
		trueBlue = new JRadioButton("True Blue");
		processImage = new JMenuItem("Process Image");
		switchPhoto = new JRadioButton("Unprocessed Image");
		switchPhoto.addActionListener(new SwitchListener());
		
		newPhoto = new JRadioButton("Processed Image");
		newPhoto.addActionListener(new SwitchListener());


		trueGreen.addActionListener(new ColorListener());
		trueBlack.addActionListener(new ColorListener());
		trueRed.addActionListener(new ColorListener());
		trueBlue.addActionListener(new ColorListener());
		importItem.addActionListener(new ImportListener());
		exportItem.addActionListener(new ExportListener());
		processImage.addActionListener(new ImageListener());
		imageDeviation.addActionListener(new RadiusListener());

		fileMenu.add(importItem);
		fileMenu.add(exportItem);
		trueMenu.add(trueRed);
		trueMenu.add(trueGreen);
		trueMenu.add(trueBlue);
		trueMenu.add(trueBlack);
		
		imageMenu.add(imageDeviation);
		imageMenu.add(processImage);

		menuBar.add(fileMenu);
		menuBar.add(trueMenu);
		menuBar.add(imageMenu);
		menuBar.add(switchPhoto);
		menuBar.add(newPhoto);
		label = new JLabel("Import new image file");
		label.setForeground(Color.RED);


		//menuPanel.add(importButton);
		menuPanel.setLayout(new BorderLayout());
		menuPanel.add(menuBar, BorderLayout.WEST);

		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(label);
		canvas.setLayout(new BorderLayout());
		canvas.add(p,BorderLayout.NORTH);


		//menuPanel.add(exportButton);


		JSplitPane sPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, menuPanel, canvas);

		content.setLayout(new BorderLayout());
		content.add(sPane, BorderLayout.CENTER);

		pixelRadius = new JRadioButton("Pixel Select");
		pixelRadius.addActionListener(new RadiusColorListener());
		clearRadius = new JButton("Clear Radius");
		clearRadius.addActionListener(new ClearListener());
		calculate = new JButton("Calculate");
		calculate.addActionListener(new CalculateListener());

		JSlider radiusSlider = new JSlider(JSlider.HORIZONTAL,MIN_PIXELS, MAX_PIXELS, INIT_PIXELS);
		radiusSlider.setMajorTickSpacing(50);
		radiusSlider.setMinorTickSpacing(10);
		radiusSlider.setPaintTicks(true);
		radiusSlider.setPaintLabels(true);
		radiusSlider.addChangeListener(new SliderListener());

		JPanel radiusPanel = new JPanel();
		JLabel radiusLabel = new JLabel ("Select Pixel");

		radiusPanel.setLayout(new BorderLayout());
		radiusPanel.add(radiusLabel, BorderLayout.NORTH);
		radiusPanel.add(radiusSlider, BorderLayout.CENTER);

		JPanel radiusPanel1 = new JPanel();
		radiusPanel1.add(pixelRadius);
		radiusPanel1.add(clearRadius);
		radiusPanel1.add(calculate);
		radiusPanel.add(radiusPanel1, BorderLayout.SOUTH);
		
		
		//dataTable1.add(new JScrollPane(table.getDataTable()), BorderLayout.CENTER);



		radiusFrame.setAlwaysOnTop(true);
		radiusFrame.add(radiusPanel);
		

		frame.setSize(1600, 1000); // width is 200 pixels, height is 100 pixels
		//frame.add(radiusPanel);
		frame.setVisible(true);  // make this JFrame object visible
		
	



	}

   private class ImportListener implements ActionListener
    {

    	 public void actionPerformed(ActionEvent event)
    	 {
    		JFileChooser file = new JFileChooser();
			file.setCurrentDirectory(new File(System.getProperty("user.home")));
			//filter the files
			file.setDialogTitle("Import Image");

			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
			file.addChoosableFileFilter(filter);
			int result = file.showOpenDialog(null);
			//if the user click on save in Jfilechooser
			if(result == JFileChooser.APPROVE_OPTION){
				File selectedFile = file.getSelectedFile();
				path = selectedFile.getAbsolutePath();

				try
					{
						BufferedImage temp = ImageIO.read(new File(path));
						image = new BufferedImage(temp.getWidth(), temp.getHeight(), BufferedImage.TYPE_INT_ARGB);
						image = temp;
						oldImage = new BufferedImage(temp.getWidth(), temp.getHeight(), BufferedImage.TYPE_INT_ARGB);
						oldImage = temp;
						newImage = new BufferedImage(temp.getWidth(), temp.getHeight(), BufferedImage.TYPE_INT_ARGB);
						newImage =  temp;
						xCoordinateRadius = -1000;
						yCoordinateRadius = -1000;
						xCoordinateRed = -1;
						yCoordinateRed = -1;
						xCoordinateGreen = -1;
						yCoordinateGreen = -1;
						xCoordinateBlue = -1;
						yCoordinateBlue = -1;
						xCoordinateBlack = -1;
						yCoordinateBlack = -1;
						currentColorValue = "white";
						trueBlack.setSelected(false);
						trueRed.setSelected(false);
						trueBlue.setSelected(false);
						trueGreen.setSelected(false);
						switchPhoto.setSelected(true);
						newPhoto.setSelected(false);


					}
					catch (IOException ex) {
					}

				label.setText("Choose True Values");


			}
			//if the user click on save in Jfilechooser




			else if(result == JFileChooser.CANCEL_OPTION){
				System.out.println("No File Select");
			}

      }





   }
	private class SliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			if (!source.getValueIsAdjusting()) {
				int pixel = (int)source.getValue();
				selectedRadius = pixel;

			}
		}
	}
	private class ClearListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			xCoordinateRadius = -1000;
			yCoordinateRadius = -1000;
			currentColorValue = "white";

		}
	}
	private class CalculateListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			int widthNewImage = newImage.getWidth();
			int heightNewImage = newImage.getHeight();
			int centerX = xCoordinateRadius;
			int centerY = yCoordinateRadius;

			double redAvg = 0.0;
			double blueAvg = 0.0;
			double greenAvg = 0.0;
			double totalPixels = 0.0;




			for (int y = 0; y < heightNewImage; y++)
			{
				for (int x = 0; x < widthNewImage; x++)
				{
					double dx  = (double)x - (double)centerX;
					double dy = (double)y - (double)centerY;
					double distanceSquared = (dx * dx) + (dy * dy);
					double radius = (selectedRadius/2) * (selectedRadius/2);

					if (distanceSquared <= radius)
					{



						int pixel = newImage.getRGB(x,y);
						Color myColor = new Color(pixel);
						int red = myColor.getRed();
						int blue = myColor.getBlue();
						int green = myColor.getGreen();

						redAvg += red;
						blueAvg += blue;
						greenAvg += green;

						totalPixels++;




					//System.out.println(x + ", " + y+"	:" + red +", "+ blue + ", " + green);
					}


				}



			}
			totalAvgR = (double)redAvg/totalPixels;
			totalAvgG = (double)greenAvg/totalPixels;
			totalAvgB = (double)blueAvg/totalPixels;

			double totalSquaredR = 0.0;
			double totalSquaredG = 0.0;
			double totalSquaredB = 0.0;
			
			Histogram h = new Histogram();
			double[] redVal = new double[(int)totalPixels];
			double[] greenVal = new double[(int) totalPixels];
			double[] blueVal = new double[(int)totalPixels];
			
			int count = 0;

			for (int y1 = 0; y1 < heightNewImage; y1++)
			{
				for (int x1 = 0; x1 < widthNewImage; x1++)
				{

					double dx  = (double)x1 - (double)centerX;
					double dy = (double)y1 - (double)centerY;
					double distanceSquared = (dx * dx) + (dy * dy);
					double radius = (selectedRadius/2) * (selectedRadius/2);

					if (distanceSquared <= radius)
					{
						



						int pixel = newImage.getRGB(x1,y1);
						Color myColor = new Color(pixel);
						int red = myColor.getRed();
						int blue = myColor.getBlue();
						int green = myColor.getGreen();

						double tempR = (red - totalAvgR) * (red - totalAvgR);
						double tempG = (green - totalAvgG) * (green - totalAvgG);
						double tempB = (blue - totalAvgB) * (blue - totalAvgB);

						totalSquaredR += tempR;
						totalSquaredG += tempG;
						totalSquaredB += tempB;
						redVal[count] = red;
						greenVal[count] = green;
						blueVal[count] = blue;
						count++;
						
						
					}


				}


			}
			

			deviationR = Math.sqrt(totalSquaredR / totalPixels);
			deviationG = Math.sqrt(totalSquaredG / totalPixels);
			deviationB = Math.sqrt(totalSquaredB / totalPixels);

			System.out.println(deviationR);
			System.out.println(deviationG);
			System.out.println(deviationB);
			
			histogramFrame.add(h.plotGraph(redVal, greenVal, blueVal));
			
			histogramFrame.setSize(800, 600);
			histogramFrame.setVisible(true);
			
			
			String[] columns = new String[] {
					"Color", "Mean", "Deviation"
			};
			
			Object[][] data = new Object[][] {
				{"Red", getAvgR() + "", getDeviationR() + "" },
				{"Green", getAvgG() + "", getDeviationG()  + ""},
				{"Blue",getAvgB() + "", getDeviationB() + ""},

				
			};
			JTable table = new JTable(data,columns);
			dataTable1.add(new JScrollPane(table));
			dataTable1.setSize(500,300);
			dataTable1.setLocation(histogramFrame.getX() + histogramFrame.getWidth(), histogramFrame.getY());
			dataTable1.setVisible(true);

			
			
			
			
			
			
			

		}

	}
	private class RadiusListener implements ActionListener
	  {

		 public void actionPerformed(ActionEvent event)
		 {
			radiusFrame.setSize(500, 300);
			radiusFrame.setVisible(true);




		}
	}
    private class ExportListener implements ActionListener
      {

     	 public void actionPerformed(ActionEvent event)
     	 {


			JFileChooser file = new JFileChooser();
			file.setCurrentDirectory(new File(System.getProperty("user.home")));
			//filter the files
			file.setDialogTitle("Import Image");

			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
			file.addChoosableFileFilter(filter);
			int result = file.showSaveDialog(null);
			//if the user click on save in Jfilechooser
			if(result == JFileChooser.APPROVE_OPTION){
				File selectedFile = file.getSelectedFile();

				String path1 = selectedFile.getAbsolutePath() + ".png";
			try{
				ImageIO.write(newImage, "png", new File(path1));
				xCoordinateRadius = -1000;
				yCoordinateRadius = - 1000;
				xCoordinateRed = -1;
				yCoordinateRed = -1;
				xCoordinateGreen = -1;
				yCoordinateGreen = -1;
				xCoordinateBlue = -1;
				yCoordinateBlue = -1;
				xCoordinateBlack = -1;
				yCoordinateBlack = -1;
				currentColorValue = "white";
				trueBlack.setSelected(false);
				trueRed.setSelected(false);
				trueBlue.setSelected(false);
				trueGreen.setSelected(false);

				}
			catch (IOException e){
					System.out.println("Unable to convert file");
				}


			}
			//if the user click on save in Jfilechooser


			else if(result == JFileChooser.CANCEL_OPTION){
				System.out.println("No File Select");
			}


     	 }
   }
    
    private class SwitchListener implements ActionListener
    {
    	public void actionPerformed(ActionEvent event)
    	{
    		Object action = event.getSource();
		
		
			if (switchPhoto == action)
			{
				
				BufferedImage temp = null;
				try {
					temp = ImageIO.read(new File(path));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				image = new BufferedImage(temp.getWidth(), temp.getHeight(), BufferedImage.TYPE_INT_ARGB) ;
				image = temp;
				newPhoto.setSelected(false);
				
			}
			if(newPhoto == action)
			{
				image = new BufferedImage(newImage.getWidth(), newImage.getHeight(), BufferedImage.TYPE_INT_ARGB) ;
				image = newImage;
				switchPhoto.setSelected(false);
			}
			
    		
    	}
    }

  private class RadiusColorListener implements ActionListener
   {

  	 public void actionPerformed(ActionEvent event)
  	 {
  		Object action = event.getSource();

		if(pixelRadius == action);
		{
			currentColorValue = "radius";
		}
  		canvas.repaint();



  	 }
   }

  private class ColorListener implements ActionListener
   {

  	 public void actionPerformed(ActionEvent event)
  	 {
  		Object action = event.getSource();

  		if (trueGreen == action)
  		{
  	
			
			currentColor = Color.GREEN;
			currentColorValue = "green";
			trueBlack.setSelected(false);
			trueRed.setSelected(false);
			trueBlue.setSelected(false);
			
			

		}
		else if (trueBlack == action)
		{
			
			
			currentColor = Color.BLACK;
			currentColorValue = "black";
			trueGreen.setSelected(false);
			trueRed.setSelected(false);
			trueBlue.setSelected(false);


		}
		else if (trueRed == action)
		{
			
			
	
			currentColor= Color.RED;
			currentColorValue = "red";
			trueBlack.setSelected(false);
			trueGreen.setSelected(false);
			trueBlue.setSelected(false);


		}

		else if (trueBlue == action)
		{
	
			
			
			
			currentColor = Color.BLUE;
			currentColorValue = "blue";
			trueBlack.setSelected(false);
			trueGreen.setSelected(false);
			trueRed.setSelected(false);


		}


  		canvas.repaint();



  	 }
   }
  //Fills in shape
   private class FillListener implements ActionListener
   {
  	  public void actionPerformed(ActionEvent event)
  	  {
  			canvas.repaint();
  	  }
   }


  private class CanvasPanel extends JPanel
    {
       //Constructor to initialize the canvas panel
       public CanvasPanel( )
        {
          // make this canvas panel listen to mouse
          addMouseListener(new PointListener());
          addMouseMotionListener(new PointListener());

         // setBackground(Color.BLACK);
        }


       //this method draws all characters pressed by a user so far
       public void paintComponent(Graphics page)
        {



		super.paintComponent(page);

		if (image != null)
		{
			//frame.setSize(image.getWidth() + 100, image.getHeight() + 100);
		}

		page.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters

		if (coordinateCheckGreen)
		{
			page.setColor(Color.WHITE);
			page.fillOval(xCoordinateGreen - 6 ,yCoordinateGreen - 6,12,12);
			page.setColor(Color.GREEN);
			page.fillOval(xCoordinateGreen - (5),yCoordinateGreen - 5, 10,10);
		}
		if (coordinateCheckBlack)
		{
			page.setColor(Color.WHITE);
			page.fillOval(xCoordinateBlack - 6,yCoordinateBlack - 6,12,12);
			page.setColor(Color.BLACK);
			page.fillOval(xCoordinateBlack - 5,yCoordinateBlack - 5, 10,10);
		}
		if (coordinateCheckRed)
		{
			page.setColor(Color.WHITE);
			page.fillOval(xCoordinateRed - 6,yCoordinateRed - 6,12,12);
			page.setColor(Color.RED);
			page.fillOval(xCoordinateRed - 5,yCoordinateRed -5, 10,10);
		}
		if (coordinateCheckBlue)
		{
			page.setColor(Color.WHITE);
			page.fillOval(xCoordinateBlue - 6,yCoordinateBlue - 6,12,12);
			page.setColor(Color.BLUE);
			page.fillOval(xCoordinateBlue - 5,yCoordinateBlue - 5, 10,10);
		}
		if (radiusSelect)
		{

			//page.setColor(Color.WHITE);
			//page.fillOval(xCoordinateRadius - ((selectedRadius + 3)/2) , (yCoordinateRadius + 3)/2 , selectedRadius + 3,selectedRadius + 3);
			page.setColor(Color.BLACK);
			page.drawOval(xCoordinateRadius - ((selectedRadius + 2)/2) , yCoordinateRadius - ((selectedRadius + 2)/2) , selectedRadius + 2,selectedRadius + 2);
			//page.
		//	page.setColor(Color.WHITE);
		//	page.fillOval(xCoordinateRadius - ((selectedRadius + 1)/2) , (yCoordinateRadius + 1)/2 , selectedRadius + 1,selectedRadius + 1);
		}


		if (coordinateCheckRed && coordinateCheckBlack && coordinateCheckGreen && coordinateCheckBlue)
		{
			//label.setText("Process Image");
		}

		if (currentColorValue.equals("green")){
			page.setColor(Color.WHITE);
			page.fillOval(xCoordinate - 6,yCoordinate - 6,12,12);
			page.setColor(Color.GREEN);
			page.fillOval(xCoordinate - 5,yCoordinate - 5, 10,10);
			xCoordinateGreen = xCoordinate;
			yCoordinateGreen = yCoordinate;
			coordinateCheckGreen = true;



		}
		else if (currentColorValue.equals("black")){
			page.setColor(Color.WHITE);
			page.fillOval(xCoordinate - 6,yCoordinate - 6,12,12);
			page.setColor(Color.BLACK);
			page.fillOval(xCoordinate - 5,yCoordinate - 5, 10,10);
			xCoordinateBlack = xCoordinate;
			yCoordinateBlack = yCoordinate;
			coordinateCheckBlack = true;

		}
		else if (currentColorValue.equals("red")){
			page.setColor(Color.WHITE);
			page.fillOval(xCoordinate - 6,yCoordinate - 6,12,12);
			page.setColor(Color.RED);
			page.fillOval(xCoordinate - 5,yCoordinate - 5, 10,10);
			xCoordinateRed = xCoordinate;
			yCoordinateRed = yCoordinate;
			coordinateCheckRed = true;

		}
		else if (currentColorValue.equals("blue")){
			page.setColor(Color.WHITE);
			page.fillOval(xCoordinate - 6,yCoordinate - 6, 12,12);
			page.setColor(Color.BLUE);
			page.fillOval(xCoordinate - 5 ,yCoordinate - 5, 10,10);
			xCoordinateBlue = xCoordinate;
			yCoordinateBlue = yCoordinate;
			coordinateCheckBlue = true;

		}
		if (currentColorValue.equals("radius"))
		{
			xCoordinateRadius = xCoordinate;
			yCoordinateRadius = yCoordinate;
			page.setColor(Color.WHITE);
			page.fillOval(xCoordinateBlue - 6 + 3,yCoordinateBlue - 6 + 3,15,15);
			page.setColor(Color.BLUE);
			page.fillOval(xCoordinateBlue - 5 + 3,yCoordinateBlue - 5 + 3, 13,13);
			radiusSelect = true;
		}






		canvas.repaint();





        }

      // listener class that listens to the mouse
      public class PointListener implements MouseListener, MouseMotionListener
           {
           //in case that a user presses using a mouse,
           //record the point where it was pressed.

           public void mousePressed (MouseEvent event) {


				int x = event.getX();
				int y = event.getY();
				xCoordinate = x;
				yCoordinate = y;

				int pixel = image.getRGB(xCoordinate,yCoordinate);
				Color myColor = new Color(pixel);
				int r = myColor.getRed();
				int g = myColor.getGreen();
				int b = myColor.getBlue();

				System.out.print("Mouse Clicked Image. RGB: "+ currentColorValue + " " +r + "," + g + "," + b + "\n");

				label.setText("Current RGB clicked pixel"+ " " +r + "," + g + "," + b);




             }
           public void mouseClicked (MouseEvent event) {



  			 }
           public void mouseReleased (MouseEvent event) {
  			 //resets the left,right,top,bottom click
  			 /*
  			 if (event.getSource() == canvas)
  			 {
  				 left = false;
  				 right = false;
  				 top = false;
  				 bottom = false;
  			 }
  			 */


             }
           public void mouseEntered (MouseEvent event) {}
           public void mouseExited (MouseEvent event) {}
           public void mouseMoved(MouseEvent event) {}
           public void mouseDragged(MouseEvent event)
            {

  		   }






      } // end of PointListener



  } // end of Canvas Panel Class

	 private class ImageListener implements ActionListener
 	 {

 		 public void actionPerformed(ActionEvent event)
 		 {
			applyNewImage();
			validTrueValues = true;
			xCoordinateRed = -1;
			yCoordinateRed = -1;
			xCoordinateGreen = -1;
			yCoordinateGreen = -1;
			xCoordinateBlue = -1;
			yCoordinateBlue = -1;
			xCoordinateBlack = -1;
			yCoordinateBlack = -1;
			currentColorValue = "white";

 		 }
	}

	public double getTrueGreenR()
	{

		int pixel = newImage.getRGB(xCoordinateGreen,yCoordinateGreen);
		Color myColor = new Color(pixel);
		double r = myColor.getRed();
		return r;

	}

	public double getTrueGreenG()
	{
		int pixel = newImage.getRGB(xCoordinateGreen,yCoordinateGreen);
		Color myColor = new Color(pixel);
		double g = myColor.getGreen();

		return g;
	}

	public double getTrueGreenB()
	{
 		int pixel = newImage.getRGB(xCoordinateGreen,yCoordinateGreen);
		Color myColor = new Color(pixel);
		double b = myColor.getBlue();
		return b;
	}

	public double getTrueRedR()
	{
		int pixel = newImage.getRGB(xCoordinateRed,yCoordinateRed);
		Color myColor = new Color(pixel);
		double r = myColor.getRed();

		return r;
	}
	public double getTrueRedG()
	{
		int pixel = newImage.getRGB(xCoordinateRed,yCoordinateRed);
		Color myColor = new Color(pixel);
		int g = myColor.getGreen();

		return g;

	}
	public double getTrueRedB()
	{
		int pixel = newImage.getRGB(xCoordinateRed,yCoordinateRed);
		Color myColor = new Color(pixel);
		double b = myColor.getBlue();

		return b;
	}
	public double getTrueBlackR()
	{
		int pixel = newImage.getRGB(xCoordinateBlack,yCoordinateBlack);
		Color myColor = new Color(pixel);
		double r = myColor.getRed();


		return r;
	}
	public double getTrueBlackG()
	{
		int pixel = newImage.getRGB(xCoordinateBlack,yCoordinateBlack);
		Color myColor = new Color(pixel);
		double g = myColor.getGreen();

		return g;
	}
	public double getTrueBlackB()
	{
		int pixel = newImage.getRGB(xCoordinateBlack,yCoordinateBlack);
		Color myColor = new Color(pixel);
		double b = myColor.getBlue();

		return b;
	}
	public double getTrueBlueR()
	{
		int pixel = image.getRGB(xCoordinateBlue,yCoordinateBlue);
		Color myColor = new Color(pixel);
		double r = myColor.getRed();

		return r;
	}
	public double getTrueBlueG()
	{
		int pixel = newImage.getRGB(xCoordinateBlue,yCoordinateBlue);
		Color myColor = new Color(pixel);
		double g = myColor.getGreen();

		return g;
	}
	public double getTrueBlueB()
	{
	int pixel = newImage.getRGB(xCoordinateBlue,yCoordinateBlue);
		Color myColor = new Color(pixel);
		double b = myColor.getBlue();

		return b;
	}



	public void applyNewImage()
	{
		double getTrueBlackR = getTrueBlackR();
		double getTrueBlackG = getTrueBlackG();
		double getTrueBlackB = getTrueBlackB();
		double getTrueRedR = getTrueRedR();
		double getTrueGreenG = getTrueGreenG();
		double getTrueBlueB = getTrueBlueB();



		int widthNewImage = newImage.getWidth();
		int heightNewImage = newImage.getHeight();

		if (getTrueBlackB() <= getTrueRedB() && getTrueBlackB() <= getTrueBlueB() && getTrueBlackB() <= getTrueGreenB() && getTrueBlackG() <= getTrueRedG() && getTrueBlackG() <= getTrueBlueG() && getTrueBlackG() <= getTrueGreenG() && getTrueBlackR() <= getTrueRedR() && getTrueBlackR() <= getTrueBlueR() && getTrueBlackR() <= getTrueGreenR() )
		{
			for (int y = 0; y < heightNewImage; y++)
			{
				for (int x = 0; x < widthNewImage; x++)
				{

					int pixel = newImage.getRGB(x,y);
					Color myColor = new Color(pixel);

					int red = myColor.getRed();
					int blue = myColor.getBlue();
					int green = myColor.getGreen();

					//System.out.println(xCoordinateRed + " ," + yCoordinateRed);
					//System.out.println(xCoordinateGreen + " ," + yCoordinateGreen);
					//System.out.println(xCoordinateBlue + " ," + yCoordinateBlue);

				/*	if ((int)getTrueRedR() != red && (int)getTrueGreenG() != green && (int)getTrueBlueB() != blue)
					{*/



						double newRed = (Double.valueOf(red) - getTrueBlackR) * (Double.valueOf(255.0/(getTrueRedR - getTrueBlackR)));

					//	System.out.println();
					//	System.out.println ("pixel red " + red + "-APPLY NEW RED: " + Math.round(newRed));
						double newGreen = (Double.valueOf(green - getTrueBlackG)) * (Double.valueOf(255.0/(getTrueGreenG - getTrueBlackG)));

					//	System.out.println ("pixel green " + green + "-APPLY NEW GREEN: " + Math.round(newGreen));


						double newBlue = (Double.valueOf(blue - getTrueBlackB)) * (Double.valueOf(255.0/(getTrueBlueB - getTrueBlackB)));

				//		System.out.println ("pixel blue: " + blue + "-APPLY NEW BLUE: " + Math.round(newBlue));

						int rgb = new Color((int)Math.round(newRed), (int) Math.round(newGreen),(int) Math.round(newBlue)).getRGB();
						newImage.setRGB(x,y, rgb);
						newPhoto.setSelected(true);
						switchPhoto.setSelected(false);
						
						
					
						
						
			/*		}
					else
					{
						int rgb = new Color(255, 255,255).getRGB();
						newImage.setRGB(x,y, rgb);
					}*/

					}
				}

			}
		else
		{
			label.setText("Unable to Process image due to invalid Black RGB values");
			validTrueValues = false;
		}
	}
	
	public double getAvgR()
	{
		return totalAvgR;
	}
	public double getAvgG()
	{
		return totalAvgG;
	}
	public double getAvgB()
	{
		return totalAvgB;
	}
	
	public double getDeviationR()
	{
		return deviationR;
	}
	
	public double getDeviationG()
	{
		return deviationG;
	}
	public double getDeviationB()
	{
		return deviationB;
	}
	}

	
	


