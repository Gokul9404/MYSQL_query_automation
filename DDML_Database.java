package MySQL_DBMS;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class DDML_Database {
   Connection con;
   Statement stmnt;
   Scanner sc = new Scanner(System.in);
   ArrayList<String> db_list = new ArrayList<>();

   public DDML_Database(Connection con, Statement stmnt) {
      this.con = con;
      this.stmnt = stmnt;
      update_db_list();
   }

   private int Choose_dbs(int a) {
      int i = 0;
      int n = -1;
      if (a != 2) {
         for (String db : db_list) {
            System.out.println(i++ + ") " + db);
         }
         System.out.println("Total no. of Databases available are ::" + i);
      }
      if ((a == 1) || (a == 2)) {
         try {
            if(a == 2){ sc.nextLine(); }
            System.out.println("------------------------------");
            System.out.print("Enter your choice [0-" + i + "] :- ");
            try {
               n = sc.nextInt();
            } catch (Exception e) { }
            if ((n >= db_list.size()) || (n < 0)) {
               System.out.println("Wrong Choice!!");
               n = Choose_dbs(2);
            }
         } catch (Exception e) {
            System.out.println("\n:: Choose Database Func Error::");
         }
      }
      return n;
   }

   private void update_db_list() {
      try {
         String qury = "SHOW DATABASES";
         ResultSet res = stmnt.executeQuery(qury);
         db_list.clear();
         while (res.next()) {
            for (int i = 1; i > 0; i++) {
               String db = res.getString(i);
               db_list.add(db);
               break;
            }
         }
      } catch (SQLException e) {
         System.out.println("\n::Update Database List Func Error::");
      }
   }

   public boolean connect_databases(String dbs) {
      try {
         String sql = String.format("USE %s", dbs);
         stmnt.executeUpdate(sql);
         return true;
      } catch (Exception e) {
         System.out.println("::Unable to Connect! : Databases does not exist!::");
         return false;
      }
   }

   public boolean choose_connect_databases() {
      try {
         int n = Choose_dbs(1);
         return connect_databases(db_list.get(n));
      } catch (Exception e) {
         System.out.println("::Connect Database Func Error::");
      }
      return false;
   }

   public boolean create_database() {
      System.out.print("Enter name of the database to create:- ");
      String dtbs = sc.nextLine();
      try {
         String sql = String.format("CREATE DATABASE %s", dtbs);
         stmnt.executeUpdate(sql);
         update_db_list();
         return true;
      } catch (Exception e) {
         System.out.println("::Create Database Func Error::");
         return false;
      }
   }

   public boolean delete_database(String dbs) {
      try {
         String sql = String.format("DROP DATABASE %s", dbs);
         stmnt.executeUpdate(sql);
         System.out.println("Database[" + dbs + "] Deleted!!");
         update_db_list();
         return true;
      } catch (Exception e) {
         System.out.println("::Unable to delete :!: Database does not exist!::");
         return false;
      }
   }

   public boolean choose_delete_database() {
      try {
         int n = Choose_dbs(1);
         return delete_database(db_list.get(n));
      } catch (Exception e) {
         System.out.println(":: Unable to delete !: Choose Databases func error::");
      }
      return false;
   }

   public void show_db_list() {
         Choose_dbs(0);
      }
}