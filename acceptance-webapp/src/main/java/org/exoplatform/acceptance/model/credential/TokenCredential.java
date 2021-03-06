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
package org.exoplatform.acceptance.model.credential;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import javax.validation.constraints.NotNull;

/**
 * <p>TokenCredential class.</p>
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
@JsonTypeName("TOKEN")
public class TokenCredential extends Credential {
  @NotNull
  private String token;

  /**
   * <p>Constructor for TokenCredential.</p>
   *
   * @param name  a {@link java.lang.String} object.
   * @param token a {@link java.lang.String} object.
   */
  @JsonCreator
  public TokenCredential(
      @NotNull @JsonProperty("name") String name,
      @NotNull @JsonProperty("token") String token) {
    super(Type.TOKEN, name);
    this.token = token;
  }

  /**
   * <p>Getter for the field <code>token</code>.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getToken() {
    return token;
  }

  /**
   * <p>Setter for the field <code>token</code>.</p>
   *
   * @param token a {@link java.lang.String} object.
   */
  public void setToken(String token) {
    this.token = token;
  }

}
