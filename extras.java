package MySQL_DBMS;

import java.sql.SQLException;
import java.util.ArrayList;
// import java.sql.Statement;
import java.sql.ResultSet;

class search_array {
    public int search_intary(int item, int ary[]) {
        int index = -1;
        for (int i = 0; i < ary.length; i++) {
            if (ary[i] == item) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int search_strary(String item, String ary[]) {
        int index = -1;
        for (int i = 0; i < ary.length; i++) {
            if (ary[i] == item) {
                index = i;
                break;
            }
        }
        return index;
    }
}

public class extras {
    public void Show_Results(ResultSet res) {
        try {
            while (res.next()) {
                for (int i = 1; i < 100; i++) {
                    try {
                        String reslt = res.getString(i);
                        System.out.print(reslt + " | ");
                    } catch (SQLException e) {
                        break;
                    }
                }
                System.out.print("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Show Result function error");
        }
    }

    
    public ArrayList<String> Get_Results(ResultSet res, int start_column, int end_column) {
        ArrayList<String> results = new  ArrayList<>();
        try {
            while (res.next()) {
                for (int i = start_column; i < end_column; i++) {
                    try {
                        String reslt = res.getString(i);
                        results.add(reslt);    
                    } catch (SQLException e) {
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Show Result function error");
        }
        return results;
    }
}
