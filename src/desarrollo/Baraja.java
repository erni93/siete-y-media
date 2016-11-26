/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desarrollo;

import java.util.Random;

/**
 *
 * @author ernid
 */
public class Baraja {
    private Carta[] cartas;

    public Baraja() {
        cartas = new Carta[40];
        //Insertamos las cartas
        for (int u=0; u<cartas.length;){
            for (int i=1; i<=12; i++){
                //Insertamos una de cada
                if (i != 8 && i != 9){
                  cartas[u] = new Carta("oro", i,false);
                    u++;
                    cartas[u] = new Carta("basto",i,false);
                    u++;
                    cartas[u] = new Carta("copa", i,false);
                    u++;
                    cartas[u] = new Carta("espada", i,false);
                    u++;  
                }
            }
        }
    }
    
    public void barajarAleatoriamente(){
        Random generadorAleatorios = new Random();
        for (int i=0; i<cartas.length; i++){
            //Numero aleatorio entre 0 y 48
            int numAleatorio = generadorAleatorios.nextInt(cartas.length);
            //Copiamos el valor que se va a pisar
            Carta copiaCartaAux = cartas[numAleatorio];
            //Intercambiamos posiciones, la destino con la origen
            cartas[numAleatorio] = cartas[i];
            cartas[i] = copiaCartaAux;
        }
    }
    public Carta[] getCartas() {
        return cartas;
    }
    
}
