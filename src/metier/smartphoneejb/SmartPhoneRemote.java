package metier.smartphoneejb;

import java.util.List;

import javax.ejb.Remote;

import metier.beans.SmartPhone;

@Remote
public interface SmartPhoneRemote {
	public int save(SmartPhone s);

	public int deleteById(Long id);

	public List<SmartPhone> findAll();

	public int update(SmartPhone s);

	public SmartPhone findByRef(String ref);

	public SmartPhone findById(Long id);
}
