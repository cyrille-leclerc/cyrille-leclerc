/*
 * Copyright 2002-2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cyrille.lang;

import org.apache.commons.lang.Validate;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
public class AssertTest extends TestCase {

    public void testAssert() throws Exception {
        String myParam = "";
        assert myParam == null;
    }

    public void testAssert2() throws Exception {

        String myParam = "";
        assert myParam == null : "assert 2";
        Validate.notNull(myParam, 1 + 1 + "mon message");

    }
}
