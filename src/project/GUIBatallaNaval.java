package proyect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Clase principal GUI que representa la interfaz gráfica del juego Batalla Naval.
 *
 * @version v.1.0.0 date:28/05/2023
 * @autor Kevin Jordan Alzate kevin.jordan@correounivalle.edu.co
 * @autor Junior Cantor Arevalo junior.cantor@correounivalle.edu.co
 * @author Johan Castro edier.castro@correounivalle.edu.co
 */
public class GUIBatallaNaval extends JFrame {

    private Header headerProject;

    public static final String PATH ="/recursos/";
    public static final String AYUDA ="Bienvenido a Batalla Naval\n"+
            "\nEl objetivo del juego es ser el primero en hundir los barcos del oponente. \n"+"\nCada jugador tiene 2 tableros compuesto por 10 filas y 10 columnas: \n"+
            "\n- Tablero de posición: Representa tu territorio, en él distribuirás tu flota antes de comenzar la partida y sólo será de observación.\n Verás la posición de tus barcos y los disparos de tu oponente en tu territorio, pero no podrás realizar ningún cambio ni disparo en él. \n"+
            "\n- Tablero principal: Representa el territorio del enemigo, donde tiene desplegada su flota. Será aquí donde se desarrollen\n los movimientos (disparos) del jugador tratando de hundir los barcos enemigos. Este tablero aparecerá en la pantalla del jugador una vez comience la\n partida y en él quedarán registrados todos sus movimientos, reflejando tanto los disparos al agua como los barcos tocados y hundidos hasta el momento. \n"
            +"\nCada jugador tiene una flota de 9 barcos de diferente tamaño, por lo que cada uno ocupará un número determinado de casillas en el tablero: \n"+
            "\n• 1 portaaviones: ocupa 4 casillas "+"\n• 2 submarinos: ocupan 3 casillas cada uno."+"\n• 3 destructores: ocupan 2 casillas cada uno "+"\n• 4 fragatas: ocupan 1 casilla cada uno "
            +"\n\nCada barco puede ser ubicado de manera horizontal haciendo click izquierdo o click derecho para la manera vertical en el tablero de posición. "+"\n\nTerminología y movimientos: \n\n"+"• Agua: cuando se dispara sobre una casilla donde no está colocado ningún barco enemigo.\n En el tablero principal del jugador aparecerá una X. Pasa el turno a tu oponente."+
            "\n• Tocado: cuando se dispara en una casilla en la que está ubicado un barco enemigo que ocupa 2 o más casillas y se destruye sólo una parte del barco.\n En el tablero del jugador aparecerá esa parte del barco con una marca indicativa de que ha sido tocado. El jugador vuelve a disparar. "
            +"\n• Hundido: si se dispara en una casilla en la que está ubicado una fragata (1 casilla) u otro barco con el resto de casillas tocadas, se habrá hundido,\n es decir, se ha eliminado ese barco del juego. Aparecerá en el tablero principal del jugador, el barco completo con la marca indicativa de que ha sido hundido.\n El jugador puede volver a disparar, siempre y cuando no hayas hundido toda la flota de su enemigo, en cuyo caso habrá ganado. ";

    private JButton ayuda, creditos, Jugar, PosicionesCPU,reiniciar;
    private Escucha escucha;
    private ImageIcon infoSentidos;
    private JPanel panelNorte, panelSur, panelEste, panelCentro;
    private proyect.PanelMatrices panelMatrices;
    private MostrarBarcos mostrarBarcos;
    private PanelAsignaciones panelAsignaciones;
    private GUIPosicionesCPU ventanaOponente;
    private int estadoJuego; // 1 seleccionar barco, 2 seleccionar orientacion del barco, 3 seleccionar sentido del barco, 4 colocar barco en el tablero, 5 combate, 6 turno del oponente
    private Batalla batalla;
    private int contadorHundidos; // Contador de barcos hundidos
    private Timer timer; // establece el tiempo que tarde el oponente en escoger casilla
    private Image image;
    private ComenzarPartidaListener comenzarPartidaListener;
    private EscuchaPosiciones escuchaPosiciones;
    private int orientacion = 1;
    private int sentidoOrientacion = 3;
    private int barcoVinculadoCount = 0;
    private String barcosList[] = new String[]{"portavion", "submarino", "submarino" , "destructor", "destructor", "destructor", "fragata", "fragata", "fragata", "fragata"};

    /**
     * Constructor de la clase GUI.
     */
    public GUIBatallaNaval() {
        initGUI();

        // Configuración del JFrame
        this.setTitle("Batalla Naval");
        this.setIconImage(image);
        this.setUndecorated(false);
        this.setSize(1500,750);
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Método utilizado para configurar los componentes de la interfaz gráfica.
     */
    private void initGUI() {
        // Creación de la ventana del oponente
        ventanaOponente = new GUIPosicionesCPU(this);
        comenzarPartidaListener = new ComenzarPartidaListener();
        escuchaPosiciones = new EscuchaPosiciones();

        // Set up JFrame Container's Layout
        panelNorte = createPanel(Color.DARK_GRAY);
        panelSur = createPanel(Color.DARK_GRAY);
        panelEste = createPanel(Color.GRAY);
        panelCentro = new JPanel();

        panelSur.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 5));
        panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 5));
        panelEste.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 60));

        panelSur.setPreferredSize(new Dimension(100, 90));
        panelNorte.setPreferredSize(new Dimension(100, 60));
        panelEste.setPreferredSize(new Dimension(1500, 20));

        this.add(panelNorte, BorderLayout.NORTH);
        this.add(panelSur, BorderLayout.SOUTH);
        this.add(panelEste, BorderLayout.CENTER);

        estadoJuego = 1;
        escucha = new Escucha();


        headerProject = createHeader("BATALLA NAVAL", Color.DARK_GRAY, 40);
        panelNorte.add(headerProject, FlowLayout.LEFT);

        ayuda = createButton("Ayuda", escucha, 40, Color.PINK);
        panelSur.add(ayuda, FlowLayout.LEFT);

        panelMatrices = new proyect.PanelMatrices();
        panelEste.add(panelMatrices);

        Jugar = createButton("Jugar", comenzarPartidaListener, 40, Color.PINK);
        panelSur.add(Jugar, FlowLayout.LEFT);

        PosicionesCPU = createButton("Posiciones CPU", escucha, 25, Color.PINK);
        panelEste.add(PosicionesCPU, FlowLayout.CENTER);

        while (ventanaOponente.getPintarFlotaOponente().cantidadTotalNaves() > 0) {
            ventanaOponente.distribucionFlotaOponente();
        }

        batalla = new Batalla(panelMatrices, ventanaOponente.getPanelTableroOponente());
        contadorHundidos = 0;
        mostrarBarcos = new MostrarBarcos(panelMatrices, panelAsignaciones);
        timer = new Timer(2000, escucha);
    }

    /**
     * Método utilizado para configurar el JFrame.
     */
    private void setupJFrame() {
        this.setTitle("Batalla Naval");
        this.setIconImage(image);
        this.setUndecorated(false);
        this.setSize(1500, 750);
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Método auxiliar utilizado para crear un JPanel con un color de fondo dado.
     */
    private JPanel createPanel(Color color) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        return panel;
    }

    /**
     * Método auxiliar utilizado para crear un objeto Header con un texto, color y tamaño de fuente dados.
     */
    private Header createHeader(String text, Color color, int fontSize) {
        Header header = new Header(text, color);
        header.setFont(new Font("MONOSPACED", Font.BOLD, fontSize));
        return header;
    }

    /**
     * Método auxiliar utilizado para crear un botón con un texto, listener, tamaño de fuente y color de fondo dados.
     */
    private JButton createButton(String text, ActionListener listener, int fontSize, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("MONOSPACED", Font.BOLD, fontSize));
        button.setForeground(Color.red);
        button.setBackground(backgroundColor);
        button.setFocusable(false);
        button.setBorder(null);
        button.addActionListener(listener);
        return button;
    }

    /**
     * Main process of the Java program
     *
     * @param args Object used in order to send input data from command line when
     *             the program is execute by console.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GUIBatallaNaval miProjectGUIBatallaNaval = new GUIBatallaNaval();
        });
    }


    /**
     * Clase que implementa ActionListener y se utiliza para manejar el evento de comenzar la partida.
     */
    private class ComenzarPartidaListener implements ActionListener {

        /**
         * Método que se ejecuta cuando se produce el evento de comenzar la partida.
         * Realiza las acciones necesarias para iniciar el juego.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            headerProject.setText("¡Posiciona tus naves! ");
            for (JLabel[] row : panelMatrices.getTablero("posicion").getMatriz()) {
                for (JLabel casilla : row) {
                    casilla.addMouseListener(escuchaPosiciones);
                }
            }
            Jugar.setEnabled(false);
        }

    }

    /**
     * Clase que implementa MouseListener y se utiliza para manejar los eventos de clic y movimiento del mouse en las casillas del tablero.
     */
    private class EscuchaPosiciones implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {}

        /**
         * Método que se ejecuta cuando se presiona el botón del mouse.
         */
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                orientacion = 1 - orientacion;
                sentidoOrientacion = orientacion == 1 ? 3 : 2;
            } else if (e.getButton() == MouseEvent.BUTTON1) {
                if (barcoVinculadoCount < barcosList.length) {
                    for (int row = 1; row < 11; row++) {
                        for (int col = 1; col < 11; col++) {
                            if (e.getSource() == panelMatrices.getTablero("posicion").getMatriz()[row][col]) {
                                mostrarBarcos.posicionarBarco(barcosList[barcoVinculadoCount], col, row, orientacion);
                                barcoVinculadoCount++;
                                if (barcoVinculadoCount >= barcosList.length) {
                                    comienzaLaGuerra();
                                }
                            }
                        }
                    }
                } else {
                    comienzaLaGuerra();
                }
            }
        }

        /**
         * Método que se ejecuta cuando se suelta el botón del mouse.
         */
        @Override
        public void mouseReleased(MouseEvent e) {}

        /**
         * Método que se ejecuta cuando el puntero del mouse entra en una casilla del tablero.
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            if (barcoVinculadoCount < barcosList.length) {
                for (int row = 1; row < 11; row++) {
                    for (int col = 1; col < 11; col++) {
                        mostrarBarcos.removerIcon(col, row);
                        if (e.getSource() == panelMatrices.getTablero("posicion").getMatriz()[row][col]) {
                            if (mostrarBarcos.funcionesFlota(barcosList[barcoVinculadoCount], orientacion, sentidoOrientacion, col, row)) {
                                break;
                            }
                        }
                    }
                }
            }
            repaint();
            revalidate();
        }

        /**
         * Método que se ejecuta cuando el puntero del mouse sale de una casilla del tablero.
         */
        @Override
        public void mouseExited(MouseEvent e) {}

        /**
         * Método que inicia la etapa de combate de la partida.
         * Realiza las acciones necesarias para iniciar la fase de ataque.
         */
        public void comienzaLaGuerra() {
            headerProject.setText("¡Hora del ataque! ");
            for (JLabel[] row : panelMatrices.getTablero("posicion").getMatriz()) {
                for (JLabel casilla : row) {
                    casilla.removeMouseListener(escuchaPosiciones);
                }
            }
        }

    }

    /**
     * Método que establece los escuchadores de eventos en las casillas del tablero.
     */
    public void setEscuchaCasillas(String evento) {
        boolean agregar = evento.equals("agregar");
        String tablero = agregar ? "posicion" : "principal";
        for (JLabel[] row : panelMatrices.getTablero(tablero).getMatriz()) {
            for (JLabel casilla : row) {
                if (agregar) {
                    casilla.addMouseListener(escucha);
                } else {
                    casilla.removeMouseListener(escucha);
                }
            }
        }
    }

    /**
     * Método que establece los escuchadores de eventos en las casillas del tablero principal.
     *
     * @param evento El evento que se va a manejar.
     */
    public void setEscuchaCasillasPrincipal(String evento) {
        setEscuchaCasillas(evento.equals("agregar") ? "agregar" : "remover");
    }

    /**
     * Método que maneja las funciones de combate cuando se realiza un ataque a una casilla.
     *
     * @param row   La fila de la casilla atacada.
     * @param col   La columna de la casilla atacada.
     * @param barco El nombre del barco atacado.
     */
    public void funcionesCombate(int row, int col, String barco) {
        ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/tocado.png")));
        panelMatrices.getTablero("principal").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/tocado.png")));
        panelMatrices.getTablero("principal").getCasillasOcupadas().replace(panelMatrices.getTablero("principal").getMatriz()[row][col], 2);
        ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").reducirCasillasUsadas(barco);
        if (ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getCasillaBarco().get(ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getMatriz()[row][col]).equals(Integer.valueOf(0)))
        {
            estadoJuego = 5;
            contadorHundidos++;
            for (int fil = 1; fil < 11; fil++) {
                for (int colu = 1; colu < 11; colu++) {
                    String nombreBarco = (String) ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getCasillaNombreBarco().get(ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getMatriz()[fil][colu]);

                    if (nombreBarco != null && nombreBarco.equals(barco)) {
                        ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getMatriz()[fil][colu].setIcon(new ImageIcon(getClass().getResource("/recursos/hundido.png")));
                        panelMatrices.getTablero("principal").getMatriz()[fil][colu].setIcon(new ImageIcon(getClass().getResource("/recursos/hundido.png")));
                    }
                }
            }
        } else {
            estadoJuego = 5;
        }

        if (contadorHundidos == 10) {
            setEscuchaCasillasPrincipal("remover");
        }
    }

    /**
     * Método que devuelve el panelTablero utilizado en el juego.
     */
    public proyect.PanelMatrices getPanelTablero() {
        return panelMatrices;
    }

    /**
     * Clase que implementa ActionListener y MouseListener y se utiliza para manejar los eventos de los botones y casillas del juego.
     */
    private class Escucha implements ActionListener, MouseListener {

        /**
         * Método que se ejecuta cuando se produce un evento de acción.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == ayuda) {
                JOptionPane.showMessageDialog(null, AYUDA, "¿Cómo se juega batalla naval?", JOptionPane.PLAIN_MESSAGE);
            } else if (e.getSource() == Jugar) {
                Jugar.removeActionListener(this);
                panelAsignaciones.getAsignarTurno().setText("¡Tu turno!");
                panelAsignaciones.getInformacionJuego().setText("Selecciona la nave que quieres desplegar");
            } else if (e.getSource() == PosicionesCPU) {
                ventanaOponente.setVisible(true);
            } else if (e.getSource() == panelAsignaciones.getExplicacionBotones()) {
                JOptionPane.showMessageDialog(null, "", "Cómo jugar", JOptionPane.PLAIN_MESSAGE, infoSentidos);
            } else if (estadoJuego == 6 && e.getSource() == timer) {
                ventanaOponente.oponenteVsUsuario();
                if (ventanaOponente.getEstado() == 0) {
                    timer.stop();
                    estadoJuego = 5;
                    panelAsignaciones.getAsignarTurno().setText("Tu turno");
                    panelAsignaciones.getInformacionJuego().setText("Selecciona otra casilla del tablero principal");
                } else if (ventanaOponente.getEstado() == 2) {
                    timer.stop();
                    panelAsignaciones.getInformacionJuego().setText("Tus barcos han sido hundidos, perdiste el juego");
                }
            }
        }

        /**
         * Método que se ejecuta cuando se produce el evento de clic del mouse.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            int auxiliar = 0;
            switch (estadoJuego) {
                case 1:
                    if (e.getSource() == panelAsignaciones.getBotonBarco("portavion")) {
                        if (panelAsignaciones.getCantidadBarco("portavion") > 0) {
                            panelAsignaciones.setCantidadBarco("portavion");
                            panelAsignaciones.getInformacionJuego().setText("Escoge si quieres ubicarlo vertical u horizontal");
                            panelAsignaciones.setNombreBoton("portavion");
                            estadoJuego = 2;
                        } else {
                            panelAsignaciones.getInformacionJuego().setText("No hay más portaviones disponibles");
                        }
                    } else if (e.getSource() == panelAsignaciones.getBotonBarco("destructor")) {
                        if (panelAsignaciones.getCantidadBarco("destructor") > 0) {
                            panelAsignaciones.setCantidadBarco("destructor");
                            panelAsignaciones.getInformacionJuego().setText("Escoge si quieres ubicarlo vertical u horizontal");
                            panelAsignaciones.setNombreBoton("destructor");
                            estadoJuego = 2;
                        } else {
                            panelAsignaciones.getInformacionJuego().setText("No hay más destructores disponibles");
                        }
                    } else if (e.getSource() == panelAsignaciones.getBotonBarco("fragata")) {
                        if (panelAsignaciones.getCantidadBarco("fragata") > 0) {
                            panelAsignaciones.setCantidadBarco("fragata");
                            panelAsignaciones.getInformacionJuego().setText("Escoge si quieres ubicarlo vertical u horizontal");
                            panelAsignaciones.setNombreBoton("fragata");
                            estadoJuego = 2;
                        } else {
                            panelAsignaciones.getInformacionJuego().setText("No hay más fragatas disponibles");
                        }
                    } else if (e.getSource() == panelAsignaciones.getBotonBarco("submarino")) {
                        if (panelAsignaciones.getCantidadBarco("submarino") > 0) {
                            panelAsignaciones.setCantidadBarco("submarino");
                            panelAsignaciones.getInformacionJuego().setText("Escoge si quieres ubicarlo vertical u horizontal");
                            panelAsignaciones.setNombreBoton("submarino");
                            estadoJuego = 2;
                        } else {
                            panelAsignaciones.getInformacionJuego().setText("No hay más submarinos disponibles");
                        }
                    }
                    break;
                case 2:
                    if (e.getSource() == panelAsignaciones.getBotonOrientacion("vertical")) {
                        panelAsignaciones.getInformacionJuego().setText("Escoge cual sentido quieres usar");
                        panelAsignaciones.setOrientacion(0);
                        estadoJuego = 3;
                    } else if (e.getSource() == panelAsignaciones.getBotonOrientacion("horizontal")) {
                        panelAsignaciones.getInformacionJuego().setText("Escoge cual sentido quieres usar");
                        panelAsignaciones.setOrientacion(1);
                        estadoJuego = 3;
                    }
                    break;
                case 3:
                    if (e.getSource() == panelAsignaciones.getBotonSentidoOrientacion("sup_inf")) {
                        panelAsignaciones.getInformacionJuego().setText("Selecciona la casilla en la que quieres ubicar la nave");
                        setEscuchaCasillas("agregar");
                        panelAsignaciones.setSentidoOrientacion(1);
                        estadoJuego = 4;
                    } else if (e.getSource() == panelAsignaciones.getBotonSentidoOrientacion("inf_sup")) {
                        panelAsignaciones.getInformacionJuego().setText("Selecciona la casilla en la que quieres ubicar la nave");
                        setEscuchaCasillas("agregar");
                        panelAsignaciones.setSentidoOrientacion(2);
                        estadoJuego = 4;
                    } else if (e.getSource() == panelAsignaciones.getBotonSentidoOrientacion("izq_der")) {
                        panelAsignaciones.getInformacionJuego().setText("Selecciona la casilla en la que quieres ubicar la nave");
                        setEscuchaCasillas("agregar");
                        panelAsignaciones.setSentidoOrientacion(3);
                        estadoJuego = 4;
                    } else if (e.getSource() == panelAsignaciones.getBotonSentidoOrientacion("der_izq")) {
                        panelAsignaciones.getInformacionJuego().setText("Selecciona la casilla en la que quieres ubicar la nave");
                        setEscuchaCasillas("agregar");
                        panelAsignaciones.setSentidoOrientacion(4);
                        estadoJuego = 4;
                    }
                    break;
            }
        }
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
    }
}
