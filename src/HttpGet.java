import java.io.*;
import java.net.*;
import java.util.*;

/**
 * <p>Title: 个人开发的API</p> <p>Description: 将指定的HTTP网络资源在本地以文件形式存放</p>
 * <p>Copyright: Copyright (c) 2004</p> <p>Company: NewSky</p>
 * 
 * @author MagicLiao
 * @version 1.0
 */
public class HttpGet {

	public final static boolean DEBUG = true;// 调试用
	private static int BUFFER_SIZE = 8096;// 缓冲区大小
	private Vector<String> vDownLoad = new Vector<String>();// URL列表
	
	/**
	 * 构造方法
	 */
	public HttpGet() {
	}

	/**
	 * 清除下载列表
	 */
	public void resetList() {
		vDownLoad.clear();
	}

	/**
	 * 增加下载列表项
	 * 
	 * @param url
	 *            String
	 * @param filename
	 *            String
	 */

	public void addItem(String url) {
		vDownLoad.add(url);
	}

	/**
	 * 根据列表下载资源
	 */
	public void downLoadByList() {
		String url = null;

		// 按列表顺序保存资源
		for (int i = 0; i < vDownLoad.size(); i++) {
//			isIPChange = vIsIPChange.get(i);
//			if(isIPChange){
//				IPChanger.connect("办公室", "200000939537", "f3v8e8w4");
//				DownloadGameFromCMCC.getAllLocalIP();
//			}
			
			url = vDownLoad.get(i);
			
			try {
				saveToFile(url);
			} catch (IOException err) {
				if (DEBUG) {
					System.out.println("资源[" + url + "]下载失败!!!");
				}
			}
//			if(isIPChange)IPChanger.disconnect();
		}

		if (DEBUG) {
			System.out.println("下载完成!!!");
		}
	}

	/**
	 * 将HTTP资源另存为文件
	 * 
	 * @param destUrl
	 *            String
	 * @param fileName
	 *            String
	 * @throws Exception
	 */
	public void saveToFile(String destUrl) throws IOException {
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		int sizeAll = 0;

		// 建立链接
		url = new URL(destUrl);
		httpUrl = (HttpURLConnection) url.openConnection();
		httpUrl.setConnectTimeout(30*1000);
		httpUrl.setReadTimeout(30*1000);
		
		setHeader(httpUrl);
		
//		if(isOnlyDownloadTail){
//	        //设置下载起始位置
//	        String property = "bytes=" + (fileLength-fileDownloadSize) + "-";
//	        httpUrl.setRequestProperty("RANGE", property);
//		}
		
		// 连接指定的资源
		httpUrl.connect();
		// 获取网络输入流
		bis = new BufferedInputStream(httpUrl.getInputStream());

		if (DEBUG)
			System.out.println("正在获取链接[" + destUrl + "]的内容...");
//			System.out.println("[" + fileName + "]");

		// 保存文件
		do{
			size = bis.read(buf);
//			fos.write(buf, 0, size);
			sizeAll+=size;
		}while(size != -1);
		
		System.out.println("sizeAll="+sizeAll);

		bis.close();
		httpUrl.disconnect();
	}
	
    /**
     * <b>function:</b> 设置URLConnection的头部信息，伪装请求信息
     * @author hoojo
     * @createDate 2011-9-28 下午05:29:43
     * @param con
     */
    private static void setHeader(URLConnection conn) {
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; U; Android 2.2; zh-cn; MotoA953 Build/MILS2_U6_2.2.16) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
        conn.setRequestProperty("Accept-Language", "en-us,en;q=0.7,zh-cn;q=0.3");
        conn.setRequestProperty("Accept-Encoding", "utf-8");
        conn.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
        conn.setRequestProperty("Keep-Alive", "300");
        conn.setRequestProperty("connnection", "keep-alive");
        conn.setRequestProperty("If-Modified-Since", "Fri, 02 Jan 2009 17:00:05 GMT");
        conn.setRequestProperty("If-None-Match", "\"1261d8-4290-df64d224\"");
        conn.setRequestProperty("Cache-conntrol", "max-age=0");
        conn.setRequestProperty("Referer", "http://www.baidu.com");
    }

    /**
     * <b>function:</b> 获取下载文件的长度
     * @author hoojo
     * @createDate 2011-9-26 下午12:15:08
     * @return
     */
    private long getFileSize(String urlText) {
        int fileLength = -1;
        try {
            URL url = new URL(urlText);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            setHeader(conn);
 
            int stateCode = conn.getResponseCode();
            //判断http status是否为HTTP/1.1 206 Partial Content或者200 OK
            if (stateCode != HttpURLConnection.HTTP_OK && stateCode != HttpURLConnection.HTTP_PARTIAL) {
                System.out.println("Error Code: " + stateCode);
                return -2;
            } else if (stateCode >= 400) {
            	System.out.println("Error Code: " + stateCode);
                return -2;
            } else {
                //获取长度
                fileLength = conn.getContentLength();
                System.out.println("FileLength: " + fileLength);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileLength;
    }
}