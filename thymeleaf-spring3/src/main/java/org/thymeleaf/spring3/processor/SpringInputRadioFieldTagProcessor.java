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
package org.thymeleaf.spring3.processor;

import org.springframework.web.servlet.support.BindStatus;
import org.springframework.web.servlet.tags.form.SelectedValueComparatorWrapper;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.model.IElementAttributes;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring3.requestdata.RequestDataValueProcessorUtils;
import org.thymeleaf.standard.util.StandardProcessorUtils;
import org.unbescape.html.HtmlEscape;


/**
 * 
 * @author Daniel Fern&aacute;ndez
 *
 * @since 3.0.0
 *
 */
public final class SpringInputRadioFieldTagProcessor extends AbstractSpringFieldTagProcessor {


    public static final String RADIO_INPUT_TYPE_ATTR_VALUE = "radio";




    public SpringInputRadioFieldTagProcessor(final String dialectPrefix) {
        super(dialectPrefix, INPUT_TAG_NAME, TYPE_ATTR_NAME, new String[] { RADIO_INPUT_TYPE_ATTR_VALUE }, true);
    }




    @Override
    protected void doProcess(final ITemplateContext context,
                             final IProcessableElementTag tag,
                             final AttributeName attributeName, final String attributeValue,
                             final BindStatus bindStatus, final IElementTagStructureHandler structureHandler) {

        String name = bindStatus.getExpression();
        name = (name == null? "" : name);

        final String id = computeId(context, tag, name, true);

        final IElementAttributes attributes = tag.getAttributes();

        final String value = attributes.getValue(this.valueAttributeDefinition.getAttributeName());
        if (value == null) {
            throw new TemplateProcessingException(
                    "Attribute \"value\" is required in \"input(radio)\" tags");
        }

        final boolean checked =
                SelectedValueComparatorWrapper.isSelected(bindStatus, HtmlEscape.unescapeHtml(value));


        StandardProcessorUtils.setAttribute(attributes, this.idAttributeDefinition, ID_ATTR_NAME, id); // No need to escape: this comes from an existing 'id' or from a token
        StandardProcessorUtils.setAttribute(attributes, this.nameAttributeDefinition, NAME_ATTR_NAME, name); // No need to escape: this is a java-valid token
        StandardProcessorUtils.setAttribute(
                attributes, this.valueAttributeDefinition, VALUE_ATTR_NAME, RequestDataValueProcessorUtils.processFormFieldValue(context, name, value, "radio"));

        if (checked) {
            StandardProcessorUtils.setAttribute(attributes, this.checkedAttributeDefinition, CHECKED_ATTR_NAME, CHECKED_ATTR_NAME);
        } else {
            attributes.removeAttribute(this.checkedAttributeDefinition.getAttributeName());
        }

    }



}
