import javax.persistence.*;
import java.util.Date;
/*
 Cumbersome handling of java.util.Date in JPA
 prefer java.time packages  with JPA
 */
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "course_generator")
    @SequenceGenerator(name="course_generator",sequenceName = "course_seq")
    private Long Id;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.TIME)
    private Date beginLecture;
    @Temporal(TemporalType.TIMESTAMP)
    private Date exam;
    @Version
    private long version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getBeginLecture() {
        return beginLecture;
    }

    public void setBeginLecture(Date beginLecture) {
        this.beginLecture = beginLecture;
    }

    public Date getExam() {
        return exam;
    }

    public void setExam(Date exam) {
        this.exam = exam;
    }

    public Long getId() {
        return Id;
    }

    public long getVersion() {
        return version;
    }
}
