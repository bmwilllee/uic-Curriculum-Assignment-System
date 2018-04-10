package table;

import java.util.ArrayList;

public class Table {
	private ArrayList<Course> courses;
	private String semester;
	int size;
	
	public Table() {};
	
	public Table(String semester){
		this.semester = semester;
	}
	
	public Table(String semester, ArrayList<Course> courses) {
		this.semester = semester;
		this.courses = courses;
	}
	
	public int courseSize() {
		size = courses.size();
		return size;
	}
	
	public String getTitle() {
		return semester;
	}
	public ArrayList<Course> getCourses(){
		return courses;
	}
}
