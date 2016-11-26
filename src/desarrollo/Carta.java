/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desarrollo;

/**
 *
 * @author ernid
 */
public class Carta {
    private final String PALO;
    private final int NUM;
    private boolean tapada;
    public Carta(String PALO, int NUM, boolean tapada) {
        this.PALO = PALO;
        this.NUM = NUM;
        this.tapada = tapada;
    }
    public String getPALO() {
        return PALO;
    }

    public int getNum() {
        return NUM;
    }
    public double getNumSieteyMedia() {
        if (NUM >=1 && NUM <=9){
            return NUM;
        }else{
            return 0.5;
        }
    }
    public boolean isTapada() {
        return tapada;
    }

    public void setTapada(boolean tapada) {
        this.tapada = tapada;
    }
    
    
}
