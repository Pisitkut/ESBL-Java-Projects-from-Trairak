/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.nih.nhlbi.abdesigner;

import java.util.ArrayList;

/**
 *
 * @author pisitkut
 */
public class ChouFasman {

    public static String getAlpha(char eachResidue) {
        String a = "";
        switch (eachResidue) {
            case 'A': a = "H"; break;
            case 'R': a = "i"; break;
            case 'N': a = "b"; break;
            case 'D': a = "I"; break;
            case 'C': a = "i"; break;
            case 'E': a = "H"; break;
            case 'Q': a = "h"; break;
            case 'G': a = "B"; break;
            case 'H': a = "I"; break;
            case 'I': a = "h"; break;
            case 'L': a = "H"; break;
            case 'K': a = "h"; break;
            case 'M': a = "H"; break;
            case 'F': a = "h"; break;
            case 'P': a = "B"; break;
            case 'S': a = "i"; break;
            case 'T': a = "i"; break;
            case 'W': a = "h"; break;
            case 'Y': a = "b"; break;
            case 'V': a = "h"; break;
            case 'X': a = ""; break;
        }
        return a;
    }

    public static String getBeta(char eachResidue) {
        String b = "";
        switch (eachResidue) {
            case 'A': b = "i"; break;
            case 'R': b = "i"; break;
            case 'N': b = "i"; break;
            case 'D': b = "B"; break;
            case 'C': b = "h"; break;
            case 'E': b = "B"; break;
            case 'Q': b = "h"; break;
            case 'G': b = "b"; break;
            case 'H': b = "i"; break;
            case 'I': b = "H"; break;
            case 'L': b = "h"; break;
            case 'K': b = "b"; break;
            case 'M': b = "h"; break;
            case 'F': b = "h"; break;
            case 'P': b = "B"; break;
            case 'S': b = "b"; break;
            case 'T': b = "h"; break;
            case 'W': b = "h"; break;
            case 'Y': b = "H"; break;
            case 'V': b = "H"; break;
            case 'X': b = ""; break;
        }
        return b;
    }

    public static double getPa(String eachSpan, int spanSetting) {
        double sumPa = 0;
        double averagePa = 0;
        for (int i = 0; i < spanSetting; i++) {
            char eachChar = eachSpan.charAt(i);
            double Pa = 0;
            switch (eachChar) {
                case 'A': Pa = 142; break;
                case 'R': Pa = 98; break;
                case 'N': Pa = 67; break;
                case 'D': Pa = 101; break;
                case 'C': Pa = 70; break;
                case 'E': Pa = 151; break;
                case 'Q': Pa = 111; break;
                case 'G': Pa = 57; break;
                case 'H': Pa = 100; break;
                case 'I': Pa = 108; break;
                case 'L': Pa = 121; break;
                case 'K': Pa = 116; break;
                case 'M': Pa = 145; break;
                case 'F': Pa = 113; break;
                case 'P': Pa = 57; break;
                case 'S': Pa = 77; break;
                case 'T': Pa = 83; break;
                case 'W': Pa = 108; break;
                case 'Y': Pa = 69; break;
                case 'V': Pa = 106; break;
                case 'X': Pa = 100; break;
            }
            sumPa = sumPa + Pa;
        }
        averagePa = Math.round((sumPa/spanSetting)*100)/100.0;
        return averagePa;
    }

    public static double getPb(String eachSpan, int spanSetting) {
        double sumPb = 0;
        double averagePb = 0;
        for (int i = 0; i < spanSetting; i++) {
            char eachChar = eachSpan.charAt(i);
            double Pb = 0;
            switch (eachChar) {
                case 'A': Pb = 83; break;
                case 'R': Pb = 93; break;
                case 'N': Pb = 89; break;
                case 'D': Pb = 54; break;
                case 'C': Pb = 119; break;
                case 'E': Pb = 37; break;
                case 'Q': Pb = 110; break;
                case 'G': Pb = 75; break;
                case 'H': Pb = 87; break;
                case 'I': Pb = 160; break;
                case 'L': Pb = 130; break;
                case 'K': Pb = 74; break;
                case 'M': Pb = 105; break;
                case 'F': Pb = 138; break;
                case 'P': Pb = 55; break;
                case 'S': Pb = 75; break;
                case 'T': Pb = 119; break;
                case 'W': Pb = 137; break;
                case 'Y': Pb = 147; break;
                case 'V': Pb = 170; break;
                case 'X': Pb = 102.9; break;
            }
            sumPb = sumPb + Pb;
        }
        averagePb = Math.round((sumPb/spanSetting)*100)/100.0;
        return averagePb;
    }

    public static double getPt(String eachSpan, int spanSetting) {
        double sumPt = 0;
        double averagePt = 0;
        for (int i = 0; i < spanSetting; i++) {
            char eachChar = eachSpan.charAt(i);
            double Pt = 0;
            switch (eachChar) {
                case 'A': Pt = 66; break;
                case 'R': Pt = 95; break;
                case 'N': Pt = 156; break;
                case 'D': Pt = 146; break;
                case 'C': Pt = 119; break;
                case 'E': Pt = 74; break;
                case 'Q': Pt = 98; break;
                case 'G': Pt = 156; break;
                case 'H': Pt = 95; break;
                case 'I': Pt = 47; break;
                case 'L': Pt = 59; break;
                case 'K': Pt = 101; break;
                case 'M': Pt = 60; break;
                case 'F': Pt = 60; break;
                case 'P': Pt = 152; break;
                case 'S': Pt = 143; break;
                case 'T': Pt = 96; break;
                case 'W': Pt = 96; break;
                case 'Y': Pt = 114; break;
                case 'V': Pt = 50; break;
                case 'X': Pt = 99.2; break;
            }
            sumPt = sumPt + Pt;
        }
        averagePt = Math.round((sumPt/spanSetting)*100)/100.0;
        return averagePt;
    }

    public static double getBendProbability(String eachTetrapeptide) {
        double bendProbability = 0;
        char firstChar = eachTetrapeptide.charAt(0);
        char secondChar = eachTetrapeptide.charAt(1);
        char thirdChar = eachTetrapeptide.charAt(2);
        char fourthChar = eachTetrapeptide.charAt(3);
        double Fi0 = 0;
        double Fi1 = 0;
        double Fi2 = 0;
        double Fi3 = 0;
        switch (firstChar) {
            case 'A': Fi0 = 0.06; break;
            case 'R': Fi0 = 0.07; break;
            case 'N': Fi0 = 0.161; break;
            case 'D': Fi0 = 0.147; break;
            case 'C': Fi0 = 0.149; break;
            case 'E': Fi0 = 0.056; break;
            case 'Q': Fi0 = 0.074; break;
            case 'G': Fi0 = 0.102; break;
            case 'H': Fi0 = 0.14; break;
            case 'I': Fi0 = 0.043; break;
            case 'L': Fi0 = 0.061; break;
            case 'K': Fi0 = 0.055; break;
            case 'M': Fi0 = 0.068; break;
            case 'F': Fi0 = 0.059; break;
            case 'P': Fi0 = 0.102; break;
            case 'S': Fi0 = 0.12; break;
            case 'T': Fi0 = 0.086; break;
            case 'W': Fi0 = 0.077; break;
            case 'Y': Fi0 = 0.082; break;
            case 'V': Fi0 = 0.062; break;
            case 'X': Fi0 = 0.089; break;
        }
        switch (secondChar) {
            case 'A': Fi1 = 0.076; break;
            case 'R': Fi1 = 0.106; break;
            case 'N': Fi1 = 0.083; break;
            case 'D': Fi1 = 0.11; break;
            case 'C': Fi1 = 0.053; break;
            case 'E': Fi1 = 0.06; break;
            case 'Q': Fi1 = 0.098; break;
            case 'G': Fi1 = 0.085; break;
            case 'H': Fi1 = 0.047; break;
            case 'I': Fi1 = 0.034; break;
            case 'L': Fi1 = 0.025; break;
            case 'K': Fi1 = 0.115; break;
            case 'M': Fi1 = 0.082; break;
            case 'F': Fi1 = 0.041; break;
            case 'P': Fi1 = 0.301; break;
            case 'S': Fi1 = 0.139; break;
            case 'T': Fi1 = 0.108; break;
            case 'W': Fi1 = 0.013; break;
            case 'Y': Fi1 = 0.065; break;
            case 'V': Fi1 = 0.048; break;
            case 'X': Fi1 = 0.084; break;
        }
        switch (thirdChar) {
            case 'A': Fi2 = 0.035; break;
            case 'R': Fi2 = 0.099; break;
            case 'N': Fi2 = 0.191; break;
            case 'D': Fi2 = 0.179; break;
            case 'C': Fi2 = 0.117; break;
            case 'E': Fi2 = 0.077; break;
            case 'Q': Fi2 = 0.037; break;
            case 'G': Fi2 = 0.19; break;
            case 'H': Fi2 = 0.093; break;
            case 'I': Fi2 = 0.013; break;
            case 'L': Fi2 = 0.036; break;
            case 'K': Fi2 = 0.072; break;
            case 'M': Fi2 = 0.014; break;
            case 'F': Fi2 = 0.065; break;
            case 'P': Fi2 = 0.034; break;
            case 'S': Fi2 = 0.125; break;
            case 'T': Fi2 = 0.065; break;
            case 'W': Fi2 = 0.064; break;
            case 'Y': Fi2 = 0.114; break;
            case 'V': Fi2 = 0.028; break;
            case 'X': Fi2 = 0.082; break;
        }
        switch (fourthChar) {
            case 'A': Fi3 = 0.058; break;
            case 'R': Fi3 = 0.085; break;
            case 'N': Fi3 = 0.091; break;
            case 'D': Fi3 = 0.081; break;
            case 'C': Fi3 = 0.128; break;
            case 'E': Fi3 = 0.064; break;
            case 'Q': Fi3 = 0.098; break;
            case 'G': Fi3 = 0.152; break;
            case 'H': Fi3 = 0.054; break;
            case 'I': Fi3 = 0.056; break;
            case 'L': Fi3 = 0.07; break;
            case 'K': Fi3 = 0.095; break;
            case 'M': Fi3 = 0.055; break;
            case 'F': Fi3 = 0.065; break;
            case 'P': Fi3 = 0.068; break;
            case 'S': Fi3 = 0.106; break;
            case 'T': Fi3 = 0.079; break;
            case 'W': Fi3 = 0.167; break;
            case 'Y': Fi3 = 0.125; break;
            case 'V': Fi3 = 0.053; break;
            case 'X': Fi3 = 0.088; break;
        }
        bendProbability = Fi0 * Fi1 * Fi2 * Fi3;
        return bendProbability;
    }

    //Get array of Modified Chou-Fasman
    public static Double[] getArrayModifiedChouFasman(String inputSequence, int spanSetting, int inputSequenceLength, int numberOfSpan) {
        //Get tetrapeptide CF
        ArrayList<Double> arrayPa = new ArrayList<Double>();
        ArrayList<Double> arrayPb = new ArrayList<Double>();
        ArrayList<Double> arrayPt = new ArrayList<Double>();
        ArrayList<Double> arrayBendProbability = new ArrayList<Double>();
        ArrayList<Double> arrayPrediction = new ArrayList<Double>();
        int startSpan = 0;
        int endSpan = 0;
        double bendProbability = 0;
        //preSpanResidue
        for (int i = 1; i < 3; i++) {
            arrayPa.add(Double.NaN);
            arrayPb.add(Double.NaN);
            arrayPt.add(Double.NaN);
            bendProbability = getBendProbability(inputSequence.substring(i - 1, i + 3));
            arrayBendProbability.add(bendProbability);
            arrayPrediction.add(0.0);
        }
        for (int i = 0; i < (inputSequenceLength - 5); i++) {
            endSpan = startSpan + 4;
            String eachSpan1 = inputSequence.substring(startSpan, endSpan);
            String eachSpan2 = inputSequence.substring(startSpan + 1, endSpan + 1);
            String eachSpan3 = inputSequence.substring(startSpan + 2, endSpan + 2);
            double average4Pa = (getPa(eachSpan1, 4) + getPa(eachSpan2, 4))/2;
            double average4Pb = (getPb(eachSpan1, 4) + getPb(eachSpan2, 4))/2;
            double average4Pt = (getPt(eachSpan1, 4) + getPt(eachSpan2, 4))/2;
            bendProbability = getBendProbability(eachSpan3);
            double prediction = 0;
            if ((average4Pa > 100.0) && (average4Pa > average4Pb) && (average4Pa > average4Pt)) {
                prediction = 1; //"alpha helix";
            }
            if ((average4Pb > 100.0) && (average4Pb > average4Pa) && (average4Pb > average4Pt)) {
                prediction = 2; //"beta sheet";
            }
            if (((average4Pt > 100.0) || (bendProbability > 0.000075)) && (average4Pt > average4Pa) && (average4Pt > average4Pb)) {
                if ((average4Pt > 100.0) && (bendProbability > 0.000075)) {
                    prediction = 3; //"strong beta turn";
                } else {
                    prediction = 4; //"weak beta turn";
                }
            }
            arrayPa.add(average4Pa);
            arrayPb.add(average4Pb);
            arrayPt.add(average4Pt);
            arrayBendProbability.add(bendProbability);
            arrayPrediction.add(prediction);
            startSpan++;
        }
        //postSpanResidue
        for (int i = 1; i < 4; i++) {
            arrayPa.add(Double.NaN);
            arrayPb.add(Double.NaN);
            arrayPt.add(Double.NaN);
            arrayBendProbability.add(Double.NaN);
            arrayPrediction.add(0.0);
        }
        //Get average CF from scratch
        ArrayList<Double> arrayAveragePa = new ArrayList<Double>();
        ArrayList<Double> arrayAveragePb = new ArrayList<Double>();
        ArrayList<Double> arrayAveragePt = new ArrayList<Double>();
        ArrayList<Double> arrayAverageBendProbability = new ArrayList<Double>();
        startSpan = 0;
        endSpan = 0;
        //preSpanResidue
        for (int i = 0; i < ((spanSetting - 1) / 2); i++) {
            arrayAveragePa.add(Double.NaN);
            arrayAveragePb.add(Double.NaN);
            arrayAveragePt.add(Double.NaN);
            arrayAverageBendProbability.add(Double.NaN);
        }
        //SpanResidue
        for (int i = 0; i < numberOfSpan; i++) {
            endSpan = startSpan + spanSetting;
            String eachSpan = inputSequence.substring(startSpan, endSpan);
            double averagePa = getPa(eachSpan, spanSetting);
            double averagePb = getPb(eachSpan, spanSetting);
            double averagePt = getPt(eachSpan, spanSetting);
            arrayAveragePa.add(averagePa);
            arrayAveragePb.add(averagePb);
            arrayAveragePt.add(averagePt);
            double sumBendProbability = 0;
            if (i < (numberOfSpan - 3)) {
                for (int j = startSpan; j < (startSpan + spanSetting); j++) {
                    sumBendProbability = sumBendProbability + arrayBendProbability.get(j);
                }
                arrayAverageBendProbability.add(sumBendProbability / spanSetting);
            } else {
                for (int j = startSpan; j < (startSpan + spanSetting - (i - numberOfSpan + 4)); j++) {
                    sumBendProbability = sumBendProbability + arrayBendProbability.get(j);
                }
                arrayAverageBendProbability.add(sumBendProbability / (spanSetting - (i - numberOfSpan + 4)));
            }
            startSpan++;
        }
        //postSpanResidue
        for (int i = 0; i < (spanSetting / 2); i++) {
            arrayAveragePa.add(Double.NaN);
            arrayAveragePb.add(Double.NaN);
            arrayAveragePt.add(Double.NaN);
            arrayAverageBendProbability.add(Double.NaN);
        }
        //Add all ArrayList<Double> then convert to double[]
        ArrayList<Double> arrayModifiedChouFasman = new ArrayList<Double>();
        arrayModifiedChouFasman.addAll(arrayPa);
        arrayModifiedChouFasman.addAll(arrayPb);
        arrayModifiedChouFasman.addAll(arrayPt);
        arrayModifiedChouFasman.addAll(arrayBendProbability);
        arrayModifiedChouFasman.addAll(arrayPrediction);
        arrayModifiedChouFasman.addAll(arrayAveragePa);
        arrayModifiedChouFasman.addAll(arrayAveragePb);
        arrayModifiedChouFasman.addAll(arrayAveragePt);
        arrayModifiedChouFasman.addAll(arrayAverageBendProbability);
        Double[] arrayCF = arrayModifiedChouFasman.toArray(new Double[0]);
        return arrayCF;
    }

}
