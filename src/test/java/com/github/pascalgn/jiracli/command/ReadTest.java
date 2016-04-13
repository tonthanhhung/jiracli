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
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.github.pascalgn.jiracli.command.Read.TextFileReader;
import com.github.pascalgn.jiracli.context.Context;
import com.github.pascalgn.jiracli.model.Issue;
import com.github.pascalgn.jiracli.model.IssueList;
import com.github.pascalgn.jiracli.model.None;
import com.github.pascalgn.jiracli.testutil.ExcelUtils;
import com.github.pascalgn.jiracli.testutil.MockContext;
import com.github.pascalgn.jiracli.util.Hint;
import com.github.pascalgn.jiracli.util.IOUtils;

public class ReadTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void test1() throws Exception {
        File file = folder.newFile("temp.xlsx");
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet1 = wb.createSheet("Sheet1");
            for (int row = 10; row <= 110; row++) {
                ExcelUtils.writeCell(sheet1, row, 20, "ISSUE-" + row);
            }
            try (OutputStream out = new FileOutputStream(file)) {
                wb.write(out);
            }
        }

        Context context = new MockContext();

        Read re = new Read(file.getAbsolutePath(), "Sheet1", "U");
        IssueList list = re.execute(context, None.getInstance());
        assertNotNull(list);

        List<Issue> issues = list.remaining(Hint.none());
        assertNotNull(issues);
        assertEquals(101, issues.size());
        assertEquals("ISSUE-10", issues.get(0).getKey());
        assertEquals("ISSUE-110", issues.get(100).getKey());
    }

    @Test
    public void test2() throws Exception {
        File file = folder.newFile("temp.txt");
        IOUtils.write(file, "ISSUE-1\nISSUE-2\n99ISSUE-3 ISSUE-4\r\nISSUE-5");
        TextFileReader fileReader = new TextFileReader(file);
        List<String> keys = new ArrayList<>();
        String key;
        while ((key = fileReader.get(Hint.none())) != null) {
            keys.add(key);
        }
        assertEquals(Arrays.asList("ISSUE-1", "ISSUE-2", "ISSUE-3", "ISSUE-4", "ISSUE-5"), keys);
    }
}
