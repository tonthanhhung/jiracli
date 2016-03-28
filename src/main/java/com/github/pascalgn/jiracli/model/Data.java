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

public abstract class Data {
    public abstract Issue toIssue();

    public final Issue toIssueOrFail() {
        return convertOrFail(toIssue(), Issue.class);
    }

    public abstract IssueList toIssueList();

    public final IssueList toIssueListOrFail() {
        return convertOrFail(toIssueList(), IssueList.class);
    }

    public abstract Text toText();

    public final Text toTextOrFail() {
        return convertOrFail(toText(), Text.class);
    }

    public abstract TextList toTextList();

    public final TextList toTextListOrFail() {
        return convertOrFail(toTextList(), TextList.class);
    }

    private <T> T convertOrFail(T instance, Class<T> type) {
        if (instance == null) {
            throw new IllegalStateException("Could not convert to " + type.getSimpleName() + ": " + this);
        }
        return instance;
    }
}
