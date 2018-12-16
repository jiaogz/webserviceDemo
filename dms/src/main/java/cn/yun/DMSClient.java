package cn.yun;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.yun.bo.LogData;
import cn.yun.bo.LogRec;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
 * 该客户端运行在给用户提供unix服务的服务器上。
 * 用来读取并收集该服务器上用户的上下线信息，并
 * 进行配对整理后发送给服务端汇总。
 * @author Administrator
 *
 */
public class DMSClient {
	//属性定义
	//第一步:解析日志所需属性
	//unix系统日志文件
	private File logFile;
	//保存解析后日志的文件
	private File textLogFile;
	//书签文件
	private File lastPositionFile;
	//每次解析日志的条目数
	private int batch;
	//第二步:配对日志所需要属性
	//保存配对日志的文件
	private File logRecFile;
	//保存未配对日志的文件
	private File loginLogFile;
	//第三步:发送日志所需要属性
	//服务端地址
	private String serverHost;
	//服务端端口
	private int serverPort;
	
	/**
	 * 构造方法，用来初始化客户端
	 * @throws Exception 
	 */
	public DMSClient() throws Exception{
		try {
			//1 解析配置文件config.xml
			Map<String,String> config	= loadConfig();
			//打桩
			System.out.println(config);
			//2 根据配置文件内容初始化属性
			init(config);
		} catch (Exception e) {
			System.out.println("初始化失败!");
			throw e;
		}
	}
	/**
	 * 构造方法初始化第二步,根据配置项初始化属性
	 * @param config
	 * @throws Exception 
	 */
	private void init(Map<String,String> config) throws Exception{
		try {
			logFile = new File(config.get("logfile"));
			textLogFile = new File(config.get("textlogfile")	);
			lastPositionFile = new File(config.get("lastpositionfile")	);
			batch = Integer.parseInt(config.get("batch"));
			logRecFile = new File(config.get("logrecfile")	);
			loginLogFile = new File(config.get("loginlogfile")	);
			serverHost = config.get("serverhost");
			serverPort = Integer.parseInt(config.get("serverport")	);				
		} catch (Exception e) {
			System.out.println("初始化属性失败!");
			e.printStackTrace();
			throw e;
		}
	}
	
	
	/**
	 * 构造方法初始化第一步，解析配置文件
	 * @return 返回的Map中保存的是配置文件中的
	 *         每一条内容，其中key:标签的名字，
	 *         value为标签中间的文本
	 * @throws Exception 
	 */
	private Map<String,String> loadConfig() throws Exception{
		try {
			SAXReader reader = new SAXReader();
			String path = Thread.currentThread().getContextClassLoader().getResource("config.xml").getPath();
			Document doc= reader.read(new File(path));
			Element root = doc.getRootElement();
			Map<String,String> config= new HashMap<String,String>();
			/*
			 * 获取<config>标签中的所有子标签
			 * 并将每一个子标签的名字作为key,中间的
			 * 文本作为value存入Map集合
			 */
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
	 * 客户端开始工作的方法
	 * 循环执行三步:
	 * 1:解析日志
	 * 2:配对日志
	 * 3:发送日志
	 */
	public void start(){
		parseLogs();
		matchLogs();
		sendLogs();
		while(true){
			//解析日志
			if(!parseLogs()){
				continue;
			}
			//配对日志
			if(!matchLogs()){
				continue;
			}
			//发送日志
			sendLogs();
		}
	}
	/**
	 * 第三步:发送日志
	 * @return true:发送成功
	 *         false:发送失败
	 */
	private boolean sendLogs(){
		Socket socket = null;
		try {
			if(!logRecFile.exists()){
				System.out.println(logRecFile+"不存在!");
				return false;
			}
			//:将所有配对日志读取出来并存入一个集合,等待发送
			List<String> matches = IOUtil.loadLogRec(logRecFile);
			//连接服务器
			socket = new Socket(serverHost,serverPort);
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"	),true);
			for(String log : matches){
				//顺序将所有配对日志按行发送给服务端
				pw.println(log);
			}
			pw.println("OVER");
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"	));
			//读取服务端发送回来的响应字符串,Ok代表接受完成删除日志文件。
			String response = br.readLine();
			if("OK".equals(response)){
				logRecFile.delete();
				return true;
			}else{
				System.out.println("发送日志失败!");
				return false;
			}
		} catch (Exception e) {
			System.out.println("发送日志失败!");
			e.printStackTrace();
		} finally{
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	/**
	 * 第二步:配对日志
	 * @return true:配对成功
	 *         false:配对失败
	 */
	private boolean matchLogs(){
		try {
			//配对日志存在不进行新的配对
			if(logRecFile.exists()){
				return true;
			}
			//记录指针的文件必须存在
			if(!textLogFile.exists()){
				System.out.println(textLogFile+"不存在!");
				return false;
			}
			//保存每一条日志
			List<LogData> list = IOUtil.loadLogData(textLogFile);
			//上次未配对成功的日志，也将其读取出来存入集合等待一起配对
			if(loginLogFile.exists()){
				list.addAll(IOUtil.loadLogData(loginLogFile));
			}
			//保存所有配对日志
			List<LogRec> matches = new ArrayList<LogRec>();
			//分别保存登入日志与登出日志
			Map<String,LogData> loginMap= new HashMap<String,LogData>();
			Map<String,LogData> logoutMap= new HashMap<String,LogData>();
			for(LogData logData : list){
				String key = logData.getUser()+","+ logData.getPid();
				if(logData.getType()==LogData.TYPE_LOGIN){//登入日志7
					loginMap.put(key, logData);
				}else	 if(logData.getType()==LogData.TYPE_LOGOUT){//退出日志8
					logoutMap.put(key, logData);
				}
			}
			//配对日志
			Set<Entry<String,LogData>> entrySet= logoutMap.entrySet();
			for(Entry<String,LogData> e : entrySet){
				LogData logout = e.getValue();
				LogData login = loginMap.remove(e.getKey());
				LogRec logRec = new LogRec(login,logout);
				matches.add(logRec);
			}
			//将配对日志写入到logRecFile中
			IOUtil.saveCollection(matches, logRecFile);
			//所有未配对日志写入到loginLogFile中
			IOUtil.saveCollection(loginMap.values(),loginLogFile);
			textLogFile.delete();
			return true;
		} catch (Exception e) {
			System.out.println("配对日志失败!");
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 第一步:解析日志
	 * @return true:解析成功
	 *         false:解析失败
	 */
	private boolean parseLogs(){
		RandomAccessFile raf = null;
		try {
			if(textLogFile.exists()){
				return true;
			}
			if(!logFile.exists()){
				System.out.println(logFile+"不存在!");
				return false;
			}
			//:将指针移动到上次最后读取的位置，准备开始新的解析工作
			long lastPosition = hasLogs();
			if(lastPosition<0){
				System.out.println("没有日志可以解析了!");
				return false;
			}
			raf = new RandomAccessFile(logFile,"r");
			raf.seek(lastPosition);
			//用于保存解析后的每一条日志(LogData实例)
			List<LogData> list= new ArrayList<LogData>();
			for(int i=0;i<batch;i++){
				//每次解析前都判断是否还有日志可以解析
				if(logFile.length()-lastPosition
						<LogData.LOG_LENGTH
				){
					break;
				}
				//解析user
				raf.seek(lastPosition+LogData.USER_OFFSET);
				String user = IOUtil.readString(raf, LogData.USER_LENGTH).trim();
				//解析PID
				raf.seek(lastPosition+LogData.PID_OFFSET);
				int pid = raf.readInt();
				//解析TYPE
				raf.seek(lastPosition+LogData.TYPE_OFFSET);
				short type = raf.readShort();
				//解析TIME
				raf.seek(lastPosition+LogData.TIME_OFFSET);
				int time = raf.readInt();
				//解析HOST
				raf.seek(lastPosition+LogData.HOST_OFFSET);
				String host = IOUtil.readString(raf, LogData.HOST_LENGTH).trim();
				LogData log = new LogData(user, pid, type, time, host);
				list.add(log);
				//当解析完一条日志后，更新lastPosition
				lastPosition = raf.getFilePointer();
			}	
			//将集合中的所有的日志以行为单位保存到 textLogFile中 
			IOUtil.saveCollection(list, textLogFile);
			//6 保存书签文件
			IOUtil.saveLong(lastPosition, lastPositionFile);
			return true;
		} catch (Exception e) {
			System.out.println("解析日志失败!");
			e.printStackTrace();
		} finally{
			if(raf != null){
				try {
					raf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	/**
	 * 第一步解析日志中的一个环节，
	 * 根据书签文件记录的位置判断是否还有
	 * 日志可以解析，若有，则将上次最后的位置
	 * 返回，若没有则返回-1。
	 * @return
	 */
	private long hasLogs(){
		try {
			//若lastPositionFile不存在，则说明 从来没有解析过，那么从头开始解析即可
			if(!lastPositionFile.exists()){
				return 0;
			}
			long lastPosition = IOUtil.readLong(lastPositionFile);
			if(logFile.length()-lastPosition>=LogData.LOG_LENGTH){
				return lastPosition;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	public static void main(String[] args) {
		try {
			DMSClient client = new DMSClient();
			client.start();
		} catch (Exception e) {
			System.out.println("客户端运行失败!");
		}
	}
}








