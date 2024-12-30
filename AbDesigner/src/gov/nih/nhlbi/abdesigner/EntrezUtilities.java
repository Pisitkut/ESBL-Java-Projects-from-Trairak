/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.nih.nhlbi.abdesigner;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author pisitkut
 */
public class EntrezUtilities {

    //Call FASTA
    public static String callFASTA(String inputID) throws IOException {
        String str = null;
        if (inputID != null) {
            boolean success = false;
            while (!success) {
                try {
                    URL ncbiFASTA = new URL("http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=protein&id=" + inputID + "&rettype=fasta&retmode=xml");
                    BufferedReader in = new BufferedReader(
                                        new InputStreamReader(
                                        ncbiFASTA.openStream()));
                    String inputLine = null;
                    while ((inputLine = in.readLine())!= null) {
                        if (inputLine.contains("<TSeq_sequence>") && inputLine.contains("</TSeq_sequence>")) {
                            str = inputLine.replace("<TSeq_sequence>", "").replace("</TSeq_sequence>", "").trim();
                        }
                    }
                    in.close();
                    success = true;
                } catch (MalformedURLException e) {
                } catch (IOException e) {
                    success = false;
                }
            }
        }
        return str;
    }

    //Call NCBI text
    public static String callNCBIText(String inputID) throws IOException {
        StringBuilder str = null;
        boolean success = false;
        while (!success) {
            str = new StringBuilder();
            try {
                URL ncbi = new URL("http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=protein&id=" + inputID + "&rettype=gp&retmode=text"); //Call NCBI protein
                BufferedReader in = new BufferedReader(
                                    new InputStreamReader(
                                    ncbi.openStream()));
                String inputLine = null;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.startsWith("DBSOURCE")) {
                        str.append(inputLine).append(" ");
                    }
                    if (inputLine.startsWith("  ORGANISM")) {
                        str.append(inputLine.trim()).append("<endSpecies>");
                    }
                    if (inputLine.startsWith("FEATURES")) {
                        do {
                            if (!String.valueOf(inputLine.charAt(5)).equals(" ")) {
                                if (inputLine.startsWith("     Region")) {
                                    str.append(inputLine.replace("Region", "<a>Region</a>").trim()).append(" ");
                                } else if (inputLine.startsWith("     Site")) {
                                    str.append(inputLine.replace("Site", "<a>Site</a>").trim()).append(" ");
                                } else {
                                    str.append("<a>").append(inputLine.trim()).append(" ");
                                    if (inputLine.startsWith("ORIGIN")) {
                                        break;
                                    }
                                }
                            } else {
                                str.append(inputLine.trim()).append(" ");
                            }
                        } while ((inputLine = in.readLine()) != null);
                    }
                }
                in.close();
                success = true;
            } catch (MalformedURLException e) {
            } catch (IOException e) {
                success = false;
            }
        }
        return str.toString();
    }

    //Get species
    public static String getSpecies(String str) {
        String species = null;
        int startOrganism = str.indexOf("ORGANISM", 0);
        if (startOrganism != -1) {
            int startSpecies = startOrganism + 10;
            int endSpecies = str.indexOf("<endSpecies>", startSpecies);
            species = str.substring(startSpecies, endSpecies);
        }
        return species;
    }

    //Get gene name
    public static String getGeneName(String str) {
        String gene = null;
        int startGene = str.indexOf("/gene=", 0);
        if (startGene != -1) {
            int endGene = str.indexOf("\"", startGene + 7);
            gene = str.substring(startGene + 7, endGene);
        }
        return gene;
    }

    //Call RefSeq
    public static String callRefSeq(String inputID, Object selectSpecies) throws IOException {
        StringBuilder str = null;
        boolean success = false;
        while (!success) {
            str = new StringBuilder();
            try {
                URL ncbi = new URL("http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=protein&term=" + inputID + "[gene]+AND+" + selectSpecies + "[orgn]+AND+srcdb_refseq[prop]"); //Call RefSeq
                BufferedReader in = new BufferedReader(
                                    new InputStreamReader(
                                    ncbi.openStream()));
                String inputLine = null;
                while ((inputLine = in.readLine()) != null) {
                    str.append(inputLine.trim()).append(" ");
                }
                in.close();
                success = true;
            } catch (MalformedURLException e) {
            } catch (IOException e) {
                success = false;
            }
        }
        //countRefSeqID(str);
        String refSeqCheck = null;
        String finalRefSeq = null;
        int startRefSeq = 0;
        int endRefSeq = 0;
        while (startRefSeq != -1) {
            startRefSeq = str.indexOf("<Id>", endRefSeq);
            if (startRefSeq != -1) {
                endRefSeq = str.indexOf("</Id>", startRefSeq + 4);
                if (endRefSeq != -1) {
                    refSeqCheck = str.substring(startRefSeq + 4, endRefSeq);
                    String geneCheck = getGeneName(callNCBIText(refSeqCheck));
                    System.out.println(inputID + "\t" + geneCheck);
                    if (inputID.toUpperCase().equals(geneCheck.toUpperCase())) {
                        finalRefSeq = refSeqCheck;
                        break;
                    }
                }
            }
        }
        return finalRefSeq;
    }

    //Call Swiss-Prot
    public static String[] callSwissProt(String inputID, Object selectSpecies) throws IOException {
        StringBuilder str = null;
        boolean success = false;
        while (!success) {
            str = new StringBuilder();
            try {
                URL ncbi = new URL("http://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=protein&term=" + inputID + "[gene]+AND+" + selectSpecies + "[orgn]+AND+srcdb_swiss-prot[prop]"); //Call SwissProt
                BufferedReader in = new BufferedReader(
                                    new InputStreamReader(
                                    ncbi.openStream()));
                String inputLine = null;
                while ((inputLine = in.readLine()) != null) {
                    str.append(inputLine.trim()).append(" ");
                }
                in.close();
                success = true;
            } catch (MalformedURLException e) {
            } catch (IOException e) {
                success = false;
            }
        }
        //countSwissProtID(str);
        String spCheck = null;
        String[] finalSp = new String[2];
        ArrayList<String> geneCheckArrayList = new ArrayList<String>();
        int startSp = 0;
        int endSp = 0;
        while (startSp != -1) {
            startSp = str.indexOf("<Id>", endSp);
            if (startSp != -1) {
                endSp = str.indexOf("</Id>", startSp + 4);
                if (endSp != -1) {
                    spCheck = str.substring(startSp + 4, endSp);
                    String geneCheck = getGeneName(callNCBIText(spCheck));
                    geneCheckArrayList.add(geneCheck);
                    System.out.println(inputID + "\t" + geneCheck);
                    if (inputID.toUpperCase().equals(geneCheck.toUpperCase())) {
                        finalSp[0] = spCheck;
                        break;
                    }
                }
            }
        }
        finalSp[1] = geneCheckArrayList.toString();
        return finalSp;
    }

    //Get uniProtLocus
    public static String getUniProtLocus(String swissProtContent) {
        String uniProtLocus = null;
        int startUniProtLocus = swissProtContent.indexOf("DBSOURCE", 0);
        if (startUniProtLocus != -1) {
            int endUniProtLocus = swissProtContent.indexOf(",", startUniProtLocus);
            uniProtLocus = swissProtContent.substring(startUniProtLocus + 29, endUniProtLocus);
        }
        return uniProtLocus;
    }

}
