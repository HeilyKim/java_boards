package com.common;
//db 컨트롤 하기위해 만듬//

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcUtil {
    public static Connection getConnection() { //접속정보로 접속하는 객체
        Connection conn = null;
        try {
            //Context 객체 초기화
            Context initCtx = new InitialContext(); //설정파일을 컨트롤함(설정값가져오는 객체)
            //Context.xml 로드
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            //Context.xml 정보에서 name이 "jdbc/MariaDB"인 resource 로드
            DataSource ds = (DataSource) envCtx.lookup("jdbc/MariaDB");
            //resource 정보에서 로드한 접속 정보 사용하여 connection 객체 얻어옴
            conn = ds.getConnection();
            //auto commit off
            conn.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    //Connection obj close(리소스 반환=물(data) 퍼 날랐던 바가지(사용권) 돌려줌)
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //PreparedStatement obj close(우물에서 물퍼는 두레박 돌려줌)
    //PreparedStatement -> 실제 쿼리 날리는 얘
    public static void close(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //최종 반환
    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //insert, update, delete 한 데이터 실제 저장-commit
    public static void commit(Connection conn) {
        if (conn != null) {
            try {
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //insert, update, delete procedure 간 오류 및 예외 처리 발생시 이전으로 롤백
    public static void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
