import billsPaymentSystem.BankAccount;
import billsPaymentSystem.BillingDetail;
import billsPaymentSystem.CreditCard;
import billsPaymentSystem.User;
import gringottsDatabase.WizardDeposit;
import hospitalDatabase.Diagnose;
import hospitalDatabase.Patient;
import hospitalDatabase.Record;
import hospitalDatabase.Visitation;
import salesDatabase.Customer;
import salesDatabase.Location;
import salesDatabase.Product;
import salesDatabase.Sale;
import universitySystem.Course;
import universitySystem.Student;
import universitySystem.Teacher;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

public class Engine implements Runnable {
    private EntityManager em;

    public Engine(String persistenceUnit) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        this.em = emf.createEntityManager();
    }

    @Override
    public void run() {
        this.em.getTransaction().begin();
//        this.gringottsDatabaseTest();
//        this.salesDatabaseTest();
//        this.universitySystemTest();
//        this.hospitalDatabaseTestAndCLI();
//        this.billsPaymentSystemTest();

        this.em.getTransaction().commit();
        this.em.close();
    }

    //01. Gringotts Database
    private void gringottsDatabaseTest() {
        WizardDeposit wizardDeposit = new WizardDeposit("Someone", "almost got me", "Note",15,
                "Some Wand Creator", (short)300, "Nai-qkata", new Date(), BigDecimal.ONE,
                BigDecimal.TEN, BigDecimal.ZERO, new Date(), false);
        this.em.persist(wizardDeposit);
    }

    //02.Sales Database
    private void salesDatabaseTest() {
        Location location = new Location();
        location.setLocation("Qmbol");

        Product product = new Product();
        product.setName("Voda");
        product.setPrice(BigDecimal.ONE);
        product.setQuantity(1);

        Customer customer = new Customer();
        customer.setName("Vasil");
        customer.setEmail("Vasil@email");
        customer.setCreditCardNumber("Nqkakuv");

        Sale sale = new Sale();
        sale.setLocation(location);
        sale.setProduct(product);
        sale.setCustomer(customer);
        sale.setDate(new Date());

        this.em.persist(sale);
    }

    //03. University System
    private void universitySystemTest() {
        Teacher teacher = new Teacher("Gospodin", "Gospodinov", 99999, "Gospodinov@email",
                new BigDecimal("6"));

        Student student = new Student("Uchenik", "Uchenikov", 888888,
                2.0, 0);

        Course course = new Course("Matematika", "Gadno jivotno - btw ne znam dati", new Date(),
                new Date(), 15);

        course.setTeacher(teacher);
        HashSet<Student> students = new HashSet<>();
        students.add(student);

        course.setStudents(students);

        student.setCourses(new HashSet<>(Collections.singleton(course)));

        this.em.persist(student);
        this.em.persist(course);
    }

    //04. Hospital Database
    private void hospitalDatabaseTestAndCLI() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Enter patient first name: ");
            String fistName = br.readLine();
            System.out.print("Enter patient last name: ");
            String lastName = br.readLine();
            System.out.print("Enter patient address: ");
            String address = br.readLine();
            System.out.print("Enter patient email: ");
            String email = br.readLine();
            System.out.print("Enter if patient is insured: ");
            boolean isInsured = Boolean.parseBoolean(br.readLine());
            System.out.print("Enter visitation reason: ");
            String visitationReason = br.readLine();
            System.out.print("Enter patient diagnose: ");
            String diagnoseComment = br.readLine();
            System.out.print("Enter patient diagnose description: ");
            String diagnoseDescription = br.readLine();
            System.out.print("Enter prescribed medicine: ");
            String medicine = br.readLine();

            Patient patient = new Patient(fistName, lastName, address,
                    email, new Date(), new byte[10], isInsured);

            Visitation visitation = new Visitation(new Date(), visitationReason);

            Diagnose diagnose = new Diagnose(diagnoseComment, diagnoseDescription);

            Record record = new Record(patient, visitation, diagnose, medicine);

            this.em.persist(record);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //05. Bills Payment System
    private void billsPaymentSystemTest() {
        User user = new User("First", "last", "@gmai", "stronk");
        CreditCard creditCard = new CreditCard(123, user,
                "Type", "May", "3000");
        BankAccount bankAccount = new BankAccount(1234, user,
                "BankName", "wtf is a swiftcode");

        this.em.persist(creditCard);
        this.em.persist(bankAccount);
    }
}
