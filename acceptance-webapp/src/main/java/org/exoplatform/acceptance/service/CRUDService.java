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
package org.exoplatform.acceptance.service;

/**
 * <p>CRUDService interface.</p>
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
public interface CRUDService<T> {

  /**
   * Retrieves an entity by its id.
   *
   * @param id must not be {@literal null}.
   * @return the entity with the given id
   * @throws java.lang.IllegalArgumentException                         if {@code id} is {@literal null}
   * @throws org.exoplatform.acceptance.service.EntityNotFoundException in case there is no entity with the given {@code id}
   */
  T findOne(String id) throws EntityNotFoundException;

  /**
   * Updates an existing entity. Use the returned instance for further operations as the save operation might have changed the
   * entity instance completely.
   *
   * @param entity the entity to update
   * @param <S>    a S object.
   * @return the saved entity
   * @throws org.exoplatform.acceptance.service.EntityNotFoundException in case there is no entity with the given {@code id}
   */
  <S extends T> S update(S entity) throws EntityNotFoundException;

  /**
   * Saves a given entity (creates it if it doesn't exist). Use the returned instance for further operations as the save
   * operation might have changed the entity instance completely.
   *
   * @param entity the entity to update
   * @param <S>    a S object.
   * @return the saved entity
   */
  <S extends T> S updateOrCreate(S entity);

  /**
   * Returns whether an entity with the given id exists.
   *
   * @param id must not be {@literal null}.
   * @return true if an entity with the given id exists, {@literal false} otherwise
   * @throws java.lang.IllegalArgumentException if {@code id} is {@literal null}
   */
  boolean exists(String id);

  /**
   * Returns all instances of the type.
   *
   * @return all entities
   */
  Iterable<T> findAll();

  /**
   * Returns all instances of the given page. Pages are zero indexed, thus providing 0 for {@code page} will return the first
   * page.
   *
   * @param page a int.
   * @param size a int.
   * @return entities for the given page
   */
  Iterable<T> findAll(int page, int size);

  /**
   * Returns the number of entities available.
   *
   * @return the number of entities
   */
  long count();

  /**
   * Deletes the entity with the given id.
   *
   * @param id must not be {@literal null}.
   * @throws java.lang.IllegalArgumentException                         in case the given {@code id} is {@literal null}
   * @throws org.exoplatform.acceptance.service.EntityNotFoundException in case there is no entity with the given {@code id}
   */
  void delete(String id) throws EntityNotFoundException;

  /**
   * Deletes all entities managed by the repository.
   */
  void deleteAll();

}
