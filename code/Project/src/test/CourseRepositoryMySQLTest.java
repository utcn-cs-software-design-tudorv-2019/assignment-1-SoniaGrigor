package test;

import dal.database.DBConnectionFactory;
import dal.model.Course;
import dal.model.builder.CourseBuilder;
import dal.repository.course.CourseRepository;
import dal.repository.course.CourseRepositoryMySQL;
import org.junit.*;

import java.sql.Connection;
import java.util.Date;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class CourseRepositoryMySQLTest {

    private static CourseRepository courseRepository;

    @BeforeClass
    public static void init(){
        Connection connection= new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        courseRepository=new CourseRepositoryMySQL(connection);
        System.out.println("Before class.");

    }
    @Before
    public void setup() {
        System.out.println("Before.");

    }

    @After
    public void clean() {
        System.out.println("After");
    }

    @AfterClass
    public static void cleanUp() {
        System.out.println("After class");
    }

    @Test
    public void save() {
        Course course = new CourseBuilder()
                .setName("Software Design")
                .setCredit(5)
                .setExam(new Date())
                .setRoom("M04")
                .build();
        assertTrue(courseRepository.save(course));
        System.out.println("save");
    }

    @Test
    public void findAll() {
        assertEquals(5, courseRepository.findAll().size());
        System.out.println("find all");

    }

    @Test
    public void getIdByName(){
        assertEquals(1, courseRepository.getIdByName("Software Design"));
        System.out.println("getIdByName");
    }
}
