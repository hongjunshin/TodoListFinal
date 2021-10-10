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
				 "========== �׸� �߰�\n"
				+ "������ �Է��Ͻÿ� : ");
		
		title = sc.next().trim();
		if (list.isDuplicate(title)) {
			System.out.println("�̹� ���� ������ �ֽ��ϴ�");
			return;
		}
		System.out.print("ī�װ��� �Է��Ͻÿ� : ");
		category = sc.next();
		sc.nextLine();
		System.out.print("������ �Է��Ͻÿ� : "); 
		desc = sc.nextLine().trim();
		System.out.print("�������ڸ� �Է��Ͻÿ� : ");
		due_date = sc.next().trim();
		
		TodoItem t = new TodoItem(title, category,desc,due_date,0);
		if(list.addItem(t)>0)
			System.out.println("�߰��Ǿ����ϴ�."); 
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.print(
				"========== �׸� ����\n"
				+ "������ �׸��� ��ȣ�� ���ÿ� : "
				);
		int number = sc.nextInt();
		
		l.check(number);
		System.out.print("�����Ͻðڽ��ϱ�?(y/n)");
		String question = sc.next();
		if(question.equals("y")) {
			if(l.deleteItem(number)>0)
				System.out.println("�����Ǿ����ϴ�."); 
			else
				System.out.println("������ �����߽��ϴ�."); 
		}
		else {
			System.out.println("������ ����մϴ�."); 
		}
			
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print(
				"========== �׸� ����\n"
				+ "������ �׸��� ��ȣ�� �Է��Ͻÿ� : ");
		int number = sc.nextInt();
		
		l.check(number);

		System.out.print("���ο� ���� : ");
		String new_title = sc.next();
		if (l.isDuplicate(new_title)) {
			System.out.println("���� ������ ���� �׸��� �ֽ��ϴ�");
			return;
		}
		System.out.print("���ο� ī�װ� : ");
		String category = sc.next();
		sc.nextLine();
		System.out.print("���ο� ���� : ");
		String new_description = sc.nextLine().trim();
		System.out.print("���ο� �������� : ");
		String due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title,category, new_description,due_date,0);
		t.setId(number);
		if(l.editItem(t)>0)
			System.out.println("�׸��� �����Ǿ����ϴ�.");
		else
			System.out.println("�׸��� ������ �����Ͽ����ϴ�.");
	}

	public static void listAll(TodoList l) {
		System.out.println("[��ü ���, �� "+ l.getCount() +"��]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l,String orderby, int ordering) {
		System.out.println("[��ü ���, �� "+ l.getCount() +"��]");
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
		System.out.println("�� "+count+"���� �׸��� ã�ҽ��ϴ�");
	}
	
	public static void findList(TodoList l, String keyword) {
		int count = 0;
		for (TodoItem item : l.getList(keyword)) {	
				System.out.println(item.toString());
				count++;
		}	
		System.out.println("�� "+count+"���� �׸��� ã�ҽ��ϴ�");
	}
	public static void find_cate(TodoList l, String keyword) {
		int count=0;
		for (TodoItem item : l.getListCategory(keyword)) {
				System.out.println(item.toString());
				count++;
		}	
		System.out.println("�� "+count+"���� �׸��� ã�ҽ��ϴ�");
	}
	
	public static void listCate(TodoList l) {
		int count =0;
		for(String item : l.getCategories()) {
			System.out.print(item+" ");
			count++;
		}
		System.out.println("\n�� "+count+"���� �׸��� ã�ҽ��ϴ�");
	}
	
	public static void completeItem(TodoList l,int index) {
		if(l.completeItem(index)>0)
			System.out.println("�Ϸ� üũ�� �Ͽ����ϴ�"); 
	}
		
	}
	
	
