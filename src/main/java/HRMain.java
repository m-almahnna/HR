import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class HRMain {

    public static final List<String> employeeList = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static Scanner scanner2 = new Scanner(System.in);

    public static void main(String[] args) {
        final Operators operator = new Operators();
        Scanner scanner = login(operator);

        Employee emp = new Employee();
        Department dep = new Department();
        List<String> e = new ArrayList<>();
        e.add(String.valueOf(emp.id));
        e.add(emp.name);
        e.add(String.valueOf(emp.department));
        e.add(String.valueOf(emp.phone));

        String sql = "select * from users where username =" + "'" + operator.user + "'" + "and password=" + "'" + operator.pass + "'";

        Connection(scanner, sql, emp, dep);


    }


    public static void pressing(Scanner scanner, ResultSet rs, Employee emp, Department dep) throws SQLException {
        System.out.println("Select number  of the options below:");
        System.out.println(" 1- Display all employees. ");
        System.out.println(" 2- Adding new employee info.");
        System.out.println(" 3- Update employee info.");
        System.out.println(" 4- Create new department.");
        System.out.println(" 5- Delete employee info.");
        System.out.print(" Your option : ");
        int input1 = scanner.nextInt();

        menu(input1, rs, emp, dep);
    }

    public static String menu(int input1, ResultSet rs, Employee emp, Department dep) throws SQLException {

        if (input1 == 1) {
            Options(emp);

        } else if (input1 == 2) {
            System.out.println("ID employee :");
            emp.id = scanner.nextInt();
            System.out.println("Name :");
            emp.name = scanner2.nextLine();

            System.out.println("ID department: ");
            emp.department = scanner.nextInt();
            System.out.println("Phone:");
            emp.phone = scanner.nextInt();
            adding(emp);

        } else if (input1 == 3) {

            System.out.println("ID employee :");
            emp.id = scanner.nextInt();
            System.out.println("Name :");
            emp.name = scanner2.nextLine();
            System.out.println("ID department: ");
            emp.department = scanner.nextInt();
            System.out.println("Phone:");
            emp.phone = scanner.nextInt();
            update(emp);
        } else if (input1 == 4) {

            System.out.println("ID department :");
            dep.id_department = scanner.nextInt();
            System.out.println("Department Name :");
            dep.department_name = scanner2.nextLine();
            System.out.println("code: ");
            dep.code = scanner2.nextLine();
            System.out.println("Location:");
            dep.location = scanner.nextInt();
            createDep(dep);
        } else if (input1 == 5) {
            System.out.println("ID employee :");
            emp.id = scanner.nextInt();
            delete(emp);


        } else System.out.println("invalid number");
        return null;
    }


    private static void Connection(Scanner scanner, String sql, Employee emp, Department dep) {
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            msg(scanner, connection, rs, emp, dep);
        } catch (SQLException | ClassNotFoundException throwable) {
            System.out.println("something went wrong with connection");
        }
    }


    private static void Options(Employee emp) {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(getString());
            rs = statement.getResultSet();

            while (rs.next()) {

                employeeList.add(String.valueOf(emp.setId(rs.getInt("id"))));
                employeeList.add(emp.setName(rs.getString("name")));
                employeeList.add(String.valueOf(emp.setDepartment(rs.getInt("department"))));
                employeeList.add(String.valueOf(emp.setPhone(rs.getInt("phone"))));
                System.out.println(emp);

            }
//fixme for countOnly
//            int columnCount = resultSetMetaData.getColumnCount();
//            System.out.println("Col" + columnCount + "\n");
//            for (int colm = 1; colm <= columnCount; colm++) {
//                System.out.println("col: " + resultSetMetaData.getColumnName(colm));
//            }

        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
    }

    private static void adding(Employee emp) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO hr.employee (`id`, `name`, `department`, `phone`)" +
                    " VALUES (?,?,?,?)");

            statement.setInt(1, emp.id);
            statement.setString(2, emp.name);
            statement.setInt(3, emp.department);
            statement.setInt(4, emp.phone);
            statement.execute();
            System.out.println("New employee added " + emp.name);


        } catch (SQLException | ClassNotFoundException throwable) {

            System.out.println("something went wrong");
        }
    }

    private static void createDep(Department dep) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO hr.department (`id_department`, `department_name`, `code`, `location`) VALUES (?,?,?,?)");

            statement.setInt(1, dep.id_department);
            statement.setString(2, dep.department_name);
            statement.setString(3, dep.code);
            statement.setInt(4, dep.location);
            statement.execute();
            System.out.println("New department added " + dep.department_name);


        } catch (SQLException | ClassNotFoundException throwable) {

            System.out.println("something went wrong");
        }
    }

    private static void update(Employee emp) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE hr.employee SET `name` = ?, `department` = ?,  `phone` = ? WHERE (`id` = ?)");


            statement.setString(1, emp.name);
            statement.setInt(2, emp.department);
            statement.setInt(3, emp.phone);
            statement.setInt(4, emp.id);
            statement.execute();
            System.out.println("new update for " + emp.id);


        } catch (SQLException | ClassNotFoundException throwable) {
            System.out.println("something went wrong");
        }
    }

    private static void delete(Employee emp) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(" DELETE FROM `hr`.`employee` WHERE (`id` = ?)");

            statement.setInt(1, emp.id);


            statement.execute();
            System.out.println("Employee has been delete with ID: " + emp.id);


        } catch (SQLException | ClassNotFoundException throwable) {
            System.out.println("something went wrong");
        }
    }

    private static String getString() {
        return "SELECT id , name , department, phone FROM hr.employee";
    }


    private static Scanner login(Operators operator) {

        System.out.println("Username:");
        Scanner scanner = new Scanner(System.in);
        operator.user = scanner.nextLine();
        System.out.println("Password:");
        Scanner scanner2 = new Scanner(System.in);
        operator.pass = scanner2.nextLine();

        return scanner;
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/hr", "root", "4242");
    }

    private static void msg(Scanner scanner, Connection connection, ResultSet rs, Employee emp, Department dep) throws SQLException {

        if (rs.next()) {
            System.out.println("Login Successful");
            pressing(scanner, rs, emp, dep);

        } else {
            System.out.println("Login failed");
        }
//        connection.close();
//        scanner.close();

    }


}