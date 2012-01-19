/*
 * Copyright 2008-2009 Xebia and the original author or authors.
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
package cyrille.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;

public class ComposedFutureTest {

    public void test() throws Exception {
        final ServiceA serviceA = new ServiceA();
        final ServiceB serviceB = new ServiceB();
        final ExecutorService executorA = Executors.newFixedThreadPool(2);
        final ExecutorService executorB = Executors.newFixedThreadPool(2);

        final InputA inputA = new InputA();

        final Future<ResultA> futureResultA = executorA.submit(new Callable<ResultA>() {
            @Override
            public ResultA call() throws Exception {
                return serviceA.work(inputA);
            }
        });

        final Future<ResultB> futureResultB;

        String technique = "";

        if (technique.equals("serial")) {
            futureResultB = executorB.submit(new Callable<ResultB>() {
                @Override
                public ResultB call() throws Exception {
                    return serviceB.work(futureResultA.get());
                }
            });
        } else if (technique.equals("complex")) {

            Function<ResultA, ResultB> myFunction = new Function<ResultA, ResultB>() {
                @Override
                public ResultB apply(final ResultA resultA) {
                    try {
                        Callable<ResultB> myCallable = new Callable<ResultB>() {
                            @Override
                            public ResultB call() throws Exception {
                                return serviceB.work(resultA);
                            }
                        };
                        return executorB.submit(myCallable).get();
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                }
            };
            futureResultB = Futures.compose(futureResultA, myFunction);
        } else {

            Function<ResultA, ResultB> myFunction = new Function<ResultA, ResultB>() {
                @Override
                public ResultB apply(ResultA from) {
                    return serviceB.work(from);
                }
            };
            futureResultB = Futures.compose(futureResultA, new AsyncFunction<ResultA, ResultB>(myFunction, executorB));

            throw new IllegalStateException();
        }

        System.out.println(futureResultB);
    }

    public static class AsyncFunction<F, T> implements Function<F, T> {
        Function<F, T> function;
        ExecutorService executor;

        public AsyncFunction(Function<F, T> function, ExecutorService executor) {
            super();
            this.function = function;
            this.executor = executor;
        }

        public T apply(final F from) {
            try {
                Future<T> futureT = executor.submit(new Callable<T>() {
                    @Override
                    public T call() throws Exception {
                        return function.apply(from);
                    }
                });
                return futureT.get();
            } catch (Exception e) {
                throw new RuntimeException(e);

            }

        };
    }
}
