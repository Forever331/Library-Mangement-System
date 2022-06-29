import javax.swing.table.*;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.sql.DataSource;
import javax.swing.*;
/*
 * Created by JFormDesigner on Mon Jun 13 13:52:26 CST 2022
 */


/**
 * @author Forever
 */
public class BookManage {
    static DataSource dataSource;
    static String UserNameInfo = "";
    static String ISBNinfo_Text = "";
    static int random;
    static String Mail_Text_Info = "";

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
            JOptionPane.showMessageDialog(null, "服务器配置文件加载失败！正在关闭软件 \n请联系管理员或重启软件", "严重错误", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception var2) {
            JOptionPane.showMessageDialog(null, "服务器连接超时 请手动重启软件\n请联系管理员确认服务是否正常", "严重错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void label1MouseClicked(MouseEvent e) {
        String url = "https://github.com/Forever331/Library-Mangement-System";
        try {
            Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void JF1WindowClosing(WindowEvent e) {
        // TODO add your code here
        JF1.dispose();
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
                LoginUser.loginuser(JF1, JF3, pass, username);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            try {
                LoginUser.loginAdmin(JF1, JF2, pass, username);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
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
                Admin_Manage.BookInfoUP(Author, BookName, ISBN, JF2, Page, Press, Price);
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
        if (!Objects.equals(ISBN.getText(), "")) {
            int input = JOptionPane.showConfirmDialog(JF2, "是否删除对应书籍的全部信息？");
            if (input == 0) {
                try {
                    Admin_Manage.BookInfoDel(ISBN, JF2);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    JF2Load();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(JF2, "请输入ISBN码", "删除书籍", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void LendBook(ActionEvent e) {
        // TODO 租借书籍相关操作
        String Book_Num = ISBNNum_1.getText();
        if (!Objects.equals(Book_Num, "")) {
            try {
                User_Manage.LendBook(ISBNNum_1, JF3);
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
            JOptionPane.showMessageDialog(JF3, "请输入要租借书籍的ISBN码", "租借书籍", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ReturnBook(ActionEvent e) {
        // TODO 归还书籍相关操作
        String Book_Num = ISBNNum_1.getText();
        if (!Objects.equals(Book_Num, "")) {
            try {
                User_Manage.ReturnBook(ISBNNum_1, JF3);
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
            String Book_Name = (String) BookInfo_SQL_Admin.getValueAt(row, 0);
            String Book_Num = (String) BookInfo_SQL_Admin.getValueAt(row, 1);
            String Book_Author = (String) BookInfo_SQL_Admin.getValueAt(row, 2);
            String Book_Press = (String) BookInfo_SQL_Admin.getValueAt(row, 3);
            String Book_Price = (String) BookInfo_SQL_Admin.getValueAt(row, 4);
            String Book_Page = (String) BookInfo_SQL_Admin.getValueAt(row, 5);
            ISBNinfo_Text = Book_Num;
            BookName.setText(Book_Name);
            ISBN.setText(Book_Num);
            Author.setText(Book_Author);
            Press.setText(Book_Press);
            Price.setText(Book_Price);
            if (Objects.equals(Book_Page, "页数未知")) {
                Page.setText(null);
            } else {
                Page.setText(Book_Page);
            }
        }
    }

    private void BookInfo_SQL_UserMouseClicked(MouseEvent e) {
        // TODO 列表双击获取
        if (e.getClickCount() == 2) {
            Point p = e.getPoint();
            int row = BookInfo_SQL_User.rowAtPoint(p);
            String Book_Num = (String) BookInfo_SQL_User.getValueAt(row, 1);
            ISBNNum_1.setText(Book_Num);
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
                Admin_Manage.AlterBook(Author, BookName, ISBN, JF2, Page, Press, Price);
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


    private void ShowPass(ActionEvent e) {
        // TODO 显示隐藏密码
        if (ShowPass.isSelected()) {
            pass.setEchoChar((char) 0);
        } else {
            pass.setEchoChar('\u2022');
        }
    }

    private void TextAllCleanMouseClicked(MouseEvent e) {
        // TODO 清空管理员窗口文本框内容
        BookName.setText("");
        ISBN.setText("");
        Author.setText("");
        Press.setText("");
        Price.setText("");
        Page.setText("");
    }

    private void PassNullMouseClicked(MouseEvent e) {
        // TODO 忘记密码
        JF1.setVisible(false);
        ForgotPass.setVisible(true);
    }

    private void reguser(ActionEvent e) {
        // TODO 用户注册按钮窗口
        JF1.setVisible(false);
        RegisterFrame.setVisible(true);
    }

    private void RegisterFrameWindowClosing(WindowEvent e) {
        // TODO 注册窗口关闭事件
        JF1.setVisible(true);
        RegisterFrame.dispose();
        t1.stop();
        Mail_Checking.setText("发送验证码");
        ForgotMail_Checking.setText("发送验证码");
        LoginUser.RegisterText(Mail_Num, Mail_Text, RegPassword_Text, RegPassword_Text_Reconfirm, RegUser_Text);
    }

    private void ShowPass_1(ActionEvent e) {
        // TODO 注册页显示密码
        if (ShowPass_1.isSelected()) {
            RegPassword_Text.setEchoChar((char) 0);
            RegPassword_Text_Reconfirm.setEchoChar((char) 0);
        } else {
            RegPassword_Text.setEchoChar('\u2022');
            RegPassword_Text_Reconfirm.setEchoChar('\u2022');
        }
    }

    private void Mail_Checking(ActionEvent e) {
        // TODO 邮箱验证事件
        PreparedStatement Prepare;
        try (Connection connection = dataSource.getConnection()) {
            String sql = "select * from userlogin where UserMail = ?";
            Prepare = connection.prepareStatement(sql);
            Prepare.setString(1, Mail_Text.getText());
            ResultSet rt = Prepare.executeQuery();
            if (!rt.next()) {
                if (Objects.equals(RegUser_Text.getText(), "")) {
                    JOptionPane.showMessageDialog(RegisterFrame, "用户名不能为空", "用户注册", JOptionPane.ERROR_MESSAGE);
                } else if (Objects.equals(RegPassword_Text.getText(), "")) {
                    JOptionPane.showMessageDialog(RegisterFrame, "密码不能为空", "用户注册", JOptionPane.ERROR_MESSAGE);
                } else if (!Objects.equals(RegPassword_Text.getText(), RegPassword_Text_Reconfirm.getText())) {
                    JOptionPane.showMessageDialog(RegisterFrame, "两次输入的密码不同", "用户注册", JOptionPane.ERROR_MESSAGE);
                } else if (Objects.equals(Mail_Text.getText(), "")) {
                    JOptionPane.showMessageDialog(RegisterFrame, "邮箱不能为空", "用户注册", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        LoginUser.Mail_Checking(Mail_Checking, Mail_Text, RegisterFrame, r, t1);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(RegisterFrame, "邮箱已被使用 请重新输入", "用户注册", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void Register(ActionEvent e) {
        // TODO 用户注册事件
        if (Objects.equals(RegUser_Text.getText(), "")) {
            JOptionPane.showMessageDialog(RegisterFrame, "用户名不能为空", "用户注册", JOptionPane.ERROR_MESSAGE);
        } else if (Objects.equals(RegPassword_Text.getText(), "")) {
            JOptionPane.showMessageDialog(RegisterFrame, "密码不能为空", "用户注册", JOptionPane.ERROR_MESSAGE);
        } else if (!Objects.equals(RegPassword_Text.getText(), RegPassword_Text_Reconfirm.getText())) {
            JOptionPane.showMessageDialog(RegisterFrame, "两次输入的密码不同", "用户注册", JOptionPane.ERROR_MESSAGE);
        } else if (!Objects.equals(Mail_Text.getText(), Mail_Text_Info)) {
            JOptionPane.showMessageDialog(RegisterFrame, "检测到获取验证码的邮箱有变动 请重新获取验证码", "用户注册", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                resuser();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(RegisterFrame, "注册失败！可能用户名重复", "用户注册", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(ex);
            }
        }
    }

    private void ForgotPassWindowClosing(WindowEvent e) {
        // TODO 找回密码窗口关闭事件
        JF1.setVisible(true);
        ForgotPass.dispose();
        t1.stop();
        Mail_Checking.setText("发送验证码");
        Mail_Checking.setEnabled(true);
        ForgotMail_Checking.setText("发送验证码");
        ForgotMail_Checking.setEnabled(true);
        LoginUser.ForgotText(ForgotMailNum_Text, ForgotMail_Text, ForgotPass_Text, ForgotPass_Text_Reconfirm, ForgotUser_Text);
    }

    private void ForgotPass_Show(ActionEvent e) {
        // TODO 找回密码页密码显示
        if (ForgotPass_Show.isSelected()) {
            ForgotPass_Text.setEchoChar((char) 0);
            ForgotPass_Text_Reconfirm.setEchoChar((char) 0);
        } else {
            ForgotPass_Text.setEchoChar('\u2022');
            ForgotPass_Text_Reconfirm.setEchoChar('\u2022');
        }
    }

    private void ForgotMail_Checking(ActionEvent e) {
        // TODO 找回密码页 发送邮件按钮
        if (Objects.equals(ForgotUser_Text.getText(), "")) {
            JOptionPane.showMessageDialog(ForgotPass, "用户名不能为空", "找回密码/修改密码", JOptionPane.ERROR_MESSAGE);
        } else if (Objects.equals(ForgotMail_Text.getText(), "")) {
            JOptionPane.showMessageDialog(ForgotPass, "邮件不能为空", "找回密码/修改密码", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                ForgotMail();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void ForgotMailNum_Checking(ActionEvent e) {
        // TODO 找回密码页 邮箱验证码按钮事件
        if (!Objects.equals(ForgotUser_Text.getText(), UserNameInfo)) {
            JOptionPane.showMessageDialog(ForgotPass, "检测到用户名已变更 请重新填写", "找回密码/修改密码", JOptionPane.ERROR_MESSAGE);
        } else if (!Objects.equals(ForgotMail_Text.getText(), Mail_Text_Info)) {
            JOptionPane.showMessageDialog(ForgotPass, "检测到邮箱已变更 请重新填写", "找回密码/修改密码", JOptionPane.ERROR_MESSAGE);
        } else if (Objects.equals(ForgotMailNum_Text.getText(), "")) {
            JOptionPane.showMessageDialog(ForgotPass, "邮箱验证码不能为空", "找回密码/修改密码", JOptionPane.ERROR_MESSAGE);
        } else if (Objects.equals(ForgotMailNum_Text.getText(), String.valueOf(random))) {
            ForgotMailNum_Checking.setText("验证成功");
            ForgotMailNum_Checking.setEnabled(false);
            ForgotUser_Text.setEditable(false);
            ForgotMail_Text.setEditable(false);
            ForgotMailNum_Text.setEditable(false);
            ForgotMail_Checking.setEnabled(false);
            ForgotPass_Text.setEditable(true);
            ForgotPass_Text_Reconfirm.setEditable(true);
        } else {
            JOptionPane.showMessageDialog(ForgotPass, "邮箱验证码错误", "找回密码/修改密码", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void Forgot(ActionEvent e) {
        // TODO 找回密码 修改密码按钮事件
        if (!Objects.equals(ForgotPass_Text.getText(), ForgotPass_Text_Reconfirm.getText())) {
            JOptionPane.showMessageDialog(ForgotPass, "两次密码验证出现错误！请重新输入", "找回密码/修改密码", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                Forgot();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
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
        textArea1 = new JTextArea();
        PassNull = new JLabel();
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
        RegisterFrame = new JFrame();
        label13 = new JLabel();
        label14 = new JLabel();
        label15 = new JLabel();
        label16 = new JLabel();
        label17 = new JLabel();
        RegUser_Text = new JTextField();
        RegPassword_Text = new JPasswordField();
        RegPassword_Text_Reconfirm = new JPasswordField();
        Mail_Text = new JTextField();
        Mail_Num = new JTextField();
        ShowPass_1 = new JCheckBox();
        Mail_Checking = new JButton();
        Register = new JButton();
        ForgotPass = new JFrame();
        label18 = new JLabel();
        ForgotUser_Text = new JTextField();
        label19 = new JLabel();
        ForgotMail_Text = new JTextField();
        label20 = new JLabel();
        ForgotMailNum_Text = new JTextField();
        ForgotMail_Checking = new JButton();
        ForgotMailNum_Checking = new JButton();
        label21 = new JLabel();
        ForgotPass_Text = new JPasswordField();
        label22 = new JLabel();
        ForgotPass_Text_Reconfirm = new JPasswordField();
        ForgotPass_Show = new JCheckBox();
        Forgot = new JButton();

        //======== JF1 ========
        {
            JF1.setTitle("\u56fe\u4e66\u7ba1\u7406\u7cfb\u7edf Ver0.0.6");
            JF1.setResizable(false);
            JF1.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            JF1.setIconImage(new ImageIcon(getClass().getResource("/BookManag.png")).getImage());
            JF1.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    JF1WindowClosing(e);
                }
            });
            var JF1ContentPane = JF1.getContentPane();

            //---- label1 ----
            label1.setText("\u56fe\u4e66\u7ba1\u7406\u7cfb\u7edf\u767b\u5f55");
            label1.setHorizontalAlignment(SwingConstants.CENTER);
            label1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
            label1.setToolTipText("\u5982\u679c\u60f3\u8bbf\u95ee\u5f00\u6e90\u9879\u76ee\u5730\u5740\u53ef\u4ee5\u70b9\u6211");
            label1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    label1MouseClicked(e);
                }
            });

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

            //---- PassNull ----
            PassNull.setText("\u5fd8\u8bb0/\u4fee\u6539\u5bc6\u7801");
            PassNull.setToolTipText("\u5efa\u8bae\u53bb\u627e\u7ba1\u7406\u5458\u5462(\u6d41\u6c57\u9ec4\u8c46.jpg)");
            PassNull.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
            PassNull.setHorizontalAlignment(SwingConstants.LEFT);
            PassNull.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    PassNullMouseClicked(e);
                }
            });

            GroupLayout JF1ContentPaneLayout = new GroupLayout(JF1ContentPane);
            JF1ContentPane.setLayout(JF1ContentPaneLayout);
            JF1ContentPaneLayout.setHorizontalGroup(
                JF1ContentPaneLayout.createParallelGroup()
                    .addGroup(JF1ContentPaneLayout.createSequentialGroup()
                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 528, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
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
                                .addGap(18, 18, 18)
                                .addGroup(JF1ContentPaneLayout.createParallelGroup()
                                    .addComponent(ShowPass)
                                    .addComponent(textArea1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(PassNull, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                            .addComponent(username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(PassNull, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(JF1ContentPaneLayout.createParallelGroup()
                            .addGroup(JF1ContentPaneLayout.createSequentialGroup()
                                .addGroup(JF1ContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label3)
                                    .addComponent(pass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ShowPass))
                                .addGap(18, 18, 18)
                                .addGroup(JF1ContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(reg)
                                    .addComponent(login))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(GroupLayout.Alignment.TRAILING, JF1ContentPaneLayout.createSequentialGroup()
                                .addGap(114, 114, 114)
                                .addComponent(textArea1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))))
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
                BookInfo_SQL_Admin.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
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
                BookInfo_SQL_User.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
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

        //======== RegisterFrame ========
        {
            RegisterFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            RegisterFrame.setTitle("\u6b22\u8fce\u6ce8\u518ci\u56fe\u4e66\u8d26\u53f7");
            RegisterFrame.setIconImage(new ImageIcon(getClass().getResource("/BookManag.png")).getImage());
            RegisterFrame.setResizable(false);
            RegisterFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    RegisterFrameWindowClosing(e);
                }
            });
            var RegisterFrameContentPane = RegisterFrame.getContentPane();

            //---- label13 ----
            label13.setText("\u7528\u6237\u540d");
            label13.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- label14 ----
            label14.setText("\u5bc6\u7801");
            label14.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- label15 ----
            label15.setText("\u786e\u8ba4\u5bc6\u7801");

            //---- label16 ----
            label16.setText("\u90ae\u7bb1");
            label16.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- label17 ----
            label17.setText("\u90ae\u7bb1\u9a8c\u8bc1\u7801");
            label17.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- ShowPass_1 ----
            ShowPass_1.setText("\u663e\u793a\u5bc6\u7801");
            ShowPass_1.addActionListener(e -> ShowPass_1(e));

            //---- Mail_Checking ----
            Mail_Checking.setText("\u53d1\u9001\u9a8c\u8bc1\u7801");
            Mail_Checking.addActionListener(e -> Mail_Checking(e));

            //---- Register ----
            Register.setText("\u6ce8\u518c");
            Register.addActionListener(e -> Register(e));

            GroupLayout RegisterFrameContentPaneLayout = new GroupLayout(RegisterFrameContentPane);
            RegisterFrameContentPane.setLayout(RegisterFrameContentPaneLayout);
            RegisterFrameContentPaneLayout.setHorizontalGroup(
                RegisterFrameContentPaneLayout.createParallelGroup()
                    .addGroup(RegisterFrameContentPaneLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(RegisterFrameContentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(RegisterFrameContentPaneLayout.createParallelGroup()
                                .addGroup(RegisterFrameContentPaneLayout.createSequentialGroup()
                                    .addComponent(label13, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(RegUser_Text, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE))
                                .addGroup(GroupLayout.Alignment.TRAILING, RegisterFrameContentPaneLayout.createSequentialGroup()
                                    .addComponent(label14, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(RegPassword_Text, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE))
                                .addGroup(GroupLayout.Alignment.TRAILING, RegisterFrameContentPaneLayout.createSequentialGroup()
                                    .addComponent(label15, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(RegPassword_Text_Reconfirm, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE))
                                .addGroup(GroupLayout.Alignment.TRAILING, RegisterFrameContentPaneLayout.createSequentialGroup()
                                    .addComponent(label16, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(Mail_Text, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE)))
                            .addGroup(RegisterFrameContentPaneLayout.createSequentialGroup()
                                .addComponent(label17, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Mail_Num, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(RegisterFrameContentPaneLayout.createParallelGroup()
                            .addComponent(ShowPass_1)
                            .addComponent(Mail_Checking))
                        .addContainerGap(56, Short.MAX_VALUE))
                    .addGroup(GroupLayout.Alignment.TRAILING, RegisterFrameContentPaneLayout.createSequentialGroup()
                        .addContainerGap(228, Short.MAX_VALUE)
                        .addComponent(Register)
                        .addGap(222, 222, 222))
            );
            RegisterFrameContentPaneLayout.setVerticalGroup(
                RegisterFrameContentPaneLayout.createParallelGroup()
                    .addGroup(RegisterFrameContentPaneLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(RegisterFrameContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label13)
                            .addComponent(RegUser_Text, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(RegisterFrameContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label14)
                            .addComponent(RegPassword_Text, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(RegisterFrameContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label15)
                            .addComponent(RegPassword_Text_Reconfirm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(ShowPass_1))
                        .addGap(18, 18, 18)
                        .addGroup(RegisterFrameContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label16)
                            .addComponent(Mail_Text, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(Mail_Checking, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(RegisterFrameContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(Mail_Num, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label17))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Register, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            RegisterFrame.setSize(530, 315);
            RegisterFrame.setLocationRelativeTo(RegisterFrame.getOwner());
        }

        //======== ForgotPass ========
        {
            ForgotPass.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            ForgotPass.setTitle("\u627e\u56de/\u4fee\u6539\u5bc6\u7801");
            ForgotPass.setResizable(false);
            ForgotPass.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    ForgotPassWindowClosing(e);
                }
            });
            var ForgotPassContentPane = ForgotPass.getContentPane();

            //---- label18 ----
            label18.setText("\u7528\u6237\u540d");
            label18.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- label19 ----
            label19.setText("\u90ae\u4ef6");
            label19.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- label20 ----
            label20.setText("\u90ae\u7bb1\u9a8c\u8bc1\u7801");
            label20.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- ForgotMail_Checking ----
            ForgotMail_Checking.setText("\u53d1\u9001\u9a8c\u8bc1\u7801");
            ForgotMail_Checking.addActionListener(e -> ForgotMail_Checking(e));

            //---- ForgotMailNum_Checking ----
            ForgotMailNum_Checking.setText("\u786e\u8ba4\u9a8c\u8bc1\u7801");
            ForgotMailNum_Checking.addActionListener(e -> ForgotMailNum_Checking(e));

            //---- label21 ----
            label21.setText("\u5bc6\u7801");
            label21.setHorizontalAlignment(SwingConstants.RIGHT);

            //---- ForgotPass_Text ----
            ForgotPass_Text.setEditable(false);

            //---- label22 ----
            label22.setText("\u786e\u8ba4\u5bc6\u7801");

            //---- ForgotPass_Text_Reconfirm ----
            ForgotPass_Text_Reconfirm.setEditable(false);

            //---- ForgotPass_Show ----
            ForgotPass_Show.setText("\u663e\u793a\u5bc6\u7801");
            ForgotPass_Show.addActionListener(e -> ForgotPass_Show(e));

            //---- Forgot ----
            Forgot.setText("\u786e\u8ba4");
            Forgot.addActionListener(e -> Forgot(e));

            GroupLayout ForgotPassContentPaneLayout = new GroupLayout(ForgotPassContentPane);
            ForgotPassContentPane.setLayout(ForgotPassContentPaneLayout);
            ForgotPassContentPaneLayout.setHorizontalGroup(
                ForgotPassContentPaneLayout.createParallelGroup()
                    .addGroup(ForgotPassContentPaneLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(ForgotPassContentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(label22)
                            .addGroup(ForgotPassContentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(label19, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                                .addComponent(label18, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                            .addComponent(label20, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label21, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(ForgotPassContentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(ForgotUser_Text, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                            .addComponent(ForgotMail_Text, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                            .addComponent(ForgotMailNum_Text, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                            .addComponent(ForgotPass_Text, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                            .addComponent(ForgotPass_Text_Reconfirm, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(ForgotPassContentPaneLayout.createParallelGroup()
                            .addGroup(ForgotPassContentPaneLayout.createSequentialGroup()
                                .addGroup(ForgotPassContentPaneLayout.createParallelGroup()
                                    .addComponent(ForgotMail_Checking)
                                    .addComponent(ForgotMailNum_Checking, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(56, Short.MAX_VALUE))
                            .addGroup(ForgotPassContentPaneLayout.createSequentialGroup()
                                .addComponent(ForgotPass_Show, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(72, Short.MAX_VALUE))))
                    .addGroup(ForgotPassContentPaneLayout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(Forgot)
                        .addGap(0, 225, Short.MAX_VALUE))
            );
            ForgotPassContentPaneLayout.setVerticalGroup(
                ForgotPassContentPaneLayout.createParallelGroup()
                    .addGroup(ForgotPassContentPaneLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(ForgotPassContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label18)
                            .addComponent(ForgotUser_Text, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(ForgotPassContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label19)
                            .addComponent(ForgotMail_Text, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(ForgotMail_Checking, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(ForgotPassContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label20)
                            .addComponent(ForgotMailNum_Text, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(ForgotMailNum_Checking, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(ForgotPassContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label21)
                            .addComponent(ForgotPass_Text, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(ForgotPassContentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label22)
                            .addComponent(ForgotPass_Text_Reconfirm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(ForgotPass_Show))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Forgot)
                        .addContainerGap(7, Short.MAX_VALUE))
            );
            ForgotPass.setSize(530, 315);
            ForgotPass.setLocationRelativeTo(ForgotPass.getOwner());
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
    private JTextArea textArea1;
    private JLabel PassNull;
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
    private JFrame RegisterFrame;
    private JLabel label13;
    private JLabel label14;
    private JLabel label15;
    private JLabel label16;
    private JLabel label17;
    private JTextField RegUser_Text;
    private JPasswordField RegPassword_Text;
    private JPasswordField RegPassword_Text_Reconfirm;
    private JTextField Mail_Text;
    private JTextField Mail_Num;
    private JCheckBox ShowPass_1;
    private JButton Mail_Checking;
    private JButton Register;
    private JFrame ForgotPass;
    private JLabel label18;
    private JTextField ForgotUser_Text;
    private JLabel label19;
    private JTextField ForgotMail_Text;
    private JLabel label20;
    private JTextField ForgotMailNum_Text;
    private JButton ForgotMail_Checking;
    private JButton ForgotMailNum_Checking;
    private JLabel label21;
    private JPasswordField ForgotPass_Text;
    private JLabel label22;
    private JPasswordField ForgotPass_Text_Reconfirm;
    private JCheckBox ForgotPass_Show;
    private JButton Forgot;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    //以下为JF1 软件主界面登录注册用户使用
    final Thread t1 = new Thread(new MyThread());
    Random rd = new Random();
    int r = rd.nextInt(999999);

    class MyThread implements Runnable {
        //TODO 发送邮件验证码按钮禁用30秒
        @Override
        public void run() {
            int ts = 30;
            int ts2 = 30;
            while (true) {
                if (ts - ts2 == 30) {
                    Mail_Checking.setEnabled(true);
                    Mail_Checking.setText("发送验证码");
                    ForgotMail_Checking.setEnabled(true);
                    ForgotMail_Checking.setText("发送验证码");
                    break;
                } else {
                    try {
                        Mail_Checking.setText(ts2 + "秒");
                        ForgotMail_Checking.setText(ts2 + "秒");
                        ts2--;
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    //TODO 书籍列表刷新 Start
    //若表格需要不同信息需额外重写
    public void JF3Load() throws Exception {
        BookTable_Refresh.BookInfo_Rec();
        BookInfo_SQL_User.setModel(BookTable_Refresh.BookInfo_Rec());
    }

    public void JF2Load() throws Exception {
        BookTable_Refresh.BookInfo_Rec();
        BookInfo_SQL_Admin.setModel(BookTable_Refresh.BookInfo_Rec());
    }

    //TODO 书籍列表刷新 END


    public void resuser() throws Exception {
        //用户注册
        String ru = RegUser_Text.getText();
        String rp = RegPassword_Text.getText();
        String mail = Mail_Text.getText();
        int mailnum = Integer.parseInt(Mail_Num.getText());
        if (!Objects.equals(mailnum, random)){
            JOptionPane.showMessageDialog(RegisterFrame, "验证码不正确 请重新输入", "用户注册", JOptionPane.INFORMATION_MESSAGE);
        }
        Connection connection = dataSource.getConnection();
        String sql = "Insert into userlogin(UserName,UserPass,UserMail) values (?,?,?)";
        PreparedStatement Prepare = connection.prepareStatement(sql);
        Prepare.setString(1, ru);
        Prepare.setString(2, rp);
        Prepare.setString(3, mail);
        int num = Prepare.executeUpdate();
        if (num == 1) {
            JOptionPane.showMessageDialog(RegisterFrame, "注册成功！", "用户注册", JOptionPane.INFORMATION_MESSAGE);
            JF1.setVisible(true);
            RegisterFrame.dispose();
            t1.stop();
            Mail_Checking.setText("发送验证码");
            ForgotMail_Checking.setText("发送验证码");
            LoginUser.RegisterText(Mail_Num, Mail_Text, RegPassword_Text, RegPassword_Text_Reconfirm, RegUser_Text);
        }
    }


    public void ForgotMail() throws Exception {
        //发送验证码按钮
        String User = ForgotUser_Text.getText();
        BookManage.UserNameInfo = User;
        String Mail = ForgotMail_Text.getText();
        BookManage.Mail_Text_Info = Mail;
        Connection connection = BookManage.dataSource.getConnection();
        String sql = "Select * from userlogin where UserName=? AND UserMail = ?";
        PreparedStatement Prepare = connection.prepareStatement(sql);
        Prepare.setString(1, User);
        Prepare.setString(2, Mail);
        ResultSet rt = Prepare.executeQuery();
        if (!rt.next()) {
            JOptionPane.showMessageDialog(ForgotPass, "找不到邮箱所绑定的用户", "密码找回/修改密码", JOptionPane.ERROR_MESSAGE);
        } else {
            LoginUser.MailSend(r, Mail);
            ForgotMail_Checking.setEnabled(false);
            JOptionPane.showMessageDialog(ForgotPass, "验证码发送成功 请前往邮箱查看", "密码找回/修改密码", JOptionPane.INFORMATION_MESSAGE);
            t1.start();
        }
    }

    public void Forgot() throws Exception{
        String User = ForgotUser_Text.getText();
        String Pass = ForgotPass_Text.getText();
        String Mail = ForgotMail_Text.getText();
        Connection connection = BookManage.dataSource.getConnection();
        String sql = "Update userlogin set UserPass = ? where UserName = ? AND UserMail = ?";
        PreparedStatement Prepare = connection.prepareStatement(sql);
        Prepare.setString(1, Pass);
        Prepare.setString(2, User);
        Prepare.setString(3, Mail);
        int num = Prepare.executeUpdate();
        if (num > 0) {
            JOptionPane.showMessageDialog(ForgotPass, "密码修改成功！", "密码找回", JOptionPane.INFORMATION_MESSAGE);
            JF1.setVisible(true);
            ForgotPass.dispose();
            t1.stop();
            Mail_Checking.setText("发送验证码");
            ForgotMail_Checking.setText("发送验证码");
            LoginUser.ForgotText(ForgotMailNum_Text, ForgotMail_Text, ForgotPass_Text, ForgotPass_Text_Reconfirm, ForgotUser_Text);
        }
    }

}


