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
package org.thymeleaf.spring4.expression;

import java.util.Map;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.spring4.requestdata.RequestDataValueProcessorUtils;

/**
 * <p>
 *   Expression object that offers the functionality of Spring MVC's <tt>RequestDataValueProcessor</tt> for
 *   performing last-moment modifications to request data values such as URLs or form contents in order to
 *   enable mechanism such as CSRF protection.
 * </p>
 * <p>
 *   Methods in this class correspond to the methods in Spring MVC's
 *   <tt>org.springframework.web.servlet.support.RequestDataValueProcessor</tt>.
 * </p>
 * <p>
 *   Using this expression object is not needed in most scenarios, as its functionality will be automatically
 *   applied by <tt>th:href</tt>,  <tt>th:src</tt>,  <tt>th:action</tt>, <tt>th:value</tt>, <tt>th:method</tt>
 *   and <tt>th:field</tt>. But sometimes there is a need to manually apply these behaviours when e.g. creating
 *   and using URLs outside attributes such as <tt>th:href</tt> or <tt>th:src</tt>.
 * </p>
 * <p>
 *   An example of such scenario would be using an URL as a parameter in a message expression:
 * </p>
 * <code>
 *   msg.knowmore=Click &lt;a href="{0}"&gt;here&lt;/a&gt; if you want to know more.
 * </code>
 * <p>
 *   And then in template code:
 * </p>
 * <code>
 *   &lt;p th:with="morelink=@{/detail/more}"
 *      th:utext="#{msg.knowmore(${#requestdatavalues.url(morelink)})}"&gt;
 * </code>
 *
 * @author Daniel Fern&aacute;ndez
 * 
 * @since 3.0.0
 *
 */
public class RequestDataValues {

    private ITemplateContext context;


    public RequestDataValues(final ITemplateContext context) {
        super();
        this.context = context;
    }



    public String action(final String action, final String httpMethod) {
        return RequestDataValueProcessorUtils.processAction(this.context, action, httpMethod);
    }


    public String url(final String url) {
        return RequestDataValueProcessorUtils.processUrl(this.context, url);
    }


    public String formFieldValue(final String name, final String value, final String type) {
        return RequestDataValueProcessorUtils.processFormFieldValue(this.context, name, value, type);
    }


    public Map<String,String> extraHiddenFields() {
        return RequestDataValueProcessorUtils.getExtraHiddenFields(this.context);
    }


}
