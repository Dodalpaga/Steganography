package HideImage;
import Utilities.Histogrammes;
import Utilities.Images;
import static Utilities.Images.loadImage;
import static Utilities.Images.saveImage;
import Utilities.Texte;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.RefineryUtilities;


public final class InterfaceImages extends JFrame{

    private final JFileChooser Fichier = new JFileChooser(System.getProperty("user.dir")+"\\Images");
    public final JFrame frame;
    private BufferedImage imageFlottante;
    private final Images sourceManager;
    private final Images cacheManager;
    private final Images finaleManager;
    private final Images rougeManager;
    private final Images vertManager;
    private final Images bleuManager;
    private final Images negatifManager;
    private final Images grisManager;
    private final Images contourManager;
    private final JLabel label;
    private final JTabbedPane tabbedPane;
    
    private final ChartPanel histogramPanel;



    public InterfaceImages() {
        frame = new JFrame();
        frame.setTitle("Projet");
        frame.setBounds(100, 100, 1250, 900);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        label = new JLabel();
        frame.getContentPane().add(label, BorderLayout.SOUTH);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        sourceManager = new Images();
        tabbedPane.addTab("Contenant", sourceManager);

        cacheManager = new Images();
        tabbedPane.addTab("Image à cacher", cacheManager);
        
        finaleManager = new Images();
        tabbedPane.addTab("Image dissimulée", finaleManager);
        
        rougeManager = new Images();
        tabbedPane.addTab("Canal rouge", rougeManager);
        
        vertManager = new Images();
        tabbedPane.addTab("Canal vert", vertManager);
        
        bleuManager = new Images();
        tabbedPane.addTab("Canal bleu", bleuManager);
        
        negatifManager = new Images();
        tabbedPane.addTab("Négatif", negatifManager);
        
        grisManager = new Images();
        tabbedPane.addTab("Niveau de gris", grisManager);
        
        contourManager = new Images();
        tabbedPane.addTab("Contours", contourManager);

        histogramPanel = new ChartPanel((JFreeChart) null);
        tabbedPane.addTab("Histogramme", histogramPanel);

        


        JMenuBar menuBar = new JMenuBar();
        menuBar.setToolTipText("");
        frame.setJMenuBar(menuBar);


        JMenu mnFile = new JMenu("Fichiers");
        menuBar.add(mnFile);


        JMenuItem mntmOpenSource = new JMenuItem("Ouvrir un contenant");
        mntmOpenSource.addActionListener((ActionEvent arg0) -> {
            openImageSource();
        });
        mnFile.add(mntmOpenSource);


        JMenuItem mntmOpenImageSecrète = new JMenuItem("Ouvrir un image secrète");
        mntmOpenImageSecrète.addActionListener((ActionEvent arg0) -> {
            openImageToHide();
        });
        mnFile.add(mntmOpenImageSecrète);

        
        JMenuItem mntmNewMenuItem = new JMenuItem("Cacher Image");
        mntmNewMenuItem.addActionListener((ActionEvent arg0) -> {
            hideImg();
        });
        mnFile.add(mntmNewMenuItem);

        
        JMenuItem mntmSaveResult = new JMenuItem("Sauvegarder");
        mntmSaveResult.addActionListener((ActionEvent event) -> {
            saveResult();
        });
        mnFile.add(mntmSaveResult);
        
        
        JMenuItem mntmRevealImage = new JMenuItem("Effectuer une stéganalyse visuelle");
        mntmRevealImage.addActionListener((ActionEvent event) -> {
            Reveal();
        });
        mnFile.add(mntmRevealImage);
        
        
        JMenuItem mntmDispHisto = new JMenuItem("Ouvrir l'histogramme");
        mntmDispHisto.addActionListener((ActionEvent arg0) -> {
            displayHistogram("Histogramme");
        });
        mnFile.add(mntmDispHisto);
    }

    
    



    public void openImageSource() {

        
        Fichier.setSelectedFile(new File("image-source"));
        //Selection d'un fichier
        
        if(Fichier.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        //Gestion de l'exception d'approbation

        imageFlottante = loadImage(Fichier.getSelectedFile());
        //Attibution du choix du fichier à la variable "imageCourante"

        if(imageFlottante == null) {
            JOptionPane.showMessageDialog(frame,"Le format du fichier n'est pas reconnu","Erreur lors du chargement de l'image",JOptionPane.ERROR_MESSAGE);
            return;
        }
        //Gestion de l'exception le fichier n'est pas une image.

        sourceManager.setCourante(loadImage(Fichier.getSelectedFile()));
        rougeManager.canalRouge(loadImage(Fichier.getSelectedFile()));
        bleuManager.canalBleu(loadImage(Fichier.getSelectedFile()));
        vertManager.canalVert(loadImage(Fichier.getSelectedFile()));
        negatifManager.negatif(loadImage(Fichier.getSelectedFile()));
        grisManager.canalGris(loadImage(Fichier.getSelectedFile()));
        contourManager.contour(loadImage(Fichier.getSelectedFile()));
        
        
        
        
        label.setText("Image source : "+sourceManager.getCourante().getWidth()+"x"+sourceManager.getCourante().getHeight());
        

        frame.pack();
    }





    

    public void openImageToHide() {

        if(sourceManager.getCourante() == null) {
            JOptionPane.showMessageDialog(frame,"Ouvrez une image source","Erreur lors du chargement de l'image",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Fichier.setSelectedFile(new File("image-to-hide"));

        //Erreur fichier non reconnue
        if(Fichier.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        imageFlottante = loadImage(Fichier.getSelectedFile());

        if(imageFlottante == null) {
            JOptionPane.showMessageDialog(frame,"Le format du fichier n'est pas reconnu comme étant une image","Erreur lors du chargement de l'image",JOptionPane.ERROR_MESSAGE);
            return;
        }

        //Check if both images have the same dimensions
        if(imageFlottante.getHeight() > sourceManager.getCourante().getHeight() || imageFlottante.getWidth() > sourceManager.getCourante().getWidth()) {
            JOptionPane.showMessageDialog(frame,"L'image à cacher doit être plus petite ou égale à l'image source","Erreur lors du chargement de l'image",JOptionPane.ERROR_MESSAGE);
            return;
        }

        cacheManager.setCourante(imageFlottante);
        label.setText("Image source : "+sourceManager.getCourante().getWidth()+"x"+sourceManager.getCourante().getHeight()+"    /     Image à cacher : "+cacheManager.getCourante().getWidth()+"x"+cacheManager.getCourante().getHeight());
        frame.pack();
    }


    
    
    

    public void displayHistogram(String titre){

        Fichier.setSelectedFile(new File("Convertir image -> histogramme"));

        if(Fichier.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        imageFlottante = loadImage(Fichier.getSelectedFile());

        if(imageFlottante == null) {
            JOptionPane.showMessageDialog(frame,"Le format du fichier n'est pas reconnu en tant que format d'image","Image Load Error",JOptionPane.ERROR_MESSAGE);
            return;
        }


        Texte fenetre = new Texte();
        fenetre.setCourante(imageFlottante);
        Histogrammes histogramme = new Histogrammes("HistogrammeDemo1",fenetre);
        histogramPanel.add(histogramme.getPanel());
        RefineryUtilities.centerFrameOnScreen(histogramme);
        frame.pack();
    }

    
    
    
    

    public void hideImg(){

        if(sourceManager.getCourante() == null || cacheManager.getCourante() == null) {
            JOptionPane.showMessageDialog(frame,"Ouvrez une source, ainsi qu'une image à cacher","Image Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        Images s = new Images(sourceManager.getCourante(), cacheManager.getCourante());
        s.hideImage();

        finaleManager.setCourante(s.getFinale());
    }






    public void Reveal(){
        Fichier.setSelectedFile(new File("image-source"));
        //Selection d'un fichier
        if(Fichier.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        imageFlottante = loadImage(Fichier.getSelectedFile());
        BufferedImage ImageDecale = loadImage(Fichier.getSelectedFile());
        
        finaleManager.setCourante(ImageDecale);
        finaleManager.decalage();
        tabbedPane.setTitleAt(2, "Stéganalyse visuelle");
        frame.pack();
    }






    public void saveResult(){
        if(finaleManager.getCourante() == null){
            JOptionPane.showMessageDialog(frame,"Cachez au préalable une image","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        Fichier.setSelectedFile(new File("Image_Cachée.png"));
        if (Fichier.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            saveImage(finaleManager.getCourante(), Fichier.getSelectedFile());
        }
    }
    
    
    
    
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                InterfaceImages window = new InterfaceImages();
                Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                window.frame.setLocation(d.width/2 - window.frame.getWidth()/2, d.height/2 - window.frame.getHeight()/2);
                window.frame.setVisible(true);
            } catch (HeadlessException event) {
                event.printStackTrace();
            }
        });
    }
    
    
}
