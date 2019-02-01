package nato.ivct.gui.shared.sut;

import java.util.Set;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface ISuTBadgeService extends IService {

	SuTBadgeTablePageData getSuTBadgeTableData(SearchFilter filter);

	Set<String> SutCapabilities(String sutId);
}
