package model.dao.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO sellers (name,email,birthdate,basesalary,department_id) values (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3,new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4,obj.getBaseSalary());
			st.setInt(5,obj.getDepartment().getId());
			int rowsAffected = st.executeUpdate();
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Erro inesperado ao tentar inserir.");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			
		}
	}

	@Override
	public void update(Seller obj) {
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE sellers SET name=?,email=?,birthdate=?,basesalary=?,department_id=? WHERE id=?");
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3,new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4,obj.getBaseSalary());
			st.setInt(5,obj.getDepartment().getId());
			st.setInt(6,obj.getId());
			st.executeUpdate();
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			
		}

		
	}

	@Override
	public void deleteById(Integer id) {
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM sellers WHERE id=?");
			st.setInt(1,id);
			st.executeUpdate();
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			
		}

		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT sellers.*,departments.name as DepName "
					+ "FROM sellers INNER JOIN departments "
					+ "ON sellers.department_id = departments.id "
					+ "WHERE sellers.id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				
				Department department = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs,department);
				
				return seller;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		
		try {
			st = conn.prepareStatement(
					"SELECT sellers.*,departments.name as DepName "
					+ "FROM sellers INNER JOIN departments "
					+ "ON sellers.department_id = departments.id "
					+ "ORDER BY sellers.name");
			
			List<Seller> sellers = new ArrayList<>();
			Map<Integer,Department> departmentsMap= new HashMap<>();
			rs = st.executeQuery();
			while (rs.next()) {
				Department department = departmentsMap.get(rs.getInt("department_id"));
				if (department == null) {
					department = instantiateDepartment(rs);
					departmentsMap.put(rs.getInt("department_id"),department);
				}
				
				Seller seller = instantiateSeller(rs,department);
				sellers.add(seller);
			}
			return sellers;
		} catch (SQLException e) {
			
			throw new DbException(e.getMessage());
			
		} finally { 
			
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
		
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		
		return  new Department(rs.getInt("department_id"),rs.getString("DepName"));
	}
	
	private Seller instantiateSeller(ResultSet rs,Department department) throws SQLException {
		
		return new Seller(rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getDate("birthdate"),rs.getDouble("basesalary"),department);
		
	}

	
	@Override
	public List<Seller> findByDepartment(Department department) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		
		try {
			st = conn.prepareStatement(
					"SELECT sellers.*,departments.name as DepName "
					+ "FROM sellers INNER JOIN departments "
					+ "ON sellers.department_id = departments.id "
					+ "WHERE sellers.department_id=? "
					+ "ORDER BY sellers.name");
			
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			List<Seller> sellers = new ArrayList<>();
			while (rs.next()) {
				Seller seller = instantiateSeller(rs,department);
				sellers.add(seller);
			}
			return sellers;
		} catch (SQLException e) {
			
			throw new DbException(e.getMessage());
			
		} finally { 
			
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	
	}

}
