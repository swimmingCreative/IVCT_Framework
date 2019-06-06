package nato.ivct.gui.shared.sut;

import java.util.Set;

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications
 * recommended.
 */
@Generated(value = "nato.ivct.gui.client.sut.SuTEditForm", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class SuTEditFormData extends AbstractFormData {

	private static final long serialVersionUID = 1L;

	public Descr getDescr() {
		return getFieldByClass(Descr.class);
	}

	public FederationName getFederationName() {
		return getFieldByClass(FederationName.class);
	}

	public Name getName() {
		return getFieldByClass(Name.class);
	}

	public RtiSettingDesignator getRtiSettingDesignator() {
		return getFieldByClass(RtiSettingDesignator.class);
	}

	public SuTCapabilityBox getSuTCapabilityBox() {
		return getFieldByClass(SuTCapabilityBox.class);
	}

	/**
	 * access method for property SutId.
	 */
	public String getSutId() {
		return getSutIdProperty().getValue();
	}

	/**
	 * access method for property SutId.
	 */
	public void setSutId(String sutId) {
		getSutIdProperty().setValue(sutId);
	}

	public SutIdProperty getSutIdProperty() {
		return getPropertyByClass(SutIdProperty.class);
	}

	public SutVendor getSutVendor() {
		return getFieldByClass(SutVendor.class);
	}

	public Version getVersion() {
		return getFieldByClass(Version.class);
	}

	public static class Descr extends AbstractValueFieldData<String> {

		private static final long serialVersionUID = 1L;
	}

	public static class FederationName extends AbstractValueFieldData<String> {

		private static final long serialVersionUID = 1L;
	}

	public static class Name extends AbstractValueFieldData<String> {

		private static final long serialVersionUID = 1L;
	}

	public static class RtiSettingDesignator extends AbstractValueFieldData<String> {

		private static final long serialVersionUID = 1L;
	}

	public static class SuTCapabilityBox extends AbstractValueFieldData<Set<String>> {

		private static final long serialVersionUID = 1L;
	}

	public static class SutIdProperty extends AbstractPropertyData<String> {

		private static final long serialVersionUID = 1L;
	}

	public static class SutVendor extends AbstractValueFieldData<String> {

		private static final long serialVersionUID = 1L;
	}

	public static class Version extends AbstractValueFieldData<String> {

		private static final long serialVersionUID = 1L;
	}
}
