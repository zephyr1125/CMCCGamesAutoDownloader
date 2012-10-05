import java.io.*;
import java.net.*;
import java.util.*;

/**
 * <p>Title: ���˿�����API</p> <p>Description: ��ָ����HTTP������Դ�ڱ������ļ���ʽ���</p>
 * <p>Copyright: Copyright (c) 2004</p> <p>Company: NewSky</p>
 * 
 * @author MagicLiao
 * @version 1.0
 */
public class HttpGet {

	public final static boolean DEBUG = true;// ������
	private static int BUFFER_SIZE = 8096;// ��������С
	private Vector<String> vDownLoad = new Vector<String>();// URL�б�
	private Vector<String> vFileList = new Vector<String>();// ���غ�ı����ļ����б�
	private Vector<Long> vFileDownloadSize = new Vector<Long>();// Ҫ���ص��ļ��Ĳ��ִ�С��0��ʾȫ��
	
	/**
	 * ���췽��
	 */
	public HttpGet() {
	}

	/**
	 * ��������б�
	 */
	public void resetList() {
		vDownLoad.clear();
		vFileList.clear();
	}

	/**
	 * ���������б���
	 * 
	 * @param url
	 *            String
	 * @param filename
	 *            String
	 */

	public void addItem(String url, String filename, long fileDownloadSize) {
		vDownLoad.add(url);
		vFileList.add(filename);
		vFileDownloadSize.add(fileDownloadSize);
	}

	/**
	 * �����б�������Դ
	 */
	public void downLoadByList() {
		String url = null;
		String filename = null;
		long fileDownloadSize = 0;

		// ���б�˳�򱣴���Դ
		for (int i = 0; i < vDownLoad.size(); i++) {
			IPChanger.connect("�칫��", "200000939537", "f3v8e8w4");
			DownloadGameFromCMCC.getAllLocalIP();
			url = vDownLoad.get(i);
			filename = vFileList.get(i);
			fileDownloadSize = vFileDownloadSize.get(i);
			try {
				saveToFile(url, filename, fileDownloadSize);
			} catch (IOException err) {
				if (DEBUG) {
					System.out.println("��Դ[" + url + "]����ʧ��!!!");
				}
			}
			IPChanger.disconnect();
		}

		if (DEBUG) {
			System.out.println("�������!!!");
		}
	}

	/**
	 * ��HTTP��Դ���Ϊ�ļ�
	 * 
	 * @param destUrl
	 *            String
	 * @param fileName
	 *            String
	 * @throws Exception
	 */
	public void saveToFile(String destUrl, String fileName, long fileDownloadSize) throws IOException {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		int sizeAll = 0;

		// ��������
		url = new URL(destUrl);
		httpUrl = (HttpURLConnection) url.openConnection();
		httpUrl.setConnectTimeout(30*1000);
		httpUrl.setReadTimeout(30*1000);
		// ����ָ������Դ
		httpUrl.connect();
		// ��ȡ����������
		bis = new BufferedInputStream(httpUrl.getInputStream());
		// �����ļ�
		fos = new FileOutputStream(fileName);

		if (DEBUG)
			System.out.println("���ڻ�ȡ����[" + destUrl + "]������...\n���䱣��Ϊ�ļ�["
					+ fileName + "]");
//			System.out.println("[" + fileName + "]");

		// �����ļ�
		do{
			size = bis.read(buf);
			fos.write(buf, 0, size);
			sizeAll+=size;
		}while((sizeAll<fileDownloadSize || fileDownloadSize==0) && size != -1);
		
		System.out.println("sizeAll="+sizeAll);

		fos.close();
		bis.close();
		httpUrl.disconnect();
	}

	/**
	 * ������(���ڲ���)
	 * 
	 * @param argv
	 *            String[]
	 */
//	public static void main(String argv[]) {
//		HttpGet oInstance = new HttpGet();
//		try {
//			// ���������б��˴��û�����д���Լ����������������б�
//			oInstance.addItem("http://g.10086.cn/e/DownSys/GetDown/game.php?classid=5830&id=23520&downfrom=www",
//					"./����.apk");
//			oInstance.addItem("http://g.10086.cn/e/DownSys/GetDown/game.php?classid=5830&id=22184&downfrom=www",
//					"./��ʱ��.apk");
////			oInstance.addItem("http://www.ebook.com/java/������003.zip",
////					"./������3.zip");
////			oInstance.addItem("http://www.ebook.com/java/������004.zip",
////					"./������4.zip");
////			oInstance.addItem("http://www.ebook.com/java/������005.zip",
////					"./������5.zip");
////			oInstance.addItem("http://www.ebook.com/java/������006.zip",
////					"./������6.zip");
////			oInstance.addItem("http://www.ebook.com/java/������007.zip",
////					"./������7.zip");
//			// ��ʼ����
//			oInstance.downLoadByList();
//		} catch (Exception err) {
//			System.out.println(err.getMessage());
//		}
//	}
}