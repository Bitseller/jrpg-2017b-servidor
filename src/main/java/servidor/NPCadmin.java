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
		        	x = 5 + (int) (Math.random() * 30);
		        	y = 5 + (int) (Math.random() * 20);
		   		}
		    	
		        x--;
		        y--;
		        //dos a iso		
		        int xIsometrica = (x - y) * (64 / 2);
		        int yIsometrica = (x + y) * (32 / 2);
		        
		        
		    	
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
/*
            while ((linea = br.readLine()) != null) {
                builder.append(linea + System.lineSeparator());
            }
*/
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
