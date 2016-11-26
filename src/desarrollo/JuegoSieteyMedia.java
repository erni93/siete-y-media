/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desarrollo;

import java.util.ArrayList;

/**
 *
 * @author ernid
 */
public class JuegoSieteyMedia {
    private ArrayList<Carta> manoJugador = new ArrayList<>();
    private ArrayList<Carta> manoBanca = new ArrayList<>();
    private double puntuacionJugador;
    private double puntuacionBanca;
    private final Carta[] BARAJA;
    private int posicionBaraja;
    private boolean devolverValorCartaDestapada = false;
    private boolean jugadorPlantado = false;
    private boolean bancaPlantada = false;
    private boolean partidaTerminada = false;
    public JuegoSieteyMedia(Baraja baraja) {
        
        //Barajeamos las cartas
        baraja.barajarAleatoriamente();
        //Obtenemos el array de cartas
        this.BARAJA = baraja.getCartas();
        //Añadimos las primeras cartas a cada mano y actualizamos su puntuacion.
        this.BARAJA[posicionBaraja].setTapada(true);
        manoJugador.add(this.BARAJA[posicionBaraja]);
        puntuacionJugador+= this.BARAJA[posicionBaraja].getNumSieteyMedia();
        posicionBaraja++;
        
        manoBanca.add(this.BARAJA[posicionBaraja]);
        puntuacionBanca+= this.BARAJA[posicionBaraja].getNumSieteyMedia();
        posicionBaraja++;
    }

    public Carta[] getManoJugador() {
        Carta[] obtenerManoJugador = new Carta[manoJugador.size()];
        obtenerManoJugador = manoJugador.toArray(obtenerManoJugador);
        return obtenerManoJugador;
    }

    public Carta[] getManoBanca() {
        Carta[] obtenerManoBanca = new Carta[manoBanca.size()];
        obtenerManoBanca = manoBanca.toArray(obtenerManoBanca);
        return obtenerManoBanca;
    }

    public double getPuntuacionJugador() {
        return puntuacionJugador;
    }

    public double getPuntuacionBanca() {
        return puntuacionBanca;
    }
    
    public void robarJugador(){
        if (posicionBaraja >= BARAJA.length){
            posicionBaraja = 0;
        }
        //Si no hay ninguna carta tapada en la mesa la siguiente va tapada
        if (hayCartaTapadaManoJugador() == false){
            this.BARAJA[posicionBaraja].setTapada(true);   
        }
        manoJugador.add(this.BARAJA[posicionBaraja]);
        posicionBaraja++;
    }
    public void robarBanca(){ 
        if (posicionBaraja >= BARAJA.length){
            posicionBaraja = 0;
        }
        manoBanca.add(this.BARAJA[posicionBaraja]);
        posicionBaraja++;
    }
    private boolean hayCartaTapadaManoJugador(){
        for (Carta buscarTapada: manoJugador){
            if (buscarTapada.isTapada()){
                return true;
            }
        }
        return false;
    }
    public void calcularPuntuacionJugador(){
        puntuacionJugador = 0;
        //Solo cuenta los puntos de las cartas destapadas
        for (Carta cartaJugador: manoJugador){
            if (cartaJugador.isTapada() == false || devolverValorCartaDestapada){
               puntuacionJugador+= cartaJugador.getNumSieteyMedia(); 
            }
            
        }
    }
    public void calcularPuntuacionBanca(){
        puntuacionBanca = 0;
        for (Carta cartaBanca: manoBanca){
            puntuacionBanca+= cartaBanca.getNumSieteyMedia();
        }
    }
    public void destaparCartasJugador(){
        for (int i=0; i<manoJugador.size(); i++){
            Carta obtenerCarta = manoJugador.get(i);
            if (obtenerCarta.isTapada()){
                obtenerCarta.setTapada(false);
                manoJugador.set(i, obtenerCarta);
            }
        }
        
    }
    public Carta obtenerUltimaCartaValidaJugador(){
        //Si la carta a calcular es la destapada  devolvemos su valor
        if (devolverValorCartaDestapada){
            for (Carta obtenerCarta: manoJugador){
                if (obtenerCarta.isTapada()){
                    return obtenerCarta;
                }
            }
        }
        for (int i= manoJugador.size()-1; i>=0; i--){
            Carta obtenerCarta = manoJugador.get(i);
            if (obtenerCarta.isTapada() == false){
                return obtenerCarta;
            }
        }
        return manoJugador.get(0);
    }

    public void setDevolverValorCartaDestapada(boolean devolverValorCartaDestapada) {
        this.devolverValorCartaDestapada = devolverValorCartaDestapada;
    }

  
    public void setJugadorPlantado(boolean jugadorPlantado) {
        this.jugadorPlantado = jugadorPlantado;
    }

    public boolean isJugadorPlantado() {
        return jugadorPlantado;
    }

    public boolean isBancaPlantada() {
        return bancaPlantada;
    }

    public void setBancaPlantada(boolean bancaPlantada) {
        this.bancaPlantada = bancaPlantada;
    }

    public boolean isPartidaTerminada() {
        return partidaTerminada;
    }

    public void setPartidaTerminada(boolean partidaTerminada) {
        this.partidaTerminada = partidaTerminada;
    }
    
}
