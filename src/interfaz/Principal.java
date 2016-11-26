/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import desarrollo.Baraja;
import desarrollo.Carta;
import desarrollo.JuegoSieteyMedia;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author ernid
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image imagenCursor_normal = toolkit.getImage(getClass().getResource("/img/icono_raton_normal.png"));
    Image imagenCursor_clic = toolkit.getImage(getClass().getResource("/img/icono_raton_clic.png"));
    Cursor cursor_normal = toolkit.createCustomCursor(imagenCursor_normal , new Point(this.getX(),this.getY()), "img");
    Cursor cursor_clic = toolkit.createCustomCursor(imagenCursor_clic , new Point(this.getX(),this.getY()), "img");
    //Creamos un nuevo juego
    JuegoSieteyMedia juegoSieteyMedia = new JuegoSieteyMedia(new Baraja());
    ArrayList<JLabel> imagenesManoJugador = new ArrayList<>();
    ArrayList<JLabel> imagenesManoBanca = new ArrayList<>();
    public Principal() {
        initComponents();
        dibujarManoJugador();
        dibujarManoBanca();
        actualizarPuntacionJugador();
        actualizarPuntuacionBanca();
        ponerFondo();
    }
    private void ponerFondo(){
        this.remove(lb_fondo_mesa);
        lb_fondo_mesa = new JLabel();
        lb_fondo_mesa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondo_mesa.png")));
        getContentPane().add(lb_fondo_mesa);
        lb_fondo_mesa.setBounds(0, 0, 800, 600);
        this.repaint();
    }
    private void dibujarManoJugador(){
        for (JLabel obtenerImagenJugador: imagenesManoJugador){
            this.remove(obtenerImagenJugador);
        }
        imagenesManoJugador = new ArrayList<>();
        int cantidadCartasJugador = juegoSieteyMedia.getManoJugador().length;
        Carta[] manoJugador = juegoSieteyMedia.getManoJugador();
        int x = 10;
        int y = 340;
        int anchura = 80;
        int altura = 130;
        for (int i=0; i<cantidadCartasJugador; i++){
            String rutaImagen = "";
            if (manoJugador[i].isTapada()){
                rutaImagen = "/img/cartas/reverso.png";
            }else{
                rutaImagen = "/img/cartas/"+manoJugador[i].getPALO()+"_"+manoJugador[i].getNum()+".png";
            }
            JLabel nuevoLabel = new JLabel();
            nuevoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource(rutaImagen)));
            if (manoJugador[i].isTapada()){
                nuevoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        clickImagenCarta(evt);
                    }
                });
            }
            nuevoLabel.setBounds(x, y, anchura, altura);
            nuevoLabel.setVisible(true);
            x+= 90;
            imagenesManoJugador.add(nuevoLabel);
            getContentPane().add(imagenesManoJugador.get(i));
        }
    }
    private void dibujarManoBanca(){
        int cantidadCartasBanca = juegoSieteyMedia.getManoBanca().length;
        Carta[] manoBanca = juegoSieteyMedia.getManoBanca();
        int x = 10;
        int y = 130;
        int anchura = 80;
        int altura = 130;
        JLabel[] imagenesManoBanca = new JLabel[cantidadCartasBanca];
        
        for (int i=0; i<cantidadCartasBanca; i++){
            String rutaImagen = "/img/cartas/"+manoBanca[i].getPALO()+"_"+manoBanca[i].getNum()+".png";
            JLabel nuevoLabel = new JLabel();
            nuevoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource(rutaImagen)));
            imagenesManoBanca[i] = nuevoLabel;
            getContentPane().add(imagenesManoBanca[i]);
            imagenesManoBanca[i].setBounds(x, y, anchura, altura);
            x+= 90;
            imagenesManoBanca[i].setVisible(true);
        }
        actualizarPuntuacionBanca();
    }
    private void actualizarPuntacionJugador(){
        juegoSieteyMedia.calcularPuntuacionJugador();
        Carta[] manoJugador = juegoSieteyMedia.getManoJugador();
        Carta ultimaCarta =  juegoSieteyMedia.obtenerUltimaCartaValidaJugador();
        String textoUltimaCarta = "";
        if (manoJugador.length == 1){
            textoUltimaCarta = "Última carta: Ninguna sin destapar";
        }else{
            textoUltimaCarta = "Última carta: " + ultimaCarta.getNum() + " de " + 
                ultimaCarta.getPALO() + "s - " + ultimaCarta.getNumSieteyMedia() + " pts";
        }
        
        lb_jugador_valorUltimaCarta.setText(textoUltimaCarta);
        lb_jugador_valorTotal.setText("Valor total: " + juegoSieteyMedia.getPuntuacionJugador() + " pts");
    }
    private void actualizarPuntuacionBanca(){
        juegoSieteyMedia.calcularPuntuacionBanca();
        Carta[] manoBanca = juegoSieteyMedia.getManoBanca();
        Carta ultimaCarta =  manoBanca[manoBanca.length - 1];
        lb_banca_valorUltimaCarta.setText("Última carta: " + ultimaCarta.getNum() + " de " + 
                ultimaCarta.getPALO() + "s - " + ultimaCarta.getNumSieteyMedia() + " pts");
        lb_banca_valorTotal.setText("Valor total: " + juegoSieteyMedia.getPuntuacionBanca()+ " pts");
    }
    private void clickImagenCarta (java.awt.event.MouseEvent evt){
        if (juegoSieteyMedia.isPartidaTerminada() == false){
            juegoSieteyMedia.setDevolverValorCartaDestapada(true);
            actualizarPuntacionJugador();
            juegoSieteyMedia.destaparCartasJugador();
            dibujarManoJugador();
            dibujarManoBanca();
            juegoSieteyMedia.setDevolverValorCartaDestapada(false);

            actualizarPuntuacionBanca();
            ponerFondo();
            comprobarFindePartida();
        }
        
        
    }
    private void comprobarFindePartida(){
        if (juegoSieteyMedia.isPartidaTerminada() == false){
            if (juegoSieteyMedia.getPuntuacionJugador() > 7.5){
                System.out.println("pierde el jugador posibilidad nº1");
                juegoSieteyMedia.setPartidaTerminada(true);
                Derrota derrota = new Derrota();
                derrota.setVisible(true);
                this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            }else if(juegoSieteyMedia.getPuntuacionBanca() > 7.5 
                    && juegoSieteyMedia.getPuntuacionJugador() < juegoSieteyMedia.getPuntuacionBanca()){
                System.out.println("gana el jugador posibilidad nº2");
                juegoSieteyMedia.setPartidaTerminada(true);
                Victoria victoria = new Victoria();
                victoria.setVisible(true);
                this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            }else if (juegoSieteyMedia.getPuntuacionBanca() == 7.5){
                System.out.println("pierde el jugador posibilidad nº3");
                juegoSieteyMedia.setPartidaTerminada(true);
                Derrota derrota = new Derrota();
                derrota.setVisible(true);
                this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            }else if (juegoSieteyMedia.isBancaPlantada() && juegoSieteyMedia.isJugadorPlantado() 
                    && juegoSieteyMedia.getPuntuacionBanca()> juegoSieteyMedia.getPuntuacionJugador()){
                System.out.println("pierde el jugador posibilidad nº4");
                juegoSieteyMedia.setPartidaTerminada(true);
                Derrota derrota = new Derrota();
                derrota.setVisible(true);
                this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            }else if (juegoSieteyMedia.getPuntuacionJugador() == 7.5){
                System.out.println("gana el jugador posibilidad nº5");
                juegoSieteyMedia.setPartidaTerminada(true);
                Victoria victoria = new Victoria();
                victoria.setVisible(true);
                this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            }else if (juegoSieteyMedia.isBancaPlantada() && juegoSieteyMedia.isJugadorPlantado() 
                    && juegoSieteyMedia.getPuntuacionBanca()< juegoSieteyMedia.getPuntuacionJugador()){
                System.out.println("gana el jugador posibilidad nº6");
                juegoSieteyMedia.setPartidaTerminada(true);
                Victoria victoria = new Victoria();
                victoria.setVisible(true);
                this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            }
        }
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bt_robar = new javax.swing.JButton();
        bt_plantarse = new javax.swing.JButton();
        lb_banca_valorUltimaCarta = new javax.swing.JLabel();
        lb_banca_valorTotal = new javax.swing.JLabel();
        lb_jugador_valorTotal = new javax.swing.JLabel();
        lb_jugador_valorUltimaCarta = new javax.swing.JLabel();
        lb_imagen_jugador = new javax.swing.JLabel();
        lb_imagen_oponente = new javax.swing.JLabel();
        lb_fondo_mesa = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Siete y Media");
        setCursor(cursor_normal);
        setMaximumSize(new java.awt.Dimension(805, 627));
        setMinimumSize(new java.awt.Dimension(805, 627));
        setPreferredSize(new java.awt.Dimension(805, 627));
        setResizable(false);
        setSize(new java.awt.Dimension(805, 627));
        getContentPane().setLayout(null);

        bt_robar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/robar_off.png"))); // NOI18N
        bt_robar.setBorder(null);
        bt_robar.setBorderPainted(false);
        bt_robar.setContentAreaFilled(false);
        bt_robar.setCursor(cursor_clic);
        bt_robar.setFocusPainted(false);
        bt_robar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bt_robarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bt_robarMouseExited(evt);
            }
        });
        bt_robar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_robarActionPerformed(evt);
            }
        });
        getContentPane().add(bt_robar);
        bt_robar.setBounds(230, 280, 130, 50);

        bt_plantarse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/plantarse_off.png"))); // NOI18N
        bt_plantarse.setBorder(null);
        bt_plantarse.setBorderPainted(false);
        bt_plantarse.setContentAreaFilled(false);
        bt_plantarse.setCursor(cursor_clic);
        bt_plantarse.setFocusPainted(false);
        bt_plantarse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bt_plantarseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bt_plantarseMouseExited(evt);
            }
        });
        bt_plantarse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_plantarseActionPerformed(evt);
            }
        });
        getContentPane().add(bt_plantarse);
        bt_plantarse.setBounds(420, 280, 140, 50);

        lb_banca_valorUltimaCarta.setFont(new java.awt.Font("Calibri", 1, 28)); // NOI18N
        lb_banca_valorUltimaCarta.setForeground(new java.awt.Color(255, 255, 255));
        lb_banca_valorUltimaCarta.setText("Última carta: -");
        getContentPane().add(lb_banca_valorUltimaCarta);
        lb_banca_valorUltimaCarta.setBounds(320, 20, 460, 50);

        lb_banca_valorTotal.setFont(new java.awt.Font("Calibri", 1, 28)); // NOI18N
        lb_banca_valorTotal.setForeground(new java.awt.Color(255, 255, 255));
        lb_banca_valorTotal.setText("Valor total: -");
        getContentPane().add(lb_banca_valorTotal);
        lb_banca_valorTotal.setBounds(320, 70, 460, 50);

        lb_jugador_valorTotal.setFont(new java.awt.Font("Calibri", 1, 28)); // NOI18N
        lb_jugador_valorTotal.setForeground(new java.awt.Color(255, 255, 255));
        lb_jugador_valorTotal.setText("Valor total: -");
        getContentPane().add(lb_jugador_valorTotal);
        lb_jugador_valorTotal.setBounds(320, 530, 460, 50);

        lb_jugador_valorUltimaCarta.setFont(new java.awt.Font("Calibri", 1, 28)); // NOI18N
        lb_jugador_valorUltimaCarta.setForeground(new java.awt.Color(255, 255, 255));
        lb_jugador_valorUltimaCarta.setText("Última carta: -");
        getContentPane().add(lb_jugador_valorUltimaCarta);
        lb_jugador_valorUltimaCarta.setBounds(320, 480, 460, 50);

        lb_imagen_jugador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/jugador.png"))); // NOI18N
        getContentPane().add(lb_imagen_jugador);
        lb_imagen_jugador.setBounds(10, 470, 310, 120);

        lb_imagen_oponente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/banca.png"))); // NOI18N
        getContentPane().add(lb_imagen_oponente);
        lb_imagen_oponente.setBounds(10, 10, 310, 120);

        lb_fondo_mesa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondo_mesa.png"))); // NOI18N
        getContentPane().add(lb_fondo_mesa);
        lb_fondo_mesa.setBounds(0, 0, 800, 600);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bt_robarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_robarMouseEntered
        bt_robar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/robar_on.png"))); 
    }//GEN-LAST:event_bt_robarMouseEntered

    private void bt_robarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_robarMouseExited
        bt_robar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/robar_off.png"))); 
    }//GEN-LAST:event_bt_robarMouseExited

    private void bt_plantarseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_plantarseMouseEntered
        bt_plantarse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/plantarse_on.png"))); 
    }//GEN-LAST:event_bt_plantarseMouseEntered

    private void bt_plantarseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_plantarseMouseExited
        bt_plantarse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/plantarse_off.png"))); 
    }//GEN-LAST:event_bt_plantarseMouseExited

    private void bt_robarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_robarActionPerformed
        
        if (juegoSieteyMedia.isPartidaTerminada() == false){
             if (juegoSieteyMedia.isJugadorPlantado() == false){
                   juegoSieteyMedia.robarJugador(); 
                }
            if (juegoSieteyMedia.getPuntuacionBanca()> juegoSieteyMedia.getPuntuacionJugador() && juegoSieteyMedia.getPuntuacionBanca() <7.5 
                    && juegoSieteyMedia.getPuntuacionBanca()>= 5 && juegoSieteyMedia.isBancaPlantada() == false){
                juegoSieteyMedia.setBancaPlantada(true);
                JOptionPane.showMessageDialog(this, "Me planto!", "Mensaje de la banca", JOptionPane.INFORMATION_MESSAGE);
            }else if (juegoSieteyMedia.isBancaPlantada() == false){
                juegoSieteyMedia.robarBanca();
            }

            dibujarManoJugador();
            dibujarManoBanca();

            actualizarPuntacionJugador();
            actualizarPuntuacionBanca();
            ponerFondo();
            comprobarFindePartida();   
        }
        

    }//GEN-LAST:event_bt_robarActionPerformed
   
    private void bt_plantarseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_plantarseActionPerformed
        if (juegoSieteyMedia.isPartidaTerminada() == false){
           if (juegoSieteyMedia.isBancaPlantada() && juegoSieteyMedia.isJugadorPlantado() == false){
                int eleccion = JOptionPane.showConfirmDialog(this, "La banca ya está plantada, ¿seguro que quieres plantarte?", "Atención", JOptionPane.OK_CANCEL_OPTION);
                if (eleccion == JOptionPane.OK_OPTION){
                    juegoSieteyMedia.setJugadorPlantado(true);
                    comprobarFindePartida();
                }
            }else if (juegoSieteyMedia.isJugadorPlantado() == false){
                juegoSieteyMedia.setJugadorPlantado(true);
            }else if (juegoSieteyMedia.isJugadorPlantado()){
                JOptionPane.showMessageDialog(this, "El jugador ya está plantado", "Información", JOptionPane.INFORMATION_MESSAGE);
            } 
        }
        
        
    }//GEN-LAST:event_bt_plantarseActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_plantarse;
    private javax.swing.JButton bt_robar;
    private javax.swing.JLabel lb_banca_valorTotal;
    private javax.swing.JLabel lb_banca_valorUltimaCarta;
    private javax.swing.JLabel lb_fondo_mesa;
    private javax.swing.JLabel lb_imagen_jugador;
    private javax.swing.JLabel lb_imagen_oponente;
    private javax.swing.JLabel lb_jugador_valorTotal;
    private javax.swing.JLabel lb_jugador_valorUltimaCarta;
    // End of variables declaration//GEN-END:variables
   
   
}
