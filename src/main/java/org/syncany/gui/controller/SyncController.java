/*
 * Syncany, www.syncany.org
 * Copyright (C) 2011-2014 Philipp C. Heckel <philipp.heckel@gmail.com> 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.syncany.gui.controller;

import java.util.logging.Logger;

import org.syncany.config.LocalEventBus;
import org.syncany.gui.Launcher;
import org.syncany.operations.daemon.messages.ListWatchesManagementRequest;
import org.syncany.operations.daemon.messages.ListWatchesManagementResponse;
import org.syncany.operations.daemon.messages.StatusEndSyncExternalEvent;
import org.syncany.operations.daemon.messages.StatusFolderResponse;
import org.syncany.operations.daemon.messages.StatusStartSyncExternalEvent;
import org.syncany.operations.daemon.messages.api.ExternalEvent;
import org.syncany.operations.daemon.messages.api.Response;

import com.google.common.eventbus.Subscribe;

/**
 * @author vwiencek
 *
 */
public class SyncController {
	private static final Logger logger = Logger.getLogger(SyncController.class.getSimpleName());

	private static SyncController instance = null;
	
	public static SyncController getInstance() {
		if (instance == null){
			instance = new SyncController();
			LocalEventBus.getInstance().register(instance);	
		}
		return instance;
	}
	
	public void restoreWatchedFolders() {
		logger.info("Restoring watched folders");
		LocalEventBus.getInstance().post(new ListWatchesManagementRequest());
	}

	@Subscribe
	public void handleResponse(Response message){
		if (message instanceof ListWatchesManagementResponse){
			Launcher.window.getTray().updateWatchedFolders(((ListWatchesManagementResponse)message).getWatches());
		}
		else if (message instanceof StatusFolderResponse){
			Launcher.window.getTray().updateWatchedFoldersStatus(((StatusFolderResponse)message).getResult());
		}
	}
	
	@Subscribe
	public void handleExternalEvent(ExternalEvent message) {
		if (message instanceof StatusStartSyncExternalEvent){
			Launcher.window.getTray().makeSystemTrayStartSync();
		}
		else if (message instanceof StatusEndSyncExternalEvent){
			Launcher.window.getTray().makeSystemTrayStopSync();
		}
	}

	@Subscribe
	public void updateInterface(Object event) {
		
	}
}