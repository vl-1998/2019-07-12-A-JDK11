package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public void listFood (Map<Integer,Food> idMap, int x) {
		String sql = "select p.food_code, f.display_name, " + 
				"count(p.portion_id) as n_porzioni " + 
				"from portion p, food f " + 
				"where p.food_code=f.food_code " + 
				"group by food_code " + 
				"having n_porzioni=?";
		Connection conn = DBConnect.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, x);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				if(!idMap.containsKey(res.getInt("p.food_code"))){
					Food f = new Food(res.getInt("p.food_code"), res.getString("f.display_name"));
					idMap.put(res.getInt("p.food_code"), f);
				}
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}
	
	public List<Adiacenza> listAdiacenze(){
		String sql = "select avg(co1.condiment_calories) as peso, f1.food_code, f2.food_code " + 
				"from condiment co1, food_condiment f1, food_condiment f2 " + 
				"where f1.condiment_code=f2.condiment_code " + 
				"and f1.condiment_code=co1.condiment_code " + 
				"and f1.food_code>f2.food_code " + 
				"group by f1.food_code, f2.food_code";
		Connection conn = DBConnect.getConnection();
		
		List <Adiacenza> result = new ArrayList<Adiacenza>();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while (res.next()) {
				
					Adiacenza a = new Adiacenza (res.getInt("f1.food_code"), res.getInt("f2.food_code"), res.getDouble("peso"));
					result.add(a);
				}
			
			conn.close();
			return result;
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
}
