package tech.demonlee.swing.demo.jlist;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Demon.Lee
 * @date 2023-07-20 09:51
 */
public class JListExample {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndSelectJBList();
        });
    }

    public static void createAndSelectJBList() {
        JFrame frame = new JFrame("JBList Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JListContent[] items = {
                new JListContent("Option 1"),
                new JListContent("Option 2"),
                new JListContent("Option 3")
        };
        JList<JListContent> list = new JList<>(items);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                JLabel jbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                JListContent ele = (JListContent) value;
                jbl.setIcon(null);
                jbl.setText(ele.getName());
                return jbl;
            }
        });

        // 使用 Map 存储生成的 JList
        Map<JListContent, JList<String>> jbListMap = new HashMap<>();

        // 创建默认的空列表
        DefaultListModel<String> emptyModel = new DefaultListModel<>();
        JList<String> newList = new JList<>(emptyModel);
        JScrollPane scrollPane = new JScrollPane(newList);
        scrollPane.setVisible(false);

        // 监听 JList 的选择事件
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // 获取选中的值
                JListContent selectedValue = list.getSelectedValue();
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
                secondLayerListener(list, jbList);
                // 更新下方的 JList
                scrollPane.setVisible(true);
                scrollPane.setViewportView(jbList);
                frame.pack();
            }
        });

        // 设置 JScrollPane 的首选大小（高度和宽度）
        JScrollPane firstScrollPane = new JScrollPane(list);
        firstScrollPane.setPreferredSize(new Dimension(200, 150));

        // 将列表和切换按钮添加到窗口中
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(firstScrollPane, BorderLayout.WEST);
        frame.getContentPane().add(scrollPane, BorderLayout.EAST);

        frame.pack();
        frame.setVisible(true);
    }

    // 监听二级 JBList 的选择事件
    private static void secondLayerListener(JList<JListContent> list, JList<String> newList) {
        newList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // 获取选中的值
                String selectedValue = newList.getSelectedValue();
                // 设置一级 JBList 的元素背景色
                for (int i = 0; i < list.getModel().getSize(); i++) {
                    JListContent item = list.getModel().getElementAt(i);
                    if (selectedValue.contains(item.getName())) {
                        list.setSelectedIndex(i); // 选中对应的元素
                        list.setSelectionBackground(Color.RED); // 设置背景色
                        item.setName(item.getName() + ", ✓");
                        break;
                    }
                }
            }
        });
    }

    static class JListContent {
        private String name;

        public JListContent(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}