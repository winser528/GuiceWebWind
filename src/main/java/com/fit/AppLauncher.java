package com.fit;

import com.fit.util.DbUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.expressme.webwind.DispatcherServlet;

public class AppLauncher {
    static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        Server server = new Server(PORT);
        startServer(server);
        System.out.println("Server started at http://localhost:" + PORT);
        server.join();
        stopServer(server);
    }

    static void startServer(Server server) throws Exception {
        DbUtils.initDb();
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        // 设置 Web 应用的上下文路径
        context.setContextPath("/");
        // 设置 resourceBase 到一个有效的目录（不需要 WEB-INF）
        context.setResourceBase(AppLauncher.class.getClassLoader().getResource("").getPath());  // 设置静态资源目录
        // 创建 ServletHolder 并配置 DispatcherServlet
        ServletHolder dispatcherServletHolder = new ServletHolder(new DispatcherServlet());
        dispatcherServletHolder.setInitParameter("container", "Guice");
        dispatcherServletHolder.setInitParameter("modules", "conf.BlogModule");
        dispatcherServletHolder.setInitParameter("template", "Velocity");
        dispatcherServletHolder.setInitOrder(0); // load-on-startup 设置为 0
        // 将 DispatcherServlet 映射到根路径 "/"
        context.addServlet(dispatcherServletHolder, "/");
        // 将 ServletContextHandler 设置到服务器
        server.setHandler(context);
        server.start();
        while (!server.isRunning()) ;
    }

    static void stopServer(Server server) {
        try {
            server.stop();
            while (!server.isStopped()) ;
        } catch (Exception e) {
        }
    }
}