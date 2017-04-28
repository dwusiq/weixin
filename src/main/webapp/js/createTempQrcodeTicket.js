function createTempQrcodeTicket() {
    $.ajax({
        type: "post",
        dataType: "json",
        url: 'weixinManage/createTempQrcodeTicket.json',
        data: {},
        success: function (data) {
            var obj = JSON.stringify(data);
            $("#content").html(obj);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            $("#content").html("error");
        }
    });
}