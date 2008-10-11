/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.extensions.validator.baseval.metadata.transformer;

import org.apache.myfaces.extensions.validator.baseval.annotation.JoinValidation;
import org.apache.myfaces.extensions.validator.baseval.annotation.extractor.DefaultPropertyScanningMetaDataExtractor;
import org.apache.myfaces.extensions.validator.core.metadata.MetaDataEntry;
import org.apache.myfaces.extensions.validator.core.metadata.PropertySourceInformationKeys;
import org.apache.myfaces.extensions.validator.core.metadata.extractor.MetaDataExtractor;
import org.apache.myfaces.extensions.validator.core.metadata.transformer.MetaDataTransformer;
import org.apache.myfaces.extensions.validator.core.metadata.transformer.AbstractMetaDataTransformer;
import org.apache.myfaces.extensions.validator.core.validation.strategy.ValidationStrategy;
import org.apache.myfaces.extensions.validator.core.el.ValueBindingExpression;
import org.apache.myfaces.extensions.validator.util.ExtValUtils;

import javax.faces.context.FacesContext;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gerhard Petracek
 * @since 1.x.1
 */
public class JoinMetaDataTransformer  extends AbstractMetaDataTransformer
{
    protected Map<String, Object> convert(MetaDataEntry metaDataEntry)
    {
        MetaDataExtractor extractor = new DefaultPropertyScanningMetaDataExtractor();

        String[] targetExpressions = metaDataEntry.getValue(JoinValidation.class).value();

        ValidationStrategy validationStrategy;
        MetaDataTransformer metaDataTransformer;

        Map<String, Object> results = new HashMap<String, Object>();

        for (String targetExpression : targetExpressions)
        {
            targetExpression = createValidBinding(metaDataEntry, targetExpression);

            for (MetaDataEntry entry : extractor.extract(FacesContext.getCurrentInstance(),
                                                                    targetExpression).getMetaDataEntries())
            {
                validationStrategy = ExtValUtils.getValidationStrategyForMetaData(entry.getKey());

                metaDataTransformer = ExtValUtils.getMetaDataTransformerForValidationStrategy(validationStrategy);

                if (metaDataTransformer != null)
                {
                    results.putAll(metaDataTransformer.convertMetaData(entry));
                }
            }
        }
        return results;
    }

    private String createValidBinding(MetaDataEntry metaDataEntry, String targetExpression)
    {
        if(ExtValUtils.getELHelper().isELTerm(targetExpression))
        {
            return targetExpression;
        }
        
        ValueBindingExpression baseExpression = new ValueBindingExpression(
            metaDataEntry.getProperty(PropertySourceInformationKeys.VALUE_BINDING_EXPRESSION, String.class));
        return ValueBindingExpression.replaceOrAddProperty(baseExpression, targetExpression).getExpressionString();
    }
}
