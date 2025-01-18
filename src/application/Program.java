package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
       SellerDao sellerDao = DaoFactory.createSellerDao();
       DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
       
       // Pesquisa por id=8
       System.out.println("Pesquisa por id=8");
       Seller seller = sellerDao.findById(8);
       System.out.println(seller);
       
       // Lista todos
       System.out.println("Lista todos os Vendedores");
       List<Seller> sellers = sellerDao.findAll();
       sellers.forEach(System.out::println);
       
       // FILTRA POR DEPARTAMENTO ID=1
       System.out.println("Filtra por Departamento Id=1");
       Department department = departmentDao.findById(1);
       List<Seller> sellers2 = sellerDao.findByDepartment(department);
       sellers2.forEach(System.out::println);	
       
	}

}
