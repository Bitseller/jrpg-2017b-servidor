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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * The Class EPersonaje.
 */
@Entity(name = "personaje")
@Table(name = "personaje")
public class EPersonaje {

    @Id
    @GenericGenerator(name = "generador", strategy = "increment")
    @GeneratedValue(generator = "generador")
    @Column(name = "idPersonaje")
    private int id;

    @Column(name = "casta")
    private String casta;
    @Column(name = "raza")
    private String raza;
    @Column(name = "fuerza")
    private int fuerza;
    @Column(name = "destreza")
    private int destreza;
    @Column(name = "inteligencia")
    private int inteligencia;
    @Column(name = "saludTope")
    private int saludTope;
    @Column(name = "energiaTope")
    private int energiaTope;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "experiencia")
    private int experiencia;
    @Column(name = "puntosSkill")
    private int puntosSkill;
    @Column(name = "nivel")
    private int nivel;

    @OneToOne(mappedBy = "personaje")
    private EUsuario usuario;

    //	@OneToOne(cascade={javax.persistence.CascadeType.ALL})
    //	@PrimaryKeyJoinColumn
    //	private EInventario inventario;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "Mochila", joinColumns = { @JoinColumn(name = "pfkPersonaje") }, inverseJoinColumns = {
        @JoinColumn(name = "pfkItem") })
    private Set<EItem> mochila = new HashSet<EItem>();

    /**
     * Instantiates a new e personaje.
     */
    public EPersonaje() {
        super();
        this.nivel = 1;
        this.experiencia = 0;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the id to set
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Gets the casta.
     *
     * @return the casta
     */
    public String getCasta() {
        return casta;
    }

    /**
     * Sets the casta.
     *
     * @param casta
     *            the casta to set
     */
    public void setCasta(final String casta) {
        this.casta = casta;
    }

    /**
     * Gets the raza.
     *
     * @return the raza
     */
    public String getRaza() {
        return raza;
    }

    /**
     * Sets the raza.
     *
     * @param raza
     *            the raza to set
     */
    public void setRaza(final String raza) {
        this.raza = raza;
    }

    /**
     * Gets the fuerza.
     *
     * @return the fuerza
     */
    public int getFuerza() {
        return fuerza;
    }

    /**
     * Sets the fuerza.
     *
     * @param fuerza
     *            the fuerza to set
     */
    public void setFuerza(final int fuerza) {
        this.fuerza = fuerza;
    }

    /**
     * Gets the destreza.
     *
     * @return the destreza
     */
    public int getDestreza() {
        return destreza;
    }

    /**
     * Sets the destreza.
     *
     * @param destreza
     *            the destreza to set
     */
    public void setDestreza(final int destreza) {
        this.destreza = destreza;
    }

    /**
     * Gets the inteligencia.
     *
     * @return the inteligencia
     */
    public int getInteligencia() {
        return inteligencia;
    }

    /**
     * Sets the inteligencia.
     *
     * @param inteligencia
     *            the inteligencia to set
     */
    public void setInteligencia(final int inteligencia) {
        this.inteligencia = inteligencia;
    }

    /**
     * Gets the salud tope.
     *
     * @return the saludTope
     */
    public int getSaludTope() {
        return saludTope;
    }

    /**
     * Sets the salud tope.
     *
     * @param saludTope
     *            the saludTope to set
     */
    public void setSaludTope(final int saludTope) {
        this.saludTope = saludTope;
    }

    /**
     * Gets the energia tope.
     *
     * @return the energiaTope
     */
    public int getEnergiaTope() {
        return energiaTope;
    }

    /**
     * Sets the energia tope.
     *
     * @param energiaTope
     *            the energiaTope to set
     */
    public void setEnergiaTope(final int energiaTope) {
        this.energiaTope = energiaTope;
    }

    /**
     * Gets the nombre.
     *
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the nombre.
     *
     * @param nombre
     *            the nombre to set
     */
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    /**
     * Gets the experiencia.
     *
     * @return the experiencia
     */
    public int getExperiencia() {
        return experiencia;
    }

    /**
     * Sets the experiencia.
     *
     * @param experiencia
     *            the experiencia to set
     */
    public void setExperiencia(final int experiencia) {
        this.experiencia = experiencia;
    }

    /**
     * Gets the puntos skill.
     *
     * @return the puntosSkill
     */
    public int getPuntosSkill() {
        return puntosSkill;
    }

    /**
     * Sets the puntos skill.
     *
     * @param puntosSkill
     *            the puntosSkill to set
     */
    public void setPuntosSkill(final int puntosSkill) {
        this.puntosSkill = puntosSkill;
    }

    /**
     * Gets the nivel.
     *
     * @return the nivel
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * Sets the nivel.
     *
     * @param nivel
     *            the nivel to set
     */
    public void setNivel(final int nivel) {
        this.nivel = nivel;
    }

    /**
     * Gets the usuario.
     *
     * @return the usuario
     */
    public EUsuario getUsuario() {
        return usuario;
    }

    /**
     * Sets the usuario.
     *
     * @param usuario
     *            the usuario to set
     */
    public void setUsuario(final EUsuario usuario) {
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
     * Gets the mochila.
     *
     * @return the mochila
     */
    public Set<EItem> getMochila() {
        return mochila;
    }

    /**
     * Sets the mochila.
     *
     * @param mochila
     *            the mochila to set
     */
    public void setMochila(final Set<EItem> mochila) {
        this.mochila = mochila;
    }

}
