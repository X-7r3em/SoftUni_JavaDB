package lab;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public class IterableResultSet implements Iterable<String> {
    private ResultSet resultSet;
    private String currentSet;

    public IterableResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            @Override
            public boolean hasNext() {
                try {
                    boolean hasNext = resultSet.next();
                    if (hasNext){
                        currentSet = resultSet.getString("full_name");
                    }
                    return hasNext;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            public String next() {
                return currentSet;
            }
        };
    }
}
