package Bean;

import org.apache.commons.mail.HtmlEmail;

public class SendEmail {
	
	public static boolean sendEmail(String emailaddress,String code,int id){
		try {
			HtmlEmail email = new HtmlEmail();//���ø���
			email.setHostName("smtp.qq.com");//��Ҫ�޸ģ�126����Ϊsmtp.126.com,163����Ϊ163.smtp.com��QQΪsmtp.qq.com
			email.setCharset("UTF-8");
			email.addTo(emailaddress);// �ռ���ַ
			
			email.setSmtpPort(587);
			email.setFrom("admin@dreamfly.work", "dreamfly");//�˴��������ַ���û���,�û�������������д
 
			email.setAuthentication("admin@dreamfly.work", "pmxtqnthawhlbhgi");//�˴���д�����ַ�Ϳͻ�����Ȩ��
 
			email.setSubject("쫷�App");//�˴���д�ʼ������ʼ�����������д
			
			if(id==0){
				email.setMsg("<h2>�𾴵��û���</h2>"+
						 
				"<p style='text-indent:2em;display:block;margin-top:30px'>�������޸��˺���Ϣ����֤���� <strong>"+code+"</strong>��������Ա������ȡ������й©��</p>" +
				"<p style='display:block;margin-top:30px'>����</p>"
				+"<p>���� 쫷�App</p>"
						);//�˴���д�ʼ�����
			}
			if(id==1){
				email.setMsg("<h2>�𾴵��û���</h2>"+
						 
				"<p style='text-indent:2em;display:block;margin-top:30px'>������ע��쫷��˺ţ���֤���� <strong>"+code+"</strong>������쫷�App����д�������֤��</p>" +
				"<p style='display:block;margin-top:30px'>����</p>"
				+"<p>���� 쫷�App</p>"
						);//�˴���д�ʼ�����
			}
			if(id==2){
				email.setMsg("<h2>�𾴵��û���</h2>"+
				"<p style='text-indent:2em;display:block;margin-top:30px'>���ã�</p>"+	 
				"<p style='text-indent:2em;display:block;margin-top:30px'>���Ǻܸ��˵�֪ͨ��������쫷�Appע����˺��ѿ�ͨ�ɹ�����ӭ������쫷�App��Ա���С��������������ǵ���վ����� ���������������Ϣ������������ϵ����ԱΪ����������䣺admin@dreamfly.work��</p>" +
				"<p style='display:block;margin-top:30px'>����</p>"
				+"<p>���� 쫷�App</p>"
						);//�˴���д�ʼ�����
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
