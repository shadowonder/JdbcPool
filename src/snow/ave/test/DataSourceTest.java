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
		//�����ж���
		System.out.println(PoolDataSource.getPoolSize());
		//�ڶ��α������������Ӻ͵�һ�εĵ�ַ��һ����˵��������Щ������Ҳû�иı�
		for (int i = 0; i < 10; i++) {
			Connection conn = PoolDataSource.getConnection();
			System.out.println(conn);
			conn.close();
		}
	}
}
