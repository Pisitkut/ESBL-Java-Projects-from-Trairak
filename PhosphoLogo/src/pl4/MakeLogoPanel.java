package pl4;

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
public class MakeLogoPanel {


//Panel for Characters
    private static class Logo extends JPanel {
        private final char character;
        private final double scale;
        private final Color charColor;
        public static Font charFont;
        public static int charheight;
        public JPanel mainPanel;
        private final int groupType;
        private int logoWidth;



        public Logo(char character, double scale, Color charColor, int groupType, Color colorBackground, int logoWidth) {
            this.character = character;
            this.scale = scale;
            this.charColor = charColor;
            this.setBackground(colorBackground);
            this.groupType = groupType;
            this.logoWidth = logoWidth;
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
// else if (character == '\u2191') {
//         g2.scale(0.999*this.getWidth()/this.getHeight(), 0.78);
//     }
 else{
         g2.scale(0.999*this.getWidth()/this.getHeight(), 0.999);
 }
           g2.setColor(charColor);

           FontMetrics fm = g2.getFontMetrics();


  if (character == '\u2191') {
         g2.drawString(String.valueOf(newChar),(int) Math.round(-0.05*this.getWidth()),(int) Math.round(1.2*this.getHeight()));
     }
 else{
           g2.drawString(String.valueOf(newChar),0,(int) Math.round(this.getHeight()));
            }
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


        //Panel for Axis Numbers
        private static class CoordNum extends JPanel {
        private final String numString;
        private Color colorAxes;

        public  CoordNum(String numString,Color colorBackground, Color colorAxes) {
            this.numString = numString;
            this.setBackground(colorBackground);
            this.colorAxes = colorAxes;
          //  this.add(BorderLayout.CENTER, numString);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
           g2.setFont(new Font("Arial Narrow", Font.BOLD,(int) Math.round(1*this.getHeight())));
           g2.scale(0.3*this.getWidth()/this.getHeight(), 0.9);
           g2.setColor(colorAxes);
           FontMetrics fm = g2.getFontMetrics();

           //g2.drawString(numString, (this.getWidth()-fm.stringWidth(numString))/2 ,(int) Math.round(this.getHeight()));
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

    public static JPanel plot(char[][] seqAlign, java.awt.Window parent, double[][] ICaa, ArrayList<Character> groupingsLabel,HashMap<Character, Color> groupingsColor, int gT, int modifiedPos, Color colorBackground, Color colorAxes, Color colorModified, boolean charOn, Boolean[][] allSignificant, boolean showFavored) {


    int groupType = gT;
    int modifiedPosition = modifiedPos;
    char[] aa= {'A','C','D','E','F','G','H','I','K','L','M','N','P','Q','R','S','T','V','W','Y'};

    //Make width flexible
    int lengthSeq = ICaa[0].length;
    int totalLogoWidth = 1138 - (1200-1124);
    int logoWidth = totalLogoWidth/lengthSeq;
    int logoHeight = 437 - (500-432);
    int remainingWidth = (totalLogoWidth - logoWidth*lengthSeq)/2;
    int axisWidth = 17;

    //Organize IC values/////////////////////////////////////////////////////////Organize IC values/////////////////////////////////////////////////////////
        //Separate positive and negative IC values
        double[][] ICaaPos = new double[ICaa.length][ICaa[0].length];
        double[][] ICaaNeg = new double[ICaa.length][ICaa[0].length];


        for(int i =0; i<ICaa[0].length;i++){                        //loop through all positions
            for(int j=0; j<ICaa.length;j++){                  //loop through all AAs
                if(ICaa[j][i]>0){             //Add only significant AAs
                    if(j<20){
                        if(allSignificant[i][j + 1] ){               
                             ICaaPos[j][i]= ICaa[j][i];
                        }
                    }
                else{
                        ICaaPos[j][i]= ICaa[j][i];
                }
                    
                }
                 else if (ICaa[j][i]<0){
                    ICaaNeg[j][i]= -1*ICaa[j][i];
                 }
                else{
                }
            }
        }

        //Sum positive and negative IC values
        double[] ICsumPos = new double[ICaa[0].length];
        double[] ICsumNeg = new double[ICaa[0].length];
        for(int i =0; i<ICaa[0].length;i++){                           //loop through all positions
            double sumPos = 0;
            double sumNeg = 0;
            for(int j=0; j<ICaa.length;j++){                  //loop through all AAs
                sumPos += ICaaPos[j][i];
                sumNeg += ICaaNeg[j][i];
            }
            ICsumPos[i]=sumPos;
            ICsumNeg[i]=sumNeg;
            System.out.println("For " + Integer.toString(i-modifiedPosition) + ", sum of IC values = " +  ICsumPos[i]); // + " Neg = " +  ICsumNeg[i] + " Sum = " +  (ICsumPos[i] - ICsumNeg[i]) );
        }

        //used for ordering from highest to lowest
        //find out what the highest sum is
        double highestSumPos = 0.0;
        double highestSumNeg = 0.0;
        for(int i = 0; i<ICsumPos.length; i++)    {         //loop through all positions to find highest ICsum
            highestSumPos = (ICsumPos[i] > highestSumPos) ? ICsumPos[i] : highestSumPos;
            highestSumNeg = (ICsumNeg[i] > highestSumNeg) ? ICsumNeg[i] : highestSumNeg;
        }

        //Scale vertical axis to 125% height of tallest AA stack
       double maxHeightPos;
       if(showFavored){
           maxHeightPos=highestSumPos/0.8+0.0000001;
       }
 else{
           maxHeightPos=highestSumNeg/0.8+0.0000001;
 }
       double scaleChar = logoHeight/maxHeightPos;
       int maxAxis = (int) Math.floor(maxHeightPos);

        //find out what the highest IC value is
        double highestICPos = 0.0;
        double highestICNeg = 0.0;
        for(int i = 0; i<ICaa.length; i++){
          for(int j = 0; j<ICaa[0].length; j++){         //loop through all positions and AAs to find highest IC
                highestICPos = (ICaaPos[i][j] > highestICPos) ? ICaaPos[i][j] : highestICPos;
                highestICNeg = (ICaaNeg[i][j] > highestICNeg) ? ICaaNeg[i][j] : highestICNeg;
            }
        }

//Create Main Panels//////////////////////////////////////////////////////////////Create Main Panels//////////////////////////////////////////////////////////////
        int width = 1138;
        int height = 437;

        //create layouts
        JDialog seqlogo = new JDialog(parent);
       JPanel mainPanel = new JPanel(new BorderLayout());
       mainPanel.setSize(width, height);
       mainPanel.setBackground(Color.WHITE);
        Font labelFont = new Font("Arial",Font.BOLD,12);

        //title
        JLabel titleLabel = new JLabel("Information Content");
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
        JPanel plotPanelPos = new JPanel(new GridLayout(1, lengthSeq)); //1 row, ICsum.length = #cols
        plotPanelPos.setBackground(colorBackground);
        JPanel plotPanelPos2 = new JPanel();
         plotPanelPos2.setBackground(colorBackground);
        plotPanelPos2.setLayout(new BoxLayout(plotPanelPos2,BoxLayout.LINE_AXIS));

        //Create two negative plot panels
        JPanel plotPanelNeg = new JPanel(new GridLayout(1, lengthSeq)); //1 row, ICsum.length = #cols
        plotPanelNeg.setBackground(colorBackground);
        JPanel plotPanelNeg2 = new JPanel();
        plotPanelNeg2.setLayout(new BoxLayout(plotPanelNeg2,BoxLayout.LINE_AXIS));
        plotPanelNeg2.setBackground(colorBackground);


         //Add Positive Left Axis///////////////////////////////////////////////Add Positive Left Axis/////////////////////////////////////////////////////////
        JPanel axisPanel = new JPanel();
        axisPanel.setLayout(new BoxLayout(axisPanel,BoxLayout.PAGE_AXIS));
        axisPanel.setBackground(colorBackground);

        //determine number of steps
        double step = 0.35 *highestSumPos;
        
        int divisions = 0;
        while(Math.floor(step) < 1 ){
            step=1.2*step;        //determine step size
            divisions ++;       //determine number of divisions in step size
        }
        double stepSize = Math.floor(step)/(Math.pow(1.2,divisions));           //round stepSize to nearest increment
       double maxScale = stepSize*Math.floor(maxHeightPos/stepSize);       //determine number of tick marks

        //determine number of exponents
        int exponent = 1;
        while(maxScale - Math.pow(10,exponent) < 0){
            exponent --;
        }

        //maxScale = Math.floor(maxScale/Math.pow(10, exponent-1)) * Math.pow(10,exponent-1);         //round to 2 sig figs
        stepSize = Math.floor(stepSize/Math.pow(10, exponent-1)) * Math.pow(10,exponent-1);         //round to 1 sig figs
        if(stepSize == 0.0) {stepSize =0.5;}
       //double stepSize = Math.floor(1.0/Math.pow(10, exponent-1)) * Math.pow(10,exponent-1);         //round to 2 sig figs
        maxScale = stepSize*Math.floor(maxHeightPos/stepSize);          //round to 2 sig figs
        int numTicks = (int) Math.floor(maxScale/stepSize);
       double maxScaleLabel = Math.round(maxScale / Math.pow(10,exponent)*10.0)/10.0;
       double  stepSizeLabel =  Math.round(stepSize / Math.pow(10,exponent)*10.0)/10.0;


        //y-axis label
        JVLabel yaxisLabel = new JVLabel("<html>" +"Bits"+ "<br>" + "x10<sup>" + Integer.toString(exponent) + "</sup> </html>");
        yaxisLabel.setVerticalAlignment(JLabel.CENTER);
        yaxisLabel.setFont(labelFont);
        mainPanel.add(yaxisLabel, BorderLayout.LINE_START);
       // mainPanel.add(yaxisLabel, BorderLayout.WEST);

       int countScale = 0;
        LogoNum axisLogoNum;

        //Add top part of axis
        axisPanel.add(Box.createVerticalGlue());
        Logo axisLogoTop= new Logo('X', (maxHeightPos - maxScale) ,colorBackground, groupType,colorBackground, logoWidth);
        axisLogoTop.setPreferredSize(new Dimension(4,(int) (scaleChar*(maxHeightPos - maxScale) - 15 +9)));      
        countScale += (int) (scaleChar*(maxHeightPos - maxScale) - 15 );
        axisPanel.add(axisLogoTop);
        //Add top tick mark label
          axisLogoNum= new LogoNum(" "+Double.toString(maxScaleLabel), colorBackground, colorAxes);//
         axisLogoNum.setPreferredSize(new Dimension(4, 15));
         countScale +=15;
         axisPanel.add(axisLogoNum);

//maxScale/stepSize
        for(int i = 0; i<numTicks -1 ;i++){
            axisPanel.add(Box.createVerticalGlue());

                    //Black
                    AxisBlack axisBlack = new AxisBlack(1, colorAxes);
                    axisBlack.setPreferredSize(new Dimension(4, 2));
                    countScale +=2;
                    axisPanel.add(axisBlack);
                     //White
                     Logo axisLogo= new Logo('X', (int) 1 ,colorBackground, groupType,colorBackground, logoWidth);
                     axisLogo.setPreferredSize(new Dimension(4, (int) (stepSize*scaleChar - 17)));
                     countScale +=(int) (stepSize*scaleChar - 17);
                     axisPanel.add(axisLogo);
                    //tick mark labels
                     axisLogoNum= new LogoNum(" "+Double.toString(Math.round((maxScaleLabel-(stepSizeLabel*(i+1)))*10.0)/10.0), colorBackground, colorAxes); //
                     axisLogoNum.setPreferredSize(new Dimension(4, 15));
                     countScale +=15;
                     axisPanel.add(axisLogoNum);
        }
                    //Black
                    AxisBlack axisBlackBot = new AxisBlack(1, colorAxes);
                    axisBlackBot.setPreferredSize(new Dimension(4, 2));
                    countScale +=2;
                    axisPanel.add(axisBlackBot);
                    //White
                     Logo axisLogoLast= new Logo('X', (int) 1 ,colorBackground, groupType,colorBackground, logoWidth);
                     axisLogoLast.setPreferredSize(new Dimension(4, logoHeight +0 - countScale - 16 ));          //- 15
                     axisPanel.add(axisLogoLast);
                     //Zero Label
                     axisLogoNum= new LogoNum(" 0.0", colorBackground, colorAxes); //
                     axisLogoNum.setPreferredSize(new Dimension(4, 15));
                    // countScale +=10;
                     axisPanel.add(axisLogoNum);


                     JPanel blackBox = new JPanel();
                    blackBox.setBackground(colorAxes);
                    blackBox.setPreferredSize(new Dimension(logoWidth,3));
                    blackBox.setMaximumSize(new Dimension(logoWidth,3));
                    blackBox.setMinimumSize(new Dimension(logoWidth,3));
                    axisPanel.add(blackBox);


            JPanel coordinateNum = new JPanel(new FlowLayout());
            coordinateNum.setBackground(colorBackground);
            Font f = new Font("Arial Narrow", Font.BOLD, 12);
            JLabel coordinateLabel = new JLabel(" ");
            coordinateLabel.setVerticalTextPosition(JLabel.CENTER);
            coordinateLabel.setFont(f);
            coordinateNum.setPreferredSize(new Dimension(4,20));
            coordinateNum.setMaximumSize(new Dimension(4,20));
            coordinateNum.setMinimumSize(new Dimension(4,20));
            coordinateNum.add( coordinateLabel);
            //coordinateNum.setBorder(BorderFactory.createLineBorder(Color.RED));
            axisPanel.add(coordinateNum);

        axisPanel.setPreferredSize(new Dimension(axisWidth,logoHeight));
        plotPanelPos2.add(axisPanel);

        //Add tall, narrow panel to take into account leftover width from the logo
        JPanel remainderPanelLeft = new JPanel();
        remainderPanelLeft.setPreferredSize(new Dimension(remainingWidth,logoHeight));
        remainderPanelLeft.setMaximumSize(new Dimension(remainingWidth,logoHeight));
        remainderPanelLeft.setMinimumSize(new Dimension(remainingWidth,logoHeight));
        remainderPanelLeft.setBackground(colorBackground);
        
        plotPanelPos2.add(remainderPanelLeft);

        //Positive IC values///////////////////////////////////////////////////////////Positive IC values///////////////////////////////////////////////////////////
        for(int i = 0; i< ICsumPos.length; i++){                //loop through positions
                    JPanel poslogo = new JPanel();
                    double  extraHeight;
                    extraHeight = maxHeightPos - ICsumPos[i];
                    poslogo.setLayout(new BoxLayout(poslogo, BoxLayout.PAGE_AXIS));
                    poslogo.setBackground(colorBackground);
                    poslogo.add(Box.createVerticalGlue());

            //Put the IC values into a treemap so it's sorted (from largest to smallest)
            TreeMap<Character, Double> myMap = new TreeMap<Character, Double>();
               for (int g = 0; g < groupingsLabel.size(); g++){
                    myMap.put(groupingsLabel.get(g),ICaaPos[g][i]);
            }
            if(i == modifiedPosition){         //only set "modification AA" IC values if position is modified position
                for(int j = 0; j<aa.length; j++ ){
                    myMap.put(aa[j],ICaaPos[groupingsLabel.size()+j][i]);
                }
            }

                //Sort myMap
                ArrayList<Map.Entry<Character, Double>> myMapList = new ArrayList<Map.Entry<Character, Double>>(myMap.entrySet());
                Comparator mapComparator = new Comparator<Map.Entry<Character, Double>>() {
                    public int compare(Map.Entry<Character, Double> o1, Map.Entry<Character, Double> o2) {
                        return o2.getValue().compareTo(o1.getValue()); //Descending
                    }
                };
                Collections.sort(myMapList, mapComparator);

            //Determine total character height
            int totalHeight = 0;
            for (int j = 0; j < myMapList.size(); j++) {
                    char c =myMapList.get(j).getKey();
                    double scale = myMapList.get(j).getValue();
                    totalHeight +=  (int) (scale*scaleChar);
            }

             //Add X
                char cX = 'X';
                double scaleX = extraHeight;
                Logo xlogo= new Logo(cX,scaleX,colorBackground, groupType,colorBackground, logoWidth);
                xlogo.setPreferredSize(new Dimension(logoWidth,logoHeight-totalHeight));
                xlogo.setMaximumSize(new Dimension(logoWidth,logoHeight-totalHeight));
                xlogo.setMinimumSize(new Dimension(logoWidth,logoHeight-totalHeight));
                poslogo.add(xlogo);

            //Add each of the characters
            for (int j = 0; j < myMapList.size(); j++) {
                    char c =myMapList.get(j).getKey();
                    double scale = myMapList.get(j).getValue();

                if(scale==0){   //get ERROR where last unused AA with scale=IC=0 prints in LOGO!  I don't understand this.
                }
                else{
                       Logo myLogo;
                        if(i==modifiedPosition){
                            if(charOn){
                                myLogo = new Logo(c,scale,colorModified, groupType,colorBackground, logoWidth); //Make center residues Black, make character symbols
                            }
                            else{
                                myLogo = new Logo(c,scale,colorModified, groupType,colorModified, logoWidth); //Make center residues Black, make bar graphs
                            }
                        }
                        else{
                           if(charOn){
                               myLogo = new Logo(c,scale,groupingsColor.get(c), groupType,colorBackground, logoWidth);         //make character symbols
                           }
                            else{
                                myLogo = new Logo(c,scale,groupingsColor.get(c), groupType,groupingsColor.get(c), logoWidth);            //make bar graphs
                            }
                        }
                            
                 myLogo.setPreferredSize(new Dimension(logoWidth, (int) Math.round(scale*scaleChar)));
                 myLogo.setMaximumSize(new Dimension(logoWidth, (int) Math.round(scale*scaleChar)));
                 myLogo.setMinimumSize(new Dimension(logoWidth, (int) Math.round(scale*scaleChar)));
                    poslogo.add(myLogo);
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
//             if(allSignificant[i][0]){
//                 coordinateLabel = new JLabel(Integer.toString(i-modifiedPosition) + "*");      //Add asterisk if position is significant
//             }
//            else{
                 coordinateLabel = new JLabel(Integer.toString(i-modifiedPosition));
        //    }
            coordinateLabel.setVerticalTextPosition(JLabel.CENTER);
            coordinateLabel.setFont(f);
            coordinateNum.setPreferredSize(new Dimension(logoWidth,20));
            coordinateNum.setMaximumSize(new Dimension(logoWidth,20));
            coordinateNum.setMinimumSize(new Dimension(logoWidth,20));
            coordinateNum.add( coordinateLabel);
            poslogo.add(coordinateNum);
           
            plotPanelPos.add(poslogo);
        }

        plotPanel.setBackground(colorAxes);
        //plotPanelPos.setPreferredSize(new Dimension(1110,logoHeight));
        plotPanelPos.setPreferredSize(new Dimension(((int) (totalLogoWidth/lengthSeq))*lengthSeq,logoHeight));
        plotPanelPos2.add(plotPanelPos);

         //Add tall, narrow panel to take into account leftover width from the logo
        JPanel remainderPanelRight = new JPanel();
        remainderPanelRight.setPreferredSize(new Dimension(remainingWidth,logoHeight));
        remainderPanelRight.setMaximumSize(new Dimension(remainingWidth,logoHeight));
        remainderPanelRight.setMinimumSize(new Dimension(remainingWidth,logoHeight));
        remainderPanelRight.setBackground(colorBackground);
        plotPanelPos2.add(remainderPanelRight);

      //Add Positive Right Axis////////////////////////////////////////////////////////Add Positive Right Axis////////////////////////////////////////////////////////////////////////
        JPanel axisPanelPos2 = new JPanel();
        axisPanelPos2.setLayout(new BoxLayout(axisPanelPos2,BoxLayout.PAGE_AXIS));
        axisPanelPos2.setBackground(colorBackground);
        //White
        Logo axisLogoTop2= new Logo('X', (maxHeightPos - maxScale) ,colorBackground,groupType,colorBackground, logoWidth);
        axisLogoTop2.setPreferredSize(new Dimension(4,(int) (scaleChar*(maxHeightPos - maxScale) - 15 + 9)));
        axisPanelPos2.add(axisLogoTop2);
         //Add top tick mark label
          axisLogoNum= new LogoNum(" "+Double.toString(maxScaleLabel), colorBackground, colorAxes);//
         axisLogoNum.setPreferredSize(new Dimension(4, 15));
         axisPanelPos2.add(axisLogoNum);

        for(int i = 0; i<numTicks - 1;i++){
                    //Black
                    AxisBlack axisBlack = new AxisBlack(1, colorAxes);
                    axisBlack.setPreferredSize(new Dimension(4, 2));
                    axisPanelPos2.add(axisBlack);
                    //White
                     Logo axisLogo= new Logo('X', (int) 1 ,colorBackground,groupType,colorBackground, logoWidth);
                    axisLogo.setPreferredSize(new Dimension(4,(int)(stepSize*scaleChar -17)));
                    axisPanelPos2.add(axisLogo);
                                        //tick mark labels
                     axisLogoNum= new LogoNum(" "+Double.toString(Math.round((maxScaleLabel-(stepSizeLabel*(i+1)))*10.0)/10.0), colorBackground, colorAxes); //
                     axisLogoNum.setPreferredSize(new Dimension(4, 15));
                     axisPanelPos2.add(axisLogoNum);
        }
                    //Black
                    axisBlackBot = new AxisBlack(1, colorAxes);
                    axisBlackBot.setPreferredSize(new Dimension(4, 2));
                    axisPanelPos2.add(axisBlackBot);
                    //White
                     axisLogoLast= new Logo('X', (int) 1 ,colorBackground, groupType,colorBackground, logoWidth);
                     axisLogoLast.setPreferredSize(new Dimension(4, logoHeight +0 -countScale - 16));
                     axisPanelPos2.add(axisLogoLast);
                     //Zero Label
                     axisLogoNum= new LogoNum(" 0.0", colorBackground, colorAxes); //
                     axisLogoNum.setPreferredSize(new Dimension(4, 15));
                     axisPanelPos2.add(axisLogoNum);


                    blackBox = new JPanel();
                    blackBox.setBackground(colorAxes);
                    blackBox.setPreferredSize(new Dimension(logoWidth,3));
                    blackBox.setMaximumSize(new Dimension(logoWidth,3));
                    blackBox.setMinimumSize(new Dimension(logoWidth,3));
                    axisPanelPos2.add(blackBox);


            coordinateNum = new JPanel(new FlowLayout());
            coordinateNum.setBackground(colorBackground);
            f = new Font("Arial Narrow", Font.BOLD, 12);
            coordinateLabel = new JLabel(" ");
            coordinateLabel.setVerticalTextPosition(JLabel.CENTER);
            coordinateLabel.setFont(f);
            coordinateNum.setPreferredSize(new Dimension(4,20));
            coordinateNum.setMaximumSize(new Dimension(4,20));
            coordinateNum.setMinimumSize(new Dimension(4,20));
            coordinateNum.add( coordinateLabel);
            axisPanelPos2.add(coordinateNum);

        axisPanelPos2.setPreferredSize(new Dimension(axisWidth,logoHeight));
        plotPanelPos2.add(axisPanelPos2);

        //Add positive panel, create horizon axis, add negative panel
        plotPanel.add(plotPanelPos2);


        mainPanel.add(plotPanel, BorderLayout.CENTER);
        plotPanel.setBorder(BorderFactory.createLineBorder(colorAxes,3));


    return mainPanel;

    }

    public static class NiceScale {

    private double minPoint;
    private double maxPoint;
    private double maxTicks = 10;
    private double tickSpacing;
    private double range;
    private double niceMin;
    private double niceMax;

    /**
     * Instantiates a new instance of the NiceScale class.
     *
     * @param min the minimum data point on the axis
     * @param max the maximum data point on the axis
     */
    public NiceScale(double min, double max) {
        this.minPoint = min;
        this.maxPoint = max;
        calculate();
    }

    /**
     * Calculate and update values for tick spacing and nice
     * minimum and maximum data points on the axis.
     */
    private void calculate() {
        this.range = niceNum(maxPoint - minPoint, false);
        this.tickSpacing = niceNum(range / (maxTicks - 1), true);
        this.niceMin =
            Math.floor(minPoint / tickSpacing) * tickSpacing;
        this.niceMax =
            Math.ceil(maxPoint / tickSpacing) * tickSpacing;
    }

    /**
     * Returns a "nice" number approximately equal to range Rounds
     * the number if round = true Takes the ceiling if round = false.
     *
     * @param range the data range
     * @param round whether to round the result
     * @return a "nice" number to be used for the data range
     */
    private double niceNum(double range, boolean round) {
        double exponent; /** exponent of range */
        double fraction; /** fractional part of range */
        double niceFraction; /** nice, rounded fraction */

        exponent = Math.floor(Math.log10(range));
        fraction = range / Math.pow(10, exponent);

        if (round) {
                if (fraction < 1.5)
                    niceFraction = 1;
                else if (fraction < 3)
                    niceFraction = 2;
                else if (fraction < 7)
                    niceFraction = 5;
                else
                    niceFraction = 10;
        } else {
                if (fraction <= 1)
                    niceFraction = 1;
                else if (fraction <= 2)
                    niceFraction = 2;
                else if (fraction <= 5)
                    niceFraction = 5;
                else
                    niceFraction = 10;
        }

        return niceFraction * Math.pow(10, exponent);
    }

    /**
     * Sets the minimum and maximum data points for the axis.
     *
     * @param minPoint the minimum data point on the axis
     * @param maxPoint the maximum data point on the axis
     */
    public void setMinMaxPoints(double minPoint, double maxPoint) {
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
        calculate();
    }

    /**
     * Sets maximum number of tick marks we're comfortable with
     *
     * @param maxTicks the maximum number of tick marks for the axis
     */
    public void setMaxTicks(double maxTicks) {
        this.maxTicks = maxTicks;
        calculate();
    }

    /**
     * Gets the tick spacing.
     *
     * @return the tick spacing
     */
    public double getTickSpacing() {
        return tickSpacing;
    }

    /**
     * Gets the "nice" minimum data point.
     *
     * @return the new minimum data point for the axis scale
     */
    public double getNiceMin() {
        return niceMin;
    }

    /**
     * Gets the "nice" maximum data point.
     *
     * @return the new maximum data point for the axis scale
     */
    public double getNiceMax() {
        return niceMax;
    }
}


}

