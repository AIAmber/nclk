package cn.com.sparknet.nclk.util;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

/**
 * XML的读写操作
 * @author chenxy
 *
 */
public final class XMLUtil {
	
	private XMLUtil(){
	}
	
	/**
	 * 新建一个docment
	 */
	public static synchronized Document newDocument() {
		Document doc = null;
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = db.newDocument();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return doc;
	}

	/**
	 * 创建根元素
	 * @return
	 */
	public static synchronized Element createRootElement() {
		Element rootElement = null;
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = db.newDocument();
			rootElement = doc.getDocumentElement();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return rootElement;
	}

	/**
	 * 通过文件路径获取根元素
	 * @param fileName
	 * @return
	 * @throws IOException 
	 */
	public static synchronized Element getRootElement(String fileName) throws IOException {
		Element element=null;
		FileInputStream fis =null;
		if (fileName == null || fileName.length() == 0) {
			return null;
		}
		try {
			fis = new FileInputStream(fileName);
			element=getRootElement(fis);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		} finally {
			if(null!=fis){
				fis.close();
				fis = null;
			}
		}
		return element;
	}

	/**
	 * 通过文件流获取根元素
	 * @param fileName
	 * @return
	 * @throws IOException 
	 */
	public static synchronized Element getRootElement(InputStream is) throws IOException {
		if (is == null) {
			return null;
		}
		Element rootElement = null;
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = db.parse(is);
			rootElement = doc.getDocumentElement();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		} finally {
			if(null!=is){
				is.close();
				is = null;
			}
		}
		return rootElement;
	}


	/**
	 * 获取子元素
	 * @param element
	 * @return
	 */
	public static synchronized Element[] getChildElements(Element element) {
		if (element == null) {
			return null;
		}
		Vector childs = new Vector();
		for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node instanceof Element) {
				childs.add((Element) node);
			}
		}
		Element[] elmt = new Element[childs.size()];
		childs.toArray(elmt);
		return elmt;
	}

	/**
	 * 获取子元素
	 * @param element
	 * @param childName
	 * @return
	 */
	public static synchronized Element[] getChildElements(Element element,String childName) {
		if (element == null || childName == null || childName.length() == 0) {
			return null;
		}
		Vector childs = new Vector();
		for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node instanceof Element) {
				if (node.getNodeName().equals(childName)) {
					childs.add((Element) node);
				}
			}
		}
		Element[] elmt = new Element[childs.size()];
		childs.toArray(elmt);
		return elmt;
	}

	/**
	 * 获取子节点
	 * @param node
	 * @return
	 */
	public static synchronized Node[] getChildNodes(Node node) {
		if (node == null) {
			return null;
		}
		Vector childs = new Vector();
		for (Node n = node.getFirstChild(); n != null; n = n.getNextSibling()) {
			childs.add((Element) n);
		}
		Node[] childNodes = new Element[childs.size()];
		childs.toArray(childNodes);
		return childNodes;
	}

	/**
	 * 获取子元素
	 * @param element
	 * @param childName
	 * @return
	 */
	public static synchronized Element getChildElement(Element element,String childName) {
		if (element == null || childName == null || childName.length() == 0) {
			return null;
		}
		Element childs = null;
		for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node instanceof Element) {
				if (node.getNodeName().equals(childName)) {
					childs = (Element) node;
					break;
				}
			}
		}
		return childs;
	}

	/**
	 * 获取子元素
	 * @param element
	 * @return
	 */
	public static synchronized Element getChildElement(Element element) {
		if (element == null) {
			return null;
		}
		Element childs = null;
		for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node instanceof Element) {
				childs = (Element) node;
				break;
			}
		}
		return childs;
	}

	/**
	 * 获取元素值
	 * @param element
	 * @return
	 */
	public static synchronized String[] getElementValues(Element element) {
		if (element == null) {
			return null;
		}
		Vector childs = new Vector();
		for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node instanceof Text) {
				childs.add(node.getNodeValue());
			}
		}
		String[] values = new String[childs.size()];
		childs.toArray(values);
		return values;
	}

	/**
	 * 获取元素值
	 * @param element
	 * @return
	 */
	public static synchronized String getElementValue(Element element) {
		if (element == null) {
			return null;
		}
		String retnStr = null;
		for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node instanceof Text) {
				String str = node.getNodeValue();
				if (str == null || str.length() == 0 || str.trim().length() == 0) {
					continue;
				} else {
					retnStr = str;
					break;
				}
			}
		}
		return retnStr;
	}

	/**
	 * 根据名称查找元素
	 * @param e
	 * @param name
	 * @return
	 */
	public static synchronized Element findElementByName(Element e, String name) {
		if (e == null || name == null || name.length() == 0) {
			return null;
		}
		String nodename = null;
		Element[] childs = getChildElements(e);
		for (int i = 0; i < childs.length; i++) {
			nodename = childs[i].getNodeName();
			if (name.equals(nodename)) {
				return childs[i];
			}
		}
		for (int i = 0; i < childs.length; i++) {
			Element retn = findElementByName(childs[i], name);
			if (retn != null) {
				return retn;
			}
		}
		return null;
	}

	/**
	 * 根据属性查找元素
	 * @param e
	 * @param attrName
	 * @param attrVal
	 * @return
	 */
	public static synchronized Element findElementByAttr(Element e, String attrName, String attrVal) {
		return findElementByAttr(e, attrName, attrVal, true);
	}
	public static synchronized Element findElementByAttr(Element e,
			String attrName, String attrVal, boolean dept) {
		if (e == null || attrName == null || attrName.length() == 0 || attrVal == null || attrVal.length() == 0) {
			return null;
		}
		String tmpValue = null;
		Element[] childs = getChildElements(e);
		for (int i = 0; i < childs.length; i++) {
			tmpValue = childs[i].getAttribute(attrName);
			if (attrVal.equals(tmpValue)) {
				return childs[i];
			}
		}
		if (dept) {
			for (int i = 0; i < childs.length; i++) {
				Element retn = findElementByAttr(childs[i], attrName, attrVal);
				if (retn != null) {
					return retn;
				}
			}
		}
		return null;
	}

	/**
	 * 在指定的节点前插入格式表示.
	 */
	private static synchronized void appendIndent(Element e, Node pos, int indent) {
		Document doc = e.getOwnerDocument();
		if (indent == 0) {
			e.insertBefore(doc.createTextNode("\n"), pos);
		}
		for (int i = 0; i < indent; i++) {
			if (i == 0) {
				e.insertBefore(doc.createTextNode("\n\t"), pos);
			} else {
				e.insertBefore(doc.createTextNode("\t"), pos);
			}
		}
	}

	/**
	 * 追加格式表示.
	 */
	private static synchronized void appendIndent(Element e, int indent) {
		Document doc = e.getOwnerDocument();
		if (indent == 0) {
			e.appendChild(doc.createTextNode("\n"));
		}
		for (int i = 0; i < indent; i++) {
			if (i == 0) {
				e.appendChild(doc.createTextNode("\n\t"));
			} else {
				e.appendChild(doc.createTextNode("\t"));
			}
		}
	}

	/**
	 * 设置属性
	 * @param e
	 * @param name
	 * @param value
	 */
	public static synchronized void setAttribute(Element e, String name,
			String value) {
		if (e == null || name == null || name.length() == 0 || value == null
				|| value.length() == 0){
			return;
		}else{
			e.setAttribute(name, value);
		}
	}

	/**
	 * 获取属性
	 * @param e
	 * @param name
	 * @return
	 */
	public static synchronized String getAttribute(Element e, String name) {
		return getAttribute(e, name, null);
	}

	/**
	 * 获取属性
	 * @param e
	 * @param name
	 * @param defval
	 * @return
	 */
	public static synchronized String getAttribute(Element e, String name,
			String defval) {
		if (e == null || name == null || name.length() == 0){
			return defval;
		}else{
			return e.getAttribute(name);
		}
	}

	public void transformerWrite(Element doc, String filename) throws Exception {
		DOMSource doms = new DOMSource(doc);
		File f = new File(filename);
		StreamResult sr = new StreamResult(f);
		transformerWrite(doms, sr);
	}
	public void transformerWrite(Element doc, File file) throws Exception {
		DOMSource doms = new DOMSource(doc);
		StreamResult sr = new StreamResult(file);
		transformerWrite(doms, sr);
	}

	public void transformerWrite(Element doc, OutputStream outstream) throws Exception {
		DOMSource doms = new DOMSource(doc);
		StreamResult sr = new StreamResult(outstream);
		transformerWrite(doms, sr);
	}

	public void transformerWrite(Element doc, Writer outwriter) throws Exception {
		DOMSource doms = new DOMSource(doc);
		StreamResult sr = new StreamResult(outwriter);
		transformerWrite(doms, sr);
	}

	public void transformerWrite(DOMSource doms, StreamResult sr) throws Exception {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		t.transform(doms, sr);
	}
}