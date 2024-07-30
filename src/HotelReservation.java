
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class HotelReservation {

    public static void newReservation(Connection con,Scanner sc)
    {
        System.out.println("Enter Guest Name: ");
        sc.skip("\\R");
        String gName = sc.nextLine();

        System.out.println("Enter Room Number: ");
        int roomNum = sc.nextInt();

        System.out.println("Enter Contact number: ");
        sc.skip("\\R");
        String conNum = sc.nextLine();

        String query = "INSERT INTO reservations (guest_name,room_number,contact_number)" +
                        "VALUES ('"+gName+"','"+roomNum+"','"+conNum+"')";

        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.executeUpdate();
            System.out.println("Registration Successful");
        }catch (Exception e){
            System.out.println("Server Error!");
        }
    }

    public static void checkReservation(Connection con,Scanner sc)
    {
        System.out.println("Enter Reservation ID or Contact number: ");
        sc.skip("\\R");
        String num = sc.nextLine();

        String query;

        if (num.length()>5)
        {
            query = "SELECT * FROM reservations WHERE contact_number='"+num+"'";
        }else {
            int idNum = Integer.parseInt(num);
            query = "SELECT * FROM reservations WHERE reservation_id='"+idNum+"'";
        }

        try{
            Statement st = con.createStatement();
            ResultSet set = st.executeQuery(query);

            if (set.next())
            {
                System.out.println("Reserved");

                System.out.format("+-----------------+--------------+---------------+----------+------------------+%n");
                System.out.format("| Registration ID |  Guest Name  |  Contact No.  | Room No. | Reservation Date |%n");
                System.out.format("+-----------------+--------------+---------------+----------+------------------+%n");
                System.out.println(set.getInt("reservation_id"));
                System.out.println(set.getString("guest_name"));
                System.out.println(set.getString("contact_number"));
                System.out.println(set.getInt("room_number"));
                System.out.println(set.getString("reservation_date"));
            }else {
                System.out.println("Not Reserved");
            }

        }
        catch (Exception e){
            System.out.println("Server Error!");
        }

    }

    public static void getRoomNum(Connection con,Scanner sc)
    {
        System.out.println("Enter Name: ");
        sc.skip("\\R");
        String gName = sc.nextLine();

        System.out.println("Enter Reservation ID or Contact number: ");
        String num = sc.nextLine();

        String query;

        if (num.length()>5)
        {
             query = "SELECT room_number FROM reservations WHERE guest_name='"+gName+"' AND contact_number='"+num+"'";
        }else {
             int idNum = Integer.parseInt(num);
             query = "SELECT room_number FROM reservations WHERE guest_name='"+gName+"' AND reservation_id='"+idNum+"'";
        }



        try{
            Statement st = con.createStatement();
            ResultSet set = st.executeQuery(query);

            if (set.next())
                System.out.println("Your Room Number: "+set.getInt("room_number"));

        }
        catch (Exception e){
            System.out.println("Server Error!");
        }

    }

    public static void updateReservation(Connection con,Scanner sc)
    {
        System.out.println("What do you want to update, Select below option:");
        System.out.println("1. Your Name");
        System.out.println("2. Contact Number");
        System.out.println("3. Room Number");
        int num = sc.nextInt();

        String query;

        switch (num){
            case 1:
                System.out.println("Enter you old name:");
                sc.skip("\\R");
                String oldName = sc.nextLine();
                System.out.println("Enter you new name:");
                String newName = sc.nextLine();
                query = "UPDATE reservations SET guest_name='"+newName+"' WHERE guest_name='"+oldName+"'";
                break;
            case 2:
                System.out.println("Enter you old Contact Number:");
                sc.skip("\\R");
                String oldContact = sc.nextLine();
                System.out.println("Enter you new Contact Number:");
                String newContact = sc.nextLine();
                query = "UPDATE reservations SET guest_name='"+newContact+"' WHERE guest_name='"+oldContact+"'";
                break;
            case 3:
                System.out.println("Enter you old Room Number:");
                sc.skip("\\R");
                String oldRoomNum = sc.nextLine();
                System.out.println("Enter you new Room Number:");
                String newRoomNum = sc.nextLine();
                query = "UPDATE reservations SET guest_name='"+newRoomNum+"' WHERE guest_name='"+oldRoomNum+"'";
                break;
            default:
                query = "select * from reservation";
                System.out.println("Invalid Number...");
        }

        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.executeUpdate();
            System.out.println("Updated...\n");
        }
        catch (Exception e){
            System.out.println("Server Error!");
        }
    }

    public static void deleteReservation(Connection con,Scanner sc)
    {
        System.out.println("Enter Name: ");
        sc.skip("\\R");
        String gName = sc.nextLine();

        System.out.println("Enter Reservation ID or Contact number: ");
        String num = sc.nextLine();

        String query;

        if (num.length()>5)
        {
            query = "DELETE FROM reservations WHERE guest_name='"+gName+"' AND contact_number='"+num+"'";
        }else {
            int idNum = Integer.parseInt(num);
            query = "DELETE FROM reservations WHERE guest_name='"+gName+"' AND reservation_id='"+idNum+"'";
        }

        try{
            PreparedStatement ps = con.prepareStatement(query);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected>0) System.out.println("Deleted...\n");
            else System.out.println("You have entered wrong data.");
        }
        catch (Exception e){
            System.out.println("Server Error!");
        }
    }

    public static boolean exit(Connection con,Scanner sc) throws InterruptedException {
        try{
            con.close();
        }
        catch (Exception e){
            System.out.println("Server Error!");
        }
        sc.close();

        System.out.print("Exiting");
        int i=1;
        while (i<=5)
        {
            Thread.sleep(500);
            System.out.print(".");
            i++;
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException {

        Connection con = getCon.getConnection();

        while (true)
        {
            System.out.println("1. New Reservation");
            System.out.println("2. Check Reservation");
            System.out.println("3. Get Room Number");
            System.out.println("4. Update Reservation");
            System.out.println("5. Delete Reservation");
            System.out.println("0. Exit");
            System.out.println("Choose the above option and enter number according to them: ");

            boolean flag = true;
            Scanner sc = new Scanner(System.in);
            int num = sc.nextInt();

            switch (num)
            {
                case 1 -> newReservation(con,sc);
                case 2 -> checkReservation(con,sc);
                case 3 -> getRoomNum(con,sc);
                case 4 -> updateReservation(con,sc);
                case 5 -> deleteReservation(con,sc);
                case 0 -> flag = exit(con,sc);
                default-> System.out.println("Invalid Input...");
            }
            if (!flag) break;
        }
    }
}
