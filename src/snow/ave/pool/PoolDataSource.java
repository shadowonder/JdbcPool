package snow.ave.pool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PoolDataSource {
	/*
	 * arraylist���̲߳���ȫ�ģ�vector()���̰߳�ȫ�ģ�����Ч��̫��
	 * ����ʹ��connections.synchornizedlist�������߳�ͬ������
	 */
	private static List<Connection> pool =
			Collections.synchronizedList(new  ArrayList<Connection>());
	
	//������ӳ��������Դ��ļ���ȡ����������
	private static int POOL_MAX = 10;
	static {//����ʮ��������
		for(int i=0 ; i<POOL_MAX ; i++) {
			Connection conn = JDBCUtils.getConnection();
			pool.add(conn);
		}
	}
	
	/**
	 * ��̬�����޸�close��������ÿһ���������������޸�close���������ص����ӳ���
	 */
	public static Connection getConnection() {
		Connection conn = pool.remove(0);
		
		Connection proxyConn = (Connection) Proxy.newProxyInstance(
				conn.getClass().getClassLoader(), //connection��ͬ���������
				conn.getClass().getInterfaces(), 
				new InvocationHandler() {//��̬�����ȡ������ǿ
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						Object rtValue = null;
						//�жϵ�ǰ�����ǲ���close����
						if("close".equals(method.getName())) {
							pool.add(conn);//���س���
						}else {
							rtValue = method.invoke(conn, args);//ֱ��ִ��
						}
						return rtValue;
					}
				});
		return proxyConn;//���ش������
	}
	
	/**
	 * ��ȡ���е�������
	 */
	public static int getPoolSize() {
		return pool.size();
	}
	
}
