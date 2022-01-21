package metier.beans;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import java.util.List;

public class User implements Serializable {
	private Long id;
	private String nom;
	private String prenom;
	private String telephone;
	private String email;
	private Date dateNaissance;
	private List<SmartPhone> smartPhones;
	private static final long serialVersionUID = 1L;

	public User() {
		super();
	}

	public User(String nom, String prenom, String telephone, String email, Date dateNaissance,
			List<SmartPhone> smartPhones) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.email = email;
		this.dateNaissance = dateNaissance;
		this.smartPhones = smartPhones;
	}

	public User(String nom, String prenom, String telephone, String email, Date dateNaissance) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.email = email;
		this.dateNaissance = dateNaissance;
	}

	public List<SmartPhone> getSmartPhones() {
		return smartPhones;
	}

	public void setSmartPhones(List<SmartPhone> smartPhones) {
		this.smartPhones = smartPhones;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateNaissance() {
		return this.dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

}
