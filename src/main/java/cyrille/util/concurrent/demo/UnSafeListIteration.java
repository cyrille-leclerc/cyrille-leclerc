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
package cyrille.util.concurrent.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:cyrille.leclerc@pobox.com">Cyrille Le Clerc</a>
 */
@SuppressWarnings({"unchecked", "unused"})
public class UnSafeListIteration {

    List values = Collections.synchronizedList(new ArrayList());

    /**
     * NOT THREAD SAFE iteration on values ! Problem would be the same with java 5 enhanced "for loop".
     */
    public void doJob() {
        Iterator valuesIterator = this.values.iterator();
        while (valuesIterator.hasNext()) {
            Object value = valuesIterator.next();
            // do job ...
        }
    }

    public void add(Object value) {
        this.values.add(value);
    }
}
