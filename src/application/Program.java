package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
       SellerDao sellerDao = DaoFactory.createSellerDao();
       DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
       
       
       // Pesquisa por id=8
       System.out.println("Pesquisa por id=8");
       Seller seller = sellerDao.findById(5);
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
       
       // insere um Seller
//       System.out.println("Insere um novo Seller");
//       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//       Date d = sdf.parse("17/08/1995");
//       Seller newSeller = new Seller(null,"Drea Morgan","drea@gmail.com",d,1550.0,department);
//       sellerDao.insert(newSeller);
//       sellers.clear();
//       sellers = sellerDao.findAll();
//       sellers.forEach(System.out::println);
       
       // Altera um Seller
       System.out.println("Alterando Seller");
       seller.setEmail("jccintr@gmail.com");
       seller.setBaseSalary(seller.getBaseSalary()*2);
       sellerDao.update(seller);
       sellers.clear();
       sellers = sellerDao.findAll();
       sellers.forEach(System.out::println);
       
       //deleta um seller
       System.out.println("Excluindo Seller");
       sellerDao.deleteById(11);
       sellers.clear();
       sellers = sellerDao.findAll();
       sellers.forEach(System.out::println);
	}

}
