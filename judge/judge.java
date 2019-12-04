package judge;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class judge {
    private static String cmd1 = "cp MainTest.java ../tmp";
    private static String cmd2 = "javac -cp .:../hamcrest-all-1.3.jar:" +
            "../junit-4.13-beta-2.jar Main.java MainTest.java";

    private static String cmd3 = "java -cp .:../junit-4.13-beta-2.jar:" +
            "../hamcrest-all-1.3.jar org.junit.runner.JUnitCore MainTest";
    
    private static Jedis jedis;

    public static void main(String[] args) {
        jedis = new Jedis("redis-master");
        //设置<list>字符串
        // jedis.lpush("code","public class Main{public static void main(String[] args) {System.out.println(\"hezskjhfskuhfjllo world\");}}");
        //获取存储数据并输出
        jedis.setnx("lock","0");
        while(true) {
            if(jedis.llen("code")!=0){
                if(jedis.get("lock").equals("0")){
                    try{
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                jedis.set("lock","0");
                String code = jedis.rpop("code");
                String commit = jedis.rpop("commit");
                jedis.set("lock","1");
                System.out.printf("get code:\n%s\n", code);
                Writecode(code);
                String res = Judge();
                jedis.lpush("res_output",res);
                jedis.lpush("res_commit",commit);
            }else{
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
            }
        }
        
    }

    public static void Writecode(String res){
        try {
            Runtime.getRuntime().exec("mkdir " + "./tmp").waitFor();
            BufferedWriter bw = new BufferedWriter(new FileWriter
                    ("./tmp/Main.java"));

            bw.write(res);
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String Judge() {
        try {
            runBash("rm Main.class MainTest.class","./tmp");
            runBash(cmd1,"./judge");
            runBash(cmd2,"./tmp");
            /*---*/
            String result = runBash(cmd3, "./tmp");
            /*---*/ 
            System.out.println("result is");
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String runBash(String cmd, String dir) throws Exception {
        File tempScript = createTempScript(cmd); // 创建临时⽂件
        // 新建PB对象
        ProcessBuilder pb = new ProcessBuilder("/bin/bash",
                tempScript.toString());
        pb.directory(new File(dir)); // 定义PB运⾏⽬录
        Process process = pb.start();
        String result = null;
        if (process.waitFor(5, TimeUnit.SECONDS)) { // 进程执⾏

            result = cmdinfo(process.getInputStream());

        } else { // 程序超时
            process.destroyForcibly(); // kill掉该进程的⼦进程
            killUnixProcess(process); // kill盖进程
        }
        return result;
    }

    public static String cmdinfo(InputStream inputStream){
        StringBuilder info = new StringBuilder();
        BufferedReader cmdr = null;
        try {
            cmdr = new BufferedReader(new InputStreamReader(inputStream));
            String cmdrl;
            while ((cmdrl = cmdr.readLine())!= null){
                info.append(cmdrl);
                info.append("\n");
            }
            return info.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static int getUnixPID(Process process) throws Exception {
        System.out.println(process.getClass().getName());
        if (process.getClass().getName().equals("java.lang.UNIXProcess")) {
            Class cl = process.getClass();
            Field field = cl.getDeclaredField("pid");
            field.setAccessible(true);
            Object pidObject = field.get(process);
            return (Integer) pidObject;
        } else {
            throw new IllegalArgumentException("Needs to be a UNIXProcess");
        }
    }

    public static int killUnixProcess(Process process) throws Exception {
        int pid = getUnixPID(process);
        return Runtime.getRuntime().exec("pkill -TERM -P " + pid).waitFor();
    }

    public static File createTempScript(String cmd) throws IOException {
        File tempScript = File.createTempFile("tempScript.sh", null);
        Writer streamWriter = new OutputStreamWriter(new
                FileOutputStream(tempScript));
        PrintWriter printWriter = new PrintWriter(streamWriter);
        printWriter.println("#!/bin/bash");
        printWriter.println(cmd);
        printWriter.close();
        return tempScript;
    }
}
