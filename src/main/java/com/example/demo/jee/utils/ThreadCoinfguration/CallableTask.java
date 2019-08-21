package com.example.demo.jee.utils.ThreadCoinfguration;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


import com.example.demo.jee.holder.SpringContextHolder;
import com.example.demo.jee.utils.ReflectUtil;


/**
 * 用于多线程池处理汇付响应(同时粒化MVCC控制),有返回结果,不获取
 * @author minz
 *
 */
public class CallableTask implements Callable<Object>{
	private String targetName;
	private String methodName;
	private Object args[];
	private Class<?> paramTypes[];
	
	public CallableTask(String targetName, String methodName, Object... args) {
		this.targetName =  targetName;
		this.methodName =  methodName;
		this.args =  args;
		if(args!=null && args.length > 0){
			paramTypes = new Class[args.length];
			int i = 0;
			for (Object obj : args) {
				paramTypes[i] = obj.getClass();
				i++;
			}
		}else
			paramTypes = new Class[0];
	}

	@Override
	public Object call()  throws Exception {
		Object target = SpringContextHolder.getBean(targetName);
		Method method = ReflectUtil.findMethod(target.getClass(), methodName, paramTypes);
		return ReflectUtil.invokeMethod(method, target, args);
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Future<Object> f = ThreadConfiguration.THREAD_POOL.submit(new CallableTask("className", "methodName", null));
		System.out.println(f.get());////不对Future结果进行操作不能串行化
		System.out.println("--------------");
//		SysConstants.EXECUTOR_SERVICE.shutdown();
	}
}
