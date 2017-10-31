package persistencia.entidades;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity (name="personaje")
@Table (name="personaje")
public class EPersonaje {

	@Id
	@GenericGenerator(name="generador" , strategy="increment")
	@GeneratedValue(generator="generador")
	@Column (name="idPersonaje")
	private int id;
	
	@Column (name="casta")
	private String casta;
	@Column (name="raza")
	private String raza;	
	@Column (name="fuerza")
	private int fuerza;
	@Column (name="destreza")
	private int destreza;
	@Column (name="inteligencia")
	private int inteligencia;
	@Column (name="saludTope")
	private int saludTope;
	@Column (name="energiaTope")
	private int energiaTope;
	@Column (name="nombre")
	private String nombre;
	@Column (name="experiencia")
	private int experiencia;
	@Column (name="puntosSkill")
	private int puntosSkill;
	@Column (name="nivel")
	private int nivel;
	
	@OneToOne(mappedBy="personaje")
	private EUsuario usuario;

//	@OneToOne(cascade={javax.persistence.CascadeType.ALL})
//	@PrimaryKeyJoinColumn
//	private EInventario inventario;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name="Mochila", joinColumns={@JoinColumn(name="pfkPersonaje")}, inverseJoinColumns={@JoinColumn(name="pfkItem")})
	private Set<EItem> mochila=new HashSet<EItem>();
	
	public EPersonaje() {
		super();
		this.nivel=1;
		this.experiencia=0;
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
	 * @return the casta
	 */
	public String getCasta() {
		return casta;
	}

	/**
	 * @param casta the casta to set
	 */
	public void setCasta(String casta) {
		this.casta = casta;
	}

	/**
	 * @return the raza
	 */
	public String getRaza() {
		return raza;
	}

	/**
	 * @param raza the raza to set
	 */
	public void setRaza(String raza) {
		this.raza = raza;
	}

	/**
	 * @return the fuerza
	 */
	public int getFuerza() {
		return fuerza;
	}

	/**
	 * @param fuerza the fuerza to set
	 */
	public void setFuerza(int fuerza) {
		this.fuerza = fuerza;
	}

	/**
	 * @return the destreza
	 */
	public int getDestreza() {
		return destreza;
	}

	/**
	 * @param destreza the destreza to set
	 */
	public void setDestreza(int destreza) {
		this.destreza = destreza;
	}

	/**
	 * @return the inteligencia
	 */
	public int getInteligencia() {
		return inteligencia;
	}

	/**
	 * @param inteligencia the inteligencia to set
	 */
	public void setInteligencia(int inteligencia) {
		this.inteligencia = inteligencia;
	}

	/**
	 * @return the saludTope
	 */
	public int getSaludTope() {
		return saludTope;
	}

	/**
	 * @param saludTope the saludTope to set
	 */
	public void setSaludTope(int saludTope) {
		this.saludTope = saludTope;
	}

	/**
	 * @return the energiaTope
	 */
	public int getEnergiaTope() {
		return energiaTope;
	}

	/**
	 * @param energiaTope the energiaTope to set
	 */
	public void setEnergiaTope(int energiaTope) {
		this.energiaTope = energiaTope;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the experiencia
	 */
	public int getExperiencia() {
		return experiencia;
	}

	/**
	 * @param experiencia the experiencia to set
	 */
	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
	}

	/**
	 * @return the puntosSkill
	 */
	public int getPuntosSkill() {
		return puntosSkill;
	}

	/**
	 * @param puntosSkill the puntosSkill to set
	 */
	public void setPuntosSkill(int puntosSkill) {
		this.puntosSkill = puntosSkill;
	}

	/**
	 * @return the nivel
	 */
	public int getNivel() {
		return nivel;
	}

	/**
	 * @param nivel the nivel to set
	 */
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	/**
	 * @return the usuario
	 */
	public EUsuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(EUsuario usuario) {
		this.usuario = usuario;
	}

//	/**
//	 * @return the inventario
//	 */
//	public EInventario getInventario() {
//		return inventario;
//	}
//
//	/**
//	 * @param inventario the inventario to set
//	 */
//	public void setInventario(EInventario inventario) {
//		this.inventario = inventario;
//		this.inventario.setPersonaje(this);
//	}

	/**
	 * @return the mochila
	 */
	public Set<EItem> getMochila() {
		return mochila;
	}

	/**
	 * @param mochila the mochila to set
	 */
	public void setMochila(Set<EItem> mochila) {
		this.mochila = mochila;
	}

	
}
