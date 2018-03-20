import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Course {

    private String CID;
    private String courseName;
    private String courseInfo;
    private String courseCredits;

    public static List<Course> getClasses(String eid) {

        Connection conn = null;
        Statement stmt = null;

        List<Course> courses = new ArrayList<>();
        try {
            final String DATABASE_URL = "jdbc:sqlserver://localhost:1433;database=ECDatabas;integratedSecurity=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(DATABASE_URL, "sa", "landskap");

            stmt = conn.createStatement();
            String sel = "SELECT * FROM UtbSchema WHERE KursAnsv = " + eid;
            ResultSet rs = stmt.executeQuery(sel);

            Course c;
            while(rs.next()){
                c = new Course();
                c.CID = rs.getString("CID");
                c.courseName = rs.getString("KursNamn");
                c.courseInfo = rs.getString("KursInfo");
                c.courseCredits = rs.getString("KursPoang");
                courses.add(c);
            }

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

        return courses;
    }

}
