package proyect;
/**
 * Clase Batalla
 * @version v.1.0.0 date:28/05/2023
 * @autor Kevin Jordan Alzate kevin.jordan@correounivalle.edu.co
 * @autor Junior Cantor Arevalo junior.cantor@correounivalle.edu.co
 * @author Johan Castro edier.castro@correounivalle.edu.co
 */
public class Batalla {
    private PanelMatrices panelMatrices;
    private PanelCPUTablero panelCPUTablero;

    /**
     * Constructor de la clase Combate
     * @param _panelMatrices
     * @param _panelCPUTablero
     */
    public Batalla(PanelMatrices _panelMatrices, PanelCPUTablero _panelCPUTablero){
        this.panelMatrices = _panelMatrices;
        this.panelCPUTablero = _panelCPUTablero;
    }

    /**
     * Busca las casillas ocupadas por naves del tablero posición del oponente y las marca en el tablero principal del usuario
     */
    public void usuarioVsOponente(){
        for(int row = 1; row < 11; row++) {
            for (int col = 1; col < 11; col++) {
                if(panelCPUTablero.getTableroOponente("posicion").getCasillasOcupadas().get(panelCPUTablero.getTableroOponente("posicion").getMatriz()[row][col]) == Integer.valueOf(1)){
                    panelMatrices.getTablero("principal").getCasillasOcupadas().put(panelMatrices.getTablero("principal").getMatriz()[row][col], 1);
                }
            }
        }
    }

    /**
     * Busca las casillas ocupadas por naves del tablero posición del usuario y las marca en el tablero principal del oponente
     */
    public void oponenteVsUsuario(){
        for(int row = 1; row < 11; row++) {
            for (int col = 1; col < 11; col++) {
                if(panelMatrices.getTablero("posicion").getCasillasOcupadas().get(panelMatrices.getTablero("posicion").getMatriz()[row][col]) == Integer.valueOf(1)){
                    panelCPUTablero.getTableroOponente("principal").getCasillasOcupadas().put(panelCPUTablero.getTableroOponente("principal").getMatriz()[row][col], 1);
                }
            }
        }
    }
}
