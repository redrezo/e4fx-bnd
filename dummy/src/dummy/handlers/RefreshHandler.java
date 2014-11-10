package dummy.handlers;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;

@SuppressWarnings({ "restriction" })
public class RefreshHandler {
	public static final String REFRESH_EVENT = "media/refresh";
	
	@Execute
	public void execute(IEventBroker broker)
			throws InvocationTargetException, InterruptedException {
		broker.send(REFRESH_EVENT, UUID.randomUUID().toString());
	}
}
