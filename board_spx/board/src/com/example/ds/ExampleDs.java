package com.example.ds;

import com.AppDao;
import com.common.JdbcUtil;

import java.sql.Connection;

import static com.common.JdbcUtil.*;

public class ExampleDs {
    private Connection conn;

    //물바가지랑 두레박 얻기
    private AppDao setDao() {
        AppDao dao = AppDao.getInstance(); //private 이라서 getInstance로 가져옴
        this.conn = JdbcUtil.getConnection();
        dao.setConnection(this.conn);
        return dao;
    }
    public void insertName(String name){
        AppDao dao = setDao();
        boolean isSuccess = dao.insertName(name);
        if (isSuccess){
            commit(this.conn); //connection에 대해 commit 함
        }else{
            rollback(this.conn);
        }
        close(conn);
    }
    public String selectNameById(int id){
        AppDao dao = setDao(); //db연결
        String name = dao.selectNameById(id);
        close(conn);
        return name;
    }
}
