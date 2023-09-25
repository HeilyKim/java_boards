package com;

import com.common.MyPagination;
import com.dto.BoardDTO;
import com.dto.MemberDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.common.JdbcUtil.close;

public class AppDao {
    private Connection conn;

    //**singleton**//
    private AppDao() {

    }

    private static class LazyHolder {
        private static final AppDao INSTANCE = new AppDao();
    }

    public static AppDao getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    public boolean insertName(String name) {
        PreparedStatement pstmt = null; //PreparedStatement->실제 쿼리 날리는 얘
        int count = 0;
        try {
            pstmt = conn.prepareStatement(
                    "INSERT INTO my_name(name) VALUES (?)");
            pstmt.setString(1, name);
            count = pstmt.executeUpdate(); //insert,update,delete 된 row 갯수를 리턴해줌
        } catch (Exception e) {
            e.printStackTrace();
        } finally {        //예외 발생 여부 상관 없이 걍 다 실행됨
            close(pstmt); //두레박은 돌려줘야지
        }
        return count > 0 ? true : false;
    }

    public String selectNameById(int id) {
        PreparedStatement pstmt = null; //두레박
        ResultSet rs = null; //바가지
        String name = null;
        try {
            pstmt = conn.prepareStatement(
                    "SELECT name FROM my_name WHERE id = ?");
            pstmt.setInt(1, id); // ? 자리 매치
            rs = pstmt.executeQuery();

            //데이터 뽑기 next()- 첫 위치에서 찾는 데이터 있는지, 있으면 T 없으면 F 리턴 동시에 다음 위치 이동
            while (rs.next()) {
                name = rs.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);  //바가지 먼저 돌려줘야됨
            close(pstmt);
        }
        return name;
    }

    public int selectAccountCountByLoginId(String loginId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        try {
            pstmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM member WHERE login_id = ? AND leaved = false ");
            pstmt.setString(1, loginId);
            rs = pstmt.executeQuery();

            //데이터 뽑기 next()- 첫 위치에서 찾는 데이터 있는지, 있으면 T 없으면 F 리턴 동시에 다음 위치 이동
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);  //바가지 먼저 돌려줘야됨
            close(pstmt);
        }
        return count;
    }

    public boolean insertMemberInfo(MemberDTO dto) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count1 = 0;
        int count2 = 0;
        try {
            pstmt = conn.prepareStatement(
                    "INSERT INTO member_detail(name,mobile_no,birthday,email,zipcode," +
                            "address,address_detail) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, dto.getName());
            pstmt.setString(2, dto.getMobileNo());
            pstmt.setObject(3, dto.getBirthday());
            pstmt.setString(4, dto.getEmail());
            pstmt.setString(5, dto.getZipcode());
            pstmt.setString(6, dto.getAddress());
            pstmt.setString(7, dto.getAddressDetail());
            count1 = pstmt.executeUpdate();
            if (count1 == 0) {
                close(rs);
                close(pstmt);
                return false;
            }
            rs = pstmt.getGeneratedKeys(); //위 쿼리 실행후 생성된 id값이 반환됨
            if (rs.next()) {
                dto.setMemberDetailId(rs.getInt(1));
            }
            pstmt = conn.prepareStatement(
                    "insert into member(login_id, password, member_detail_id) values (?,?,?)");
            pstmt.setString(1, dto.getLoginId());
            pstmt.setString(2, dto.getPassword());
            pstmt.setInt(3, dto.getMemberDetailId());
            count2 = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return count2 > 0 ? true : false;
    }

    public MemberDTO selectMemberByInfoByLoginId(String loginId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null; //바가지
        MemberDTO dto = null;
        try {
            pstmt = conn.prepareStatement(
                    "SELECT a.id,a.login_id,a.password," +
                            "b.name,b.birthday,b.mobile_no,b.email,b.zipcode,b.address,b.address_detail" +
                            " FROM member a inner join member_detail b on a.member_detail_id=b.id" +
                            " WHERE login_id = ? and leaved = false");
            pstmt.setString(1, loginId);
            rs = pstmt.executeQuery();

            //데이터 뽑기 next()- 첫 위치에서 찾는 데이터 있는지, 있으면 T 없으면 F 리턴 동시에 다음 위치 이동
            while (rs.next()) {
                dto = MemberDTO.builder()
                        .id(rs.getInt("id"))
                        .loginId(rs.getString("login_id"))
                        .password(rs.getString("password"))
                        .name(rs.getString("name"))
                        .birthday(LocalDate.parse(rs.getString("birthday")))
                        .mobileNo(rs.getString("mobile_no"))
                        .email(rs.getString("email"))
                        .zipcode(rs.getString("zipcode"))
                        .address(rs.getString("address"))
                        .addressDetail(rs.getString("address_detail"))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);  //바가지 먼저 돌려줘야됨
            close(pstmt);
        }
        return dto;
    }

    public MemberDTO selectMemberByInfoById(int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null; //바가지
        MemberDTO dto = null;
        try {
            pstmt = conn.prepareStatement(
                    "SELECT a.id,a.login_id,a.password," +
                            "b.name,b.birthday,b.mobile_no,b.email,b.zipcode,b.address,b.address_detail" +
                            " FROM member a inner join member_detail b on a.member_detail_id=b.id" +
                            " WHERE a.id = ? and a.leaved = false");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            //데이터 뽑기 next()- 첫 위치에서 찾는 데이터 있는지, 있으면 T 없으면 F 리턴 동시에 다음 위치 이동
            while (rs.next()) {
                dto = MemberDTO.builder()
                        .id(rs.getInt("id"))
                        .loginId(rs.getString("login_id"))
                        .password(rs.getString("password"))
                        .name(rs.getString("name"))
                        .birthday(LocalDate.parse(rs.getString("birthday")))
                        .mobileNo(rs.getString("mobile_no"))
                        .email(rs.getString("email"))
                        .zipcode(rs.getString("zipcode"))
                        .address(rs.getString("address"))
                        .addressDetail(rs.getString("address_detail"))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);  //바가지 먼저 돌려줘야됨
            close(pstmt);
        }
        return dto;
    }

    public boolean updateMemberInfo(MemberDTO dto) {
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = conn.prepareStatement("UPDATE member_detail " +
                    "SET name=?,mobile_no=?,email=?,birthday=?,zipcode=?,address=?,address_detail=? " +
                    "WHERE id=(SELECT member_detail_id FROM member WHERE id=?)");
            pstmt.setString(1, dto.getName());
            pstmt.setString(2, dto.getMobileNo());
            pstmt.setString(3, dto.getEmail());
            pstmt.setObject(4, dto.getBirthday());
            pstmt.setString(5, dto.getZipcode());
            pstmt.setString(6, dto.getAddress());
            pstmt.setString(7, dto.getAddressDetail());
            pstmt.setInt(8, dto.getId());
            count = pstmt.executeUpdate(); //insert,update,delete 된 row 갯수를 리턴해줌
        } catch (Exception e) {
            e.printStackTrace();
        } finally {        //예외 발생 여부 상관 없이 걍 다 실행됨
            close(pstmt); //두레박은 돌려줘야지
        }
        return count > 0 ? true : false;
    }

    public String selectLoginIdForFindId(MemberDTO dto) {
        PreparedStatement pstmt = null;
        ResultSet rs = null; //바가지
        String loginId = null;
        try {
            pstmt = conn.prepareStatement(
                    "SELECT a.login_id " +
                            "FROM member a inner join member_detail b on a.member_detail_id=b.id" +
                            " WHERE b.name = ? and b.mobile_no=? and b.email=? and a.leaved = false");
            pstmt.setString(1, dto.getName());
            pstmt.setString(2, dto.getMobileNo());
            pstmt.setString(3, dto.getEmail());
            rs = pstmt.executeQuery();

            //데이터 뽑기 next()- 첫 위치에서 찾는 데이터 있는지, 있으면 T 없으면 F 리턴 동시에 다음 위치 이동
            while (rs.next()) {
                loginId = rs.getString("login_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);  //바가지 먼저 돌려줘야됨
            close(pstmt);
        }
        return loginId;
    }

    public int selectMemberIdForFindPassword(MemberDTO dto) {
        PreparedStatement pstmt = null;
        ResultSet rs = null; //바가지
        int id = 0;
        try {
            pstmt = conn.prepareStatement(
                    "SELECT a.id " +
                            "FROM member a inner join member_detail b on a.member_detail_id=b.id" +
                            " WHERE b.name = ? and b.mobile_no=? and b.email=? and a.leaved = false and a.login_id=?");
            pstmt.setString(1, dto.getName());
            pstmt.setString(2, dto.getMobileNo());
            pstmt.setString(3, dto.getEmail());
            pstmt.setString(4, dto.getLoginId());
            rs = pstmt.executeQuery();

            //데이터 뽑기 next()- 첫 위치에서 찾는 데이터 있는지, 있으면 T 없으면 F 리턴 동시에 다음 위치 이동
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);  //바가지 먼저 돌려줘야됨
            close(pstmt);
        }
        return id;
    }

    public boolean updateMemberPassword(MemberDTO dto) {
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = conn.prepareStatement("UPDATE member" +
                    " SET password=?" +
                    "WHERE id=?");
            pstmt.setString(1, dto.getPassword());
            pstmt.setInt(2, dto.getId());
            count = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return count > 0 ? true : false;
    }

    public boolean updateLeave(int id) {
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = conn.prepareStatement("UPDATE member" +
                    " SET leaved=true " +
                    "WHERE id=?");
            pstmt.setInt(1, id);
            count = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return count > 0 ? true : false;
    }

    public boolean insertBoard(BoardDTO dto) {
        PreparedStatement pstmt = null; //PreparedStatement->실제 쿼리 날리는 얘
        int count = 0;
        try {
            pstmt = conn.prepareStatement(
                    "INSERT INTO board(title,contents,register_id) values (?,?,?)");
            pstmt.setString(1, dto.getTitle());
            pstmt.setString(2, dto.getContents());
            pstmt.setInt(3, dto.getRegisterId());
            count = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {        //예외 발생 여부 상관 없이 걍 다 실행됨
            close(pstmt); //두레박은 돌려줘야지
        }
        return count > 0 ? true : false;
    }

    public boolean deleteBoardById(int id) {
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = conn.prepareStatement(
                    "update board set deleted=true where id = ?");
            pstmt.setInt(1, id);
            count = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return count > 0 ? true : false;
    }

    public boolean updateBoard(BoardDTO dto) {
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = conn.prepareStatement(
                    "update board " +
                            "set title=?, " +
                            "contents=? " +
                            "where id =? and register_id=?");
            pstmt.setString(1, dto.getTitle());
            pstmt.setString(2, dto.getContents());
            pstmt.setInt(3, dto.getId());
            pstmt.setInt(4, dto.getRegisterId());
            count = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return count > 0 ? true : false;
    }


    //목록은 null 체크 안 함
    public List<BoardDTO> selectBoardList(MyPagination mp, String extraQuery) {
        PreparedStatement pstmt = null;
        ResultSet rs = null; //바가지
        List<BoardDTO> list = new ArrayList<>();
        try {
            pstmt = conn.prepareStatement(
                    "SELECT a.id, a.title, a.hits, a.register_datetime, b.login_id " +
                            "FROM board a " +
                            "inner join member b on a.register_id = b.id " +
                            "WHERE a.deleted=false" + extraQuery +
                            " limit ?,?");
            pstmt.setInt(1, mp.getStartArticleNumber());
            pstmt.setInt(2, mp.getArticleCountPerPage());
            rs = pstmt.executeQuery();
            //데이터 뽑기 next()- 첫 위치에서 찾는 데이터 있는지, 있으면 T 없으면 F 리턴 동시에 다음 위치 이동
            while (rs.next()) {
                BoardDTO dto = BoardDTO.builder()
                        .id(rs.getInt("id"))
                        .title(rs.getString("title"))
                        .hits(rs.getInt("hits"))
                        .registerDatetime(rs.getString("register_datetime"))
                        .registerLoginId(rs.getString("login_id"))
                        .build();
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return list;
    }

    public BoardDTO selectBoardDetailById(int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BoardDTO dto = null;
        try {
            pstmt = conn.prepareStatement(
                    "SELECT a.id, a.title, a.hits, a.contents, a.register_datetime, a.register_id,b.login_id " +
                            "FROM board a " +
                            "inner join member b on a.register_id = b.id " +
                            "WHERE a.deleted=false and a.id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            //데이터 뽑기 next()- 첫 위치에서 찾는 데이터 있는지, 있으면 T 없으면 F 리턴 동시에 다음 위치 이동
            while (rs.next()) {
                dto = BoardDTO.builder()
                        .id(rs.getInt("id"))
                        .title(rs.getString("title"))
                        .contents(rs.getString("contents"))
                        .hits(rs.getInt("hits"))
                        .registerId(rs.getInt("register_id"))
                        .registerDatetime(rs.getString("register_datetime"))
                        .registerLoginId(rs.getString("login_id"))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return dto;
    }

    public int selectTotalArticleCount(String extraQuery) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        try {
            pstmt = conn.prepareStatement(
                    "SELECT count(*) " +
                            "FROM board a " +
                            "inner join member b on a.register_id = b.id " +
                            "WHERE a.deleted=false" + extraQuery);
            rs = pstmt.executeQuery();
            //데이터 뽑기 next()- 첫 위치에서 찾는 데이터 있는지, 있으면 T 없으면 F 리턴 동시에 다음 위치 이동
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstmt);
        }
        return count;
    }


}
