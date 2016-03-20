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
package com.github.pascalgn.jiracli.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

/**
 * Main window of the GUI: Displays the console for input/output
 */
public class MainWindow extends JFrame {
    private static final long serialVersionUID = -6569821157912403607L;

    private final ConsoleTextArea consoleTextArea;

    public MainWindow() {
        super("Jiracli 1.0.0");
        consoleTextArea = new ConsoleTextArea(25, 80);
        JScrollPane consoleTextAreaScroll = new JScrollPane(consoleTextArea);
        consoleTextAreaScroll.setBorder(null);
        consoleTextAreaScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(consoleTextAreaScroll);
        pack();
        setLocationRelativeTo(null);
    }

    public void appendText(String str) {
        consoleTextArea.appendText(str);
    }

    public String readLine() {
        return consoleTextArea.readLine();
    }
}
