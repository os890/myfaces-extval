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
package org.apache.myfaces.extensions.validator.test.trinidad;

import org.apache.myfaces.extensions.validator.test.base.AbstractExValTestCase;
import org.apache.myfaces.extensions.validator.trinidad.startup.TrinidadModuleStartupListener;

/**
 * 
 * @author Rudy De Busscher
 * since v4
 *
 */
public abstract class AbstractTrinidadSupportTestCase extends AbstractExValTestCase
{

    public AbstractTrinidadSupportTestCase(String name)
    {
        super(name);
    }

    @Override
    protected void invokeStartupListeners()
    {
        new TrinidadModuleStartupListener(){
            private static final long serialVersionUID = 423076920926752646L;

            @Override
            protected void init()
            {
                super.init();
            }
        }.init();
        
    }

}