public class IPChanger {
 /**
  * 连接宽带
  * @param cname: 连接名，比如“宽带连接”
  * @param uname: 宽带连接的用户名
  * @param pwd:宽带连接的密码
  */
 static void connect(String cname,String uname,String pwd){
  try{
   Process p = Runtime.getRuntime().exec("rasdial.exe "+cname+" "+uname+" "+pwd);
   p.waitFor();
   System.out.println("连接");
  }catch(Exception e){e.printStackTrace();}
 }
 /**断开宽带*/
 static void disconnect(){
  try{
   Process p = Runtime.getRuntime().exec("rasdial.exe /DISCONNECT");
   p.waitFor();
   System.out.println("断开");
  }catch(Exception e){e.printStackTrace();}
 }
} 