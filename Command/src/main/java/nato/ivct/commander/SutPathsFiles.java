package nato.ivct.commander;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SutPathsFiles {
	/**
	 * Get the path of the folder where SUTs are stored
	 * 
	 * @return path to SUTs home folder
	 */
	public String getSutsHomePath() {
		// If property is not set, do not have any access to any SUTs
		if (Factory.props.containsKey(Factory.IVCT_SUT_HOME_ID) == false) {
			return null;
		}
		return Factory.props.getProperty(Factory.IVCT_SUT_HOME_ID);
	}

	/**
	 * Get the names of SUTs available
	 * 
	 * @return set of sut names
	 */
	public List<String> getSuts() {
		List<String> sutNames = new ArrayList<String>();

		String sutsHomePath = getSutsHomePath();
		// Do not have any access to any SUTs
		if (sutsHomePath == null) {
			return sutNames;
		}

		File dir = new File(sutsHomePath);
		if (dir.exists() == false || dir.isDirectory() == false) {
			return sutNames;
		}
		File[] filesList = dir.listFiles();
		for (File file : filesList) {
			if (file.isDirectory()) {
				sutNames.add(file.getName());
			}
		}
		return sutNames;
	}

	/**
	 * Get the path where the report file(s) of the requested SUT are located
	 * @param sutId the desired SUT name
	 * @return path where report file(s) are located
	 */
	public String getReportPath(final String sutId) {
		String sutsHomePath = getSutsHomePath();
		// Do not have any access to any SUTs
		if (sutsHomePath == null) {
			return null;
		}

		return sutsHomePath + "/" + sutId + "/Reports";
	}

	/**
	 * Get the names of report files without path prefix for the requested SUT
	 * 
	 * @param sutId the ID of the desired SUT
	 * @return a set of report file names
	 */
	public List<String> getSutReportFileNames(final String sutId) {
		return getSutReportFileNames(sutId, false);
	}

	/**
	 * Get the names of report files with/without path prefix depending on withPath parameter
	 * 
	 * @param sutId the ID of the desired SUT
	 * @param withPath whether the path name is prefixed to report file name
	 * @return a set of report file names
	 */
	public List<String> getSutReportFileNames(final String sutId, final boolean withPath) {
		List<String> reportFileNames = new ArrayList<String>();
		String path = getReportPath(sutId);
		final File folder = new File(path);
		if (folder.exists() == false || folder.isDirectory() == false) {
			return reportFileNames;
		}
		reportFileNames = listReportFilesForFolder(folder, path, withPath);
		return reportFileNames;
	}


	/**
	 * Get report file names with/without path prefix depending on withPath parameter
	 * 
	 * @param folderName report file
	 * @param path folder where to get the log files from
	 * @param withPath whether the path name is prefixed to report file name
	 * @return set of report file names
	 */
	private List<String> listReportFilesForFolder(final File folderName, final String path, final boolean withPath) {
		List<String> reportFileNames = new ArrayList<String>();
		if (folderName == null) {
			return reportFileNames;
		}
	    for (final File fileEntry : folderName.listFiles()) {
	        if (fileEntry.isFile()) {
	        	String s = fileEntry.getName();
	        	int len = s.length();
	        	if (s.substring(len - 4, len).equals(".txt")) {
	        		if (withPath) {
	        			reportFileNames.add(path + "/" + fileEntry.getName());
	        		} else {
	        			reportFileNames.add(fileEntry.getName());
	        		}
	        	}
	        }
	    }
		return reportFileNames;
	}

	/**
	 * Get the path where the TcParam file(s) are located
	 * @param sutId the desired SUT ID
	 * @return path where TcParam file(s) are located
	 */
	public String getTcParamPath(final String sutId, final String badgeName) {
		String sutsHomePath = getSutsHomePath();
		// Do not have any access to any SUTs
		if (sutsHomePath == null) {
			return null;
		}

		return sutsHomePath + "/" + sutId + "/" + badgeName;
	}

	/**
	 * Get the names of the TcParam files available without path prefix.
	 * Currently only one, may change in the future.
	 * 
	 * @param sutId the desired SUT ID
	 * @param badgeName the name of the badge under consideration
	 * @return set of TcParam file names
	 */
	public List<String> getTcParamFileNames(final String sutId, final String badgeName) {
		return getTcParamFileNames(sutId, badgeName, false);
	}

	/**
	 * Get the names of the TcParam files available with/without path prefix depending
	 * on withPath parameter.
	 * Currently only one, may change in the future
	 *
	 * @param sutId the desired SUT ID
	 * @param badgeId the name of the badge under consideration
	 * @return set of TcParam file names
	 */
	public List<String> getTcParamFileNames(final String sutId, final String badgeId, final boolean withPath) {
		List<String> tcParamFileNames = new ArrayList<String>();

		String folderName = getTcParamPath(sutId, badgeId);
		if (folderName == null) {
			return tcParamFileNames;
		}
		final File folder = new File(folderName);
		if (folder.exists() == false || folder.isDirectory() == false) {
			return tcParamFileNames;
		}
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isFile()) {
	        	String s = fileEntry.getName();
//              May have multiple TcParam file in the future
//	        	if (s.substring(len - 5, len).equals(".json")) {
	        	if (s.equals("TcParam.json")) {
                    if (withPath) {
                        tcParamFileNames.add(folderName + "/" + fileEntry.getName());
                    } else {
                        tcParamFileNames.add(fileEntry.getName());
                    }
	        	}
	        }
	    }
		return tcParamFileNames;
	}

	/**
	 * Get the names of log files without path prefix
	 * 
	 * @param sutId the ID of the SUT
	 * @param badgeId the name of the badge under consideration
	 * @return a set of log file names
	 */
	public List<String> getSutLogFileNames(final String sutId, final String badgeId) {
		return getSutLogFileNames(sutId, badgeId, false);
	}

	/**
	 * Get the names of log files with/without path prefix depending on withPath parameter
	 * 
	 * @param sutId the ID of the SUT
	 * @param badgeId the name of the badge under consideration
	 * @param withPath whether the path name is prefixed to log file name
	 * @return a set of log file names
	 */
	public List<String> getSutLogFileNames(final String sutId, final String badgeId, final boolean withPath) {
		List<String> logFileNames = new ArrayList<String>();
		String path = getSutLogPathName(sutId, badgeId);
		final File folder = new File(path);
		if (folder.exists() == false || folder.isDirectory() == false) {
			return logFileNames;
		}
		logFileNames = listLogFilesForFolder(folder, path, withPath);
		return logFileNames;
	}

	/**
	 * Get the path where the log files are located
	 * 
	 * @param sutId the ID of the SUT
	 * @param badgeId the name of the badge under consideration
	 * @return path where logfiles are located or null
	 */
	public String getSutLogPathName(final String sutId, final String badgeId) {
		String sutsHome = getSutsHomePath();
		if (sutsHome == null) {
			return null;
		}
		return sutsHome + "/" + sutId + "/" + badgeId + "/Logs";
	}

	/**
	 * Get log file names with/without path prefix depending on withPath parameter
	 * 
	 * @param folderName log file
	 * @param path folder where to get the log files from
	 * @param withPath whether the path name is prefixed to log file name
	 * @return set of logfile names
	 */
	private List<String> listLogFilesForFolder(final File folderName, final String path, final boolean withPath) {
		List<String> logFileNames = new ArrayList<String>();
		if (folderName == null) {
			return logFileNames;
		}
	    for (final File fileEntry : folderName.listFiles()) {
	        if (fileEntry.isFile()) {
	        	String s = fileEntry.getName();
	        	int len = s.length();
	        	if (s.substring(len - 4, len).equals(".log")) {
	        		if (withPath) {
	        			logFileNames.add(path + "/" + fileEntry.getName());
	        		} else {
	        			logFileNames.add(fileEntry.getName());
	        		}
	        	}
	        }
	    }
		return logFileNames;
	}
}