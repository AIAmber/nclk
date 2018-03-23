package cn.com.sparknet.nclk.endpointserver.core;

import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * @author tangzj
 */
@WebServlet(name = "EndPointServerServlet", value = {}, loadOnStartup = 1)
public class EndPointServerServlet extends HttpServlet {

    private Logger logger = Logger.getLogger(getClass());
    private EndPointServer endPointServer;


    @Override
    public void init() {
        endPointServer = new EndPointServer();
        try {
            endPointServer.startListen();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void destroy() {
        try {
            endPointServer.destroy();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
