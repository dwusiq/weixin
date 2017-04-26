function queryMessage() {
    var condition = $("#condition").val();

    $.ajax({
        type: "post",
        dataType: "json",
        url: 'login/queryMessage.do',
        data: {"condition": condition},
        success: function (data) {
            var obj = JSON.stringify(data);
            alert(obj);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("errot");
        }
    });
}