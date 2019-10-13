function getUrlRelativePath() {
    var url = document.location.toString();
    var arrUrl = url.split("//");
    var start = arrUrl[1].indexOf("/");
    var relUrl = arrUrl[1].substring(start);//stop省略，截取从start开始到结尾的所有字符
    if (relUrl.indexOf("?") != -1) {
        relUrl = relUrl.split("?")[0];
    }
    return relUrl;
}

function addClass() {
    var url = getUrlRelativePath();
    var liList = $("#nav-id").find('li');
    for (var i = 0; i < liList.length; i++) {
        var hrefStr = $($(liList[i]).find('a')[0]).attr("href");
        if (url===hrefStr){
            $(liList[i]).addClass("active");
        }
    }
}