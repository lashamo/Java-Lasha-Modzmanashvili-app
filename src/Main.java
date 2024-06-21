import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/mysql";
        String user = "root";
        String pass = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        }catch (Exception e){
            System.out.println(e);
        }

        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("1. All product in warehouse ");
                System.out.println("2. Add product in warehouse ");
                System.out.println("0. exit");
                int press = scanner.nextInt();
                if (press == 1) {
                    ResultSet resultSet = statement.executeQuery("select * from product3");
                    List<Product> products = new ArrayList<>();
                    Stream<Product> data=products.stream();
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        int quantity = resultSet.getInt("quantity");
                        String about = resultSet.getString("about");
                        Product product = new Product(id, name, quantity, about);
                        products.add(product);
                    }
                   data.forEach(n -> System.out.println(n));

                }
                if (press == 2) {
                    System.out.println("please enter product id");
                    int id = scanner.nextInt();
                    System.out.println("please enter product name");
                    String name = scanner.next();
                    System.out.println("please enter product quantity");
                    int quantity = scanner.nextInt();
                    System.out.println("please enter about information for product");
                    String about = scanner.next();
                    PreparedStatement preparedStatement = connection.prepareStatement("insert into product3 values(?,?,?,?)");
                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, name);
                    preparedStatement.setInt(3, quantity);
                    preparedStatement.setString(4, about);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                }
                if (press == 0) {
                    break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}