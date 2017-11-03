package servidor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

import mensajeria.PaqueteDeNPC;
import mensajeria.PaqueteMovimiento;

public class NPCadmin {

	
    private Map<Integer, PaqueteDeNPC> NPCs = new HashMap<>();
    private Map<Integer, PaqueteMovimiento> ubicacionNPCs = new HashMap<>();
    private boolean[][] matMapa;
    
    private static int altoMapa;
	private static int anchoMapa;
	
    private static final int CANTIDAD_NPC = 10;
	
    public NPCadmin( String pathMapa) {
		super();
		cargarMapa(pathMapa);
	}

	void cargarPrimerosNPCS(){
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
	    	x = 5 + (int) (Math.random() * 30);
	    	y = 5 + (int) (Math.random() * 20);     	
	        while( matMapa[y][x] )
	        {
	        	x = 5 + (int) (Math.random() * 30);
	        	y = 5 + (int) (Math.random() * 20);
	   		}
	    	
	        // resta 1 pq empiezan a graficar desde el 1 y no desde el 0 
	        x--;
	        y--;
	        
	        // pasa de coordenadas de tiles a coordenadas isometricas para la posicion	
	        xIsometrica = (x - y) * (64 / 2);
	        yIsometrica = (x + y) * (32 / 2);
	        
	        
	    	
	        PaqueteMovimiento paqueteMovimiento = new PaqueteMovimiento(i, (float)xIsometrica, (float)yIsometrica);
	
	        NPCs.put(i, paqueteDeNPC);
	        ubicacionNPCs.put(i, paqueteMovimiento);
	
	        setNPCs(NPCs);
	        setUbicacionNPCs(ubicacionNPCs);
    	}
    }
	


	private void cargarMapa(String path) {
      
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

	
	//////getters y setters //////
	public Map<Integer, PaqueteDeNPC> getNPCs() {
		return NPCs;
	}

	public void setNPCs(Map<Integer, PaqueteDeNPC> nPCs) {
		NPCs = nPCs;
	}

	public Map<Integer, PaqueteMovimiento> getUbicacionNPCs() {
		return ubicacionNPCs;
	}

	public void setUbicacionNPCs(Map<Integer, PaqueteMovimiento> ubicacionNPCs) {
		this.ubicacionNPCs = ubicacionNPCs;
	}

	public boolean[][] getMatMapa() {
		return matMapa;
	}

	public void setMatMapa(boolean[][] matMapa) {
		this.matMapa = matMapa;
	}

	public static int getAltoMapa() {
		return altoMapa;
	}

	public static void setAltoMapa(int altoMapa) {
		NPCadmin.altoMapa = altoMapa;
	}

	public static int getAnchoMapa() {
		return anchoMapa;
	}

	public static void setAnchoMapa(int anchoMapa) {
		NPCadmin.anchoMapa = anchoMapa;
	}

	public static int getCantidadNpc() {
		return CANTIDAD_NPC;
	}
    
    
    
    
	
	
}
