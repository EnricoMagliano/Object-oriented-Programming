package university;

public class Student {
	private int id;
	private String name;
	private Course[] courses = new Course[25];
	private int n_courses = 0;

	public Student(int id, String name) {
		
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean ck_disp() {
		if(n_courses >= 25)
			return false;
		return true;
	}
	public void register(Course c) {
		courses[n_courses] = c;
		n_courses ++;
	}
	
	public String studeyPlan() {
		String str ="";
		for(Course c:courses) {
			if(c == null)
				break;
			str = str+c.getId()+","+c.getTitle()+","+c.getTeacher()+"\n";
		}
		return str;
	}

}
