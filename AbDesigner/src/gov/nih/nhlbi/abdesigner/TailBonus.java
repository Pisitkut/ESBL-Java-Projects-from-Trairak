/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.nih.nhlbi.abdesigner;

/**
 *
 * @author pisitkut
 */
public class TailBonus {

    public static double[] getTailBonus(String[] arrayRegionSite, int arrayTailLoop, double tailBonus, int spanSetting, int inputSequenceLength, int numberOfSpan) {
        double[] tailBonusArray = new double[inputSequenceLength];
        int index = 0;
        int startSpan = 0;
        //preSpanResidue
        for (int i = 0; i < ((spanSetting - 1) / 2); i++) {
            tailBonusArray[index++] = Double.NaN;
        }
        //Get tail bonus
        for (int i = 0; i < numberOfSpan; i++) {
            double sumTailBonus = 0;
            for (int j = startSpan; j < (startSpan + spanSetting); j++) {
                if ((arrayRegionSite[j + (arrayTailLoop * inputSequenceLength)].equals("NH2-tail")) || (arrayRegionSite[j + (arrayTailLoop * inputSequenceLength)].equals("COOH-tail")))
                    sumTailBonus += tailBonus;
                else
                    sumTailBonus++;
            }
            tailBonusArray[index++] = sumTailBonus / spanSetting;
            startSpan++;
        }
        //postSpanResidue
        for (int i = 0; i < (spanSetting / 2); i++) {
            tailBonusArray[index++] = Double.NaN;
        }
        return tailBonusArray;
    }

}
