import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Course {
     @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "course_generator")
     @SequenceGenerator(name="course_generator",sequenceName = "course_seq")
     private Long Id;
     private String name;
     private LocalDate startDate;
     private LocalDate endDate;

     @ManyToOne
     @JoinColumn(name = "prof_id")
     private Professor professor;

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

    public Professor getProfessor() {
        return professor;
    }

    public Long getId() {
        return Id;
    }

    public void setProfessor(Professor professor) {
        // remove old association
        if(this.professor != null){
            this.professor.getCourses().remove(this);
        }
        this.professor = professor;
        if(professor != null){
            this.professor.getCourses().add(this);
        }
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
