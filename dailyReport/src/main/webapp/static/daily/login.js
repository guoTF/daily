layui.config({
		base:host+'/static/daily/common/lib/'
	}).extend({
	    common:'common'
	});
layui.use(['layer','common','form'],function(){
	var layer =layui.layer,
		common =layui.common,
		form =layui.form;

	$(function(){
		$("#validateImg").getImgCode();
		//登录
		$("#submitForm").on('click',function(){
			var userName =$("#userName").val();
			var password =$("#password").val();
			var identifyingCode =$("#identifyingCode").val();
			if(userName ==''){
				common.lxrErrormsg('用户名不能为空');
				return false;
			}
			if(password ==''){
				common.lxrErrormsg('登录密码不能为空');
				return false;
			}
			if(identifyingCode ==''){
				common.lxrErrormsg('验证码不能为空');
				return false;
			}
			if(userName.length>25){
				common.lxrErrormsg('用户名长度不能超过25位');
				return false;	
				}
			if(password.length<6){
				common.lxrErrormsg('登录密码长度不少于6位');
				return false;
			}
			var serializeResult =$("#serializeForm").serialize();
			
			common.sendRequest('/login/login',serializeResult,function(data){
				if(common.reqError(data)){return false;}
			})
			return false;
		});
		//发送手机验证码
		$("#sendMobile").on('click',function(){
			var phone =$('#phoneNum').val();
			debugger
			 var that = this;
			  $.post(host+"/login/sendMobileCode", {
                phone: $('#phoneNum').val()
            }, function (data, textStatus, jqXHR) {
              if(!data.success){layer.msg(data.errorMessage);return false;}
                // 发送成功
                $(that).off('click');
                var time = 60; // 发送间隔
                $(that).css({
                    'background-color': '#ccc',
                    'cursor': 'not-allowed'
                }).text('已发送 '+ time +' s');
                let jishi = setInterval(function () {
                    $(that).text('已发送 '+ --time +' s');
                    if (time == 0) {
                        clearInterval(jishi);
                        $(that).on('click', yancodeInterval).text('重发验证码').css({
                            'background-color': '',
                            'cursor': 'pointer'
                        });
                    }
                }, 1000);
                layer.msg("短信验证码发送成功！", { icon: 1, time: 1500 });
	          });
			  return false;
		});
		//注册
		$("#submitResiger").on('click',function(){
			var flag =notNull();
			if(!flag){return false};
			if(!chkMobile($("#phoneNum").val())){
				lxrErrormsg("手机格式不正确");
				return false;
			}
			if(!checkEmail($("#mail").val())){
				lxrErrormsg("邮箱格式不正确");
				return false;
			}
			var serializeResult =$("#serializeResiger").serialize();
			var obj =conveterParamsToJson(serializeResult);
			common.sendRequest('/login/register',obj,function(data){
				if(common.reqError(data)){return false;}
			})
			return false;
		});
		
      
		
	})

//序列化的参数转对象
function conveterParamsToJson(paramsAndValues) {
	var jsonObj = {};
 
	var param = paramsAndValues.split("&");
	for ( var i = 0; param != null && i < param.length; i++) {
		var para = param[i].split("=");
		jsonObj[para[0]] = para[1];
	}
 
	return jsonObj;
}
//验证非空‘
function notNull(){
	var flag =true;
	$(".required").each(function(item){
		if($.trim($(this).val())===''){
			layer.msg($(this).attr('placeholder')+"为空",{icon:2});
			flag =false;
			return false;
		}
		if($.trim($(this).val()).length>25){
			layer.msg("字符串长度不能过长",{icon:2});
			flag =false;
			return false;
		}
	});
	
	return flag;
}
 //手机验证
    function chkMobile(mobileNum){
        var mobile =/^1[3|5|8]\d{9}$/ ,phone = /^0\d{2,3}-?\d{7,8}$/;
        if(mobile.test(mobileNum)||phone.test(mobileNum)){
            return true;
        }else{
            return false;
            
        }
    }
    //验证邮箱
    function checkEmail(email){
    	var pattern =/^\w+@\w+(\.\w+)+$/;
    	if(!pattern.test(email)){
    		return false;
    	}else{
    		return true;
    	}
    }
$.fn.extend({
	//点击切换验证码
	getImgCode:function(){
		$(this).on('click',function(){
			$(this).attr("src",host+"/common/getImgCode?timestamp="+ (new Date()).valueOf())
		});
		 
	}
});

})