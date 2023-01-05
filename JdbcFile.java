import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class JdbcFile {
    String url = "jdbc:mysql://localhost:3306";
    static String username;
    static String password;
    String query;

    public static void main(String[] args) {
        System.out.println("<-------------------------   MYSQL   ------------------------->");
        System.out.println();
        System.out.println("<--  Type your queries without ending them with semicolon  --->");
        System.out.println();
        System.out.println("                         --> SHOW <--");
        System.out.println("                          --> USE <--");
        System.out.println("                        --> SELECT <--");
        System.out.println("                       --> DESCRIBE <--");
        System.out.println("                        --> CREATE <--");
        System.out.println("                        --> INSERT <--");
        System.out.println("                        --> UPDATE <--");
        System.out.println("                        --> DELETE <--");
        System.out.println("                         --> EXIT <--");
        System.out.println();
        Scanner scan = new Scanner(System.in);
        System.out.println("----------------------------------------------------------------");
        System.out.println("                             LOGIN");
        System.out.print("Enter your username -> ");
        username = scan.nextLine();
        System.out.print("Enter your password -> ");
        password = scan.nextLine();
        System.out.println("----------------------------------------------------------------");
        System.out.println();
        JdbcFile obj = new JdbcFile();
        obj.startingPage();
    }

    public void startingPage() {
        Scanner scan = new Scanner(System.in);
        System.out.print("sql-->");
        query = scan.nextLine();
        while (!query.equalsIgnoreCase("exit")) {
            if (query.split(" ")[0].equalsIgnoreCase("describe") || query.split(" ")[0].equalsIgnoreCase("desc")) {
                descQuery(query.split(" ")[1]);
            } else if (query.split(" ")[0].equalsIgnoreCase("use")) {
                useQuery(query.split(" ")[1]);
            } else if (query.equalsIgnoreCase("show databases")) {
                showDatabaseQuery();
            } else if (query.equalsIgnoreCase("show tables")) {
                showTablesQuery();
            } else if (query.split(" ")[0].equalsIgnoreCase("select")) {
                selectQuery(query.split(" ")[3]);
            } else if (query.split(" ")[0].equalsIgnoreCase("create") && query.split(" ")[1].equalsIgnoreCase("table")) {
                createQuery(query);
            } else if (query.split(" ")[0].equalsIgnoreCase("create") && query.split(" ")[1].equalsIgnoreCase("database")) {
                createdatabaseQuery(query);
            } else if (query.split(" ")[0].equalsIgnoreCase("delete")) {
                deleteQuery(query);
            } else if (query.split(" ")[0].equalsIgnoreCase("insert") && query.split(" ")[1].equalsIgnoreCase("into")) {
                insertQuery(query);
            } else if (query.split(" ")[0].equalsIgnoreCase("update")) {
                updateQuery(query);
            }

            System.out.print("sql-->");
            query = scan.nextLine();
        }
    }

    public void descQuery(String name) {
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Describe " + name);
            System.out.println();
            System.out.println("+------------------------------------------------------------------------------------------------------+");
            System.out.printf("| %-20s | %-15s | %-8s | %-10s | %-12s | %-20s | %n", "Field", "Type", "Null", "Key", "Default", "Extra");
            System.out.println("+------------------------------------------------------------------------------------------------------+");
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.printf("| %-20s | %-15s | %-8s | %-10s | %-12s | %-20s | %n", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
            }
            System.out.println("+------------------------------------------------------------------------------------------------------+");
            System.out.println();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void showDatabaseQuery() {
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Show Databases");
            int a = 12;
            ArrayList<String> arrayList = new ArrayList<>();
            while (rs.next()) {
                if (rs.getString(1).length() > a) {
                    a = rs.getString(1).length();
                }
                arrayList.add(rs.getString(1));
            }
            System.out.println("+" + "-".repeat(a + 2) + "+");
            System.out.printf("| %-" + a + "s | %n", "Databases: ");
            System.out.println("+" + "-".repeat(a + 2) + "+");
            for (int i = 0; i < arrayList.size(); i++) {
                System.out.printf("| %-" + a + "s | %n", arrayList.get(i));
            }
            System.out.println("+" + "-".repeat(a + 2) + "+");
        } catch (Exception e) {
            System.out.println("Sorry, something went wrong");
        }
    }

    public void showTablesQuery() {
        try {
            ArrayList<String> arrayList = new ArrayList<>();
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Show tables");
            int a = 12;
            while (rs.next()) {
                arrayList.add(rs.getString(1));
                if (rs.getString(1).length() > a) {
                    a = rs.getString(1).length();
                }
            }
            System.out.println("+" + "-".repeat(a + 2) + "+");
            System.out.printf("| %-" + a + "s | %n", "Tables ");
            System.out.println("+" + "-".repeat(a + 2) + "+");
            for (int i = 0; i < arrayList.size(); i++) {
                System.out.printf("| %-" + a + "s | %n", arrayList.get(i));
            }
            System.out.println("+" + "-".repeat(a + 2) + "+");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void useQuery(String name) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            url = "jdbc:mysql://localhost:3306/" + name;
            System.out.println("Database changed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectQuery(String name) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery("Describe " + name);
            int count = 0;
            ArrayList<String> arrayList = new ArrayList<>();
            while (rs1.next()) {
                arrayList.add(rs1.getString(1));
                count++;
            }
            int gap = 12;
            Connection con = DriverManager.getConnection(url, username, password);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from " + name);
            System.out.println("");
            while (rs.next()) {
                for (int i = 0; i < count; i++) {
                    if (rs.getString(arrayList.get(i)).length() > gap) {
                        gap = rs.getString(arrayList.get(i)).length();
                    }
                }
            }
            Statement st1 = conn.createStatement();
            ResultSet rs2 = st1.executeQuery("Select * from " + name);
            System.out.println("+" + "-".repeat((gap + 2) * count) + "+");
            System.out.print("| ");
            for (int i = 0; i < count; i++) {
                System.out.printf("%-" + gap + "s |", (arrayList.get(i)));
            }
            System.out.println("");
            System.out.println("+" + "-".repeat((gap + 2) * count) + "+");
            while (rs2.next()) {
                System.out.print("| ");
                for (int i = 0; i < count; i++) {
                    System.out.printf("%-" + gap + "s |", rs2.getString(arrayList.get(i)));
                }
                System.out.println("");
            }
            System.out.println("+" + "-".repeat((gap + 2) * count) + "+");
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createQuery(String name) {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(name);
            System.out.println("Table created");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void createdatabaseQuery(String name) {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(name);
            System.out.println("Database Created");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void insertQuery(String name) {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(name);
            System.out.println("Records Inserted");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteQuery(String name) {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = conn.prepareStatement(name);
            preparedStatement.executeUpdate();
            System.out.println("Deleted Success");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateQuery(String name) {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(name);
            System.out.println("Records Updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
