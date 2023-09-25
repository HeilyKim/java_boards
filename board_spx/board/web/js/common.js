function getValueOfParameter(key) {
    var urlParams = new URLSearchParams(location.search);// 주소 ?포함 뒤 다 저장
    var value = urlParams.get(key);
    if (value == null || value == undefined) {
        value = "";
    }
    return value;
}