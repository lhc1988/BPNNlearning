package lab.cgcl.aliBigdata.BPNNlearning.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public class SimpleDao {
	protected DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	/**
	 * Constructor
	 */
	public SimpleDao () {
		DbcpPool pool = new DbcpPool();
		this.dataSource = pool.getDataSource();
	}
	
	/**
	 * 不含参数的简单查询
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public Map<?,?> retrieve(String sql) throws Exception {
		Connection con = dataSource.getConnection();
		Statement stmt =  con.createStatement();
		Map <String , Object> res = new HashMap<String , Object>();
		try {
			ResultSet rset = stmt.executeQuery(sql);
			if (rset.next()) {
				for (int i = 1 ; i  <= rset.getMetaData().getColumnCount() ; i ++) {
					res.put(rset.getMetaData().getColumnName(i) , rset.getString(i) );
				}
			}
		}finally {
			con.close();
		}
		
		return res;
	}
	
	/**
	 * 含参数的查询
	 * @param sql
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<?,?> preparedRetrieve(String sql , List<?> param) throws Exception {
		Connection con = dataSource.getConnection();
		PreparedStatement stmt = con.prepareStatement(sql);  
		Iterator<?> it = param.iterator();
		int i = 1;
		while (it.hasNext()) {
			try {
				stmt.setString(i, it.next().toString());
				i ++;
			} catch (SQLException e) {
				// parameter doesn't fit for the statement.
				break;
			}
		}
		Map <String , Object> res = new HashMap<String , Object>();
		try {
			ResultSet rset = stmt.executeQuery(sql);
			if (rset.next()) {
				for (i = 1 ; i  <= rset.getMetaData().getColumnCount() ; i ++) {
					res.put(rset.getMetaData().getColumnName(i) , rset.getString(i) );
				}
			}
		}finally {
			con.close();
		}
		
		return res;
	}
	
	/**
	 * 含参数的list
	 * @param sql
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map<String , Object>> preparedList(String sql , List<?> param) throws Exception {
		Connection con = dataSource.getConnection();
		PreparedStatement stmt = con.prepareStatement(sql);  
		Iterator<?> it = param.iterator();
		int i = 1;
		while (it.hasNext()) {
			try {
				stmt.setString(i, it.next().toString());
				i ++;
			} catch (SQLException e) {
				// parameter doesn't fit for the statement.
				break;
			}
		}
		List<Map<String , Object>> result = new ArrayList<Map<String , Object>>();
		try {
			ResultSet rset = stmt.executeQuery(sql);
			while (rset.next()) {
				Map <String , Object> res = new HashMap<String , Object>();

				for (i = 1 ; i  <= rset.getMetaData().getColumnCount() ; i ++) {
					res.put(rset.getMetaData().getColumnName(i) , rset.getString(i) );
				}
				result.add(res);
			}
		}finally {
			con.close();
		}
		
		return result;
	}
	
	public int preparedInsert (String strsql , List<?> param) throws Exception {
		Connection con = dataSource.getConnection();
		int ret = 0;
		try {
			PreparedStatement stmt = con.prepareStatement(strsql);  
			Iterator<?> it = param.iterator();
			int i = 1;
			while (it.hasNext()) {
				try {
					stmt.setString(i, it.next().toString());
					i ++;
				} catch (SQLException e) {
					// parameter doesn't fit for the statement.
					break;
				}
			}
			
			ret = stmt.executeUpdate();
		} finally {
			con.close();
		}
		return ret;
	}
	
	public int batchInsert(String strsql , List<List<?>> param) throws SQLException {
		Connection con = dataSource.getConnection();
		try {
		PreparedStatement stmt = con.prepareStatement(strsql);  
		stmt.clearBatch();
		for (List l : param) {
			Iterator<?> it = l.iterator();
			int i = 1;
			while (it.hasNext()) {
				try {
					stmt.setString(i, it.next().toString());
					i ++;
				} catch (SQLException e) {
					break;
				}
			}
			stmt.addBatch();
		}
		stmt.executeBatch();
		} finally {
			con.close();
		}
		return param.size();
	}
	

}
