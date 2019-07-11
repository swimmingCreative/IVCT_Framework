package nato.ivct.gui.server.cb;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.job.IFuture;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nato.ivct.commander.BadgeDescription;
import nato.ivct.commander.BadgeDescription.InteroperabilityRequirement;
import nato.ivct.commander.CmdListBadges;
import nato.ivct.gui.server.ServerSession;
import nato.ivct.gui.shared.cb.CbFormData;
import nato.ivct.gui.shared.cb.CbFormData.CbRequirementsTable;
import nato.ivct.gui.shared.cb.CbFormData.CbRequirementsTable.CbRequirementsTableRowData;
import nato.ivct.gui.shared.cb.CbTablePageData;
import nato.ivct.gui.shared.cb.CbTablePageData.CbTableRowData;
import nato.ivct.gui.shared.cb.CreateCbPermission;
import nato.ivct.gui.shared.cb.ICbService;
import nato.ivct.gui.shared.cb.ReadCbPermission;
import nato.ivct.gui.shared.cb.UpdateCbPermission;

public class CbService implements ICbService {

	private static final Logger LOG = LoggerFactory.getLogger(ServerSession.class);

	HashMap<String, BadgeDescription> cb_hm = null;

	@Override
	public Set<String> loadBadges() {
		if (cb_hm == null)
			// load badge descriptions
			waitForBadgeLoading(); 

		return new TreeSet<>(cb_hm.keySet());
	}

	public BadgeDescription getBadgeDescription(String cb) {
		if (cb_hm == null)
			waitForBadgeLoading();
		return cb_hm.get(cb);
	}

	void waitForBadgeLoading () {
		// wait until load badges job is finished
		IFuture<CmdListBadges> future = ServerSession.get().getLoadBadgesJob();
		CmdListBadges badgeCmd = (CmdListBadges) future.awaitDoneAndGet();
		cb_hm = badgeCmd.badgeMap;		
	}

	@Override
	public CbTablePageData getCbTableData(SearchFilter filter) {
		LOG.info("getCbTableData");
		CbTablePageData pageData = new CbTablePageData();
		// wait until load badges job is finished
		if (cb_hm == null)
			waitForBadgeLoading();
		
		CbTableRowData row;
		for (BadgeDescription value : cb_hm.values()) {
			row = pageData.addRow();
			row.setCpId(value.ID);
			row.setCapabilityName(value.name);
			row.setCapabilityDescription(value.description);
			cb_hm.put(value.ID, value);
		}
		return pageData;
	}

	@Override
	public CbTablePageData getCbTableData(SearchFilter filter, String sutId) {
		LOG.info("getCbTableData with SuT restriction");
		CbTablePageData pageData = new CbTablePageData();
		return pageData;
	}

	@Override
	public CbFormData prepareCreate(CbFormData formData) {
		LOG.info("prepareCreate");
		if (!ACCESS.check(new CreateCbPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}
		return formData;
	}

	@Override
	public CbFormData create(CbFormData formData) {
		LOG.info("create");
		if (!ACCESS.check(new CreateCbPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}
		return formData;
	}

	@Override
	public CbFormData load(CbFormData formData) {
		LOG.info("load");
		if (!ACCESS.check(new ReadCbPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}
		BadgeDescription cb = cb_hm.get(formData.getCbId());
		formData.getCbName().setValue(cb.name);
		
		formData.getCbDescription().setValue(cb.description);

		// dependencies tree is built in CbDependenciesLookupService class
		return formData;
	}
	
	public byte[] loadBadgeIcon(String badgeId) {
		BadgeDescription cb = cb_hm.get(badgeId);
		
		if (cb.cbVisual == null) {
			LOG.error("No icon file for badge ID " + cb.ID);
			return null;
		}
		
		try {
			return Files.readAllBytes((Paths.get(cb.cbVisual)));
		} catch (IOException exe) {
			LOG.error("Could not open icon file " + cb.cbVisual == null ? ": Icon File not available" : cb.cbVisual);
			return null;
		}
	}

	@Override
	public CbFormData store(CbFormData formData) {
		LOG.info("store");
		if (!ACCESS.check(new UpdateCbPermission())) {
			throw new VetoException(TEXTS.get("AuthorizationFailed"));
		}
		return formData;
	}

	@Override
	public CbRequirementsTable loadRequirements(final Set<String> badges) {
		CbRequirementsTable requirementTableRows = new CbRequirementsTable();
		for (String badge:badges) {
			BadgeDescription bd = getBadgeDescription(badge);
			for (InteroperabilityRequirement requirement:bd.requirements) {
				CbRequirementsTableRowData row = requirementTableRows.addRow();
				row.setRequirementId(requirement.ID);
				row.setRequirementDesc(requirement.description);
				row.setAbstractTC(requirement.TC);
			}
		}

		return requirementTableRows;
	}
}
