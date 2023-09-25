<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="`">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://code.jquery.com/jquery-3.7.0.js"
            integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM=" crossorigin="anonymous"></script>
    <title>Document</title>
    <script>
        function findPassword() {
            var loginId = $('#loginId');
            var name = $('#name');
            var mobileNo = $('#mobileNo');
            var email = $('#email');

            // id 입력 여부 확인
            if (loginId.val() == '') {
                alert('id는 필수 입력 값입니다.');
                loginId.focus();
                return;
            }
            // 이름 입력 여부 확인
            if (name.val() == '') {
                alert('이름은 필수 입력 값입니다.');
                name.focus();
                return;
            }
            // 휴대전화번호 입력 여부 확인
            if (mobileNo.val() == '') {
                alert('휴대전화번호는 필수 입력 값입니다.');
                mobileNo.focus();
                return;
            }
            // 이메일 입력 여부 확인
            if (email.val() == '') {
                alert('이메일은 필수 입력 값입니다.');
                email.focus();
                return;
            }

            // 이름 정규표현식 통과 여부 확인
            var regexp = new RegExp('^[가-힣]{2,10}$');
            if (regexp.exec(name.val()) == null) {
                alert('이름은 2~10자의 한글로 입력해 주세요');
                name.focus();
                return;
            }
            // 휴대전화번호 정규표현식 통과 여부 확인
            regexp = new RegExp('^[0-9]{10,12}$');
            if (regexp.exec(mobileNo.val()) == null) {
                alert('휴대전화번호는 10~12자 숫자로 입력해 주세요.');
                mobileNo.focus();
                return;
            }
            // 이메일 정규표현식 통과 여부 확인
            regexp = new RegExp('^.{1,50}$');
            if (regexp.exec(email.val()) == null) {
                alert('이메일은 50자 이내로 입력해 주세요.');
                email.focus();
                return;
            }
            // 전송
            $.ajax({
                url: "/member/password/find/result.do"
                , type: "post"
                , async: true
                , dataType: "json"
                , data: {
                    loginId: loginId.val()
                    , name: name.val()
                    , mobileNo: mobileNo.val()
                    , email: email.val()
                }
                , error: function () {
                    alert('서버와 통신하는데 실패하였습니다.');
                }
                , success: function (data) {
                    if (data.isSuccess == 'true') {
                        if (data.message == 'null') { //회원정보 있을때
                            location.href = '/member/password/update.do'
                        } else {
                            alert(data.message)
                        }
                    } else {
                        alert(data.message);
                    }
                }
            });
        }
    </script>
</head>
<body>
<div>
    아이디
    <input type="text" id="loginId" maxlength="15">
</div>
<div>
    이름
    <input type="text" id="name" maxlength="10">
</div>
<div>
    휴대전화번호
    <input type="text" id="mobileNo" maxlength="12">
</div>
<div>
    이메일
    <input type="text" id="email" maxlength="50">
</div>
<button onclick="findPassword()">확인</button>
<button onclick="location.href='/login.do'">취소</button>
</body>
</html>