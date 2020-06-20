package university;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {

	private String name;
	private String rector;
	private Student[] students = new Student[1000];
	private Course[] courses = new Course[50];
	private int n_students = 0;
	private int n_courses = 0;
	
	public University(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setRector(String first, String last){
		this.rector = first+" "+last;
	}
	
	
	public String getRector(){
		return rector;
	}
	
	public int enroll(String first, String last){
		if(this.n_students >= 1000)
			return -1;
		else {
	
			this.students[this.n_students] = new Student(this.n_students+10000, first+" "+last);
			this.n_students++;
			return this.students[this.n_students-1].getId();
		}
	}
	
	public String student(int id){
		if(id < 10000 || id >= (this.n_students+10000))
			return null;
		return id + " " + this.students[id-10000].getName(); 
	}
	
	
	public int activate(String title, String teacher){
		if(n_courses >= 50)
			return -1;
		else {
			this.courses[this.n_courses] = new Course(this.n_courses+10, title, teacher);
			this.n_courses++;
			return this.courses[this.n_courses-1].getId();
		}
	}
	
	public String course(int code){
		if(code < 10 || code >= (this.n_courses+10))
			return null;
		return code+","+this.courses[code-10].getTitle()+","+this.courses[code-10].getTeacher();
	}
	
	
	public void register(int studentID, int courseCode){
		if(students[studentID-10000].ck_disp() && courses[courseCode-10].ck_disp()) {
			students[studentID-10000].register(courses[courseCode-10]);
			courses[courseCode-10].register(students[studentID-10000]);
		}
	}
	
	public String listAttendees(int courseCode){
		if(courseCode < 10 || courseCode >= (this.n_courses+10))
			return null;
		String str = course(courseCode)+"\n";
		str = str+courses[courseCode-10].listAttendees();
		
		return str;
	}

	public String studyPlan(int studentID){
		if(studentID < 10000 || studentID >= (this.n_students+10000))
			return null;
		String str = student(studentID)+"\n";
		str = str+students[studentID-10000].studeyPlan();
		return str;
	}
}
