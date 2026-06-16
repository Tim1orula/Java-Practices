import java.util.*;
class Student1{
    private int    id;
    private String name;
    private String email;

    public Student1(int id, String name, String email) {
        this.id    = id;
        this.name  = name;
        this.email = email;
    }

    public int    getId()    { return id;    }
    public String getName()  { return name;  }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format("Student{id=%d, name='%s', email='%s'}", id, name, email);
    }
}
class StudentManager{
    ArrayList<Student1>students=new ArrayList();
    HashSet<String>courses=new HashSet();
    HashMap<Integer,ArrayList<Integer>>grades=new HashMap();
    HashMap<Integer,HashSet<String>>enrollments=new HashMap();
    public void addStudents(int id ,String name,String email){
      for(Student1 t:students){
        if (t.getId()==id){
          System.out.println("The id "+id+" already exists");
          return;
        }
      }
      Student1 newStudent=new Student1(id,name,email);
      students.add(newStudent);
      System.out.println("Student added to system successfully.");
    }
    public void enrollStudents(int studentId ,String courseCode){
      courses.add(courseCode);
      enrollments.putIfAbsent(studentId, new HashSet<>());
      enrollments.get(studentId).add(courseCode);
      System.out.println("Student " + studentId + " enrolled in course " + courseCode);
    }
    /*public void grading(String studentId ,String grade){
      grades.putIfAbsent(studentId, new ArrayList<>());
      grades.get(id).add(grade);
      System.out.println("Grade recorded ");
    }*/
    
    public void grading(int studentId, String courseCode, int grade) {
        if (!enrollments.containsKey(studentId)) {
            System.out.println("Error: No student found with ID " + studentId);
            return;
        }
        HashSet<String> courses = enrollments.get(studentId);
        if (!enrollments.containsKey(studentId)){
            System.out.println("Error: Student " + studentId
                               + " is not enrolled in " + courseCode);
              return;
        }
        if (grade < 0 || grade > 100) {
            System.out.println("Error: Grade must be between 0 and 100.");
        }
        grades.putIfAbsent(studentId, new ArrayList<>());
        grades.get(studentId).add(grade);
      System.out.println("Grade recorded ");
    }
    public void sortedReport(){
      //sort by names
      if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
      TreeMap<String,Student1>sortedValues=new TreeMap();
      for (Student1 t : students) {
            sortedValues.put(t.getName(), t);
        }
      System.out.println("\n--- Sorted Student Report ---");
      for (Student1 t : sortedValues.values()) {
            System.out.println(t);
        }
    }
    public void findStudentByCourse(String courseCode){
      ArrayList<String>courseMates=new ArrayList();
      for (Student1 t : students){
        if(enrollments.containsKey(t.getId())&& enrollments.get(t.getId()).contains(courseCode))
        {
          courseMates.add(t.getName());
        }
      }
      if (courseMates.isEmpty()){
        System.out.println("No students registered in course "+courseCode);
      }
      System.out.println("Students in course "+courseCode+ " inlude "+courseMates);
    }
    public void averageGrades(){
      System.out.println("\n---Student Grade Averages------");
      for (Student1 t: students){
        ArrayList<Integer>studentGrades=grades.get(t.getId());
        if (studentGrades==null||studentGrades.isEmpty()){
          System.out.println("No grades recorded");
        }
        else{
          double total=0.0;
          for(int i:studentGrades){
            total+=i;
          }
          double average=total/studentGrades.size();
          System.out.printf("%s: %.2f%n ", t.getName(),average);
        }
      }
    }
    public void listAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses registered yet.");
        } else {
            List<String> sorted = new ArrayList<>(courses);
            Collections.sort(sorted);
            System.out.println("All registered courses: " + sorted);
        }
    }
  }

public class FrameworksStudentsManagementSys{
  public static void main(String[] args)
  {
    
    Scanner read =new Scanner(System.in);
    StudentManager s =new StudentManager();
    System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   Student Management System (Java)   ║");
        System.out.println("╚══════════════════════════════════════╝");
  
     while (true) {
            printMenu();
            System.out.print("> ");
            String choice = read.nextLine().trim();

            switch (choice) {

                case "1": { // Add student
                    int    id    = promptInt(read,    "Enter student ID: ");
                    String name  = promptStr(read,    "Enter student name: ");
                    String email = promptStr(read,    "Enter student email: ");
                    s.addStudents(id, name, email);
                    break;
                }

                case "2": { // Enroll in course
                    int  id   = promptInt(read, "Enter student ID: ");
                    String code = promptStr(read, "Enter course code: ");
                    s.enrollStudents(id, code);
                    break;
                }

                case "3": { // Record grade
                    int   id    = promptInt(read, "Enter student ID: ");
                    String code  = promptStr(read, "Enter course code: ");
                    int    grade = promptInt(read, "Enter grade (0-100): ");
                    s.grading(id, code, grade);
                    break;
                }

                case "4": // Sorted report
                  s.sortedReport();
                    break;

                case "5": { // Find by course
                    String code = promptStr(read, "Enter course code: ");
                    s.findStudentByCourse(code);
                    break;
                }

                case "6": // Averages
                    s.averageGrades();
                    break;

                case "7": // List all courses (bonus)
                    s.listAllCourses();
                    break;

                case "8": // Exit
                    System.out.println("Goodbye!");
                    read.close();
                    return;

                default:
                    System.out.println("Invalid option. Please choose 1-8.");
            }
            System.out.println();
        }
  }
  private static void printMenu() {
        System.out.println("┌─────────────────────────────────┐");
        System.out.println("│  1. Add Student                 │");
        System.out.println("│  2. Enroll Student in Course    │");
        System.out.println("│  3. Record Grade                │");
        System.out.println("│  4. Generate Sorted Report      │");
        System.out.println("│  5. Find Students by Course     │");
        System.out.println("│  6. Calculate Student Averages  │");
        System.out.println("│  7. List All Courses            │");
        System.out.println("│  8. Exit                        │");
        System.out.println("└─────────────────────────────────┘");
    }

    // ── Input helpers ─────────────────────────

    private static int promptInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static String promptStr(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }
}