package tech.demonlee.swing.demo.jlist;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Demon.Lee
 * @date 2023-07-20 09:51
 */
public class JBListExample {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndSelectJBList();
        });
    }

    public static void createAndSelectJBList() {
        JFrame frame = new JFrame("JBList Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] items = {"Option 1", "Option 2", "Option 3", "Option 4", "Option 5"};
        JList<String> list = new JList<>(items);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 使用 Map 存储生成的 JList
        Map<String, JList<String>> jbListMap = new HashMap<>();

        // 创建默认的空列表
        DefaultListModel<String> emptyModel = new DefaultListModel<>();
        JList<String> newList = new JList<>(emptyModel);
        JScrollPane scrollPane = new JScrollPane(newList);
        scrollPane.setVisible(false);

        // 监听 JList 的选择事件
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // 获取选中的值
                String selectedValue = list.getSelectedValue();
                // 检查 Map 中是否已存在对应的 JList
                JList<String> jbList = jbListMap.get(selectedValue);
                if (jbList == null) {
                    // 如果不存在，生成新的 JList
                    DefaultListModel<String> model = new DefaultListModel<>();
                    model.addElement("List Item 1 for " + selectedValue);
                    model.addElement("List Item 2 for " + selectedValue);
                    model.addElement("List Item 3 for " + selectedValue);
                    jbList = new JList<>(model);
                    jbListMap.put(selectedValue, jbList);
                }
                // 更新下方的 JList
                scrollPane.setVisible(true);
                scrollPane.setViewportView(jbList);
                frame.pack();
            }
        });

        // 将列表和切换按钮添加到窗口中
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(new JScrollPane(list), BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }
}