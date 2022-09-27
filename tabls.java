package MySQL_DBMS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class tabls {
    Connection con;
    Statement stmnt;
    ResultSet results;
    Scanner tb_sc = new Scanner(System.in);
    extras ets = new extras();
    ArrayList<String> tbl_list = new ArrayList<>();
    ArrayList<String> tbl_property_nameList = new ArrayList<>();
    ArrayList<String> tbl_property_typeList = new ArrayList<>();
    
    public tabls(Connection con, Statement stmnt) {
        this.con = con;
        this.stmnt = stmnt;
        update_tbl_list();
    };

    public void update_con_stmnt(Connection con, Statement stmnt) {
        this.con = con;
        this.stmnt = stmnt;
    };
    
    private void update_tbl_list() {
        String qury = "SHOW TABLES";
        try {
            results = stmnt.executeQuery(qury);
            tbl_list.clear();
            while (results.next()) {
                String db = results.getString(1);
                tbl_list.add(db);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(" Update table list func. Error ");
        }
    }
    
    private int Choose_tbl(int a) {
        int i = 0;
        int n = -1;
        System.out.println("Tables in the database are:-");
        for (String db : tbl_list) {
            System.out.println(i + ") " + db);
            i++;
        }
        System.out.println("Total no. of Tables available in the database are ::" + i);
        if (a == 1) {
            try {
                System.out.print("Enter your choice [0-" + i + "] :- ");
                n = tb_sc.nextInt();
                // System.out.println(n);
                if ((n >= i) || (n < 0)) {
                    System.out.println("Wrong Choice!!");
                    n = Choose_tbl(1);
                }
                // System.out.println(n);
                return n;
            } catch (Exception e) {
                System.out.println(" Choose Table Func Error ");
            }
        }
        return n;
    }

    public void show_tb_list() {
        Choose_tbl(0);
    }

    public boolean show_table(String tbl) {
        try {
            String sql = String.format("SELECT * FROM %s", tbl);
            results = stmnt.executeQuery(sql);
            ets.Show_Results(results);
            return true;
        } catch (Exception e) {
            // System.out.println(e);
            System.out.println("Unable to Retrieve:: Table does not exist!");
            return false;
        }
    }

    public boolean choose_show_table() {
        int n = Choose_tbl(1);
        try {
            String tbbl = tbl_list.get(n);
            return show_table(tbbl);
        } catch (Exception e) {
            // System.out.println(e);
            System.out.println(" Show Table Func Error ");
        }
        return false;
    }
    
    public boolean describe_table(String tbl){
        try {
            String sql = String.format("DESCRIBE %s",tbl);
            results = stmnt.executeQuery(sql);
            // ets.Show_Results(results);
            tbl_property_nameList.clear();
            tbl_property_typeList.clear();
            tbl_property_nameList = ets.Get_Results(results, 1, 2);
            tbl_property_typeList = ets.Get_Results(results, 2, 3);
            return true;
        } catch (Exception e) {
            // System.out.println(e);
            System.out.println(" Describe Table Func Error ");
        }
        return false;
    }
    public boolean choose_describe_table(){
        int n = Choose_tbl(1);
        try {
            String tbl =  tbl_list.get(n);
            return describe_table(tbl);
        } catch (Exception e) {
            // System.out.println(e);
            System.out.println(" Choose Descirbe Table Func Error ");
        }
        return false;
    }

    public boolean delete_table(String tbbl) {
        try {
            String sql = String.format("DROP TABLE %s", tbbl);
            System.out.println(sql);
            stmnt.executeUpdate(sql);
            System.out.println("Table[" + tbbl + "] Deleted!!");
            update_tbl_list();
            return true;
        } catch (Exception e) {
            // System.out.println(e);
            System.out.println("Unable to delete:: Database does not exist!");
            return false;
        }
    }

    public boolean choose_delete_table() {
        int n = Choose_tbl(1);
        try {
            String tbbl = tbl_list.get(n);
            System.out.println("Table seleted :-" + tbbl);
            System.out.print("Delete above table [yes: 1/ no:0]:-");
            int cnf = tb_sc.nextInt();
            if(cnf != 0){
                return delete_table(tbbl);
            }
            else{
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Table does not exist!!!");
        }
        return false;
    }
}
