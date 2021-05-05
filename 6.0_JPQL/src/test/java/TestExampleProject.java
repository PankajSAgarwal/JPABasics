import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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
    public void adhocJpqlFindAllCourses(){
        log.info("...adhocJpqlFindAllCourses...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        //create an adhoc query to select all courses
        TypedQuery<Course> q = em.createQuery("SELECT c FROM Course c",Course.class);
        List<Course> results = q.getResultList();

        Assert.assertEquals(10,results.size());

        for (Course c : results) {
            log.info("Course-->" + c);
        }
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void namedJpqlFindAllCourses(){
        log.info("...namedJpqlFindAllCourses...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // create a named query to select all courses

        TypedQuery<Course> q = em.createNamedQuery(Course.FIND_ALL_COURSES,Course.class);
        List<Course> results = q.getResultList();

        Assert.assertEquals(10, results.size());
        for (Course c : results) {
            log.info("Course-->" + c);
        }

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void adhocJpqlFindAllCourseIdsAndNames(){
        log.info("... adhocJpqlFindAllCourseIdsAndNames ...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // create an adhoc query to select all courses
        Query q = em.createQuery("SELECT c.id,c.name from Course c");
        List<Object[]> resultList = q.getResultList();

        Assert.assertEquals(10,resultList.size());

        for (Object[] r : resultList) {
            log.info(r[0] + "-" + r[1]);
        }

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void adhocJpqlFindAllCourseValues(){
        log.info("...adhocJpqlFindAllCourseValues...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        //create an adhoc query to select all courses

        TypedQuery<CourseValue> q = em.createQuery("SELECT new CourseValue(c.id,c.name) from Course c", CourseValue.class);
        List<CourseValue> results = q.getResultList();
        Assert.assertEquals(10, results.size());

        for (CourseValue course : results) {
            log.info(course);
        }
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void adhocJpqlFindOneCourseByName(){
        log.info("...adhocJpqlFindOneCourseByName...");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // create an adhoc query to select a course with a given name

        TypedQuery<Course> q = em.createQuery("SELECT c FROM Course c where c.name LIKE :name",Course.class);
        q.setParameter("name","Course 1%");
        List<Course> courses = q.getResultList();

        Assert.assertEquals(2, courses.size());

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void adhocJpqlFindCourseByNameOrId(){
        log.info("...adhocJpqlFindCourseByNameOrId...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        TypedQuery<Course> q = em.createQuery("SELECT c FROM Course c where c.id = :id OR c.name = :name", Course.class);
        q.setParameter("id", 3L);
        q.setParameter("name","Course 5");

        List<Course> courses = q.getResultList();

        Assert.assertEquals(2,courses.size());

        for (Course c : courses) {
            log.info("Course-->" + c);
        }

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void adhocJpqlJoin(){
        log.info("...adhocJpqlJoin...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        // create an adhoc query to select all courses
        // 1. explicit JOIN which generates INNER JOIN and is more efficient
        TypedQuery<Course> q = em.createQuery(
                " SELECT c FROM Course c JOIN c.professor p WHERE p.firstName = :name",Course.class);
//
        // 2. implicit join by using path parameter "." . Generates cross join and not that efficient
//        TypedQuery<Course> q = em.createQuery(
//                "SELECT c FROM Course c where c.professor.firstName = :name",Course.class);

        q.setParameter("name","John");

        Course course = q.getSingleResult();

        Assert.assertEquals(new Long(1), course.getId());

        log.info("Course-->"+ course);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void adhocJpqlCountCourses(){
        log.info("...adhocJpqlCountCourses...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("SELECT p,count(c) from Course c JOIN c.professor p GROUP BY p");
        List<Object[]> results = query.getResultList();
        
        Assert.assertEquals(4L, results.size());

        for (Object[] result : results) {
            log.info(result[0] + " teaches " + result[1] + " courses");
        }

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void adhocJpqlHaving(){
        log.info("...adhocJpqlHaving...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // create an adhoc query to select all courses
        TypedQuery<Professor> q = em.createQuery(
                "SELECT p FROM Course c JOIN c.professor p GROUP BY p HAVING count(c) > 2",Professor.class);
        List<Professor> results = q.getResultList();

        Assert.assertEquals(2,results.size());
        for (Professor professor : results) {
            log.info("Professor-->" + professor);
        }

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void adhocJpqlOrderBy(){
        log.info("...adhocJpqlOrderBy...");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        TypedQuery<Course> query = em.createQuery("SELECT c from Course c ORDER BY c.name DESC ", Course.class);
        List<Course> courses = query.getResultList();

        Assert.assertEquals(10L, courses.size());

        for (Course course : courses) {
            log.info("Course-->"+ course);
        }

        em.getTransaction().commit();
        em.close();

    }


    public Long createCourse() {
        return createCourse("Software Development 1");
    }

    public Long createCourse(String name) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Course course = new Course();
        course.setName(name);
        course.setStartDate(LocalDate.of(2018, 8, 15));
        course.setEndDate(LocalDate.of(2019, 5, 31));
        em.persist(course);

        em.getTransaction().commit();
        em.close();

        return course.getId();
    }

    public Long createProfessor() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Professor prof = new Professor();
        prof.setFirstName("Jane");
        prof.setLastName("Doe");
        em.persist(prof);

        em.getTransaction().commit();
        em.close();

        return prof.getId();
    }
}
