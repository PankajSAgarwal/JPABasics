import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TestExampleProject_javatimeAPI {
    Logger logger = Logger.getLogger(this.getClass().getName());
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
    public void persistCourse() {
        logger.info("... persist course ...");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        // create a new course entity
        Course2 course2 = new Course2();
        course2.setName("Software Developemnt1");
        course2.setStartDate(LocalDate.of(2018,8, 15));
        course2.setBeginLecture(LocalTime.of(10,30));
        course2.setExam(LocalDateTime.of(2019,3,22,10,30));

        // persist entity
        em.persist(course2);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void findCourse(){
        logger.info("... find course");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // get the primary key of a course
        Long courseId = createCourse();
        // load Course entity by primary key
        Course2 course2 = em.find(Course2.class,courseId);
        logger.info(course2);

        em.getTransaction().commit();
        em.close();
    }

    private Long createCourse() {
        logger.info("... create course ...");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        // create a new course entity
        Course2 course2 = new Course2();
        course2.setName("Software Developemnt1");
        course2.setStartDate(LocalDate.of(2018,8, 15));
        course2.setBeginLecture(LocalTime.of(10,30));
        course2.setExam(LocalDateTime.of(2019,3,22,10,30));

        // persist entity
        em.persist(course2);
        em.getTransaction().commit();
        em.close();
        return course2.getId();
    }
}
