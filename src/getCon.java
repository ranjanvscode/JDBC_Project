import java.sql.*;

public class getCon {

    private static Connection con;

    public static Connection getConnection()
    {
        String url = "jdbc:mysql://localhost:3306/hotel_db";
        String user = "root";
        String password = "8340";

        if(con==null)
        {
            try{

                con = DriverManager.getConnection(url, user, password);
    
            }catch(Exception e){
    
                System.out.println("Not Connected");
            }
        }

        return con;
    }

    public static void main(String[] args) {

    }
    
}
