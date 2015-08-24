package handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;

public class OpenStatisticSiteHandler extends AbstractHandler {
	
	public Object execute(ExecutionEvent event) {
		WebsiteGenerator wg = new WebsiteGenerator();
		wg.createPage();
		return null;
	}
}
