package cn.com.sparknet.common.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 字符工具类
 * @author chenxy
 */
public final class StringUtil{
	
	private StringUtil(){
	}
		
	/**
     * 判断是否不为null
     * @param string
     * @return boolean
     */
	public static final boolean isNotEmpty(String string) {
		return string != null &&(!string.equals(""))&& string.length() > 0;
	}
	
	/**
	 * 判断是否为null
	 * @param string
	 * @return boolean
	 */
	public static final boolean isEmpty(String string) {
		return string == null || string.length() == 0|| "null".equals(string);
	}
	
	/**
     * null转换成""
     * @param string
     * @return String
     */
	public static  final String nullToEmpty(String str) {
		if (str == null||"null".equals(str)) {
			return "";
		} else {
			return str.toString().trim();
		}
	}
	/**
     * null转换成"0"
     * @param string
     * @return String
     */
	public static final String nullToZero(String str) {
		if (str == null || "null".equals(str) || "".equals(str)) {
			return "0";
		} else {
			return str.toString().trim();
		}
	}
	/**
     * null转换成" "
     * @param string
     * @return String
     */
	public static final String nullToSpace(String str) {
		if (str == null || "".equals(str)) {
			return " ";
		} else {
			return str.toString().trim();
		}
	}
	/**
     * null转换成"null"
     * @param string
     * @return String
     */
	public static final String nullToNull(String str) {
		if (str == null||"null".equals(str)||"".equals(str)) {
			return "null";
		} else {
			return str.toString().trim();
		}
	}
	
	/**
	 * 对象为null时返回其他自定义对象
	 * @param obj
	 * @param replaceContent
	 * @return
	 */
	public static Object nullToObject(Object sourceObj,Object targetObj){
		if(sourceObj == null){
			return targetObj;
		}
		String str = sourceObj.toString().trim();
		return str.length() == 0 ? targetObj : str;
	}

	/**
	 * 
	 * 说明：将字符串数组{"str1","str2","str3"}转换成'str1','str2','str3'
	 * @param string[]
	 * @return String
	 *
	 */
	public static final String arrToStr(String[] strings)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("'");
		buf.append(join("','",strings));
		buf.append("'");
		return buf.toString();
	}
    /**
	 *
	 * 说明：将字符串"str1,str2,str3"转换成"'str1','str2','str3'"
	 * @param String
	 * @return String
	 *
	 */
    public static final String strToStr(String str)
    {
        StringBuffer buf = new StringBuffer();
		buf.append("'");
		buf.append(replace(str,",","','"));
		buf.append("'");
        return buf.toString();
    }
    
    public static final String join(String seperator, String[] strings) {
		int length = strings.length;
		if (length == 0){
			return "";
		}
		StringBuffer buf = new StringBuffer(length * strings[0].length()).append(strings[0]);
		for ( int i = 1; i < length; i++ ) {
			buf.append(seperator).append(strings[i]);
		}
		return buf.toString();
	}
    /**
	 *
	 * 说明：替换字符串
	 * @param String
	 * @return String
	 *
	 */
	public static final String replace(String template, String placeholder, String replacement) {
		return replace(template, placeholder, replacement, false );
	}
	public static final String replace(String template, String placeholder, String replacement, boolean wholeWords) {
		int loc = template.indexOf( placeholder );
		if ( loc < 0 ) {
			return template;
		}
		else {
			final boolean actuallyReplace = !wholeWords ||
					loc + placeholder.length() == template.length() ||
					!Character.isJavaIdentifierPart( template.charAt( loc + placeholder.length() ) );
			String actualReplacement = actuallyReplace ? replacement : placeholder;
			return new StringBuffer( template.substring( 0, loc ) )
					.append( actualReplacement )
					.append( replace( template.substring( loc + placeholder.length() ),
							placeholder,
							replacement,
							wholeWords ) ).toString();
		}
	}
    
    /**
     * Map中的null处理
     * @param Map
     * @return Map
     */
    public static final Map nullToEmptyForMap(Map map) {   
        Map convertedMap = null;
        Set mapset = map.entrySet();
        String EMPTYSTR = "";   
        Entry entry=null;
        if (map != null) {   
            convertedMap = new HashMap();   
            Iterator it=null;
            for (it = mapset.iterator(); it.hasNext();) {   
                entry = (Entry) it.next();   
                if (entry.getValue() == null) {   
                    convertedMap.put(entry.getKey(), EMPTYSTR);   
                }   
                else {   
                    convertedMap.put(entry.getKey(), entry.getValue());   
                }   
            }   
        }
        return convertedMap;   
    }  
    /**
     * List中的null处理
     * @param List
     * @return List
     */
    public static final List nullToEmptyForList(List list) {   
	    List convertedList = null;   
	    if (list != null && list.size() > 0) {   
	        convertedList = new ArrayList();   
	        Iterator it = list.iterator();   
	        Map map=null;
	        Map convertedMap=null;
	        while (it.hasNext()) {   
	            map = (Map) it.next();   
	            convertedMap = nullToEmptyForMap(map);   
	            convertedList.add(convertedMap);   
	        }   
	    }   
	    return convertedList;   
	}  
    
    /**
     * Date转为String
     * @return String
     */
    public static final String dateToString(Date date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);   
	    Calendar cd = Calendar.getInstance();   
	    cd.setTime(date);
        return sdf.format(cd.getTime());
    }
    
    /**
     * 获取系统日期
     * @return String
     */
    public static final String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
	    Calendar cd = Calendar.getInstance();   
	    cd.setTime(new Date());
        return sdf.format(cd.getTime());
    }
    /**
     * 获取系统时间
     * @return String
     */
    public static final String getTime(){
    	SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");   
	    Calendar cd = Calendar.getInstance();   
	    cd.setTime(new Date());
        return sdf.format(cd.getTime());
    }
    /**
     * 获取系统日期 时间
     * @return String
     */
    public static final String getDateTime(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	    Calendar cd = Calendar.getInstance();
	    cd.setTime(new Date());
        return sdf.format(cd.getTime());
    }
    
    private static GregorianCalendar calendar = new GregorianCalendar();
    
	public static final int getYear() {
		return calendar.get(Calendar.YEAR);
	}

	public static final int getMonth() {
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static final int getDay() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static final int getHours() {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public static final int getMinutes() {
		return calendar.get(Calendar.MINUTE);
	}

	public static final int getSeconds() {
		return calendar.get(Calendar.SECOND);
	}

	public static final int getMilliSeconds() {
		return calendar.get(Calendar.MILLISECOND);
	}
	public static final String getMorningValue() {
		if (getHours() >= 1 && getHours() <= 12) {
			return "上午好";
		} else if (getHours() >= 13 && getHours() <= 18) {
			return "下午好";
		} else {
			return "晚上好";
		}
	}
	
	/**
	 * 说明：将字符串org_id转换成orgId的形式
     * @return String
     */
    public static final String getBeanStr(String str){
    	String beanStr="";
    	if(str.indexOf("_",-1)>-1){
    		String[] strArr=str.toLowerCase().split("_");
			for(int i=0;i<strArr.length;i++){
				if(i==0){
					beanStr+=strArr[0];
				}else{
					beanStr+=(strArr[i]).substring(0,1).toUpperCase()+strArr[i].substring(1,strArr[i].length());
				}
			}
    	}else{
    		beanStr=str.toLowerCase();
    	}
        return beanStr;
    }
    
    /**
     * 说明：获取Map中的所有键的集合
     * @return List<String>
     */
    public static final List<String> getMapKey(Map map){
    	List<String> list = new ArrayList<String>();
    	Set set = map.entrySet();
        Iterator iterator = set.iterator();  
        Map.Entry mapEntry=null;
        while(iterator.hasNext()){   
            mapEntry = (Map.Entry) iterator.next();
            list.add(mapEntry.getKey().toString()); 
        }
        return list;
    }
    
    /**
	 * 说明：将Map中的键转换成大(小)写
     * @return Map
     */
    public static final Map getUpperLowerMap(Map map,String upperOrLower){
    	String upperOrLowerStr=nullToEmpty(upperOrLower);
    	Map resultMap = new HashMap();
    	Set set = map.entrySet();   
        Iterator iterator = set.iterator();  
        Map.Entry mapEntry=null;
        while(iterator.hasNext()){
            mapEntry = (Map.Entry) iterator.next();
            if(upperOrLowerStr.equalsIgnoreCase("upper")){
            	resultMap.put(mapEntry.getKey().toString().toUpperCase(), mapEntry.getValue()); 
            }else if(upperOrLowerStr.equalsIgnoreCase("lower")){
            	resultMap.put(mapEntry.getKey().toString().toLowerCase(), mapEntry.getValue()); 
            }else{
            	resultMap.put(mapEntry.getKey().toString(), mapEntry.getValue()); 
            }
        }
        return resultMap;
    }
    
    /**
     * 说明：获取项目名称
     * @return String
     */
    public static final String getContextPath(HttpServletRequest request){
    	return request.getContextPath();
    }
    
    /**
     * 说明：获取项目URL路径
     * @return String
     */
    public static final String getUrlPath(HttpServletRequest request){
    	return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+getContextPath(request);
    }
    
    /**
     * 说明：获取项目目录路径
     * @return String
     */
    public static final String getDirectoryPath(HttpServletRequest request,Class<?> clazz){
    	String url=clazz.getClassLoader().getResource("/").toString();
    	if(url.indexOf("file:/")>-1){
    		url=url.replace("file:/","");
    	}
    	String contextPath=getContextPath(request);
    	url=url.split(contextPath)[0]+contextPath;
    	return url.replaceAll("%20", " ");//处理路劲中的空格
    }
    
    /**
     * 说明：设置Cookie
     * @param response
     * @param key		健
     * @param value		值
     * @param maxAge	存活周期 单位：秒 如果设置为负值，则在内存中保存，关闭浏览器就失效
     */
    public static final void setCookie(HttpServletResponse response,String key,String value,int maxAge){
    	String encodeValue="";
		try {
			encodeValue = java.net.URLEncoder.encode(value,"utf-8");
			Cookie cookie = new Cookie(key,encodeValue);
	    	cookie.setMaxAge(maxAge);
	    	cookie.setValue(encodeValue);
	    	response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
    }
    /**
     * 说明：获取Cookie
     * @param request
     * @param key		健
     * @return
     */
    public static final String getCookie(HttpServletRequest request,String key){
    	String value="";
        try {
	    	Cookie c = null;
	    	Cookie[] cookies = request.getCookies();
	        for (int i = 0; i < cookies.length; i++) {
	            c = cookies[i];
	            if(c.getName().equals(key)){
	            	value=java.net.URLDecoder.decode(c.getValue(), "UTF-8");
	            }
	        }
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return value;
    }
    
    /**
     * 说明：修改Cookie
     * @param request
     * @param response
     * @param key
     * @param newValue
     */
    public static final void updCookie(HttpServletRequest request,HttpServletResponse response,String key,String newValue){
    	try {
			String encodeValue = java.net.URLEncoder.encode(newValue,"utf-8");
			Cookie[] cookies = request.getCookies();
	    	if(cookies.length>1){
	    		for(int i = 0;i<cookies.length;i++){
	    			if(cookies[i].getName().equals(key)){
	    				cookies[i].setValue(encodeValue);
	    				response.addCookie(cookies[i]);
	    				break;
	    			}
	    		}
	    	}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
    }
    /**
     * 说明：删除Cookie
     * @param response
     * @param key
     */
    public static final void delCookie(HttpServletResponse response,String key){
    	Cookie cookie = new Cookie(key,null);
    	cookie.setMaxAge(0);
    	response.addCookie(cookie);
    }
    
    /**
     * 获取IP地址
     * @param request
     * @return
     */
    public static final String getIpAddress(HttpServletRequest request) {         
        String ip = request.getHeader("x-forwarded-for");         
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {         
            ip = request.getHeader("Proxy-Client-IP");         
        }         
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {         
            ip = request.getHeader("WL-Proxy-Client-IP");         
        }         
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {         
           ip = request.getRemoteAddr();         
       }         
       return ip;         
   }  

    
    /**
     * 获取本机Mac地址
     * @return
     */
    public static final String getMacAddress() {
    	String line="";
    	String MacAddress="";
    	InputStreamReader is=null;
    	LineNumberReader ln=null;
    	try {
    	    Process process = Runtime.getRuntime().exec("ipconfig /all");
    	    is = new InputStreamReader(process.getInputStream());
    	    ln = new LineNumberReader(is);
    	    while ((line = ln.readLine()) != null){
    	        if (line.indexOf("Physical Address") > 0) {
    	        	MacAddress = line.substring(line.indexOf("-") - 2);
    	        }
    	    }
    	} catch (IOException e) {
    		throw new RuntimeException(e.getMessage(),e);
    	} finally{
    		try{
	    		if(is!=null){
	    			is.close();
	    			is=null;
	    		}
	    		if(ln!=null){
	    			ln.close();
	    			ln=null;
	    		}
    		}catch(Exception e){
    			throw new RuntimeException(e.getMessage(),e);
    		}
    	}
    	return MacAddress;
    }

    
    /**
     * 根据IP获取Mac地址
     * @param ip
     * @return
     * @throws IOException
     */
    public static final String getMacAddress(String ip){
    	if(ip.equals("localhost")||ip.equals("127.0.0.1")){
    		return getMacAddress();
    	}
    	String line="";
        String macAddress = "";
        InputStreamReader is = null;
        LineNumberReader ln = null;
        try{
	        Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
	        is = new InputStreamReader(p.getInputStream());
	        ln = new LineNumberReader(is);
	        for (int i = 1; i < 100; i++) {
	        	line = ln.readLine();
	            if (line != null) {
	                if (line.indexOf("MAC Address") > 1) {
	                    macAddress = line.substring(line.indexOf("MAC Address") + 14, line.length());
	                    break;
	                }
	            }
	        }
        }catch(IOException e){
        	throw new RuntimeException(e.getMessage(),e);
        }finally{
    		try{
    			if(ln!=null){
	    			ln.close();
	    			ln=null;
	    		}
	    		if(is!=null){
	    			is.close();
	    			is=null;
	    		}
    		}catch(Exception e){
    			throw new RuntimeException(e.getMessage(),e);
    		}
    	}
        return macAddress;
    }
    
    /**
	 * 判断date1是否在date2之后
	 */
	public static boolean isAfterDate(String date1, String date2) throws Exception{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = format.parse(date1);
		Date d2 = format.parse(date2);
		if (d1.after(d2)) {
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断date1是否在date2之前
	 */
	public static boolean isBeforeDate(String date1, String date2) throws Exception{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = format.parse(date1);
		Date d2 = format.parse(date2);
		if (d1.before(d2)) {
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断date1是否与date2相等
	 */
	public static boolean isEqualsDate(String date1, String date2) throws Exception{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = format.parse(date1);
		Date d2 = format.parse(date2);
		if (d1.equals(d2)) {
			return true;
		}else{
			return false;
		}
	}
	
	/**
     * 获取日期区间
     */
    public static String[] getRangeDate(String startDateStr,String endDateStr) throws Exception {
       SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
       Date tempDate=myFormatter.parse(startDateStr);
       Date date1= myFormatter.parse(startDateStr);
       Date date2= myFormatter.parse(endDateStr);
       long day=(date2.getTime()-date1.getTime())/(24*60*60*1000)+1;
       String[] date=new String[(int)day];
       date[0]=startDateStr;
       long startDate=0;
       for(int i=1;i<(int)day;i++){
          startDate=(tempDate.getTime()/1000)+60*60*24;
          tempDate.setTime(startDate*1000);
          date[i]=myFormatter.format(tempDate);
          if(tempDate.getTime()==date2.getTime()){
              break;
          }
       }
       return date;
    }
    
	/**
	 * 16进制转换为二进制
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {  
        if (hexStr.length() < 1) {
        	return null;
        }
        byte[] result = new byte[hexStr.length()/2];  
        for (int i = 0;i< hexStr.length()/2; i++) {  
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
            result[i] = (byte) (high * 16 + low);  
        }  
        return result;  
	}
	
	/**
	 * 二进制转换成16进制  
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte[] buf) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
            String hex = Integer.toHexString(buf[i] & 0xFF);  
            if (hex.length() == 1) {  
                hex = '0' + hex;  
            }  
            sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
	}
    
    /**
     * 过滤掉<>防止跨站脚本
     * @param str
     * @return
     */
    public static String strFiltrate(String str) {
        if (str == null || str.equals("")) {
            return str;
        }
        str = str.replaceAll("&", "&amp;");
        str = str.replaceAll("<", "&#x3C;");
        str = str.replaceAll(">", "&#x3E;");
        str = str.replaceAll("\"", "");
        str = str.replaceAll("\'", "");
        str = str.replaceAll("%", "");
        str = str.replaceAll("eval", "");
        str = str.replaceAll("expression", "");
        str = str.replaceAll("unescape", "");
        str = str.replaceAll(",", "，");
        str = str.replaceAll(";", "；");
        str = str.replaceAll(":", "：");
        return str;
    }
}
