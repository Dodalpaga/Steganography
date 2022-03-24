/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

/**
 *
 * @author thevo
 */
public class Pixel {
    
    private int R;
    private int V;
    private int B;
    private int t;
    
    public Pixel(int R,int V,int B,int t){
        this.R = R;
        this.V = V;
        this.B = B;
        this.t = t;
    }
    
    public Pixel(){
        this.R = 255;
        this.V = 255;
        this.B = 255;
        this.t = 255;
    }

    public void setR(int R) {
        this.R = R;
    }

    public void setV(int V) {
        this.V = V;
    }

    public void setB(int B) {
        this.B = B;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getR() {
        return R;
    }

    public int getV() {
        return V;
    }

    public int getB() {
        return B;
    }

    public int getT() {
        return t;
    }
    

    
    public int getPoidsFaibleBleu(){
        String o = Integer.toBinaryString(this.getB());
        int O = Integer.parseInt(o,10);
        return O%2;
    }
    
    public int toInt(){
        return(getT()<<24|getR()<<16|getV()<<8|getB());
    }
    
    public void setPoidsFaibleBleu(int bitMessage){
        int bleu = this.getB();
        if(bleu%2==0){
            if(bitMessage==1){
                this.setB(bleu-1);
            }
        }
        else if(bleu%2==1){
            if(bitMessage==0){
                this.setB(bleu-1);
            }
        }
    }
    
    public Pixel decale(){
        return new Pixel(getR()<<7,getV()<<7,getB()<<7,getT());
    }
    
    
}
