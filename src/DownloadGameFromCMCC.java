import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


public class DownloadGameFromCMCC implements Runnable{

	private long downloadStartTime;
	
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
		while(true){
			IPChanger.connect("办公室", "200000939537", "f3v8e8w4");
			getAllLocalIP();
			downloadStartTime = System.currentTimeMillis();
			try {
				oInstance.resetList();
				// 增加下载列表（此处用户可以写入自己代码来增加下载列表）
				oInstance.addItem("http://g.10086.cn/e/DownSys/GetDown/game.php?classid=5830&id=23520&downfrom=www",
						"./艾格.apk");
				oInstance.addItem("http://g.10086.cn/e/DownSys/GetDown/game.php?classid=5830&id=22184&downfrom=www",
						"./超时空.apk");
				// 开始下载
				oInstance.downLoadByList();
			} catch (Exception err) {
				System.out.println(err.getMessage());
			}
			times++;
			long timeTakes = System.currentTimeMillis()-downloadStartTime;
			System.out.println("第"+times+"次下载完成，耗时"+timeTakes/1000+"秒");
			IPChanger.disconnect();
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
