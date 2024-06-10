package com.poscodx.guestbook.repository.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class SqlSession {
	private DataSource dataSource;
	
	public SqlSession(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public <T> List<T> query(String sql, RowMapper<T> rowMapper) {
		List<T> result = new ArrayList<>();
		return executeQueryWithStatementStrategy(new StatementStrategy() {
			@Override
			public PreparedStatement makeStatement(Connection connection) throws SQLException {
				PreparedStatement pstmt = connection.prepareStatement(sql);
				return pstmt;
			}
		}, rowMapper);
	}
	
	public int update(String sql) {
		return executeUpdateWithStatementStrategy(new StatementStrategy() {

			@Override
			public PreparedStatement makeStatement(Connection connection) throws SQLException {
				PreparedStatement pstmt = connection.prepareStatement(sql);
				return pstmt;
			}
			
		});
	}

	public int update(String sql, Object[] parameters) {
		return executeUpdateWithStatementStrategy(new StatementStrategy() {

			@Override
			public PreparedStatement makeStatement(Connection connection) throws SQLException {
				PreparedStatement pstmt = connection.prepareStatement(sql);
				for(int i = 0; i < parameters.length; i++) {
					pstmt.setObject(i+1, parameters[i]);
				}
				return pstmt;
			}
			
		});
	}
	
	public  <E> List<E> executeQueryWithStatementStrategy(StatementStrategy statementStrategy, RowMapper<E> rowMapper) {
		
		List<E> result = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			conn = DataSourceUtils.getConnection(dataSource);
			pstmt = statementStrategy.makeStatement(conn);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				E e = rowMapper.mapRow(rs, rs.getRow());
				result.add(e);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			
				try {
					if(pstmt != null) {
						pstmt.close();
					}
					if(conn != null) {
						DataSourceUtils.releaseConnection(conn, dataSource);
					}
				} catch (SQLException ignored) {
				}
		}
		
		return result;
	}
	
	public int executeUpdateWithStatementStrategy(StatementStrategy statementStrategy) {
		
		int result = 0; 
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			
			conn = DataSourceUtils.getConnection(dataSource);
			pstmt = statementStrategy.makeStatement(conn);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// 이렇게 던지면 올라타고가서 서비스 내에 있는 트랜잭션 과정에서 catch로 받게됨 
			throw new RuntimeException(e);		// 이게 sqlSessionTemplate mybatis에 다 들어있음 (jdbc template)
		} finally {
			
				try {
					if(pstmt != null) {
						pstmt.close();
					}
					if(conn != null) {
						DataSourceUtils.releaseConnection(conn, dataSource);
					}
				} catch (SQLException ignored) {
				}
		}
		
		return result;
	}

}
