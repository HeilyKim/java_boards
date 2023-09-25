package com.board.ds;

import com.AppDao;
import com.common.JdbcUtil;
import com.common.MyPagination;
import com.dto.BoardDTO;

import java.sql.Connection;
import java.util.List;

import static com.common.JdbcUtil.*;
//DAO가 할일이 많아서 애한테 임시 분배함

public class BoardDs {
    private Connection conn;

    //물바가지랑 두레박 얻기
    private AppDao setDao() {
        AppDao dao = AppDao.getInstance(); //private 이라서 getInstance로 가져옴
        this.conn = JdbcUtil.getConnection();
        dao.setConnection(this.conn);
        return dao;
    }

    public boolean insertBoard(BoardDTO dto) {
        AppDao dao = setDao();
        boolean isSuccess = dao.insertBoard(dto);
        if (isSuccess) {
            commit(this.conn);
        } else {
            rollback(this.conn);
        }
        close(conn);
        return isSuccess;
    }

    public boolean deleteBoardById(int id) {
        AppDao dao = setDao();
        boolean isSuccess = dao.deleteBoardById(id);
        if (isSuccess) {
            commit(this.conn);
        } else {
            rollback(this.conn);
        }
        close(conn);
        return isSuccess;
    }

    public boolean updateBoard(BoardDTO dto) {
        AppDao dao = setDao();
        boolean isSuccess = dao.updateBoard(dto);
        if (isSuccess) {
            commit(this.conn);
        } else {
            rollback(this.conn);
        }
        close(conn);
        return isSuccess;
    }

    public int selectTotalArticleCount(String extraQuery) {
        AppDao dao = setDao();
        int count = dao.selectTotalArticleCount(extraQuery);
        close(conn);
        return count;
    }

    public List<BoardDTO> selectBoardList(MyPagination mp, String extraQuery) {
        AppDao dao = setDao();
        List<BoardDTO> list = dao.selectBoardList(mp, extraQuery);
        close(conn);
        return list;

    }

    public BoardDTO selectBoardDetailById(int id) {
        AppDao dao = setDao();
        BoardDTO dto = dao.selectBoardDetailById(id);
        close(conn);
        return dto;
    }


}
