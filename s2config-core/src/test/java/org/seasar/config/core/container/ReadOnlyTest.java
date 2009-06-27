/*
 *  Copyright 2009 happy_ryo.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package org.seasar.config.core.container;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


import org.junit.runner.RunWith;
import org.seasar.config.core.config.ApplicationConfig;
import org.seasar.framework.unit.Seasar2;

/**
 *
 * @author happy_ryo
 */
@RunWith(Seasar2.class)
public class ReadOnlyTest {
	private ConfigContainer configContainer;
	private ApplicationConfig applicationConfig;

    /**
     * @ConfigKyeにTrueを選択した場合の動作確認用
     */
	@Test
    public void testReadOnlyTrue() {
        configContainer.setConfigName("test");
        configContainer.loadToBeans();
        assertEquals("read", applicationConfig.readOnlyTrue);
	}
}