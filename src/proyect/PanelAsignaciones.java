package proyect;

import javax.swing.*;
import java.awt.*;

/**
 * Clase PanelFlota
 * @version v.1.0.0 date:28/05/2023
 * @autor Kevin Jordan Alzate kevin.jordan@correounivalle.edu.co
 * @autor Junior Cantor Arevalo junior.cantor@correounivalle.edu.co
 * @author Johan Castro edier.castro@correounivalle.edu.co
 */

/**
 * Clase PanelFlota que representa el panel de la flota de barcos en el juego Batalla Naval.
 */
public class PanelAsignaciones extends JPanel {
    private JButton portavion, destructor, fragata, submarino, vertical, horizontal, sup_inf, inf_sup, izq_der, der_izq, explicacionBotones;
    private JLabel asignarTurno;
    private JTextPane informacionJuego;
    private String nombreBoton;
    private int orientacion;
    private int sentidoOrientacion;
    private int cantidadPortavion;
    private int cantidadSubmarino;
    private int cantidadDestructor;
    private int cantidadFragata;

    /**
     * Constructor de la clase PanelFlota.
     */
    public PanelAsignaciones() {
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        this.setBackground(Color.gray);
        this.setPreferredSize(new Dimension(600, 100));
    }

    /**
     * Retorna el botón del barco especificado
     * @param barco El nombre del barco
     * @return El botón correspondiente al barco
     */
    public JButton getBotonBarco(String barco) {
        JButton boton = new JButton();
        if (barco.equals("portavion")) {
            boton = portavion;
        } else if (barco.equals("submarino")) {
            boton = submarino;
        } else if (barco.equals("destructor")) {
            boton = destructor;
        } else if (barco.equals("fragata")) {
            boton = fragata;
        }
        return boton;
    }

    /**
     * Retorna el botón de orientación especificado
     * @param orientacion La orientación ("vertical" o "horizontal")
     * @return El botón correspondiente a la orientación
     */
    public JButton getBotonOrientacion(String orientacion) {
        JButton boton = new JButton();
        if (orientacion.equals("vertical")) {
            boton = vertical;
        } else if (orientacion.equals("horizontal")) {
            boton = horizontal;
        }
        return boton;
    }

    /**
     * Retorna el botón del sentido de la orientación especificado
     * @param sentido El sentido de la orientación ("sup_inf", "inf_sup", "izq_der" o "der_izq")
     * @return El botón correspondiente al sentido de la orientación
     */
    public JButton getBotonSentidoOrientacion(String sentido) {
        JButton boton = new JButton();
        if (sentido.equals("sup_inf")) {
            boton = sup_inf;
        } else if (sentido.equals("inf_sup")) {
            boton = inf_sup;
        } else if (sentido.equals("izq_der")) {
            boton = izq_der;
        } else if (sentido.equals("der_izq")) {
            boton = der_izq;
        }
        return boton;
    }

    /**
     * Guarda el nombre del botón presionado en un string
     * @param nombreBoton El nombre del botón
     */
    public void setNombreBoton(String nombreBoton) {
        this.nombreBoton = nombreBoton;
    }

    /**
     * Retorna el nombre del barco que se presionó
     * @return El nombre del barco
     */
    public String getNombreBoton() {
        return nombreBoton;
    }

    /**
     * Asigna el estado de orientación
     * @param orientacion El estado de orientación
     */
    public void setOrientacion(int orientacion) {
        this.orientacion = orientacion;
    }

    /**
     * Asigna el estado de sentidoOrientacion
     * @param sentidoOrientacion El estado de sentidoOrientacion
     */
    public void setSentidoOrientacion(int sentidoOrientacion) {
        this.sentidoOrientacion = sentidoOrientacion;
    }


    /**
     * Reduce la cantidad disponible del barco ingresado
     * @param barco El nombre del barco
     */
    public void setCantidadBarco(String barco) {
        if (barco.equals("portavion")) {
            cantidadPortavion--;
        } else if (barco.equals("submarino")) {
            cantidadSubmarino--;
        } else if (barco.equals("destructor")) {
            cantidadDestructor--;
        } else if (barco.equals("fragata")) {
            cantidadFragata--;
        }
    }

    /**
     * Retorna la cantidad disponible del barco ingresado
     * @param barco El nombre del barco
     * @return La cantidad disponible del barco
     */
    public int getCantidadBarco(String barco) {
        int cantidad = 0;
        if (barco.equals("portavion")) {
            cantidad = cantidadPortavion;
        } else if (barco.equals("submarino")) {
            cantidad = cantidadSubmarino;
        } else if (barco.equals("destructor")) {
            cantidad = cantidadDestructor;
        } else if (barco.equals("fragata")) {
            cantidad = cantidadFragata;
        }
        return cantidad;
    }

    /**
     * Retorna el JTextPane para editar la información del juego
     * @return El JTextPane para editar la información del juego
     */
    public JTextPane getInformacionJuego() {
        return informacionJuego;
    }

    /**
     * Retorna el JLabel que edita el turno
     * @return El JLabel que edita el turno
     */
    public JLabel getAsignarTurno() {
        return asignarTurno;
    }

    /**
     * Retorna el botón que explica la dinámica de los botones
     * @return El botón que explica la dinámica de los botones
     */
    public JButton getExplicacionBotones() {
        return explicacionBotones;
    }
}

