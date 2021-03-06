/*
 * Copyright (C) 2011-2014 eXo Platform SAS.
 *
 * This file is part of eXo Acceptance Webapp.
 *
 * eXo Acceptance Webapp is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * eXo Acceptance Webapp software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with eXo Acceptance Webapp; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see <http://www.gnu.org/licenses/>.
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
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
@Controller
@RequestMapping(value = "admin/vcs/repository", produces = MediaType.APPLICATION_JSON_VALUE)
public class VCSRepositoryCRUDController extends CRUDController<VCSRepository, String> {

  @Inject
  private VCSRepositoryService vcsRepositoryService;

  /**
   * <p>createListItemDTOs.</p>
   *
   * @param repositories a {@link java.lang.Iterable} object.
   * @return a {@link java.lang.Iterable} object.
   */
  private static Iterable<VCSRepository> createListItemDTOs(Iterable<VCSRepository> repositories) {
    List<VCSRepository> dtos = new ArrayList<>();
    for (VCSRepository repository : repositories) {
      dtos.add(createListItemDTO(repository));
    }
    return dtos;
  }

  /**
   * <p>createListItemDTO.</p>
   *
   * @param repository a {@link org.exoplatform.acceptance.model.vcs.VCSRepository} object.
   * @return a {@link org.exoplatform.acceptance.model.vcs.VCSRepository} object.
   */
  private static VCSRepository createListItemDTO(VCSRepository repository) {
    VCSRepositoryListItemDTO dto = new VCSRepositoryListItemDTO(repository.getName());
    dto.setId(repository.getId());
    return dto;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected CRUDService<VCSRepository> getCRUDService() {
    return vcsRepositoryService;
  }

  /**
   * {@inheritDoc}
   * Get a (potentially paginated) list of objects
   */
  @Override
  public Iterable<VCSRepository> getObjects(
      @RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "-1") int limit) {
    // We filter list result to not expose a list of all our repositories
    return createListItemDTOs(super.getObjects(offset, limit));
  }

  /**
   * <p>setVCSRepositoryService.</p>
   *
   * @param vcsRepositoryService a {@link org.exoplatform.acceptance.service.vcs.VCSRepositoryService} object.
   */
  void setVCSRepositoryService(VCSRepositoryService vcsRepositoryService) {
    this.vcsRepositoryService = vcsRepositoryService;
  }

  @JsonIgnoreProperties({"remoteRepositories", "tags", "branches", "references"})
  public final static class VCSRepositoryListItemDTO extends VCSRepository {

    private VCSRepositoryListItemDTO(@NotNull String name) {
      super(name);
    }

  }
}
