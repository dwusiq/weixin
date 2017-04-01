function doLogin() {
    var username = $("#username").val(); //document.getElementsByName("username").value;
    var userpwd = $("#userpwd").val(); //document.getElementsByName("userpwd").value;

    $.ajax({
        type: "post",
        dataType: "json",
        url: 'login/doLogin.do',
        data: {"username": username, "userpwd": userpwd},
        success: function (data) {
            var json = JSON.stringify(data);
            alert(json);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("errot");
        }
    });
}