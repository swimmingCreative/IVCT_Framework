package nato.ivct.gui.shared.sut;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface ISuTCbService extends IService {

	SuTCbTablePageData getSuTCbTableData(SearchFilter filter);
	void executeTestCase(String sut, String tc, String badge);

}