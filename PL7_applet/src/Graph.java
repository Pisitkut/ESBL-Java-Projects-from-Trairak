/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/*
 * test.java
 *
 * Created on February 4, 2011, 12:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

//package javaapplication5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.axis.*;
import java.util.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

/**
 *
 * @author fahadsaeed
 */

    /** Creates a new instance of test */

public class Graph {

    public static ChartPanel getGraph(double[][] Pvalues, ArrayList<String> groupings, int modifiedPosition, double AAAlpha) {
        //reading the cluster output result
                    XYSeries series;
                    XYSeriesCollection dataset = new XYSeriesCollection();
                    boolean upperRangeUsed = false;
                   

                    int clustercount=1;          //equals number of series

            JFreeChart chart=null;
            for(int k = 0; k< groupings.size(); k++){
                //Only add present groups
                if(groupings.get(k).length() > 0) {

            //Create a simple XY chart
            series = new XYSeries(groupings.get(k));         //series label - use grouping strings

            int lengthSeq =Pvalues.length;                 //length of sequence
            int count = 0;  //x index
            int totaldatapoints = lengthSeq;
            //generates series for a single cluster
           while(totaldatapoints>=1){
               if(count == modifiedPosition){
                   series.add(count - (modifiedPosition),null);
               }
               else if(Pvalues[count][k + 1] == 0)
               {         //pvalue too small ~ 0
                   if(modifiedPosition >= 0  && modifiedPosition < lengthSeq ) series.add(count - (modifiedPosition),16.0);
                   else series.add(count,16.0);
                   //upperRangeUsed = true;
               }
                else{
                   if(modifiedPosition >= 0  && modifiedPosition < lengthSeq ) series.add(count - (modifiedPosition),-1*Math.log10(Pvalues[count][k+1]));
                   else series.add(count,-1*Math.log10(Pvalues[count][k+1]));
                    //series.add(count,-1*Math.log10(Pvalues[count][k+1]));      //add row corresponding to y values for that series (series num = count)//////////////??????????
                }

              // System.out.println("count = " + count + " k = " + k + " " + -1*Math.log10(Pvalues[count][k+1]));
               totaldatapoints--;
               if(totaldatapoints==0)dataset.addSeries(series);         //add series to dataset
               count++;
            }


          //  System.out.println(clustercount);

            chart = ChartFactory.createXYLineChart(null,"Position","-log10(P-value)", dataset, PlotOrientation.VERTICAL, true, true, false );       //title, xaxis, yaxis, data  //"<html>" +"Amino Acid -log<sub>10</sup>(P-value) in Each Position"+" </html>"
            chart.setBackgroundPaint(Color.WHITE);
           // chart.getPlot()
            //Plot p = chart.getPlot(); // Get the Plot object for a bar graph
            chart.getPlot().setBackgroundPaint(Color.white); // Modify the plot background
            //chart.getPlot().  //.setRangeGridlinePaint(Color.red); // Modify the colour of the plot gridlines Modify Chart
            //chart.getPlot().setDomainGridlinePaint(Color.white);
            //chart.getPlot().setRangeGridlinePaint(Color.white);


            XYPlot plot = (XYPlot)chart.getPlot();
            NumberAxis rangeAxis = (NumberAxis) plot.getDomainAxis();
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());


            XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer();
        xylineandshaperenderer.setLinesVisible( true);
        xylineandshaperenderer.setShapesVisible( true);   
        plot.setRenderer(xylineandshaperenderer);

        float dash1[] = { 10.0f };
        ValueMarker valueMarker = new ValueMarker(-1*Math.log10(AAAlpha), Color.BLACK, new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f ));
        plot.addRangeMarker(valueMarker);
            //rangeAxis.setAutoRange( true);
            
           // if(upperRangeUsed)
            //rangeAxis.setUpperBound(10.0);
            //rangeAxis.DEFAULT_AUTO_RANGE;
            //plot.setRangeAxes(ValueAxis[] {0.0,10.0});
            //rangeAxis.set
            //ValueAxis valueAxis = new ValueAxis();
            //rangeAxis.
            //rangeAxis.setAxisRange(0.0, 100.0);
            //rangeAxis.setMaximumAxisValue(100.0);
            //rangeAxis.setUpperBound(double)
                 
//
//            try {
//
//               String fily=groupings.get(k)+".jpg";                //Create chart for each series
//               ChartUtilities.saveChartAsJPEG(new File(fily), chart, 500,100*clustercount);
//
//
//           } catch (IOException e) {
//            System.err.println("Problem occurred creating chart.");
//        }
        }
                }//end of while



            ChartPanel panel = new ChartPanel(chart);
            panel.setSize(new Dimension(300,650));
//
//panel.setMaximumDrawHeight(400);
//panel.setMinimumDrawHeight(400);
//panel.setMaximumDrawWidth(800);
//panel.setMaximumDrawWidth(800);
            //Why do I have to add this ChartPanel to a frame to get it to show up in another JPanel?
//            JFrame frame = new JFrame();
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//    //3. Create components and put them in the frame.
//    //...create emptyLabel...
//     //   frame.getContentPane().add(panel);//, BorderLayout.CENTER);
//
////4. Size the frame.
//    frame.pack();
//    frame.setSize(new Dimension(800,400));
//
////5. Show it.
//    frame.setVisible(false);



        return panel;





   }

//    public static void main(String[]args){
//
//        getGraph();
//    System.out.println();
//
//    }




}





