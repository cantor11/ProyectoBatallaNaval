package proyect;

import javax.swing.*;
import java.awt.*;

/**
 * Clase PanelTablero
 * @version v.1.0.0 date:28/05/2023
 * @autor Kevin Jordan Alzate kevin.jordan@correounivalle.edu.co
 * @autor Junior Cantor Arevalo junior.cantor@correounivalle.edu.co
 * @author Johan Castro edier.castro@correounivalle.edu.co
 */
public class PanelMatrices extends JPanel {
    public static final String PATH = "/recursos/";
    private JLabel nombreTableroPosicion, nombreTableroPrincipal, imagenTiros;
    private BackgroundPane panelTableroPosicion, panelTableroPrincipal;
    private MatricesTableros tableroPosicion, tableroPrincipal;
    private String abecedario[];

    /**
     * Constructor de la clase PanelTablero
     */
    public PanelMatrices() {
        GridBagLayout gb = new GridBagLayout();
        this.setLayout(gb);
        this.setBackground(Color.black);
        tableroPosicion = new MatricesTableros();
        tableroPrincipal = new MatricesTableros();
        abecedario = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        iniciar();
        modelTablero();
    }

    /**
     * Establece la configuración inicial del JComponent
     */
    public void iniciar() {
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel tablero posición
        nombreTableroPosicion = new JLabel("Jugador");
        nombreTableroPosicion.setForeground(Color.white);
        nombreTableroPosicion.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(nombreTableroPosicion, gbc);

        panelTableroPosicion = new BackgroundPane();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 15, 0, 15);
        this.add(panelTableroPosicion, gbc);

        // Panel tablero principal
        nombreTableroPrincipal = new JLabel("Oponente(PC)");
        nombreTableroPrincipal.setForeground(Color.white);
        nombreTableroPrincipal.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(nombreTableroPrincipal, gbc);

        panelTableroPrincipal = new BackgroundPane();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 15, 0, 15);
        this.add(panelTableroPrincipal, gbc);

    }

    /**
     * JPanel para agregar las matrices
     */

    public class BackgroundPane extends JPanel {

        public BackgroundPane() {
            this.setLayout(new GridLayout(11, 11));
            this.setPreferredSize(new Dimension(400, 400));
            this.setBorder(BorderFactory.createLineBorder(Color.blue));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            //g.drawImage(img, 0, 0, this);
            revalidate();
            repaint();
        }
    }

    /**
     * Crea los tableros posición y principal
     */
    public void modelTablero() {
        for (int row = 0; row < 11; row++) {
            for (int col = 0; col < 11; col++) {
                if (row == 0 && col == 0) {
                    tableroPosicion.getMatriz()[row][col] = new JLabel();
                    tableroPosicion.getMatriz()[row][col].setOpaque(true);
                    tableroPosicion.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    tableroPosicion.getMatriz()[row][col].setBackground(Color.WHITE);

                    tableroPrincipal.getMatriz()[row][col] = new JLabel();
                    tableroPrincipal.getMatriz()[row][col].setOpaque(true);
                    tableroPrincipal.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    tableroPrincipal.getMatriz()[row][col].setBackground(Color.WHITE);
                } else {
                    if (row == 0 && col > 0) {
                        tableroPosicion.getMatriz()[row][col] = new JLabel(abecedario[col - 1], SwingConstants.CENTER);
                        tableroPosicion.getMatriz()[row][col].setOpaque(true);
                        tableroPosicion.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        tableroPosicion.getMatriz()[row][col].setBackground(Color.WHITE);

                        tableroPrincipal.getMatriz()[row][col] = new JLabel(abecedario[col - 1], SwingConstants.CENTER);
                        tableroPrincipal.getMatriz()[row][col].setOpaque(true);
                        tableroPrincipal.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        tableroPrincipal.getMatriz()[row][col].setBackground(Color.WHITE);
                    } else {
                        if (row > 0 && col == 0) {
                            tableroPosicion.getMatriz()[row][col] = new JLabel(String.valueOf(row), SwingConstants.CENTER);
                            tableroPosicion.getMatriz()[row][col].setOpaque(true);
                            tableroPosicion.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                            tableroPosicion.getMatriz()[row][col].setBackground(Color.WHITE);

                            tableroPrincipal.getMatriz()[row][col] = new JLabel(String.valueOf(row), SwingConstants.CENTER);
                            tableroPrincipal.getMatriz()[row][col].setOpaque(true);
                            tableroPrincipal.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                            tableroPrincipal.getMatriz()[row][col].setBackground(Color.WHITE);
                        } else {
                            tableroPosicion.getMatriz()[row][col] = new JLabel();
                            tableroPosicion.getMatriz()[row][col].setOpaque(false);
                            tableroPosicion.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                            tableroPrincipal.getMatriz()[row][col] = new JLabel();
                            tableroPrincipal.getMatriz()[row][col].setOpaque(false);
                            tableroPrincipal.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        }
                    }
                }

                panelTableroPosicion.add(tableroPosicion.getMatriz()[row][col]);
                panelTableroPrincipal.add(tableroPrincipal.getMatriz()[row][col]);
            }
        }
    }

    /**
     * Retorna el tablero ingresado
     *
     * @param _tablero
     * @return Tableros
     */
    public MatricesTableros getTablero(String _tablero) {
        MatricesTableros tablero = new MatricesTableros();
        if (_tablero.equals("posicion")) {
            tablero = tableroPosicion;
        } else {
            if (_tablero.equals("principal")) {
                tablero = tableroPrincipal;
            }
        }
        return tablero;
    }
}