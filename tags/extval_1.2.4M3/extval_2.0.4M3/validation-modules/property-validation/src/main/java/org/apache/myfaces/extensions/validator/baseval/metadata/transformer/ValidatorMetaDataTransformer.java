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

import org.apache.myfaces.extensions.validator.baseval.annotation.Validator;
import org.apache.myfaces.extensions.validator.core.metadata.CommonMetaDataKeys;
import org.apache.myfaces.extensions.validator.core.metadata.MetaDataEntry;
import org.apache.myfaces.extensions.validator.internal.UsageInformation;
import org.apache.myfaces.extensions.validator.internal.UsageCategory;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Gerhard Petracek
 * @since 1.x.1
 */
@UsageInformation(UsageCategory.INTERNAL)
public class ValidatorMetaDataTransformer  extends AbstractValidationParameterAwareTransformer
{
    protected Map<String, Object> transformMetaData(MetaDataEntry metaDataEntry)
    {
        Map<String, Object> results = new HashMap<String, Object>();
        Validator annotation = metaDataEntry.getValue(Validator.class);

        Class[] validators = annotation.value();

        List<String> value = new ArrayList<String>();

        for(Class currentClass : validators)
        {
            if(currentClass.getSimpleName().toLowerCase().contains(CommonMetaDataKeys.EMAIL))
            {
                value.add(CommonMetaDataKeys.EMAIL);
            }
            value.add(currentClass.getName());
        }

        results.put(CommonMetaDataKeys.CUSTOM, value);

        return results;
    }
}
