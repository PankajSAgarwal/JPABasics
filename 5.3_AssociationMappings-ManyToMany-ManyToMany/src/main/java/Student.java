import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "student_generator")
    @SequenceGenerator(name="student_generator",sequenceName = "student_seq")
    private Long Id;
    private String firstName;
    private String lastName;
    private StudentStatus state;
    @ManyToMany(mappedBy = "students")
    private Set<Course> courses= new HashSet<>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public StudentStatus getState() {
        return state;
    }

    public void setState(StudentStatus state) {
        this.state = state;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Long getId() {
        return Id;
    }

    public void enrollToCourse(Course course){
        this.courses.add(course);
        course.getStudents().add(this);
    }

    public void unenrollToCourse(Course course){
        this.courses.remove(course);
        course.getStudents().remove(this);
    }

    @Override
    public String toString() {
        return "Student{" +
                "Id=" + Id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", state=" + state +
                '}';
    }
}
