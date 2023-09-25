package com.member.ds;

import com.AppDao;

import static com.common.JdbcUtil.*;

import com.common.JdbcUtil;
import com.dto.MemberDTO;

import java.sql.Connection;


public class MemberDs {
    private Connection conn;

    //물바가지랑 두레박 얻기
    private AppDao setDao() {
        AppDao dao = AppDao.getInstance(); //private 이라서 getInstance로 가져옴
        this.conn = JdbcUtil.getConnection();
        dao.setConnection(this.conn);
        return dao;
    }

    public int selectAccountCountByLoginId(String loginId) {
        AppDao dao = setDao();
        int count = dao.selectAccountCountByLoginId(loginId);
        close(conn);
        return count;
    }

    public boolean insertMemberInfo(MemberDTO dto) {
        AppDao dao = setDao();
        boolean isSuccess = dao.insertMemberInfo(dto);
        if (isSuccess) {
            commit(this.conn); //connection에 대해 commit 함
        } else {
            rollback(this.conn);
        }
        close(conn);
        return isSuccess;
    }

    public MemberDTO selectMemberByInfoByLoginId(String loginId) {
        AppDao dao = setDao();
        MemberDTO dto = dao.selectMemberByInfoByLoginId(loginId);
        close(conn);
        return dto;
    }

    public MemberDTO selectMemberByInfoById(int id) {
        AppDao dao = setDao();
        MemberDTO dto = dao.selectMemberByInfoById(id);
        close(conn);
        return dto;
    }

    public boolean updateMemberInfo(MemberDTO dto) {
        AppDao dao = setDao();
        boolean isSuccess = dao.updateMemberInfo(dto);
        if (isSuccess) {
            commit(this.conn); //connection에 대해 commit 함
        } else {
            rollback(this.conn);
        }
        close(conn);
        return isSuccess;
    }

    public String selectLoginIdForFindId(MemberDTO dto) {
        AppDao dao = setDao();
        String loginId = dao.selectLoginIdForFindId(dto);
        close(conn);
        return loginId;
    }

    public int selectMemberIdForFindPassword(MemberDTO dto) {
        AppDao dao = setDao();
        int id = dao.selectMemberIdForFindPassword(dto);
        close(conn);
        return id;
    }

    public boolean updateMemberPassword(MemberDTO dto) {
        AppDao dao = setDao();
        boolean isSuccess = dao.updateMemberPassword(dto);
        if (isSuccess) {
            commit(this.conn);
        } else {
            rollback(this.conn);
        }
        close(conn);
        return isSuccess;
    }

    public boolean updateLeave(int id) {
        AppDao dao = setDao();
        boolean isSuccess = dao.updateLeave(id);
        if (isSuccess) {
            commit(this.conn);
        } else {
            rollback(this.conn);
        }
        close(conn);
        return isSuccess;
    }


}
