package sq;
import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Lite {
	Socket s;

	public Lite(Socket s) {
		this.s=s;
	}

	public void transact() throws Exception{
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter pw = new PrintWriter(s.getOutputStream());
			String request = br.readLine();
			System.out.println(request);
			if(request.equals("Login")){
				Connection conn = DriverManager.getConnection("jdbc:sqlite:AMGApp.db");
			    Statement stat = conn.createStatement();
			    String req = br.readLine();
			    System.out.println(req);
				ResultSet rs2 = stat.executeQuery(req);
				System.out.println(rs2.getString("rechthoehe"));
				pw.println(rs2.getString("rechthoehe"));
				pw.flush();
			}
			else if(request.equals("ITTeamMelden")){
				String datum="";
				String raum="";
				String wichtigk="";
				String fehler="";
				String beschr="";
				String status="";
				datum=new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
				raum=br.readLine();
				wichtigk=br.readLine();
				fehler=br.readLine();
				beschr=br.readLine();
				status=br.readLine();
				
				beschr=beschr.replaceAll("//", "\n");
				
				Class.forName("org.sqlite.JDBC");
			    Connection conn = DriverManager.getConnection("jdbc:sqlite:AMGApp.db");
			    Statement stat = conn.createStatement();
			    stat.executeUpdate("CREATE TABLE IF NOT EXISTS fehlermeldungen (datum, raum, wichtigkeit, fehler, beschr, status);");
			    PreparedStatement prep = conn.prepareStatement("insert into fehlermeldungen values (?, ?, ?, ?, ?, ?);");
			    
			    prep.setString(1, datum);
			    prep.setString(2, raum);
			    prep.setString(3, wichtigk);
			    prep.setString(4, fehler);
			    prep.setString(5, beschr);
			    prep.setString(6, status);
			    prep.addBatch();
			    
			    conn.setAutoCommit(false);
			    prep.executeBatch();
			    conn.setAutoCommit(true);
			    
			    /*ResultSet rs = stat.executeQuery("select * from fehlermeldungen where raum=\"A202\";");
			    while (rs.next())
			    {
			      System.out.println(rs.getString("raum"));
			    }
			    rs.close();*/
			    conn.close();
			}
			else if(request.equals("ITTeamHolen")){
				Connection conn = DriverManager.getConnection("jdbc:sqlite:AMGApp.db");
			    Statement stat = conn.createStatement();
			    String req = br.readLine();
			    System.out.println(req);
				ResultSet rs2 = stat.executeQuery(req);
				boolean ready=false;
				int rowcount = 0;
				while (!ready){
					if (!rs2.next()) {
						ready=true;
					}
					else {
						rowcount++;
					}
				}
				System.out.println(rowcount);
				pw.println(""+rowcount);
				pw.flush();
				ResultSet rs = stat.executeQuery(req);
			    while (rs.next()){
			      pw.println("Datum: "+rs.getString("datum")+"//Raum: "+rs.getString("raum")+"//Wichtigkeit: "+rs.getString("wichtigkeit")+"//Fehler: "+rs.getString("fehler")+"//Beschreibung: "+rs.getString("beschr")+"//Status: "+rs.getString("status"));
			      pw.flush();
			    }
			    rs.close();
			    stat.close();
			    conn.close();
			}
			else if(request.equals("ITTeamLoeschen")){
				Connection c = DriverManager.getConnection("jdbc:sqlite:AMGApp.db");
				c.setAutoCommit(false);
				Statement stmt = c.createStatement();
				String sql = br.readLine();
				stmt.executeUpdate(sql);
				c.commit();
			}
			
			br.close();
			pw.close();
			s.close();
		}
		catch (NullPointerException e){
			System.out.println("Internet überprüfen");
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		System.out.println("---------------------");

	}

}
