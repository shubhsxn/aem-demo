/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.samples.demos.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.samples.demos.core.services.MyTestSampleMultiConfigService;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet that writes some sample content into the response. It is mounted for
 * all resources of a specific Sling resource type. The
 * {@link SlingSafeMethodsServlet} shall be used for HTTP methods that are
 * idempotent. For write operations use the {@link SlingAllMethodsServlet}.
 */
@Component(service = Servlet.class, property = {
		Constants.SERVICE_DESCRIPTION + "=MySampleMultiConfigServiceConsumer Demo Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=" + "/bin/mytestsamplemulticonfigdata" })
public class MySampleMultiConfigServiceConsumer extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(MySampleMultiConfigServiceConsumer.class);
	private List<MyTestSampleMultiConfigService> configurationList;
	// @Reference
	// MyTestSampleMultiConfigService myTestSampleMultiConfigService;

	/**
	 * Executed on Configuration Add event
	 * 
	 * @param config
	 *            New configuration for factory
	 */
	@Reference(name = "configurationFactory", cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	protected synchronized void bindConfigurationFactory(final MyTestSampleMultiConfigService config) {
		LOGGER.info("[Raghava]--> bindConfigurationFactory: " + config.getMemberName());
		if (configurationList == null) {
			configurationList = new ArrayList<MyTestSampleMultiConfigService>();
		}
		configurationList.add(config);
	}

	/**
	 * Executed on Configuration Remove event
	 * 
	 * @param config
	 *            New configuration for factory
	 */
	protected synchronized void unbindConfigurationFactory(final MyTestSampleMultiConfigService config) {
		LOGGER.info("unbindConfigurationFactory: " + config.getMemberName());
		configurationList.remove(config);
	}

	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		if(configurationList != null) {
		resp.getWriter().println("Total Items = " + configurationList.size());
		for (MyTestSampleMultiConfigService myTestSampleMultiConfigService : configurationList) {
			resp.getWriter().println("Name = " + myTestSampleMultiConfigService.getMemberName()+" " +"place = " + myTestSampleMultiConfigService.getMemberPlace()+" " +"PIN = " + myTestSampleMultiConfigService.getMemberPIN());
		}
		} else {
			resp.getWriter().println("Zero Items");
		}
	}
}
