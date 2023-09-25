package www.kb.member.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import www.kb.member.dto.request.*;
import www.kb.member.dto.response.MemberDTO;

@Repository //얜 dao로 동작함
public class MemberDaoImpl implements MemberDao {
    @Autowired
    private SqlSession session; //여기부터 db허가권을 얻음

    public int selectMemberCountByLoginId(IdCheckDTO dto) {
        return session.selectOne("member.selectMemberCountByLoginId", dto); //db접속(바가지,두레박 필요 x)
        //applicationContext.xml -> mapperLocations -> memberMapper -> selectMemberCountByLoginId
    }

    public MemberDTO getLoginInfo(String id) {
        return session.selectOne("member.selectLoginInfoById", id);
    }

    @Override
    public void registerMember(JoinDTO dto) {
        session.insert("member.insertMemberDetail", dto);
        session.insert("member.insertMember", dto);
    }

    @Override
    public MemberDTO getMemberInfoByLoginId(String loginId) {
        return session.selectOne("member.selectMemberInfoByLoginId", loginId);
    }

    @Override
    public void updateMemberInfo(UpdateDTO dto) {
        session.update("member.updateMemberInfoByLoginId", dto);
    }

    @Override
    public String findId(FindIdDTO dto) {
        return session.selectOne("member.selectLoginIdForFindId", dto);
    }

    @Override
    public String getLoginIdForFindPassword(FindPasswordDTO dto) {
        return session.selectOne("member.selectLoginIdForFindPassword", dto);
    }

    @Override
    public String updatePassword(PasswordUpdateDTO dto) {
        return session.selectOne("member.updatePassword", dto);
    }

    @Override
    public void leave(String loginId) {
        session.update("member.updateLeavedState", loginId);
    }
}