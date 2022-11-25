import MySQL_DBMS.extras;
import MySQL_DBMS.DDML_Database;
import MySQL_DBMS.DML_Table;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class use_case_example {
   public static void main(String[] args) {
      try {
         int choice = 0, Run = 1;
         extras ets = new extras();
         Scanner tb_sc = new Scanner(System.in);
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "gokul");
         Statement statement = conn.createStatement();
         
         DDML_Database dbs = new DDML_Database(conn, statement);
         System.out.println(":: Select a database to have some work ::");
         dbs.choose_connect_databases();
         
         DML_Table tabel = new DML_Table(conn, statement);
         while (Run == 1) {
            System.out.println(":: Select any operation to perform on database/table on database ::");
            System.out.println("1) Show table list");
            System.out.println("2) Descirbe any table on database");
            System.out.println("3) Insert values on table");
            System.out.println("4) Show whole table content");
            System.out.println("5) Show table content of specific row");
            System.out.println("6) Show table content of specific column");
            System.out.println("7) Delete any table");
            System.out.println("8) Exit");
            choice = ets.Get_userInput(1, 8);
            ets.user_inputScanner.nextLine();
            switch (choice) {
               case 1:
                  tabel.show_tb_list();
                  break;

               case 2:
                  tabel.choose_describe_table();
                  break;

               case 3:
                  tabel.choose_table_insert_values();
                  break;

               case 4:
                  tabel.choose_show_table();
                  break;

               case 5:
                  tabel.choose_show_specific_row();
                  break;

               case 6:
                  tabel.choose_show_specific_column();
                  break;

               case 7:
                  tabel.choose_delete_table();
                  break;

               case 8:
                  Run--;

               default:
                  break;
            }
            if (Run == 1) {
               ets.user_inputScanner.nextLine();
               System.out.print("\033[H\033[2J");
               System.out.flush();
            }
         }
         conn.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
}
