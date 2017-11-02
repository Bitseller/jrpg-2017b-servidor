package servidor;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import mensajeria.PaqueteDeNPC;
import mensajeria.PaqueteMensaje;
import mensajeria.PaqueteMovimiento;
import mensajeria.PaquetePersonaje;
import mundo.Tile;
import properties.PropiedadesComunicacion;

/**
 * The Class Servidor que se inicia para correr los clientes
 */
public class Servidor extends Thread {

    private static final int POS_X_SEGUNDO_BOTON = 360;

    private static final int POS_X_PRIMER_BOTON = 220;

    private static final int FUENTE_LOG = 13;

    private static final int FUENTE_TITULO = 16;

    private static final int POS_Y_SEGUNDA_ETIQUETA = 40;

    private static final int POS_X_PRIMER_ETIQUETA = 10;

    private static final int ANCHO_ETIQUETA = 200;

    private static final int CANTIDAD_NPC = 10;

    private static final int ALTO_ETIQUETA = 30;

    private static final int ANCHO_BOTON = 100;

    private static final int RANDOM_POS_DESDE = 10;

    private static final double RANDOM_POS = 0.707;

    private static final int RANDOM_HASTA = 500;

    private static ArrayList<EscuchaCliente> clientesConectados = new ArrayList<>();

    private static Map<Integer, PaqueteMovimiento> ubicacionPersonajes = new HashMap<>();
    private static Map<Integer, PaquetePersonaje> personajesConectados = new HashMap<>();
    private static Map<Integer, PaqueteMovimiento> ubicacionNPCs = new HashMap<>();
    private static Map<Integer, PaqueteDeNPC> NPCs = new HashMap<>();
    private static boolean[][] matMapa;
    private static int altoMapas[] = new int[2];
	private static int anchoMapas[] = new int[2];
    

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
     * @param args parametros
     */
    public static void main(final String[] args) {
        cargarInterfaz();
    }

    /**
     * Carga la interfaz del server.
     */
    private static void cargarInterfaz() {
        JFrame ventana = new JFrame("Servidor WOME");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(ANCHO, ALTO);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        ventana.setLayout(null);
        ventana.setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/java/servidor/server.png"));
        JLabel titulo = new JLabel("Log del servidor...");
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
        botonIniciar.setText("Iniciar");
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

        botonDetener.setText("Detener");
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
                    appendLog("El servidor se ha detenido.");
                } catch (IOException e1) {
                    appendLog("Fallo al intentar detener el servidor.");
                }
                if (conexionDB != null) {
                    conexionDB.close();
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
                        appendLog("El servidor se ha detenido.");
                    } catch (IOException e) {
                        appendLog("Fallo al intentar detener el servidor.");
                        System.exit(1);
                    }
                }
                if (conexionDB != null) {
                    conexionDB.close();
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
            conexionDB.connect();

            appendLog("Iniciando el servidor...");
            serverSocket = new ServerSocket(PropiedadesComunicacion.getPuertoServidor());
            appendLog("Servidor esperando conexiones...");
            String ipRemota;

            atencionConexiones = new AtencionConexiones();
            atencionMovimientos = new AtencionMovimientos();

            atencionConexiones.start();
            atencionMovimientos.start();
            
            cargarMapa("mapaSolides.txt");

            for (int i = 0; i < CANTIDAD_NPC; i++) { // crea 10 NPCs en posiciones randoms
            	
            //for (int i = 0; i < 10; i++) {
            //int i = 0; //////////////
            	PaqueteDeNPC paqueteDeNPC = new PaqueteDeNPC(i);
            	
            	    
            	/*int xx, yy;
            	
            	
                float x = (float)  Math.random() * RANDOM_HASTA;
                float y = (float) Math.random() * RANDOM_HASTA;
                
                int a =(int)x / 64;
                		int b =(int)y / 64 ;

            	
                 while( matMapa[a][b] )
            	{
            		
	                x = (float)  Math.random() * RANDOM_HASTA;
	                y = (float) Math.random() * RANDOM_HASTA;
	                
	                a =(int)x / 64;
            		 b =(int)y / 64 ;
            	}
                 
                 //xx = (int) (RANDOM_POS_DESDE + ((int)(x) * RANDOM_POS) - ((int)(y) * RANDOM_POS));
                 //yy = (int) (RANDOM_POS_DESDE + ((int)(x) * RANDOM_POS) + ((int)(y) * RANDOM_POS));
                 xx = (int) (((int)(x) * RANDOM_POS) - ((int)(y) * RANDOM_POS));
                 yy = (int) ( ((int)(x) * RANDOM_POS) + ((int)(y) * RANDOM_POS));
                 
                 appendLog("(a,b) = (" + a + "," + b + ")  " );
                 appendLog("(xx,yy) = (" + xx + "," + yy + ")  " );
          
          	*/
            	/*
            	
            	int x = (int) (Math.random() * anchoMapas[0]);
            	int y = (int) (Math.random() * altoMapas[0]);     	
                while( matMapa[x][y] )
                {
                   	x = (int) (Math.random() * anchoMapas[0]);
                	y = (int) (Math.random() * altoMapas[0]); 
           		}*/
            	
            	int x = 5 + (int) (Math.random() * 30);
            	int y = 5 + (int) (Math.random() * 20);     	
                while( matMapa[y][x] )
                {
                	appendLog("(a,b) = (" + x + "," + y + ")  " + "es solidooooo");
                	x = 5 + (int) (Math.random() * 30);
                	y = 5 + (int) (Math.random() * 20); 
                	
           		}
            	
                appendLog("(a,b) = (" + x + "," + y + ")  " );
                x--;
                y--;
                //dos a iso		
                int xIsometrica = (x - y) * (64 / 2);
                int yIsometrica = (x + y) * (32 / 2);
                
                
                appendLog("(xx,yy) = (" + xIsometrica + "," + yIsometrica + ")  " );
            	
                PaqueteMovimiento paqueteMovimiento = new PaqueteMovimiento(i, (float)xIsometrica, (float)yIsometrica);


                NPCs.put(i, paqueteDeNPC);
                ubicacionNPCs.put(i, paqueteMovimiento);

                setNPCs(NPCs);
                setUbicacionNPCs(ubicacionNPCs);
            }
            appendLog("listo" );
            while (true) {
                Socket cliente = serverSocket.accept();
                ipRemota = cliente.getInetAddress().getHostAddress();
                appendLog(ipRemota + " se ha conectado");

                ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());

                EscuchaCliente atencion = new EscuchaCliente(ipRemota, cliente, entrada, salida);
                atencion.start();
                clientesConectados.add(atencion);
            }
        } catch (Exception e) {
            appendLog("Fallo la conexión.");
        }

    }

    private void cargarMapa(String path) {
        
    	
    	
    	

        try {
        	Scanner sc = new Scanner(new File(path));
/*
            while ((linea = br.readLine()) != null) {
                builder.append(linea + System.lineSeparator());
            }
*/
            altoMapas[0] = 74;
            anchoMapas[0] = 74;
            
            matMapa = new boolean[74][74];
            
            //sc.nextInt();
            //sc.nextInt();
            for (int i = 0; i < altoMapas[0]; i++) {
                for (int j = 0; j < anchoMapas[0]; j++) {
    				matMapa[i][j] = (sc.nextInt()) == 1 ? true : false;
    			}
			}
            
            
            sc.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fallo al intentar cargar el mapa " + path);
        }
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
            appendLog(pqm.getUserEmisor() + " envió mensaje a " + pqm.getUserReceptor());
            return true;
        } else {
            // Si no existe informo y devuelvo false
            appendLog("El mensaje para " + pqm.getUserReceptor() + " no se envió, ya que se encuentra desconectado.");
            return false;
        }
    }

    /**
     * Append log.
     *
     * @param text
     *            texto a añadir
     */
    public static void appendLog(final String text) {
        log.append(text + System.lineSeparator());
    }

    /**
     * Verifica si se envio mensaje a todos los clientes conectados
     *
     * @param contador
     *            la cantidad de clientes en el server
     * @return true, si hay inicio de sesion
     */
    public static boolean mensajeAAll(final int contador) {
        // Si existe inicio sesion
        if (personajesConectados.size() == contador + 1) {
            appendLog("Se ha enviado un mensaje a todos los usuarios");
            return true;
        } else {
            // Si no existe informo y devuelvo false
            appendLog("Uno o más de todos los usuarios se ha desconectado, se ha mandado el mensaje a los demas.");
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
    public static Map<Integer, PaqueteMovimiento> getUbicacionNPCs() {
        return ubicacionNPCs;
    }

    /**
     * Sets the ubicacion NPCs.
     *
     * @param ubicacionNPCs
     *            paquete con la ubicacion del NPCs
     */
    public static void setUbicacionNPCs(final Map<Integer, PaqueteMovimiento> ubicacionNPCs) {
        Servidor.ubicacionNPCs = ubicacionNPCs;
    }

    /**
     * Gets the NPCs.
     *
     * @return the NPCs
     */
    public static Map<Integer, PaqueteDeNPC> getNPCs() {
        return NPCs;
    }

    /**
     * Sets the NPCs.
     *
     * @param NPCs
     *            the NPCs.
     */
    public static void setNPCs(final Map<Integer, PaqueteDeNPC> NPCs) {
        Servidor.NPCs = NPCs;
    }

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
    

    
}
