package MySQL_DBMS;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;

public class extras {
    public void Show_Results(ResultSet res) {
        int res_count =0;
        try {
            while (res.next()) {
                for (int i = 1; i != 0; i++) {
                    try {
                        String reslt = res.getString(i);
                        System.out.print(reslt + " | ");
                    } catch (SQLException e) {
                        break;
                    }
                }
                res_count++;
                System.out.print("\n");
            }
            if(res_count==0){
                System.out.println("NO RESULT GENERATED!!");
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
