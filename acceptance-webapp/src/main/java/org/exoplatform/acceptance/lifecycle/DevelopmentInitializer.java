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
package org.exoplatform.acceptance.lifecycle;

import org.exoplatform.acceptance.annotation.Development;
import org.exoplatform.acceptance.service.utils.DevDataLoaderService;

import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Bootstrap service is used to initialize the application in a development environment
 *
 * @author Arnaud Héritier ( aheritier@exoplatform.com )
 * @since 2.0.0
 */
@Named
@Development
public class DevelopmentInitializer extends ProductionInitializer implements ApplicationListener<ContextRefreshedEvent> {
  /**
   * Constant <code>LOGGER</code>
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(DevelopmentInitializer.class);
  @Inject
  private DevDataLoaderService devDataLoaderService;

  /**
   * {@inheritDoc}
   * Handle an application event.
   */
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    super.onApplicationEvent(event);
    ApplicationContext context = (ApplicationContext) event.getSource();
    // Load data in the root context only
    if (context.getParent() == null) {
      devDataLoaderService.initializeData();
    }
    LOGGER.info("Data are ready for development.");
  }

}
