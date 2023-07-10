package proyect;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Clase PintarFlotaOponente
 * @version v.1.0.0 date:28/05/2023
 * @autor Kevin Jordan Alzate kevin.jordan@correounivalle.edu.co
 * @autor Junior Cantor Arevalo junior.cantor@correounivalle.edu.co
 * @author Johan Castro edier.castro@correounivalle.edu.co
 */
public class MostrarBarcosCPU {
    private PanelCPUTablero panelCPUTablero;
    private int cantidadPortavion;
    private int cantidadSubmarino;
    private int cantidadDestructor;
    private int cantidadFragata;
    private int barcoUsado;
    private ArrayList<Integer> casillasUsadasBarco;

    /**
     * Constructor de la clase PintarFlotaOponente
     */
    public MostrarBarcosCPU(PanelCPUTablero _panelCPUTablero){
        this.panelCPUTablero = _panelCPUTablero;
        cantidadPortavion = 1;
        cantidadSubmarino = 2;
        cantidadDestructor = 3;
        cantidadFragata = 4;
        barcoUsado = 1;
        casillasUsadasBarco = new ArrayList<>();
    }

    /**
     * Retorna la dirección de la imagen dependiendo del barco ingresado
     */
    public String pathImages(String barco, int estadoOrientacion, int estadoSentidoOrientacion){
        String path = "";
        if(estadoOrientacion == 0){
            switch(estadoSentidoOrientacion){
                case 1:
                    path = "/recursos/" + barco + "_V_S_I/";
                    break;
                case 2:
                    path = "/recursos/" + barco + "_V_I_S/";
                    break;
            }
        }else{
            switch(estadoSentidoOrientacion){
                case 3:
                    path = "/recursos/" + barco + "_H_I_D/";
                    break;
                case 4:
                    path = "/recursos/" + barco + "_H_D_I/";
                    break;
            }
        }
        return path;
    }

    /**
     * Relaciona la casilla y la cantidad de casillas que usa el barco ingresado
     */
    public void relacionJLabelBarco(JLabel casilla, String barco, int numeroBarco){
        if(barco.equals("portavion" + String.valueOf(numeroBarco))){
            casillasUsadasBarco.add(4);
            panelCPUTablero.getTableroOponente("posicion").getCasillaBarco().put(casilla, casillasUsadasBarco.get(casillasUsadasBarco.size()-1));
        }else{
            if(barco.equals("submarino" + String.valueOf(numeroBarco))){
                casillasUsadasBarco.add(3);
                panelCPUTablero.getTableroOponente("posicion").getCasillaBarco().put(casilla, casillasUsadasBarco.get(casillasUsadasBarco.size()-1));
            }else{
                if(barco.equals("destructor" + String.valueOf(numeroBarco))){
                    casillasUsadasBarco.add(2);
                    panelCPUTablero.getTableroOponente("posicion").getCasillaBarco().put(casilla, casillasUsadasBarco.get(casillasUsadasBarco.size()-1));
                }else{
                    if(barco.equals("fragata" + String.valueOf(numeroBarco))){
                        casillasUsadasBarco.add(1);
                        panelCPUTablero.getTableroOponente("posicion").getCasillaBarco().put(casilla, casillasUsadasBarco.get(casillasUsadasBarco.size()-1));
                    }
                }
            }
        }
    }

    /**
     * Pinta el barco en las respectivas casillas del tablero posición
     */
    public boolean funcionesFlota(String barco, int estadoOrientacion, int estadoSentidoOrientacion, int col, int row){
        int casillasAUsar; // Cantidad de casillas que ocupa el barco
        int casillasUsadas = 0; // Determina si las casillas siguientes están ocupadas para poder desplegar el barco
        int columnaReferencia = 0; // Es la columna de referencia dependiendo si es horizontal o vertical
        int filaReferencia = 0; // Es la fila de referencia dependiendo si es horizontal o vertical
        int nextImage; // Acumulador para mostrar las imágenes en orden
        boolean auxiliar = false; // false si no se puede colocar el barco, de lo contrario true

        if(barco == "portavion"){
            casillasAUsar = 4;
        }else{
            if(barco == "submarino"){
                casillasAUsar = 3;
            }else{
                if(barco == "destructor"){
                    casillasAUsar = 2;
                }else{
                    casillasAUsar = 1;
                }
            }
        }

        // Si la orientación es horizontal, solo puede usar dos sentidos
        if(estadoOrientacion == 1){
            if(estadoSentidoOrientacion == 3){
                columnaReferencia = 10;
            }else{
                if(estadoSentidoOrientacion == 4){
                    columnaReferencia = 1;
                }
            }

            int ultimasCasillas = Math.abs(col - columnaReferencia);
            if(ultimasCasillas < casillasAUsar-1){
                auxiliar = false;
            }else{
                if(estadoSentidoOrientacion == 3){
                    nextImage = 1;
                    // Determina si las casillas siguientes estan ocupadas ocupadas por otra nave o no
                    for(int casilla=col; casilla < col+casillasAUsar; casilla++){
                        if(panelCPUTablero.getTableroOponente("posicion").getCasillasOcupadas().get(panelCPUTablero.getTableroOponente("posicion").getMatriz()[row][casilla]) == Integer.valueOf(1)) {
                            casillasUsadas++;
                        }
                    }

                    // Si las casillas siguientes no estan ocupadas, se despliega el barco
                    if(casillasUsadas == 0){
                        for(int pic=col; pic < col+casillasAUsar; pic++){
                            panelCPUTablero.getTableroOponente("posicion").getMatriz()[row][pic].setIcon(new ImageIcon(getClass().getResource(pathImages(barco, estadoOrientacion, estadoSentidoOrientacion) + String.valueOf(nextImage) + ".png")));
                            panelCPUTablero.getTableroOponente("posicion").getCasillasOcupadas().put(panelCPUTablero.getTableroOponente("posicion").getMatriz()[row][pic], 1);
                            panelCPUTablero.getTableroOponente("posicion").getCasillaNombreBarco().put(panelCPUTablero.getTableroOponente("posicion").getMatriz()[row][pic], barco + String.valueOf(barcoUsado));
                            relacionJLabelBarco(panelCPUTablero.getTableroOponente("posicion").getMatriz()[row][pic], barco + String.valueOf(barcoUsado), barcoUsado);
                            nextImage++;
                            auxiliar = true;
                        }
                        barcoUsado++;
                    }else{
                        auxiliar = false;
                    }
                }else{
                    nextImage = casillasAUsar;
                    for(int casilla=col; casilla > col-casillasAUsar; casilla--){
                        if(panelCPUTablero.getTableroOponente("posicion").getCasillasOcupadas().get(panelCPUTablero.getTableroOponente("posicion").getMatriz()[row][casilla]) == Integer.valueOf(1)) {
                            casillasUsadas++;
                        }
                    }

                    if(casillasUsadas == 0){
                        for(int pic=col; pic > col-casillasAUsar; pic--){
                            panelCPUTablero.getTableroOponente("posicion").getMatriz()[row][pic].setIcon(new ImageIcon(getClass().getResource(pathImages(barco, estadoOrientacion, estadoSentidoOrientacion) + String.valueOf(nextImage) + ".png")));
                            panelCPUTablero.getTableroOponente("posicion").getCasillasOcupadas().put(panelCPUTablero.getTableroOponente("posicion").getMatriz()[row][pic], 1);
                            panelCPUTablero.getTableroOponente("posicion").getCasillaNombreBarco().put(panelCPUTablero.getTableroOponente("posicion").getMatriz()[row][pic], barco + String.valueOf(barcoUsado));
                            relacionJLabelBarco(panelCPUTablero.getTableroOponente("posicion").getMatriz()[row][pic], barco + String.valueOf(barcoUsado), barcoUsado);
                            nextImage--;
                            auxiliar = true;
                        }
                        barcoUsado++;
                    }else{
                        auxiliar = false;
                    }
                }
            }
        }else{
            if(estadoSentidoOrientacion == 1){
                filaReferencia = 10;
            }else{
                if(estadoSentidoOrientacion == 2){
                    filaReferencia = 1;
                }
            }

            int ultimasCasillas = Math.abs(row - filaReferencia);
            if(ultimasCasillas < casillasAUsar-1){
                auxiliar = false;
            }else{
                if(estadoSentidoOrientacion == 1){
                    nextImage = 1;
                    for(int casilla=row; casilla < row+casillasAUsar; casilla++){
                        if(panelCPUTablero.getTableroOponente("posicion").getCasillasOcupadas().get(panelCPUTablero.getTableroOponente("posicion").getMatriz()[casilla][col]) == Integer.valueOf(1)) {
                            casillasUsadas++;
                        }
                    }

                    if(casillasUsadas == 0){
                        for(int pic=row; pic < row+casillasAUsar; pic++){
                            panelCPUTablero.getTableroOponente("posicion").getMatriz()[pic][col].setIcon(new ImageIcon(getClass().getResource(pathImages(barco, estadoOrientacion, estadoSentidoOrientacion) + String.valueOf(nextImage) + ".png")));
                            panelCPUTablero.getTableroOponente("posicion").getCasillasOcupadas().put(panelCPUTablero.getTableroOponente("posicion").getMatriz()[pic][col], 1);
                            panelCPUTablero.getTableroOponente("posicion").getCasillaNombreBarco().put(panelCPUTablero.getTableroOponente("posicion").getMatriz()[pic][col], barco + String.valueOf(barcoUsado));
                            relacionJLabelBarco(panelCPUTablero.getTableroOponente("posicion").getMatriz()[pic][col], barco + String.valueOf(barcoUsado), barcoUsado);
                            nextImage++;
                            auxiliar = true;
                        }
                        barcoUsado++;
                    }else{
                        auxiliar = false;
                    }
                }else{
                    nextImage = casillasAUsar;
                    for(int casilla=row; casilla > row-casillasAUsar; casilla--){
                        if(panelCPUTablero.getTableroOponente("posicion").getCasillasOcupadas().get(panelCPUTablero.getTableroOponente("posicion").getMatriz()[casilla][col]) == Integer.valueOf(1)) {
                            casillasUsadas++;
                        }
                    }

                    if(casillasUsadas == 0){
                        for(int pic=row; pic > row-casillasAUsar; pic--){
                            panelCPUTablero.getTableroOponente("posicion").getMatriz()[pic][col].setIcon(new ImageIcon(getClass().getResource(pathImages(barco, estadoOrientacion, estadoSentidoOrientacion) + String.valueOf(nextImage) + ".png")));
                            panelCPUTablero.getTableroOponente("posicion").getCasillasOcupadas().put(panelCPUTablero.getTableroOponente("posicion").getMatriz()[pic][col], 1);
                            panelCPUTablero.getTableroOponente("posicion").getCasillaNombreBarco().put(panelCPUTablero.getTableroOponente("posicion").getMatriz()[pic][col], barco + String.valueOf(barcoUsado));
                            relacionJLabelBarco(panelCPUTablero.getTableroOponente("posicion").getMatriz()[pic][col], barco + String.valueOf(barcoUsado), barcoUsado);
                            nextImage--;
                            auxiliar = true;
                        }
                        barcoUsado++;
                    }else{
                        auxiliar = false;
                    }
                }
            }
        }
        return auxiliar;
    }

    /**
     * Cambia la cantidad disponible del barco ingresado
     */
    public void setCantidadBarco(String barco){
        if(barco.equals("portavion")){
            cantidadPortavion--;
        }else{
            if(barco.equals("submarino")) {
                cantidadSubmarino--;
            }else{
                if(barco.equals("destructor")) {
                    cantidadDestructor--;
                }else{
                    if(barco.equals("fragata")) {
                        cantidadFragata--;
                    }
                }
            }
        }
    }

    /**
     * Retorna la cantidad disponible del barco ingresado
     */
    public int getCantidadBarco(String barco){
        int cantidad = 0;
        if(barco.equals("portavion")){
            cantidad = cantidadPortavion;
        }else{
            if(barco.equals("submarino")) {
                cantidad = cantidadSubmarino;
            }else{
                if(barco.equals("destructor")) {
                    cantidad = cantidadDestructor;
                }else{
                    if(barco.equals("fragata")) {
                        cantidad = cantidadFragata;
                    }
                }
            }
        }
        return cantidad;
    }

    /**
     * Retorna la cantidad total de naves disponibles
     */
    public int cantidadTotalNaves(){
        return cantidadPortavion + cantidadSubmarino + cantidadDestructor + cantidadFragata;
    }
}