package metier.positionEjb;

import java.util.List;

import javax.ejb.Remote;

import metier.beans.Position;

@Remote
public interface PositionRemote {
	
	public int save(Position p);

	public int deleteById(Long id);

	public List<Position> findAll();

	public int update(Position p);

	public Position findById(Long id);
}
