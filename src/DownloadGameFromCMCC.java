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
	
	private int TIMES = 512;
	
	/**目前只要广东省的号段*/
	private static final String PROVINCE_NAME = "guangdong";
	
	private ProvinceData provinceGuangdong,provinceShandong;
	
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
		
		provinceGuangdong = new ProvinceData(PROVINCE_NAME);
		
		provinceShandong = new ProvinceData("shandong");
		
		// 增加下载列表（此处用户可以写入自己代码来增加下载列表）
		
		while(times<TIMES){
			oInstance.resetList();
			//使用同一个手机号下载文件头
//			oInstance.addItem("http://download.cmgame.com:8513/entry?C=0300000001&ContentID=653510053138&F=963375_963376&T=1&O=136"+rdmPhoneNo+"&D=0&Y=2&H=10011000&M=0&P=1&G=0&U=0&E=6118&CFM=1&GCT=0&S=cf90e063ba156463e7bc8c12686e57a0",
//					"./深海head.apk", false, 8*1024, false);
//			//使用同一个手机号下载文件尾
//			oInstance.addItem("http://download.cmgame.com:8513/entry?C=0300000001&ContentID=653510053138&F=963375_963376&T=1&O=136"+rdmPhoneNo+"&D=0&Y=2&H=10011000&M=0&P=1&G=0&U=0&E=6118&CFM=1&GCT=0&S=cf90e063ba156463e7bc8c12686e57a0",
//					"./深海tail.apk", false, 8*1024, true);
			
//			oInstance.addItem("http://download.cmgame.com:8513/entry?C=0300000001&ContentID=653510053354&F=963379_992854&T=1&O="
//					+provinceGuangdong.randomPhoneNo()
//					+"&D=0&Y=2&H=10011000&M=0&P=1&G=0&U=0&E=6118&CFM=1&GCT=0&S=2d0549898a8fae5e335a49bce4b464f1",
//					"./艾格.apk", false, 0, false);
			
			//战火
			oInstance.addItem("http://download.cmgame.com:8513/entry?C=0300000001&ContentID=651310055114&F=1016354_1016355&T=1&O="
					+provinceShandong.randomPhoneNo()
					+"&D=0&Y=2&H=10011000&M=0&P=1&G=0&U=0&E=6118&CFM=1&GCT=0&S=57a74027464e843e33e1f5100835b1ff");
			
//			oInstance.addItem("http://download.cmgame.com:8513/entry?C=0300000001&ContentID=653510053138&F=963375_963376&T=1&O=136"+rdmPhoneNo+"&D=0&Y=2&H=10011000&M=0&P=1&G=0&U=0&E=6118&CFM=1&GCT=0&S=cf90e063ba156463e7bc8c12686e57a0",
//					"./深海.apk", false, 0, false);
//			oInstance.addItem("http://download.cmgame.com:8513/entry?C=0300000001&ContentID=653510046743&F=867904_867905&T=1&O="+provinceGuangdong.randomPhoneNo()+"&D=0&Y=2&H=10011000&M=0&P=1&G=0&U=0&E=6118&CFM=1&GCT=0&S=12051985be6020cc568b17bbe9436443",
//					"./超时空.apk", false, 0, false);
			downloadStartTime = System.currentTimeMillis();
			try {
				// 开始下载
				oInstance.downLoadByList();
			} catch (Exception err) {
				err.printStackTrace();
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
