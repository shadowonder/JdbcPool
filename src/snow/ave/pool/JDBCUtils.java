package snow.ave.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class JDBCUtils {
	/**
	 * ����dbconfig.properties�ļ�
	 */
	private static ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");
	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	/**
	 * static��̬���������ļ�����ʼ���ĸ�����
	 */
	static {
		driver = bundle.getString("DRIVER");
		url = bundle.getString("URL");
		user = bundle.getString("USER");
		password = bundle.getString("PASSWORD");
	}
	/**
	 * ͨ����̬�����Ĳ�������ȡconnection����
	 */
	public static Connection getConnection(){
		try {
			//ʵ����driverģ�ͣ��°汾����Ҫ�������Ǳ��������Է���һ
			Class.forName(driver);
			return DriverManager.getConnection(url, user ,password);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	//�ر�����
	public static void release(ResultSet rs, Statement st, Connection conn) {
		if(rs!=null) {
			try {rs.close();}catch(Exception e) {e.printStackTrace();}
		}
		if(st!=null) {
			try {st.close();}catch(Exception e) {e.printStackTrace();}
		}
		if(conn!=null) {
			try {conn.close();}catch(Exception e) {e.printStackTrace();}
		}
	}
}
