package test;

import database.DBConnectionFactory;
import model.User;
import model.builder.UserBuilder;
import org.junit.*;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.AuthenticationException;
import repository.user.UserRepositoryMySQL;

import javax.swing.*;
import java.sql.Connection;
import java.util.Collections;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class UserRepositoryMySQLTest {

    private static RightsRolesRepository rightsRolesRepository;
    private static UserRepositoryMySQL userRepositoryMySQL;

    @BeforeClass
    public static void init(){
        Connection connection= new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        rightsRolesRepository=new RightsRolesRepositoryMySQL(connection);
        userRepositoryMySQL=new UserRepositoryMySQL(connection,rightsRolesRepository);
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
        User user = new UserBuilder()
                .setUsername("sonia.grigor@yahoo.com")
                .setPassword("Aziiau10l_ais!")
                .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle("customer")))
                .build();
        assertTrue(userRepositoryMySQL.save(user));
        System.out.println("save");
    }

    @Test
    public void findAll() {
        assertEquals(0, userRepositoryMySQL.findAll().size());
        System.out.println("find all");

    }

    @Test
    public void findByUsernameAndPassword() {
        try {
            assertNotNull(userRepositoryMySQL.findByUsernameAndPassword("sonia.grigor@gmail.com", "Aziiau10l_ais!"));
        } catch (AuthenticationException e) {
            JOptionPane.showMessageDialog(null, "JunitTest findByUsernameAndPassword!");
        }
        System.out.println("find by id");

    }

}
