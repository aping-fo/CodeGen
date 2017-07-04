package com.road.oplog.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.road.oplog.mgr.OpLogMgr;

public class VmCodeGen
{
    private static Logger LOGGER = LoggerFactory.getLogger(OpLogMgr.class);

    private VelocityEngine vmEngine;
    private Template template;

    /**
     * VmCodeGen构造子
     * 
     * @param templatePath vm模板地址
     */
    public VmCodeGen(String templatePath)
    {
        Thread thread = Thread.currentThread();
        ClassLoader loader = thread.getContextClassLoader();
        thread.setContextClassLoader(VmCodeGen.class.getClassLoader());
        try
        {
            vmEngine = new VelocityEngine();
            vmEngine.addProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            vmEngine.addProperty("input.encoding", "UTF-8");
            vmEngine.addProperty("output.encoding", "UTF-8");
            vmEngine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new NullLogChute()); // 防止log导致空指针异常
            vmEngine.init();
            
            template = vmEngine.getTemplate(templatePath, "UTF-8");
        }
        catch(Exception e)
        {
            LOGGER.error("VMEngine init error.", e);
        }
        finally
        {
            thread.setContextClassLoader(loader);
        }
    }

    /**
     * 生成类代码
     * 
     * @param srcPath 生成类代码path
     * @param packageName 生成类package名字
     * @param clazz 生成类元属性
     * @param clazzPrefix 生成类前缀
     */
    public void genClazz(String srcPath, String packageName, Clazz clazz, String clazzPrefix)
    {
        try
        {
            File file = new File(srcPath);
            file.mkdirs();
            FileOutputStream fos = new FileOutputStream(file.getAbsolutePath() + File.separator + clazzPrefix + clazz.getName() + ".java");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));

            VelocityContext ctx = new VelocityContext();
            ctx.put("packageName", packageName);
            ctx.put("clazz", clazz);
            template.merge(ctx, writer);

            writer.flush();
            writer.close();
            fos.close();
        }
        catch (FileNotFoundException e)
        {
            LOGGER.error("file not found. {}", srcPath, e);
        }
        catch (IOException e)
        {
            LOGGER.error("io exception.", e);
        }
        catch (Exception e)
        {
            LOGGER.error("genClazz error.", e);
        }
    }

}
