import javax.swing.table.*;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import javax.sql.DataSource;
import javax.swing.*;
/*
 * Created by JFormDesigner on Mon Jun 13 13:52:26 CST 2022
 */


/**
 * @author Forever
 */
public class BookManage {
    private static String UserNameInfo = "";
    private static DataSource dataSource;
    private static String ISBNinfo_Text = "";



    public static void main(String[] args){
        new BookManage();
    }

    public BookManage() {
        initComponents();
        JF1.setVisible(true);
    }

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src\\druid.properties"));
        } catch (IOException var3) {
            JOptionPane.showMessageDialog(null, "服务器配置文件加载失败！", "严重错误", JOptionPane.ERROR_MESSAGE);
        }
        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception var2) {
            JOptionPane.showMessageDialog(null, "服务器连接超时 请重启软件", "严重错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void JF1WindowClosing(WindowEvent e) {
        // TODO add your code here
        JF1.dispose();
    }

    private void JF1WindowOpened(WindowEvent e) {
        // TODO add your code here
        JF1.getRootPane().setDefaultButton(login);
    }

    private void radioButton1(ActionEvent e) {
        // TODO add your code here
        reg.setEnabled(false);
    }

    private void resusername(ActionEvent e) {
        // TODO add your code here
        reg.setEnabled(true);
    }

    private void login(ActionEvent e) {
        // TODO 登录用户识别
        if (resusername.isSelected()) {
            try {
                loginuser();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            try {
                loginAdmin();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void reguser(ActionEvent e) {
        // TODO 用户注册
        try {
            resuser();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(JF1, "注册失败！可能用户名重复", "用户注册", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(ex);
        }
    }

    private void JF2WindowClosing(WindowEvent e) {
        // TODO add your code here
        JF2.dispose();
    }

    private void JF2WindowOpened(WindowEvent e) {
        // TODO JF2窗口启动加载事件
        JF2.setTitle("管理员 " + UserNameInfo + " 欢迎登录i图书");
        try {
            JF2Load();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void JF3WindowClosing(WindowEvent e) {
        // TODO add your code here
        JF3.dispose();
    }

    private void JF3WindowOpened(WindowEvent e) {
        // TODO JF3窗口启动加载事件
        JF3.setTitle("你好 " + UserNameInfo + " 欢迎登录i图书");
        try {
            JF3Load();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void BookInfoUP(ActionEvent e) {
        // TODO 上传图书信息
        String BookName_UP = BookName.getText();
        String BookNum_UP = ISBN.getText();
        String Author_UP = Author.getText();
        String Press_UP = Press.getText();
        String Price_UP = Price.getText();
        if (Objects.equals(BookName_UP, "") ||
                Objects.equals(BookNum_UP, "") ||
                Objects.equals(Author_UP, "") ||
                Objects.equals(Press_UP, "") ||
                Objects.equals(Price_UP, "")) {
            JOptionPane.showMessageDialog(JF2, "部分必填项目为空 请补全后上传", "上传书籍信息", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                BookInfoUP();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(JF2, "提交书籍到服务器失败 \n请检测必填项输入格式是否有误", "上传书籍信息", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(ex);
            }
            try {
                JF2Load();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void BookInfoDel(ActionEvent e) {
        // TODO 删除图书信息
        if (!Objects.equals(ISBN.getText(), "")){
            int input = JOptionPane.showConfirmDialog(JF2, "是否删除对应书籍的全部信息？");
            if (input == 0) {
                try {
                    BookInfoDel();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    JF2Load();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }else {
            JOptionPane.showMessageDialog(JF2, "请输入ISBN码","删除书籍",JOptionPane.ERROR_MESSAGE);
        }

    }

    private void LendBook(ActionEvent e) {
        // TODO 租借书籍相关操作
        String Book_Num = ISBNNum_1.getText();
        if (!Objects.equals(Book_Num, "")) {
            try {
                LendBook();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(JF3, "出现错误 请重试\n或联系管理员", "租借书籍", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(ex);
            }
            try {
                JF3Load();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }else {
            JOptionPane.showMessageDialog(JF3, "请输入要租借书籍的ISBN码", "租借书籍", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ReturnBook(ActionEvent e) {
        // TODO 归还书籍相关操作
        String Book_Num = ISBNNum_1.getText();
        if (!Objects.equals(Book_Num, "")) {
            try {
                ReturnBook();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(JF3, "出现错误 请重试\n或联系管理员", "租借书籍", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(ex);
            }
            try {
                JF3Load();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else {
            JOptionPane.showMessageDialog(JF3, "请输入要归还书籍的ISBN码", "归还书籍", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void BookInfo_SQL_AdminMouseClicked(MouseEvent e) {
        // TODO 列表双击获取
        if (e.getClickCount() == 2) {
            Point p = e.getPoint();
            int row = BookInfo_SQL_Admin.rowAtPoint(p);
            String Book_Name = (String) BookInfo_SQL_Admin.getValueAt(row,0);
            String Book_Num = (String) BookInfo_SQL_Admin.getValueAt(row,1);
            String Book_Author = (String) BookInfo_SQL_Admin.getValueAt(row,2);
            String Book_Press = (String) BookInfo_SQL_Admin.getValueAt(row,3);
            String Book_Price = (String) BookInfo_SQL_Admin.getValueAt(row,4);
            String Book_Page = (String) BookInfo_SQL_Admin.getValueAt(row,5);
//            System.out.println(info);
            ISBNinfo_Text=Book_Num;
            BookName.setText(Book_Name);
            ISBN.setText(Book_Num);
            Author.setText(Book_Author);
            Press.setText(Book_Press);
            Price.setText(Book_Price);
            if (Objects.equals(Book_Page, "页数未知")){
                Page.setText(null);
            }else {
                Page.setText(Book_Page);
            }
        }
    }

    private void BookInfoAlter(ActionEvent e) {
        // TODO 书籍信息修改
        String BookName_UP = BookName.getText();
        String BookNum_UP = ISBN.getText();
        String Author_UP = Author.getText();
        String Press_UP = Press.getText();
        String Price_UP = Price.getText();
        if (Objects.equals(BookName_UP, "") ||
                Objects.equals(BookNum_UP, "") ||
                Objects.equals(Author_UP, "") ||
                Objects.equals(Press_UP, "") ||
                Objects.equals(Price_UP, "")) {
            JOptionPane.showMessageDialog(JF2, "部分必填项目为空 请补全后修改", "修改书籍信息", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                AlterBook();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(JF2, "书籍信息修改失败 请重试！", "修改书籍信息", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(ex);
            }
            try {
                JF2Load();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void BookInfo_SQL_UserMouseClicked(MouseEvent e) {
        // TODO 列表双击获取
        if (e.getClickCount() == 2) {
            Point p = e.getPoint();
            int row = BookInfo_SQL_User.rowAtPoint(p);
            String Book_Num = (String) BookInfo_SQL_User.getValueAt(row,1);
            ISBNNum_1.setText(Book_Num);
        }
    }

    private void ShowPass(ActionEvent e) {
        // TODO 显示隐藏密码
        if (ShowPass.isSelected()) {
            pass.setEchoChar((char)0);
        } else {
            pass.setEchoChar('\u2022');
        }
    }

    private void TextAllCleanMouseClicked(MouseEvent e) {
        // TODO 清空管理员窗口文本框内容
        TextClean();
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        JF1 = new JFrame();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        username = new JTextField();
        pass = new JPasswordField();
        reg = new JButton();
        login = new JButton();
        admin = new JRadioButton();
        resusername = new JRadioButton();
        ShowPass = new JCheckBox();
        JF2 = new JFrame();
        label4 = new JLabel();
        BookName = new JTextField();
        label5 = new JLabel();
        ISBN = new JFormattedTextField();
        label6 = new JLabel();
        Author = new JTextField();
        label7 = new JLabel();
        Press = new JTextField();
        label8 = new JLabel();
        Price = new JTextField();
        label9 = new JLabel();
        Page = new JTextField();
        label10 = new JLabel();
        scrollPane1 = new JScrollPane();
        BookInfo_SQL_Admin = new JTable();
        BookInfoUP = new JButton();
        BookInfoDel = new JButton();
        BookInfoAlter = new JButton();
        TextAllClean = new JButton();
        JF3 = new JFrame();
        label11 = new JLabel();
        label12 = new JLabel();
        ISBNNum_1 = new JTextField();
        LendBook = new JButton();
        ReturnBook = new JButton();
        scrollPane2 = new JScrollPane();
        BookInfo_SQL_User = new JTable();

        //======== JF1 ========
        {
            JF1.setTitle("\u56fe\u4e66\u7ba1\u7406\u7cfb\u7edf Ver0.0.3");
            JF1.setResizable(false);
            JF1.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            JF1.setIconImage(new ImageIcon(getClass().getResource("/BookManag.png")).getImage());
            JF1.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    JF1WindowClosing(e);
                }
                @Override
                public void windowOpened(WindowEvent e) {
                    JF1WindowOpened(e);
                }
            });
            var JF1ContentPane = JF1.getContentPane();

            //---- label1 ----
            label1.setText("\u56fe\u4e66\u7ba1\u7406\u7cfb\u7edf\u767b\u5f55");
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            label1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));

            //---- label2 ----
            label2.setText("\u7528\u6237\u540d");
            label2.setHorizontalAlignment(SwingConstants.RIGHT);
            label2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));

            //---- label3 ----
            label3.setText("\u5bc6\u7801");
            label3.setHorizontalAlignment(SwingConstants.RIGHT);
            label3.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));

            //---- reg ----
            reg.setText("\u6ce8\u518c");
            reg.addActionListener(e -> reguser(e));

            //---- login ----
            login.setText("\u767b\u5f55");
            login.addActionListener(e -> login(e));

            //---- admin ----
            admin.setText("\u7ba1\u7406\u5458");
            admin.addActionListener(e -> radioButton1(e));

            //---- resusername ----
            resusername.setText("\u7528\u6237");
            resusername.setSelected(true);
            resusername.addActionListener(e -> resusername(e));

            //---- ShowPass ----
            ShowPass.setText("\u663e\u793a\u5bc6\u7801");
            ShowPass.addActionListener(e -> ShowPass(e));

            GroupLayout JF1ContentPaneLayout = new GroupLayout(JF1ContentPane);
            JF1ContentPane.setLayout(JF1ContentPaneLayout);
            JF1ContentPaneLayout.setHorizontalGroup(
                JF1ContentPaneLayout.createParallelGroup()
                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 528, GroupLayout.PREFERRED_SIZE)
                    .addGroup(JF1ContentPaneLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(JF1ContentPaneLayout.createParallelGroup()
                            .addComponent(label2, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label3, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(JF1ContentPaneLayout.createParallelGroup()
                            .addGroup(JF1ContentPaneLayout.createSequentialGroup()
                                .addComponent(admin)
                                .addGap(18, 18, 18)
                                .addComponent(resusername))
                            .addGroup(JF1ContentPaneLayout.createSequentialGroup()
                                .addGroup(JF1ContentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(pass, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(username, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
                                    .addGroup(JF1ContentPaneLayout.createSequentialGroup()
                                        .addComponent(reg)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(login)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ShowPass))))
            );
            JF1ContentPaneLayout.setVerticalGroup(
                JF1ContentPaneLayout.createParallelGroup()
                    .addGroup(JF1ContentPaneLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(JF1ContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(admin)
                            .addComponent(resusername))
                        .addGap(8, 8, 8)
                        .addGroup(JF1ContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label2)
                            .addComponent(username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(JF1ContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label3)
                            .addComponent(pass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(ShowPass))
                        .addGap(18, 18, 18)
                        .addGroup(JF1ContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(reg)
                            .addComponent(login))
                        .addContainerGap(43, Short.MAX_VALUE))
            );
            JF1.setSize(530, 285);
            JF1.setLocationRelativeTo(JF1.getOwner());
        }

        //======== JF2 ========
        {
            JF2.setMinimumSize(new Dimension(910, 530));
            JF2.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            JF2.setIconImage(new ImageIcon(getClass().getResource("/BookManag.png")).getImage());
            JF2.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    JF2WindowClosing(e);
                }
                @Override
                public void windowOpened(WindowEvent e) {
                    JF2WindowOpened(e);
                }
            });
            var JF2ContentPane = JF2.getContentPane();

            //---- label4 ----
            label4.setText("\u4e66\u7c4d\u540d\u79f0*");
            label4.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- label5 ----
            label5.setText("ISBN\u7801*");
            label5.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- ISBN ----
            ISBN.setToolTipText("\u8f93\u516513\u4f4d\u6570\u5b57");

            //---- label6 ----
            label6.setText("\u4e66\u7c4d\u4f5c\u8005*");
            label6.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- label7 ----
            label7.setText("\u51fa\u7248\u793e*");
            label7.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- label8 ----
            label8.setText("\u4e66\u7c4d\u4ef7\u683c*");
            label8.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- Price ----
            Price.setToolTipText("\u4fdd\u7559\u540e\u4e24\u4f4d\u5c0f\u6570\u70b9");

            //---- label9 ----
            label9.setText("\u9875\u6570");
            label9.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- Page ----
            Page.setToolTipText("\u6b64\u9879\u53ef\u4e0d\u586b\u5199");

            //---- label10 ----
            label10.setText("\u8f93\u5165\u6846\u672a\u4f5c\u9650\u5236 \u8bf7\u6309\u9700\u683c\u5f0f\u586b\u5199 (*\u53f7\u5185\u4e3a\u5fc5\u586b\u9879\u76ee)   \u82e5\u5220\u9664\u4e66\u7c4d\u586b\u5199ISBN\u7801\u5373\u53ef");

            //======== scrollPane1 ========
            {

                //---- BookInfo_SQL_Admin ----
                BookInfo_SQL_Admin.setModel(new DefaultTableModel());
                BookInfo_SQL_Admin.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        BookInfo_SQL_AdminMouseClicked(e);
                    }
                });
                scrollPane1.setViewportView(BookInfo_SQL_Admin);
            }

            //---- BookInfoUP ----
            BookInfoUP.setText("\u5f55\u5165\u4e66\u7c4d");
            BookInfoUP.addActionListener(e -> BookInfoUP(e));

            //---- BookInfoDel ----
            BookInfoDel.setText("\u5220\u9664\u4e66\u7c4d");
            BookInfoDel.addActionListener(e -> BookInfoDel(e));

            //---- BookInfoAlter ----
            BookInfoAlter.setText("<html>\u4fee<br>\u6539<br>\u4e66<br>\u7c4d</html>");
            BookInfoAlter.addActionListener(e -> BookInfoAlter(e));

            //---- TextAllClean ----
            TextAllClean.setText("\u6587\u672c\u6846\u6e05\u7a7a");
            TextAllClean.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
            TextAllClean.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    TextAllCleanMouseClicked(e);
                }
            });

            GroupLayout JF2ContentPaneLayout = new GroupLayout(JF2ContentPane);
            JF2ContentPane.setLayout(JF2ContentPaneLayout);
            JF2ContentPaneLayout.setHorizontalGroup(
                JF2ContentPaneLayout.createParallelGroup()
                    .addGroup(JF2ContentPaneLayout.createSequentialGroup()
                        .addGroup(JF2ContentPaneLayout.createParallelGroup()
                            .addGroup(JF2ContentPaneLayout.createSequentialGroup()
                                .addGroup(JF2ContentPaneLayout.createParallelGroup()
                                    .addGroup(JF2ContentPaneLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(label10, GroupLayout.PREFERRED_SIZE, 442, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(TextAllClean))
                                    .addGroup(JF2ContentPaneLayout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addGroup(JF2ContentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(label4)
                                            .addComponent(label5))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(JF2ContentPaneLayout.createParallelGroup()
                                            .addGroup(JF2ContentPaneLayout.createSequentialGroup()
                                                .addComponent(BookName, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
                                                .addGap(24, 24, 24)
                                                .addGroup(JF2ContentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                    .addComponent(label6)
                                                    .addComponent(label7)))
                                            .addComponent(ISBN, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(JF2ContentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(JF2ContentPaneLayout.createSequentialGroup()
                                                .addComponent(Press, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(label9))
                                            .addGroup(JF2ContentPaneLayout.createSequentialGroup()
                                                .addComponent(Author, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(label8)))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(JF2ContentPaneLayout.createParallelGroup()
                                            .addComponent(Price, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(Page, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
                                        .addGap(25, 25, 25)
                                        .addGroup(JF2ContentPaneLayout.createParallelGroup()
                                            .addComponent(BookInfoDel)
                                            .addComponent(BookInfoUP))
                                        .addGap(20, 20, 20)
                                        .addComponent(BookInfoAlter, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)))
                                .addGap(32, 32, 32))
                            .addGroup(JF2ContentPaneLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane1)))
                        .addContainerGap())
            );
            JF2ContentPaneLayout.setVerticalGroup(
                JF2ContentPaneLayout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, JF2ContentPaneLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(JF2ContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label10)
                            .addComponent(TextAllClean))
                        .addGap(2, 2, 2)
                        .addGroup(JF2ContentPaneLayout.createParallelGroup()
                            .addGroup(JF2ContentPaneLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(JF2ContentPaneLayout.createParallelGroup()
                                    .addComponent(BookName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label4)
                                    .addComponent(label6)
                                    .addComponent(Author, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label8)
                                    .addGroup(JF2ContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(Price, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(BookInfoUP)))
                                .addGap(18, 18, 18)
                                .addGroup(JF2ContentPaneLayout.createParallelGroup()
                                    .addGroup(JF2ContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(Page, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(BookInfoDel))
                                    .addComponent(label9)
                                    .addComponent(ISBN, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label5)
                                    .addComponent(label7)
                                    .addComponent(Press, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                            .addComponent(BookInfoAlter, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                        .addContainerGap())
            );
            JF2.setSize(910, 530);
            JF2.setLocationRelativeTo(JF2.getOwner());
        }

        //======== JF3 ========
        {
            JF3.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            JF3.setMinimumSize(new Dimension(910, 530));
            JF3.setIconImage(new ImageIcon(getClass().getResource("/BookManag.png")).getImage());
            JF3.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    JF3WindowClosing(e);
                }
                @Override
                public void windowOpened(WindowEvent e) {
                    JF3WindowOpened(e);
                }
            });
            var JF3ContentPane = JF3.getContentPane();

            //---- label11 ----
            label11.setText("\u501f\u4e66/\u8fd8\u4e66 \u8f93\u5165ISBN\u7801\u5373\u53ef");

            //---- label12 ----
            label12.setText("ISBN\u7801");
            label12.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- LendBook ----
            LendBook.setText("\u501f\u4e66");
            LendBook.addActionListener(e -> LendBook(e));

            //---- ReturnBook ----
            ReturnBook.setText("\u8fd8\u4e66");
            ReturnBook.addActionListener(e -> ReturnBook(e));

            //======== scrollPane2 ========
            {

                //---- BookInfo_SQL_User ----
                BookInfo_SQL_User.setModel(new DefaultTableModel());
                BookInfo_SQL_User.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        BookInfo_SQL_UserMouseClicked(e);
                    }
                });
                scrollPane2.setViewportView(BookInfo_SQL_User);
            }

            GroupLayout JF3ContentPaneLayout = new GroupLayout(JF3ContentPane);
            JF3ContentPane.setLayout(JF3ContentPaneLayout);
            JF3ContentPaneLayout.setHorizontalGroup(
                JF3ContentPaneLayout.createParallelGroup()
                    .addGroup(JF3ContentPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(JF3ContentPaneLayout.createParallelGroup()
                            .addComponent(scrollPane2)
                            .addGroup(JF3ContentPaneLayout.createSequentialGroup()
                                .addGroup(JF3ContentPaneLayout.createParallelGroup()
                                    .addGroup(JF3ContentPaneLayout.createSequentialGroup()
                                        .addGap(41, 41, 41)
                                        .addComponent(label12)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ISBNNum_1, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
                                        .addGap(35, 35, 35)
                                        .addComponent(LendBook)
                                        .addGap(35, 35, 35)
                                        .addComponent(ReturnBook))
                                    .addComponent(label11, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
                                .addGap(439, 439, 439)))
                        .addContainerGap())
            );
            JF3ContentPaneLayout.setVerticalGroup(
                JF3ContentPaneLayout.createParallelGroup()
                    .addGroup(JF3ContentPaneLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(label11)
                        .addGap(18, 18, 18)
                        .addGroup(JF3ContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label12)
                            .addComponent(ISBNNum_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(LendBook)
                            .addComponent(ReturnBook))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                        .addContainerGap())
            );
            JF3.setSize(910, 530);
            JF3.setLocationRelativeTo(JF3.getOwner());
        }

        //---- buttonGroup1 ----
        var buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(admin);
        buttonGroup1.add(resusername);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JFrame JF1;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTextField username;
    private JPasswordField pass;
    private JButton reg;
    private JButton login;
    private JRadioButton admin;
    private JRadioButton resusername;
    private JCheckBox ShowPass;
    private JFrame JF2;
    private JLabel label4;
    private JTextField BookName;
    private JLabel label5;
    private JFormattedTextField ISBN;
    private JLabel label6;
    private JTextField Author;
    private JLabel label7;
    private JTextField Press;
    private JLabel label8;
    private JTextField Price;
    private JLabel label9;
    private JTextField Page;
    private JLabel label10;
    private JScrollPane scrollPane1;
    private JTable BookInfo_SQL_Admin;
    private JButton BookInfoUP;
    private JButton BookInfoDel;
    private JButton BookInfoAlter;
    private JButton TextAllClean;
    private JFrame JF3;
    private JLabel label11;
    private JLabel label12;
    private JTextField ISBNNum_1;
    private JButton LendBook;
    private JButton ReturnBook;
    private JScrollPane scrollPane2;
    private JTable BookInfo_SQL_User;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    //以下为JF1 软件主界面登录注册用户使用
    public void loginAdmin() throws SQLException {
        //管理员登录
        String sn = username.getText();
        String sp = pass.getText();
        if (Objects.equals(sn, "")) {
            JOptionPane.showMessageDialog(JF1, "用户名不能为空", "管理员登录", JOptionPane.ERROR_MESSAGE);
        } else if (Objects.equals(sp, "")) {
            JOptionPane.showMessageDialog(JF1, "密码不能为空", "管理员登录", JOptionPane.ERROR_MESSAGE);
        } else {
            Connection connection = dataSource.getConnection();
            String sql = "select * from adminlogin where UserName=? AND UserPass = ?";
            PreparedStatement Prepare = connection.prepareStatement(sql);
            Prepare.setString(1, sn);
            Prepare.setString(2, sp);
            ResultSet rt = Prepare.executeQuery();
            if (!rt.next()) {
                JOptionPane.showMessageDialog(JF1, "用户名或密码错误 请重试！", "管理员登录", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(JF1, "登录成功！", "管理员登录", JOptionPane.INFORMATION_MESSAGE);
                JF1.dispose();
                JF2.setVisible(true);
            }
            UserNameInfo = sn;
            rt.close();
            Prepare.close();
            connection.close();
        }
    }

    public void loginuser() throws SQLException {
        //用户登录
        String sn = username.getText();
        String sp = pass.getText();
        if (Objects.equals(sn, "")) {
            JOptionPane.showMessageDialog(JF1, "用户名不能为空", "用户登录", JOptionPane.ERROR_MESSAGE);
        } else if (Objects.equals(sp, "")) {
            JOptionPane.showMessageDialog(JF1, "密码不能为空", "用户登录", JOptionPane.ERROR_MESSAGE);
        } else {
            Connection connection = dataSource.getConnection();
            String sql = "select * from userlogin where UserName=? AND UserPass = ?";
            PreparedStatement Prepare = connection.prepareStatement(sql);
            Prepare.setString(1, sn);
            Prepare.setString(2, sp);
            ResultSet rt = Prepare.executeQuery();
            if (!rt.next()) {
                JOptionPane.showMessageDialog(JF1, "用户名或密码错误 请重试！", "用户登录", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(JF1, "登录成功！", "用户登录", JOptionPane.INFORMATION_MESSAGE);
                JF1.dispose();
                JF3.setVisible(true);
            }
            UserNameInfo = sn;
            rt.close();
            Prepare.close();
            connection.close();
        }
    }

    public void resuser() throws Exception {
        //用户注册
        String sn = username.getText();
        String sp = pass.getText();
        if (Objects.equals(sn, "")) {
            JOptionPane.showMessageDialog(JF1, "用户名不能为空", "用户注册", JOptionPane.ERROR_MESSAGE);
        } else if (Objects.equals(sp, "")) {
            JOptionPane.showMessageDialog(JF1, "密码不能为空", "用户注册", JOptionPane.ERROR_MESSAGE);
        } else {
            Connection connection = dataSource.getConnection();
            String sql = "Insert into userlogin(UserName,UserPass) values (?,?)";
            PreparedStatement Prepare = connection.prepareStatement(sql);
            Prepare.setString(1, sn);
            Prepare.setString(2, sp);
            int num = Prepare.executeUpdate();
            if (num == 1) {
                JOptionPane.showMessageDialog(JF1, "注册成功！", "用户注册", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    //以下为JF2窗口 管理员修改/删除/添加书籍所使用
    public void BookInfoUP() throws Exception {
        //书籍提交
        String BookName_UP = BookName.getText();
        String BookNum_UP = ISBN.getText();
        String Author_UP = Author.getText();
        String Press_UP = Press.getText();
        String Price_UP = Price.getText();
        String Page_UP;
        if (Objects.equals(Page.getText(), "")) {
            Page_UP = null;
        } else {
            Page_UP = Page.getText();
        }
        Connection connection = dataSource.getConnection();
        if (!Objects.equals(BookName_UP, "") || !Objects.equals(BookNum_UP, "") || !Objects.equals(Author_UP, "") || !Objects.equals(Press_UP, "") || !Objects.equals(Price_UP, "")) {
                String sql = "Insert into bookinfo(Book_Name,Book_Num,Book_Author,Book_Press,Book_Price,Book_Page) values (?,?,?,?,?,?)";
                PreparedStatement Prepare = connection.prepareStatement(sql);
                Prepare.setString(1, BookName_UP);
                Prepare.setString(2, BookNum_UP);
                Prepare.setString(3, Author_UP);
                Prepare.setString(4, Press_UP);
                Prepare.setString(5, Price_UP);
                Prepare.setString(6, Page_UP);
                int num = Prepare.executeUpdate();
                if (num == 1) {
                    JOptionPane.showMessageDialog(JF2, "书籍上传成功！", "上传书籍信息", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }


    public void BookInfoDel() throws Exception {
        //书籍删除
        String BookNum_UP = ISBN.getText();
        Connection connection = dataSource.getConnection();
        if (!Objects.equals(BookNum_UP, "")) {
            String sql = "DELETE from bookinfo where Book_Num = ? ";
            PreparedStatement Prepare = connection.prepareStatement(sql);
            Prepare.setString(1, BookNum_UP);
            int num = Prepare.executeUpdate();
            if (num == 1) {
                JOptionPane.showMessageDialog(JF2, "书籍删除成功！", "删除书籍信息", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(JF2, "书籍删除失败！\n可能是没有对应书籍信息 \n请联系管理员", "删除书籍信息", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void AlterBook() throws Exception {
        //书籍修改
        String BookName_UP = BookName.getText();
        String BookNum_UP = ISBN.getText();
        String Author_UP = Author.getText();
        String Press_UP = Press.getText();
        String Price_UP = Price.getText();
        String Page_UP;
        if (Objects.equals(Page.getText(), "")) {
            Page_UP = null;
        } else {
            Page_UP = Page.getText();
        }
        Connection connection = dataSource.getConnection();
        String sql = "Update bookinfo set Book_Name = ? , Book_Num = ? , Book_Author = ? , Book_Press = ? , Book_Price = ? , Book_Page = ? where Book_Num = ? ";
        PreparedStatement Prepare = connection.prepareStatement(sql);
        Prepare.setString(1, BookName_UP);
        Prepare.setString(2, BookNum_UP);
        Prepare.setString(3, Author_UP);
        Prepare.setString(4, Press_UP);
        Prepare.setString(5, Price_UP);
        Prepare.setString(6, Page_UP);
        Prepare.setString(7, ISBNinfo_Text);
        int num = Prepare.executeUpdate();
        if (num > 0) {
            JOptionPane.showMessageDialog(JF2, "书籍修改成功！", "修改书籍信息", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    //以下为JF3窗口 普通用户租借/归还书籍所使用
    public void LendBook() throws Exception {
        //租借书籍
        String Book_Num = ISBNNum_1.getText();
        Connection connection = dataSource.getConnection();
        //查找书籍关键字 Start
        String sql_1 = "Select * from bookinfo where Book_Num = ?";
        PreparedStatement Prepare_1 = connection.prepareStatement(sql_1);
        Prepare_1.setString(1, Book_Num);
        ResultSet rt = Prepare_1.executeQuery();
        //查找书籍关键字 End
        if (!rt.next()) {
            JOptionPane.showMessageDialog(JF3, "无法查找到ISBN码对应的书籍 \n可能是输入有误或书籍信息错误 \n请联系管理员或重试", "租借书籍", JOptionPane.ERROR_MESSAGE);
        } else {
            if (rt.getString(9) == null) {
                String sql = "UPDATE bookinfo set ReturnBook = 1, LendUser = ?, LendTime = now() where Book_Num = ? ";
                PreparedStatement Prepare = connection.prepareStatement(sql);
                Prepare.setString(1, UserNameInfo);
                Prepare.setString(2, Book_Num);
                int num = Prepare.executeUpdate();
                if (num == 1) {
                    JOptionPane.showMessageDialog(JF3, "租借书籍成功！", "租借书籍", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(JF3, "租借书籍失败！\n可能是没有对应书籍或书籍信息有误 \n请联系管理员或重试", "租借书籍", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(JF3, "租借书籍失败！\n该书已被其他用户租借", "租借书籍", JOptionPane.ERROR_MESSAGE);
            }
        }
        rt.close();
    }

    public void ReturnBook() throws Exception {
        //归还书籍
        String Book_Num = ISBNNum_1.getText();
        Connection connection = dataSource.getConnection();
        //查找书籍关键字 Start
        String sql_1 = "Select * from bookinfo where Book_Num = ?";
        PreparedStatement Prepare_1 = connection.prepareStatement(sql_1);
        Prepare_1.setString(1, Book_Num);
        ResultSet rt = Prepare_1.executeQuery();
        //查找书籍关键字 End
        if (!rt.next()) {
            JOptionPane.showMessageDialog(JF3, "无法查找到ISBN码对应的书籍 \n可能是输入有误或书籍信息错误 \n请联系管理员或重试", "归还书籍", JOptionPane.ERROR_MESSAGE);
        } else {
            if (Objects.equals(rt.getString(9), UserNameInfo)) {
                String LendTime = rt.getString(10);
                String Time = getString(LendTime);
                String sql = "UPDATE bookinfo set ReturnBook = 0, LendUser = null, LendTime = null where Book_Num = ? ";
                PreparedStatement Prepare = connection.prepareStatement(sql);
                Prepare.setString(1, Book_Num);
                int num = Prepare.executeUpdate();
                if (num == 1) {
                    JOptionPane.showMessageDialog(JF3, "归还书籍成功！您的借书时长为"+Time, "归还书籍", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(JF3, "归还书籍失败！\n可能是没有对应书籍或书籍信息有误 \n请联系管理员或重试", "归还书籍", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(JF3, "租借书籍失败！\n您未借该书籍 无需归还", "归还书籍", JOptionPane.ERROR_MESSAGE);
            }
        }
        rt.close();
    }

    private String getString(String LendTime) throws ParseException {
        //时间计算方法提取
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String NowData = formatter.format(date);
        Date d1 = formatter.parse(NowData);
        Date d2 = formatter.parse(LendTime);
        long now = d1.getTime() - d2.getTime();
        long days = now / (1000 * 60 * 60 * 24);
        long hours = (now - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (now - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
        long s = (now / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60);
        return (days + "天" + hours + "小时" + minutes + "分" + s + "秒");
    }

    //TODO 书籍列表刷新 Start
    //若表格需要不同信息需额外重写
    public DefaultTableModel BookInfo_Rec() throws SQLException {
        String[] col = {"书籍名称", "书籍编码(ISBN)", "书籍作者", "书籍出版社", "书籍价格(元)", "书籍页数", "是否租借","租借用户"};
        DefaultTableModel BookInfo = new DefaultTableModel(col, 0){
            public boolean isCellEditable(int row, int column) {return false;}
        };
        Connection connection = dataSource.getConnection();
        String sql_1 = "Select Book_Name,Book_Num,Book_Author,Book_Press,Book_Price,Book_Page,ReturnBook,LendUser from bookinfo";
        PreparedStatement Prepare_1 = connection.prepareStatement(sql_1);
        ResultSet rs = Prepare_1.executeQuery();
        while (rs.next()) {
            String Name = rs.getString(1);
            String Num = rs.getString(2);
            String Author = rs.getString(3);
            String Press = rs.getString(4);
            String Price = rs.getString(5);
            String Page;
            if (Objects.equals(rs.getString(6), null)) {
                Page = "页数未知";
            } else {
                Page = rs.getString(6);
            }
            String Return;
            if (rs.getInt(7) == 0) {
                Return = "未租借";
            } else {
                Return = "已被租借";
            }
            String LendUser = rs.getString(8);
            String[] str_row = {Name, Num, Author, Press, Price, Page, Return, LendUser};
            BookInfo.addRow(str_row);
        }
        return BookInfo;
    }

    public void JF3Load() throws Exception {
        BookInfo_Rec();
        BookInfo_SQL_User.setModel(BookInfo_Rec());

    }

    public void JF2Load() throws Exception {
        BookInfo_Rec();
        BookInfo_SQL_Admin.setModel(BookInfo_Rec());
    }
    //TODO 书籍列表刷新 END

    public void TextClean() {
        //管理员窗口清除文本框按钮
        BookName.setText("");
        ISBN.setText("");
        Author.setText("");
        Press.setText("");
        Price.setText("");
        Page.setText("");
    }






}


