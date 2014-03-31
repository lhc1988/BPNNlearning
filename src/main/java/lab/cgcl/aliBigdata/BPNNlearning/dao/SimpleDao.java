package lab.cgcl.aliBigdata.BPNNlearning.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
