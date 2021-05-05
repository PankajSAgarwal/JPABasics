import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;

public class TestExampleProject_javautilDate {
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
    public void persistCourse(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        // create a new course entity
        Course course = new Course();
        course.setName("Software Developemnt1");
        //2018/08/15
        course.setStartDate(new Date(118,7,15));
        //10 hrs and 30 minutes
        course.setBeginLecture(new Date(10*60*60*1000+30*60*1000));
        // 2019/03/22 10.30 AM
        course.setExam(new Date(119,2,22,10,30));
        //persist the course entity
        em.persist(course);
        em.getTransaction().commit();
        em.close();
    }
}

