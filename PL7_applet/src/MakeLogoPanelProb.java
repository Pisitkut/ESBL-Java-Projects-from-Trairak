

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jacquelinedouglass
 */

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.lang.Math.*;
//import com.ms.awt.FontX;

/**
 *
 * @author Jacqueline Douglass
 * @version 1.0
 * @description class for generating sequence logos
 */
public class MakeLogoPanelProb {


//Panel for Characters
    private static class Logo extends JPanel {
        private final char character;
        private final double scale;
        private final Color charColor;
        public static Font charFont;
        public static int charheight;
        public JPanel mainPanel;
        private final int groupType;


        public Logo(char character, double scale, Color charColor, int groupType, Color colorBackground) {
            this.character = character;
            this.scale = scale;
            this.charColor = charColor;
            this.setBackground(colorBackground);
            this.groupType = groupType;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
           g2.setFont(new Font("Consolas", Font.BOLD,(int) Math.round(1.53*this.getHeight())));

   HashMap<Character, Integer> grouping;
   int symbolInt;
   char newChar  ;
    ArrayList<Character> groupingsLabel;
    ArrayList<Integer> groupingsSymbol;

     newChar = character;

     if(character =='Q'){
         g2.scale(0.999*this.getWidth()/this.getHeight(), 0.78);
     }
 else if (character == 'S') {
         g2.scale(0.999*this.getWidth()/this.getHeight(), 0.985);
     }
 else{
         g2.scale(0.999*this.getWidth()/this.getHeight(), 0.999);
 }
            
           g2.setColor(charColor);
           g2.drawString(String.valueOf(newChar), 0 ,(int) Math.round(this.getHeight()));
        }
    }

//Panel for Axis Numbers
        private static class LogoNum extends JPanel {
        private final String numString;
        private Color colorAxes;

        public  LogoNum(String numString,Color colorBackground, Color colorAxes) {
            this.numString = numString;
            this.setBackground(colorBackground);
            this.colorAxes = colorAxes;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
           g2.setFont(new Font("Arial Narrow", Font.BOLD,(int) Math.round(1*this.getHeight())));
           g2.scale(0.7*this.getWidth()/this.getHeight(), 0.9);
           g2.setColor(colorAxes);
           g2.drawString(numString, 0 ,(int) Math.round(this.getHeight()));
        }
    }

//Panel for Black Tick Marks
        private static class AxisBlack extends JPanel {
        private final double scale;
        public JPanel mainPanel;

        public AxisBlack(double scale, Color colorAxes) {
            this.scale = scale;
            this.setBackground(colorAxes);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.scale(0.999*this.getWidth()/this.getHeight(), 0.01);
        }
    }

    private static class JVLabel extends JLabel {
        public JVLabel(String s) {
            super(s);
            setPreferredSize(new Dimension(30,100));
            setMinimumSize(new Dimension(30,100));
        }
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            java.awt.geom.AffineTransform at = g2.getTransform();
            g2.setTransform(at);
            super.paintComponent(g);
        }
    }

    public static JPanel plot(char[][] seqAlign, java.awt.Window parent, double[][] aaProbabilities,ArrayList<Character> groupingsLabel ,HashMap<Character, Color> groupingsColor, int gT, int modifiedPos, Color colorBackground, Color colorAxes, Color colorModified, boolean charOn) {


    int groupType = gT;
    int modifiedPosition = modifiedPos;
    char[] aa= {'A','C','D','E','F','G','H','I','K','L','M','N','P','Q','R','S','T','V','W','Y'};

    //Make width flexible
    int lengthSeq = aaProbabilities[0].length;

    int width = 1138;
    int height = 437;       //437
    int panelHeight = height - (500-444) - 4;
    int axisPanelWidth = 17;
    int totalLogoWidth = width - (1200- 1124);
    int logoWidth = totalLogoWidth/lengthSeq;
    int remainingWidth = (totalLogoWidth - logoWidth*lengthSeq)/2;
    int logoHeight = height - (500-432) - 4;



        //Scale vertical axis to 125% height of tallest AA stack
       double maxHeightPos=1; //maximum probability
       double scaleChar = panelHeight/(maxHeightPos);
       int maxAxis = (int) Math.floor(maxHeightPos);


//Create Main Panels//////////////////////////////////////////////////////////////Create Main Panels//////////////////////////////////////////////////////////////
        

        //create layouts
        JDialog seqlogo = new JDialog(parent);
       JPanel mainPanel = new JPanel(new BorderLayout());
       mainPanel.setBackground(Color.WHITE);
       mainPanel.setSize(width, height);
        Font labelFont = new Font("Arial",Font.BOLD,12);

        //title
        JLabel titleLabel = new JLabel("Relative Frequencies");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(labelFont);
        mainPanel.add(titleLabel, BorderLayout.PAGE_START);

        //x-axis and y-axis
        JLabel xaxisLabel = new JLabel("Residue Positions");
        xaxisLabel.setHorizontalAlignment(JLabel.CENTER);
        xaxisLabel.setFont(labelFont);
        mainPanel.add(xaxisLabel, BorderLayout.PAGE_END);



        //Create Plot Panel
       //JPanel plotPanel = new JPanel(new GridLayout(2,1));
        JPanel plotPanel = new JPanel();
        plotPanel.setLayout(new BoxLayout(plotPanel,BoxLayout.PAGE_AXIS));
        plotPanel.setBackground(colorBackground);

        //Create two positive plot panels
        JPanel plotPanelPos = new JPanel(new GridLayout(1, lengthSeq)); //1 row,lengthSeq = #cols
        plotPanelPos.setBackground(colorBackground);
        JPanel plotPanelPos2 = new JPanel();
         plotPanelPos2.setBackground(colorBackground);
        plotPanelPos2.setLayout(new BoxLayout(plotPanelPos2,BoxLayout.LINE_AXIS));



        //Add Positive Left Axis///////////////////////////////////////////////Add Positive Left Axis/////////////////////////////////////////////////////////
        JPanel axisPanel = new JPanel();
        axisPanel.setLayout(new BoxLayout(axisPanel,BoxLayout.PAGE_AXIS));
        axisPanel.setBackground(colorBackground);

               //y-axis label
        JVLabel yaxisLabel = new JVLabel("");
        yaxisLabel.setVerticalAlignment(JLabel.CENTER);
        yaxisLabel.setFont(labelFont);
        mainPanel.add(yaxisLabel, BorderLayout.LINE_START);


        LogoNum axisLogoNum;
        Logo axisLogo;


         Double[] probAxisTickMarks = new Double[]{0.8, .6, 0.4, 0.2};
        for(int i = 0; i<4 ;i++){

                     //White
                     axisLogo= new Logo('X', (int) 1 ,colorBackground, groupType,colorBackground);
                     if(i ==0){
                         axisLogo.setPreferredSize(new Dimension(4, (int) (scaleChar*0.2 - 11)));
                     }
                    else{
                         axisLogo.setPreferredSize(new Dimension(4, (int) (scaleChar*0.2 - 18)));
                    }
                     axisPanel.add(axisLogo);

                        //tick mark labels
                        axisLogoNum= new LogoNum(" " + Double.toString(probAxisTickMarks[i]), colorBackground, colorAxes); //
                        axisLogoNum.setPreferredSize(new Dimension(4, 15));
                        axisPanel.add(axisLogoNum);

                         //Black
                        AxisBlack axisBlack = new AxisBlack(1, colorAxes);
                        axisBlack.setPreferredSize(new Dimension(4, 2));
                        axisPanel.add(axisBlack);
        }
                     //White
                     axisLogo= new Logo('X', (int) 1 ,colorBackground, groupType,colorBackground);
                     axisLogo.setPreferredSize(new Dimension(4, (int) (scaleChar*.2 - 15)));
                     axisPanel.add(axisLogo);

                        //zero tick mark labels
                        axisLogoNum= new LogoNum(" 0.0", colorBackground, colorAxes); //
                        axisLogoNum.setPreferredSize(new Dimension(4, 15));
                        axisPanel.add(axisLogoNum);

                    JPanel blackBox = new JPanel();
                    blackBox.setBackground(colorAxes);
                    blackBox.setPreferredSize(new Dimension(logoWidth,3));
                    blackBox.setMaximumSize(new Dimension(logoWidth,3));
                    blackBox.setMinimumSize(new Dimension(logoWidth,3));
                    axisPanel.add(blackBox);

            JPanel coordinateNum = new JPanel(new FlowLayout());
            coordinateNum.setBackground(colorBackground);
            Font f = new Font("Arial Narrow", Font.BOLD, 15);
            JLabel coordinateLabel = new JLabel(" ");
            coordinateLabel.setVerticalTextPosition(JLabel.CENTER);
            coordinateLabel.setFont(f);
            coordinateNum.setPreferredSize(new Dimension(4,13));
            coordinateNum.setMaximumSize(new Dimension(4,13));
            coordinateNum.setMinimumSize(new Dimension(4,13));
            coordinateNum.add( coordinateLabel);
            axisPanel.add(coordinateNum);


        axisPanel.setPreferredSize(new Dimension(axisPanelWidth,panelHeight));
        plotPanelPos2.add(axisPanel);

         //Add tall, narrow panel to take into account leftover width from the logo
        JPanel remainderPanelLeft = new JPanel();
        remainderPanelLeft.setPreferredSize(new Dimension(remainingWidth,logoHeight));
        remainderPanelLeft.setMaximumSize(new Dimension(remainingWidth,logoHeight));
        remainderPanelLeft.setMinimumSize(new Dimension(remainingWidth,logoHeight));
        remainderPanelLeft.setBackground(colorBackground);
        plotPanelPos2.add(remainderPanelLeft);

        //Positive IC values///////////////////////////////////////////////////////////Positive Freq values///////////////////////////////////////////////////////////
        for(int i = 0; i< lengthSeq; i++){                //loop through positions
                    JPanel poslogo = new JPanel();
                    double  extraHeight;
                    extraHeight = 0;
                    poslogo.setLayout(new BoxLayout(poslogo, BoxLayout.PAGE_AXIS));
                    poslogo.setBackground(colorBackground);
                    poslogo.add(Box.createVerticalGlue());

            //Put the IC values into a treemap so it's sorted (from largest to smallest)
            TreeMap<Character, Double> myMap = new TreeMap<Character, Double>();
               for (int g = 0; g < groupingsLabel.size(); g++){
                    myMap.put(groupingsLabel.get(g),aaProbabilities[g][i]);
            }


            //////////CREATE SECOND MAP FOR INDIVIDUAL AAs
               TreeMap<Character, Double> myMapAA = new TreeMap<Character, Double>();
               for (int g = 0; g < aa.length; g++){
                    myMapAA.put(aa[g],aaProbabilities[groupingsLabel.size()+g][i]);
            }

                //Sort myMap
                ArrayList<Map.Entry<Character, Double>> myMapList = new ArrayList<Map.Entry<Character, Double>>(myMap.entrySet());
                Comparator mapComparator = new Comparator<Map.Entry<Character, Double>>() {
                    public int compare(Map.Entry<Character, Double> o1, Map.Entry<Character, Double> o2) {
                        return o2.getValue().compareTo(o1.getValue()); //Descending
                    }
                };
                //Collections.sort(myMapList, mapComparator);

                //Sort myMapAA
                ArrayList<Map.Entry<Character, Double>> myMapListAA = new ArrayList<Map.Entry<Character, Double>>(myMapAA.entrySet());
                Comparator mapComparatorAA = new Comparator<Map.Entry<Character, Double>>() {
                    public int compare(Map.Entry<Character, Double> o1, Map.Entry<Character, Double> o2) {
                        return o2.getValue().compareTo(o1.getValue()); //Descending
                    }
                };
                //Collections.sort(myMapListAA, mapComparatorAA);

            //Determine total character height for groups
            int totalHeight = 0;
            int remainingHeight = 0;
            for (int j = 0; j < myMapList.size(); j++) {
                    char c =myMapList.get(j).getKey();
                    double scale = myMapList.get(j).getValue();
                    totalHeight +=  (int) ((scale*scaleChar));
            }
            remainingHeight = panelHeight - totalHeight;

            //Determine total character height for AAs at modified Position
            int totalHeightAA = 0;
            int remainingHeightAA = 0;
            for (int j = 0; j < myMapListAA.size(); j++) {
                    char c =myMapListAA.get(j).getKey();
                    double scale = myMapListAA.get(j).getValue();
                    totalHeightAA +=  (int) (scale*scaleChar);
            }
            remainingHeightAA = panelHeight - totalHeightAA;


            //Add non-modified AAs/groups
            if(i!=modifiedPosition){
                boolean first = true;
            for (int j = 0; j < myMapList.size(); j++) {
                    char c =myMapList.get(j).getKey();
                    double scale = myMapList.get(j).getValue();

                if(scale==0){   //get ERROR where last unused AA with scale=IC=0 prints in LOGO!  I don't understand this.
                }
                else{
                       Logo myLogo;
                        
                           if(charOn){
                               myLogo = new Logo(c,scale,groupingsColor.get(c), groupType,colorBackground);         //make character symbols
                           }
                            else{
                                myLogo = new Logo(c,scale,groupingsColor.get(c), groupType,groupingsColor.get(c));            //make bar graphs
                            }

                       if(first){
                           myLogo.setPreferredSize(new Dimension(logoWidth, ((int) (scale*scaleChar)) +remainingHeight));
                           myLogo.setMaximumSize(new Dimension(logoWidth, ((int) (scale*scaleChar))+ remainingHeight));
                           myLogo.setMinimumSize(new Dimension(logoWidth, ((int) (scale*scaleChar))+ remainingHeight));
 
                       }
                else{
                 myLogo.setPreferredSize(new Dimension(logoWidth, (int) (scale*scaleChar)));
                 myLogo.setMaximumSize(new Dimension(logoWidth, (int) (scale*scaleChar)));
                 myLogo.setMinimumSize(new Dimension(logoWidth, (int) (scale*scaleChar)));
                    }
                    poslogo.add(myLogo);
                    first = false;
                 }
            }
            }


            //Add modified AAs
            else{
                    boolean first = true;
                    for (int j = 0; j < myMapListAA.size(); j++) {
                    char c =myMapListAA.get(j).getKey();
                    double scale =  myMapListAA.get(j).getValue();

                if(scale==0){   //get ERROR where last unused AA with scale=IC=0 prints in LOGO!  I don't understand this.
                }
                else{
                       Logo myLogo;

                            if(charOn){
                                myLogo = new Logo(c,scale,colorModified, groupType,colorBackground); //Make center residues Black, make character symbols
                            }
                            else{
                                myLogo = new Logo(c,scale,colorModified, groupType,colorModified); //Make center residues Black, make bar graphs
                            }

                if(first){
                           myLogo.setPreferredSize(new Dimension(logoWidth, ((int) (scale*scaleChar)) + remainingHeightAA));
                           myLogo.setMaximumSize(new Dimension(logoWidth, ((int) (scale*scaleChar))+ remainingHeightAA));
                           myLogo.setMinimumSize(new Dimension(logoWidth, ((int) (scale*scaleChar))+ remainingHeightAA));
                       }
                else{
                 myLogo.setPreferredSize(new Dimension(logoWidth, ((int) (scale*scaleChar))));
                 myLogo.setMaximumSize(new Dimension(logoWidth, ((int) (scale*scaleChar))));
                 myLogo.setMinimumSize(new Dimension(logoWidth, ((int) (scale*scaleChar))));
                    }
                    poslogo.add(myLogo);
                    first = false;

                 }
            }
            }

                blackBox = new JPanel();
                blackBox.setBackground(colorAxes);
                blackBox.setPreferredSize(new Dimension(logoWidth,3));
                blackBox.setMaximumSize(new Dimension(logoWidth,3));
                blackBox.setMinimumSize(new Dimension(logoWidth,3));
                poslogo.add(blackBox);


             coordinateNum = new JPanel(new FlowLayout());
            coordinateNum.setBackground(colorBackground);
             f = new Font("Arial Narrow", Font.BOLD, 15);
             coordinateLabel = new JLabel(Integer.toString(i - modifiedPosition));
            //coordinateLabel.setVerticalTextPosition(JLabel.CENTER);
            //coordinateLabel.setHorizontalTextPosition(JLabel.CENTER);
            coordinateLabel.setFont(f);
            coordinateNum.setPreferredSize(new Dimension(logoWidth,20));
            coordinateNum.setMaximumSize(new Dimension(logoWidth,20));
            coordinateNum.setMinimumSize(new Dimension(logoWidth,20));
            coordinateNum.add( coordinateLabel);
            //coordinateNum.setBorder(BorderFactory.createLineBorder(Color.blue));
            poslogo.add(coordinateNum);

            poslogo.setPreferredSize(new Dimension(logoWidth,panelHeight));
            poslogo.setMaximumSize(new Dimension(logoWidth,panelHeight));
            poslogo.setMinimumSize(new Dimension(logoWidth,panelHeight));
            plotPanelPos.add(poslogo);
        }
       
 
        plotPanel.setBackground(colorAxes);
        plotPanelPos.setPreferredSize(new Dimension(((int) (totalLogoWidth/lengthSeq))*lengthSeq,panelHeight));
        plotPanelPos2.add(plotPanelPos);

        //Add tall, narrow panel to take into account leftover width from the logo
        JPanel remainderPanelRight = new JPanel();
        remainderPanelRight.setPreferredSize(new Dimension(remainingWidth,logoHeight));
        remainderPanelRight.setMaximumSize(new Dimension(remainingWidth,logoHeight));
        remainderPanelRight.setMinimumSize(new Dimension(remainingWidth,logoHeight));
        remainderPanelRight.setBackground(colorBackground);
        plotPanelPos2.add(remainderPanelRight);

      //Add Positive Right Axis////////////////////////////////////////////////////////Add Positive Right Axis////////////////////////////////////////////////////////////////////////
        JPanel axisPanelRight = new JPanel();
        axisPanelRight.setLayout(new BoxLayout(axisPanelRight,BoxLayout.PAGE_AXIS));
        axisPanelRight.setBackground(colorBackground);

               //y-axis label
        yaxisLabel = new JVLabel("");
        yaxisLabel.setVerticalAlignment(JLabel.CENTER);
        yaxisLabel.setFont(labelFont);
        mainPanel.add(yaxisLabel, BorderLayout.LINE_START);

        probAxisTickMarks = new Double[]{0.8, .6, 0.4, 0.2};
        for(int i = 0; i<4 ;i++){

                     //White
                     axisLogo= new Logo('X', (int) 1 ,colorBackground, groupType,colorBackground);
                     if(i ==0){
                         axisLogo.setPreferredSize(new Dimension(4, (int) (scaleChar*0.2 - 11)));
                     }
                    else{
                         axisLogo.setPreferredSize(new Dimension(4, (int) (scaleChar*0.2 - 18)));
                    }
                     axisPanelRight.add(axisLogo);

                        //tick mark labels
                        axisLogoNum= new LogoNum(Double.toString(probAxisTickMarks[i]), colorBackground, colorAxes); //
                        axisLogoNum.setPreferredSize(new Dimension(4, 15));
                        axisPanelRight.add(axisLogoNum);

                         //Black
                        AxisBlack axisBlack = new AxisBlack(1, colorAxes);
                        axisBlack.setPreferredSize(new Dimension(4, 2));
                        axisPanelRight.add(axisBlack);
        }
                     //White
                     axisLogo= new Logo('X', (int) 1 ,colorBackground, groupType,colorBackground);
                     axisLogo.setPreferredSize(new Dimension(4, (int) (scaleChar*.2 - 15)));
                     axisPanelRight.add(axisLogo);

                        //zero tick mark labels
                        axisLogoNum= new LogoNum("0.0", colorBackground, colorAxes); //
                        axisLogoNum.setPreferredSize(new Dimension(4, 15));
                        axisPanelRight.add(axisLogoNum);

                    blackBox = new JPanel();
                    blackBox.setBackground(colorAxes);
                    blackBox.setPreferredSize(new Dimension(logoWidth,3));
                    blackBox.setMaximumSize(new Dimension(logoWidth,3));
                    blackBox.setMinimumSize(new Dimension(logoWidth,3));
                    axisPanelRight.add(blackBox);

            coordinateNum = new JPanel(new FlowLayout());
            coordinateNum.setBackground(colorBackground);
            f = new Font("Arial Narrow", Font.BOLD, 15);
            coordinateLabel = new JLabel(" ");
            coordinateLabel.setVerticalTextPosition(JLabel.CENTER);
            coordinateLabel.setFont(f);
            coordinateNum.setPreferredSize(new Dimension(4,13));
            coordinateNum.setMaximumSize(new Dimension(4,13));
            coordinateNum.setMinimumSize(new Dimension(4,13));
            coordinateNum.add( coordinateLabel);
            axisPanelRight.add(coordinateNum);

         //Add Right Axis Panel
        axisPanelRight.setPreferredSize(new Dimension(axisPanelWidth,panelHeight));
        plotPanelPos2.add(axisPanelRight);

        //Add positive panel
        plotPanel.add(plotPanelPos2);

        mainPanel.add(plotPanel, BorderLayout.CENTER);
        plotPanel.setBorder(BorderFactory.createLineBorder(colorAxes,3));


    return mainPanel;

    }

}

