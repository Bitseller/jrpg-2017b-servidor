package persistencia.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="item")
public class EItem {
	
	@Id
	@Column (name="idItem")
	private int id;
	@Column (name="nombre")
	private String nombre;
	@Column (name="wereable")
	private int wereable;
	@Column (name="bonusSalud")
	private int bonusSalud;
	@Column (name="bonusFuerza")
	private int bonusFuerza;
	@Column (name="bonusDestreza")
	private int bonusDestreza;
	@Column (name="bonusInteligencia")
	private int bonusInteligencia;
	@Column (name="bonusEnergia")
	private int bonusEnergia;
	@Column (name="foto")
	private String foto;
	@Column (name="fotoEquipado")
	private String fotoEquipado;
	@Column (name="fuerzaRequerida")
	private int fuerzaRequerida;
	@Column (name="destrezaRequerida")
	private int destrezaRequerida;
	@Column (name="inteligenciaRequerida")
	private int inteligenciaRequerida;

	public EItem() {
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
	 * @return the wereable
	 */
	public int getWereable() {
		return wereable;
	}
	/**
	 * @param wereable the wereable to set
	 */
	public void setWereable(int wereable) {
		this.wereable = wereable;
	}
	/**
	 * @return the bonusSalud
	 */
	public int getBonusSalud() {
		return bonusSalud;
	}
	/**
	 * @param bonusSalud the bonusSalud to set
	 */
	public void setBonusSalud(int bonusSalud) {
		this.bonusSalud = bonusSalud;
	}
	/**
	 * @return the bonusFuerza
	 */
	public int getBonusFuerza() {
		return bonusFuerza;
	}
	/**
	 * @param bonusFuerza the bonusFuerza to set
	 */
	public void setBonusFuerza(int bonusFuerza) {
		this.bonusFuerza = bonusFuerza;
	}
	/**
	 * @return the bonusDestreza
	 */
	public int getBonusDestreza() {
		return bonusDestreza;
	}
	/**
	 * @param bonusDestreza the bonusDestreza to set
	 */
	public void setBonusDestreza(int bonusDestreza) {
		this.bonusDestreza = bonusDestreza;
	}
	/**
	 * @return the bonusInteligencia
	 */
	public int getBonusInteligencia() {
		return bonusInteligencia;
	}
	/**
	 * @param bonusInteligencia the bonusInteligencia to set
	 */
	public void setBonusInteligencia(int bonusInteligencia) {
		this.bonusInteligencia = bonusInteligencia;
	}
	/**
	 * @return the bonusEnergia
	 */
	public int getBonusEnergia() {
		return bonusEnergia;
	}
	/**
	 * @param bonusEnergia the bonusEnergia to set
	 */
	public void setBonusEnergia(int bonusEnergia) {
		this.bonusEnergia = bonusEnergia;
	}
	/**
	 * @return the foto
	 */
	public String getFoto() {
		return foto;
	}
	/**
	 * @param foto the foto to set
	 */
	public void setFoto(String foto) {
		this.foto = foto;
	}
	/**
	 * @return the fotoEquipado
	 */
	public String getFotoEquipado() {
		return fotoEquipado;
	}
	/**
	 * @param fotoEquipado the fotoEquipado to set
	 */
	public void setFotoEquipado(String fotoEquipado) {
		this.fotoEquipado = fotoEquipado;
	}
	/**
	 * @return the fuerzaRequerida
	 */
	public int getFuerzaRequerida() {
		return fuerzaRequerida;
	}
	/**
	 * @param fuerzaRequerida the fuerzaRequerida to set
	 */
	public void setFuerzaRequerida(int fuerzaRequerida) {
		this.fuerzaRequerida = fuerzaRequerida;
	}
	/**
	 * @return the destrezaRequerida
	 */
	public int getDestrezaRequerida() {
		return destrezaRequerida;
	}
	/**
	 * @param destrezaRequerida the destrezaRequerida to set
	 */
	public void setDestrezaRequerida(int destrezaRequerida) {
		this.destrezaRequerida = destrezaRequerida;
	}
	/**
	 * @return the inteligenciaRequerida
	 */
	public int getInteligenciaRequerida() {
		return inteligenciaRequerida;
	}
	/**
	 * @param inteligenciaRequerida the inteligenciaRequerida to set
	 */
	public void setInteligenciaRequerida(int inteligenciaRequerida) {
		this.inteligenciaRequerida = inteligenciaRequerida;
	}
	
}
