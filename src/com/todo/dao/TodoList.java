package com.todo.dao;

import java.util.*;

import com.google.gson.Gson;
import com.todo.service.DbConnect;
import java.sql.*;
import java.io.*;

public class TodoList {
	private List<TodoItem> list;
	Connection conn;

	public TodoList() {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, category, current_date, due_date, difficulty)"
				+ " values (?,?,?,?,?,?);";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, t.getDifficulty());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index) {
		String sql = "delete from list where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int editItem(TodoItem t) {
		String sql = "update list set title=?, memo=?, category=?, current_date=?, due_date=?, difficulty=?"
				+ " where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, t.getDifficulty());
			pstmt.setInt(7, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public void check(int index) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"select title, memo, category ,due_date , current_date, is_completed,percent,difficulty from list where id='"
							+ index + "'");
			while (rs.next()) {
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String category = rs.getString("category");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int percent = rs.getInt("percent");
				int difficulty = rs.getInt("difficulty");
				TodoItem t = new TodoItem(title, category, desc, due_date, is_completed, difficulty);
				t.setId(index);
				t.setCurrent_date(current_date);
				t.setPercent(percent);
				System.out.println(t.toString());
			}
		} catch (SQLException e) {
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
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int percent = rs.getInt("percent");
				int difficulty = rs.getInt("difficulty");
				TodoItem t = new TodoItem(title, category, desc, due_date, is_completed, difficulty);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setPercent(percent);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%" + keyword + "%";
		try {
			String sql = "SELECT * FROM list WHERE title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int percent = rs.getInt("percent");
				int difficulty = rs.getInt("difficulty");
				TodoItem t = new TodoItem(title, category, desc, due_date, is_completed, difficulty);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setPercent(percent);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
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
			pstmt.setInt(1, number);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int percent = rs.getInt("percent");
				int difficulty = rs.getInt("difficulty");
				TodoItem t = new TodoItem(title, category, desc, due_date, is_completed, difficulty);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setPercent(percent);
				list.add(t);
			}
			pstmt.close();
		} catch (SQLException e) {
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
			pstmt.setString(1, check);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String title = rs.getString("title");
				if (title.equals(check))
					duplicate = true;
			}
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return duplicate;
	}

	public int completeItem(int index) {
		String sql = "update list set is_completed=?, percent=?" + " where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, 100);
			pstmt.setInt(3, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int updatePercent(int index, int percent) {
		int count = 0;
		if (percent >= 100) {
			String sql = "update list set is_completed=?, percent=?" + " where id = ?;";
			PreparedStatement pstmt;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 1);
				pstmt.setInt(2, 100);
				pstmt.setInt(3, index);
				count = pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			String sql = "update list set is_completed=?, percent=?" + " where id = ?;";
			PreparedStatement pstmt;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 0);
				pstmt.setInt(2, percent);
				pstmt.setInt(3, index);
				count = pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return count;
	}

	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String category = rs.getString("category");
				list.add(category);
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getListCategory(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int percent = rs.getInt("percent");
				int difficulty = rs.getInt("difficulty");
				TodoItem t = new TodoItem(title, category, desc, due_date, is_completed, difficulty);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setPercent(percent);
				list.add(t);
			}
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			if (ordering == 0) {
				sql += " desc";
			}
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int percent = rs.getInt("percent");
				int difficulty = rs.getInt("difficulty");
				TodoItem t = new TodoItem(title, category, desc, due_date, is_completed, difficulty);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setPercent(percent);
				list.add(t);
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void listtojson() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Gson gson = new Gson();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int percent = rs.getInt("percent");
				int difficulty = rs.getInt("difficulty");
				TodoItem t = new TodoItem(title, category, desc, due_date, is_completed, difficulty);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setPercent(percent);
				list.add(t);
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String jsonstr = gson.toJson(list);
		System.out.println(jsonstr);

		try {
			FileWriter writer = new FileWriter("data.txt");
			writer.write(jsonstr);
			writer.close();
			System.out.println("파일에 저장되었습니다");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void tolist() {
		Gson gson = new Gson();
		String jsonstr = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("data.txt"));
			jsonstr = br.readLine();
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("파일에서 데이터를 가져왔습니다");
		TodoItem [] array = gson.fromJson(jsonstr, TodoItem[].class);
		List<TodoItem> list = Arrays.asList(array);
		System.out.println("list: " + list);
	}

}
