import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author Forever(JxJ)
 * @version 1.0
 * @date 2022/6/28 20:58
 */
public class LoginUser {
    public static void loginuser(JFrame JF1, JFrame JF3, JPasswordField pass, JTextField username) throws SQLException {
        //用户登录
        String sn = username.getText();
        String sp = pass.getText();
        if (Objects.equals(sn, "")) {
            JOptionPane.showMessageDialog(JF1, "用户名不能为空", "用户登录", JOptionPane.ERROR_MESSAGE);
        } else if (Objects.equals(sp, "")) {
            JOptionPane.showMessageDialog(JF1, "密码不能为空", "用户登录", JOptionPane.ERROR_MESSAGE);
        } else {
            Connection connection = BookManage.dataSource.getConnection();
            String sql = "select * from userlogin where UserName=? AND UserPass = ?";
            PreparedStatement Prepare = connection.prepareStatement(sql);
            Prepare.setString(1, sn);
            Prepare.setString(2, sp);
            ResultSet rt = Prepare.executeQuery();
            if (!rt.next()) {
                JOptionPane.showMessageDialog(JF1, "用户名或密码错误 请重试！", "用户登录", JOptionPane.ERROR_MESSAGE);
                pass.setText("");
            } else {
                JOptionPane.showMessageDialog(JF1, "登录成功！", "用户登录", JOptionPane.INFORMATION_MESSAGE);
                JF1.dispose();
                JF3.setVisible(true);
            }
            BookManage.UserNameInfo = sn;
            rt.close();
            Prepare.close();
            connection.close();
        }
    }

    public static void loginAdmin(JFrame JF1, JFrame JF2, JPasswordField pass, JTextField username) throws SQLException {
        //管理员登录
        String sn = username.getText();
        String sp = pass.getText();
        if (Objects.equals(sn, "")) {
            JOptionPane.showMessageDialog(JF1, "用户名不能为空", "管理员登录", JOptionPane.ERROR_MESSAGE);
        } else if (Objects.equals(sp, "")) {
            JOptionPane.showMessageDialog(JF1, "密码不能为空", "管理员登录", JOptionPane.ERROR_MESSAGE);
        } else {
            Connection connection = BookManage.dataSource.getConnection();
            String sql = "select * from adminlogin where UserName=? AND UserPass = ?";
            PreparedStatement Prepare = connection.prepareStatement(sql);
            Prepare.setString(1, sn);
            Prepare.setString(2, sp);
            ResultSet rt = Prepare.executeQuery();
            if (!rt.next()) {
                JOptionPane.showMessageDialog(JF1, "用户名或密码错误 请重试！", "管理员登录", JOptionPane.ERROR_MESSAGE);
                pass.setText("");
            } else {
                JOptionPane.showMessageDialog(JF1, "登录成功！", "管理员登录", JOptionPane.INFORMATION_MESSAGE);
                JF1.dispose();
                JF2.setVisible(true);
            }
            BookManage.UserNameInfo = sn;
            rt.close();
            Prepare.close();
            connection.close();
        }
    }

    public static void Mail_Checking(JButton mail_Checking, JTextField mail_Text, JFrame registerFrame, int r, Thread t1) throws Exception {
        //邮件验证码发送
        String Mail = mail_Text.getText();
        String check = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern regex = Pattern.compile(check);
        if (!Mail.matches(String.valueOf(regex))) {
            JOptionPane.showMessageDialog(registerFrame, "邮箱格式不正确", "用户注册", JOptionPane.ERROR_MESSAGE);
        } else {
            MailSend(r, Mail);
            BookManage.Mail_Text_Info = Mail;
            mail_Checking.setEnabled(false);
            JOptionPane.showMessageDialog(registerFrame, "验证码发送成功 请前往邮箱查看", "用户注册", JOptionPane.INFORMATION_MESSAGE);
            t1.start();
        }
    }

    static void MailSend(int r, String Mail) throws EmailException {
        //邮件发送方法提取
        String SMTP = "";
        //填写SMTP发送地址
        String username = "";
        //填写邮箱用户名
        String password = "";
        //填写邮箱密码
        String FromMail = "";
        //填写所显示的发送邮箱
        String FromName = "";
        //填写所显示的发送用户
        Email email = new SimpleEmail();
        email.setHostName(SMTP);
        email.setSslSmtpPort("587");
        email.setStartTLSEnabled(true);
        email.setAuthenticator(new DefaultAuthenticator(username,password));
        email.setSubject("i图书验证码");
        email.setMsg("感谢使用i图书"+"\n您的验证码是:"+ r +"\n验证码无时效 用后销毁");
        email.addTo(Mail);
        email.setFrom(FromMail,FromName,"utf-8");
        email.send();
        BookManage.random = r;
    }

    static void RegisterText(JTextField mail_Num, JTextField mail_Text, JPasswordField regPassword_Text, JPasswordField regPassword_Text_Reconfirm, JTextField regUser_Text) {
        //用户注册文本框清空
        regUser_Text.setText("");
        regPassword_Text.setText("");
        regPassword_Text_Reconfirm.setText("");
        mail_Text.setText("");
        mail_Num.setText("");
    }

    static void ForgotText(JTextField forgotMailNum_Text, JTextField forgotMail_Text, JPasswordField forgotPass_Text, JPasswordField forgotPass_Text_Reconfirm, JTextField forgotUser_Text) {
        //密码找回/修改密码 文本框清空
        forgotUser_Text.setText("");
        forgotMail_Text.setText("");
        forgotMailNum_Text.setText("");
        forgotPass_Text.setText("");
        forgotPass_Text_Reconfirm.setText("");
    }


}
