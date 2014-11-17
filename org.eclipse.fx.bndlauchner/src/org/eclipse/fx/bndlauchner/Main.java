package org.eclipse.fx.bndlauchner;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.application.ApplicationDescriptor;
import org.osgi.service.application.ApplicationHandle;
import org.osgi.service.component.annotations.Component;

@Component(property="main.thread=true")
public class Main implements Runnable {
	 
	String[] args;
     
	public void run(){ 
		System.err.println("Starting application from product");
		System.err.println();
		final BundleContext ctx = FrameworkUtil.getBundle(Main.class).getBundleContext();
		try {
			IProduct p = Platform.getProduct();
			String application = p.getApplication();
			System.err.println("application = " + application);
			final Collection<ServiceReference<ApplicationDescriptor>> refs = ctx.getServiceReferences(ApplicationDescriptor.class, "(service.pid="+application+")");
			if (!refs.isEmpty()) {
				final ServiceReference<ApplicationDescriptor> first = refs.iterator().next();
				final ApplicationDescriptor appDesc = ctx.getService(first);
				
				final ApplicationHandle handle = appDesc.launch(Collections.emptyMap());
				
				final Object exitValue = handle.getExitValue(0);
				System.err.println("Exit Value: " + exitValue);
			}
			else {
				System.err.println("application not found!");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// somehow this does not resolve? it worked last time i used it...
//	@Reference(target="(launcher.arguments=*)")
	void setArgs(Object service, Map<String,Object> props) {
		this.args = (String[]) props.get("launcher.arguments");
	}
	
 }