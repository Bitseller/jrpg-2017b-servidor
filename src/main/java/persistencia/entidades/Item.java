package persistencia.entidades;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class EItem.
 */
@Entity
@Table(name = "item")
public class Item {

    @Id
    @Column(name = "idItem")
    private int id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "wereable")
    private int wereable;
    @Column(name = "bonusSalud")
    private int bonusSalud;
    @Column(name = "bonusFuerza")
    private int bonusFuerza;
    @Column(name = "bonusDestreza")
    private int bonusDestreza;
    @Column(name = "bonusInteligencia")
    private int bonusInteligencia;
    @Column(name = "bonusEnergia")
    private int bonusEnergia;
    @Column(name = "foto")
    private String foto;
    @Column(name = "fotoEquipado")
    private String fotoEquipado;
    @Column(name = "fuerzaRequerida")
    private int fuerzaRequerida;
    @Column(name = "destrezaRequerida")
    private int destrezaRequerida;
    @Column(name = "inteligenciaRequerida")
    private int inteligenciaRequerida;

    /**
     * Instantiates a new e item.
     */
    public Item() {
        super();
        // TODO Auto-generated constructor stub
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
     * Gets the wereable.
     *
     * @return the wereable
     */
    public int getWereable() {
        return wereable;
    }

    /**
     * Sets the wereable.
     *
     * @param wereable
     *            the wereable to set
     */
    public void setWereable(final int wereable) {
        this.wereable = wereable;
    }

    /**
     * Gets the bonus salud.
     *
     * @return the bonusSalud
     */
    public int getBonusSalud() {
        return bonusSalud;
    }

    /**
     * Sets the bonus salud.
     *
     * @param bonusSalud
     *            the bonusSalud to set
     */
    public void setBonusSalud(final int bonusSalud) {
        this.bonusSalud = bonusSalud;
    }

    /**
     * Gets the bonus fuerza.
     *
     * @return the bonusFuerza
     */
    public int getBonusFuerza() {
        return bonusFuerza;
    }

    /**
     * Sets the bonus fuerza.
     *
     * @param bonusFuerza
     *            the bonusFuerza to set
     */
    public void setBonusFuerza(final int bonusFuerza) {
        this.bonusFuerza = bonusFuerza;
    }

    /**
     * Gets the bonus destreza.
     *
     * @return the bonusDestreza
     */
    public int getBonusDestreza() {
        return bonusDestreza;
    }

    /**
     * Sets the bonus destreza.
     *
     * @param bonusDestreza
     *            the bonusDestreza to set
     */
    public void setBonusDestreza(final int bonusDestreza) {
        this.bonusDestreza = bonusDestreza;
    }

    /**
     * Gets the bonus inteligencia.
     *
     * @return the bonusInteligencia
     */
    public int getBonusInteligencia() {
        return bonusInteligencia;
    }

    /**
     * Sets the bonus inteligencia.
     *
     * @param bonusInteligencia
     *            the bonusInteligencia to set
     */
    public void setBonusInteligencia(final int bonusInteligencia) {
        this.bonusInteligencia = bonusInteligencia;
    }

    /**
     * Gets the bonus energia.
     *
     * @return the bonusEnergia
     */
    public int getBonusEnergia() {
        return bonusEnergia;
    }

    /**
     * Sets the bonus energia.
     *
     * @param bonusEnergia
     *            the bonusEnergia to set
     */
    public void setBonusEnergia(final int bonusEnergia) {
        this.bonusEnergia = bonusEnergia;
    }

    /**
     * Gets the foto.
     *
     * @return the foto
     */
    public String getFoto() {
        return foto;
    }

    /**
     * Sets the foto.
     *
     * @param foto
     *            the foto to set
     */
    public void setFoto(final String foto) {
        this.foto = foto;
    }

    /**
     * Gets the foto equipado.
     *
     * @return the fotoEquipado
     */
    public String getFotoEquipado() {
        return fotoEquipado;
    }

    /**
     * Sets the foto equipado.
     *
     * @param fotoEquipado
     *            the fotoEquipado to set
     */
    public void setFotoEquipado(final String fotoEquipado) {
        this.fotoEquipado = fotoEquipado;
    }

    /**
     * Gets the fuerza requerida.
     *
     * @return the fuerzaRequerida
     */
    public int getFuerzaRequerida() {
        return fuerzaRequerida;
    }

    /**
     * Sets the fuerza requerida.
     *
     * @param fuerzaRequerida
     *            the fuerzaRequerida to set
     */
    public void setFuerzaRequerida(final int fuerzaRequerida) {
        this.fuerzaRequerida = fuerzaRequerida;
    }

    /**
     * Gets the destreza requerida.
     *
     * @return the destrezaRequerida
     */
    public int getDestrezaRequerida() {
        return destrezaRequerida;
    }

    /**
     * Sets the destreza requerida.
     *
     * @param destrezaRequerida
     *            the destrezaRequerida to set
     */
    public void setDestrezaRequerida(final int destrezaRequerida) {
        this.destrezaRequerida = destrezaRequerida;
    }

    /**
     * Gets the inteligencia requerida.
     *
     * @return the inteligenciaRequerida
     */
    public int getInteligenciaRequerida() {
        return inteligenciaRequerida;
    }

    /**
     * Sets the inteligencia requerida.
     *
     * @param inteligenciaRequerida
     *            the inteligenciaRequerida to set
     */
    public void setInteligenciaRequerida(final int inteligenciaRequerida) {
        this.inteligenciaRequerida = inteligenciaRequerida;
    }

    public dominio.Item convertToItem() throws IOException{
    	return new dominio.Item(this.id, this.nombre, this.wereable, this.bonusSalud,
                this.bonusEnergia, this.bonusFuerza, this.bonusDestreza, this.bonusInteligencia,
                this.foto, this.fotoEquipado);
    }
    
}
