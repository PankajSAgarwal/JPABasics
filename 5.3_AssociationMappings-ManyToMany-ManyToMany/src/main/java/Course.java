import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "course_generator")
    @SequenceGenerator(name = "course_generator",sequenceName = "course_seq")
    private Long Id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToMany
    @JoinTable(name="enrollments",
               joinColumns = @JoinColumn(name="c_id"),
                inverseJoinColumns = @JoinColumn(name="s_id"))

    private Set<Student> students = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Long getId() {
        return Id;
    }

    public void enrollStudent(Student student){
        this.students.add(student);
        student.getCourses().add(this);
    }
    public void unenrollStudent(Student student){
        this.students.remove(student);
        student.getCourses().remove(this);
    }
    @Override
    public String toString() {
        return "Course{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
