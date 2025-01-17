package model.dao;

import model.dao.implement.SellerDaoJDBC;

public class DaoFectory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC();
	}
}
