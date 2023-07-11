package proyect;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Clase PintarFlota
 * @version v.1.0.0 date:28/05/2023
 * @autor Kevin Jordan Alzate kevin.jordan@correounivalle.edu.co
 * @autor Junior Cantor Arevalo junior.cantor@correounivalle.edu.co
 * @author Johan Castro edier.castro@correounivalle.edu.co
 */

/**
 * Clase que se encarga de pintar los barcos en el tablero.
 */
public class MostrarBarcos {
    private PanelMatrices panelMatrices;
    private PanelAsignaciones panelAsignaciones;
    private int barcoUsado; // Acumulador para identificar cuál nave ha sido desplegada (en orden del 1 al 10)
    private ArrayList<Integer> casillasUsadasBarco; // casillas usadas por cada nave

    /**
     * Constructor de la clase PintarFlota.
     */
    public MostrarBarcos(PanelMatrices _panelMatrices, PanelAsignaciones _panelAsignaciones) {
        panelMatrices = _panelMatrices;
        panelAsignaciones = _panelAsignaciones;
        barcoUsado = 1;
        casillasUsadasBarco = new ArrayList<>();
    }

    /**
     * Retorna la dirección de la imagen dependiendo del barco ingresado.
     */
    public String pathImages(String barco, int estadoOrientacion, int estadoSentidoOrientacion) {
        String path = "";
        if (estadoOrientacion == 0) {
            switch (estadoSentidoOrientacion) {
                case 1:
                    path = "/recursos/" + barco + "_V_S_I/";
                    break;
                case 2:
                    path = "/recursos/" + barco + "_V_I_S/";
                    break;
            }
        } else {
            switch (estadoSentidoOrientacion) {
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
     * Relaciona la casilla y la cantidad de casillas que usa el barco ingresado.
     */
    public void relacionJLabelBarco(JLabel casilla, String barco, int numeroBarco) {
        int casillasUsadas;
        switch (barco) {
            case "portavion":
                casillasUsadas = 4;
                break;
            case "submarino":
                casillasUsadas = 3;
                break;
            case "destructor":
                casillasUsadas = 2;
                break;
            case "fragata":
                casillasUsadas = 1;
                break;
            default:
                casillasUsadas = 0;
                break;
        }
        casillasUsadasBarco.add(casillasUsadas);
        panelMatrices.getTablero("posicion").getCasillaBarco().put(casilla, casillasUsadasBarco.get(casillasUsadasBarco.size() - 1));
    }

    /**
     * Pinta el barco en las respectivas casillas del tablero posición.
     */
    public boolean funcionesFlota(String barco, int estadoOrientacion, int estadoSentidoOrientacion, int col, int row) {
        int casillasAUsar;
        int casillasUsadas = 0;
        int columnaReferencia = 0;
        int filaReferencia = 0;
        int nextImage;
        boolean auxiliar = false; // falso si no pudo colocar el barco, de lo contrario verdadero

        switch (barco) {
            case "portavion":
                casillasAUsar = 4;
                break;
            case "submarino":
                casillasAUsar = 3;
                break;
            case "destructor":
                casillasAUsar = 2;
                break;
            default:
                casillasAUsar = 1;
                break;
        }

        if (estadoOrientacion == 1) {
            if (estadoSentidoOrientacion == 3) {
                columnaReferencia = 10;
            } else if (estadoSentidoOrientacion == 4) {
                columnaReferencia = 1;
            }

            int ultimasCasillas = Math.abs(col - columnaReferencia);
            if (ultimasCasillas < casillasAUsar - 1) {
                // NO hay donde poner el barco
            } else {
                if (estadoSentidoOrientacion == 3) {
                    nextImage = 1;
                    for (int casilla = col; casilla < col + casillasAUsar; casilla++) {
                        if (panelMatrices.getTablero("posicion").getCasillasOcupadas().get(panelMatrices.getTablero("posicion").getMatriz()[row][casilla]) == Integer.valueOf(1)) {
                            casillasUsadas++;
                        }
                    }

                    if (casillasUsadas == 0) {
                        for (int pic = col; pic < col + casillasAUsar; pic++) {
                            panelMatrices.getTablero("posicion").getMatriz()[row][pic].setIcon(new ImageIcon(getClass().getResource(pathImages(barco, estadoOrientacion, estadoSentidoOrientacion) + String.valueOf(nextImage) + ".png")));
                            nextImage++;
                            auxiliar = true;
                        }
                        barcoUsado++;
                    } else {
                        // no hay donde poner el barco
                    }
                } else {
                    nextImage = casillasAUsar;
                    for (int casilla = col; casilla > col - casillasAUsar; casilla--) {
                        if (panelMatrices.getTablero("posicion").getCasillasOcupadas().get(panelMatrices.getTablero("posicion").getMatriz()[row][casilla]) == Integer.valueOf(1)) {
                            casillasUsadas++;
                        }
                    }

                    if (casillasUsadas == 0) {
                        for (int pic = col; pic > col - casillasAUsar; pic--) {
                            panelMatrices.getTablero("posicion").getMatriz()[row][pic].setIcon(new ImageIcon(getClass().getResource(pathImages(barco, estadoOrientacion, estadoSentidoOrientacion) + String.valueOf(nextImage) + ".png")));
                            nextImage--;
                            auxiliar = true;
                        }
                        barcoUsado++;
                    } else {
                        // no hay donde poner el barco
                    }
                }
            }
        } else {
            if (estadoSentidoOrientacion == 1) {
                filaReferencia = 10;
            } else if (estadoSentidoOrientacion == 2) {
                filaReferencia = 1;
            }

            int ultimasCasillas = Math.abs(row - filaReferencia);
            if (ultimasCasillas < casillasAUsar - 1) {
                // no hay donde poner el barco
            } else {
                if (estadoSentidoOrientacion == 1) {
                    nextImage = 1;
                    for (int casilla = row; casilla < row + casillasAUsar; casilla++) {
                        if (panelMatrices.getTablero("posicion").getCasillasOcupadas().get(panelMatrices.getTablero("posicion").getMatriz()[casilla][col]) == Integer.valueOf(1)) {
                            casillasUsadas++;
                        }
                    }

                    if (casillasUsadas == 0) {
                        for (int pic = row; pic < row + casillasAUsar; pic++) {
                            panelMatrices.getTablero("posicion").getMatriz()[pic][col].setIcon(new ImageIcon(getClass().getResource(pathImages(barco, estadoOrientacion, estadoSentidoOrientacion) + String.valueOf(nextImage) + ".png")));
                            nextImage++;
                            auxiliar = true;
                        }
                        barcoUsado++;
                    } else {
                        // no hay donde poner el barco
                    }
                } else {
                    nextImage = casillasAUsar;
                    for (int casilla = row; casilla > row - casillasAUsar; casilla--) {
                        if (panelMatrices.getTablero("posicion").getCasillasOcupadas().get(panelMatrices.getTablero("posicion").getMatriz()[casilla][col]) == Integer.valueOf(1)) {
                            casillasUsadas++;
                        }
                    }

                    if (casillasUsadas == 0) {
                        for (int pic = row; pic > row - casillasAUsar; pic--) {
                            panelMatrices.getTablero("posicion").getMatriz()[pic][col].setIcon(new ImageIcon(getClass().getResource(pathImages(barco, estadoOrientacion, estadoSentidoOrientacion) + String.valueOf(nextImage) + ".png")));
                            nextImage--;
                            auxiliar = true;
                        }
                        barcoUsado++;
                    } else {
                        // no hay donde poner el barco
                    }
                }
            }
        }
        return auxiliar;
    }

    /**
     * Remueve el ícono de una casilla en el tablero.
     */
    public void removerIcon(int col, int row) {
        if (panelMatrices.getTablero("posicion").getCasillasOcupadas().get(panelMatrices.getTablero("posicion").getMatriz()[row][col]) != Integer.valueOf(1)) {
            panelMatrices.getTablero("posicion").getMatriz()[row][col].setIcon(null);
        }
    }

    /**
     * Posiciona un barco en el tablero.
     */
    public void posicionarBarco(String barco, int col, int row, int orientacion) {
        int casillasAUsar;
        switch (barco) {
            case "portavion":
                casillasAUsar = 4;
                break;
            case "submarino":
                casillasAUsar = 3;
                break;
            case "destructor":
                casillasAUsar = 2;
                break;
            default:
                casillasAUsar = 1;
                break;
        }
        if (orientacion == 1) {
            for (int casilla = col; casilla < col + casillasAUsar; casilla++) {
                if (panelMatrices.getTablero("posicion").getCasillasOcupadas().get(panelMatrices.getTablero("posicion").getMatriz()[row][casilla]) != Integer.valueOf(1)) {
                    panelMatrices.getTablero("posicion").getCasillasOcupadas().put(panelMatrices.getTablero("posicion").getMatriz()[row][casilla], 1);
                    panelMatrices.getTablero("posicion").getCasillaNombreBarco().put(panelMatrices.getTablero("posicion").getMatriz()[row][casilla], barco + String.valueOf(barcoUsado));
                }
            }
        } else {
            for (int casilla = row; casilla > row - casillasAUsar; casilla--) {
                if (panelMatrices.getTablero("posicion").getCasillasOcupadas().get(panelMatrices.getTablero("posicion").getMatriz()[casilla][col]) != Integer.valueOf(1)) {
                    panelMatrices.getTablero("posicion").getCasillasOcupadas().put(panelMatrices.getTablero("posicion").getMatriz()[casilla][col], 1);
                    panelMatrices.getTablero("posicion").getCasillaNombreBarco().put(panelMatrices.getTablero("posicion").getMatriz()[casilla][col], barco + String.valueOf(barcoUsado));
                }
            }
        }
    }
}
