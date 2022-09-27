package MySQL_DBMS;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class databs {
   Connection con;
   Statement stmnt;
   Scanner sc = new Scanner(System.in);
   ArrayList<String> db_list = new ArrayList<>();

   public databs(Connection con, Statement stmnt) {
      this.con = con;
      this.stmnt = stmnt;
      update_db_list();
   }
   
   public void update_con_stmnt(Connection con, Statement stmnt) {
      this.con = con;
      this.stmnt = stmnt;
   };

   private int Choose_dbs(int a) {
      int i = 0;
      int n = -1;
      for (String db : db_list) {
         System.out.println(i + ") " + db);
         i++;
      }
      System.out.println("Total no. of Databases available are ::" + i);
      if (a == 1) {
         try{            
            System.out.print("Enter your choice [0-" + i + "] :- ");
            n = sc.nextInt();
            // System.out.println(n);
            if ((n >= i) || (n < 0)) {
               System.out.println("Wrong Choice!!");
               n = Choose_dbs(1);
            }
            // System.out.println(n);
            return n;
         } catch (Exception e) {
            System.out.println(" Choose Database Func Error ");
         }
      }
      return n;
   }

   private void update_db_list() {
      String qury = "SHOW DATABASES";
      try {
         ResultSet res = stmnt.executeQuery(qury);
         db_list.clear();
         while (res.next()) {
            String db = res.getString(1);
            db_list.add(db);
         }
      } catch (SQLException e) {
         // e.printStackTrace();
         System.out.println(" Update Database List Func Error ");
      }
   }

   public void show_db_list() {
      Choose_dbs(0);
   }

   public boolean connect_databases(String dbs) {
      try {
         String sql = String.format("USE %s", dbs);
         stmnt.executeUpdate(sql);
         return true;
      } catch (Exception e) {
         // System.out.println(e);
         System.out.println("Unable to Connect:: Databases does not exist!");
         return false;
      }
   }

   public boolean choose_connect_databases() {
      int n = Choose_dbs(1);
      try {
         String sql = String.format("USE %s", db_list.get(n));
         stmnt.executeUpdate(sql);
         return true;
      } catch (Exception e) {
         // System.out.println(e);
         System.out.println(" Connect Database Func Error ");
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
         // System.out.println(e);
         System.out.println(" Create Database Func Error ");
         return false;
      }
   }

   public boolean delete_database(String dbs) {
      try {
         String sql = String.format("DROP DATABASE %s", dbs);
         stmnt.executeUpdate(sql);
         System.out.println("Database["+ dbs +"] Deleted!!");
         update_db_list();
         return true;
         
      } catch (Exception e) {
         // System.out.println(e);
         System.out.println("Unable to delete:: Database does not exist!");
         return false;
      }
   }

   public boolean choose_delete_database() {
      int n = Choose_dbs(1);
      try {
         String db = db_list.get(n);
         String sql = String.format("DROP DATABASE %s",db);
         System.out.println(sql);
         stmnt.executeUpdate(sql);
         System.out.println("Database["+ db +"] Deleted!!");
         update_db_list();
         return true;
      } catch (SQLException e){
         System.out.println(e);
         System.out.println("Databases does not exist!!!");
      }
      return false;
   }
}