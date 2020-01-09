/*
Copyright 2019, brf (Fraunhofer IOSB)
(v  05.12.2019)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package de.fraunhofer.iosb.tc_lib;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nato.ivct.commander.Factory;


/**
 * Tests for verifying if the IVCT-Version of the components are compatible
 */

public class IVCTVersionCheck {

  String testCaseIVCTVersion;
  String FactoryIVCtVersion = Factory.getVersion();
  String compatibleListKey;
  String compatibleListKeyFound = "notFound";
  String compatibleListValue;
  String compatibleListValueFound = "notFound";

  private static Logger logger = LoggerFactory.getLogger(IVCTVersionCheck.class);

  // Constructor
  // We get a IVCTVersion-String from the caler (TestEngine) generated by 'testCase.getIVCTVersion()'
  public IVCTVersionCheck(String _testCaseIVCTVersion) throws IVCTVersionCheckException {
    this.testCaseIVCTVersion = _testCaseIVCTVersion;
  }

  public void compare() throws IVCTVersionCheckException {

    boolean testresult = false;

    // if the Version-Number (String testCaseIVCTVersion) ist the same as the Factory IVCTVersion we are content
    if (testCaseIVCTVersion.equals(FactoryIVCtVersion)) {
      testresult = true;
      
    } else {
      logger.debug ("IVCTVersionCheck.compare: Versions differ: Factory: "+FactoryIVCtVersion+" TestCase: "+testCaseIVCTVersion);

      /**
       * if the Version differ, we have to test the testCaseIVCTVersion against a list
       * of compatible versions which we get from TC.exec in property-file compatibleVersions.properties
       */
      InputStream in = this.getClass().getResourceAsStream("/compatibleVersions.properties");      
      
      if (in == null) {
        throw new IVCTVersionCheckException("/compatibleVersions.properties could not be loaded ");
      }

      Properties compareProperties = new Properties();      
      
      try {
        compareProperties.load(in);
        
        // for debugging show the whole content of property - file 
        logger.debug("IVCTVersionCheck.compare: the whole content of TC.exec/../compatibleVersions.properties: "+compareProperties.toString());
        
        // we have to know each Key of the property - and it's value
        Set<Object> set = compareProperties.keySet();

        for (Object obj : set) {
          compatibleListKey = (String) obj;
          compatibleListValue = compareProperties.getProperty(compatibleListKey);         
          
          // if we find the same version-number marked with 'compatible' we are as well content and stop searching          
          if (testCaseIVCTVersion.equals(compatibleListKey) ) {
            compatibleListKeyFound = compatibleListKey;
            compatibleListValueFound = compatibleListValue;            
            
            if (compatibleListValue.equals("compatible")) {              
              testresult = true;
              break;
            }            
          }          
        }

      } catch (IOException ex) {
        throw new IVCTVersionCheckException("/compatibleVersions.properties could not be read ", ex);
      }
    }

    if (testresult) {
      logger.debug("IVCTVersionCheck testresult: found in TC.exec-compatibleVersions " + compatibleListKey + " Value: "
          + compatibleListValue);
      logger.debug("IVCTVersionCheck testresult: the versions are known as compatible: Factory: " + FactoryIVCtVersion
          + " TestCase: " + testCaseIVCTVersion);
      
    } else {
      logger.warn("IVCTVersionCheck testresult: Versions  Factory: " + FactoryIVCtVersion + " TestCase: " + testCaseIVCTVersion);
      //logger.warn("IVCTVersionCheck testresult: found in TC.exec-compatibleVersions "+compatibleListKey+" Value: "+ compatibleListValue);
      logger.warn("IVCTVersionCheck testresult: found in TC.exec-compatibleVersions "+compatibleListKeyFound+" Value: "+ compatibleListValueFound);
      throw new IVCTVersionCheckException("The IVCT-Versions of Testrunner and TestCase are not known as compatible ");
    }
  }
}
