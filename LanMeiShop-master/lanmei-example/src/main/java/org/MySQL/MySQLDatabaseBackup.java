package org.MySQL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Date;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;  
  
/** 
 * MySQL数据库备份 
 *  
 * @author GaoHuanjie 
 */  
public class MySQLDatabaseBackup {  
  
    /** 
     * Java代码实现MySQL数据库导出 
     *  
     * @author GaoHuanjie 
     * @param hostIP MySQL数据库所在服务器地址IP 
     * @param userName 进入数据库所需要的用户名 
     * @param password 进入数据库所需要的密码 
     * @param savePath 数据库导出文件保存路径 
     * @param fileName 数据库导出文件文件名 
     * @param databaseName 要导出的数据库名 
     * @return 返回true表示导出成功，否则返回false。 
     */  
    public static boolean exportDatabaseTool(String hostIP, String userName, String password, String savePath, String fileName, String databaseName) throws InterruptedException {  
        File saveFile = new File(savePath);  
        if (!saveFile.exists()) {// 如果目录不存在  
            saveFile.mkdirs();// 创建文件夹  
        }  
        if(!savePath.endsWith(File.separator)){  
            savePath = savePath + File.separator;  
        }  
          
        PrintWriter printWriter = null;  
        BufferedReader bufferedReader = null;  
        try {  
            printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(savePath + fileName), "utf8"));  
            Process process = Runtime.getRuntime().exec(" mysqldump -h" + hostIP + " -u" + userName + " -p" + password + " --set-charset=UTF8 " + databaseName);  
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");  
            bufferedReader = new BufferedReader(inputStreamReader);  
            String line;  
            while((line = bufferedReader.readLine())!= null){  
                printWriter.println(line);  
            }  
            printWriter.flush();  
            if(process.waitFor() == 0){//0 表示线程正常终止。  
                return true;  
            }  
        }catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (bufferedReader != null) {  
                    bufferedReader.close();  
                }  
                if (printWriter != null) {  
                    printWriter.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return false;  
    }  
      
    public static void main(String[] args){  
    	
    	Properties properties = new Properties();
    	Reader proreader = null;
    	Date  date = new Date();
    	/*读取配置文件的配置信息*/
    	try {
			InputStream in = Resources.getResourceAsStream("mysqlBackup.properties");
			proreader = new InputStreamReader(in);
			properties.load(proreader);
    	} catch (Exception e) {
			// TODO: handle exception
		}
    
    	
		String fileName = String.valueOf(date.getYear() + 1900) 
    					  + "-" + String.valueOf(date.getMonth()+1) 
    			          + "-" +  String.valueOf(date.getDate()) 
    			          + "-" +  String.valueOf(date.getHours()) 
    			          + ":" +  String.valueOf(date.getMinutes()) 
    			          + ".sql";
		//fileName  = date.toString() + ".sql";
        try {  
            if (exportDatabaseTool((String)properties.get("hostIP"),
            						(String)properties.get("userName"),
            						(String)properties.get("password"),
            						(String)properties.get("savePath"),
            		                fileName,
            		                (String)properties.get("databaseName"))){  
                System.out.println("数据库成功备份！！！");  
            } else {  
                System.out.println("数据库备份失败！！！");  
            }  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
}  