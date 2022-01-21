package metier.userejb;

import java.util.List;

import javax.ejb.Local;

import metier.beans.User;

@Local
public interface UserLocal {
	public int save(User u);

	public int deleteById(Long id);

	public List<User> findAll();

	public int update(User u);

	public User findByEmail(String email);

	public User findById(Long id);
}
