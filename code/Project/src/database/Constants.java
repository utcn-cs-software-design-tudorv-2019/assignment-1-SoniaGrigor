package database;
import com.sun.javaws.jnl.UpdateDesc;

import java.util.*;

import static database.Constants.Rights.*;
import static database.Constants.Roles.*;

public class Constants {

    public static class Schemas {
        public static final String TEST = "test_university";
        public static final String PRODUCTION = "university";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {
        public static final String COURSE = "course";
        public static final String USER = "user";
        public static final String STUDENT = "student";
        public static final String GROUP = "groupt";


        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String USER_COURSE = "user_course";

        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{COURSE, USER, GROUP, STUDENT,  ROLE, RIGHT, ROLE_RIGHT, USER_ROLE, USER_COURSE};

    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String BASIC = "basic";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, BASIC};
    }

    public static class Rights {
        public static final String CREATE_USER = "create_user";
        public static final String DELETE_USER = "delete_user";
        public static final String UPDATE_USER = "update_user";

        public static final String ENROLL_USER = "enroll_user";
        public static final String ATTEND_EXAM = "attend_exam";


        public static final String CREATE_GRADE = "create_grade";
        public static final String SET_EXAM = "set_exam";

        public static final String[] RIGHTS = new String[]{CREATE_USER, DELETE_USER, UPDATE_USER, CREATE_GRADE, SET_EXAM, ENROLL_USER, ATTEND_EXAM};
    }

    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> rolesRights = new HashMap<>();
        for (String role : ROLES) {
            rolesRights.put(role, new ArrayList<>());
        }
        rolesRights.get(ADMINISTRATOR).addAll(Arrays.asList(RIGHTS));
        rolesRights.get(BASIC).addAll(Arrays.asList(CREATE_USER, DELETE_USER, UPDATE_USER, ENROLL_USER, ATTEND_EXAM));

        return rolesRights;
    }

}