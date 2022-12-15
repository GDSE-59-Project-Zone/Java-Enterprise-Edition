package listeners;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        BasicDataSource bds= new BasicDataSource();
        bds.setDriverClassName("com.mysql.jdbc.Driver");
        bds.setUrl("jdbc:mysql://localhost:3306/company");
        bds.setUsername("root");
        bds.setPassword("sanu1234");
        bds.setMaxTotal(2);
        bds.setInitialSize(2);
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("dbcp",bds);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
