package servidor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

import mensajeria.PaqueteDeNPC;
import mensajeria.PaqueteFinalizarBatalla;
import mensajeria.PaqueteMovimiento;

/**
 * The Class NPCadmin.
 */
public class NPCadmin {

    private Map<Integer, PaqueteDeNPC> NPCs = new HashMap<>();
    private Map<Integer, PaqueteMovimiento> ubicacionNPCs = new HashMap<>();
    private boolean[][] matMapa;

    private static int altoMapa;
    private static int anchoMapa;

    private static final int CANTIDAD_NPC = 10;
    private static final int MULTIPLICADOR_RANDOM_X = 30;
    private static final int MULTIPLICADOR_RANDOM_y = 20;
    private static final int INICIALIZADOR_POSICIONES = 5;
    private static final int X_ISO = (64 / 2);
    private static final int Y_ISO = (32 / 2);

    /**
     * Instantiates a new NP cadmin.
     *
     * @param pathMapa
     *            the path mapa
     */
    public NPCadmin(String pathMapa) {
        super();
        cargarMapa(pathMapa);
    }

    /**
     * Cargar primeros NPCS.
     */
    void cargarPrimerosNPCS() {
        cargarMapa("mapaSolides.txt");
        for (int i = 0; i < CANTIDAD_NPC; i++) { // crea 10 NPCs en posiciones randoms

            //for (int i = 0; i < 10; i++) {
            //int i = 0; //////////////
            PaqueteDeNPC paqueteDeNPC = new PaqueteDeNPC(i);

            /* asi seria si lo queres repartir por todo el mapa pero quedan re lejos si son 10 nomas    	
            int x = (int) (Math.random() * anchoMapas[0]);
            int y = (int) (Math.random() * altoMapas[0]);     	
            while( matMapa[x][y] )
            {
               	x = (int) (Math.random() * anchoMapas[0]);
            	y = (int) (Math.random() * altoMapas[0]); 
            }*/

            // coordenadas de los tiles en los q va  a estar ubicado el NPC para ver si es solido o no
            int x;
            int y;

            // coordenadas en forma isometrica
            int xIsometrica;
            int yIsometrica;

            // asi te los reparte los NPC al peincipio pero no donde spawneas
            x = INICIALIZADOR_POSICIONES + (int) (Math.random() * MULTIPLICADOR_RANDOM_X);
            y = INICIALIZADOR_POSICIONES + (int) (Math.random() * MULTIPLICADOR_RANDOM_y);
            while (matMapa[y][x]) {
                x = INICIALIZADOR_POSICIONES + (int) (Math.random() * MULTIPLICADOR_RANDOM_X);
                y = INICIALIZADOR_POSICIONES + (int) (Math.random() * MULTIPLICADOR_RANDOM_y);
            }

            // resta 1 pq empiezan a graficar desde el 1 y no desde el 0 
            x--;
            y--;

            // pasa de coordenadas de tiles a coordenadas isometricas para la posicion	
            xIsometrica = (x - y) * X_ISO;
            yIsometrica = (x + y) * Y_ISO;

            PaqueteMovimiento paqueteMovimiento = new PaqueteMovimiento(i, xIsometrica, yIsometrica);

            NPCs.put(i, paqueteDeNPC);
            ubicacionNPCs.put(i, paqueteMovimiento);

            setNPCs(NPCs);
            setUbicacionNPCs(ubicacionNPCs);
        }
    }

    /**
     * Cargarnuevos NPCS.
     *
     * @param paqueteFinalizarBatalla
     *            the paquete finalizar batalla
     */
    public void cargarnuevosNPCS(final PaqueteFinalizarBatalla paqueteFinalizarBatalla) {

        int x;
        int y;
        int xIso;
        int yIso;
        int xOffset;
        int yOffset;

        x = INICIALIZADOR_POSICIONES + (int) (Math.random() * MULTIPLICADOR_RANDOM_X);
        y = INICIALIZADOR_POSICIONES + (int) (Math.random() * MULTIPLICADOR_RANDOM_y);
        while (matMapa[y][x]) {
            x = INICIALIZADOR_POSICIONES + (int) (Math.random() * MULTIPLICADOR_RANDOM_X);
            y = INICIALIZADOR_POSICIONES + (int) (Math.random() * MULTIPLICADOR_RANDOM_y);
        }

        // resta 1 pq empiezan a graficar desde el 1 y no desde el 0 
        x--;
        y--;

        // pasa de coordenadas de tiles a coordenadas isometricas para la posicion	
        xIso = (x - y) * X_ISO;
        yIso = (x + y) * Y_ISO;

        PaqueteMovimiento newPosicion = new PaqueteMovimiento(paqueteFinalizarBatalla.getIdEnemigo(), xIso, yIso);
        Servidor.getNPCs().getUbicacionNPCs().put(paqueteFinalizarBatalla.getIdEnemigo(), newPosicion);
    }

    /**
     * Cargar mapa.
     *
     * @param path
     *            the path
     */
    private void cargarMapa(final String path) {

        try {
            Scanner sc = new Scanner(new File(path));

            altoMapa = 74;
            anchoMapa = 74;

            matMapa = new boolean[74][74];

            for (int i = 0; i < altoMapa; i++) {
                for (int j = 0; j < anchoMapa; j++) {
                    matMapa[i][j] = (sc.nextInt()) == 1 ? true : false;
                }
            }

            sc.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Fallo al intentar cargar el mapa " + path);
        }
    }

    /**
     * Gets the NP cs.
     *
     * @return the NP cs
     */
    //////getters y setters //////
    public Map<Integer, PaqueteDeNPC> getNPCs() {
        return NPCs;
    }

    /**
     * Sets the NP cs.
     *
     * @param nPCs
     *            the n P cs
     */
    public void setNPCs(final Map<Integer, PaqueteDeNPC> nPCs) {
        NPCs = nPCs;
    }

    /**
     * Gets the ubicacion NP cs.
     *
     * @return the ubicacion NP cs
     */
    public Map<Integer, PaqueteMovimiento> getUbicacionNPCs() {
        return ubicacionNPCs;
    }

    /**
     * Sets the ubicacion NP cs.
     *
     * @param ubicacionNPCs
     *            the ubicacion NP cs
     */
    public void setUbicacionNPCs(final Map<Integer, PaqueteMovimiento> ubicacionNPCs) {
        this.ubicacionNPCs = ubicacionNPCs;
    }

    /**
     * Gets the mat mapa.
     *
     * @return the mat mapa
     */
    public boolean[][] getMatMapa() {
        return matMapa;
    }

    /**
     * Sets the mat mapa.
     *
     * @param matMapa
     *            the new mat mapa
     */
    public void setMatMapa(final boolean[][] matMapa) {
        this.matMapa = matMapa;
    }

    /**
     * Gets the alto mapa.
     *
     * @return the alto mapa
     */
    public static int getAltoMapa() {
        return altoMapa;
    }

    /**
     * Sets the alto mapa.
     *
     * @param altoMapa
     *            the new alto mapa
     */
    public static void setAltoMapa(final int altoMapa) {
        NPCadmin.altoMapa = altoMapa;
    }

    /**
     * Gets the ancho mapa.
     *
     * @return the ancho mapa
     */
    public static int getAnchoMapa() {
        return anchoMapa;
    }

    /**
     * Sets the ancho mapa.
     *
     * @param anchoMapa
     *            the new ancho mapa
     */
    public static void setAnchoMapa(final int anchoMapa) {
        NPCadmin.anchoMapa = anchoMapa;
    }

    /**
     * Gets the cantidad npc.
     *
     * @return the cantidad npc
     */
    public static int getCantidadNpc() {
        return CANTIDAD_NPC;
    }
}
