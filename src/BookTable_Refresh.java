import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author Forever(JxJ)
 * @version 1.0
 * @date 2022/6/28 21:40
 */
public class BookTable_Refresh {
    public static DefaultTableModel BookInfo_Rec() throws SQLException {
        //表格刷新
        String[] col = {"书籍名称", "书籍编码(ISBN)", "书籍作者", "书籍出版社", "书籍价格(元)", "书籍页数", "是否租借","租借用户"};
        DefaultTableModel BookInfo = new DefaultTableModel(col, 0){
            public boolean isCellEditable(int row, int column) {return false;}
        };
        Connection connection = BookManage.dataSource.getConnection();
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
}
