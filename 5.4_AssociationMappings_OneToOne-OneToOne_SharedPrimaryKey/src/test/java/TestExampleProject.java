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
    public void init() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }



    @After
    public void close() {
        emf.close();
    }

    @Test
    public void manageOneToOneAssociation() {
        log.info("... manageOneToOneAssociation ...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Course course = new Course();
        course.setName("Software Development 1");
        course.setStartDate(LocalDate.of(2018, 8, 15));
        course.setEndDate(LocalDate.of(2019, 5, 31));
        em.persist(course);

        Curriculum curriculum = new Curriculum();
        curriculum.setDescription("This is a course curriculum");
        curriculum.setCourse(course);
        em.persist(curriculum);

        // add the association
        curriculum.setCourse(course);

        em.getTransaction().commit();
        em.close();
    }
}
