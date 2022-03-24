package HideMorse;

import Utilities.Texte;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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


public class InterfaceMorse {

    private static final JFileChooser Fichier = new JFileChooser(System.getProperty("user.dir")+"\\Images");
    public final JFrame frame;
    public final JFrame morseframe;
    private BufferedImage imageCourante;
    private final  Texte ImageViewSource;
    private final  Texte ImageViewFinale;
    private final JPanel messageDevoile;
    private final JPanel Morse;
    private final JLabel label;
    private String message;
    private int Bit;
    private JMenuItem menuHideMessage;



    public InterfaceMorse(){
        Bit = 0;
        
        frame = new JFrame();
        frame.setTitle("Projet");
        frame.setBounds(100, 100, 850, 600);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));
        
        morseframe = new JFrame();
        morseframe.setTitle("Morse");
        morseframe.setBounds(100, 100, 850, 600);
        morseframe.getContentPane().setLayout(new BorderLayout(0, 0));
        
        label = new JLabel("by Dorian VOYDIE & Jean BAUMERT");
        frame.getContentPane().add(label, BorderLayout.SOUTH);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        ImageViewSource = new  Texte();
        tabbedPane.addTab("Contenant", ImageViewSource);
        
        Morse = new  JPanel();
        morseframe.add(Morse);

        ImageViewFinale = new  Texte();
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
        
        
        menuHideMessage = new JMenuItem("Cacher un message");
        menuHideMessage.addActionListener((ActionEvent event) -> {
            try {
                cacheMessage();
            } catch (IOException ex) {
                Logger.getLogger(InterfaceMorse.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(InterfaceMorse.class.getName()).log(Level.SEVERE, null, ex);
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


    public void cacheMessage() throws IOException{

        if(ImageViewSource.getCourante() == null) {
            JOptionPane.showMessageDialog(frame,"Ouvrir une image source","Image Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2));
        JPanel setup = new JPanel();
        setup.setLayout(new GridLayout(3, 1));
        
        JButton btn1,btn2,btn3,btn4,btn5;
        ImageIcon img1 = new ImageIcon(ImageIO.read(new File("Images\\Walrus 1.png")));
        ImageIcon img2 = new ImageIcon(ImageIO.read(new File("Images\\Walrus 2.png")));
        
        
        
        btn1 = new JButton(img1);
        btn1.setPreferredSize(new Dimension(400, 600));
        buttons.add(btn1);
        
        btn2 = new JButton(img2);
        btn2.setPreferredSize(new Dimension(400, 600));
        buttons.add(btn2);
        
        btn4 = new JButton("Char");
        btn4.setPreferredSize(new Dimension(250, 200));
        setup.add(btn4);
        
        btn5 = new JButton("Space");
        btn5.setPreferredSize(new Dimension(250, 200));
        setup.add(btn5);
        
        btn3 = new JButton("Stop");
        btn3.setPreferredSize(new Dimension(250, 200));
        setup.add(btn3);
        
        
        Morse.add(buttons);
        Morse.add(setup);
        Texte s = new  Texte();
        s.setCourante(ImageViewSource.getCourante());
        
        btn1.addActionListener((ActionEvent point) -> {
            if(Bit<ImageViewSource.getWidth()*ImageViewSource.getHeight()){
                s.cacheMorse(".",Bit);
                Bit+=8;
            }
        });

        btn2.addActionListener((ActionEvent trait) -> {
            if(Bit<ImageViewSource.getWidth()*ImageViewSource.getHeight()){
                s.cacheMorse("-",Bit);
                Bit+=8;
            }
        });
        
        btn4.addActionListener((ActionEvent charact) -> {
            if(Bit<ImageViewSource.getWidth()*ImageViewSource.getHeight()){
                s.cacheMorse("0",Bit);
                Bit+=8;
            }
        });
        
        btn5.addActionListener((ActionEvent space) -> {
            if(Bit<ImageViewSource.getWidth()*ImageViewSource.getHeight()){
                s.cacheMorse("A",Bit);
                Bit+=8;
            }
        });
        
        
        btn3.addActionListener((ActionEvent Stop) -> {
            JOptionPane.showMessageDialog(frame,"Vous avez arrêté l'écriture (vous pouvez la reprendre dans le menu déroulant","Message Error",JOptionPane.ERROR_MESSAGE);
            menuHideMessage.setText("Reprendre le message en cours");
            morseframe.setVisible(false);
        });
        
        
        
        
        

        
        
        
        
        ImageViewFinale.setCourante(s.getCourante());
        morseframe.pack();
        morseframe.setVisible(true);
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
            JOptionPane.showMessageDialog(frame,"Ouvrir une image source","Image Error",JOptionPane.ERROR_MESSAGE);
        return;
        }
        
        
        String StrNbChar = JOptionPane.showInputDialog(frame, "Combien de caractères voulez vous dévoiler ?"); //Procéder par dichotomie croissante
        if(StrNbChar == null){
            JOptionPane.showMessageDialog(frame,"Révelez un nombre de caractères","Size Error",JOptionPane.ERROR_MESSAGE);
        return;
        }
        
        int nbChar = Integer.parseInt(StrNbChar);
        
        ImageViewSource.setCourante(imageCourante);
        
        Texte s = new  Texte();
        s.setCourante(ImageViewSource.getCourante());
        
        String messageApparent = s.decodeMorse(nbChar);
        
        JTextArea text = new JTextArea(messageApparent);
        text.setWrapStyleWord(true);
        text.setLineWrap(true);
        text.setEditable(false);
        JLabel lb = new JLabel();
        lb.setFont(new Font("Book Antiqua",Font.PLAIN,40));
        
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
                InterfaceMorse window = new InterfaceMorse();
                Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                window.frame.setLocation(d.width/2 - window.frame.getWidth()/2, d.height/2 - window.frame.getHeight()/2);
                window.frame.setVisible(true);
            } catch (HeadlessException event) {
                event.printStackTrace();
            }
        });
    }
    
}
