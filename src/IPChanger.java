public class IPChanger {
 /**
  * ���ӿ��
  * @param cname: �����������硰������ӡ�
  * @param uname: ������ӵ��û���
  * @param pwd:������ӵ�����
  */
 static void connect(String cname,String uname,String pwd){
  try{
   Process p = Runtime.getRuntime().exec("rasdial.exe "+cname+" "+uname+" "+pwd);
   p.waitFor();
   System.out.println("����");
  }catch(Exception e){e.printStackTrace();}
 }
 /**�Ͽ����*/
 static void disconnect(){
  try{
   Process p = Runtime.getRuntime().exec("rasdial.exe /DISCONNECT");
   p.waitFor();
   System.out.println("�Ͽ�");
  }catch(Exception e){e.printStackTrace();}
 }
} 