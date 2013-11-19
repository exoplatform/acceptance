/*
 * Copyright (C) 2011-2013 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.exoplatform.acceptance.model.vcs;

import org.exoplatform.acceptance.model.StorableObject;

import java.io.File;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 */
@Document(collection = "dvcsfilesets")
public class DVCSFileSet extends StorableObject {
  /**
   * Local base directory where the clone is created
   */
  @NotNull
  private File baseDir;
  /**
   * Identifier of the DVCSRepositoryObject from which this local copy was created
   */
  @NotNull
  private String repositoryId;

  public DVCSFileSet(@NotNull String name, @NotNull File baseDir, @NotNull String repositoryId) {
    super(name);
    this.baseDir = baseDir;
    this.repositoryId = repositoryId;
  }

  public File getBaseDir() {
    return baseDir;
  }

  public void setBaseDir(File baseDir) {
    this.baseDir = baseDir;
  }

  public String getRepositoryId() {
    return repositoryId;
  }
}
