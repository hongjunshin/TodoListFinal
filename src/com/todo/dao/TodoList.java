package com.todo.dao;

import java.util.*;
import com.todo.service.DbConnect;
import java.sql.*;
import java.io.*;

public class TodoList {
	private List<TodoItem> list;
	Connection conn;

	public TodoList() {
		//this.list = new ArrayList<TodoItem>();
		this.conn = DbConnect.getConnection();
	}

	public int getCount() {
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, category, current_date, due_date)" + " values (?,?,?,?,?);";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,t.getTitle());
			pstmt.setString(2,t.getDesc());
			pstmt.setString(3,t.getCategory());
			pstmt.setString(4,t.getCurrent_date());
			pstmt.setString(5,t.getDue_date());
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index) {
		String sql = "delete from list where id = ?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,index);
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int editItem(TodoItem t) {
		String sql = "update list set title=?, memo=?, category=?, current_date=?, due_date=?"+" where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,t.getTitle());
			pstmt.setString(2,t.getDesc());
			pstmt.setString(3,t.getCategory());
			pstmt.setString(4,t.getCurrent_date());
			pstmt.setString(5,t.getDue_date());
			pstmt.setInt(6,t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	public void check(int index) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select title, memo, category ,due_date , current_date, is_completed from list where id='" +  index + "'");
			while(rs.next()) {
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String category = rs.getString("category");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(title,category,desc,due_date,is_completed);
				t.setId(index);
				t.setCurrent_date(current_date);
				System.out.println(t.toString());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(title,category,desc,due_date,is_completed);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%"+keyword+"%";
		try {
			String sql = "SELECT * FROM list WHERE title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,keyword);
			pstmt.setString(2,keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(title,category,desc,due_date,is_completed);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getList(int number) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE is_completed like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,number);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(title,category,desc,due_date,is_completed);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public TodoItem get(int index) {
		return list.get(index);
	}

	public boolean isDuplicate(String check) {
		PreparedStatement pstmt;
		boolean duplicate = false;
		try {
			String sql = "SELECT * FROM list WHERE title = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,check);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String title = rs.getString("title");
				if(title.equals(check))
					duplicate = true;
			}
			pstmt.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return duplicate;
	}
	
	public int completeItem(int index) {
		String sql = "update list set is_completed=?"+" where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,1);
			pstmt.setInt(2,index);
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<String> getCategories(){
		ArrayList <String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String category = rs.getString("category");
				list.add(category);
			}
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(title,category,desc,due_date,is_completed);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			if(ordering == 0) {
				sql += " desc";
			}
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				TodoItem t = new TodoItem(title,category,desc,due_date,is_completed);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/*public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, category, current_date, due_date)"+ " values (?,?,?,?,?);";
			int records = 0;
			while((line=br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(line,"##");
				String title = st.nextToken();
				String category = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,title);
				pstmt.setString(2,desc);
				pstmt.setString(3,category);
				pstmt.setString(4,current_date);
				pstmt.setString(5,due_date);
				int count = pstmt.executeUpdate();
				if(count>0) 
					records++;
				pstmt.close();
			}
			System.out.println(records+" ¿Ã ¿˙¿Âµ ");
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	
}
