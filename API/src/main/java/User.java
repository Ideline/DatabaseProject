import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String EID;
    private String fName;
    private String lName;
    private String sAddress;
    private int zipCode;
    private String city;
    private String email;
    private String phNumb;
    private String accNumb;
    private int salary;
    private int posID;
    private int serviceGr;
    private String password;
    private List<Course> courses = new ArrayList<>();
    private List<CoursePart> courseParts = new ArrayList<>();
    private User user = new User();

    public List<Course> getCourses() {
        return courses;
    }

    public User getUser(String eid, String pw) {


        Connection conn = null;
        Statement stmt = null;

        try {
            final String DATABASE_URL = "jdbc:sqlserver://localhost:1433;database=ECDatabas;integratedSecurity=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(DATABASE_URL, "sa", "landskap");

            stmt = conn.createStatement();
            String sel = "SELECT * FROM Anstalld" + (eid == null ? "" : " WHERE AID = " + eid);
            ResultSet rs = stmt.executeQuery(sel);

            user.EID = rs.getString("AID");
            user.fName = rs.getString("FNamn");
            user.lName = rs.getString("ENamn");
            user.email = rs.getString("Epost");
            user.sAddress = rs.getString("GAdress");
            user.zipCode = rs.getInt("PostNr");
            user.city = rs.getString("Ort");
            user.phNumb = rs.getString("TfnNr");
            user.password = rs.getString("LosenOrd");
            user.salary = rs.getInt("GLon");
            user.posID = rs.getInt("BefID");
            user.accNumb = rs.getString("KontoNr");
            user.serviceGr = rs.getInt("TjanstGr");

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        if (user.password == pw) {

            return user;
        }
        return null;
    }


}
