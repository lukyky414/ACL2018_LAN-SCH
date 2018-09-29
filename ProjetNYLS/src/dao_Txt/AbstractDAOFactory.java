package dao_Txt;

public abstract class AbstractDAOFactory {
	
	public abstract MapDAO getMapDAO();
	
	public static AbstractDAOFactory getAbstractDAOFactory(String classe){
		String c = classe.toUpperCase();
		switch(classe){
		case "TXT":
			return new ConcreteTxtFactory();
		}
		
		return null;
	}
	

}
