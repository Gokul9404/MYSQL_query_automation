package MySQL_DBMS;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.Scanner;

public class extras {

    public Scanner user_inputScanner = new Scanner(System.in);

    public int Get_userInput(int range_start, int range_end) {
        int choice;
        while (true) {
            try {
                System.out.print("Enter your choice[" + range_start + " - "+ range_end+"] :- ");
                choice = user_inputScanner.nextInt();
                if ((choice >= range_start) && (choice <= range_end)) {
                    return choice;
                }
            } catch (Exception e) {
            }
            System.out.println("\t Wrong choice enter again!!");
        }
    }

    public void Show_Results(ResultSet res) {
        int res_count = 0;
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
            if (res_count == 0) {
                System.out.println("NO RESULT GENERATED!!");
            }
        } catch (SQLException e) {
            System.out.println("Show Result function error");
        }
    }

    public void Show_Mutiple_Single_Results(ArrayList res1, ArrayList res2) {
        int Ar1, Ar2, Ls_ary_size;
        Ar1 = res1.size();
        Ar2 = res2.size();
        Ls_ary_size = Ar1 < Ar2 ? Ar1 : Ar2;
        try {
            for (int i = 1; i <= Ls_ary_size; i++) {
                try {
                    System.out.println(String.format("%25s | %s", res1.get(i), res2.get(i)));
                } catch (Exception e) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Show Multiple Result function error");
        }
    }

    public ArrayList<String> Get_Results(ResultSet res, int start_column, int end_column) {
        ArrayList<String> results = new ArrayList<>();
        try {
            while (res.next()) {
                for (int i = 1; i < end_column; i++) {
                    try {
                        String reslt = res.getString(i);
                        if (i >= start_column) {
                            results.add(reslt);
                        }
                    } catch (SQLException e) {
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Show Result function error");
        }
        return results;
    }
}
