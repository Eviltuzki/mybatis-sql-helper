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
            html += '<tr>';
            html += '<td>' + res[i].columnName + '</td>';
            html += ' <td><input type="checkbox" checked name="selectedField[' + i + ']" value=""></td>';
            html += '<td>';
            html += '<label><input name="where[' + i + ']" value="0" type="radio">N</label>';
            html += '<label><input name="where[' + i + ']" value="1" checked type="radio">S</label>';
            html += '<label><input name="where[' + i + ']" value="2" type="radio">M</label>';
            html += '</td>';
            html += '<td>' + res[i].dataType + '</td>';
            html += '</tr>';
        }
        $("#column").html(html);
    })
});
var checked = false;
$("#selected-all").click(function () {
    $("input[type=checkbox]").prop("checked", !checked);
    checked = !checked;
});
$("#generateCode").click(function () {
    var list = getColumnList();
    var req = {
        list: list,
        tableName: $("#tableName").val(),
        operateType: $("input[name='operateType']:checked").val()
    };
    var data = JSON.stringify(req);
    $.ajax({
        type: "POST",
        url: "getSQL",
        contentType: "application/json;charset=utf-8",
        data: data,
        success: function (res) {
            $("#code").text(res.data);
        }

    });
});

function getColumnList() {
    var list = [];
    var trList = $("#column").find("tr");
    for (var i = 0; i < trList.length; i++) {
        var tdList = $(trList[i]).find("td");
        var columnName = $(tdList[0]).text();
        var stringType = $(tdList[4]).text();
        var selected = $($(tdList[1]).find("input")).is(":checked");
        var where = $($(tdList[2]).find("input:radio:checked")).val();


        var item = {
            columnName: columnName,
            selected: selected,
            where:where,
            stringType:stringType
        };
        console.log(item)
        list.push(item);
    }
    return list;
}