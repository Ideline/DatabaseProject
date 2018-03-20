import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Student {

    private String SID;
    private String EDID;
    private String fName;
    private String lName;
    private String sAddress;
    private String zipCode;
    private String city;
    private String email;
    private String phNumb;

    public List<Student> getStudents(String eid){
        Connection conn = null;
        Statement stmt = null;

        List<Student> students = new ArrayList<>();
        try{
            final String DATABASE_URL = "jdbc:sqlserver://localhost:1433;database=ECDatabas;integratedSecurity=true";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(DATABASE_URL,"sa","landskap");

            stmt = conn.createStatement();
            //String sel = "SELECT * FROM Studerande" + (eid == null ? "" : " WHERE eid LIKE '" + eid + "%'");
            String sel = "SELECT * FROM Studerande s JOIN UtbSchema u ON s.UID = u.UID WHERE u.KursAnsv = " + eid;
            ResultSet rs = stmt.executeQuery(sel);

            Student s;
            while(rs.next()) {
                s = new Student();
                s.SID = rs.getString("EID");
                s.EDID = rs.getString("UID");
                s.fName = rs.getString("FNamn");
                s.lName = rs.getString("ENamn");
                s.email = rs.getString("Epost");
                s.sAddress = rs.getString("GAdress");
                s.zipCode = rs.getString("PostNr");
                s.city = rs.getString("Ort");
                s.phNumb = rs.getString("TfnNr");
                students.add(s);
            }

            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }
            catch(SQLException se2){ }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

        return students;
    }
}