/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.nih.nhlbi.abdesigner;

/**
 *
 * @author pisitkut
 */
public class KyteDoolittle {

    public static double getHydropathy(String eachSpan, int spanSetting) {
        double sumHydropathyIndex = 0;
        double averageHydropathy = 0;
        for (int i = 0; i < spanSetting; i++) {
            char eachChar = eachSpan.charAt(i);
            double hydropathyIndex = 0;
            switch (eachChar) {
                case 'A': hydropathyIndex = 1.8; break;
                case 'R': hydropathyIndex = -4.5; break;
                case 'N': hydropathyIndex = -3.5; break;
                case 'D': hydropathyIndex = -3.5; break;
                case 'C': hydropathyIndex = 2.5; break;
                case 'Q': hydropathyIndex = -3.5; break;
                case 'E': hydropathyIndex = -3.5; break;
                case 'G': hydropathyIndex = -0.4; break;
                case 'H': hydropathyIndex = -3.2; break;
                case 'I': hydropathyIndex = 4.5; break;
                case 'L': hydropathyIndex = 3.8; break;
                case 'K': hydropathyIndex = -3.9; break;
                case 'M': hydropathyIndex = 1.9; break;
                case 'F': hydropathyIndex = 2.8; break;
                case 'P': hydropathyIndex = -1.6; break;
                case 'S': hydropathyIndex = -0.8; break;
                case 'T': hydropathyIndex = -0.7; break;
                case 'W': hydropathyIndex = -0.9; break;
                case 'Y': hydropathyIndex = -1.3; break;
                case 'V': hydropathyIndex = 4.2; break;
                case 'X': hydropathyIndex = -0.5; break;
            }
            sumHydropathyIndex = sumHydropathyIndex + hydropathyIndex;
        }
        averageHydropathy = Math.round((sumHydropathyIndex/spanSetting)*100)/100.0;
        return averageHydropathy;
    }

    //Get array of Kyte-Doolittle
    public static double[] getArrayKyteDoolittle(String inputSequence, int spanSetting, int inputSequenceLength, int numberOfSpan) {
        double[] arrayKyteDoolittle = new double[inputSequenceLength];
        int index = 0;
        int startSpan = 0;
        int endSpan = 0;
        //preSpanResidue
        for (int i = 0; i < ((spanSetting - 1) / 2); i++) {
            arrayKyteDoolittle[index++] = Double.NaN;
        }
        //getHydropathy
        for (int i = 0; i < numberOfSpan; i++) {
            endSpan = startSpan + spanSetting;
            double hydropathy = getHydropathy(inputSequence.substring(startSpan, endSpan), spanSetting);
            arrayKyteDoolittle[index++] = hydropathy;
            startSpan++;
        }
        //postSpanResidue
        for (int i = 0; i < (spanSetting / 2); i++) {
            arrayKyteDoolittle[index++] = Double.NaN;
        }
        return arrayKyteDoolittle;
    }

}
