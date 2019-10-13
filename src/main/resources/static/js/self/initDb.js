$("#init-info").click(function () {
    var url = $("#url").val();
    var username = $("#username").val();
    var password = $("#password").val();
    var data= {
        url:url,
        username:username,
        password:password
    };
    $.ajax({
        type: "POST",
        url: "initDbInfo",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(data),
        success: function (res) {
            alert(res.data)
        }

    });
});

$(document).ready(function () {
    $.ajax({
        type: "POST",
        url: "getDbInfo",
        contentType: "application/json;charset=utf-8",
        success: function (res) {
            var data = res.data;
            var url = $("#url").val(data.url);
            var username = $("#username").val(data.username);
            var password = $("#password").val(data.password);
        }
    });
});