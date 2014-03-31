package lab.cgcl.aliBigdata.BPNNlearning.getVector;

import lab.cgcl.aliBigdata.BPNNlearning.dao.SimpleDao;

public abstract class GetInfoFromDB implements IGetInfo {
	
	protected SimpleDao dao ;
	
	private final int VectorOffset;

	public SimpleDao getDao() {
		return dao;
	}

	public void setDao(SimpleDao dao) {
		this.dao = dao;
	}
	
	public GetInfoFromDB (int id) {
		VectorOffset = id;
	}

}
