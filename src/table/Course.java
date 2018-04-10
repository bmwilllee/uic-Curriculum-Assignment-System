package table;

public class Course {
	private String courseTitle;
	private int group;
	private String student;
	private String instructor;
	private String ta1 = "";
	private String ta2 = "";
	private String time1 = "";
	private String time2 = "";
	
	public Course() {};
	
	public Course(String courseTitle, int groupNumber, String student, String instructor) {
		this.courseTitle = courseTitle;
		this.group = groupNumber;
		this.student = student;
		this.instructor = instructor;
	}
	
	public Course(String courseTitle, int groupNumber, String student, String instructor, String ta1, String ta2) {
		this.courseTitle = courseTitle;
		this.group = groupNumber;
		this.student = student;
		this.instructor = instructor;
		this.ta1 = ta1;
		this.ta2 = ta2;
	}
	
	public Course(String courseTitle, int groupNumber, String student, String instructor, String ta1, String ta2, String time1, String time2) {
		this.courseTitle = courseTitle;
		this.group = groupNumber;
		this.student = student;
		this.instructor = instructor;
		this.ta1 = ta1;
		this.ta2 = ta2;
		this.time1 = time1;
		this.time2 = time2;
	}
	
	public String getCourseTitle() {
		return courseTitle;
	}
	
	public int getGroup() {
		return group;
	}
	
	public String getStudent() {
		return student;
	}
	
	public String getInstructor() {
		return instructor;
	}
	
	public String getTa1() {
		return ta1;
	}
	
	public String getTa2() {
		return ta2;
	}
	
	public String getTime1() {
		return time1;
	}
	
	public String getTime2() {
		return time2;
	}
	
	public void setTa1(String ta1) {
		this.ta1 = ta1;
	}
	
	public void setTa2(String ta2) {
		this.ta2 = ta2;
	}
	
	public void setTime1(String time1) {
		this.time1 = time1;
	}
	
	public void setTime2(String time2) {
		this.time2 = time2;
	}
}
