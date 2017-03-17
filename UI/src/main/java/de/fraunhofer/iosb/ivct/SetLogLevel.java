/*
Copyright 2016, Johannes Mulder (Fraunhofer IOSB)

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

package de.fraunhofer.iosb.ivct;

public class SetLogLevel implements Command {
	final String logLevel;
	final IVCTcommander ivctCommander;
	final int counter;

	SetLogLevel (final String logLevel, IVCTcommander ivctCommander, final int counter) {
		this.logLevel = logLevel;
		this.ivctCommander = ivctCommander;
		this.counter = counter;
	}

	public void execute() {
		String setLogLevelString = IVCTcommander.printJson("setLogLevel", this.counter, "logLevelId", logLevel);
		this.ivctCommander.sendToJms(setLogLevelString);
	}
}