import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Professor {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="professor_generator")
    @SequenceGenerator(name="professor_generator",sequenceName = "professor_seq")
    private Long Id;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "professor")
    private List<Course> courses = new ArrayList<>();

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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course){
        course.setProfessor(this);
    }

    public void removeCourse(Course course){
        course.setProfessor(null);
    }

    public Long getId() {
        return Id;
    }
}
