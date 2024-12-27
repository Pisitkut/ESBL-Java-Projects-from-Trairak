package pl4;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jacquelinedouglass
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.lang.Math.*;
import java.lang.*;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;

/**
 *
 * @author douglassj
 */
public class MakeLogo{


    //arrays
    public char[] aa= {'A','C','D','E','F','G','H','I','K','L','M','N','P','Q','R','S','T','V','W','Y'};				//AAs
    public ArrayList<String> groupings;                                         //= new ArrayList(Arrays.asList("A","C","D","E","F","G","H","I","K","L","M","N","P","Q","R","S","T","V","W","Y"));							//string of AAs
    public ArrayList<Character> groupingsLabel;                          // = new ArrayList(Arrays.asList('A','C','D','E','F','G','H','I','K','L','M','N','P','Q','R','S','T','V','W','Y'));					//label
    public ArrayList<Color> groupingsColors;
    public double[] aaBgFreq;
    public int groupType;
    public HashMap<Character, Color> color;			//HashMap linking character to grouping color
    public double[] groupBgFreq;
    public double[][] probMatrix;
    public double[][] Pvalues;

    //Other
    public double maxEntropy;
    public static double logbase = 2;
    public int modifiedPosition;
    public Color colorOfBackground;
    public Color colorOfAxes;
    public Color colorOfModified;
    public boolean charactersOn;
    public boolean showFavored;

    //for sequence logo
    public double[][] icmatrix;
    public double[] icsum;
    public JFrame resultsFrame2;
    public JPanel mainPanel;
    public JPanel probabilityPanel;
    public ChartPanel pvaluePanel;
    public double[][] outputArray;
    public double[] AApresent;




//     MakeLogo logo = new MakeLogo(seqAlign, numSeq, lengthSeq, groupings, groupingsLabel, colors, ppResidueType);
public MakeLogo(char[][] seq, int numSeq, int lengthSeq, ArrayList<String> group,ArrayList<Character> groupLabels,ArrayList<Color> groupColors, int ppResidueType, double[] aaFreq, int modifiedPos, Color colorBackground, Color colorAxes, Color colorModified, boolean charOn, double  posAlpha, double  AAAlpha, boolean showFavored){



      //Make center position ungrouped
      this.modifiedPosition= modifiedPos - 1;  //position number of modified AA........subtract 1 to shift to java array
      this.colorOfBackground = colorBackground;
      this.colorOfAxes = colorAxes;
      this.colorOfModified = colorModified;
      this.charactersOn = charOn;

    //initiate new HashMap linking labels to strings
        this.groupings = group;
        this.groupingsLabel = groupLabels;
        this.groupingsColors = groupColors;
        this.aaBgFreq = aaFreq;
        


  // Make Probability Matrix to hold observed probabilities
 probMatrix = new double[groupings.size()+aa.length][lengthSeq];	// (#groups + # individual AAs) x #positions
 //Make Probability Matrix to hold background probabilities - used if Pref's are calculated from the input sequences
 double[] probMatrixBgFromInput = new double[groupings.size() + aa.length];
   //initially set all background reference probabilities to zero in case all amino acids are not used in input sequences
   for (int m=0; m < groupings.size() + aa.length; m++){
       probMatrixBgFromInput[m] = 0.0;
   }

            for(int i = 0; i < lengthSeq; i++){                             //loop through positions

                //Center position is ungrouped
                if(i==modifiedPosition){
                    for(int j = 0; j < numSeq ; j++){                           //loop through sequences
                        for(int g=0; g< aa.length; g++){             //loop through individual AAs
                            if(seq[j][i] == aa[g]){
                            probMatrix[groupings.size()+g][i]+= 1.0/numSeq;		//increment pos i and AA g
                            probMatrixBgFromInput[groupings.size()+g] += 1.0/numSeq;		//increment AA g for background frequencies of modified position
                            break;
                        }
                        }
                     }
                }

                //Calculate group reference Ps
                //Non-center positions
        else{
                for(int j = 0; j < numSeq ; j++){                                                   //loop through sequences

                    //For groups
                    for(int g=0; g< groupings.size(); g++){                                     //loop through groups
                        for(int a = 0; a < groupings.get(g).length(); a++){             //loop through AAs in each group
                        if(seq[j][i] == groupings.get(g).charAt(a)){				//if AA = AA at pos i and seq j
                            probMatrix[g][i]+= 1.0/numSeq;		//increment pos i and group g
                            //aaProbabilities[g][i]+= 1.0/numSeq;		//increment pos i and AA g
                            if(aaBgFreq == null  && modifiedPosition >= 0  && modifiedPosition < lengthSeq){
                                probMatrixBgFromInput[g] += 100.0/(numSeq*(lengthSeq-1));       //Don't count modified position toward background frequency calculation
                            }
                            else if(aaBgFreq == null){
                                probMatrixBgFromInput[g] += 100.0/(numSeq*lengthSeq);           //No modified position.
                            }
                            else{
                            }
                            break;
                        }
                    }
                }

                    //Calculate individual AA reference Ps
                    //for individual AA Prefs - necessary to normalize modified AA Pref's
                    for(int g = 0; g < aa.length; g++){
                        if(seq[j][i] == aa[g]){
                            if(aaBgFreq == null  && modifiedPosition >= 0  && modifiedPosition < lengthSeq){
                                probMatrixBgFromInput[groupings.size()+ g] += 100.0/(numSeq*(lengthSeq-1));       //Don't count modified position toward background frequency calculation
                            }
                            else if(aaBgFreq == null){
                                probMatrixBgFromInput[groupings.size()+ g] += 100.0/(numSeq*lengthSeq);           //No modified position.
                            }
                            else{
                            }
                        }
                    }
               }
         }
    }
   
      
   
   
   //Correctly assign reference probabilities for groups and individual AAs
   groupBgFreq = new double [groupings.size() + aa.length];                           //initiate array to hold group frequencies
      //initially set all background reference probabilities to zero in case all amino acids are not used in input sequences
   for (int m=0; m < groupings.size() + aa.length; m++){
       groupBgFreq[m] = 0.0;
   }


   //If calculating background frequencies from peptide sequences
   if(aaBgFreq==null){
       groupBgFreq = probMatrixBgFromInput;

       double[] PKAbgFreq = new double[] {7.796726828, 1.445396146, 5.728051392, 7.911440808, 3.116396452, 7.460232487, 2.049556439, 5.016824717, 8.420006118, 8.469715509, 1.98837565, 3.927041909, 4.756806363, 4.309421842, 6.98608137, 6.125726522, 4.810339553, 6.768124809, 0.695931478, 2.21780361};
       double[] CK2bgFreq = new double[] {7.768620269, 0.656288156, 7.158119658, 12.08791209, 2.304639805, 7.142857143, 1.999389499, 3.357753358, 8.058608059, 6.440781441, 1.388888889, 3.571428571, 5.555555556, 4.700854701, 6.913919414, 8.180708181, 4.685592186, 5.601343101, 0.549450549, 1.877289377};
       double[] p38bgFreq = new double[] {8.852518861, 1.569724994, 5.779995133, 7.812119737, 3.236797274, 7.599172548, 2.178145534, 4.636164517, 8.23192991, 7.720856656, 1.715745924, 3.833049404, 6.820394257, 4.058165004, 6.132879046, 5.183743003, 4.477975176, 7.136772937, 0.730104648, 2.293745437};

       //To assign background frequencies manually:
       for(int n = 0; n < 20; n++){
       //groupBgFreq[n] = CK2bgFreq[n];
        //groupBgFreq[20+n] = CK2bgFreq[n];
        }
   }
   //if background frequencies are defined
 else{
                //create HashMap to link AAs to individual background frequencies
                  HashMap<Character,Double> aaBgFreqHM = new HashMap<Character,Double>();
                   for (int i = 0; i < aa.length; i++) {			//loop across AAs in group
                   aaBgFreqHM.put(aa[i], aaBgFreq[i]);				//link each AA to it's individual background frequency
                   //aaBgFreqHM.put(aa[i], groupBgFreq[groupings.size() + i]);				//link each AA to it's individual background frequency  /////////HELP
               }

                //Calculate background frequencies for groups
                for (int i = 0; i < groupings.size(); i++) {                                                            //Loop through grouping
                Double groupFreqSum = new Double(0);                                                     //sums bgFreq of all AAs in a given group
                for (int j = 0; j < groupings.get(i).length(); j++) {                                               //loop through AAs in a given group
                  groupFreqSum += (Double) aaBgFreqHM.get(groupings.get(i).charAt(j));          //Sum individual AA frequencies
                }
                groupBgFreq[i]= groupFreqSum;                                   //set value of group probability as sum of inidividual frequencies
            }

            for(int i = 0; i < aa.length; i++){
            //groupBgFreq[i] = aaBgFreq[i];  //add individual AA frequences for special modified position handling
            groupBgFreq[groupings.size() + i] = aaBgFreq[i];  //add individual AA frequences for special modified position handling
            }
 }

             //Assign colors to groups
            HashMap<Character,Double> aaBgFreqHM = new HashMap<Character,Double>();
            color = new HashMap<Character,Color>();
                        for (int i = 0; i < groupings.size(); i++) {
                            color.put(groupingsLabel.get(i), (Color) groupingsColors.get(i));
                        }
            color.put('X', Color.WHITE);



   //Correctly implement Information Content - calculate Pref's
                //double[][] probMatrixNorm = new double[groupings.size() + aa.length][lengthSeq];
                double[] aaBgProb = new double[groupings.size()+aa.length];

                //Only rescale if user inputs (correctly normalize modified position reference P's)
                if (ppResidueType==1){
                    double sumBgFreq = 0;
                    //array for whether AA (or group) is present at modified site
                    AApresent = new double[aa.length];         //no groupings at modified position
                    //AApresentForChiSquared = new double[aa.length];
                    for(int n=0; n<aa.length; n++){
                        AApresent[n]=0;  //set all values to zero initially
                    }

                                        for(int i = 0; i < lengthSeq; i++){     //loop through positions
                                           //Just modified position:
                                            if (i==modifiedPosition){
                                            for(int g=0; g< aa.length; g++){                        //loop through AAs
                                                    for(int j = 0; j < numSeq ; j++){                //loop through sequences
                                                        if(seq[j][i]==aa[g]){                              //if AA  = AA  at pos i and seq j
                                                            AApresent[g]=1;                             //Account for whether "modified AAs" are each present
                                                        }
                                                    }
                                                    AApresent[g] = AApresent[g]*groupBgFreq[(int) (groupings.size()+g)];                             //multiply "boolean" values by bgFreq's  --> THIS CHANGES AApresent from 0's/1's to  AA Freq's
                                                }

                                                //calculate sum of background frequencies for only modified AAs
                                                for (int  m =0 ; m< AApresent.length; m++) {
                                                    sumBgFreq += AApresent[m];
                                                }

                                               //Rescale reference probabilities for only the modified AAs
                                                for(int k = 0; k < aa.length; k++){      //loop through AAs
                                                    aaBgProb[groupings.size()+ k] = groupBgFreq[groupings.size() + k]/ sumBgFreq;    //rescale background frequency of modified AA's
                                                 }
                                            }


                                            //All other positions:
                                            else{
                                                     for(int k = 0; k < groupings.size(); k++){      //loop through groups
                                                         aaBgProb[k] = groupBgFreq[k]/ 100;            //convert frequencies to probabilities
                                                     }
                                                 }
                                            }
    }


                // IF modified residue scaling is NOT desired:
                else {
                    for(int i = 0; i < lengthSeq; i++){     //loop through positions
                                                for(int k = 0; k < groupings.size()+aa.length; k++){      //loop through AAs
                                                    aaBgProb[k] = groupBgFreq[k] / 100;                             //convert frequencies to probabilities
                                                 }
                    }
             }

//Calculate IC value for all groups/AAs and positions.  IC = Pref * log2(Pref) - P * log2(P)
          double[][] ICaa = new double[groupings.size()+aa.length][lengthSeq];


   //Calculate log(p(b)/p_ref(b))
            double[][] probMatrixNormLog = new double[groupings.size()+aa.length][lengthSeq];
            for(int i = 0; i < lengthSeq; i++){
                for(int k = 0; k <groupings.size()+aa.length; k++){
                    double val = probMatrix[k][i];
                    //show favored residues
                    if(showFavored){
                        probMatrixNormLog[k][i] = (val == 0) ? 0 : Math.log(val/aaBgProb[k])/Math.log(logbase);
                    }
                    //show disfavored residues
                    else{
                       probMatrixNormLog[k][i] = (val == 0) ? 0 : Math.log((1-val)/(0.999999999-aaBgProb[k]))/Math.log(logbase);
                    }
                    
                }
            }

  // p(b)*ln(p(b)/p_ref(b)) -> gives individual IC
   //sigma(p(b)*ln(p(b)/p_ref(b))) -> this gives the relative entropy or information content depending on value of bgFreqCalc
            boolean favoredICnotZero = false;                  //if all IC values = 0, don't create logo
            boolean disfavoredICnotZero = false;                  //if all IC values = 0, don't create logo
            for(int i = 0; i < lengthSeq; i++){
                for(int k = 0; k < groupings.size()+aa.length; k++){
                    //show favored residues
                    if(showFavored){
                        ICaa[k][i] = probMatrix[k][i]*probMatrixNormLog[k][i];
                        if(0 - ICaa[k][i] < 0) {favoredICnotZero = true;}          //if all IC values = 0, don't create logo
                    }
                    //show disfavored residues
                    else{
                        ICaa[k][i] = (1-probMatrix[k][i]) * probMatrixNormLog[k][i];
                        if(0 - ICaa[k][i] < 0) {disfavoredICnotZero = true;}          //if all IC values = 0, don't create logo
                    }
                }
            }


            //Count number of groups
            int numGroups = 0;
            for(int i = 0; i < groupings.size(); i++){
                if(groupings.get(i).length() > 0){
                     numGroups ++;
                }
            }

            //Run Chi-square calculations
        ChiSquared chiSquared = new ChiSquared(seq, modifiedPosition, lengthSeq, numSeq, groupings, groupingsLabel,  posAlpha, AAAlpha, AApresent);
        Pvalues = chiSquared.Pvalues;
        Boolean[][] allSignificant = chiSquared.allSignificant;
        //Check that atleast one amino acid is significant
        boolean noneSignificant = true;
       for(int i = 0; i< lengthSeq; i++){
            for(int j = 0; j< groupings.size()  ; j++){//Pvalues[0].length
    //check position significance
                if(allSignificant[i][j+1]== true){
                    noneSignificant = false;
                }
        }
    }



//Generate IC Logo with error messages
        if(noneSignificant){
            mainPanel = new JPanel();
             final JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "Error: No residues/groups are statistically significant at the specified alpha.", "ERROR", JOptionPane.WARNING_MESSAGE);
        }
        else if((showFavored && favoredICnotZero) || (!showFavored && disfavoredICnotZero))
        {
            mainPanel = MakeLogoPanel.plot(seq, resultsFrame2, ICaa, groupingsLabel, color, groupType, modifiedPosition,  colorOfBackground,  colorOfAxes, colorOfModified, charactersOn, allSignificant, true );
        }
        else if (showFavored){
            mainPanel = new JPanel();
             final JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "Error: All favored IC values equal zero.", "ERROR", JOptionPane.WARNING_MESSAGE);
        }
        else{
            mainPanel = new JPanel();
             final JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "Error: All disfavored IC values equal zero.", "ERROR", JOptionPane.WARNING_MESSAGE);
        }

 //Generate frequency logo
  probabilityPanel = MakeLogoPanelProb.plot(seq, resultsFrame2, probMatrix, groupingsLabel, color, groupType, modifiedPosition,  colorOfBackground,  colorOfAxes, colorOfModified, charactersOn);

  //Generate Chi-Squared Pvalue Plot
  pvaluePanel = Graph.getGraph(Pvalues, groupings, modifiedPosition, AAAlpha);


    
    }
}


