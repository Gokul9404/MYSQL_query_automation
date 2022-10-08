package MySQL_DBMS;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class table_dml extends table_ddl{
    Scanner tb_sc = new Scanner(System.in);
    extras ets = new extras();
    
    public table_dml(Connection con, Statement stmnt) {
        super(con,stmnt);
    };

    public boolean create_table(){
        return false;
    }

    public boolean insert_values(){
        return false;
    }

    public boolean update_values_on_row(String tbl){
        try {
            describe_table(tbl);
            int clm_lgt = Choose_column(0);
            int pr, cpr;
            String paramtr, value, condition_para, condition_value;
            System.out.print("Enter Column to change [0-" + clm_lgt + "] :- ");
            pr = tb_sc.nextInt();
            paramtr = tbl_property_nameList.get(pr);
            System.out.print("Value to Update:- ");
            value = tb_sc.nextLine();
            
            System.out.print("Enter Column Parameter [0-" + clm_lgt + "] :- ");
            cpr = tb_sc.nextInt();
            condition_para = tbl_property_nameList.get(cpr);
            System.out.print("Condition Value[s]: ");
            condition_value = tb_sc.nextLine();
            
            String qry = String.format("UPDATE %s set %s = '%s' WHERE %s = '%s'",tbl,paramtr, value, condition_para, condition_value);
            stmnt.executeUpdate(qry);
            System.out.println(qry);
        } catch (SQLException e) {
            // System.out.println(e);
            System.out.println(":: Unable to Update ::\nUpdate tbl Func Error!");
        }
        return false;
    }

    public boolean update_values_on_row(String tbl,String paramtr,String value,String condition_para,String condition_value){
        try {
            String qry = String.format("UPDATE %s set %s = '%s' WHERE %s = '%s'",tbl,paramtr, value, condition_para, condition_value);
            stmnt.executeUpdate(qry);
            System.out.println(qry);
        } catch (SQLException e) {
            // System.out.println(e);
            System.out.println(":: Unable to Update ::\nUpdate tbl Func(Value asses ar parameter) Error!");
        }
        return false;
    }
    
    public boolean choose_tbl_and_update_values_on_row() {
        int n = Choose_tbl(1);
        try {
            String tbbl = tbl_list.get(n);
            return update_values_on_row(tbbl);
        } catch (Exception e) {
            // System.out.println(e);
            System.out.println("Table does not exist!!\nChoose Update tbl Func Error!");
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
        } catch (SQLException e) {
            // System.out.println(e);
            System.out.println("Unable to delete:: Tables does not exist!");
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