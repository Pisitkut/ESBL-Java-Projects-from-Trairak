
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * KinaseMotifClassifier.java
 *
 * Created on Jul 13, 2011, 4:47:03 PM
 */
/**
 *
 * @author pisitkut
 */
public class KinaseMotifClassifier extends javax.swing.JFrame {

    /** Creates new form KinaseMotifClassifier */
    public KinaseMotifClassifier() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        inputTextArea = new javax.swing.JTextArea();
        submitButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        outputTextArea = new javax.swing.JTextArea();
        saveButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        copyButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        clearButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kinase Motif Classifier");

        inputTextArea.setColumns(20);
        inputTextArea.setRows(5);
        jScrollPane1.setViewportView(inputTextArea);

        submitButton.setText("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        outputTextArea.setColumns(20);
        outputTextArea.setRows(5);
        jScrollPane2.setViewportView(outputTextArea);

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Enter list of phosphopeptides (at least 13 amino acids):");

        copyButton.setText("Copy");
        copyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyButtonActionPerformed(evt);
            }
        });

        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        clearButton2.setText("Clear");
        clearButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(saveButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(copyButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(clearButton2))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(submitButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(clearButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                    .addComponent(jLabel1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submitButton)
                    .addComponent(clearButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(copyButton)
                    .addComponent(clearButton2))
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        String[] allInput = inputTextArea.getText().split("\\s+");
        String Motif;
        outputTextArea.append("Peptide Sequence" + "\t" + "Motif" + "\n");
        for (int i = 0; i < allInput.length; i++) {
            String input = null;

            input = allInput[i];
            input = input.replace("E", "D").replace("R", "K").replace("T*", "S*");
            boolean patternY = input.contains("Y*");
            if (patternY) {
                Motif = "tyrosine";
            } else {
                int phosphoresidue = input.indexOf("S*");
                boolean hasProline = input.substring(phosphoresidue + 2, phosphoresidue + 3).equals("P");
                System.out.println(input.substring(phosphoresidue + 2, phosphoresidue + 3));
                if (hasProline) {
                    Motif = "proline-directed";
                } else {
                    String oneToSix = input.substring(phosphoresidue + 2, phosphoresidue + 8);
                    String oneToSixD = oneToSix.replaceAll("[^D]", "");
                    int Dcount = oneToSixD.length();
                    if (Dcount >= 5) {
                        Motif = "acidic";
                    } else {
                        boolean minusThree = input.substring(phosphoresidue - 3, phosphoresidue - 2).equals("K");
                        if (minusThree) {
                            Motif = "basic";
                        } else {
                            boolean plusThreeAcidic = input.substring(phosphoresidue + 2, phosphoresidue + 5).contains("D");
                            if (plusThreeAcidic) {
                                Motif = "acidic";
                            } else {
                                String minusOneToMinusSix = input.substring(phosphoresidue - 6, phosphoresidue);
                                String minusOneToMinusSixK = minusOneToMinusSix.replaceAll("[^K]", "");
                                int Kcount = minusOneToMinusSixK.length();
                                if (Kcount >= 2) {
                                    Motif = "basic";
                                } else {
                                    Motif = "other";
                                }
                            }
                        }
                    }
                }
            }
            
            outputTextArea.append(allInput[i] + "\t" + Motif + "\n");
        }
    }//GEN-LAST:event_submitButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(KinaseMotifClassifier.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            PrintStream p = null;
            try {
                File file = fc.getSelectedFile();
                p = new PrintStream(new BufferedOutputStream(new FileOutputStream(file)));
                p.println(outputTextArea.getText());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(KinaseMotifClassifier.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                p.close();
            }
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void copyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyButtonActionPerformed
        outputTextArea.requestFocusInWindow();
        outputTextArea.selectAll();
        Clipboard cb =
                Toolkit.getDefaultToolkit().
                getSystemClipboard();
        String s = outputTextArea.getText();
        StringSelection contents =
                new StringSelection(s);
        cb.setContents(contents, null);
    }//GEN-LAST:event_copyButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
    inputTextArea.setText("");
    }//GEN-LAST:event_clearButtonActionPerformed

    private void clearButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButton2ActionPerformed
    outputTextArea.setText("");
    }//GEN-LAST:event_clearButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new KinaseMotifClassifier().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton clearButton2;
    private javax.swing.JButton copyButton;
    private javax.swing.JTextArea inputTextArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea outputTextArea;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton submitButton;
    // End of variables declaration//GEN-END:variables

}
