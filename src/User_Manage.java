import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author Forever(JxJ)
 * @version 1.0
 * @date 2022/6/28 21:33
 */
public class User_Manage {
    public static void LendBook(JTextField ISBNNum_1, JFrame JF3) throws Exception {
        //租借书籍
        String Book_Num = ISBNNum_1.getText();
        Connection connection = BookManage.dataSource.getConnection();
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
                Prepare.setString(1, BookManage.UserNameInfo);
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

    public static void ReturnBook(JTextField ISBNNum_1, JFrame JF3) throws Exception {
        //归还书籍
        String Book_Num = ISBNNum_1.getText();
        Connection connection = BookManage.dataSource.getConnection();
        //查找书籍关键字 Start
        String sql_1 = "Select * from bookinfo where Book_Num = ?";
        PreparedStatement Prepare_1 = connection.prepareStatement(sql_1);
        Prepare_1.setString(1, Book_Num);
        ResultSet rt = Prepare_1.executeQuery();
        //查找书籍关键字 End
        if (!rt.next()) {
            JOptionPane.showMessageDialog(JF3, "无法查找到ISBN码对应的书籍 \n可能是输入有误或书籍信息错误 \n请联系管理员或重试", "归还书籍", JOptionPane.ERROR_MESSAGE);
        } else {
            if (Objects.equals(rt.getString(9), BookManage.UserNameInfo)) {
                String LendTime = rt.getString(10);
                String Time = CounTime(LendTime);
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

    private static String CounTime(String LendTime) throws ParseException {
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


}
