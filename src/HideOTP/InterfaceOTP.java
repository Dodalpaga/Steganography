package HideOTP;

import Utilities.Texte;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;


public class InterfaceOTP {

    private static final JFileChooser Fichier = new JFileChooser(System.getProperty("user.dir")+"\\Images");
    public final JFrame frame;
    private BufferedImage imageCourante;
    private final Texte ImageViewSource;
    private final Texte ImageViewFinale;
    private final JPanel messageDevoile;
    private final JLabel label;
    private String message;
    private String key;



    public InterfaceOTP() {
        frame = new JFrame();
        frame.setTitle("Projet");
        frame.setBounds(100, 100, 850, 600);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));
        
        label = new JLabel("by Dorian VOYDIE & Jean BAUMERT");
        frame.getContentPane().add(label, BorderLayout.SOUTH);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        ImageViewSource = new Texte();
        tabbedPane.addTab("Contenant", ImageViewSource);

        ImageViewFinale = new Texte();
        tabbedPane.addTab("Image dissimulée", ImageViewFinale);
        
        messageDevoile = new JPanel();
        tabbedPane.addTab("Message codé", messageDevoile);


        
        JMenuBar menuBar = new JMenuBar();
        menuBar.setToolTipText("");
        frame.setJMenuBar(menuBar);


        
        JMenu menuFile = new JMenu("Fichiers");
        menuBar.add(menuFile);


        JMenuItem menuOpenSource = new JMenuItem("Ouvrir un contenant");
        menuOpenSource.addActionListener((ActionEvent event) -> {
            openImageSource();
        });
        menuFile.add(menuOpenSource);
        
        
        JMenuItem menuDefineKey = new JMenuItem("Définissez une clé");
        menuDefineKey.addActionListener((ActionEvent event) -> {
            defineKey();
        });
        menuFile.add(menuDefineKey);
        
        
        JMenuItem menuHideMessage = new JMenuItem("Cacher un message");
        menuHideMessage.addActionListener((ActionEvent event) -> {
            try {
                cacheMessage();
            } catch (IOException ex) {
                Logger.getLogger(InterfaceOTP.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        menuFile.add(menuHideMessage);

        
        JMenuItem menuSaveResult = new JMenuItem("Sauvegarder");
        menuSaveResult.addActionListener((ActionEvent event) -> {
            saveResult();
        });
        menuFile.add(menuSaveResult);
        
        JMenuItem menuDevoile = new JMenuItem("Dévoilez un potentiel message caché");
        menuDevoile.addActionListener((ActionEvent event) -> {
            try {
                Reveal();
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(InterfaceOTP.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        menuFile.add(menuDevoile);
        
    }


    public void openImageSource() {

        Fichier.setSelectedFile(new File("image-source"));
        //Selection d'un fichier
        if(Fichier.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        //Gestion de l'exception d'approbation

        imageCourante = Texte.loadImage(Fichier.getSelectedFile());
        //Attibution du choix du fichier à la variable "imageCourante"

        if(imageCourante == null) {
            JOptionPane.showMessageDialog(frame,"Le format du fichier n'est pas reconnu","Erreur lors du chargement de l'image",JOptionPane.ERROR_MESSAGE);
            return;
        }
        //Gestion de l'exception le fichier n'est pas une image.

        ImageViewSource.setCourante(imageCourante);
        //On définit la source comme étant l'image courante, qui est en réalité le fichier sélectionné
        label.setText(ImageViewSource.getCourante().getWidth()+"X"+ImageViewSource.getCourante().getHeight());
        frame.pack();
        
        
    }
    
    public void defineKey(){
        key = JOptionPane.showInputDialog(frame, "Définissez une clé");
        
        if(key.length()==0) {
            JOptionPane.showMessageDialog(frame,"Il faut définir une clé","Message Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
         
    }
    
    public void mdp(){
        key = JOptionPane.showInputDialog(frame, "Définissez une clé");
        
        if(key == null) {
            JOptionPane.showMessageDialog(frame,"Mot de passe ?","Message Error",JOptionPane.ERROR_MESSAGE);
            return;
        } 
    }

    
    
    public void cacheMessage() throws IOException{

        if(ImageViewSource.getCourante() == null) {
            JOptionPane.showMessageDialog(frame,"Ouvrir une image source","Image Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(key.length()==0) {
            JOptionPane.showMessageDialog(frame,"Il faut définir une clé","Message Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        message = JOptionPane.showInputDialog(frame, "Enter a message");
        
        if(message == null) {
            JOptionPane.showMessageDialog(frame,"Ecrire un message","Message Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        Texte s = new Texte();
        s.setCourante(ImageViewSource.getCourante());
        
        s.cacheTexte(s.change(message,key));
        
        ImageViewFinale.setCourante(s.getCourante());
    }



    public void saveResult(){
        if(ImageViewFinale.getCourante() == null){
            JOptionPane.showMessageDialog(frame,"Cachez un message d'abord","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        Fichier.setSelectedFile(new File("Image_Cachée.png"));
        if (Fichier.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            Texte.saveImage(ImageViewFinale.getCourante(), Fichier.getSelectedFile());
        }
    }
    
    


    public void Reveal() throws UnsupportedEncodingException{
        
        
        if(ImageViewSource.getCourante() == null){
            JOptionPane.showMessageDialog(frame,"Ouvrir une image contenante","Image Error",JOptionPane.ERROR_MESSAGE);
        return;
        }
        String StrNbChar = JOptionPane.showInputDialog(frame, "Combien de caractères voulez vous dévoiler ?"); //Procéder par dichotomie croissante
        int nbChar = Integer.parseInt(StrNbChar);
        
        ImageViewSource.setCourante(imageCourante);
        
        Texte s = new Texte();
        s.setCourante(ImageViewSource.getCourante());
        
        mdp();
        
        String messageApparent = s.reveleOTP(nbChar,key);
        
        JTextArea text = new JTextArea(messageApparent);
        text.setWrapStyleWord(true);
        text.setLineWrap(true);
        text.setEditable(false);
        JLabel lb = new JLabel();
        
        text.setBorder(lb.getBorder());
        text.setBackground(new Color(lb.getBackground().getRGB(), true));
        text.setForeground(new Color(lb.getForeground().getRGB(), true));
        text.setOpaque(lb.isOpaque());
        text.setSize(frame.getWidth(),frame.getHeight());
        
        messageDevoile.removeAll();

        text.setColumns(20);
        text.setRows(5);

        GroupLayout layout;
        layout = new GroupLayout(messageDevoile);
        messageDevoile.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(text, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(text, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }
    
    




    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                InterfaceOTP window = new InterfaceOTP();
                Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                window.frame.setLocation(d.width/2 - window.frame.getWidth()/2, d.height/2 - window.frame.getHeight()/2);
                window.frame.setVisible(true);
            } catch (HeadlessException event) {
                event.printStackTrace();
            }
        });
    }
    
}
