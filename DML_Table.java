package MySQL_DBMS;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DML_Table extends DDL_Table {
    public DML_Table(Statement stmnt) {
        super(stmnt);
    };

    protected boolean delete_specific_row(String tbbl,String column_para,String value, int type) {
        try {
            String sql = "";
            if(type == 0){
                sql = String.format("DELETE FROM %s WHERE %s = %s", tbbl,column_para, value);
            }
            else{
                sql = String.format("DELETE FROM %s WHERE %s = '%s'", tbbl,column_para, value);
            }
            stmnt.executeUpdate(sql);
            update_tbl_list();
            return true;
        } catch (SQLException e) {
            System.out.println("Unable to delete:: Tables does not exist!");
        }
        return false;
    }

    public boolean choose_specific_row_and_delete(String tbbl) {
        try {
            int pr, clm_lgt = -1;
            clm_lgt = Choose_column(0);
            String paramtr, value, condition_para, condition_value;
            paramtr = value = condition_para =  condition_value = "";
            System.out.print("Enter Column Parameter where to delete");
            pr = ets.Get_userInput(0, clm_lgt);
            condition_para = tbl_property_nameList.get(pr);
            ets.user_inputScanner.nextLine();
            System.out.print("Value where to delete:- ");
            condition_value = ets.user_inputScanner.nextLine();
            String proprty = tbl_property_typeList.get(pr);
            if (proprty.matches("int") || proprty.matches("decimal") || proprty.matches("float") || proprty.matches("double")) {
                return delete_specific_row(tbbl, condition_para, condition_value, 0);
            } else {
                return delete_specific_row(tbbl, condition_para, condition_value, 1);
            }
        } catch (Exception e) {
            System.out.println("Unable to delete row:: choose_specific_row func error!");
        }
        return false;
    }

    public boolean choose_delete_specific_row_from_tbl() {
        try {
            int n = Choose_tbl(1);
            String tbbl = tbl_list.get(n);
            System.out.println("Table seleted :-" + tbbl);
            return choose_specific_row_and_delete(tbbl);
        } catch (Exception e) {
            System.out.println("Table does not exist!!!");
        }
        return false;
    }

    protected boolean delete_table(String tbbl) {
        try {
            String sql = String.format("DROP TABLE %s", tbbl);
            System.out.println(sql);
            stmnt.executeUpdate(sql);
            System.out.println("Table[" + tbbl + "] Deleted!!");
            update_tbl_list();
            return true;
        } catch (SQLException e) {
            System.out.println("Unable to delete:: Tables does not exist!");
        }
        return false;
    }

    public boolean choose_delete_table() {
        try {
            int n = Choose_tbl(1);
            String tbbl = tbl_list.get(n);
            System.out.println("Table seleted :-" + tbbl);
            System.out.print("Delete above table [yes: 1/ no:0]:-");
            int cnf = ets.user_inputScanner.nextInt();
            if (cnf != 0) {
                return delete_table(tbbl);
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Table does not exist!!!");
        }
        return false;
    }

    protected boolean insert_values(String tbl) {
        try {
            describe_table(tbl);
            int Fin, total_column, Int_Input;
            total_column = tbl_property_typeList.size();
            String vals = "", Columns = "", value;
            Columns = tbl_property_nameList.toString();
            Columns = Columns.substring(1, Columns.length() - 1);
            ets.user_inputScanner.nextLine();

            while (true) {
                for (int i = 0; i < tbl_property_nameList.size(); i++) {
                    String proprty = tbl_property_typeList.get(i);
                    System.out.print("Column name (" + tbl_property_nameList.get(i) + ") type:(" + proprty + ")\nEnter value :>");
                    value = ets.user_inputScanner.next();
                    if (proprty.matches("int") || proprty.matches("decimal") || proprty.matches("float") || proprty.matches("double")) {
                        vals = vals + String.format("%s,", value);
                    } else {
                        vals = vals + String.format("'%s',", value);
                    }
                }
                vals = vals.substring(0, vals.length() - 1);
                String qry = String.format("INSERT INTO %s (%s) VALUES (%s)", tbl, Columns, vals);
                try {
                    stmnt.executeUpdate(qry);
                } catch (SQLException e) { 
                    System.out.println("Values not inserted into Table :: Wrong input type error!");
                }
                try {
                    System.out.print("Quit inserting( YES [1] / NO [0]):-");
                    try {
                        Fin = ets.user_inputScanner.nextInt();
                    } catch (Exception e) { 
                        Fin = 0;
                    }
                    if (Fin != 0) {
                        return true;
                    }
                } catch (Exception e) { }
            }
        } catch (Exception e) {
            System.out.println(":: Unable to Insert ::\nInsert tbl Func Error!");
        }
        return false;
    }

    public boolean choose_table_insert_values() {
        try {
            int n = Choose_tbl(1);
            String tbbl = tbl_list.get(n);
            return insert_values(tbbl);
        } catch (Exception e) {
            System.out.println(":: Choose Table insert func error!! ::");
        }
        return false;
    }

    protected boolean update_values_on_row(String tbl) {
        try {
            describe_table(tbl);
            int clm_lgt = Choose_column(0);
            int pr, cpr;
            String paramtr, value, condition_para, condition_value;
            paramtr = value = condition_para =  condition_value = "";
            System.out.print("Enter Column where to change [0-" + clm_lgt + "] :- ");
            pr = ets.user_inputScanner.nextInt();
            paramtr = tbl_property_nameList.get(pr);
            System.out.print("Value to Update:- ");
            value = ets.user_inputScanner.nextLine();

            System.out.print("Enter Column Parameter [0-" + clm_lgt + "] :- ");
            cpr = ets.user_inputScanner.nextInt();
            condition_para = tbl_property_nameList.get(cpr);
            System.out.print("Condition Value[s]: ");
            condition_value = ets.user_inputScanner.nextLine();

            update_values_on_row(tbl, paramtr, value, condition_para, condition_value);
        } catch (Exception e) {
            System.out.println(":: Unable to Update ::\nUpdate tbl Func Error!");
        }
        return false;
    }

    protected boolean update_values_on_row(String tbl, String paramtr, String value, String condition_para, String condition_value) {
        try {
            String qry = String.format("UPDATE %s set %s = '%s' WHERE %s = '%s'", tbl, paramtr, value, condition_para,condition_value);
            stmnt.executeUpdate(qry);
        } catch (SQLException e) {
            System.out.println(":: Unable to Update ::\nUpdate tbl Func(Value as parameter) Error!");
        }
        return false;
    }

    public boolean choose_tbl_and_update_values_on_row() {
        try {
            int n = Choose_tbl(1);
            String tbbl = tbl_list.get(n);
            return update_values_on_row(tbbl);
        } catch (Exception e) {
            System.out.println("Table does not exist!!\nChoose Update tbl Func Error!");
        }
        return false;
    }
}