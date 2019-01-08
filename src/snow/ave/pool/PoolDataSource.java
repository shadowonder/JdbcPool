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
	 * arraylist是线程不安全的，vector()是线程安全的，但是效率太低
	 * 所以使用connections.synchornizedlist来进行线程同步操作
	 */
	private static List<Connection> pool =
			Collections.synchronizedList(new  ArrayList<Connection>());
	
	//最大连接池数，可以从文件获取，这里懒了
	private static int POOL_MAX = 10;
	static {//先来十个尝尝鲜
		for(int i=0 ; i<POOL_MAX ; i++) {
			Connection conn = JDBCUtils.getConnection();
			pool.add(conn);
		}
	}
	
	/**
	 * 动态代理，修改close方法，在每一个传出的连接中修改close方法让他回到连接池中
	 */
	public static Connection getConnection() {
		Connection conn = pool.remove(0);
		
		Connection proxyConn = (Connection) Proxy.newProxyInstance(
				conn.getClass().getClassLoader(), //connection相同的类加载器
				conn.getClass().getInterfaces(), 
				new InvocationHandler() {//动态代理获取方法加强
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						Object rtValue = null;
						//判断当前方法是不是close方法
						if("close".equals(method.getName())) {
							pool.add(conn);//换回池中
						}else {
							rtValue = method.invoke(conn, args);//直接执行
						}
						return rtValue;
					}
				});
		return proxyConn;//返回代理对象
	}
	
	/**
	 * 获取池中的链接数
	 */
	public static int getPoolSize() {
		return pool.size();
	}
	
}
