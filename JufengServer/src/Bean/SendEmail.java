package Bean;

import org.apache.commons.mail.HtmlEmail;

public class SendEmail {
	
	public static boolean sendEmail(String emailaddress,String code,int id){
		try {
			HtmlEmail email = new HtmlEmail();//不用更改
			email.setHostName("smtp.qq.com");//需要修改，126邮箱为smtp.126.com,163邮箱为163.smtp.com，QQ为smtp.qq.com
			email.setCharset("UTF-8");
			email.addTo(emailaddress);// 收件地址
			
			email.setSmtpPort(587);
			email.setFrom("admin@dreamfly.work", "dreamfly");//此处填邮箱地址和用户名,用户名可以任意填写
 
			email.setAuthentication("admin@dreamfly.work", "pmxtqnthawhlbhgi");//此处填写邮箱地址和客户端授权码
 
			email.setSubject("飓风App");//此处填写邮件名，邮件名可任意填写
			
			if(id==0){
				email.setMsg("<h2>尊敬的用户：</h2>"+
						 
				"<p style='text-indent:2em;display:block;margin-top:30px'>您正在修改账号信息，验证码是 <strong>"+code+"</strong>，工作人员不会索取，请勿泄漏。</p>" +
				"<p style='display:block;margin-top:30px'>此致</p>"
				+"<p>来自 飓风App</p>"
						);//此处填写邮件内容
			}
			if(id==1){
				email.setMsg("<h2>尊敬的用户：</h2>"+
						 
				"<p style='text-indent:2em;display:block;margin-top:30px'>您正在注册飓风账号，验证码是 <strong>"+code+"</strong>，请在飓风App中填写，完成验证。</p>" +
				"<p style='display:block;margin-top:30px'>此致</p>"
				+"<p>来自 飓风App</p>"
						);//此处填写邮件内容
			}
			if(id==2){
				email.setMsg("<h2>尊敬的用户：</h2>"+
				"<p style='text-indent:2em;display:block;margin-top:30px'>您好！</p>"+	 
				"<p style='text-indent:2em;display:block;margin-top:30px'>我们很高兴地通知您，您在飓风App注册的账号已开通成功。欢迎您加入飓风App会员行列。快来体验下我们的网站服务吧 。如果您对以上信息有疑问请您联系管理员为您解决，邮箱：admin@dreamfly.work。</p>" +
				"<p style='display:block;margin-top:30px'>此致</p>"
				+"<p>来自 飓风App</p>"
						);//此处填写邮件内容
			}
			email.send();
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
