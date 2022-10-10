package MySQL_DBMS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class DDL_Table {
    Connection con;
    Statement stmnt;
    ResultSet results, res;
    Scanner tb_sc = new Scanner(System.in);
    extras ets = new extras();
    ArrayList<String> tbl_list = new ArrayList<>();
    ArrayList<String> tbl_property_nameList = new ArrayList<>();
    ArrayList<String> tbl_property_typeList = new ArrayList<>();

    public DDL_Table(Connection con, Statement stmnt) {
        this.con = con;
        this.stmnt = stmnt;
        update_tbl_list();
    };

    public void update_con_stmnt(Connection con, Statement stmnt) {
        this.con = con;
        this.stmnt = stmnt;
    };

    protected void update_tbl_list() {
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

    protected int Choose_tbl(int a) {
        int i = 0;
        int n = -1;
        System.out.println("Tables in the database are:-");
        for (String db : tbl_list) {
            System.out.println(i + ") " + db);
            i++;
        }
        if (a != 1){
            System.out.println("Total no. of Tables available in the database are ::" + i);
        }
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

    protected int Choose_column(int a) {
        int i = 0;
        int n = -1;
        System.out.println("Columns in table:-");
        for (String clmn : tbl_property_nameList) {
            System.out.println(i + ") " + clmn);
            i++;
        }
        n = i;
        if (a == 1) {
            try {
                System.out.print("Enter your choice [0-" + i + "] :- ");
                n = tb_sc.nextInt();
                // System.out.println(n);
                if ((n >= i) || (n < 0)) {
                    System.out.println("Wrong Choice!!");
                    n = Choose_column(1);
                }
                // System.out.println(n);
                return n;
            } catch (Exception e) {
                System.out.println(" Choose Column Func Error ");
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
        } catch (SQLException e) {
            // System.out.println(e);
            System.out.println("Unable to Retrieve:: Table does not exist!\nShow table error!");
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

    public boolean show_specific_column(String tbl) {
        try {
            describe_table(tbl);
            String clmn_name = tbl_property_nameList.get(Choose_column(1));
            String sql = String.format("SELECT %s FROM %s", clmn_name, tbl);
            results = stmnt.executeQuery(sql);
            ets.Show_Results(results);
            return true;
        } catch (SQLException e) {
            // System.out.println(e);
            System.out.println("Unable to Retrieve:: Table does not exist!\nSpecific Column error!");
            return false;
        }
    }
    
    public boolean choose_show_specific_column() {
        int n = Choose_tbl(1);
        try {
            String tbbl = tbl_list.get(n);
            return show_specific_column(tbbl);
        } catch (Exception e) {
            // System.out.println(e);
            System.out.println(" Show Specific column Func Error ");
        }
        return false;
    }

    public boolean show_specific_row(String tbl) {
        try {
            describe_table(tbl);
            int z = Choose_column(1);
            String paramtr;
            String clmn_name = tbl_property_nameList.get(z);
            System.out.print("Parameter to search:- ");
            paramtr = tb_sc.nextLine();
            String sql = String.format("SELECT * FROM %s where %s = '%s'", tbl, clmn_name, paramtr);
            // System.out.println(sql);
            results = stmnt.executeQuery(sql);
            ets.Show_Results(results);
            return true;
        } catch (SQLException e) {
            // System.out.println(e);
            System.out.println("Unable to Retrieve:: Table does not exist!\nSpecific Row error!");
            return false;
        }
    }

    public boolean choose_show_specific_row() {
        int n = Choose_tbl(1);
        try {
            String tbbl = tbl_list.get(n);
            return show_specific_row(tbbl);
        } catch (Exception e) {
            // System.out.println(e);
            System.out.println(" Show Specific Row Func Error ");
        }
        return false;
    }

    public boolean describe_table(String tbl) {
        try {
            String sql = String.format("DESCRIBE %s", tbl);
            tbl_property_nameList.clear();
            tbl_property_typeList.clear();
            results = stmnt.executeQuery(sql);
            tbl_property_nameList = ets.Get_Results(results, 1, 2);
            results =  stmnt.executeQuery(sql);
            tbl_property_typeList = ets.Get_Results(results, 2, 3);
            return true;
        } catch (SQLException e) {
            // System.out.println(e);
            System.out.println(" Describe Table Func Error ");
        }
        return false;
    }

    public boolean choose_describe_table() {
        int n = Choose_tbl(1);
        try {
            String tbl = tbl_list.get(n);
            boolean z = describe_table(tbl);
            if(z){
                ets.Show_Mutiple_Single_Results(tbl_property_nameList, tbl_property_typeList);
                return true;
            }
        } catch (Exception e) {
            // System.out.println(e);
            System.out.println(" Choose Descirbe Table Func Error ");
        }
        return false;
    }
}