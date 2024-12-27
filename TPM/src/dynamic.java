

//Created by Fahad Saeed on 30th Oct 2010
// Purpose is to cluster the temporal time points
//for itraq mass spectrometery data.
import java.io.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class dynamic {

    public static File copy(String toFileName)
            throws IOException {
        //  File fromFile = new File(fromFileName);
        File toFile = new File(toFileName);

        return toFile;
    }

    public static void putinfile(String filename, String data) {
        try {
            try (BufferedWriter out = new BufferedWriter(new FileWriter(filename, true))) {
                out.write(data);
                out.write("\n");
            }
        } catch (IOException e) {
        }
    }

    public static void delete(String fileName) {
        try {
            // Construct a File object for the file to be deleted.
            File target = new File(fileName);
            if (!target.exists()) {
                System.err.println("File " + fileName + " not present to begin with!");
                return;
            }
            // Quick, now, delete it immediately:
            if (target.delete()) {
                System.err.println("** Deleted " + fileName + "**");
            } else {
                System.err.println("Failed to delete " + fileName);
            }
        } catch (SecurityException e) {
            System.err.println("Unable to delete " + fileName + "(" + e.getMessage() + ")");
        }
    }

    public static int getLevenshteinDistance(String s, String t) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        int n = s.length(); // length of s
        int m = t.length(); // length of t

        if (n == 0) {
            return m;
        } else if (m == 0) {
            return n;
        }

        if (n > m) {
            // swap the input strings to consume less memory
            String tmp = s;
            s = t;
            t = tmp;
            n = m;
            m = t.length();
        }

        int p[] = new int[n + 1]; //'previous' cost array, horizontally
        int d[] = new int[n + 1]; // cost array, horizontally
        int _d[]; //placeholder to assist in swapping p and d

        // indexes into strings s and t
        int i; // iterates through s
        int j; // iterates through t

        char t_j; // jth character of t

        int cost; // cost

        for (i = 0; i <= n; i++) {
            p[i] = i;
        }

        for (j = 1; j <= m; j++) {
            t_j = t.charAt(j - 1);
            d[0] = j;

            for (i = 1; i <= n; i++) {
                cost = s.charAt(i - 1) == t_j ? 0 : 1;
                // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
                d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
            }

            // copy current distance counts to 'previous row' distance counts
            _d = p;
            p = d;
            d = _d;
        }

        // our last action in the above loop was to switch d and p, so p now
        // actually has the most recent cost counts
        return p[n];
    }

    public static String mapping(String[] candidate) {
        //first is considered peptide name
        String mapping = "";

        for (int i = 1; i < candidate.length; i++) {
//System.out.println(candidate[i]);	
            if (i == 1) {
                if (Double.parseDouble(candidate[1]) < 0.0) {
                    mapping = mapping + "0";
                } else {
                    mapping = mapping + "1";
                }
            } else {
                if (Double.parseDouble(candidate[i - 1]) > Double.parseDouble(candidate[i])) {
                    mapping = mapping + "0";
                } else {
                    mapping = mapping + "1";
                }
            }



        }






        return mapping;
    }

//public static void main(String[]args){
    public static boolean dynomite(String filename, String outfilename) throws IOException {
        Hashtable hashtable = new Hashtable();
        Hashtable original = new Hashtable();
        Hashtable checky = new Hashtable();
        boolean success = true;
        BufferedReader input = null;
        //String filename=args[0];
        //String outfilename=args[1];
        delete(outfilename);
        String currentmapped;
        try {
            FileReader fileClustalW = new FileReader(filename);
            input = new BufferedReader(fileClustalW);
        } catch (FileNotFoundException x) { //The file may not exist.
            System.err.println("File not found:" + filename);
            success = false;
        }

        try {

            String line;

            while ((line = input.readLine()) != null) {


                String[] candidate = line.split("\\s+");

                currentmapped = mapping(candidate);
//		System.out.println(candidate[0]+"	"+currentmapped);
                hashtable.put(candidate[0], currentmapped);
                original.put(candidate[0], line);


            } //end of while

            Enumeration e = hashtable.keys();
            Enumeration e1 = hashtable.keys();
            Enumeration o = original.keys();

            int count = 0, counter = 0;
            String element;
            String tocheck;
            String originalstring;
            while (e1.hasMoreElements()) {
                count++;
                element = e1.nextElement().toString();
                //System.out.println("Element : "+element);
                e = hashtable.keys();
                o = original.keys();
                while (e.hasMoreElements()) {
                    tocheck = e.nextElement().toString();
                    originalstring = o.nextElement().toString();
                    //System.out.println("To Check "+ tocheck);
                    //if levenstein distance is zero for the mapping then they belong to the same cluster
                    if (getLevenshteinDistance(hashtable.get(element).toString(), hashtable.get(tocheck).toString()) == 0 && checky.containsKey(tocheck) == false) {
                        //System.out.println(original.get(originalstring)+"	cluster-"+count);
                        putinfile(outfilename, original.get(originalstring) + "   cluster-" + count);
                        //System.out.println(hashtable.get(element).toString()+"	"+hashtable.get(tocheck).toString());
                        checky.put(element, "");
                        checky.put(tocheck, "");
                    }


                }

            }




        } catch (Throwable t) {
            //t.printStackTrace();
            //System.out.println("Invalid");
            success = false;
        }





        File output = copy(outfilename);
        return success;

    }
//public static void main(String[]args)throws IOException{
    //  dynomite("haha1","lulu");
    //   }
}
