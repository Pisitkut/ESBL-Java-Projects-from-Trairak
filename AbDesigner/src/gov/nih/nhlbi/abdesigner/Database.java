/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.nih.nhlbi.abdesigner;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Pisitkun
 */
public class Database {

    //Get accessionLocusMap
    public static HashMap<String, String> getAccessionLocusMap() throws IOException, ClassNotFoundException {
        HashMap<String, String> accessionLocusMap = null;
        ObjectInputStream inputStream = null;
        try {
            inputStream =
                    new ObjectInputStream(
                    new BufferedInputStream(
                    new FileInputStream("database/human_mouse_rat_accessionLocusMap.ab")));
            accessionLocusMap = (HashMap<String, String>) inputStream.readObject();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return accessionLocusMap;
    }

    //Get geneLocusMap
    public static HashMap<String, String> getGeneLocusMap() throws IOException, ClassNotFoundException {
        HashMap<String, String> geneLocusMap = null;
        ObjectInputStream inputStream = null;
        try {
            inputStream =
                    new ObjectInputStream(
                    new BufferedInputStream(
                    new FileInputStream("database/human_mouse_rat_geneLocusMap.ab")));
            geneLocusMap = (HashMap<String, String>) inputStream.readObject();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return geneLocusMap;
    }

    //Get locusFeaturesMap
    public static HashMap<String, String> getLocusFeaturesMap() throws IOException, ClassNotFoundException {
        HashMap<String, String> locusFeaturesMap = null;
        ObjectInputStream inputStream = null;
        try {
            inputStream =
                    new ObjectInputStream(
                    new BufferedInputStream(
                    new FileInputStream("database/human_mouse_rat_locusFeaturesMap.ab")));
            locusFeaturesMap = (HashMap<String, String>) inputStream.readObject();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return locusFeaturesMap;
    }

    //Get locusSequenceMap
    public static HashMap<String, String> getLocusSequenceMap() throws IOException, ClassNotFoundException {
        HashMap<String, String> locusSequenceMap = null;
        ObjectInputStream inputStream = null;
        try {
            inputStream =
                    new ObjectInputStream(
                    new BufferedInputStream(
                    new FileInputStream("database/human_mouse_rat_locusSequenceMap.ab")));
            locusSequenceMap = (HashMap<String, String>) inputStream.readObject();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return locusSequenceMap;
    }

    //Get locusSegmaskerMap
    public static HashMap<String, String> getLocusSegmaskerMap() throws IOException, ClassNotFoundException {
        HashMap<String, String> locusSegmaskerMap = null;
        ObjectInputStream inputStream = null;
        try {
            inputStream =
                    new ObjectInputStream(
                    new BufferedInputStream(
                    new FileInputStream("database/human_mouse_rat_locusSegmaskerMap.ab")));
            locusSegmaskerMap = (HashMap<String, String>) inputStream.readObject();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return locusSegmaskerMap;
    }

    //Get locusSequence2DArrayList
    public static ArrayList<ArrayList<String>> getLocusSequence2DArrayList(String species) throws IOException, ClassNotFoundException {
        ArrayList<ArrayList<String>> locusSequence2DArrayList = null;
        ObjectInputStream inputStream = null;
        try {
            inputStream =
                    new ObjectInputStream(
                    new BufferedInputStream(
                    new FileInputStream("database/" + species + "_locusSequenceArrayList.ab")));
            locusSequence2DArrayList = (ArrayList<ArrayList<String>>) inputStream.readObject();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return locusSequence2DArrayList;
    }

}
