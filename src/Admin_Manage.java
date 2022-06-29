import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author Forever(JxJ)
 * @version 1.0
 * @date 2022/6/28 21:18
 */
public class Admin_Manage {
    public static void BookInfoUP(JTextField author, JTextField bookName, JFormattedTextField ISBN, JFrame JF2, JTextField page, JTextField press, JTextField price) throws Exception {
        //书籍提交
        String BookName_UP = bookName.getText();
        String BookNum_UP = ISBN.getText();
        String Author_UP = author.getText();
        String Press_UP = press.getText();
        String Price_UP = price.getText();
        String Page_UP;
        if (Objects.equals(page.getText(), "")) {
            Page_UP = null;
        } else {
            Page_UP = page.getText();
        }
        Connection connection = BookManage.dataSource.getConnection();
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

    public static void BookInfoDel(JFormattedTextField ISBN, JFrame JF2) throws Exception {
        //书籍删除
        String BookNum_UP = ISBN.getText();
        Connection connection = BookManage.dataSource.getConnection();
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

    public static void AlterBook(JTextField author, JTextField bookName, JFormattedTextField ISBN, JFrame JF2, JTextField page, JTextField press, JTextField price) throws Exception {
        //书籍修改
        String BookName_UP = bookName.getText();
        String BookNum_UP = ISBN.getText();
        String Author_UP = author.getText();
        String Press_UP = press.getText();
        String Price_UP = price.getText();
        String Page_UP;
        if (Objects.equals(page.getText(), "")) {
            Page_UP = null;
        } else {
            Page_UP = page.getText();
        }
        Connection connection = BookManage.dataSource.getConnection();
        if (!Objects.equals(BookManage.ISBNinfo_Text, BookNum_UP)) {
            int input = JOptionPane.showConfirmDialog(JF2, "检测到ISBN码已变更，请查看该书籍是否有人租借后确认修改");
            if (input == 0) {
                AlterBook_SQL(JF2, BookName_UP, BookNum_UP, Author_UP, Press_UP, Price_UP, Page_UP, connection);
            }
        }else{
            AlterBook_SQL(JF2, BookName_UP, BookNum_UP, Author_UP, Press_UP, Price_UP, Page_UP, connection);
        }
    }
    public static void AlterBook_SQL(JFrame JF2, String bookName_UP, String bookNum_UP, String author_UP, String press_UP, String price_UP, String page_UP, Connection connection) throws SQLException {
        String sql = "Update bookinfo set Book_Name = ? , Book_Num = ? , Book_Author = ? , Book_Press = ? , Book_Price = ? , Book_Page = ? where Book_Num = ? ";
        PreparedStatement Prepare = connection.prepareStatement(sql);
        Prepare.setString(1, bookName_UP);
        Prepare.setString(2, bookNum_UP);
        Prepare.setString(3, author_UP);
        Prepare.setString(4, press_UP);
        Prepare.setString(5, price_UP);
        Prepare.setString(6, page_UP);
        Prepare.setString(7, BookManage.ISBNinfo_Text);
        int num = Prepare.executeUpdate();
        if (num > 0) {
            JOptionPane.showMessageDialog(JF2, "书籍修改成功！", "修改书籍信息", JOptionPane.INFORMATION_MESSAGE);
        }
    }


}
