package metier.smartphoneejb;

import java.util.List;

import javax.ejb.Local;

import metier.beans.SmartPhone;
@Local
public interface SmartPhoneLocal {
	public int save(SmartPhone s);

	public int deleteById(Long id);

	public List<SmartPhone> findAll();

	public int update(SmartPhone s);

	public SmartPhone findByRef(String ref);

	public SmartPhone findById(Long id);
}
