package com.todo.service;

import java.io.Writer;
import java.io.*;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, category, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print(
				 "========== 항목 추가\n"
				+ "제목을 입력하시오 : ");
		
		title = sc.next().trim();
		if (list.isDuplicate(title)) {
			System.out.println("이미 같은 제목이 있습니다");
			return;
		}
		System.out.print("카테고리를 입력하시오 : ");
		category = sc.next();
		sc.nextLine();
		System.out.print("내용을 입력하시오 : "); 
		desc = sc.nextLine().trim();
		System.out.print("마감일자를 입력하시오 : ");
		due_date = sc.next().trim();
		
		TodoItem t = new TodoItem(title, category,desc,due_date,0);
		if(list.addItem(t)>0)
			System.out.println("추가되었습니다."); 
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.print(
				"========== 항목 제거\n"
				+ "제거할 항목의 번호를 쓰시오 : "
				);
		int number = sc.nextInt();
		
		l.check(number);
		System.out.print("삭제하시겠습니까?(y/n)");
		String question = sc.next();
		if(question.equals("y")) {
			if(l.deleteItem(number)>0)
				System.out.println("삭제되었습니다."); 
			else
				System.out.println("삭제에 실패했습니다."); 
		}
		else {
			System.out.println("삭제를 취소합니다."); 
		}
			
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print(
				"========== 항목 수정\n"
				+ "수정할 항목의 번호를 입력하시오 : ");
		int number = sc.nextInt();
		
		l.check(number);

		System.out.print("새로운 제목 : ");
		String new_title = sc.next();
		if (l.isDuplicate(new_title)) {
			System.out.println("같은 제목을 가진 항목이 있습니다");
			return;
		}
		System.out.print("새로운 카테고리 : ");
		String category = sc.next();
		sc.nextLine();
		System.out.print("새로운 내용 : ");
		String new_description = sc.nextLine().trim();
		System.out.print("새로운 마감일자 : ");
		String due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title,category, new_description,due_date,0);
		t.setId(number);
		if(l.editItem(t)>0)
			System.out.println("항목이 수정되었습니다.");
		else
			System.out.println("항목이 수정에 실패하였습니다.");
	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록, 총 "+ l.getCount() +"개]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l,String orderby, int ordering) {
		System.out.println("[전체 목록, 총 "+ l.getCount() +"개]");
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, int number) {
		int count = 0;
		for (TodoItem item : l.getList(1)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("총 "+count+"개의 항목을 찾았습니다");
	}
	
	public static void findList(TodoList l, String keyword) {
		int count = 0;
		for (TodoItem item : l.getList(keyword)) {	
				System.out.println(item.toString());
				count++;
		}	
		System.out.println("총 "+count+"개의 항목을 찾았습니다");
	}
	public static void find_cate(TodoList l, String keyword) {
		int count=0;
		for (TodoItem item : l.getListCategory(keyword)) {
				System.out.println(item.toString());
				count++;
		}	
		System.out.println("총 "+count+"개의 항목을 찾았습니다");
	}
	
	public static void listCate(TodoList l) {
		int count =0;
		for(String item : l.getCategories()) {
			System.out.print(item+" ");
			count++;
		}
		System.out.println("\n총 "+count+"개의 항목을 찾았습니다");
	}
	
	public static void completeItem(TodoList l,int index) {
		if(l.completeItem(index)>0)
			System.out.println("완료 체크를 하였습니다"); 
	}
		
	}
	
	
