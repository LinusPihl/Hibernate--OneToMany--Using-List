import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;

@Entity
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Student> students;

    public Tutor() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> getStudents() {
        return students;
    }

    // Getters and setters for other attributes
}

@Entity
class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Getters and setters for attributes
}

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("your-persistence-unit");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // Create a tutor and students
        Tutor tutor = new Tutor();
        tutor.setName("John Doe");

        Student student1 = new Student();
        student1.setName("Alice");

        Student student2 = new Student();
        student2.setName("Bob");

        // Add students to the tutor's list
        tutor.addStudent(student1);
        tutor.addStudent(student2);

        // Persist the tutor and students
        em.persist(tutor);
        em.persist(student1);
        em.persist(student2);

        tx.commit();

        // Retrieve the tutor from the database and print their students
        Tutor persistedTutor = em.find(Tutor.class, tutor.getId());
        System.out.println("Tutor: " + persistedTutor.getName());
        System.out.println("Students:");

        for (Student student : persistedTutor.getStudents()) {
            System.out.println(student.getName());
        }

        em.close();
        emf.close();
    }
}
