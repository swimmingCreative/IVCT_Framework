package nato.ivct.gui.client.sut;

import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.shared.notification.INotificationHandler;
import org.slf4j.LoggerFactory;

import nato.ivct.gui.client.Desktop;
import nato.ivct.gui.client.ResourceBase;
import nato.ivct.gui.client.outlines.BadgeOutline;
import nato.ivct.gui.shared.sut.TestCaseNotification;

public class TestCaseNotificationHandler implements INotificationHandler<TestCaseNotification> {

	org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void handleNotification(TestCaseNotification notification) {
		// inform client about test case verdict
		ModelJobs.schedule(new IRunnable() {
			@Override
			public void run() throws Exception {
				logger.trace("Test Case Notification " + notification.getVerdict() + " received for "
						+ notification.getTc());

				for (IOutline outline : Desktop.CURRENT.get().getAvailableOutlines()) {
					if (outline instanceof BadgeOutline) {
						SuTCbTablePage cTP = (SuTCbTablePage) outline.getActivePage();
						for (ITableRow tr : cTP.getTable().getRows()) {
							// find row with test case name
							if (tr.getCellValue(3).equals(notification.getTc())) {
								tr.setCellValue(4, notification.getVerdict());
								tr.setBackgroundColor(ResourceBase.getVerdictColor(notification.getVerdict()));
							}
						}
					}
				}
			}
		}, ModelJobs.newInput(ClientRunContexts.copyCurrent()));
	}
}
