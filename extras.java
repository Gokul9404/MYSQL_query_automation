package MySQL_DBMS;

import java.sql.SQLException;
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
                // System.out.println(res.getString(1));
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
}
