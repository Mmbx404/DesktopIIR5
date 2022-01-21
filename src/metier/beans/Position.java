package metier.beans;

import java.io.Serializable;
import java.lang.Double;
import java.lang.Long;
import java.util.Date;
import java.util.List;

/**
 * Entity implementation class for Entity: Position
 *
 */
public class Position implements Serializable {
	private Long id;
	private Double latitude;
	private Double longitude;
	private Date date;
	private List<SmartPhone> smartPhones;
	private static final long serialVersionUID = 1L;

	public Position() {
		super();
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
	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}   
	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}   
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
   
}
