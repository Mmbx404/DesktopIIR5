package metier.positionEjb;

import java.util.List;

import javax.ejb.Local;

import metier.beans.Position;

@Local
public interface PositionLocal {
	public int save(Position p);

	public int deleteById(Long id);

	public List<Position> findAll();

	public int update(Position p);

	public Position findById(Long id);
}
