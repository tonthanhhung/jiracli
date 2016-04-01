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

import java.util.Arrays;
import java.util.List;

final class CommandList {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static final List<Class<Command>> COMMANDS = (List) Arrays.asList(Base64.class, Browse.class, Filter.class,
            Get.class, Help.class, JavaScript.class, Print.class, Read.class, ReadExcel.class, Search.class,
            EpicIssues.class, Echo.class, Sort.class, Join.class, Split.class, Labels.class, Edit.class, Rest.class);

    private CommandList() {
        // don't allow instances
    }

    /**
     * @return A list of all types implementing {@link Command}
     */
    public static List<Class<Command>> getCommands() {
        return COMMANDS;
    }
}
