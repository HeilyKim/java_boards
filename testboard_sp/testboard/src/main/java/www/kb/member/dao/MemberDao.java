package www.kb.member.dao;

import www.kb.member.dto.request.*;
import www.kb.member.dto.response.MemberDTO;

public interface MemberDao {
    int selectMemberCountByLoginId(IdCheckDTO dto);

    MemberDTO getLoginInfo(String id);

    void registerMember(JoinDTO dto);

    MemberDTO getMemberInfoByLoginId(String loginId);

    void updateMemberInfo(UpdateDTO dto);

    String findId(FindIdDTO dto);

    String getLoginIdForFindPassword(FindPasswordDTO dto);

    String updatePassword(PasswordUpdateDTO dto);

    void leave(String loginId);
}
