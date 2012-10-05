import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;


public class DownloadGameFromCMCC implements Runnable{

	private long downloadStartTime;
	
	private int TIMES = 10000;
	
//	private static final long DOWNLOAD_INTERVAL_TIME = 300000;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		AGNetwork.instance().downloadGame();
		(new Thread(new DownloadGameFromCMCC())).start();
	}

	@Override
	public void run() {
		HttpGet oInstance = new HttpGet();
		int times = 0;
		while(times<TIMES){
			downloadStartTime = System.currentTimeMillis();
			try {
				oInstance.resetList();
				// 增加下载列表（此处用户可以写入自己代码来增加下载列表）
				Random rdm = new Random();			
				int rdmPhoneNo = 10000000+rdm.nextInt(89999999);
				oInstance.addItem("http://download.cmgame.com:8513/entry?C=0300000001&ContentID=653510053354&F=963379_992854&T=1&O=136"+rdmPhoneNo+"&D=0&Y=2&H=10011000&M=0&P=1&G=0&U=0&E=6118&CFM=1&GCT=0&S=2d0549898a8fae5e335a49bce4b464f1",
						"./艾格.apk", 0);
//				rdmPhoneNo = 10000000+rdm.nextInt(89999999);
//				oInstance.addItem("http://download.cmgame.com:8513/entry?C=0300000001&ContentID=653510053354&F=963379_992854&T=1&O=136"+rdmPhoneNo+"&D=0&Y=2&H=10011000&M=0&P=1&G=0&U=0&E=6118&CFM=1&GCT=0&S=2d0549898a8fae5e335a49bce4b464f1",
//						"./艾格64K.apk", 65536);
//				rdmPhoneNo = 10000000+rdm.nextInt(89999999);
//				oInstance.addItem("http://download.cmgame.com:8513/entry?C=0300000001&ContentID=653510053354&F=963379_992854&T=1&O=136"+rdmPhoneNo+"&D=0&Y=2&H=10011000&M=0&P=1&G=0&U=0&E=6118&CFM=1&GCT=0&S=2d0549898a8fae5e335a49bce4b464f1",
//						"./艾格128K.apk", 131072);
//				rdmPhoneNo = 10000000+rdm.nextInt(89999999);
//				oInstance.addItem("http://download.cmgame.com:8513/entry?C=0300000001&ContentID=653510053354&F=963379_992854&T=1&O=136"+rdmPhoneNo+"&D=0&Y=2&H=10011000&M=0&P=1&G=0&U=0&E=6118&CFM=1&GCT=0&S=2d0549898a8fae5e335a49bce4b464f1",
//						"./艾格1M.apk", 1048576);
//				rdmPhoneNo = 10000000+rdm.nextInt(89999999);
//				oInstance.addItem("http://download.cmgame.com:8513/entry?C=0300000001&ContentID=653510053354&F=963379_992854&T=1&O=136"+rdmPhoneNo+"&D=0&Y=2&H=10011000&M=0&P=1&G=0&U=0&E=6118&CFM=1&GCT=0&S=2d0549898a8fae5e335a49bce4b464f1",
//						"./艾格2.apk", false);
//				oInstance.addItem("http://g.10086.cn/e/DownSys/GetDown/game.php?classid=5830&id=22184&downfrom=www",
//						"./超时空.apk");
				// 开始下载
				oInstance.downLoadByList();
			} catch (Exception err) {
				System.out.println(err.getMessage());
			}
			times++;
			long timeTakes = System.currentTimeMillis()-downloadStartTime;
			System.out.println("第"+times+"次下载完成，耗时"+timeTakes/1000+"秒");
//			try {
//				Thread.sleep(DOWNLOAD_INTERVAL_TIME-timeTakes);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
	}
	
	private static Enumeration<NetworkInterface> allNetInterfaces = null;

	public static void getAllLocalIP() {

		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InetAddress ip = null;
		while (allNetInterfaces.hasMoreElements()) {
			NetworkInterface netInterface = (NetworkInterface) allNetInterfaces
					.nextElement();
			if(netInterface.getName().startsWith("ppp")){
//				System.out.println(netInterface.getName());
				Enumeration<InetAddress> addresses = netInterface
						.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address) {
						System.out.println("IP = " + ip.getHostAddress());
					}
				}
			}
		}
	}

}
