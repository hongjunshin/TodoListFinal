package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;
import com.todo.service.DbConnect;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean quit = false;
		Menu.displaymenu();
		do {
			Menu.prompt();
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
			
			case "find":
				String word = sc.nextLine().trim();
				TodoUtil.findList(l, word);
				break;
				
			case "find_cate":
				String cateword = sc.nextLine().trim();
				TodoUtil.find_cate(l, cateword);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name":
				System.out.println("제목순으로 정렬 : ");
				TodoUtil.listAll(l,"title",1);
				break;

			case "ls_name_desc":
				System.out.println("제목역순으로 정렬 : ");
				TodoUtil.listAll(l,"title",0);
				break;
				 
			case "ls_date":
				System.out.println("날짜순으로 정렬 : ");
				TodoUtil.listAll(l,"due_date",1);
				break;
				
			case "ls_date_desc":

				System.out.println("날짜역순으로 정렬 : ");
				TodoUtil.listAll(l,"due_date",0);
				break;
				
			case "ls_cate":
				TodoUtil.listCate(l);
				break;
			
			case "ls_comp":
				System.out.println("완료된 항목 정렬 : ");
				TodoUtil.listAll(l,1);
				break;
				
			case "comp":
				int number = sc.nextInt();
				TodoUtil.completeItem(l,number);
				break;
				
			case "help" :
				Menu.displaymenu();
				break;

			case "exit":
				quit = true;
				break;

			default:
				System.out.println("정확한 명령어를 입력해주세요. 도움말이 필요하시면 - help");
				break;
			}
			
		} while (!quit);
		DbConnect.closeConnection();
	}
}
