/*
 * Copyright (c) 2014-2015 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2018, 2019 Eclipse Krazo committers and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package org.eclipse.krazo.ext.pebble;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import org.eclipse.krazo.engine.ViewEngineBase;
import org.eclipse.krazo.engine.ViewEngineConfig;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.engine.ViewEngine;
import jakarta.mvc.engine.ViewEngineContext;
import jakarta.mvc.engine.ViewEngineException;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @see <a href="https://pebbletemplates.io/">Pebble</a>
 */
@ApplicationScoped
@Priority(ViewEngine.PRIORITY_FRAMEWORK)
public class PebbleViewEngine extends ViewEngineBase {

    private PebbleEngine pebbleEngine;

    @SuppressWarnings("unused")
    protected PebbleViewEngine() {
        // needs a default constructor to make it proxyable
    }

    @Inject
    public PebbleViewEngine(@ViewEngineConfig PebbleEngine pebbleEngine) {
        this.pebbleEngine = pebbleEngine;
    }

    @Override
    public boolean supports(String view) {
        return view.endsWith(".peb");
    }

    @Override
    public void processView(ViewEngineContext context) throws ViewEngineException {

        Charset charset = resolveCharsetAndSetContentType(context);

        try (Writer writer = new OutputStreamWriter(context.getOutputStream(), charset)) {

            PebbleTemplate template = pebbleEngine.getTemplate(resolveView(context));

            Map<String, Object> model = new HashMap<>(context.getModels().asMap());
            model.put("request", context.getRequest(HttpServletRequest.class));

            template.evaluate(writer, model);

        } catch (PebbleException | IOException ex) {
            throw new ViewEngineException(String.format("Could not process view %s.", context.getView()), ex);
        }
    }
}
