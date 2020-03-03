import entities.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Student s = new Student();
        s.setName("Vasko");
        s.setRegistrationDate(Date.valueOf("1990-03-04"));
        em.persist(s);
        s = em.find(Student.class, 6);
        em.remove(s);
        em.getTransaction().commit();
    }
}
