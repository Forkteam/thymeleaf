/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.thymeleaf.spring4.view;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.RequestContextHolder;
import org.springframework.webflow.execution.View;


/**
 * <p>
 *   Subclass of {@link AjaxThymeleafView} for Spring WebFlow,
 *   designed for obtaining the fragments to be rendered via
 *   AJAX in the way needed by Spring WebFlow. 
 * </p>
 * <p>
 *   Most people will need to use this class instead of 
 *   {@link AjaxThymeleafView} if you are using Spring WebFlow.
 * </p>
 *
 * @author Daniel Fern&aacute;ndez
 *
 * @since 2.0.11
 *
 * @deprecated Deprecated in 3.0.0. Moved to the <tt>org.thymeleaf.spring4.webflow.view</tt> package. Will be removed
 *             from this package in Thymeleaf 3.1.
 *
 */
@Deprecated
public class FlowAjaxThymeleafView extends AjaxThymeleafView {

    


    
    public FlowAjaxThymeleafView() {
        super();
    }




    
    @Override
    @SuppressWarnings("rawtypes")
    protected Set<String> getRenderFragments(
            final Map model, final HttpServletRequest request, final HttpServletResponse response) {
        
        final RequestContext context = RequestContextHolder.getRequestContext();
        if (context == null) {
            return super.getRenderFragments(model, request, response);
        }
        
        final String[] fragments = (String[]) context.getFlashScope().get(View.RENDER_FRAGMENTS_ATTRIBUTE);
        if (fragments == null || fragments.length == 0) {
            return super.getRenderFragments(model, request, response);
        }
        if (fragments.length == 1) {
            return Collections.singleton(fragments[0]);
        }
        return new HashSet<String>(Arrays.asList(fragments));
        
    }
    
    

}
