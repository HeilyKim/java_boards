package www.kb.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import www.kb.member.dao.MemberDao;
import www.kb.member.dto.request.*;
import www.kb.member.dto.response.MemberDTO;
import www.kb.security.util.LoginUtil;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service //얘가 서비스임
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao dao;

    public Map<String, Object> checkLoginId(IdCheckDTO dto) {
        boolean isUsable = true;
        String message = "사용 가능한 아이디 입니다.";

        Map<String, Object> map = new HashMap<>();
        int count = dao.selectMemberCountByLoginId(dto);
        if (count > 0) {
            isUsable = false;
            message = "이미 사용 중인 아이디 입니다.";
        }

        map.put("isUsable", isUsable);
        map.put("message", message);

        return map;
    }

    @Override
    @Transactional //insert delete udpate 일어나는 로직이있으면 이거 걸어라
    public Map<String, Object> joinMember(JoinDTO dto) {
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isEmpty(dto.getStrBirthday())) {
            dto.setBirthday(LocalDate.parse(
                    dto.getStrBirthday().substring(0, 4)
                            + "-" + dto.getStrBirthday().substring(4, 6)
                            + "-" + dto.getStrBirthday().substring(6))
            );
        }
        dto.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(dto.getPassword()));
        dao.registerMember(dto);
        map.put("isSuccess", true);
        return map;
    }

    @Override
    public MemberDTO getMemberInfo() {
        return dao.getMemberInfoByLoginId(LoginUtil.getLoginId());
    }

    @Override
    @Transactional //rollback commit여부
    public Map<String, Object> updateMember(UpdateDTO dto) {
        dto.setLoginId(LoginUtil.getLoginId());
        if (!StringUtils.isEmpty(dto.getStrBirthday())) {
            dto.setBirthday(LocalDate.parse(
                    dto.getStrBirthday().substring(0, 4)
                            + "-" + dto.getStrBirthday().substring(4, 6)
                            + "-" + dto.getStrBirthday().substring(6))
            );
        }
        dao.updateMemberInfo(dto);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "회원정보를 수정했따");
        map.put("isSuccess", true);
        return map;
    }

    @Override
    public Map<String, Object> findId(FindIdDTO dto) {
        String loginId = dao.findId(dto);
        Map<String, Object> map = new HashMap<>();
        if (loginId == null) {
            map.put("message", "회원정보를 찾을수 없따");
            map.put("isSuccess", false);
        } else {
            map.put("loginId", loginId);
            map.put("isSuccess", true);
        }
        return map;
    }

    @Override
    public Map<String, Object> findPassword(FindPasswordDTO dto) {
        String loginId = dao.getLoginIdForFindPassword(dto);
        Map<String, Object> map = new HashMap<>();
        if (loginId == null) {
            map.put("message", "회원정보를 찾을수 없따");
        } else {
            map.put("loginId", loginId);
        }
        map.put("isSuccess", true);
        return map;
    }

    @Override
    public Map<String, Object> updatePassword(PasswordUpdateDTO dto) {
        dto.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(dto.getPassword()));
        dao.updatePassword(dto);
        Map<String, Object> map = new HashMap<>();
        map.put("isSuccess", true);
        map.put("message", "비번을 변경했따");
        return map;
    }

    @Override
    public void leave() {
        dao.leave(LoginUtil.getLoginId());
    }
}
