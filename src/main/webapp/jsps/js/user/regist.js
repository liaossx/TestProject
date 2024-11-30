
/*换一张*/
function  _hyz(){
    $("#imgVerifyCode").attr("src","/goods/user/createVerifyCode?xxx="+new Date());
}

/*页面初始化*/
/*onload*/
$(function(){
    /*注册按钮  点击事件 */
    $("#submitBtn").click(function(){
       /*1.重新校验5个框*/
        let  bool = verifyloginname() && verifyloginpass() && verifyreloginpass() && verifyemail() && verifyverifyCode() ;
        if(bool){
            let  loginname = $("#loginname").val();
            let  loginpass = $("#loginpass").val();
            let  email = $("#email").val();
            location.href="/goods/user/regist?loginname="+loginname+"&loginpass="+loginpass+"&email="+email;
        }else{
            alert("请检查表单");
        }
    });
    /*注册按钮 高亮*/
    /*hover 移入 移出 事件*/
    $("#submitBtn").hover(
        /*鼠标移入*/
        function(){
            $(this).attr("src","/goods/images/regist2.jpg");
        }
        ,
        /*鼠标移出*/
        function(){
            $(this).attr("src","/goods/images/regist1.jpg");
        }
    );


    /*1. 右侧所有的错误提示框都隐藏*/

    $(".errorClass").css({display:"none"});
    /*2. 左侧的文本框丢失焦点事件=>对应的错误提示框显示*/
    /*blur 丢失焦点事件*/
    $(".inputClass").blur(function(){
        /*谁丢失焦点，谁就是当前的$(this)*/
        let  id = $(this).attr("id");
        let   labelId =  id +"Error";

        $("#"+labelId).css({display: "inline"});
        /*某个文本框丢失焦点的时候，触发调用相应的校验函数*/
        /*把一个字符串，转换成一句代码来执行*/
        // eval("alert('能把这个字符串转换代码直接执行')");
        eval("verify"+id+"()");


    })
    /*3. 左侧的文本框获取焦点事件=>对应的错误提示框 隐藏*/
    /*focus:获取焦点事件*/
    $(".inputClass").focus(function(){
        let  labelId = $(this).attr("id")+"Error";
        $("#"+labelId).css({display:"none"});
    })

});
/*5个文本框的校验函数*/
/*某个文本框丢失焦点的时候，触发调用相应的校验函数*/
function verifyloginname(){
    /*先获取用户输入的文本框值*/
    let  val =  $("#loginname").val();
    /*1. 非空校验*/
    if(val==""){
        $("#loginnameError").removeClass("okClass").addClass("errorClass");
        $("#loginnameError").text("用户名不能为空！");
        return false ;
    }
    /*2. 正则校验*/
    let  reg =   /^[a-zA-Z][a-zA-Z0-9_-]{5,19}$/;
    if(!reg.test(val)){
        $("#loginnameError").removeClass("okClass").addClass("errorClass");
        $("#loginnameError").text("用户名格式不正确6~20 ！");
        return false ;
    }
    /*3. 是否已存在   异步请求*/
    /*设置同步 死等服务器返回回来的结果*/
    $.ajaxSettings.async = false; //同步，死等
    /*标志位*/
    let  flag = false ;

    /*1. 请求路径*/
    /*2.  请求参数*/
    /*3. data 服务器返回回来的结果 ，function：前端拿到了结果后做什么   */
    $.post("/goods/user/verifyloginname",{"loginname":val},function(res){
        /*res  可用  不可用*/
        if(res){
            $("#loginnameError").removeClass("errorClass").addClass("okClass");
            $("#loginnameError").text("通过");
            flag = true ;
        }else{
            $("#loginnameError").removeClass("okClass").addClass("errorClass");
            /*不可用*/
            $("#loginnameError").text("用户名已存在");
            //return 是function(res) 的return false ;
            flag = false ;
        }
    });
    return  flag ;
}
function verifyloginpass(){
    let  val =  $("#loginpass").val();
    /*1.非空校验*/
    if(val==""){
        $("#loginpassError").removeClass("okClass").addClass("errorClass");
        $("#loginpassError").text("密码不能为空！");
        return false ;
    }

    /*2. 正则校验*/
    let  reg =   /^[a-zA-Z0-9_-]{3,12}$/;
    if(!reg.test(val)){
        $("#loginpassError").removeClass("okClass").addClass("errorClass");
        $("#loginpassError").text("密码格式不正确3~12 ！");
        return false ;
    }
    /**/
    $("#loginpassError").removeClass("errorClass").addClass("okClass");
    $("#loginpassError").text("通过！");
    return true

}
function verifyreloginpass(){
    // $("#reloginpassError").removeClass("okClass").addClass("errorClass");
    /*1.两次密码是否一致*/
    let  loginpass =  $("#loginpass").val();
    let  reloginpass =  $("#reloginpass").val();
    if (loginpass!=reloginpass){
        $("#reloginpassError").removeClass("okClass").addClass("errorClass");
        $("#reloginpassError").text("两次密码不一致！");
        return false ;
    }else{
        $("#reloginpassError").removeClass("errorClass").addClass("okClass");
        $("#reloginpassError").text("通过！");
        return true;
    }
}
function verifyemail(){
    let  val =  $("#email").val();
    /*1.非空校验*/
    if(val==""){
        $("#emailError").removeClass("okClass").addClass("errorClass");
        $("#emailError").text("邮箱不能为空！");
        return false ;
    }
    /*2.正则校验*/
    let  reg =   /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if(!reg.test(val)){
        $("#emailError").removeClass("okClass").addClass("errorClass");
        $("#emailError").text("邮箱格式不正确！");
        return false ;
    }
    /*3.邮箱 数据库是否可用 ajax 异步请求*/
    $.ajaxSettings.async=false ;
    let  flag = false ;
    $.post("/goods/user/verifyemail",{"email":val},function(res){
        if(res){
            $("#emailError").removeClass("errorClass").addClass("okClass");
            $("#emailError").text("通过!");
            flag = true ;
        }else{
            $("#emailError").removeClass("okClass").addClass("errorClass");
            $("#emailError").text("Email已存在!");
            flag = false ;
        }
    });
    return  flag ;
}
function verifyverifyCode(){
    /**/
    let  val =  $("#verifyCode").val();
    /*1 非空校验*/
    if(val==""){
        $("#verifyCodeError").removeClass("okClass").addClass("errorClass");
        $("#verifyCodeError").text("验证码不能为空！");
        return false ;
    }
    /*2  长度校验  4*/
    if(val.length!=4){
        $("#verifyCodeError").removeClass("okClass").addClass("errorClass");
        $("#verifyCodeError").text("验证码长度不正确！");
        return false ;
    }
    /*3。是否正确*/
    $.ajaxSettings.async=false ;
    let  flag = false ;
    $.post("/goods/user/verifyVerifyCode",{verifyCode:val},function(res){
        if(res){
            $("#verifyCodeError").removeClass("errorClass").addClass("okClass");
            $("#verifyCodeError").text("通过！");
            flag = true ;
        }else{
            $("#verifyCodeError").removeClass("okClass").addClass("errorClass");
            $("#verifyCodeError").text("验证码不正确！");
            flag = false ;
        }
    });

    return  flag ;

}