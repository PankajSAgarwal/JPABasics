import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "student_generator")
    @SequenceGenerator(name = "student_generator",sequenceName = "student_seq")
    private Long id;

    private String firstName;

    private String lastName;

    private StudentStatus state;

    @ManyToMany
    @JoinTable(name="enrollments",
    joinColumns = @JoinColumn(name="c_id"),
    inverseJoinColumns = @JoinColumn(name="s_id"))
    private Set<Course> courses = new HashSet<>();

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
        return id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", state=" + state +
                ", courses=" + courses +
                '}';
    }
}
