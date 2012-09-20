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

	public void addItem(String url, String filename) {
		vDownLoad.add(url);
		vFileList.add(filename);
	}

	/**
	 * �����б�������Դ
	 */
	public void downLoadByList() {
		String url = null;
		String filename = null;

		// ���б�˳�򱣴���Դ
		for (int i = 0; i < vDownLoad.size(); i++) {
			url = (String) vDownLoad.get(i);
			filename = (String) vFileList.get(i);

			try {
				saveToFile(url, filename);
			} catch (IOException err) {
				if (DEBUG) {
					System.out.println("��Դ[" + url + "]����ʧ��!!!");
				}
			}
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
	public void saveToFile(String destUrl, String fileName) throws IOException {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;

		// ��������
		url = new URL(destUrl);
		httpUrl = (HttpURLConnection) url.openConnection();
		// ����ָ������Դ
		httpUrl.connect();
		// ��ȡ����������
		bis = new BufferedInputStream(httpUrl.getInputStream());
		// �����ļ�
		fos = new FileOutputStream(fileName);

		if (DEBUG)
//			System.out.println("���ڻ�ȡ����[" + destUrl + "]������...\n���䱣��Ϊ�ļ�["
//					+ fileName + "]");
			System.out.println("[" + fileName + "]");

		// �����ļ�
		while ((size = bis.read(buf)) != -1)
			fos.write(buf, 0, size);

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