import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class TestExampleProject {
    Logger log = Logger.getLogger(this.getClass().getName());
    private EntityManagerFactory emf;

    @Before
    public void init(){
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }
    @After
    public void close(){
        emf.close();
    }

    @Test
    public void manageOneToOneAssociation(){
        log.info("... manageOneToOneAssociation ...");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // get the primary key of a course and a Curriculum
        Long courseId= createCourse();
        Long curriculumId = createCurriculum();

        // load Course and Curriculum entities by primary key

        Course course = em.find(Course.class,courseId);
        Curriculum curriculum = em.find(Curriculum.class,curriculumId);

        // add the association
        curriculum.setCourse(course);
        course.setCurriculum(curriculum);

        em.getTransaction().commit();
        em.close();

    }

    @Test
    public void useOneToOneAssociation(){
        log.info("... useOneToOneAssociation ...");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Long courseId = prepareTestData();

        Course course = em.find(Course.class, courseId);
        log.info("Curriculum.description: " + course.getCurriculum().getDescription());

        em.getTransaction().commit();
        em.close();
    }

    private Long createCourse() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Course course = new Course();
        course.setName("Software Development 1");
        course.setStartDate(LocalDate.of(2018, 8, 15));
        course.setEndDate(LocalDate.of(2019, 5, 31));
        em.persist(course);

        em.getTransaction().commit();
        em.close();

        return course.getId();
    }

    private Long createCurriculum() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Curriculum c = new Curriculum();
        c.setDescription("This is a course curriculum");
        em.persist(c);

        em.getTransaction().commit();
        em.close();

        return c.getId();
    }

    private Long prepareTestData() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // get the primary key of a course and a professor
        Long courseId = createCourse();
        Long curriculumId = createCurriculum();

        // load Course and Curriculum entities by primary key
        Course course = em.find(Course.class, courseId);
        Curriculum curriculum = em.find(Curriculum.class, curriculumId);

        // add the association
        curriculum.setCourse(course);
        course.setCurriculum(curriculum);

        em.getTransaction().commit();
        em.close();

        return course.getId();
    }

}
