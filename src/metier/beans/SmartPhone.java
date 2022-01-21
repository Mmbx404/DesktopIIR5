package metier.beans;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;

/**
 * Entity implementation class for Entity: SmartPhone
 *
 */
public class SmartPhone implements Serializable {
	private Long id;
	private String ref;
	private String name;
	private String marque;
	private User user;
	private Position position;
	private static final long serialVersionUID = -558553967080513790L;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMarque() {
		return marque;
	}
	public void setMarque(String marque) {
		this.marque = marque;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public SmartPhone(Long id, String ref, String name, String marque, User user, Position position) {
		super();
		this.id = id;
		this.ref = ref;
		this.name = name;
		this.marque = marque;
		this.user = user;
		this.position = position;
	}
	public SmartPhone() {
		super();
		// TODO Auto-generated constructor stub
	}
}
