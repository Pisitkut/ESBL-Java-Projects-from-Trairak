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
import org.apache.commons.math.*;
import org.apache.commons.math.stat.inference.*;
import org.apache.commons.math.distribution.*;
//import org.apache.commons.math.stat.inference.TestUtils*;

/**
 *
 * @author jacquelinedouglass
 */
public class ChiSquared{
    public double[][] Pvalues;
     Boolean[][] allSignificant;

    public ChiSquared(char[][] seq, int modifiedPosition, int lengthSeq, int numSeq, ArrayList<String> groupings, ArrayList<Character> groupingsLabel, double posAlpha, double AAAlpha, double[] AApresent) {

        //Need  to distinguish which indices have actual groups
        Boolean[] groupPresent = new Boolean[groupings.size()];
        allSignificant = new Boolean[lengthSeq][groupings.size()+1];
        Pvalues = new double[lengthSeq][groupings.size()+1];//groupings.size()

        //Regroup to get rid of blank groups  ---  Cannot have 0 frequency groups for x^2 test
        ArrayList<String> groupingsShort = new ArrayList<String>();
        for(int i = 0; i < groupings.size(); i++){
            if(groupings.get(i).length() > 0){       //groupings.get(i).compareTo((String) "")
                groupPresent[i]= true;
                groupingsShort.add(groupings.get(i));
            }
            else{
                groupPresent[i] = false;
             }
        }


        //Set AAs at modified position to zero - ignore modified position
        if(modifiedPosition >= 0  && modifiedPosition < lengthSeq){
             for(int j = 0; j < numSeq ; j++){
                // for(int i = 0; i < AApresent.length; i++){
                     seq[j][modifiedPosition] = 0;       //change AA present at modifiedPosition to 0 if  ..... if(AApresent[i]==0)
                // }
            }
        }

        //Generate observed matrix: AA/groups x position
        int[][] observedMatrix = new int[lengthSeq][groupingsShort.size()];	//  #positions x (#groups + # individual AAs)
        for(int i = 0; i < lengthSeq; i++){                             //loop through positions
            for(int j = 0; j < numSeq ; j++){
                    for(int g=0; g< groupingsShort.size(); g++){                                     //loop through groups
                        for(int a = 0; a < groupingsShort.get(g).length(); a++){             //loop through AAs in each group
                        if(seq[j][i] == groupingsShort.get(g).charAt(a)){				//if AA = AA at pos i and seq j
                            observedMatrix[i][g]+= 1;		//increment pos i and group g
                        }
                    }
                }
    }
}

         ///////////////Position Chi Squared Testing////////////////////////////////////////////////////////Position Chi Squared Testing//////////////////////////////////////////

        //Observed must be a long
        //Create this position vs other positions matrix
        //Sum across positions.  Counts total number of times a given group is observed
        long[][] observedMatrixPos = new long[lengthSeq][2*groupingsShort.size()];	// #positions x (this position vs other positions) x (#groups + # individual AAs)
        int[] sumVector = new int[groupingsShort.size()];	// (#groups + # individual AAs) x #positions x (this position vs other positions)
        for(int j = 0; j < groupingsShort.size(); j++){
            int groupingCount = 0;
            for(int i = 0; i < lengthSeq; i++){                             //loop through positions
                observedMatrixPos[i][j] = (long) observedMatrix[i][j];          //frequency at position i
                groupingCount += observedMatrix[i][j];          //sum number of observations for a given group across all positions
            }
            for(int i = 0; i < lengthSeq; i++){                             //loop through positions
                observedMatrixPos[i][groupingsShort.size()+j] = (long) (groupingCount - observedMatrixPos[i][j]);        //total frequency of all other positions
                sumVector[j] = groupingCount;                                                                        //total frequency of AA at ALL positions
            }
        }

        //Sum column values for 20x2 tables
        int thisPosSum = numSeq;
        int otherPosSum;
        int allSum;
//Ignore modified position if applicable
         if(modifiedPosition >= 0  && modifiedPosition < lengthSeq){
             otherPosSum = numSeq *( lengthSeq - 2);
             allSum = numSeq * (lengthSeq - 1);
         }
        else{
             otherPosSum = numSeq *( lengthSeq - 1);
             allSum = numSeq * (lengthSeq);
        }


        //Expected Matrix
        double[][] expectedMatrixPos = new double[lengthSeq][2*groupingsShort.size()];	// #positions x (#groups + # individual AAs)
        for(int i = 0; i < lengthSeq; i++){                             //loop through positions
        for(int j = 0; j < groupingsShort.size(); j++){
            expectedMatrixPos[i][j] = (double) (thisPosSum*sumVector[j])/allSum;
            expectedMatrixPos[i][groupingsShort.size()+j] = (double) (otherPosSum*sumVector[j])/allSum;
            }
        }

        //Perform chi squared test
        double[] posPValues = new double[lengthSeq];
        ChiSquareTestImpl posChiTest = new ChiSquareTestImpl();
        ChiSquaredDistributionImpl posChiDist = new ChiSquaredDistributionImpl(groupingsShort.size() - 1); //set dof as #groups - 1
        for(int i = 0; i < lengthSeq; i++){
            double Xstatistic;
                    try
                    {
                Xstatistic =  posChiTest.chiSquare(expectedMatrixPos[i], observedMatrixPos[i]);  //expected, observed
            }
                    catch (java.lang.IllegalArgumentException e)
                    {
                        Xstatistic = 0.0;
                    }
            double cumPr;
            try
             {
                cumPr =  posChiDist.cumulativeProbability(Xstatistic);
                Pvalues[i][0] = 1- cumPr;
            }
            catch ( MathException e )
            {
                 Pvalues[i][0] = 1.0;
            }

        }
        ///////////////Position Chi Squared Testing////////////////////////////////////////////////////////Position Chi Squared Testing//////////////////////////////////////////


       ///////////////AA Chi Squared Testing////////////////////////////////////////////////////////AA Chi Squared Testing//////////////////////////////////////////

        //Observed must be a long
        //Create this AA vs other AA matrix
        //Sum across AAs
        long[][][] observedMatrixAA = new long[lengthSeq][groupingsShort.size()][4];	// #positions x # groups x 4 values (one 2x2) per group
        for(int j = 0; j < groupingsShort.size(); j++){                 //loop through groups
            for(int i = 0; i < lengthSeq; i++){                             //loop through positions
                observedMatrixAA[i][j][0] = (long) observedMatrix[i][j];                                                            //First value: frequency of AA at position i
                observedMatrixAA[i][j][1] = (long) (numSeq -  observedMatrix[i][j]);                                        //Second value: frequency of all other AAs at position i
                observedMatrixAA[i][j][2] = (long) observedMatrixPos[i][groupingsShort.size()+j];                   //Third value: frequency of AA at all other positions
                if(modifiedPosition >= 0  && modifiedPosition < lengthSeq){
                     observedMatrixAA[i][j][3] = (long) (numSeq * (lengthSeq-2) - observedMatrixAA[i][j][2]  );      //Fourth value: frequency of all other AAs at all other positions - with modified Pos
                }
                else{
                    observedMatrixAA[i][j][3] = (long) (numSeq * (lengthSeq-1) - observedMatrixAA[i][j][2]  );      //Fourth value: frequency of all other AAs at all other positions - without modified Pos
                }
            }
        }

              //Expected Matrix
        double[][][] expectedMatrixAA = new double[lengthSeq][groupingsShort.size()][4];	// #positions x (#groups + # individual AAs)
        for(int i = 0; i < lengthSeq; i++){                             //loop through positions
        for(int j = 0; j < groupingsShort.size(); j++){
            expectedMatrixAA[i][j][0] = (double) (thisPosSum*sumVector[j])/allSum;                                //First value: frequency of AA at position i
            expectedMatrixAA[i][j][1] = (double) (thisPosSum*(allSum - sumVector[j]))/allSum;                   //Second value: frequency of all other AAs at position i
            expectedMatrixAA[i][j][2] = (double) (otherPosSum*sumVector[j])/allSum;                             //Third value: frequency of AA at all other positions
            expectedMatrixAA[i][j][3] = (double) (otherPosSum*(allSum - sumVector[j]))/allSum;         //Fourth value: frequency of all other AAs at all other positions - with modified Pos
            }
        }

        //Perform chi squared test
        double[][] AAPValues = new double[lengthSeq][groupingsShort.size()];
        ChiSquareTestImpl AAChiTest = new ChiSquareTestImpl();
        ChiSquaredDistributionImpl AAChiDist = new ChiSquaredDistributionImpl(1); //set dof = 1

         for(int i = 0; i < lengthSeq; i++){
              int missingGroup = 0;
        for(int j = 0; j < 20 ; j++){      //groupings.size()
           
          if(groupPresent[j]){
                    double Xstatistic;
                    try{
                        Xstatistic =  AAChiTest.chiSquare(expectedMatrixAA[i][j - missingGroup],observedMatrixAA[i][j - missingGroup]);
                         }
                     catch (java.lang.IllegalArgumentException e)
                    {
                        Xstatistic = 0.0;
                    }
                      //expected, observed
                    double cumPr;
                try
                {
                    cumPr =  AAChiDist.cumulativeProbability(Xstatistic);
                    Pvalues[i][j+1] = 1- cumPr;
                }
                catch ( MathException e )
                {
                    Pvalues[i][j+1] = 1.0;
                }
        }
 else{
                         missingGroup++;
                         Pvalues[i][j+1] = 1.0;
 }
        }
    }
///////////////AA Chi Squared Testing////////////////////////////////////////////////////////AA Chi Squared Testing//////////////////////////////////////////



////////Check Significance of positional and AA p values///////////////////////Check Significance of positional and AA p values/////////////////////
for(int i = 0; i< lengthSeq; i++){
    //check position significance
    if(Pvalues[i][0] < posAlpha){
        allSignificant[i][0] = true;
    }
    else{
        allSignificant[i][0] = false;
    }
    //check AA significance
    for(int j = 0; j< groupings.size()  ; j++){//Pvalues[0].length
        if(Pvalues[i][j+1] < AAAlpha ){ // || Pvalues[i][j] ==null
            allSignificant[i][j+1]= true;
        }
        else{
            allSignificant[i][j+1]= false;
        }
    }
}
        
    }
}


