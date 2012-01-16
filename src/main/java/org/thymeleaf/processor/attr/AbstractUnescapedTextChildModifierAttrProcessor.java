/*
 * =============================================================================
 * 
 *   Copyright (c) 2011, The THYMELEAF team (http://www.thymeleaf.org)
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
package org.thymeleaf.processor.attr;

import java.util.List;

import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Tag;
import org.thymeleaf.exceptions.AttrProcessorException;
import org.thymeleaf.exceptions.TemplateEngineException;
import org.thymeleaf.processor.IAttributeNameProcessorMatcher;
import org.thymeleaf.templateparser.ITemplateParser;

/**
 * 
 * @author Daniel Fern&aacute;ndez
 * 
 * @since 1.0
 *
 */
public abstract class AbstractUnescapedTextChildModifierAttrProcessor 
        extends AbstractChildrenModifierAttrProcessor {
    
    
    
    
    public AbstractUnescapedTextChildModifierAttrProcessor(final IAttributeNameProcessorMatcher matcher) {
        super(matcher);
    }


    public AbstractUnescapedTextChildModifierAttrProcessor(final String attributeName) {
        super(attributeName);
    }

    
    
    
    @Override
    protected final List<Node> getModifiedChildren(
            final Arguments arguments, final Tag tag, final String attributeName) {
        
        final String text = getText(arguments, tag, attributeName);
        
        try {
            
            final Configuration configuration = arguments.getConfiguration();
            
            final ITemplateParser templateParser =
                    configuration.getTemplateModeHandler(
                            arguments.getTemplateResolution().getTemplateMode()).getTemplateParser();
            
            // Use the parser to obtain a DOM from the String
            final List<Node> fragNodes = templateParser.parseFragment(configuration, text);
            
            for (final Node node : fragNodes) {
                node.setSkippable(true);
            }

            return fragNodes;
            
        } catch (final TemplateEngineException e) {
            throw e;
        } catch (final Exception e) {
            throw new AttrProcessorException(
                    "An error happened during parsing of unescaped text: \"" + tag.getAttributeValue(attributeName) + "\"", e);
        }
        
    }

    
    protected abstract String getText(
            final Arguments arguments, final Tag tag, final String attributeName);
    
    
    
}
