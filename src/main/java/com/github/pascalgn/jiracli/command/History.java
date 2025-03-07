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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.github.pascalgn.jiracli.context.Configuration;
import com.github.pascalgn.jiracli.context.Context;
import com.github.pascalgn.jiracli.model.Data;
import com.github.pascalgn.jiracli.model.None;
import com.github.pascalgn.jiracli.model.Text;
import com.github.pascalgn.jiracli.model.TextList;
import com.github.pascalgn.jiracli.util.Hint;
import com.github.pascalgn.jiracli.util.Supplier;

@CommandDescription(names = "history", description = "Show the command history")
class History implements Command {
    @Argument(names = { "-c", "--clear" }, description = "clear the history")
    private boolean clear;

    @Override
    public Data execute(Context context, Data input) {
        Configuration configuration = context.getConfiguration();
        if (clear) {
            configuration.setHistory(Collections.<String> emptyList());
            return None.getInstance();
        } else {
            List<String> history = configuration.getHistory();
            final Iterator<String> it = history.iterator();
            return new TextList(new Supplier<Text>() {
                @Override
                public Text get(Set<Hint> hints) {
                    return (it.hasNext() ? new Text(it.next()) : null);
                }
            });
        }
    }
}
