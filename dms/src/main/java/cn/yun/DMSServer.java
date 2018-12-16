package cn.yun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * DMS服务端，用来接收每个客户端发送过来的
 * 配对日志并保存在本地文件中
 * @author Administrator
 *
 */
public class DMSServer {
	//属性定义
	//用来接收客户端连接的服务端的ServerSocket
	private ServerSocket server;
	//用来管理处理客户端请求的线程的线程池
	private ExecutorService threadPool;
	//保存所有客户端发送过来配对日志的文件
	private File serverLogFile;
	//消息队列
	private BlockingQueue<String> messageQueue = new LinkedBlockingQueue<String>();
	
	public DMSServer() throws Exception{
		try {
			System.out.println("服务端正在初始化...");
			//1 解析配置文件server-config.xml
			Map<String,String> config = loadConfig();
			//2 根据配置文件内容初始化属性
			init(config);
		} catch (Exception e) {
			System.out.println("初始化服务端失败!");
			throw e;
		}
	}
	
	/**
	 * 构造方法初始化第一步，解析配置文件
	 */
	private Map<String,String> loadConfig() throws Exception{
		try {
			SAXReader reader = new SAXReader();
			String path = Thread.currentThread().getContextClassLoader().getResource("server-config.xml").getPath();
			Document doc= reader.read(new File(path));
			Element root = doc.getRootElement();
			Map<String,String> config= new HashMap<String,String>();
			List<Element> list = root.elements();
			for(Element e : list){
				String key = e.getName();
				String value = e.getTextTrim();
				config.put(key, value);
			}
			return config;
		} catch (Exception e) {
			System.out.println("解析配置文件异常!");
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 构造方法初始化第二步,根据配置项初始化属性
	 * @param config
	 * @throws Exception 
	 */
	private void init(Map<String,String> config) throws Exception{
		this.server = new ServerSocket(Integer.parseInt(config.get("serverport")));
		this.serverLogFile = new File(config.get("logrecfile")	);
		this.threadPool = Executors.newFixedThreadPool(Integer.parseInt(config.get("threadsum")));
	}	
	/**
	 * 服务端开始工作的方法
	 * @throws Exception
	 */
	public void start() throws Exception{
		try {
			System.out.println("服务端开始工作...");
			//启动用来保存日志的线程
			SaveLogHandler saveHandler= new SaveLogHandler();
			new Thread(saveHandler).start();
			while(true){
				Socket socket = server.accept();//监视服务器端口
				ClientHandler clientHandler= new ClientHandler(socket);
				threadPool.execute(clientHandler);//将任务交给线程池进行管理
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void main(String[] args) {
		try {
			DMSServer server = new DMSServer();
			server.start();
		} catch (Exception e) {
			System.out.println("启动服务端失败!");
		}
	}
	/**
	 * 该线程负责从消息队列中取出每一条配对日志，并存入到serverLogFile文件
	 */
	private class SaveLogHandler implements Runnable{
		public void run(){
			try {
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(serverLogFile, true)));
				while(true){
					if(messageQueue.isEmpty()){
						pw.flush();
						Thread.sleep(500);
					}else {
						pw.println(messageQueue.poll());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}
	
	/**
	 * 处理一个指定客户端请求：获取日志、发送响应字符串
	 */
	private class ClientHandler implements Runnable{
		private Socket socket;
		public ClientHandler(Socket socket){
			this.socket = socket;
		}
		public void run(){
			try {
				 //通过Socket创建输出流，用来给客户端发送响应
				OutputStream out = socket.getOutputStream();
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(out,"UTF-8"),true);
				//通过Socket创建输入流，读取客户端发送过来的日志
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				String message = null;
				while(!"OVER".equals(message = br.readLine())){
					messageQueue.offer(message);//将日志写入队列
				}
				//成功读取所有日志后回复客户端"OK" 
				pw.println("OK");
			} catch (Exception e) {
				//若在任何一个环节出现异常，回复客户端"ERROR"
				throw new RuntimeException("ERROR");
			}finally{
				try{
					if(socket == null){
						socket.close();
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}		
		}
	}
}





