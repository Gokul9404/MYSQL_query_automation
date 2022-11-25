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

    protected void update_tbl_list() {
        String qury = "SHOW TABLES";
        try {
            results = stmnt.executeQuery(qury);
            tbl_list.clear();
            while (results.next()) {
                for (int i = 1; i > 0; i++) {
                    String tb = results.getString(i);
                    tbl_list.add(tb);
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println(" Update table list func. Error ");
        }
    }

    protected int Choose_tbl(int a) {
        int n = -1;
        int i = 0;
        System.out.println("Tables in the database are:-");
        for (String db : tbl_list) {
            System.out.println(i++ + ") " + db);
        }
        System.out.println("Total no. of Tables available in the database are ::" + i);
        if (a == 1) {
            i = tbl_list.size() - 1;
            try {
                n = ets.Get_userInput(0, i);
            } catch (Exception e) {
                System.out.println(" Choose Table Func Error ");
            }
        }
        return n;
    }

    protected int Choose_column(int a) {
        if(a==3){
            return tbl_property_nameList.size();
        }
        int n = -1, i = 0;
        
        System.out.println("Columns in table:-");
        for (String clmn : tbl_property_nameList) {
            System.out.println(i++ + ") " + clmn);
        }
        
        n = i;  // Use for DML_Table's func
        
        if (a == 1){
            try {
                n = ets.Get_userInput(0, tbl_property_nameList.size()-1);
            } catch (Exception e) {
                System.out.println(" Choose Column Func Error ");
            }
        }
        return n;
    }

    public boolean describe_table(String tbl) {
        try {
            String sql = String.format("DESCRIBE %s", tbl);
            tbl_property_nameList.clear();
            tbl_property_typeList.clear();
            results = stmnt.executeQuery(sql);
            tbl_property_nameList = ets.Get_Results(results, 1, 2);
            results = stmnt.executeQuery(sql);
            tbl_property_typeList = ets.Get_Results(results, 2, 3);
            return true;
        } catch (SQLException e) {
            System.out.println(" Describe Table Func Error ");
        }
        return false;
    }

    public boolean choose_describe_table() {
        try {
            int n = Choose_tbl(1);
            String tbl = tbl_list.get(n);
            describe_table(tbl);
            ets.Show_Mutiple_Single_Results(tbl_property_nameList, tbl_property_typeList);
            return true;
        } catch (Exception e) {
            System.out.println(" Choose Descirbe Table Func Error ");
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
            System.out.println("Unable to Retrieve:: Table does not exist!\nSpecific Column error!");
            return false;
        }
    }

    public boolean choose_show_specific_column() {
        try {
            int n = Choose_tbl(1);
            return show_specific_column(tbl_list.get(n));
        } catch (Exception e) {
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
            return show_specific_row_(tbl, paramtr, clmn_name);
        } catch (Exception e) {
            System.out.println("Unable to Retrieve:: show_specefic_row func error");
            return false;
        }
    }

    public boolean show_specific_row_(String tbl, String Column_to_search_for, String To_search_for) {
        try {
            String sql = String.format("SELECT * FROM %s where %s = '%s'", tbl, Column_to_search_for, To_search_for);
            results = stmnt.executeQuery(sql);
            ets.Show_Results(results);
            return true;
        } catch (SQLException e) {
            System.out.println("Unable to Retrieve:: Table does not exist!\n'show_specefic_row_' func error!");
            return false;
        }
    }

    public boolean choose_show_specific_row() {
        try {
            int n = Choose_tbl(1);
            return show_specific_row(tbl_list.get(n));
        } catch (Exception e) {
            System.out.println(" Show Specific Row Func Error ");
        }
        return false;
    }

    public boolean show_table(String tbl) {
        try {
            String sql = String.format("SELECT * FROM %s", tbl);
            results = stmnt.executeQuery(sql);
            ets.Show_Results(results);
            return true;
        } catch (SQLException e) {
            System.out.println("Unable to Retrieve:: Table does not exist!\nShow table error!");
            return false;
        }
    }

    public boolean choose_show_table() {
        try {
            int n = Choose_tbl(1);
            return show_table(tbl_list.get(n));
        } catch (Exception e) {
            System.out.println(" Show Table Func Error ");
        }
        return false;
    }

    public void show_tb_list() {
        Choose_tbl(0);
    }
}