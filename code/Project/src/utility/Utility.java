package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static utility.ProjectConstants.LOGGED_USER_FILE;

public class Utility {

    public static int getLoggedUser() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(LOGGED_USER_FILE));
            String currentLine = reader.readLine();
            reader.close();
            return Integer.parseInt(currentLine);
        }catch (IOException e){
            e.printStackTrace();
            return -1;
        }
    }
}
