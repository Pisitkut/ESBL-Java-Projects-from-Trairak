
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.apache.commons.codec.digest.DigestUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trairak
 */
public class TPM extends javax.swing.JFrame {

    private String inputFilePath;
    private String inputFileName;
    private String outputDirectoryPath;

    /**
     * Creates new form TPM
     */
    public TPM() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inputLabel = new javax.swing.JLabel();
        inputTextField = new javax.swing.JTextField();
        inputButton = new javax.swing.JButton();
        outputLabel = new javax.swing.JLabel();
        outputTextField = new javax.swing.JTextField();
        outputButton = new javax.swing.JButton();
        submitButton = new javax.swing.JButton();
        messageLabel = new javax.swing.JLabel();
        TPMLabel = new javax.swing.JLabel();
        ESBLLabel = new javax.swing.JLabel();
        totalclusterLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TPM algorithm");
        setIconImage(Toolkit.getDefaultToolkit().getImage(
            TPM.class.getResource("NHLBI.jpg"))
    );

    inputLabel.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
    inputLabel.setText("Please select a tab-delimited input text file:");

    inputButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
    inputButton.setText("Browse");
    inputButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            inputButtonActionPerformed(evt);
        }
    });

    outputLabel.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
    outputLabel.setText("Save an output folder in:");

    outputButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
    outputButton.setText("Browse");
    outputButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            outputButtonActionPerformed(evt);
        }
    });

    submitButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
    submitButton.setForeground(new java.awt.Color(255, 0, 0));
    submitButton.setText("Submit");
    submitButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            submitButtonActionPerformed(evt);
        }
    });

    TPMLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
    TPMLabel.setForeground(new java.awt.Color(0, 0, 255));
    TPMLabel.setText("Temporal Pattern Mining (TPM) algorithm");

    ESBLLabel.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
    ESBLLabel.setForeground(java.awt.Color.darkGray);
    ESBLLabel.setText("Developed by Epithelial Systems Biology Laboratory, NHLBI, NIH");

    totalclusterLabel.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(messageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(inputTextField)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(inputButton))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(outputTextField)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(outputButton))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addComponent(totalclusterLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(submitButton))
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(inputLabel)
                        .addComponent(outputLabel)
                        .addComponent(TPMLabel)
                        .addComponent(ESBLLabel))
                    .addGap(0, 45, Short.MAX_VALUE)))
            .addContainerGap())
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(TPMLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(ESBLLabel)
            .addGap(50, 50, 50)
            .addComponent(inputLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(inputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(inputButton))
            .addGap(18, 18, 18)
            .addComponent(outputLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(outputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(outputButton))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(submitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(totalclusterLabel)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(messageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inputButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputButtonActionPerformed
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showDialog(TPM.this, "Select");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File inputFile = fc.getSelectedFile();
            inputFilePath = inputFile.getPath();
            inputFileName = inputFile.getName();
            inputTextField.setForeground(Color.blue);
            inputTextField.setText(inputFilePath);
        }
    }//GEN-LAST:event_inputButtonActionPerformed

    private void outputButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outputButtonActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showSaveDialog(TPM.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File outputDirectory = fc.getSelectedFile();
            outputDirectoryPath = outputDirectory.getPath();
            outputTextField.setForeground(Color.blue);
            outputTextField.setText(outputDirectoryPath);
        }
    }//GEN-LAST:event_outputButtonActionPerformed

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        if (inputFileName == null) {
            inputTextField.setForeground(Color.red);
            inputTextField.setText("Please select a tab-delimited input text file");
        } else if (outputDirectoryPath == null) {
            outputTextField.setForeground(Color.red);
            outputTextField.setText("Please select an output directory");
        } else {
            submit();
        }
    }//GEN-LAST:event_submitButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TPM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new TPM().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ESBLLabel;
    private javax.swing.JLabel TPMLabel;
    private javax.swing.JButton inputButton;
    private javax.swing.JLabel inputLabel;
    private javax.swing.JTextField inputTextField;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JButton outputButton;
    private javax.swing.JLabel outputLabel;
    private javax.swing.JTextField outputTextField;
    private javax.swing.JButton submitButton;
    private javax.swing.JLabel totalclusterLabel;
    // End of variables declaration//GEN-END:variables

    private void submit() {
        totalclusterLabel.setText("");
        messageLabel.setText("");
        String inputName = inputFileName.split("\\.")[0];
        String outputFolderName = inputName + "_" + DigestUtils.shaHex(inputName + System.currentTimeMillis());
        String fileSeparator = File.separator;
        String outputFolderPath = outputDirectoryPath + fileSeparator + outputFolderName;
        String outputFileName = outputFolderName + "_output.txt";
        boolean mkdir = new File(outputFolderPath).mkdir();
        File file = new File(outputFolderPath + fileSeparator + outputFileName);
        String outputFilePath = file.getPath();
//        System.out.println("inputFilePath = " + inputFilePath);
//        System.out.println("outputFolderPath = " + outputFolderPath);
//        System.out.println("outputFileName = " + outputFileName);
//        System.out.println("outputFilePath = " + outputFilePath);
        try {
            boolean success = dynamic.dynomite(inputFilePath, outputFilePath);
            if (success) {
                TreeSet<String> graphSet = Graph.getGraph(outputFilePath, outputFolderPath);
                totalclusterLabel.setText("Total number of clusters = " + graphSet.size());
                messageLabel.setText("<html>Please see the output in:<br/>" + outputFolderPath + "</html>");
                Desktop.getDesktop().open(
                        new File(outputFolderPath));
            } else {
                messageLabel.setForeground(Color.red);
                messageLabel.setText("Invalid input format: please see the example and resubmit");
            }
        } catch (IOException ex) {
            Logger.getLogger(TPM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}