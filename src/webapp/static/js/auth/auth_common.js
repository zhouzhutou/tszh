function sendVerifyCodeTimer(obj,time) {
    obj.attr("disabled",true);
    obj.text(time<=0 ? "获取邮箱验证码":"等待"+time+"秒后可重新获取");
    var handler=er=setInterval(function () {
        if(time<=0){
            clearInterval(handler);
            obj.text("获取邮箱验证码");
            obj.attr("disabled",false);
            return false;
        }else{
            obj.text("等待"+(--time)+"秒后可重修获取")
        }
    },1000);
}