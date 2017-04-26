function doLogin() {
    var username = $("#username").val(); //document.getElementsByName("username").value;
    var userpwd = $("#userpwd").val(); //document.getElementsByName("userpwd").value;

    $.ajax({
        type: "post",
        dataType: "json",
        url: 'login/doLogin.do',
        data: {"username": username, "userpwd": userpwd},
        success: function (data) {
            var obj = JSON.stringify(data);
             if(data.result=="1"){
                 window.location.href="http://dwusiq.tunnel.qydev.com/weixin/secondPage.jsp";
             }else {
                 alert("fail")
             }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("errot");
        }
    });
}