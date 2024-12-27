/*
 * test.java
 *
 * Created on February 4, 2011, 12:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.io.*;
import java.util.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;

/**
 *
 * @author fahadsaeed
 */
/**
 * Creates a new instance of test
 */
public class Graph {

    public static TreeSet<String> getGraph(String outputfilepath, String outputFolderPath) {
        TreeSet<String> graphSet = new TreeSet<>();
        //reading the cluster output result
        XYSeries series;
        XYSeriesCollection dataset = new XYSeriesCollection();
        ArrayList<String> myArr = new ArrayList<>();
        BufferedReader input = null;
        //String filename="/Users/fahadsaeed/Desktop/output_1.txt";
        String filename = outputfilepath;
        try {
            FileReader fileClustalW = new FileReader(filename);
            input = new BufferedReader(fileClustalW);
        } catch (FileNotFoundException x) { //The file may not exist.
            System.err.println("File not found:" + filename);
            System.exit(2);
        }

        try {

            String line;
            int countsPerCluster = 1;

            while ((line = input.readLine()) != null) {
//                System.out.println(line);
                String[] candidate = line.split("\\s+");

                myArr.add(candidate[candidate.length - 1]);
                if (myArr.size() >= 2) {
                    if (myArr.get(myArr.size() - 1).equals(myArr.get(myArr.size() - 2))) {
//                        System.out.println();
                        countsPerCluster++;
                    } else {
                        dataset = new XYSeriesCollection();
                        countsPerCluster = 1;
                    }
                }

//                System.out.println("countPerCluster = " + countsPerCluster);
//        Create a simple XY chart           
                series = new XYSeries(candidate[0]);

                int totaldatapoints = candidate.length - 2;
                int count = 0;

                //generates series for a single cluster
                while (totaldatapoints >= 1) {
                    count++;
                    series.add(count, Double.parseDouble(candidate[count]));
                    totaldatapoints--;
                    if (totaldatapoints == 0) {
                        dataset.addSeries(series);
                    }
                }

//                System.out.println("totaldatapoints = " + candidate.length);
                JFreeChart chart = ChartFactory.createXYLineChart(candidate[candidate.length - 1], "Time Points", "Ratio's", dataset, PlotOrientation.VERTICAL, true, true, false);
                chart.setBackgroundPaint(Color.WHITE);
                XYPlot plot = (XYPlot) chart.getPlot();
                NumberAxis rangeAxis = (NumberAxis) plot.getDomainAxis();
                rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
                try {

                    String fily = outputFolderPath + "/" + candidate[candidate.length - 1] + ".jpg";
                    ChartUtilities.saveChartAsJPEG(new File(fily), chart, 100 * candidate.length, (countsPerCluster * 7) + 400);
                    graphSet.add(candidate[candidate.length - 1] + ".jpg");

                } catch (IOException e) {
                    System.err.println("Problem occurred creating chart.");
                }

            }//end of while

        } catch (IOException | NumberFormatException t) {
        }

        return graphSet;
    }
}
