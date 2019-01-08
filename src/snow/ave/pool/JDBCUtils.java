package snow.ave.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class JDBCUtils {
	/**
	 * 调用dbconfig.properties文件
	 */
	private static ResourceBundle bundle = ResourceBundle.getBundle("dbconfig");
	private static String driver;
	private static String url;
	private static String user;
	private static String password;
	/**
	 * static静态代码块加载文件，初始化四个参数
	 */
	static {
		driver = bundle.getString("DRIVER");
		url = bundle.getString("URL");
		user = bundle.getString("USER");
		password = bundle.getString("PASSWORD");
	}
	/**
	 * 通过静态代码块的参数，获取connection对象
	 */
	public static Connection getConnection(){
		try {
			//实例化driver模型，新版本不需要，但还是保留代码以防万一
			Class.forName(driver);
			return DriverManager.getConnection(url, user ,password);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	//关闭连接
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
