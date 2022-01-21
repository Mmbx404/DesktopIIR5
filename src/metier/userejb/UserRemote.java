package metier.userejb;

import java.util.List;

import javax.ejb.Remote;

import metier.beans.User;

@Remote
public interface UserRemote {
	public int save(User u);

	public int deleteById(Long id);

	public List<User> findAll();

	public int update(User u);

	public User findByEmail(String email);

	public User findById(Long id);
}
