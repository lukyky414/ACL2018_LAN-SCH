package dao_Txt;

public class ConcreteTxtFactory extends AbstractDAOFactory{

	@Override
	public MapDAO getMapDAO() {
		// TODO Auto-generated method stub
		return MapTxtDAO.getInstance();
	}

}
