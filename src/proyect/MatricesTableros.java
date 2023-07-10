package proyect;

import javax.swing.*;
import java.util.HashMap;

/**
 * Clase Tableros
 * @version v.1.0.0 date:28/05/2023
 * @autor Kevin Jordan Alzate kevin.jordan@correounivalle.edu.co
 * @autor Junior Cantor Arevalo junior.cantor@correounivalle.edu.co
 * @author Johan Castro edier.castro@correounivalle.edu.co
 */
public class MatricesTableros {
    private JLabel tablero[][];
    private HashMap<JLabel, Integer> casillasOcupadas; // Verifica si la casilla esta ocupada por una nave, 1 si esta ocupado, 2 si fue atacado
    private HashMap<JLabel, Integer> casillaBarco; // Relacion entre la casilla y las casillas que usa la nave
    private HashMap<JLabel, String> casillaNombreBarco; // Almacena el nombre del barco ubicado en la casilla

    /**
     * Constructor de la clase Tableros
     */
    public MatricesTableros(){
        tablero = new JLabel[11][11];
        casillasOcupadas = new HashMap<>();
        casillaBarco = new HashMap<>();
        casillaNombreBarco = new HashMap<>();
    }

    /**
     * Reduce la cantidad de casillas ocupadas por los barcos para que sea hundido
     * @param barco
     */
    public void reducirCasillasUsadas(String barco){
        for (int row = 1; row < 11; row++) {
            for (int col = 1; col < 11; col++) {
                if(casillaNombreBarco.get(tablero[row][col]) != null){
                    if(casillaNombreBarco.get(tablero[row][col]).equals(barco)){
                        casillaBarco.replace(tablero[row][col], casillaBarco.get(tablero[row][col])-1);
                    }
                }
            }
        }
    }

    /**
     * Retorna la matriz
     * @return JLabel[][]
     */
    public JLabel[][] getMatriz(){
        return tablero;
    }

    /**
     * Retorna el map casillasOcupadas
     * @return HashMap
     */
    public HashMap getCasillasOcupadas(){
        return casillasOcupadas;
    }

    /**
     * Retorna el map casillaBarco
     * @return HashMap
     */
    public HashMap getCasillaBarco(){
        return casillaBarco;
    }

    /**
     * Retorna el map casillaNombreBarco
     * @return HashMap
     */
    public HashMap getCasillaNombreBarco(){
        return casillaNombreBarco;
    }
}