package com.expertiz.entities;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
@Table(name="User",schema="dsExpertizFileTranscript",uniqueConstraints={@UniqueConstraint(columnNames="login")})
@NamedQuery(name = "User.getAll", query = "SELECT u FROM  User u")
public class User implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String nom;
	private String login;
	private String password;
	private static final long serialVersionUID = 1L;

	public User() {
		super();
	}   
		
	public User(int id, String nom, String login, String password) {
		super();
		this.id = id;
		this.nom = nom;
		this.login = login;
		this.password = password;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}   
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}   
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
   
}
