package Utilities;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Texte extends JComponent{

    private int width, height;
    private BufferedImage imageCourante;
    private static final String IMAGE_FORMAT = "PNG";

    
    public Texte(){
        imageCourante = null;
    }
    
    public void setCourante(BufferedImage image)    {
        if(image != null) {
            width = image.getWidth();
            height = image.getHeight();
            imageCourante = image;
            repaint();
        }
    }
    public BufferedImage getCourante(){
    	return imageCourante;
    }
    public void clearImageCourante(){
        Graphics imageGraphics = imageCourante.getGraphics();
        imageGraphics.setColor(Color.LIGHT_GRAY);
        imageGraphics.fillRect(0, 0, width, height);
        repaint();
    }
    
    
    ////Display
    @Override   
    public Dimension getPreferredSize(){
        return new Dimension(width, height);
    }
    @Override
    public void paintComponent(Graphics g){
        Dimension size = getSize();
        g.clearRect(0, 0, size.width, size.height);
        if(imageCourante != null) {
            g.drawImage(imageCourante, 0, 0, null);
        }
    }
    //Display
    
    
    //PixelsManager
    public int blue(int x, int y){
        int pixel = imageCourante.getRGB(x, y);
        
        
        int blue = (pixel) & 0xff;
        return blue;
    }
    public int green(int x, int y){
        int pixel = imageCourante.getRGB(x, y);
        
        
        int blue = (pixel>>8) & 0xff;
        return blue;
    }
    public int red(int x, int y){
        int pixel = imageCourante.getRGB(x, y);
        
        
        int blue = (pixel>>16) & 0xff;
        return blue;
    }
    public int alpha(int x, int y){
        int pixel = imageCourante.getRGB(x, y);
        
        
        int blue = (pixel>>24) & 0xff;
        return blue;
    }
    public Pixel getPixel(int x , int y){
        return(new Pixel(red(x,y),green(x,y),blue(x,y),alpha(x,y)));
    }
    //PixelsManager
    
    
    ////Textes
    public void cacheTexte(String mess) throws IOException{
        StringBuilder binaire = new StringBuilder();
        for (byte b : mess.getBytes("UTF-8")){
            int octet = (int) b; //Convertit le byte en un entier binaire
            for (int i = 0; i < 8; i++){
                if ((octet & 128) == 0){
                    binaire.append(0);
                }
                else binaire.append(1);
                octet <<= 1;
            }
        }
        
        int l = binaire.length();
        if((imageCourante.getWidth())*(imageCourante.getHeight())>l){
            for(int i=0 ; i< l ; i++){
            
            int x = i%(imageCourante.getWidth()); //Retour à la ligne
            int y = i/(imageCourante.getWidth()); //Retour à la ligne
            
            char caract = binaire.charAt(i);
            int car = Integer.parseInt(String.valueOf(caract));
            
            Pixel P = getPixel(x,y);
            P.setPoidsFaibleBleu(car);
            
            imageCourante.setRGB(x,y, P.toInt());
            }
        }

    }
    public String decodeTexte(int nbChar) throws UnsupportedEncodingException{
        String BinaryMessage = "";
        
        for (int i=0 ; i<=nbChar*8-1 ; i++){
            int x = i%(imageCourante.getWidth());
            int y = i/(imageCourante.getWidth());
            
            Pixel P = getPixel(x,y);
            BinaryMessage = BinaryMessage + P.getPoidsFaibleBleu();
        }
        //On a à présent un String binaire ayant pour taille 8*nbChar
        
        String TextMessage="";
        for (int i=0 ; i<=BinaryMessage.length()-8 ; i+=8){
            int lettreInt = Integer.parseInt((String) BinaryMessage.subSequence(i, i+8),2);
            char lettre = (char) lettreInt;
            TextMessage = TextMessage + lettre;
        }
        //On a converti chaque octet en son caractère puis on le "range" dans TextMessage
        
        return TextMessage;
        
    }
    ////Textes
    
  
    ////OTP
    public int toInt(String Caract){
        if(Caract.equals("a")||Caract.equals("A")){
            return 1;
        }
        if(Caract.equals("b")||Caract.equals("B")){
            return 2;
        }
        if(Caract.equals("c")||Caract.equals("C")){
            return 3;
        }
        if(Caract.equals("d")||Caract.equals("D")){
            return 4;
        }
        if(Caract.equals("e")||Caract.equals("E")){
            return 5;
        }
        if(Caract.equals("f")||Caract.equals("F")){
            return 6;
        }
        if(Caract.equals("g")||Caract.equals("G")){
            return 7;
        }
        if(Caract.equals("h")||Caract.equals("H")){
            return 8;
        }
        if(Caract.equals("i")||Caract.equals("I")){
            return 9;
        }
        if(Caract.equals("j")||Caract.equals("J")){
            return 10;
        }
        if(Caract.equals("k")||Caract.equals("K")){
            return 11;
        }
        if(Caract.equals("l")||Caract.equals("L")){
            return 12;
        }
        if(Caract.equals("m")||Caract.equals("M")){
            return 13;
        }
        if(Caract.equals("n")||Caract.equals("N")){
            return 14;
        }
        if(Caract.equals("o")||Caract.equals("O")){
            return 15;
        }
        if(Caract.equals("p")||Caract.equals("P")){
            return 16;
        }
        if(Caract.equals("q")||Caract.equals("Q")){
            return 17;
        }
        if(Caract.equals("r")||Caract.equals("R")){
            return 18;
        }
        if(Caract.equals("s")||Caract.equals("S")){
            return 19;
        }
        if(Caract.equals("t")||Caract.equals("T")){
            return 20;
        }
        if(Caract.equals("u")||Caract.equals("U")){
            return 21;
        }
        if(Caract.equals("v")||Caract.equals("V")){
            return 22;
        }
        if(Caract.equals("w")||Caract.equals("W")){
            return 23;
        }
        if(Caract.equals("x")||Caract.equals("X")){
            return 24;
        }
        if(Caract.equals("y")||Caract.equals("Y")){
            return 25;
        }
        if(Caract.equals("z")||Caract.equals("Z")){
            return 26;
        }
        if(Caract.equals("0")){
            return 27;
        }
        if(Caract.equals("1")){
            return 28;
        }
        if(Caract.equals("2")){
            return 29;
        }
        if(Caract.equals("3")){
            return 30;
        }
        if(Caract.equals("4")){
            return 31;
        }
        if(Caract.equals("5")){
            return 32;
        }
        if(Caract.equals("6")){
            return 33;
        }
        if(Caract.equals("7")){
            return 34;
        }
        if(Caract.equals("8")){
            return 35;
        }
        if(Caract.equals("9")){
            return 36;
        }
        if(Caract.equals(" ")){
            return 37;
        }
        if(Caract.equals("@")){
            return 38;
        }
        //Rajouter autant de caractères lisibles que l'on veut, compléter dans toInt
        
        
        else{
            return 0;
        }
    }
    public String toString(int Caract){
        if(Caract==1){
            return "A";
        }
        if(Caract==2){
            return "B";
        }
        if(Caract==3){
            return "C";
        }
        if(Caract==4){
            return "D";
        }
        if(Caract==5){
            return "E";
        }
        if(Caract==6){
            return "F";
        }
        if(Caract==7){
            return "G";
        }
        if(Caract==8){
            return "H";
        }
        if(Caract==9){
            return "I";
        }
        if(Caract==10){
            return "J";
        }
        if(Caract==11){
            return "K";
        }
        if(Caract==12){
            return "L";
        }
        if(Caract==13){
            return "M";
        }
        if(Caract==14){
            return "N";
        }
        if(Caract==15){
            return "O";
        }
        if(Caract==16){
            return "P";
        }
        if(Caract==17){
            return "Q";
        }
        if(Caract==18){
            return "R";
        }
        if(Caract==19){
            return "S";
        }
        if(Caract==20){
            return "T";
        }
        if(Caract==21){
            return "U";
        }
        if(Caract==22){
            return "V";
        }
        if(Caract==23){
            return "W";
        }
        if(Caract==24){
            return "X";
        }
        if(Caract==25){
            return "Y";
        }
        if(Caract==26){
            return "Z";
        }
        if(Caract==27){
            return "0";
        }
        if(Caract==28){
            return "1";
        }
        if(Caract==29){
            return "2";
        }
        if(Caract==30){
            return "3";
        }
        if(Caract==31){
            return "4";
        }
        if(Caract==32){
            return "5";
        }
        if(Caract==33){
            return "6";
        }
        if(Caract==34){
            return "7";
        }
        if(Caract==35){
            return "8";
        }
        if(Caract==36){
            return "9";
        }
        if(Caract==37){
            return " ";
        }
        if(Caract==38){
            return "@";
        }
        //Rajouter autant de caractères lisibles que l'on veut, compléter dans aller
        
        
        else{
            return "?";
        }
    }
    public String change(String mess,String clé){
        String messCaché = "";
        for (int i = 0 ; i<mess.length() ; i++){
            String c = "" + mess.charAt(i);
            int indexMess = toInt(c);
            int indexKey = toInt(""+clé.charAt(i%clé.length()));
            String result = toString((indexMess + indexKey)%39);
            messCaché = messCaché+result;
        }
        return messCaché;
    }
    public String reveleOTP(int nbChar, String clé){
        String BinaryMessage = "";
        for (int i=0 ; i<=nbChar*8-1 ; i++){
            int x = i%(imageCourante.getWidth());
            int y = i/(imageCourante.getWidth());
            Pixel P = getPixel(x,y);
            BinaryMessage = BinaryMessage + P.getPoidsFaibleBleu();
        }
        String TextMessage="";
        for (int i=0 ; i<=BinaryMessage.length()-8 ; i+=8){
            int lettreInt = Integer.parseInt((String) BinaryMessage.subSequence(i, i+8),2);
            char lettre = (char) lettreInt;
            int diff = toInt(""+lettre)-toInt(""+clé.charAt((i/8)%clé.length()));
            if (diff>=0){
                diff=diff%39;
            }
            else{
                diff = diff+39;
            }
            TextMessage = TextMessage + toString(diff);
        }
        return TextMessage;
    }
    ////OTP
    
    
    ////Morse
    public String decodeMorse(int nbChar) throws UnsupportedEncodingException{
        String BinaryMessage = "";
        
        for (int i=0 ; i<=nbChar*4*8-1 ; i++){
            int x = i%(imageCourante.getWidth());
            int y = i/(imageCourante.getWidth());
            
            Pixel P = getPixel(x,y);
            BinaryMessage = BinaryMessage + P.getPoidsFaibleBleu();
        }
        //On a à présent un String binaire ayant pour taille 8*nbChar
        
        String TextMessage="";
        for (int i=0 ; i<=BinaryMessage.length()-8 ; i+=8){
            int lettreInt = Integer.parseInt((String) BinaryMessage.subSequence(i, i+8),2);
            char lettre = (char) lettreInt;
            TextMessage = TextMessage + lettre;
        }
        //On a converti chaque octet en son caractère puis on le "range" dans TextMessage
        
        return decodage(TextMessage);
    }
    public String traduction(String morse){
        if(morse.equals(".-")){
            return "a";
        }
        if(morse.equals("-...")){
            return "b";
        }
        if(morse.equals("-.-.")){
            return "c";
        }
        if(morse.equals("-..")){
            return "d";
        }
        if(morse.equals(".")){
            return "e";
        }
        if(morse.equals("..-.")){
            return "f";
        }
        if(morse.equals("--.")){
            return "g";
        }
        if(morse.equals("....")){
            return "h";
        }
        if(morse.equals("..")){
            return "i";
        }
        if(morse.equals(".---")){
            return "j";
        }
        if(morse.equals("-.-")){
            return "k";
        }
        if(morse.equals(".-..")){
            return "l";
        }
        if(morse.equals("--")){
            return "m";
        }
        if(morse.equals("-.")){
            return "n";
        }
        if(morse.equals("---")){
            return "o";
        }
        if(morse.equals(".--.")){
            return "p";
        }
        if(morse.equals("--.-")){
            return "q";
        }
        if(morse.equals(".-.")){
            return "r";
        }
        if(morse.equals("...")){
            return "s";
        }
        if(morse.equals("-")){
            return "t";
        }
        if(morse.equals("..-")){
            return "u";
        }
        if(morse.equals("...-")){
            return "v";
        }
        if(morse.equals(".--")){
            return "w";
        }
        if(morse.equals("-..-")){
            return "x";
        }
        if(morse.equals("-.--")){
            return "y";
        }
        if(morse.equals("--..")){
            return "z";
        }
        if(morse.equals("-----")){
            return "0";
        }
        if(morse.equals(".----")){
            return "1";        
        }
        if(morse.equals("..---")){
            return "2";
        }
        if(morse.equals("...--")){
            return "3";
        }
        if(morse.equals("....-")){
            return "4";
        }
        if(morse.equals(".....")){
            return "5";
        }
        if(morse.equals("-....")){
            return "6";
        }
        if(morse.equals("--...")){
            return "7";
        }
        if(morse.equals("---..")){
            return "8";
        }
        if(morse.equals("----.")){
            return "9";
        }
        
        else{
            return "?";
        }
    }
    public String decodage(String morse){
        String messageFinal = "";
        String[] morseSplited = morse.split("A");
        for(int i = 0; i<=morseSplited.length-1 ; i++){
            String[] wordSplited = morseSplited[i].split("0");
            for(int j = 0 ; j<= wordSplited.length-1 ; j++){
                messageFinal+=traduction(wordSplited[j]);
            }
            messageFinal = messageFinal + " ";
        }
        return messageFinal;
    }
    public void cacheMorse(String mess,int Bit){
        StringBuilder binaire = new StringBuilder();
        for (byte b : mess.getBytes()){
            int octet = (int) b; //Convertit le byte en un entier binaire
            for (int i = 0; i < 8; i++){
                if ((octet & 128) == 0){
                    binaire.append(0);
                }
                else binaire.append(1);
                octet <<= 1;
            }
        }
        
        
        
        int l = binaire.length();
        if((imageCourante.getWidth())*(imageCourante.getHeight())>l){
            for(int i=Bit ; i<=Bit+7 ; i++){
            
                int x = i%(imageCourante.getWidth()); //Retour à la ligne
                int y = i/(imageCourante.getWidth()); //Retour à la ligne

                char caract = binaire.charAt(i%8);
                int car = Integer.parseInt(String.valueOf(caract));

                Pixel P = getPixel(x,y);
                P.setPoidsFaibleBleu(car);

                imageCourante.setRGB(x,y, P.toInt());
            }
        }
    }
    ////Morse
    

    
    //Lecteur
    public static BufferedImage loadImage(File imageFile){
        try {
            BufferedImage image = ImageIO.read(imageFile);
            if(image == null) {
                return null;
            }
            return image;
        }
        catch(IOException e) {
            return null;
        }
    }
    //Sauveur
    public static void saveImage(BufferedImage image, File file){
        try {
            ImageIO.write(image, IMAGE_FORMAT, file);
        }
        catch(IOException exc) {
        }
    }
}