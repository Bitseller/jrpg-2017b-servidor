package servidor;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import mensajeria.PaqueteMensaje;
import mensajeria.PaqueteMovimiento;
import mensajeria.PaquetePersonaje;
import persistencia.hibernate.HibernateUtil;
import properties.Idioma;
import properties.PropiedadesComunicacion;

/**
 * The Class Servidor que se inicia para correr los clientes.
 */
public class Servidor extends Thread {

    private static final int POS_X_SEGUNDO_BOTON = 360;

    private static final int POS_X_PRIMER_BOTON = 220;

    private static final int FUENTE_LOG = 13;

    private static final int FUENTE_TITULO = 16;

    private static final int POS_Y_SEGUNDA_ETIQUETA = 40;

    private static final int POS_X_PRIMER_ETIQUETA = 10;

    private static final int ANCHO_ETIQUETA = 200;

    private static final int ALTO_ETIQUETA = 30;

    private static final int ANCHO_BOTON = 100;

    private static ArrayList<EscuchaCliente> clientesConectados = new ArrayList<>();

    private static Map<Integer, PaqueteMovimiento> ubicacionPersonajes = new HashMap<>();
    private static Map<Integer, PaquetePersonaje> personajesConectados = new HashMap<>();

    private static NPCadmin NPCs;

    private static Thread server;

    private static ServerSocket serverSocket;
    private static Conector conexionDB;

    private static final int ANCHO = 700;
    private static final int ALTO = 640;

    private static final int POS_Y_BOTONES_ABAJO = ALTO - 70;
    private static final int MARCO_LOG = 25;
    private static final int ALTO_LOG = 520;
    private static final int ANCHO_LOG = ANCHO - MARCO_LOG;

    public static JTextArea log;

    public static AtencionConexiones atencionConexiones;
    public static AtencionMovimientos atencionMovimientos;

    /**
     * The main method.
     * 
     * @param args
     *            parametros
     */
    public static void main(final String[] args) {
        Idioma.setIdiomaEspaniol();
        cargarInterfaz();
    }

    /**
     * Carga la interfaz del server.
     */
    private static void cargarInterfaz() {
        JFrame ventana = new JFrame(Idioma.getIdioma().getProperty("SERVIDOR_TITLE"));
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(ANCHO, ALTO);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        ventana.setLayout(null);
        ventana.setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/java/servidor/server.png"));
        JLabel titulo = new JLabel(Idioma.getIdioma().getProperty("SERVIDOR_TITLE"));
        titulo.setFont(new Font("Courier New", Font.BOLD, FUENTE_TITULO));
        titulo.setBounds(POS_X_PRIMER_ETIQUETA, 0, ANCHO_ETIQUETA, ALTO_ETIQUETA);
        ventana.add(titulo);

        log = new JTextArea();
        log.setEditable(false);
        log.setFont(new Font("Times New Roman", Font.PLAIN, FUENTE_LOG));
        JScrollPane scroll = new JScrollPane(log, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(POS_X_PRIMER_ETIQUETA, POS_Y_SEGUNDA_ETIQUETA, ANCHO_LOG, ALTO_LOG);
        ventana.add(scroll);

        final JButton botonIniciar = new JButton();
        final JButton botonDetener = new JButton();
        botonIniciar.setText(Idioma.getIdioma().getProperty("BTN_INICIAR"));
        botonIniciar.setBounds(POS_X_PRIMER_BOTON, POS_Y_BOTONES_ABAJO, ANCHO_BOTON, ALTO_ETIQUETA);
        botonIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                server = new Thread(new Servidor());
                server.start();
                botonIniciar.setEnabled(false);
                botonDetener.setEnabled(true);
            }
        });

        ventana.add(botonIniciar);

        botonDetener.setText(Idioma.getIdioma().getProperty("BTN_DETENER"));
        botonDetener.setBounds(POS_X_SEGUNDO_BOTON, POS_Y_BOTONES_ABAJO, ANCHO_BOTON, ALTO_ETIQUETA);
        botonDetener.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    server.stop();
                    atencionConexiones.stop();
                    atencionMovimientos.stop();
                    for (EscuchaCliente cliente : clientesConectados) {
                        cliente.getSalida().close();
                        cliente.getEntrada().close();
                        cliente.getSocket().close();
                    }
                    serverSocket.close();
                    appendLog(Idioma.getIdioma().getProperty("SERVIDOR_DETENIDO_CON_EXITO"));
                } catch (IOException e1) {
                    appendLog(Idioma.getIdioma().getProperty("SERVIDOR_ERROR_AL_DETENER"));
                }
                if (conexionDB != null) {
                    HibernateUtil.cerrarSessionFactory();
                    //         conexionDB.close();
                }
                botonDetener.setEnabled(false);
                botonIniciar.setEnabled(true);
            }
        });
        botonDetener.setEnabled(false);
        ventana.add(botonDetener);

        ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent evt) {
                if (serverSocket != null) {
                    try {
                        server.stop();
                        atencionConexiones.stop();
                        atencionMovimientos.stop();
                        for (EscuchaCliente cliente : clientesConectados) {
                            cliente.getSalida().close();
                            cliente.getEntrada().close();
                            cliente.getSocket().close();
                        }
                        serverSocket.close();
                        appendLog(Idioma.getIdioma().getProperty("SERVIDOR_DETENIDO_CON_EXITO"));
                    } catch (IOException e) {
                        appendLog(Idioma.getIdioma().getProperty("SERVIDOR_ERROR_AL_DETENER"));
                        System.exit(1);
                    }
                }
                if (conexionDB != null) {
                    HibernateUtil.cerrarSessionFactory();// conexionDB.close();
                }
                System.exit(0);
            }
        });

        ventana.setVisible(true);
    }

    @Override
    public void run() {
        try {

            conexionDB = new Conector();
            //   conexionDB.connect();

            appendLog(Idioma.getIdioma().getProperty("SERVIDOR_INICIANDO"));
            serverSocket = new ServerSocket(PropiedadesComunicacion.getPuertoServidor());
            appendLog(Idioma.getIdioma().getProperty("SERVIDOR_ECUCHANDO"));
            String ipRemota;

            atencionConexiones = new AtencionConexiones();
            atencionMovimientos = new AtencionMovimientos();

            atencionConexiones.start();
            atencionMovimientos.start();

            NPCs = new NPCadmin("mapaSolides.txt");
            NPCs.cargarPrimerosNPCS();

            while (true) {
                Socket cliente = serverSocket.accept();
                ipRemota = cliente.getInetAddress().getHostAddress();
                appendLog(ipRemota + " online");

                ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());

                EscuchaCliente atencion = new EscuchaCliente(ipRemota, cliente, entrada, salida);
                atencion.start();
                clientesConectados.add(atencion);
            }
        } catch (Exception e) {
            appendLog(Idioma.getIdioma().getProperty("SERVIDOR_ERROR_CONEXION"));
        }
        HibernateUtil.eliminarSessionFactory();

    }

    /**
     * Mensaje A un usuario.
     *
     * @param pqm
     *            paquete que contiene el mensaje
     * @return true, si el usuario existe
     */
    public static boolean mensajeAUsuario(final PaqueteMensaje pqm) {
        boolean encontrado = false;

        Iterator<Entry<Integer, PaquetePersonaje>> it = personajesConectados.entrySet().iterator();

        while (it.hasNext() && !encontrado) {
            Map.Entry<Integer, PaquetePersonaje> personaje = it.next();
            if (personaje.getValue().getNombre().equals(pqm.getUserReceptor())) {
                encontrado = true;
            }
        }

        // Si existe inicio sesion
        if (encontrado) {
            appendLog(pqm.getUserEmisor() + " " + Idioma.getIdioma().getProperty("SERVIDOR_COMUNICACION_ENTRE") + " "
                + pqm.getUserReceptor());
            return true;
        } else {
            // Si no existe informo y devuelvo false
            appendLog(Idioma.getIdioma().getProperty("SERVIDOR_ERR_ENVIAR_MSJ_USR_DECON") + " " + pqm
                .getUserReceptor());
            return false;
        }
    }

    /**
     * Append log.
     *
     * @param text
     *            texto a a�adir
     */
    public static void appendLog(final String text) {
        log.append(text + System.lineSeparator());
    }

    /**
     * Verifica si se envio mensaje a todos los clientes conectados.
     *
     * @param contador
     *            la cantidad de clientes en el server
     * @return true, si hay inicio de sesion
     */
    public static boolean mensajeAAll(final int contador) {
        // Si existe inicio sesion
        if (personajesConectados.size() == contador + 1) {
            appendLog(Idioma.getIdioma().getProperty("SERVIDOR_MENSAJE_ALL"));
            return true;
        } else {
            // Si no existe informo y devuelvo false
            appendLog(Idioma.getIdioma().getProperty("SERVIDOR_USUARIO_DESCONECTADO"));
        }
        return false;
    }

    /**
     * Gets the clientes conectados.
     *
     * @return the clientes conectados
     */
    public static ArrayList<EscuchaCliente> getClientesConectados() {
        return clientesConectados;
    }

    /**
     * Gets the ubicacion NPCs.
     *
     * @return the ubicacion NPCs
     */

    /**
     * Gets the ubicacion personajes.
     *
     * @return the ubicacion personajes
     */
    public static Map<Integer, PaqueteMovimiento> getUbicacionPersonajes() {
        return ubicacionPersonajes;
    }

    /**
     * Gets the personajes conectados.
     *
     * @return the personajes conectados
     */
    public static Map<Integer, PaquetePersonaje> getPersonajesConectados() {
        return personajesConectados;
    }

    /**
     * Sets the ubicacion personajes.
     *
     * @param ubicacionPersonajes
     *            the ubicacion personajes
     */
    public static void setUbicacionPersonajes(final Map<Integer, PaqueteMovimiento> ubicacionPersonajes) {
        Servidor.ubicacionPersonajes = ubicacionPersonajes;
    }

    /**
     * Gets the conector.
     *
     * @return the conector
     */
    public static Conector getConector() {
        return conexionDB;
    }

    /**
     * Gets the NP cs.
     *
     * @return the NP cs
     */
    public static NPCadmin getNPCs() {
        return NPCs;
    }

    /**
     * Sets the NP cs.
     *
     * @param nPCs
     *            the new NP cs
     */
    public static void setNPCs(final NPCadmin nPCs) {
        NPCs = nPCs;
    }

}
