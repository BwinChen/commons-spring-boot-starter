package com.bwin.commons.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.*;
import java.io.ByteArrayOutputStream;

/**
 * <a href="https://blog.csdn.net/u011411069/article/details/78570125">commons-exec执行系统命令</a>
 */
@Slf4j
public class ExecUtil {

    /**
     * 执行不需要返回结果的命令
     */
    public static void execCmdWithoutResult() {
        try {
            //开启windows telnet: net start telnet
            //注意：第一个空格之后的所有参数都为参数
            CommandLine command = new CommandLine("net");
            command.addArgument("start");
            command.addArgument("telnet");
            DefaultExecutor executor = new DefaultExecutor();
            executor.setExitValue(1);
            //设置60秒超时，执行超过60秒后会直接终止
            ExecuteWatchdog watchdog = new ExecuteWatchdog(60 * 1000);
            executor.setWatchdog(watchdog);
            DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();
            executor.execute(command, handler);
            //命令执行返回前一直阻塞
            handler.waitFor();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 带返回结果的命令执行
     */
    public static String execCmdWithResult(String commandLine) {
        String normal = null;
        String error = null;
        try {
            DefaultExecutor executor = new DefaultExecutor();
            executor.setExitValues(null);
            //设置一分钟超时
            ExecuteWatchdog watchdog = new ExecuteWatchdog(60 * 1000);
            executor.setWatchdog(watchdog);
            //接收正常结果流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //接收异常结果流
            ByteArrayOutputStream err = new ByteArrayOutputStream();
            PumpStreamHandler streamHandler = new PumpStreamHandler(out, err);
            executor.setStreamHandler(streamHandler);
            CommandLine command = CommandLine.parse(commandLine);
            executor.execute(command);
            //不同操作系统注意编码，否则结果乱码
            normal = out.toString("GBK");
            error = err.toString("GBK");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return normal + error;
    }

    public static void main(String[] args) {
        log.info(execCmdWithResult("D:/Program Files/WinRAR/UnRAR x -y " + "D:/Test/rar/kibana.rar " + "D:/Test/rar/"));
//        execCmdWithoutResult();
    }

}
