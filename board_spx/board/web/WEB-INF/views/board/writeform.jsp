<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://code.jquery.com/jquery-3.7.0.js"
            integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM=" crossorigin="anonymous"></script>
    <title>Document</title>
    <script>
        function register() {
            var title = $('#title');
            var contents = $('#contents');
            if (title.val() == '') {
                alert('제목을 입력해줘라');
                title.focus();
                return;
            }
            if (contents.val() == '') {
                alert('내용을 입력해줘라');
                contents.focus();
                return;
            }
            if (title.val().length > 50) {
                alert('제목은 50자 이내로 입력해줘라');
                title.focus();
                return;
            }
            $.ajax({
                url: "/board/write/process.do"
                , type: "post"
                , async: true
                , dataType: "json"
                , data: {
                    title: title.val()
                    , contents: contents.val()
                }
                , error: function () {
                    alert('서버와 통신하는데 실패하였습니다.');
                }
                , success: function (data) {
                    alert(data.message);
                    if (data.isSuccess == 'true') {
                        location.replace('/board/list.do'); //이전 페이지로 가서 다시 수정 및 저장 불가
                    }
                }
            });

        }
    </script>
</head>
<body>
<div>
    제목
    <input type="text" id="title" maxlength="50">
</div>
<div>
    내용
    <textarea id="contents" cols="30" rows="10"></textarea>
</div>
<button onclick="register()">저장</button>
<button onclick="location.href='/board/list.do'">목록</button>
</body>
</html>