import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestExampleProject {
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
    public void createStudent(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Student student = new Student();
        student.setFirstName("Peter");
        student.setLastName("Doe");
        student.setState(StudentStatus.ENROLLED);
        em.persist(student);
        em.getTransaction().commit();
        em.close();
    }
}
