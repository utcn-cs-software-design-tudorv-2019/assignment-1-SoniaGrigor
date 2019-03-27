package dal.database;

import utility.JDBConnectionWrapper;

import static dal.database.Constants.Schemas.PRODUCTION;
import static dal.database.Constants.Schemas.TEST;

public class DBConnectionFactory {
    public JDBConnectionWrapper getConnectionWrapper(boolean test)
    {
        if(test){
            return new JDBConnectionWrapper(TEST);
        }else{
            return new JDBConnectionWrapper(PRODUCTION);
        }
    }
}
