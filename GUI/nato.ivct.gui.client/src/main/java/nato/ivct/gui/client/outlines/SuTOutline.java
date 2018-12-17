package nato.ivct.gui.client.outlines;

import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.basic.tree.ITreeNode;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import nato.ivct.gui.client.sut.SuTBadgeTablePage;
import nato.ivct.gui.shared.Icons;
import nato.ivct.gui.shared.sut.ISuTService;

public class SuTOutline extends AbstractOutline {

      @Override
      protected String getConfiguredTitle() {
        return TEXTS.get("SuTTablePage");
      }

	  @Override
	  protected void execCreateChildPages(List<IPage<?>> pageList) {
//		  pageList.clear();
	      Set<String> suts = BEANS.get(ISuTService.class).loadSuts();
		  suts.forEach(s -> {SuTBadgeTablePage tp = new SuTBadgeTablePage(s); tp.setOverviewIconId(getConfiguredIconId());pageList.add(tp);});
		  
//		  pageList.add(new SuTBadgeTablePage("NewSUT"));
//        addChildNode(getRootPage(), new SuTBadgeTablePage("<NewSUT>"));
	  }

	  @Override
	  protected String getConfiguredIconId() {
	    return Icons.Gear;
	  }
}
