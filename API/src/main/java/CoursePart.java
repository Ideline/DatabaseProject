import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursePart {

    private int CPID;
    private String CID;
    private String coursePart;

    public List<CoursePart> getCourseParts(String cid) {

        Connection conn = null;
        Statement stmt = null;

        List<CoursePart> courseParts = new ArrayList<>();
        try {
            final String DATABASE_URL = "jdbc:sqlserver://localhost:1433;database=ECDatabas;integratedSecurity=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(DATABASE_URL, "sa", "landskap");

            stmt = conn.createStatement();
            String sel = "SELECT * FROM KursMoment WHERE KID = '" + cid + "'";
            ResultSet rs = stmt.executeQuery(sel);

            CoursePart cp;
            while (rs.next()) {
                cp = new CoursePart();
                cp.CID = rs.getString("KID");
                cp.CPID = rs.getInt("KMID");
                cp.coursePart = rs.getString("KursMoment");
                courseParts.add(cp);
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

        return courseParts;
    }

    public CoursePart getCoursePart(String part) {

        Connection conn = null;
        Statement stmt = null;

        CoursePart cp = new CoursePart();
        try {
            final String DATABASE_URL = "jdbc:sqlserver://localhost:1433;database=ECDatabas;integratedSecurity=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(DATABASE_URL, "sa", "landskap");

            stmt = conn.createStatement();
            String sel = "SELECT * FROM KursMoment";
            ResultSet rs = stmt.executeQuery(sel);


            while (rs.next()) {
                cp.CID = rs.getString("KID");
                cp.CPID = rs.getInt("KMID");
                cp.coursePart = rs.getString("KursMoment");
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

        return cp;
    }
}
