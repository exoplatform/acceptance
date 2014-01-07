/*
 * Copyright (C) 2011-2014 eXo Platform SAS.
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
package org.exoplatform.acceptance.rest.administration.vcs;

import org.exoplatform.acceptance.model.vcs.VCSRepository;
import org.exoplatform.acceptance.rest.administration.CRUDController;
import org.exoplatform.acceptance.service.CRUDService;
import org.exoplatform.acceptance.service.vcs.VCSRepositoryService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller to manage REST services for repositories
 */
@Controller
@RequestMapping(value = "admin/vcs/repository", produces = MediaType.APPLICATION_JSON_VALUE)
public class VCSRepositoryCRUDController extends CRUDController<VCSRepository, String> {

  @Inject
  private VCSRepositoryService vcsRepositoryService;

  static private VCSRepository createListItemDTO(VCSRepository repository) {
    VCSRepositoryListItemDTO dto = new VCSRepositoryListItemDTO(repository.getName());
    dto.setId(repository.getId());
    return dto;
  }

  static private Iterable<VCSRepository> createListItemDTOs(Iterable<VCSRepository> repositories) {
    List<VCSRepository> dtos = new ArrayList<>();
    for (VCSRepository repository : repositories) {
      dtos.add(createListItemDTO(repository));
    }
    return dtos;
  }

  @Override
  protected CRUDService<VCSRepository> getCRUDService() {
    return vcsRepositoryService;
  }

  void setVCSRepositoryService(VCSRepositoryService vcsRepositoryService) {
    this.vcsRepositoryService = vcsRepositoryService;
  }

  /**
   * Get a (potentially paginated) list of objects
   *
   * @param offset the page number to get (0 by default is the first page)
   * @param limit  the maximum number of entries in the page of results ( > 0 to activate pagination, -1 thus everything by default )
   * @return a list of objects
   */
  @Override
  public Iterable<VCSRepository> getObjects(
      @RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "-1") int limit) {
    // We filter list result to not expose a list of all our repositories
    return createListItemDTOs(super.getObjects(offset, limit));
  }

  @JsonIgnoreProperties({"remoteRepositories"})
  public static class VCSRepositoryListItemDTO extends VCSRepository {

    private VCSRepositoryListItemDTO(@NotNull String name) {
      super(name);
    }

  }
}
