package gov.nih.nhlbi.abdesigner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ToolTipManager;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author pisitkut
 */
public final class HeatMap extends javax.swing.JFrame implements Serializable {

    //Declare HeatMap arguments
    private String inputID;
    private int inputSequenceLength;
    private int spanSetting;
    private char[] arrResidue;
    private double[] arrIgScoreHeatMap;
    private String[] igScoreRank;
    private double[] arrUniquenessHeatMap;
    private String[] allUniquenessMatchList;
    private double[] arrConservationHeatMap;
    private String[] allConservationMatchList;
    private String[] peptide;
    private Object[] arrayAllRegion;
    private Object[] arrayAllSite;
    private String[] arrayRegionSite;
    private boolean[] arrayAllRegionSelectedState;
    private boolean[] arrayAllSiteSelectedState;
    private boolean hasTM;
    private int arrayTailLoop;
    private double[] arrayKDHeatMap;
    private double[] arrayKD;
    private String[] structureArray;
    private Color igScoreColorMax, igScoreColorMin;
    private Color uniquenessColorMax, uniquenessColorMin;
    private Color conservationColorMax, conservationColorMin;
    //Declare labels
    private JPanel labels;
    private JPanel topFillerLabel;
    private JPanel positionLabel;
    private JPanel residueLabel;
    private ArrayList<JLabel> regionSiteHeaderList;
    private JLabel structureLabel;
    private JLabel hydropathyLabel;
    private JPanel middleFillerLabel;
    private JLabel igScoreLabel;
    private JLabel uniquenessLabel;
    private JLabel conservationLabel;
    private JPanel bottomFillerLabel;
    //Declare heatMap
    private JPanel heatMap;
    private int heatMapWidth;
    private JPanel topFillerPanel;
    private JPanel positionPanel;
    private JPanel residuePanel;
    private HeatMapResidue[] residueComponent;
    private ArrayList<JPanel> regionSitePanelList;
    private JPanel structurePanel;
    private JPanel hydropathyPanel;
    private JPanel middleFillerPanel;
    private JPanel igScorePanel;
    private JPanel uniquenessPanel;
    private JPanel conservationPanel;
    private RegionSiteComponent[] structureComponent;
    private HeatMapComponent[] hydropathyComponent;
    private HeatMapComponent[] igScoreComponent;
    private HeatMapComponent[] uniquenessComponent;
    private HeatMapComponent[] conservationComponent;
    private JPanel bottomFillerPanel;
    //Declare heatMapScrollPane
    private JScrollPane heatMapScrollPane;
    //Declare JFrame behavior
    private boolean targetLocked;
    //Declare HeatMap constants
    private final int HYDROPATHY = 0;
    private final int IGSCORE = 1;
    private final int UNIQUENESS = 2;
    private final int CONSERVATION = 3;
    private final int RECT_WIDTH = 7;
    private final int SCREEN_W = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int SCREEN_H = Toolkit.getDefaultToolkit().getScreenSize().height;
    private final Color BACKGROUND_COLOR = new JPanel().getBackground();

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HeatMap().setVisible(true);
            }
        });
    }

    public HeatMap() {
        this.arrayAllRegion = new Object[0];
        this.arrayAllSite = new Object[0];
        igScoreColorMax = Color.GREEN;
        igScoreColorMin = Color.BLACK;
        uniquenessColorMax = Color.YELLOW;
        uniquenessColorMin = Color.BLACK;
        conservationColorMax = Color.RED;
        conservationColorMin = Color.BLACK;
        setIconImage(new ImageIcon(getClass().getResource("NHLBI.jpg")).getImage());
        setTitle("AbDesigner HeatMap");
        setJMenuBar(createMenuBar());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public HeatMap(
        String inputID,
        int inputSequenceLength,
        int spanSetting,
        char[] arrResidue, //Use segmaskerChar
        double[] arrIgScoreHeatMap,
        String[] igScoreRank,
        double[] arrUniquenessHeatMap,
        String[] allUniquenessMatchList,
        double[] arrConservationHeatMap,
        String[] allConservationMatchList,
        String[] peptide,
        Object[] arrayAllRegion,
        Object[] arrayAllSite,
        String[] arrayRegionSite,
        boolean hasTM,
        int arrayTailLoop,
        double[] arrayKDHeatMap,
        double[] arrayKD,
        String[] structureArray) {
        this.inputID = inputID;
        this.inputSequenceLength = inputSequenceLength;
        this.spanSetting = spanSetting;
        this.arrResidue = arrResidue;
        this.arrIgScoreHeatMap = arrIgScoreHeatMap;
        this.igScoreRank = igScoreRank;
        this.arrUniquenessHeatMap = arrUniquenessHeatMap;
        this.allUniquenessMatchList = allUniquenessMatchList;
        this.arrConservationHeatMap = arrConservationHeatMap;
        this.allConservationMatchList = allConservationMatchList;
        this.peptide = peptide;
        this.arrayAllRegion = arrayAllRegion;
        this.arrayAllSite = arrayAllSite;
        this.arrayRegionSite = arrayRegionSite;
        if (arrayAllRegion.length != 0) {
            arrayAllRegionSelectedState = new boolean[arrayAllRegion.length];
            Arrays.fill(arrayAllRegionSelectedState, true); //Show all region by default
        }
        if (arrayAllSite.length != 0) {
            arrayAllSiteSelectedState = new boolean[arrayAllSite.length];
            Arrays.fill(arrayAllSiteSelectedState, true); //Show all site by default
        }
        this.hasTM = hasTM;
        this.arrayTailLoop = arrayTailLoop;
        this.arrayKDHeatMap = arrayKDHeatMap;
        this.arrayKD = arrayKD;
        this.structureArray = structureArray;
        igScoreColorMax = Color.GREEN;
        igScoreColorMin = Color.BLACK;
        uniquenessColorMax = Color.YELLOW;
        uniquenessColorMin = Color.BLACK;
        conservationColorMax = Color.RED;
        conservationColorMin = Color.BLACK;
        drawHeatMap();
    }

    public void drawHeatMap() {
        //Initialize labels
        labels = new JPanel(null);
        topFillerLabel = new JPanel();
        positionLabel = new JPanel();
        residueLabel = new JPanel();
        structureLabel = new JLabel("Chou-Fasman", JLabel.CENTER);
        hydropathyLabel = new JLabel("K-D Hydropathy", JLabel.CENTER);
        middleFillerLabel = new JPanel();
        igScoreLabel = new JLabel("Ig-score", JLabel.CENTER);
        uniquenessLabel = new JLabel("Uniqueness", JLabel.CENTER);
        conservationLabel = new JLabel("Conservation", JLabel.CENTER);
        bottomFillerLabel = new JPanel();
        topFillerLabel.setBounds(0, 0, 120, 40);
        positionLabel.setBounds(0, 40, 120, 20);
        residueLabel.setBounds(0, 60, 120, 20);
        structureLabel.setBounds(0, 80, 120, 20);
        hydropathyLabel.setBounds(0, 100, 120, 20);
        middleFillerLabel.setBounds(0, 120, 120, 4);
        igScoreLabel.setBounds(0, 124, 120, 40);
        uniquenessLabel.setBounds(0, 164, 120, 40);
        conservationLabel.setBounds(0, 204, 120, 40);
        regionSiteHeaderList = new ArrayList<JLabel>();
        for (int i = 0; i < arrayAllRegion.length; i++) {
            regionSiteHeaderList.add(new JLabel(arrayAllRegion[i].toString(), JLabel.CENTER));
            regionSiteHeaderList.get(i).setToolTipText(arrayAllRegion[i].toString());
        }
        for (int i = 0; i < arrayAllSite.length; i++) {
            regionSiteHeaderList.add(new JLabel(arrayAllSite[i].toString(), JLabel.CENTER));
            regionSiteHeaderList.get(i + arrayAllRegion.length).setToolTipText(arrayAllSite[i].toString());
        }

        //Set labels
        setLabels();
        labels.setPreferredSize(new Dimension(120, 284 + (regionSiteHeaderList.size() * 20)));

        //Initialize heatMap
        heatMap = new JPanel(null);
        heatMapWidth = inputSequenceLength * RECT_WIDTH;
        topFillerPanel = new JPanel();
        positionPanel = new PositionPanel();
        residuePanel = new JPanel(new GridLayout(1, inputSequenceLength));
        structurePanel = new JPanel(new GridLayout(1, inputSequenceLength));
        hydropathyPanel = new JPanel(new GridLayout(1, inputSequenceLength));
        middleFillerPanel = new Filler();
        igScorePanel = new JPanel(new GridLayout(1, inputSequenceLength));
        uniquenessPanel = new JPanel(new GridLayout(1, inputSequenceLength));
        conservationPanel = new JPanel(new GridLayout(1, inputSequenceLength));
        bottomFillerPanel = new Filler();
        residueComponent = new HeatMapResidue[inputSequenceLength];
        structureComponent = new RegionSiteComponent[inputSequenceLength];
        hydropathyComponent = new HeatMapComponent[inputSequenceLength];
        igScoreComponent = new HeatMapComponent[inputSequenceLength];
        uniquenessComponent = new HeatMapComponent[inputSequenceLength];
        conservationComponent = new HeatMapComponent[inputSequenceLength];
        for (int i = 0; i < inputSequenceLength; i++) {
            residueComponent[i] = new HeatMapResidue(arrResidue[i], peptide[i], i);
            residuePanel.add(residueComponent[i]);
            structureComponent[i] = new RegionSiteComponent(structureArray[i], peptide[i], i, true);
            structurePanel.add(structureComponent[i]);
            hydropathyComponent[i] = new HeatMapComponent(HYDROPATHY, arrayKDHeatMap[i], Double.toString(arrayKD[i]), peptide[i], i);
            hydropathyPanel.add(hydropathyComponent[i]);
            igScoreComponent[i] = new HeatMapComponent(IGSCORE, arrIgScoreHeatMap[i], igScoreRank[i], peptide[i], i);
            igScorePanel.add(igScoreComponent[i]);
            uniquenessComponent[i] = new HeatMapComponent(UNIQUENESS, arrUniquenessHeatMap[i], allUniquenessMatchList[i], peptide[i], i);
            uniquenessPanel.add(uniquenessComponent[i]);
            conservationComponent[i] = new HeatMapComponent(CONSERVATION, arrConservationHeatMap[i], allConservationMatchList[i], peptide[i], i);
            conservationPanel.add(conservationComponent[i]);
        }
        topFillerPanel.setBounds(0, 0, heatMapWidth, 40);
        positionPanel.setBounds(0, 40, heatMapWidth + 50, 20); //+ 50 for overhanging position
        residuePanel.setBounds(0, 60, heatMapWidth, 20);
        structurePanel.setBounds(0, 80, heatMapWidth, 20);
        hydropathyPanel.setBounds(0, 104, heatMapWidth, 15);
        middleFillerPanel.setBounds(0, 120, heatMapWidth, 4);
        igScorePanel.setBounds(0, 124, heatMapWidth, 40);
        uniquenessPanel.setBounds(0, 164, heatMapWidth, 40);
        conservationPanel.setBounds(0, 204, heatMapWidth, 40);
        regionSitePanelList = new ArrayList<JPanel>();
        for (int i = 0; i < arrayAllRegion.length; i++) {
            JPanel panel = new JPanel(new GridLayout(1, inputSequenceLength));
            RegionSiteComponent[] regionSiteComponents = new RegionSiteComponent[inputSequenceLength];
            for (int j = 0; j < inputSequenceLength; j++) {
                regionSiteComponents[j] = new RegionSiteComponent(arrayRegionSite[j + (i * inputSequenceLength)].replace("\t", "<br>"), peptide[j], j, false);
                panel.add(regionSiteComponents[j]);
            }
            regionSitePanelList.add(panel);
        }
        for (int i = 0; i < arrayAllSite.length; i++) {
            JPanel panel = new JPanel(new GridLayout(1, inputSequenceLength));
            RegionSiteComponent[] regionSiteComponents = new RegionSiteComponent[inputSequenceLength];
            for (int j = 0; j < inputSequenceLength; j++) {
                regionSiteComponents[j] = new RegionSiteComponent(arrayRegionSite[j + ((i + arrayAllRegion.length) * inputSequenceLength)].replace("\t", "<br>"), peptide[j], j, false);
                panel.add(regionSiteComponents[j]);
            }
            regionSitePanelList.add(panel);
        }

        //Set heatMap
        setHeatMap();
        heatMap.setPreferredSize(new Dimension(heatMapWidth + 120, 284 + (regionSitePanelList.size() * 20)));

        //Set JScrollPane
        heatMapScrollPane = new JScrollPane(heatMap);
        heatMapScrollPane.getHorizontalScrollBar().setUnitIncrement(RECT_WIDTH * 10);
        heatMapScrollPane.setRowHeaderView(labels);
        heatMapScrollPane.setBorder(null);
        heatMapScrollPane.setPreferredSize(new Dimension(
                (heatMapWidth + 240 > SCREEN_W) ? SCREEN_W : heatMapWidth + 240,
                (284 + (regionSitePanelList.size() * 20) > SCREEN_H) ? SCREEN_H : 284 + (regionSitePanelList.size() * 20)));
        
        //Set JFrame
        targetLocked = false;
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
        setContentPane(heatMapScrollPane);
        setIconImage(new ImageIcon(getClass().getResource("NHLBI.jpg")).getImage());
        setTitle("NHLBI AbDesigner : " + inputID + " HeatMap");
        setJMenuBar(createMenuBar());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu colorMenu = new JMenu("Color");
        menuBar.add(fileMenu);
        menuBar.add(colorMenu);
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem export = new JMenuItem("Export Image");
        fileMenu.add(open);
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    openActionPerformed(evt);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AbDesigner.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(AbDesigner.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AbDesigner.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        fileMenu.add(save);
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    saveActionPerformed(evt);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AbDesigner.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(AbDesigner.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        fileMenu.add(export);
        export.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exportImageActionPerformed(evt);
            }
        });
        JMenu igScoreMenu = new JMenu("Ig-score");
        JMenu uniquenessMenu = new JMenu("Uniqueness");
        JMenu conservationMenu = new JMenu("Conservation");
        colorMenu.add(igScoreMenu);
        colorMenu.add(uniquenessMenu);
        colorMenu.add(conservationMenu);
        JMenuItem igScoreMenuMax = new JMenuItem("Max");
        JMenuItem igScoreMenuMin = new JMenuItem("Min");
        igScoreMenu.add(igScoreMenuMax);
        igScoreMenuMax.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            Color c = JColorChooser.showDialog(HeatMap.this,
                    "Select Color", igScoreColorMax);
            if (c != null)
                igScoreColorMax = c;
            repaint();
            }
        });
        igScoreMenu.add(igScoreMenuMin);
        igScoreMenuMin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            Color c = JColorChooser.showDialog(HeatMap.this,
                    "Select Color", igScoreColorMin);
            if (c != null)
                igScoreColorMin = c;
            repaint();
            }
        });
        JMenuItem uniquenessMenuMax = new JMenuItem("Best");
        JMenuItem uniquenessMenuMin = new JMenuItem("Worst");
        uniquenessMenu.add(uniquenessMenuMax);
        uniquenessMenuMax.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            Color c = JColorChooser.showDialog(HeatMap.this,
                    "Select Color", uniquenessColorMax);
            if (c != null)
                uniquenessColorMax = c;
            repaint();
            }
        });
        uniquenessMenu.add(uniquenessMenuMin);
        uniquenessMenuMin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            Color c = JColorChooser.showDialog(HeatMap.this,
                    "Select Color", uniquenessColorMin);
            if (c != null)
                uniquenessColorMin = c;
            repaint();
            }
        });
        JMenuItem conservationMenuMax = new JMenuItem("Best");
        JMenuItem conservationMenuMin = new JMenuItem("Worst");
        conservationMenu.add(conservationMenuMax);
        conservationMenuMax.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            Color c = JColorChooser.showDialog(HeatMap.this,
                    "Select Color", conservationColorMax);
            if (c != null)
                conservationColorMax = c;
            repaint();
            }
        });
        conservationMenu.add(conservationMenuMin);
        conservationMenuMin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            Color c = JColorChooser.showDialog(HeatMap.this,
                    "Select Color", conservationColorMin);
            if (c != null)
                conservationColorMin = c;
            repaint();
            }
        });
        if (arrayAllRegion.length != 0) {
            JMenu regionMenu = new JMenu("Region");
            menuBar.add(regionMenu);
            final JCheckBoxMenuItem[] regionCheckBoxMenuItems = new JCheckBoxMenuItem[arrayAllRegion.length];
            for (int i = 0; i < arrayAllRegion.length; i++) {
                final int index = i;
                regionCheckBoxMenuItems[i] = new JCheckBoxMenuItem(arrayAllRegion[i].toString(), arrayAllRegionSelectedState[i]);
                regionMenu.add(regionCheckBoxMenuItems[i]).addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        arrayAllRegionSelectedState[index] = regionCheckBoxMenuItems[index].getState();
                        updateHeatMap();
                    }
                });
            }
            if (arrayAllRegion.length > 1) {
                regionMenu.addSeparator();
                final JMenuItem showAllRegion = new JMenuItem("Show all");
                final boolean[] allTrue = new boolean[arrayAllRegionSelectedState.length];
                Arrays.fill(allTrue, true);
                regionMenu.add(showAllRegion).addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (!Arrays.equals(arrayAllRegionSelectedState, allTrue)) {
                            Arrays.fill(arrayAllRegionSelectedState, true);
                            for (int i = 0; i < regionCheckBoxMenuItems.length; i++) {
                                regionCheckBoxMenuItems[i].setState(true);
                            }
                            updateHeatMap();
                        }
                    }
                });
                final JMenuItem hideAllRegion = new JMenuItem("Hide all");
                final boolean[] allFalse = new boolean[arrayAllRegionSelectedState.length];
                Arrays.fill(allFalse, false);
                regionMenu.add(hideAllRegion).addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (!Arrays.equals(arrayAllRegionSelectedState, allFalse)) {
                            Arrays.fill(arrayAllRegionSelectedState, false);
                            for (int i = 0; i < regionCheckBoxMenuItems.length; i++) {
                                regionCheckBoxMenuItems[i].setState(false);
                            }
                            updateHeatMap();
                        }
                    }
                });
            }
        }
        if (arrayAllSite.length != 0) {
            JMenu siteMenu = new JMenu("Site");
            menuBar.add(siteMenu);
            final JCheckBoxMenuItem[] siteCheckBoxMenuItems = new JCheckBoxMenuItem[arrayAllSite.length];
            for (int i = 0; i < arrayAllSite.length; i++) {
                final int index = i;
                siteCheckBoxMenuItems[i] = new JCheckBoxMenuItem(arrayAllSite[i].toString(), arrayAllSiteSelectedState[i]);
                siteMenu.add(siteCheckBoxMenuItems[i]).addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        arrayAllSiteSelectedState[index] = siteCheckBoxMenuItems[index].getState();
                        updateHeatMap();
                    }
                });
            }
            if (arrayAllSite.length > 1) {
                siteMenu.addSeparator();
                final JMenuItem showAllSite = new JMenuItem("Show all");
                final boolean[] allTrue = new boolean[arrayAllSiteSelectedState.length];
                Arrays.fill(allTrue, true);
                siteMenu.add(showAllSite).addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (!Arrays.equals(arrayAllSiteSelectedState, allTrue)) {
                            Arrays.fill(arrayAllSiteSelectedState, true);
                            for (int i = 0; i < siteCheckBoxMenuItems.length; i++) {
                                siteCheckBoxMenuItems[i].setState(true);
                            }
                            updateHeatMap();
                        }
                    }
                });
                final JMenuItem hideAllSite = new JMenuItem("Hide all");
                final boolean[] allFalse = new boolean[arrayAllSiteSelectedState.length];
                Arrays.fill(allFalse, false);
                siteMenu.add(hideAllSite).addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (!Arrays.equals(arrayAllSiteSelectedState, allFalse)) {
                            Arrays.fill(arrayAllSiteSelectedState, false);
                            for (int i = 0; i < siteCheckBoxMenuItems.length; i++) {
                                siteCheckBoxMenuItems[i].setState(false);
                            }
                            updateHeatMap();
                        }
                    }
                });
            }
        }
        return menuBar;
    }

    private void updateHeatMap() {
        labels.removeAll();
        setLabels();
        heatMap.removeAll();
        setHeatMap();
        heatMapScrollPane.repaint();
    }

    private void setLabels() {
        labels.add(topFillerLabel);
        labels.add(positionLabel);
        labels.add(residueLabel);
        int selectedIndex = 0;
        for (int i = 0; i < arrayAllRegion.length; i++) {
            if (arrayAllRegionSelectedState[i]) {
                regionSiteHeaderList.get(i).setBounds(0, 244 + (selectedIndex * 20), 120, 20);
                labels.add(regionSiteHeaderList.get(i));
                selectedIndex++;
            }
        }
        for (int i = 0; i < arrayAllSite.length; i++) {
            if (arrayAllSiteSelectedState[i]) {
                regionSiteHeaderList.get(i + arrayAllRegion.length).setBounds(0, 244 + (selectedIndex * 20), 120, 20);
                labels.add(regionSiteHeaderList.get(i + arrayAllRegion.length));
                selectedIndex++;
            }
        }
        bottomFillerLabel.setBounds(0, 244 + (selectedIndex * 20), 120, 284 + (regionSiteHeaderList.size() * 20) - (244 + (selectedIndex * 20)));
        labels.add(structureLabel);
        labels.add(hydropathyLabel);
        labels.add(middleFillerLabel);
        labels.add(igScoreLabel);
        labels.add(uniquenessLabel);
        labels.add(conservationLabel);
        labels.add(bottomFillerLabel);
    }

    private void setHeatMap() {
        heatMap.add(topFillerPanel);
        heatMap.add(positionPanel);
        heatMap.add(residuePanel);
        int selectedIndex = 0;
        for (int i = 0; i < arrayAllRegion.length; i++) {
            if (arrayAllRegionSelectedState[i]) {
                regionSitePanelList.get(i).setBounds(0, 244 + (selectedIndex * 20), heatMapWidth + 25, 20);
                heatMap.add(regionSitePanelList.get(i));
                selectedIndex++;
            }
        }
        for (int i = 0; i < arrayAllSite.length; i++) {
            if (arrayAllSiteSelectedState[i]) {
                regionSitePanelList.get(i + arrayAllRegion.length).setBounds(0, 244 + (selectedIndex * 20), heatMapWidth + 25, 20);
                heatMap.add(regionSitePanelList.get(i + arrayAllRegion.length));
                selectedIndex++;
            }
        }
        bottomFillerPanel.setBounds(0, 244 + (selectedIndex * 20), heatMapWidth + 25, 284 + (regionSitePanelList.size() * 20) - (244 + (selectedIndex * 20)));
        heatMap.add(structurePanel);
        heatMap.add(hydropathyPanel);
        heatMap.add(middleFillerPanel);
        heatMap.add(igScorePanel);
        heatMap.add(uniquenessPanel);
        heatMap.add(conservationPanel);
        heatMap.add(bottomFillerPanel);
    }

    void updateData(double[] arrUniquenessHeatMap, String[] allUniquenessMatchList, double[] arrConservationHeatMap, String[] allConservationMatchList) {
        this.arrUniquenessHeatMap = arrUniquenessHeatMap;
        this.allUniquenessMatchList = allUniquenessMatchList;
        this.arrConservationHeatMap = arrConservationHeatMap;
        this.allConservationMatchList = allConservationMatchList;
        for (int i = 0; i < inputSequenceLength; i++) {
            uniquenessComponent[i].setIntensity(arrUniquenessHeatMap[i]);
            conservationComponent[i].setIntensity(arrConservationHeatMap[i]);
            if (!peptide[i].isEmpty()) {
                uniquenessComponent[i].setToolTipText("<html><font face='Monospaced'>" + allUniquenessMatchList[i] + "</font></html>");
                conservationComponent[i].setToolTipText("<html><font face='Monospaced'>" + allConservationMatchList[i] + "</font></html>");
            }
        }
        repaint();
    }

    public class PositionPanel extends JPanel {

        public PositionPanel() {
            setFont(new Font("Monospaced", Font.PLAIN, 12));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(0, 0, 0));
            for (int i = 0; i < inputSequenceLength; i++) {
                if (i % 10 == 0) {
                    if (i < 10)
                        g.drawString(Integer.toString(i + 1), (i * RECT_WIDTH), 15);
                    else if ((i >= 10) && (i < 100))
                        g.drawString(Integer.toString(i + 1), (i * RECT_WIDTH) - 4, 15);
                    else if ((i >= 100) && (i < 1000))
                        g.drawString(Integer.toString(i + 1), (i * RECT_WIDTH) - 7, 15);
                    else
                        g.drawString(Integer.toString(i + 1), (i * RECT_WIDTH) - 11, 15);
                    g.drawLine((i * RECT_WIDTH) + 3, 18, (i * RECT_WIDTH) + 3, 20);
                }
            }
        }

    }

    public class HeatMapResidue extends JLabel implements MouseListener {

        private char residue;
        private String peptide;
        private int index;
        private Color color;
        private final JPopupMenu popup;
        private final JMenuItem copy;
        private final JMenuItem copyAddNtermCys;
        private final JMenuItem copyAddCtermCys;

        public HeatMapResidue(char residue, final String peptide, int index) {
            this.residue = residue;
            this.peptide = peptide;
            this.index = index;
            setFont(new Font("Monospaced", Font.PLAIN, 12));
            setText(Character.toString(residue).toUpperCase());
            if (Character.isLowerCase(residue)) {
                setToolTipText("Low-complexity sequence");
            }
            color = BACKGROUND_COLOR;
            //Create the popup menu
            popup = new JPopupMenu();
            copy = new JMenuItem("Copy");
            copy.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection contents = new StringSelection(peptide.toUpperCase());
                    cb.setContents(contents, null);
                }
            });
            popup.add(copy);
            copyAddNtermCys = new JMenuItem("Add N-terminal Cysteine + Copy");
            copyAddNtermCys.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection contents = new StringSelection("C" + peptide.toUpperCase());
                    cb.setContents(contents, null);
                }
            });
            popup.add(copyAddNtermCys);
            copyAddCtermCys = new JMenuItem("Add C-terminal Cysteine + Copy");
            copyAddCtermCys.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection contents = new StringSelection(peptide.toUpperCase() + "C");
                    cb.setContents(contents, null);
                }
            });
            popup.add(copyAddCtermCys);
            addMouseListener(this);
        }

        public void setColor(Color color) {
            this.color = color;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setOpaque(true);
            if (Character.isLowerCase(residue)) {
                setForeground(Color.RED);
            } else {
                setForeground(Color.BLACK);
            }
            //Draw an underline
            g.setColor(color);
            g.drawLine(0, 18, 6, 18);
            g.drawLine(0, 19, 6, 19);
        }

        public void mouseClicked(MouseEvent e) {

        }

        public void mousePressed(MouseEvent e) {
            if (!peptide.isEmpty()) {
                if (!getBackground().equals(Color.LIGHT_GRAY)) {
                    freezePeptide(index);
                    targetLocked = true;
                } else {
                    unfreezePeptide(index);
                    targetLocked = false;
                }
                maybeShowPopup(e);
            }
        }

        public void mouseReleased(MouseEvent e) {
            if (!peptide.isEmpty()) {
                maybeShowPopup(e);
            }
        }

        public void mouseEntered(MouseEvent e) {
            highlightPeptide(index);
        }

        public void mouseExited(MouseEvent e) {
            unhighlightPeptide(index);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                freezePeptide(index);
                targetLocked = true;
                popup.show(e.getComponent(),
                           e.getX(), e.getY());
            }
        }

    }

    public class RegionSiteComponent extends JPanel implements MouseListener {

        private String regionSite;
        private String peptide;
        private int index;
        private boolean structure;

        public RegionSiteComponent(String regionSite, String peptide, int index, boolean structure) {
            this.regionSite = regionSite;
            this.peptide = peptide;
            this.index = index;
            this.structure = structure;
            addMouseListener(this);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (hasTM && regionSite.equals("Transmembrane region")) {
                g.setColor(Color.RED);
                g.fillRect(0, 8, RECT_WIDTH, 7);
            } else {
                if (!regionSite.equals(" ")) {
                    if (structure) {
                        if (regionSite.equals("alpha helix")) {
                            g.setColor(new Color(102, 102, 102));
                        } else if (regionSite.equals("beta sheet")) {
                            g.setColor(new Color(0, 0, 0));
                        } else if (regionSite.equals("strong beta turn")) {
                            g.setColor(new Color(255, 0, 0));
                        } else if (regionSite.equals("weak beta turn")) {
                            g.setColor(new Color(255, 153, 153));
                        }
                        g.fillRect(0, 4, RECT_WIDTH, 15);
                    } else {
                        g.setColor(new Color(regionSite.hashCode()));
                        g.fillRect(0, 8, RECT_WIDTH, 7);
                    }
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 11, RECT_WIDTH, 1);
                }
            }
            if (!regionSite.equals(" ")) {
                setToolTipText("<html><font face='Monospaced'>" + regionSite + "</font></html>");
            } else {
                setToolTipText(null);
            }
        }

        public void mouseClicked(MouseEvent e) {

        }

        public void mousePressed(MouseEvent e) {
            unfreezePeptide(index);
            targetLocked = false;
        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {
            highlightPeptide(index);
        }

        public void mouseExited(MouseEvent e) {
            unhighlightPeptide(index);
        }

    }

    public class HeatMapComponent extends JPanel implements MouseListener {

        private int type;
        private double intensity;
        private String peptide;
        private int index;
        private final JPopupMenu popup;
        private final JMenuItem copy;
        private final JMenuItem copyAddNtermCys;
        private final JMenuItem copyAddCtermCys;

        public HeatMapComponent(int type, double intensity, String toolTip, final String peptide, int index) {
            this.type = type;
            this.intensity = intensity;
            this.peptide = peptide;
            this.index = index;
            if (!peptide.isEmpty()) {
                if (type == 0)
                    setToolTipText("<html><font face='Monospaced'>" + peptide + "<br>value: " + toolTip + "</font></html>");
                else if (type == 1)
                    setToolTipText("<html><font face='Monospaced'>" + peptide + "<br>value: " + Math.round(intensity * 100)/100.0 + "<br>rank: " + toolTip + "</font></html>");
                else
                    setToolTipText("<html><font face='Monospaced'>" + toolTip + "</font></html>");
            }
            //Create the popup menu
            popup = new JPopupMenu();
            copy = new JMenuItem("Copy");
            copy.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection contents = new StringSelection(peptide.toUpperCase());
                    cb.setContents(contents, null);
                }
            });
            popup.add(copy);
            copyAddNtermCys = new JMenuItem("Add N-terminal Cysteine + Copy");
            copyAddNtermCys.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection contents = new StringSelection("C" + peptide.toUpperCase());
                    cb.setContents(contents, null);
                }
            });
            popup.add(copyAddNtermCys);
            copyAddCtermCys = new JMenuItem("Add C-terminal Cysteine + Copy");
            copyAddCtermCys.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection contents = new StringSelection(peptide.toUpperCase() + "C");
                    cb.setContents(contents, null);
                }
            });
            popup.add(copyAddCtermCys);
            addMouseListener(this);
            setBorder(BorderFactory.createEmptyBorder());
        }

        public void setIntensity(double intensity) {
            this.intensity = intensity;
        }

        @Override
        public Point getToolTipLocation(MouseEvent event) {
            if (spanSetting % 2 == 1) {
                return new Point((spanSetting * RECT_WIDTH * -1 / 2) - 1, getHeight());
            } else {
                return new Point((spanSetting * RECT_WIDTH * -1 / 2) + 3, getHeight());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (type == 0) {
                setBackground(new Color(((int) intensity * (Color.BLACK.getRed() - Color.RED.getRed()) / 255) + Color.RED.getRed(),
                                        ((int) intensity * (Color.BLACK.getGreen() - Color.RED.getGreen()) / 255) + Color.RED.getGreen(),
                                        ((int) intensity * (Color.BLACK.getBlue() - Color.RED.getBlue()) / 255) + Color.RED.getBlue()));
            }
            if (type == 1) {
                setBackground(new Color(((int) intensity * (igScoreColorMax.getRed() - igScoreColorMin.getRed()) / 255) + igScoreColorMin.getRed(),
                                        ((int) intensity * (igScoreColorMax.getGreen() - igScoreColorMin.getGreen()) / 255) + igScoreColorMin.getGreen(),
                                        ((int) intensity * (igScoreColorMax.getBlue() - igScoreColorMin.getBlue()) / 255) + igScoreColorMin.getBlue()));
            }
            if (type == 2) {
                setBackground(new Color(((int) intensity * (uniquenessColorMax.getRed() - uniquenessColorMin.getRed()) / 255) + uniquenessColorMin.getRed(),
                                        ((int) intensity * (uniquenessColorMax.getGreen() - uniquenessColorMin.getGreen()) / 255) + uniquenessColorMin.getGreen(),
                                        ((int) intensity * (uniquenessColorMax.getBlue() - uniquenessColorMin.getBlue()) / 255) + uniquenessColorMin.getBlue()));
            }
            if (type == 3) {
                setBackground(new Color(((int) intensity * (conservationColorMax.getRed() - conservationColorMin.getRed()) / 255) + conservationColorMin.getRed(),
                                        ((int) intensity * (conservationColorMax.getGreen() - conservationColorMin.getGreen()) / 255) + conservationColorMin.getGreen(),
                                        ((int) intensity * (conservationColorMax.getBlue() - conservationColorMin.getBlue()) / 255) + conservationColorMin.getBlue()));
            }
        }

        public void mouseClicked(MouseEvent e) {

        }

        public void mousePressed(MouseEvent e) {
            if (!peptide.isEmpty()) {
                if (!getBorder().isBorderOpaque()) {
                    freezePeptide(index);
                    setBorder(BorderFactory.createLineBorder(Color.WHITE));
                    targetLocked = true;
                } else {
                    unfreezePeptide(index);
                    targetLocked = false;
                }
                maybeShowPopup(e);
            }
        }

        public void mouseReleased(MouseEvent e) {
            if (!peptide.isEmpty()) {
                maybeShowPopup(e);
            }
        }

        public void mouseEntered(MouseEvent e) {
            highlightPeptide(index);
        }

        public void mouseExited(MouseEvent e) {
            unhighlightPeptide(index);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                freezePeptide(index);
                setBorder(BorderFactory.createLineBorder(Color.WHITE));
                targetLocked = true;
                popup.show(e.getComponent(),
                           e.getX(), e.getY());
            }
        }

    }

    public class Filler extends JPanel implements MouseListener {

        public Filler() {
            addMouseListener(this);
        }

        public void mouseClicked(MouseEvent e) {

        }

        public void mousePressed(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {
            ToolTipManager.sharedInstance().setEnabled(false);
        }

        public void mouseExited(MouseEvent e) {
            ToolTipManager.sharedInstance().setEnabled(true);
        }

    }

    private void openActionPerformed(ActionEvent evt) throws FileNotFoundException, IOException, ClassNotFoundException {
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter datFilter = new FileNameExtensionFilter("Data File (.dat)", "dat");
        chooser.addChoosableFileFilter(datFilter);
        int returnVal = chooser.showOpenDialog(HeatMap.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            //Create an input stream from data file
            ObjectInputStream input =
                    new ObjectInputStream(
                    new BufferedInputStream(
                    new FileInputStream(chooser.getSelectedFile())));
            //Read data from the input stream
            inputID = (String) input.readObject();
            inputSequenceLength = input.readInt();
            spanSetting = input.readInt();
            arrResidue = (char[]) input.readObject();
            arrIgScoreHeatMap = (double[]) input.readObject();
            igScoreRank = (String[]) input.readObject();
            arrUniquenessHeatMap = (double[]) input.readObject();
            allUniquenessMatchList = (String[]) input.readObject();
            arrConservationHeatMap = (double[]) input.readObject();
            allConservationMatchList = (String[]) input.readObject();
            peptide = (String[]) input.readObject();
            arrayAllRegion = (Object[]) input.readObject();
            arrayAllSite = (Object[]) input.readObject();
            arrayRegionSite = (String[]) input.readObject();
            arrayAllRegionSelectedState = (boolean[]) input.readObject();
            arrayAllSiteSelectedState = (boolean[]) input.readObject();
            hasTM = input.readBoolean();
            arrayTailLoop = input.readInt();
            arrayKDHeatMap = (double[]) input.readObject();
            arrayKD = (double[]) input.readObject();
            structureArray = (String[]) input.readObject();
            igScoreColorMax = (Color) input.readObject();
            igScoreColorMin = (Color) input.readObject();
            uniquenessColorMax = (Color) input.readObject();
            uniquenessColorMin = (Color) input.readObject();
            conservationColorMax = (Color) input.readObject();
            conservationColorMin = (Color) input.readObject();
            //Close the stream
            input.close();
            //Draw new heatmap
            drawHeatMap();
        }
    }

    private void saveActionPerformed(ActionEvent evt) throws FileNotFoundException, IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter datFilter = new FileNameExtensionFilter("Data File (.dat)", "dat");
        chooser.addChoosableFileFilter(datFilter);
        int returnVal = chooser.showSaveDialog(HeatMap.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            //Create an output stream for data file
            ObjectOutputStream output =
                    new ObjectOutputStream(
                    new BufferedOutputStream(
                    new FileOutputStream(chooser.getSelectedFile().getPath() + ".dat", false)));
            //Write data to the output stream
            output.writeObject(inputID);
            output.writeInt(inputSequenceLength);
            output.writeInt(spanSetting);
            output.writeObject(arrResidue);
            output.writeObject(arrIgScoreHeatMap);
            output.writeObject(igScoreRank);
            output.writeObject(arrUniquenessHeatMap);
            output.writeObject(allUniquenessMatchList);
            output.writeObject(arrConservationHeatMap);
            output.writeObject(allConservationMatchList);
            output.writeObject(peptide);
            output.writeObject(arrayAllRegion);
            output.writeObject(arrayAllSite);
            output.writeObject(arrayRegionSite);
            output.writeObject(arrayAllRegionSelectedState);
            output.writeObject(arrayAllSiteSelectedState);
            output.writeBoolean(hasTM);
            output.writeInt(arrayTailLoop);
            output.writeObject(arrayKDHeatMap);
            output.writeObject(arrayKD);
            output.writeObject(structureArray);
            output.writeObject(igScoreColorMax);
            output.writeObject(igScoreColorMin);
            output.writeObject(uniquenessColorMax);
            output.writeObject(uniquenessColorMin);
            output.writeObject(conservationColorMax);
            output.writeObject(conservationColorMin);
            //Close the stream
            output.close();
        }
    }

    private void exportImageActionPerformed(ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter bmpFilter = new FileNameExtensionFilter("Bitmap Image (.bmp)", "bmp");
        FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG File (.png)", "png");
        chooser.addChoosableFileFilter(bmpFilter);
        chooser.addChoosableFileFilter(pngFilter);
        int returnVal = chooser.showDialog(HeatMap.this, "Export Image");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            //Paint labels
            BufferedImage labelsBufferedImage = new BufferedImage(labels.getWidth(), labels.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics labelsGraphics = labelsBufferedImage.getGraphics();
            labels.paint(labelsGraphics);
            labelsGraphics.dispose();
            //Paint heatMap
            BufferedImage heatMapBufferedImage = new BufferedImage(heatMap.getWidth(), heatMap.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics heatMapGraphics = heatMapBufferedImage.getGraphics();
            heatMap.paint(heatMapGraphics);
            heatMap.update(heatMapGraphics);
            heatMapGraphics.dispose();
            //Draw final image
            BufferedImage bufferedImage = new BufferedImage(labels.getWidth() + heatMap.getWidth(), heatMap.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferedImage.getGraphics();
            g.drawImage(labelsBufferedImage, 0, 0, null);
            g.drawImage(heatMapBufferedImage, labels.getWidth(), 0, null);
            g.dispose();
            //Write image to file
            String extension = ((FileNameExtensionFilter) chooser.getFileFilter()).getExtensions()[0];
            File file = new File(chooser.getSelectedFile().getPath() + "." + extension);
            try {
                ImageIO.write(bufferedImage, extension, file);
            } catch (IOException e) {
            }
        }
    }

    private void highlightPeptide(int index) {
        if (!targetLocked) {
            int leftSpan = index - ((spanSetting - 1) / 2);
            int rightSpan = index + ((spanSetting + 2) / 2);
            int start = (leftSpan < 0) ? 0 : leftSpan;
            int end = (rightSpan > inputSequenceLength) ? inputSequenceLength : rightSpan;
            for (int i = start; i < end; i++) {
                residueComponent[i].setBackground(Color.YELLOW);
                if (i == index) {
                    residueComponent[i].setColor(Color.BLACK);
                } else {
                    residueComponent[i].setColor(Color.YELLOW);
                } 
            }
        }
    }

    private void unhighlightPeptide(int index) {
        if (!targetLocked) {
            int leftSpan = index - ((spanSetting - 1) / 2);
            int rightSpan = index + ((spanSetting + 2) / 2);
            int start = (leftSpan < 0) ? 0 : leftSpan;
            int end = (rightSpan > inputSequenceLength) ? inputSequenceLength : rightSpan;
            for (int i = start; i < end; i++) {
                residueComponent[i].setBackground(BACKGROUND_COLOR);
                residueComponent[i].setColor(BACKGROUND_COLOR);
            }
        }
    }

    private void freezePeptide(int index) {
        int leftSpan = index - ((spanSetting - 1) / 2);
        int rightSpan = index + ((spanSetting + 2) / 2);
        for (int i = 0; i < inputSequenceLength; i++) {
            hydropathyComponent[i].setBorder(BorderFactory.createEmptyBorder());
            igScoreComponent[i].setBorder(BorderFactory.createEmptyBorder());
            uniquenessComponent[i].setBorder(BorderFactory.createEmptyBorder());
            conservationComponent[i].setBorder(BorderFactory.createEmptyBorder());
            if (i >= leftSpan && i < rightSpan) {
                residueComponent[i].setBackground(Color.LIGHT_GRAY);
                residueComponent[i].setToolTipText("Click to unfreeze");
                if (i == index) {
                    residueComponent[i].setColor(Color.BLACK);
                } else {
                    residueComponent[i].setColor(Color.LIGHT_GRAY);
                }
            } else {
                residueComponent[i].setBackground(BACKGROUND_COLOR);
                residueComponent[i].setColor(BACKGROUND_COLOR);
                if (residueComponent[i].getForeground() == Color.RED) {
                    residueComponent[i].setToolTipText("Low-complexity sequence");
                } else {
                    residueComponent[i].setToolTipText(null);
                }
            }
        }
    }

    private void unfreezePeptide(int index) {
        int leftSpan = index - ((spanSetting - 1) / 2);
        int rightSpan = index + ((spanSetting + 2) / 2);
        for (int i = 0; i < inputSequenceLength; i++) {
            hydropathyComponent[i].setBorder(BorderFactory.createEmptyBorder());
            igScoreComponent[i].setBorder(BorderFactory.createEmptyBorder());
            uniquenessComponent[i].setBorder(BorderFactory.createEmptyBorder());
            conservationComponent[i].setBorder(BorderFactory.createEmptyBorder());
            if (i >= leftSpan && i < rightSpan) {
                residueComponent[i].setBackground(Color.YELLOW);
                if (i == index) {
                    residueComponent[i].setColor(Color.BLACK);
                } else {
                    residueComponent[i].setColor(Color.YELLOW);
                }
            } else {
                residueComponent[i].setBackground(BACKGROUND_COLOR);
                residueComponent[i].setColor(BACKGROUND_COLOR);
            }
            if (residueComponent[i].getForeground() == Color.RED) {
                residueComponent[i].setToolTipText("Low-complexity sequence");
            } else {
                residueComponent[i].setToolTipText(null);
            }
        }
    }

}
