package com.expertiz.entities;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Setting
 *
 */
@Entity
@Table(name="Setting",schema="dsExpertizFileTranscript",uniqueConstraints={@UniqueConstraint(columnNames="cle")})
@NamedQuery(name = "Setting.getAll", query = "SELECT s FROM Setting s")
public class Setting implements Serializable {

	   
	public Setting(int id, String cle, String valeur) {
		super();
		this.id = id;
		this.cle = cle;
		this.valeur = valeur;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String cle;
	private String valeur;
	private static final long serialVersionUID = 1L;

	public Setting() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getCle() {
		return this.cle;
	}

	public void setCle(String cle) {
		this.cle = cle;
	}   
	public String getValeur() {
		return this.valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
   
}
