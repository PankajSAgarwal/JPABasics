import javax.persistence.*;

@Entity
public class Curriculum {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "curriculum_generator")
    @SequenceGenerator(name = "curriculum_generator", sequenceName = "curriculum_seq")
    private Long id;

    private String description;

    @OneToOne
    @JoinColumn(name="c_id")
    private Course course;
    @Version
    private int version;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }
}
