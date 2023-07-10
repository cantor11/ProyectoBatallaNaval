package proyect;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Clase GUI_Secundaria
 * @version v.1.0.0 date:28/05/2023
 * @autor Kevin Jordan Alzate kevin.jordan@correounivalle.edu.co
 * @autor Junior Cantor Arevalo junior.cantor@correounivalle.edu.co
 * @author Johan Castro edier.castro@correounivalle.edu.co
 */

public class GUIPosicionesCPU extends JFrame {
    public static final String PATH = "/recursos/";
    private PanelCPUTablero panelCPUTablero;
    private MostrarBarcosCPU mostrarBarcosCPU;
    private GUIBatallaNaval guiBatallaNavalPrincipal;
    private int contadorHundidos;
    private int estado; // 1 si continua, 2 si gana el oponente, de lo contrario 0

    /**
     * Constructor de la clase GUI_Secundaria
     */
    public GUIPosicionesCPU(GUIBatallaNaval _guiBatallaNavalPrincipal) {
        this.guiBatallaNavalPrincipal = _guiBatallaNavalPrincipal;
        contadorHundidos = 0;
        initGUI_Secundaria();
        setupJFrame();
    }

    /**
     * Este método se utiliza para inicializar la interfaz gráfica de la ventana secundaria
     */
    private void initGUI_Secundaria() {
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panelCentral = createPanel(Color.gray, BorderLayout.CENTER);

        panelCentral.setLayout(new GridBagLayout());

        panelCPUTablero = new PanelCPUTablero();
        mostrarBarcosCPU = new MostrarBarcosCPU(panelCPUTablero);
        panelCentral.add(panelCPUTablero);
    }

    private JPanel createPanel(Color color, Object layout) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        getContentPane().add(panel, layout);
        return panel;
    }

    /**
     * Este método se utiliza para configurar las propiedades de la ventana JFrame
     */
    private void setupJFrame() {
        setTitle("Batalla Naval");
        setUndecorated(false);
        setSize(460, 460);
        setResizable(false);
        setVisible(false);
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JPanel panelContenedor = new JPanel(new GridBagLayout());
        getContentPane().add(panelContenedor);

        panelContenedor.add(panelCPUTablero, gbc);
    }

    /**
     * Este método se utiliza para configurar las propiedades de la ventana JFrame
     */
    public void oponenteVsUsuario() {
        Random random = new Random();
        int row = random.nextInt(10) + 1;
        int col = random.nextInt(10) + 1;

        if (panelCPUTablero.getTableroOponente("principal").getCasillasOcupadas().get(panelCPUTablero.getTableroOponente("principal").getMatriz()[row][col]) == Integer.valueOf(1)) {
            // Verificar si la casilla seleccionada contiene un barco del usuario
            if (guiBatallaNavalPrincipal.getPanelTablero().getTablero("posicion").getCasillaBarco().get(guiBatallaNavalPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[row][col]) != Integer.valueOf(0)) {
                for (int num = 1; num < 11; num++) {
                    String barco = "";
                    // Obtener el nombre del barco en la casilla seleccionada
                    if (guiBatallaNavalPrincipal.getPanelTablero().getTablero("posicion").getCasillaNombreBarco().get(guiBatallaNavalPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[row][col]) != null) {
                        barco = (String) guiBatallaNavalPrincipal.getPanelTablero().getTablero("posicion").getCasillaNombreBarco().get(guiBatallaNavalPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[row][col]);
                    }
                    // Realizar acciones de combate según el barco seleccionado
                    if (barco.equals("portavion" + String.valueOf(num))) {
                        funcionesCombate(row, col, barco);
                        break;
                    } else if (barco.equals("submarino" + String.valueOf(num))) {
                        funcionesCombate(row, col, barco);
                        break;
                    } else if (barco.equals("destructor" + String.valueOf(num))) {
                        funcionesCombate(row, col, barco);
                        break;
                    } else if (barco.equals("fragata" + String.valueOf(num))) {
                        funcionesCombate(row, col, barco);
                        break;
                    }
                }
            }
        } else {
            if (panelCPUTablero.getTableroOponente("principal").getCasillasOcupadas().get(panelCPUTablero.getTableroOponente("principal").getMatriz()[row][col]) == Integer.valueOf(2)) {
                // Volver a intentar si la casilla ya fue atacada anteriormente
                oponenteVsUsuario();
            } else {
                // Actualizar casillas y estado si la casilla contiene agua
                panelCPUTablero.getTableroOponente("principal").getCasillasOcupadas().put(panelCPUTablero.getTableroOponente("principal").getMatriz()[row][col], Integer.valueOf(2));
                guiBatallaNavalPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/agua.png")));
                panelCPUTablero.getTableroOponente("principal").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/agua.png")));
                estado = 0;
            }
        }
    }

    /**
     * Este método realiza las acciones de combate cuando se ha tocado un barco
     */
    public void funcionesCombate(int row, int col, String barco) {
        guiBatallaNavalPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/tocado.png")));
        panelCPUTablero.getTableroOponente("principal").getCasillasOcupadas().replace(panelCPUTablero.getTableroOponente("principal").getMatriz()[row][col], Integer.valueOf(2));

        guiBatallaNavalPrincipal.getPanelTablero().getTablero("posicion").reducirCasillasUsadas(barco);

        if (guiBatallaNavalPrincipal.getPanelTablero().getTablero("posicion").getCasillaBarco().get(guiBatallaNavalPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[row][col]) == Integer.valueOf(0)) {
            contadorHundidos++;
            estado = 1;
            for (int fil = 1; fil < 11; fil++) {
                for (int colu = 1; colu < 11; colu++) {
                    String nombreBarco = (String) guiBatallaNavalPrincipal.getPanelTablero().getTablero("posicion").getCasillaNombreBarco().get(guiBatallaNavalPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[fil][colu]);
                    if (nombreBarco != null && nombreBarco.equals(barco)) {
                        guiBatallaNavalPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[fil][colu].setIcon(new ImageIcon(getClass().getResource("/recursos/hundido.png")));
                    }
                }
            }
        } else {
            estado = 1;
        }

        if (contadorHundidos == 10) {
            estado = 2;
        }
    }

    /**
     * Este método realiza la distribución aleatoria de la flota en el tablero del oponente
     */
    public void distribucionFlotaOponente() {
        Random random = new Random();
        String[] nombresBarcos = { "portavion", "submarino", "destructor", "fragata" };
        int numBarcoAleatorio = random.nextInt(4) + 1;
        String nombreBarco = nombresBarcos[numBarcoAleatorio - 1];

        int numOrientacionAleatoria = random.nextInt(2);
        int numSentidoAleatorio = numOrientacionAleatoria == 0 ? random.nextInt(2) + 1 : random.nextInt(2) + 3;
        int numColumnaAleatoria = random.nextInt(10) + 1;
        int numFilaAleatoria = random.nextInt(10) + 1;

        if (mostrarBarcosCPU.getCantidadBarco(nombreBarco) > 0) {
            if (!mostrarBarcosCPU.funcionesFlota(nombreBarco, numOrientacionAleatoria, numSentidoAleatorio, numColumnaAleatoria, numFilaAleatoria)) {
                distribucionFlotaOponente();
            } else {
                mostrarBarcosCPU.setCantidadBarco(nombreBarco);
            }
        }
    }

    /**
     * Este método retorna el panelTableroOponente
     */
    public PanelCPUTablero getPanelTableroOponente() {
        return panelCPUTablero;
    }

    /**
     * Este método retorna el panelTableroOponente
     */
    public MostrarBarcosCPU getPintarFlotaOponente() {
        return mostrarBarcosCPU;
    }

    /**
     * Este método retorna la variable estado
     */
    public int getEstado() {
        return estado;
    }
}
