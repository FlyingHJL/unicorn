package com.anaheim.unicorn.tool;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateLog {
    static String path = "C:\\Users\\15437\\Desktop\\新建文件夹";
    static String logPath = path+"log\\";
    public static void main(String[] args) {
        File dir = new File(logPath);
        if (dir.exists()){
            for (File log:dir.listFiles()) {
                log.delete();
            }
        }else {
            dir.mkdir();
        }
        //遍历txt文件
        File fileDir = new File(path);
        for (File sourceTXT:fileDir.listFiles()) {
            if (sourceTXT.isDirectory()){
                continue;
            }
            //文件名
            String txtName = sourceTXT.getName();
            //文件大小
            long txtSpace = sourceTXT.length();
            //最后修改时间
            Date date = new Date(sourceTXT.lastModified());
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = sdt.format(date);
            //文件是否正常生成
            String isCorrect = "Y";
            //文件记录数
            int lineNums=0;
            try {
                LineNumberReader lnReader = new LineNumberReader(new FileReader(sourceTXT));
                lnReader.skip(Long.MAX_VALUE);
                lineNums = lnReader.getLineNumber()+1;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            createLog(txtName,txtSpace,createTime,isCorrect,lineNums);
        }
    }

    public static void createLog(String txtName,long txtSpace,String createTime,String isCorrect,int lineNums){
        //新建log文件
        File log = new File(logPath+txtName.substring(0,txtName.indexOf("."))+".log");
        BufferedWriter bwriter = null;
        try {
            //写入信息
            bwriter = new BufferedWriter(new FileWriter(log,true));
            bwriter.write(txtName+System.getProperty("line.separator"));
            bwriter.write(txtSpace+System.getProperty("line.separator"));
            bwriter.write(createTime+System.getProperty("line.separator"));
            bwriter.write(isCorrect+System.getProperty("line.separator"));
            bwriter.write(lineNums+System.getProperty("line.separator"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bwriter.flush();
                bwriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("log文件已生成！");
        System.out.println(System.getProperty("file.encoding"));
    }
}
