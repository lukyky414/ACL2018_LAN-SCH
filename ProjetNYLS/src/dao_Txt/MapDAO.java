package dao_Txt;

import exceptions.CorruptDataException;
import model.plateau.Map;

public interface MapDAO {

	
	public abstract void save(Map m);
	public abstract Map load(int idMap) throws CorruptDataException;
	
}
