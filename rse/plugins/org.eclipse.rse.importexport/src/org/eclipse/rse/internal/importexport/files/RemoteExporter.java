/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 * Martin Oberhuber (Wind River) - [183824] Forward SystemMessageException from IRemoteFileSubsystem
 *******************************************************************************/
package org.eclipse.rse.internal.importexport.files;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.rse.core.model.IHost;
import org.eclipse.rse.services.clientserver.SystemEncodingUtil;
import org.eclipse.rse.services.clientserver.messages.SystemMessageException;
import org.eclipse.rse.subsystems.files.core.model.RemoteFileUtility;
import org.eclipse.rse.subsystems.files.core.subsystems.IRemoteFileSubSystem;

// Similar to org.eclipse.ui.wizards.datatransfer.FileSystemExporter
/**
 * Helper class for exporting resources to the file system.
 */
class RemoteExporter {
	private IHost _host = null;

	/**
	 *  Create an instance of this class.  Use this constructor if you wish to
	 *  use an host object */
	public RemoteExporter(IHost s) {
		super();
		_host = s;
	}

	/**
	 *  Create an instance of this class.
	 */
	public RemoteExporter() {
		super();
	}

	/**
	 *  Creates the specified file system directory at <code>destinationPath</code>.
	 *  This creates a new file system directory.
	 */
	public void createFolder(IPath destinationPath) {
		// IFS: use IFSJaveFile object if necessary
		if (_host != null)
			new UniFilePlus(Utilities.getIRemoteFile(_host, destinationPath.toString())).mkdir();
		else
			new File(destinationPath.toOSString()).mkdir();
	}

	/**
	 *  Writes the passed resource to the specified location recursively
	 * @throws SystemMessageException TODO
	 */
	public void write(IResource resource, IPath destinationPath) throws IOException, CoreException, SystemMessageException {
		if (resource.getType() == IResource.FILE)
			writeFile((IFile) resource, destinationPath);
		else
			writeChildren((IContainer) resource, destinationPath);
	}

	/**
	 *  Exports the passed container's children
	 * @throws SystemMessageException TODO
	 */
	protected void writeChildren(IContainer folder, IPath destinationPath) throws IOException, CoreException, SystemMessageException {
		if (folder.isAccessible()) {
			IResource[] children = folder.members();
			for (int i = 0; i < children.length; i++) {
				IResource child = children[i];
				writeResource(child, destinationPath.append(child.getName()));
			}
		}
	}

	/**
	 *  Writes the passed file resource to the specified destination on the remote
	 *  file system
	 * @throws SystemMessageException TODO
	 */
	protected void writeFile(IFile file, IPath destinationPath) throws IOException, CoreException, SystemMessageException {
		IRemoteFileSubSystem rfss = RemoteFileUtility.getFileSubSystem(_host);
		String dest = destinationPath.toString();
		char sep = rfss.getSeparatorChar();
		if (sep != '/')
		{
			// for windows
			dest = dest.replace('/', sep);
		}
		rfss.upload(file.getLocation().makeAbsolute().toOSString(), SystemEncodingUtil.ENCODING_UTF_8, dest, System.getProperty("file.encoding"), new NullProgressMonitor()); //$NON-NLS-1$
	}

	/**
	 *  Writes the passed resource to the specified location recursively
	 * @throws SystemMessageException TODO
	 */
	protected void writeResource(IResource resource, IPath destinationPath) throws IOException, CoreException, SystemMessageException {
		if (resource.getType() == IResource.FILE)
			writeFile((IFile) resource, destinationPath);
		else {
			createFolder(destinationPath);
			writeChildren((IContainer) resource, destinationPath);
		}
	}
}
