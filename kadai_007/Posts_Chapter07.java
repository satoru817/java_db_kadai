package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Date;


public class Posts_Chapter07 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement statement = null;
		Statement st = null;
		
		Object[][] postList = {
				{1003,"2023-02-08","昨日の夜は徹夜でした・・",13},
				{1002,"2023-02-08","お疲れ様です！",12},
				{1003,"2023-02-09","今日も頑張ります!",18},
				{1001,"2023-02-09","無理は禁物ですよ！",17},
				{1002,"2023-02-10","明日から連休ですね!",20}
		};
		
		try {
		//データベース接続
		con = DriverManager.getConnection(
			"jdbc:mysql://localhost/challenge_java",
			"root",
			"misha208@Guppy"
		);
		System.out.println("データベース接続成功：" + con);
		System.out.println("レコード追加を実行します");
		
		//SQLクエリの準備
		String sql = "INSERT INTO posts(user_id,posted_at,post_content,likes) VALUES(?,?,?,?);";
		statement = con.prepareStatement(sql);
		int records = 0;
		
		for(Object[]post:postList) {
			//クエリへのデータ追加
			statement.setInt(1,(Integer)post[0]);
			statement.setDate(2,Date.valueOf(post[1].toString()));
			statement.setString(3,post[2].toString());
			statement.setInt(4,(Integer)post[3]);
			
			//クエリ実行および変更数の加算
			records += statement.executeUpdate();
		
		}
		
		System.out.println(records +"件のレコードが追加されました");
		
		String sql1 = "SELECT posted_at,post_content,likes FROM posts WHERE user_id = 1002;";
		
		st = con.createStatement();
		
		ResultSet result = st.executeQuery(sql1) ;
		
		int cnt = 1;
		while(result.next()) {
			Date postedAt = result.getDate("posted_at");
			String post_content = result.getString("post_content");
			int likes = result.getInt("likes");
			
			System.out.println(cnt + "件目：投稿日時=" + postedAt + "/投稿内容=" + post_content + "/いいね数=" + likes);
			cnt++;
		}
							
		}catch(SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		}finally {
			if(statement != null) {
				try {statement.close();}catch(SQLException ignore) {}
			}
			if(st != null) {
				try {st.close();}catch(SQLException ignore) {}
			}
			if(con != null) {
				try {con.close();}catch(SQLException ignore) {}
			}
		}
	}

}
