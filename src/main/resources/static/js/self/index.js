var map = {};
$(document).ready(function () {
    $.get("getDb", function (resp) {
        if (resp.status === 200) {
            var res = resp.data;
            var html = "<option>请选择</option>";
            for (var i = 0; i < res.length; i++) {
                html += "<option>" + res[i] + "</option>"
            }
            $("#dbName").html(html)
        } else {
            alert(resp.msg)
        }
    });
    addClass()
});
$("#dbName").change(function () {
    var val = $("#dbName").val()
    $.get("getTable?dbName=" + val, function (resp) {
        var res = resp.data;
        var html = "<option>请选择</option>";
        for (var i = 0; i < res.length; i++) {
            html += "<option>" + res[i] + "</option>"
        }
        $("#tableName").html(html)
    })
});
$("#tableName").change(function () {
    var dbName = $("#dbName").val();
    var tableName = $("#tableName").val();
    $.get("getColumns?dbName=" + dbName + "&tableName=" + tableName, function (resp) {
        var res = resp.data;
        var html = "";
        for (var i = 0; i < res.length; i++) {
            map[i] = res[i];
            html += '<tr>';
            html += '<td><input type="checkbox" name="column" value="' + i + '"></td>';
            html += '<td>' + res[i].dbName + '</td>';
            html += '<td>' + res[i].tableName + '</td>';
            html += '<td>' + res[i].columnName + '</td>';
            html += '<td>' + res[i].dataType + '</td>';
            html += '<td>' + res[i].columnComment + '</td>';
            html += '</tr>';
        }
        $("#column").html(html);
    })
});
var checked = false;
$("#check-all").click(function () {
    $("input[name=column]").prop("checked", !checked);
    checked = !checked;
});
$("#generateCode").click(function () {
    var list = [];
    var selectItem = $("input[name=column]");
    for (var i = 0; i < selectItem.length; i++) {
        var idx = $(selectItem[i]).val();
        list.push(map[idx])
    }
    var data = JSON.stringify(list);
    $.ajax({
        type: "POST",
        url: "getJavaBean",
        contentType: "application/json;charset=utf-8",
        data: data,
        success: function (res) {
            $("#code").text(res.data);
        }

    });
});