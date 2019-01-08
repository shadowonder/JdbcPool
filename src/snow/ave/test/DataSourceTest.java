package snow.ave.test;

import java.sql.Connection;
import java.sql.SQLException;

import snow.ave.pool.PoolDataSource;

public class DataSourceTest {
	public static void main(String[] args) throws SQLException {
		System.out.println(PoolDataSource.getPoolSize());
		
		for (int i = 0; i < 10; i++) {
			Connection conn = PoolDataSource.getConnection();
			System.out.println(conn);
			conn.close();
		}
		//看看有多少
		System.out.println(PoolDataSource.getPoolSize());
		//第二次遍历的所有连接和第一次的地址都一样，说明还是那些，数量也没有改变
		for (int i = 0; i < 10; i++) {
			Connection conn = PoolDataSource.getConnection();
			System.out.println(conn);
			conn.close();
		}
	}
}
