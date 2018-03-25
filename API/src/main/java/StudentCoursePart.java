import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentCoursePart {

    private String SID;
    private int CPID;
    private String Date;
    private List<StudentCoursePart> studentCourseParts = new ArrayList<>();
    private List<StudentCoursePart> coursePartNotGradedStudens = new ArrayList<>();

    public List<StudentCoursePart> getStudentCoursePart(int kmid) {

        Connection conn = null;
        Statement stmt = null;


        try {
            final String DATABASE_URL = "jdbc:sqlserver://localhost:1433;database=ECDatabas;integratedSecurity=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(DATABASE_URL, "sa", "landskap");

            stmt = conn.createStatement();
            String sel = "SELECT * FROM StuderandeMoment WHERE KMID =" + kmid;
            ResultSet rs = stmt.executeQuery(sel);

            StudentCoursePart scp;
            while (rs.next()) {
                scp = new StudentCoursePart();
                scp.CPID = rs.getInt("KMID");
                scp.SID = rs.getString("EID");
                scp.Date = rs.getString("Datum");
                studentCourseParts.add(scp);
            }

            // TODO: Ändra detta. Gör filtreringen direkt i selecten!!
            coursePartNotGradedStudens = studentCourseParts.stream()
                    .filter(s -> s.Date == null)
                    .collect(Collectors.toList());

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

        return coursePartNotGradedStudens;
    }
}
