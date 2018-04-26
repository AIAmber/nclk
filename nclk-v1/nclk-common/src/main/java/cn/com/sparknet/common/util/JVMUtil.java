package cn.com.sparknet.common.util;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

/**
 * Java虚拟机内存管理
 * @author chenxy
 *
 */
public class JVMUtil {
	
	private Runtime run;
	private OperatingSystemMXBean osmb;
	
	public JVMUtil() {
		this.run=Runtime.getRuntime();
		this.osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean(); 
	}
	
	/**
	 * 获取Runtime
	 * @return
	 */
	public Runtime getRunTime(){
		return this.run;
	}
	
	/**
	 * 获取JVM最大内存(Byte)
	 * @return
	 */
	public long getMaxMemory(){
		return this.run.maxMemory();
	}
	
	/**
	 * 获取JVM已分配内存(Byte)
	 * @return
	 */
	public long getTotalMemory(){
		return this.run.totalMemory();
	}
	
	/**
	 * 获取JVM已分配内存的剩余内存(Byte)
	 * @return
	 */
	public long getFreeMemory(){
		return this.run.freeMemory();
	}
	
	/**
	 * 获取JVM最大可用内存(Byte)
	 * @return
	 */
	public long getUsableMemory(){
		return (getMaxMemory() - getTotalMemory() + getFreeMemory());
	}
	
	/**
	 * 垃圾回收
	 */
	public void gc(){
		this.run.gc();
	}

	/**
	 * 获取系统最大物理内存(Byte)
	 * @return
	 */
	public long getTotalPhysicalMemorySize(){
		return this.osmb.getTotalPhysicalMemorySize();
	}
	
	/**
	 * 获取系统最大虚拟内存(Byte)
	 * @return
	 */
	public long getTotalSwapSpaceSize(){
		return this.osmb.getTotalSwapSpaceSize();
	}
	
	/**
	 * 获取系统剩余物理内存(Byte)
	 * @return
	 */
	public long getFreePhysicalMemorySize(){
		return this.osmb.getFreePhysicalMemorySize();
	}
	
	/**
	 * 获取系统剩余虚拟内存(Byte)
	 * @return
	 */
	public long getFreeSwapSpaceSize(){
		return this.osmb.getFreeSwapSpaceSize();
	}
	
	/**
	 * 获取系统已使用物理内存(Byte)
	 * @return
	 */
	public long getUsedPhysicalMemorySize(){
		return this.getTotalPhysicalMemorySize()-this.getFreePhysicalMemorySize();
	}
	
	/**
	 * 获取系统已使用虚拟内存(Byte)
	 * @return
	 */
	public long getUsedSwapSpaceSize(){
		return this.getTotalSwapSpaceSize()-this.getFreeSwapSpaceSize();
	}
	
}
