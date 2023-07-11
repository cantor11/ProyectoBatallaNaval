package proyect;

import javax.swing.*;
import java.awt.*;

/**
 * Clase PanelTableroOponente
 * @autor Mayra Alejandra Sanchez - mayra.alejandra.sanchez@correounivalle.edu.co - 202040506
 * @autor Brayan Stiven Sanchez - brayan.sanchez.leon@correounivalle.edu.co - 202043554
 * @version 1.0.0 fecha 19/3/2022
 */

/**
 * Clase que representa el panel del tablero del oponente.
 * @version v.1.0.0 date:28/05/2023
 * @autor Kevin Jordan Alzate kevin.jordan@correounivalle.edu.co
 * @autor Junior Cantor Arevalo junior.cantor@correounivalle.edu.co
 * @author Johan Castro edier.castro@correounivalle.edu.co
 */
public class PanelCPUTablero extends JPanel {
    private BackgroundPane panelTableroPosicion;
    private JLabel nombreTableroPosicion;
    private MatricesTableros tableroPosicionOponente, tableroPrincipalOponente;
    private String abecedario[];

    /**
     * Constructor de la clase PanelTableroOponente.
     * Inicializa los componentes del panel y crea los tableros.
     */
    public PanelCPUTablero() {
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        tableroPosicionOponente = new MatricesTableros();
        tableroPrincipalOponente = new MatricesTableros();
        abecedario = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        iniciar();
        modelTableroOponente();
    }

    /**
     * Establece la configuración inicial del panel.
     * Agrega el nombre del tablero y el panel del tablero de posición.
     */
    public void iniciar() {
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel tablero posición
        nombreTableroPosicion = new JLabel("POSICIONES DEL OPONENTE");
        nombreTableroPosicion.setForeground(Color.white);
        nombreTableroPosicion.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(nombreTableroPosicion, gbc);

        panelTableroPosicion = new BackgroundPane();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 15, 0, 15);
        add(panelTableroPosicion, gbc);
    }

    /**
     * JPanel para agregar las matrices del tablero.
     */
    public class BackgroundPane extends JPanel {
        public BackgroundPane() {
            setLayout(new GridLayout(11, 11));
            setPreferredSize(new Dimension(400, 400));
            setBorder(BorderFactory.createLineBorder(Color.BLUE));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            revalidate();
            repaint();
        }
    }

    /**
     * Crea los tableros de posición y principal del oponente.
     */
    public void modelTableroOponente() {
        for (int row = 0; row < 11; row++) {
            for (int col = 0; col < 11; col++) {
                if (row == 0 && col == 0 || row == 0 && col > 0 || row > 0 && col == 0) {
                    JLabel labelPosicion = new JLabel();
                    labelPosicion.setOpaque(true);
                    labelPosicion.setBackground(Color.WHITE);
                    labelPosicion.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    tableroPosicionOponente.getMatriz()[row][col] = labelPosicion;

                    JLabel labelPrincipal = new JLabel();
                    labelPrincipal.setOpaque(true);
                    labelPrincipal.setBackground(Color.WHITE);
                    labelPrincipal.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    tableroPrincipalOponente.getMatriz()[row][col] = labelPrincipal;
                } else {
                    if (row == 0 && col > 0) {
                        JLabel labelPosicion = new JLabel(abecedario[col - 1], SwingConstants.CENTER);
                        labelPosicion.setOpaque(true);
                        labelPosicion.setBackground(Color.WHITE);
                        labelPosicion.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        tableroPosicionOponente.getMatriz()[row][col] = labelPosicion;

                        JLabel labelPrincipal = new JLabel(abecedario[col - 1], SwingConstants.CENTER);
                        labelPrincipal.setOpaque(true);
                        labelPrincipal.setBackground(Color.WHITE);
                        labelPrincipal.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        tableroPrincipalOponente.getMatriz()[row][col] = labelPrincipal;
                    } else if (row > 0 && col == 0) {
                        JLabel labelPosicion = new JLabel(String.valueOf(row), SwingConstants.CENTER);
                        labelPosicion.setOpaque(true);
                        labelPosicion.setBackground(Color.WHITE);
                        labelPosicion.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        tableroPosicionOponente.getMatriz()[row][col] = labelPosicion;

                        JLabel labelPrincipal = new JLabel(String.valueOf(row), SwingConstants.CENTER);
                        labelPrincipal.setOpaque(true);
                        labelPrincipal.setBackground(Color.WHITE);
                        labelPrincipal.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        tableroPrincipalOponente.getMatriz()[row][col] = labelPrincipal;
                    } else {
                        JLabel labelPosicion = new JLabel();
                        labelPosicion.setOpaque(false);
                        labelPosicion.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        tableroPosicionOponente.getMatriz()[row][col] = labelPosicion;

                        JLabel labelPrincipal = new JLabel();
                        labelPrincipal.setOpaque(false);
                        labelPrincipal.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        tableroPrincipalOponente.getMatriz()[row][col] = labelPrincipal;
                    }
                }
                panelTableroPosicion.add(tableroPosicionOponente.getMatriz()[row][col]);
            }
        }
    }

    /**
     * Retorna el tablero del oponente especificado.
     */
    public MatricesTableros getTableroOponente(String tipoTablero) {
        if (tipoTablero.equals("posicion")) {
            return tableroPosicionOponente;
        } else if (tipoTablero.equals("principal")) {
            return tableroPrincipalOponente;
        } else {
            return null;
        }
    }
}
