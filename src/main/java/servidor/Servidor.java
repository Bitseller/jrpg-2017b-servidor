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
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import mensajeria.PaqueteMensaje;
import mensajeria.PaqueteMovimiento;
import mensajeria.PaquetePersonaje;
import properties.PropiedadesComunicacion;
import mensajeria.PaqueteDeNPC;

public class Servidor extends Thread {

	private static ArrayList<EscuchaCliente> clientesConectados = new ArrayList<>();
	
	private static Map<Integer, PaqueteMovimiento> ubicacionPersonajes = new HashMap<>();
	private static Map<Integer, PaquetePersonaje> personajesConectados = new HashMap<>();
	private static Map<Integer, PaqueteMovimiento> ubicacionNPCs = new HashMap<>();
	private static Map<Integer, PaqueteDeNPC> NPCs = new HashMap<>();

	private static Thread server;
	
	private static ServerSocket serverSocket;
	private static Conector conexionDB;

	private final static int ANCHO = 700;
	private final static int ALTO = 640;
	private final static int ALTO_LOG = 520;
	private final static int ANCHO_LOG = ANCHO - 25;

	public static JTextArea log;
	
	public static AtencionConexiones atencionConexiones;
	public static AtencionMovimientos atencionMovimientos;

	public static void main(String[] args) {
		cargarInterfaz();	
	}

	private static void cargarInterfaz() {
		JFrame ventana = new JFrame("Servidor WOME");
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setSize(ANCHO, ALTO);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setLayout(null);
		ventana.setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/java/servidor/server.png"));
		JLabel titulo = new JLabel("Log del servidor...");
		titulo.setFont(new Font("Courier New", Font.BOLD, 16));
		titulo.setBounds(10, 0, 200, 30);
		ventana.add(titulo);

		log = new JTextArea();
		log.setEditable(false);
		log.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		JScrollPane scroll = new JScrollPane(log, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(10, 40, ANCHO_LOG, ALTO_LOG);
		ventana.add(scroll);

		final JButton botonIniciar = new JButton();
		final JButton botonDetener = new JButton();
		botonIniciar.setText("Iniciar");
		botonIniciar.setBounds(220, ALTO - 70, 100, 30);
		botonIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server = new Thread(new Servidor());
				server.start();
				botonIniciar.setEnabled(false);
				botonDetener.setEnabled(true);
			}
		});

		ventana.add(botonIniciar);

		botonDetener.setText("Detener");
		botonDetener.setBounds(360, ALTO - 70, 100, 30);
		botonDetener.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				if(conexionDB != null)
					conexionDB.close();
				botonDetener.setEnabled(false);
				botonIniciar.setEnabled(true);
			}
		});
		botonDetener.setEnabled(false);
		ventana.add(botonDetener);

		ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		ventana.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
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
				if (conexionDB != null)
					conexionDB.close();
				System.exit(0);
			}
		});

		ventana.setVisible(true);
	}

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
			
			
			
			
			for (int i = 0; i < 10; i++) { // crea 10 NPCs en posiciones randoms
				PaqueteDeNPC paqueteDeNPC = new PaqueteDeNPC(i);
				float x = (float) Math.random() * 500;
				float y = (float)Math.random() * 500;
				
				PaqueteMovimiento paqueteMovimiento = new PaqueteMovimiento(i, (float)(10 + (x * 0.707) - (y * 0.707 )), (float)(10 + (x * 0.707) + (y * 0.707 )) );
				
				
				NPCs.put( i, paqueteDeNPC);
				ubicacionNPCs.put( i, paqueteMovimiento);
				
				setNPCs( NPCs );
				setUbicacionNPCs( ubicacionNPCs );
			}


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

	public static boolean mensajeAUsuario(final PaqueteMensaje pqm) {
		boolean result = true;
		boolean noEncontro = true;
		for (Map.Entry<Integer, PaquetePersonaje> personaje : personajesConectados.entrySet()) {
			if(noEncontro && (!personaje.getValue().getNombre().equals(pqm.getUserReceptor()))) {
				result = false;
			} else {
				result = true;
				noEncontro = false;
			}
		}
		// Si existe inicio sesion
		if (result) {
			appendLog(pqm.getUserEmisor() + " envió mensaje a " + pqm.getUserReceptor());
				return true;
		} else {
			// Si no existe informo y devuelvo false
			appendLog("El mensaje para " + pqm.getUserReceptor() + " no se envió, ya que se encuentra desconectado.");
			return false;
		}
	}
	
	public static void appendLog(final String text) {
		 log.append(text+System.lineSeparator());	
	}
	
	public static boolean mensajeAAll(final int contador) {
		// Si existe inicio sesion
		if ( personajesConectados.size() == contador + 1 ) {
			appendLog("Se ha enviado un mensaje a todos los usuarios");
			return true;
		}
		// Si no existe informo y devuelvo false
		appendLog("Uno o más de todos los usuarios se ha desconectado, se ha mandado el mensaje a los demas.");
		return false;
	}
	
	public static ArrayList<EscuchaCliente> getClientesConectados() {
		return clientesConectados;
	}

	public static Map<Integer, PaqueteMovimiento> getUbicacionNPCs() {
		return ubicacionNPCs;
	}

	public static void setUbicacionNPCs(final Map<Integer, PaqueteMovimiento> ubicacionNPCs) {
		Servidor.ubicacionNPCs = ubicacionNPCs;
	}

	public static Map<Integer, PaqueteDeNPC> getNPCs() {
		return NPCs;
	}

	public static void setNPCs(final Map<Integer, PaqueteDeNPC> NPCs) {
		Servidor.NPCs = NPCs;
	}

	public static Map<Integer, PaqueteMovimiento> getUbicacionPersonajes() {
		return ubicacionPersonajes;
	}
	
	public static Map<Integer, PaquetePersonaje> getPersonajesConectados() {
		return personajesConectados;
	}

	public static void setUbicacionPersonajes(final Map<Integer, PaqueteMovimiento> ubicacionPersonajes) {
		Servidor.ubicacionPersonajes = ubicacionPersonajes;
	}

	public static Conector getConector() {
		return conexionDB;
	}
}