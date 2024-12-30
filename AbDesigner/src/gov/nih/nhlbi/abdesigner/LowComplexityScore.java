/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.nih.nhlbi.abdesigner;

/**
 *
 * @author pisitkut
 */
public class LowComplexityScore {

    //Get low-complexity score
    public static double[] getLowComplexityScore(char[] segmaskerChar, int spanSetting, int inputSequenceLength, int numberOfSpan) {
        double[] lowComplexityScore = new double[inputSequenceLength];
        int index = 0;
        int startSpan = 0;
        //preSpanResidue
        for (int i = 1; i < ((spanSetting + 1)/2); i++) {
            lowComplexityScore[index++] = 0;
        }
        //getLowComplexityScore
        for (int i = 0; i < numberOfSpan; i++) {
            double sumLowComplexity = 0;
            for (int j = startSpan; j < (startSpan + spanSetting); j++) {
                if (Character.isLowerCase(segmaskerChar[j]))
                    sumLowComplexity++;
            }
            lowComplexityScore[index++] = 1 - (sumLowComplexity / spanSetting);
            startSpan++;
        }
        //postSpanResidue
        if (spanSetting % 2 == 1) {
            for (int i = 1; i < ((spanSetting + 1)/2); i++) {
                lowComplexityScore[index++] = 0;
            }
        } else {
            for (int i = 0; i < ((spanSetting + 1)/2); i++) {
                lowComplexityScore[index++] = 0;
            }
        }
        return lowComplexityScore;
    }

}
