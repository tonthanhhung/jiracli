/**
 * Copyright 2016 Pascal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.pascalgn.jiracli.util;

import java.util.Set;

public final class MemoizingSupplier<T> implements Supplier<T> {
    private final Supplier<T> supplier;

    private T value;

    public MemoizingSupplier(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public synchronized T get(Set<Hint> hints) {
        if (value == null) {
            value = supplier.get(hints);
            if (value == null) {
                throw new IllegalStateException("Supplied value must not be null!");
            }
        }
        return value;
    }
}
