package nato.ivct.gui.server.sut;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

import nato.ivct.gui.server.cb.CbService;
import nato.ivct.gui.shared.cb.ICbService;
import nato.ivct.gui.shared.sut.ISuTCbLookupService;


public class SuTCbLookupService extends AbstractLookupService<String> implements ISuTCbLookupService {

    @Override
    public List<? extends ILookupRow<String>> getDataByKey(ILookupCall<String> call) {
        return CollectionUtility.emptyArrayList();
    }


    @Override
    public List<? extends ILookupRow<String>> getDataByRec(ILookupCall<String> call) {
        return CollectionUtility.emptyArrayList();
    }


    @Override
    public List<? extends ILookupRow<String>> getDataByText(ILookupCall<String> call) {
        return CollectionUtility.emptyArrayList();
    }


    @Override
    public List<? extends ILookupRow<String>> getDataByAll(ILookupCall<String> call) {
        ArrayList<LookupRow<String>> cbList = CollectionUtility.emptyArrayList();
        CbService cbService = (CbService) BEANS.get(ICbService.class);

        cbService.loadBadges().forEach(id-> {
            // TODO Replace ID by name; this requires a table with 2 columns instead of a list in the SuTEditForm
        	LookupRow<String> lookupRow = new LookupRow<String>(id, cbService.getBadgeDescription(id).ID);
            cbList.add(lookupRow);
        });
        
        return cbList;
    }
}
