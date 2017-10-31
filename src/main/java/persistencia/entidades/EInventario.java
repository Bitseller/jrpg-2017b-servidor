package persistencia.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="inventario")
@Table (name="inventario")
public class EInventario {

	@Id
	@Column (name="idPersonaje")
	private int id;

    @MapsId
    @OneToOne
    @JoinColumn(name="idPersonaje")
	private EPersonaje personaje;

    @ManyToOne
    @JoinColumn(name = "manos1")
    private EItem mano1;

    @ManyToOne
    @JoinColumn(name = "manos2")
    private EItem mano2;

    @ManyToOne
    @JoinColumn(name = "pie")
	private EItem pie;
    
    @ManyToOne
    @JoinColumn(name = "cabeza")
	private EItem cabeza;
    
    @ManyToOne
    @JoinColumn(name = "pecho")
	private EItem pecho;
    
    @ManyToOne
    @JoinColumn(name = "accesorio")
	private EItem accesorio;
	
	public EInventario() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the personaje
	 */
	public EPersonaje getPersonaje() {
		return personaje;
	}

	/**
	 * @param personaje the personaje to set
	 */
	public void setPersonaje(EPersonaje personaje) {
		this.personaje = personaje;
	}

	/**
	 * @return the mano1
	 */
	public EItem getMano1() {
		return mano1;
	}

	/**
	 * @param mano1 the mano1 to set
	 */
	public void setMano1(EItem mano1) {
		this.mano1 = mano1;
	}

	/**
	 * @return the mano2
	 */
	public EItem getMano2() {
		return mano2;
	}

	/**
	 * @param mano2 the mano2 to set
	 */
	public void setMano2(EItem mano2) {
		this.mano2 = mano2;
	}

	/**
	 * @return the pie
	 */
	public EItem getPie() {
		return pie;
	}

	/**
	 * @param pie the pie to set
	 */
	public void setPie(EItem pie) {
		this.pie = pie;
	}

	/**
	 * @return the cabeza
	 */
	public EItem getCabeza() {
		return cabeza;
	}

	/**
	 * @param cabeza the cabeza to set
	 */
	public void setCabeza(EItem cabeza) {
		this.cabeza = cabeza;
	}

	/**
	 * @return the pecho
	 */
	public EItem getPecho() {
		return pecho;
	}

	/**
	 * @param pecho the pecho to set
	 */
	public void setPecho(EItem pecho) {
		this.pecho = pecho;
	}

	/**
	 * @return the accesorio
	 */
	public EItem getAccesorio() {
		return accesorio;
	}

	/**
	 * @param accesorio the accesorio to set
	 */
	public void setAccesorio(EItem accesorio) {
		this.accesorio = accesorio;
	}
	
}
