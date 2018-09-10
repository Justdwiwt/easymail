/* 点击图片换一张验证码 */
$(function () {
    $("#img").click(function () {
        /*
        在页面中，任意一个元素发生改变，浏览器都会检测到，并且重新显示页面元素。
        所以，只需要每次改变访问验证码后台的路径，浏览器即可改变元素的内容。
        但是为了每次都是访问ValiImageServlet，资源路径不能改变，
        但是可以拼接动态的参数，这样既不会影响访问的资源，又能改变元素的内容
         */
        $(this).attr("src", "<%= request.getContextPath() %>/servlet/ValiImageServlet?time=" + new Date().getTime());
    });
});

/* 注册表单的js校验 */
var formObj = {
    "checkForm": function () {
        //1.非空检验
        var flag1 = this.checkNull("username", "用户名不能为空！");
        var flag2 = this.checkNull("password", "密码不能为空！");
        var flag3 = this.checkNull("password2", "确认密码不能为空！");
        var flag4 = this.checkNull("nickname", "昵称不能为空！");
        var flag5 = this.checkNull("email", "邮箱不能为空！");
        var flag6 = this.checkNull("valistr", "验证码不能为空！");
        //2.两次密码是否一致
        var flag7 = this.checkPassword("password", "两次密码不一致！");
        //3.邮箱格式校验
        var flag8 = this.checkEmail("email", "邮箱格式不正确！");

        return flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8;
    },
    /* 非空校验 */
    "checkNull": function (name, msg) {
        var value = $("input[name='" + name + "']").val();
        //清空之前的提示消息
        this.setMsg(name, "");

        if (value.trim() == "") {
            //设置提示消息
            this.setMsg(name, msg);
            return false;
        }
        return true;
    },
    //设置错误提示消息
    "setMsg": function (name, msg) {
        $("input[name='" + name + "']").nextAll("span").html(msg).css("color", "red");
    },
    /* 检查两次密码是否一致 */
    "checkPassword": function (name, msg) {
        var pw1 = $("input[name='" + name + "']").val();
        var pw2 = $("input[name='" + name + "2']").val();
        if (pw1 != "" && pw2 != "" && pw1 != pw2) {
            //设置提示消息
            this.setMsg(name + "2", msg);
            return false;
        }
        return true;
    },
    /* 检查邮箱格式是否正确 */
    "checkEmail": function (name, msg) {
        var email = $("input[name='" + name + "']").val();
        var regExp = /^\w+@\w+(.\w+)+$/;
        if (email != "" && !regExp.test(email)) {
            this.setMsg(name, msg);
            return false;
        }
        return true;
    }
};

/* 给所有输入框添加失去输入焦点事件，当失去输入焦点时进行非空校验或者密码一致校验或者邮箱格式校验 */
//用户名
$(function () {
    $("input[name='username']").blur(function () {
        formObj.checkNull("username", "用户名不能为空！");
    });
//密码
    $("input[name='password']").blur(function () {
        formObj.checkNull("password", "密码不能为空！");
    });
//确认密码
    $("input[name='password2']").blur(function () {
        formObj.checkNull("password2", "确认密码不能为空！");
        formObj.checkPassword("password", "两次密码不一致！");
    });
//昵称
    $("input[name='nickname']").blur(function () {
        formObj.checkNull("nickname", "昵称不能为空！");
    });
//邮箱
    $("input[name='email']").blur(function () {
        formObj.checkNull("email", "邮箱不能为空！");
        formObj.checkEmail("email", "邮箱格式不正确！")
    });
//验证码
    $("input[name='valistr']").blur(function () {
        formObj.checkNull("valistr", "验证码不能为空！");
    });
});