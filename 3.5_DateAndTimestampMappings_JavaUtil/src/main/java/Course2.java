import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/*
No specific annotation required for LocalDate,LocalTime and LocalDateTime
as JPA 2.2 supports these API of java.time
 */
@Entity
public class Course2 {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "course_generator")
    @SequenceGenerator(name="course_generator",sequenceName = "course_seq")
    private Long Id;
    private String name;
    private LocalDate startDate;
    private LocalTime beginLecture;
    private LocalDateTime exam;
    @Version
    private long version;

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

    public LocalTime getBeginLecture() {
        return beginLecture;
    }

    public void setBeginLecture(LocalTime beginLecture) {
        this.beginLecture = beginLecture;
    }

    public LocalDateTime getExam() {
        return exam;
    }

    public void setExam(LocalDateTime exam) {
        this.exam = exam;
    }

    public Long getId() {
        return Id;
    }

    public long getVersion() {
        return version;
    }
}
