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
    private PanelMatrices panelMatrices;
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

        panelMatrices = new PanelMatrices();
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
     * inner class that extends an Adapter Class or implements Listeners used by GUI class
     */
    private class Escucha {

    }
}
