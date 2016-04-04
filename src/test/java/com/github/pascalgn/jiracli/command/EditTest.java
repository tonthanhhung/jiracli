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
package com.github.pascalgn.jiracli.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.github.pascalgn.jiracli.model.Field;
import com.github.pascalgn.jiracli.model.Issue;
import com.github.pascalgn.jiracli.testutil.IssueFactory;
import com.github.pascalgn.jiracli.testutil.Resource;
import com.github.pascalgn.jiracli.util.IOUtils;

public class EditTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void test1a() throws Exception {
        Issue issue = IssueFactory.create("ISSUE-123", "summary", "Summary 123");
        File file = folder.newFile("issue123.txt");
        try (BufferedWriter writer = IOUtils.createBufferedWriter(file)) {
            Edit.write(writer, issue);
        }
        Resource resource = Resource.get(getClass(), "EditTest.test1.txt");
        assertEquals(resource.getContents(), IOUtils.toString(file));
    }

    @Test
    public void test1b() throws Exception {
        Issue originalIssue = IssueFactory.create("ISSUE-123", "summary", "Old summary");

        List<Issue> result = read("EditTest.test1.txt", originalIssue);

        assertEquals(1, result.size());
        Issue issue = result.get(0);
        assertSame(originalIssue, issue);
        assertEquals("Summary 123", getFieldValue(issue, "summary"));
    }

    @Test
    public void test2() throws Exception {
        Issue originalIssue = IssueFactory.create("ISSUE-123", "summary", "Old summary");

        List<Issue> result = read("EditTest.test2.txt", originalIssue);

        assertEquals(1, result.size());
        Issue issue = result.get(0);
        assertSame(originalIssue, issue);
        assertEquals("Summary 123", getFieldValue(issue, "summary"));
    }

    @Test
    public void test3() throws Exception {
        Issue originalIssue = IssueFactory.create("ISSUE-123", "summary", "", "environment", "", "description", "",
                "custom_123", "X", "custom_456", "X", "custom_789", "X");

        List<Issue> result = read("EditTest.test3.txt", originalIssue);

        assertEquals(1, result.size());
        Issue issue = result.get(0);
        assertSame(originalIssue, issue);
        assertEquals("Summary\r\nwith\r\nnewline", getFieldValue(issue, "summary"));
        assertEquals("test", getFieldValue(issue, "environment"));
        assertEquals("Some description\r\n; not a comment", getFieldValue(issue, "description"));
        assertEquals("", getFieldValue(issue, "custom_123"));
        assertEquals("", getFieldValue(issue, "custom_456"));
        assertEquals("\r\n", getFieldValue(issue, "custom_789"));
    }

    private static Object getFieldValue(Issue issue, String id) {
        Field field = issue.getFieldMap().getFieldById(id);
        if (field == null) {
            throw new IllegalStateException("No such field: " + id);
        }
        return field.getValue().getValue();
    }

    private static List<Issue> read(String resourceName, Issue... originalIssues) throws IOException {
        Resource resource = Resource.get(EditTest.class, resourceName);
        try (BufferedReader reader = new BufferedReader(resource.openReader())) {
            return Edit.read(Arrays.asList(originalIssues), reader);
        }
    }
}
