/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.nih.nhlbi.abdesigner;

import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 *
 * @author pisitkut
 */
public class SwissProtExtraction {

    //Get all region
    public static Object[] getAllRegion(String swissProtContent) {
        LinkedHashSet<String> allRegion = new LinkedHashSet<String>();
        int startRegion = 0;
        int endRegion = 0;
        //Read all region
        while (startRegion != -1) {
            startRegion = swissProtContent.indexOf("/region_name=", endRegion);
            if (startRegion != -1) {
                endRegion = swissProtContent.indexOf("\"", startRegion + 14);
                String eachRegion = swissProtContent.substring(startRegion + 14, endRegion);
                allRegion.add(eachRegion);
            }
        }
        System.out.println(allRegion);
        Object[] arrayAllRegion = allRegion.toArray();
        return arrayAllRegion;
    }

    //Get region
    public static ArrayList getRegion(String swissProtContent, Object arrayAllRegion, int inputSequenceLength) {
        int startEachRegion = 0;
        int endEachRegion = 0;
        int startRegion = 0;
        int startNextLine = 0;
        int endRegion = 0;
        int startNote = 0;
        int endNote = 0;
        Object note;
        ArrayList<Integer> allRegion = new ArrayList<Integer>();
        ArrayList arrayNote = new ArrayList();
        ArrayList allNote = new ArrayList();
        //Process each region
        while (startEachRegion != -1) {
        startEachRegion = swissProtContent.indexOf("/region_name=\"" + arrayAllRegion + "\"", endEachRegion);
            if (startEachRegion != -1) {
                startRegion = swissProtContent.lastIndexOf("Region</a>", startEachRegion);
                startNextLine = swissProtContent.indexOf("/", startRegion + 10);
                String region = swissProtContent.substring(startRegion + 10, startNextLine).trim();
                endRegion = swissProtContent.indexOf("<a>", startNextLine);
                //Check if there is a note or not
                if (swissProtContent.substring(startNextLine, endRegion).contains("/note=")) {
                    startNote = swissProtContent.indexOf("/note=", startEachRegion) + 7;
                    endNote = swissProtContent.indexOf("\"", startNote);
                    note = swissProtContent.substring(startNote, endNote);
                } else {
                    note = arrayAllRegion;
                }
                //Read region number
                int startEachRegionNumber = 0;
                int endEachRegionNumber = 0;
                if (region.contains("..")) {
                    startEachRegionNumber = Integer.parseInt(region.substring(0, region.indexOf(".")).replaceAll("[<>(]", ""));
                    endEachRegionNumber = Integer.parseInt(region.substring(region.lastIndexOf(".") + 1).replaceAll("[<>)]", ""));
                } else {
                    startEachRegionNumber = Integer.parseInt(region);
                    endEachRegionNumber = startEachRegionNumber;
                }
                //Put region number and note in same order
                for (int i = 0; i < (endEachRegionNumber - startEachRegionNumber + 1); i++) {
                    allRegion.add(i + startEachRegionNumber);
                    arrayNote.add(note);
                }
                System.out.println(region);
                endEachRegion = startEachRegion + 1;
            }
        }
        System.out.println(allRegion);
        System.out.println(arrayNote);
        //Create Tail/Loop array if arrayAllRegion has Transmembrane region
        if (arrayAllRegion.equals("Transmembrane region")) {
            Integer[] arrAllRegion = allRegion.toArray(new Integer[0]);
            int endNTail = arrAllRegion[0] - 1;
            int startCTail = arrAllRegion[arrAllRegion.length - 1] + 1;
            for (int i = 0; i < inputSequenceLength; i++) {
                int eachTMResidue = i + 1;
                if (allRegion.contains(eachTMResidue)) {
                    allNote.add("Transmembrane region");
                } else {
                    if (i < endNTail) {
                        allNote.add("NH2-tail");
                    } else if (i >= (startCTail - 1)) {
                        allNote.add("COOH-tail");
                    } else {
                        allNote.add("Loop");
                    }
                }
            }
        } else {
            //Create array of note from all region
            for (int i = 0; i < inputSequenceLength; i++) {
                int eachRegionResidue = i + 1;
                if (allRegion.contains(eachRegionResidue)) {
                    StringBuilder eachNote = new StringBuilder();
                    //Put overlapping notes together separated by \t
                    for (int j = 0; j < allRegion.size(); j++) {
                        if (allRegion.get(j).equals(eachRegionResidue)) {
                            eachNote.append((String) arrayNote.get(j)).append("\t");
                        }
                    }
                    allNote.add(eachNote.substring(0, eachNote.length() - 1));
                } else {
                    allNote.add(" ");
                }
            }
        }
        System.out.println(allNote);
        return(allNote);
    }

    //Get all site
    public static Object[] getAllSite(String swissProtContent) {
        LinkedHashSet<String> allSite = new LinkedHashSet<String>();
        int startSite = 0;
        int endSite = 0;
        while (startSite != -1) {
            startSite = swissProtContent.indexOf("/site_type=", endSite);
            if (startSite != -1) {
                endSite = swissProtContent.indexOf("\"", startSite + 12);
                String eachSite = swissProtContent.substring(startSite + 12, endSite);
                allSite.add(eachSite);
            }
        }
        System.out.println(allSite);
        Object[] arrayAllSite = allSite.toArray();
        return arrayAllSite;
    }

    //Get site
    public static ArrayList getSite(String swissProtContent, Object arrayAllSite, int inputSequenceLength) {
        int startEachSite = 0;
        int endEachSite = 0;
        int startSite = 0;
        int startNextLine = 0;
        int endSite = 0;
        int startNote = 0;
        int endNote = 0;
        Object note;
        ArrayList<Integer> allSite = new ArrayList<Integer>();
        ArrayList arrayNote = new ArrayList();
        ArrayList allNote = new ArrayList();
        while (startEachSite != -1) {
        startEachSite = swissProtContent.indexOf("/site_type=\"" + arrayAllSite + "\"", endEachSite);
            if (startEachSite != -1) {
                startSite = swissProtContent.lastIndexOf("Site</a>", startEachSite);
                startNextLine = swissProtContent.indexOf("/", startSite + 8);
                String site = swissProtContent.substring(startSite + 8, startNextLine).trim();
                endSite = swissProtContent.indexOf("<a>", startNextLine);
                //Check if there is a note or not
                if (swissProtContent.substring(startNextLine, endSite).contains("/note=")) {
                    startNote = swissProtContent.indexOf("/note=", startEachSite) + 7;
                    endNote = swissProtContent.indexOf("\"", startNote);
                    note = swissProtContent.substring(startNote, endNote);
                } else {
                    note = arrayAllSite;
                }
                //Read site number
                int startEachSiteNumber = 0;
                int endEachSiteNumber = 0;
                if (site.contains("order")) {
                    String[] arraySite = site.replace("order(", "").replace(")", "").replace(" ", "").split(",");
                    for (int i = 0; i < arraySite.length; i++) {
                        if (arraySite[i].contains("..")) {
                            startEachSiteNumber = Integer.parseInt(arraySite[i].substring(0, arraySite[i].indexOf(".")).replaceAll("[<>]", ""));
                            endEachSiteNumber = Integer.parseInt(arraySite[i].substring(arraySite[i].indexOf(".") + 2).replaceAll("[<>]", ""));
                        } else {
                            startEachSiteNumber = Integer.parseInt(arraySite[i]);
                            endEachSiteNumber = startEachSiteNumber;
                        }
                        //Put site number and note in same order
                        for (int j = 0; j < (endEachSiteNumber - startEachSiteNumber + 1); j++) {
                            allSite.add(j + startEachSiteNumber);
                            arrayNote.add(note);
                        }
                    }
                } else {
                    if (site.contains("..")) {
                        startEachSiteNumber = Integer.parseInt(site.substring(0, site.indexOf(".")).replaceAll("[<>]", ""));
                        endEachSiteNumber = Integer.parseInt(site.substring(site.indexOf(".") + 2).replaceAll("[<>]", ""));
                    } else {
                        startEachSiteNumber = Integer.parseInt(site);
                        endEachSiteNumber = startEachSiteNumber;
                    }
                    //Put site number and note in same order
                    for (int i = 0; i < (endEachSiteNumber - startEachSiteNumber + 1); i++) {
                        allSite.add(i + startEachSiteNumber);
                        arrayNote.add(note);
                    }
                }
                System.out.println(site);
                endEachSite = startEachSite + 1;
            }
        }
        System.out.println(allSite);
        System.out.println(arrayNote);
        //Create array of note from all site
        for (int i = 0; i < inputSequenceLength; i++) {
            int eachSiteResidue = i + 1;
            if (allSite.contains(eachSiteResidue)) {
                StringBuilder eachNote = new StringBuilder();
                //Put overlapping notes together separated by \t
                for (int j = 0; j < allSite.size(); j++) {
                    if (allSite.get(j).equals(eachSiteResidue)) {
                        eachNote.append((String) arrayNote.get(j)).append("\t");
                    }
                }
                allNote.add(eachNote.substring(0, eachNote.length() - 1));
            } else {
                allNote.add(" ");
            }
        }
        System.out.println(allNote);
        return(allNote);
    }

}
