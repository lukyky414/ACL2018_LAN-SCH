package dao_Txt;

public class ConcreteTxtFactory extends AbstractDAOFactory{

	@Override
	public Map getMapDAO() {
		// TODO Auto-generated method stub
		return MapTxtDAO.getInstance();
	}

}
