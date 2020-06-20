package university;

public class Course {
	private int id;
	private String title;
	private String teacher;
	private Student[] students = new Student[100];
	private int n_students = 0;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	
	public Course(int id, String title, String teacher) {
		this.id = id;
		this.title = title;
		this.teacher = teacher;
	}
	
	public boolean ck_disp() {
		if(n_students >= 100)
			return false;
		return true;
	}
	public void register(Student s) {
		students[n_students] = s;
		n_students ++;
	}
	
	public String listAttendees() {
		String str ="";
		for(Student s:students) {
			if(s == null)
				break;
			str = str+s.getId()+" "+s.getName()+"\n";
		}
		return str;
	}
	
}
