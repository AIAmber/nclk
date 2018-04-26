package cn.com.sparknet.common.util;

/**
 * 获取Web服务器
 * @author chenxy
 * 
 */
public class ServerDetector {
	
	private static ServerDetector instance = new ServerDetector();

	private String serverId;
	private Boolean geronimo;
	private Boolean glassfish;
	private Boolean glassfish2;
	private Boolean glassfish3;
	private Boolean jBoss;
	private Boolean jetty;
	private Boolean jonas;
	private Boolean oc4j;
	private Boolean resin;
	private Boolean tomcat;
	private Boolean webLogic;
	private Boolean webSphere;

	/**
	 * 获取服务器ID
	 * @return
	 */
	public static String getServerId() {
		ServerDetector sd = instance;
		if (sd.serverId == null) {
			if (isGeronimo()) {
				sd.serverId = "Geronimo";
			} else if (isGlassfish()) {
				sd.serverId = "Glassfish";
			} else if (isJBoss()) {
				sd.serverId = "Jboss";
			} else if (isJOnAS()) {
				sd.serverId = "Jonas";
			} else if (isOC4J()) {
				sd.serverId = "Oc4j";
			} else if (isResin()) {
				sd.serverId = "Resin";
			} else if (isWebLogic()) {
				sd.serverId = "Weblogic";
			} else if (isWebSphere()) {
				sd.serverId = "Websphere";
			}
			if (isJetty()) {
				if (sd.serverId == null) {
					sd.serverId = "Jetty";
				} else {
					sd.serverId += "-Jetty";
				}
			} else if (isTomcat()) {
				if (sd.serverId == null) {
					sd.serverId = "Tomcat";
				} else {
					sd.serverId += "-Tomcat";
				}
			}
			if (sd.serverId == null) {
				throw new RuntimeException("Server is not supported");
			}
		}
		return sd.serverId;
	}

	public static boolean isGeronimo() {
		ServerDetector sd = instance;
		if (sd.geronimo == null) {
			sd.geronimo = _detect("/org/apache/geronimo/system/main/Daemon.class");
		}
		return sd.geronimo.booleanValue();
	}

	public static boolean isGlassfish() {
		ServerDetector sd = instance;
		if (sd.glassfish == null) {
			String value = System.getProperty("com.sun.aas.instanceRoot");
			if (value != null) {
				sd.glassfish = Boolean.TRUE;
			} else {
				sd.glassfish = Boolean.FALSE;
			}
		}
		return sd.glassfish.booleanValue();
	}

	public static boolean isGlassfish2() {
		ServerDetector sd = instance;
		if (sd.glassfish2 == null) {
			if ((isGlassfish()) && (!(isGlassfish3()))) {
				sd.glassfish2 = Boolean.TRUE;
			} else {
				sd.glassfish2 = Boolean.FALSE;
			}
		}
		return sd.glassfish2.booleanValue();
	}

	public static boolean isGlassfish3() {
		ServerDetector sd = instance;
		if (sd.glassfish3 == null) {
			String value = "";
			if (isGlassfish()) {
				value = System.getProperty("product.name").trim();
			}
			if (value.equals("GlassFish/v3")) {
				sd.glassfish3 = Boolean.TRUE;
			} else {
				sd.glassfish3 = Boolean.FALSE;
			}
		}
		return sd.glassfish3.booleanValue();
	}

	public static boolean isJBoss() {
		ServerDetector sd = instance;
		if (sd.jBoss == null) {
			sd.jBoss = _detect("/org/jboss/Main.class");
		}
		return sd.jBoss.booleanValue();
	}

	public static boolean isJetty() {
		ServerDetector sd = instance;
		if (sd.jetty == null) {
			sd.jetty = _detect("/org/mortbay/jetty/Server.class");
		}
		return sd.jetty.booleanValue();
	}

	public static boolean isJOnAS() {
		ServerDetector sd = instance;
		if (sd.jonas == null) {
			sd.jonas = _detect("/org/objectweb/jonas/server/Server.class");
		}
		return sd.jonas.booleanValue();
	}

	public static boolean isOC4J() {
		ServerDetector sd = instance;
		if (sd.oc4j == null) {
			sd.oc4j = _detect("oracle.oc4j.util.ClassUtils");
		}
		return sd.oc4j.booleanValue();
	}

	public static boolean isResin() {
		ServerDetector sd = instance;
		if (sd.resin == null) {
			sd.resin = _detect("/com/caucho/server/resin/Resin.class");
		}
		return sd.resin.booleanValue();
	}

	public static boolean isSupportsComet() {
		return false;
	}

	public static boolean isTomcat() {
		ServerDetector sd = instance;
		if (sd.tomcat == null) {
			sd.tomcat = _detect("/org/apache/catalina/startup/Bootstrap.class");
		}
		if (sd.tomcat == null) {
			sd.tomcat = _detect("/org/apache/catalina/startup/Embedded.class");
		}
		return sd.tomcat.booleanValue();
	}

	public static boolean isWebLogic() {
		ServerDetector sd = instance;
		if (sd.webLogic == null) {
			sd.webLogic = _detect("/weblogic/Server.class");
		}
		return sd.webLogic.booleanValue();
	}

	public static boolean isWebSphere() {
		ServerDetector sd = instance;
		if (sd.webSphere == null) {
			sd.webSphere = _detect("/com/ibm/websphere/product/VersionInfo.class");
		}
		return sd.webSphere.booleanValue();
	}

	private static Boolean _detect(String className) {
		try {
			ClassLoader.getSystemClassLoader().loadClass(className);
			return Boolean.TRUE;
		} catch (ClassNotFoundException cnfe) {
			ServerDetector sd = instance;
			if (sd.getClass().getResource(className) != null) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
}
