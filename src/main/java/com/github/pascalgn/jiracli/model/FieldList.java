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
package com.github.pascalgn.jiracli.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import com.github.pascalgn.jiracli.util.Function;
import com.github.pascalgn.jiracli.util.Hint;
import com.github.pascalgn.jiracli.util.Supplier;

public class FieldList extends List<Field> {
    public FieldList() {
        super();
    }

    public FieldList(Field... fields) {
        super(Arrays.asList(fields).iterator());
    }

    public FieldList(Iterator<Field> iterator) {
        super(iterator);
    }

    public FieldList(Supplier<Field> supplier) {
        super(supplier);
    }

    @Override
    public IssueList toIssueList() {
        return new IssueList(convertingSupplier(new Function<Field, Issue>() {
            @Override
            public Issue apply(Field field, Set<Hint> hints) {
                return field.toIssue();
            }
        }));
    }

    @Override
    public FieldList toFieldList() {
        return this;
    }

    @Override
    public FieldList filteredList(Filter<Field> filter) {
        return new FieldList(new FilteredSupplier<>(getSupplier(), filter));
    }
}
