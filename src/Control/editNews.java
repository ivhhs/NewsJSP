package Control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

/**
 * Servlet implementation class editNews
 */
@WebServlet("/editNews")
public class editNews extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public editNews() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		response.setContentType("application/json;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String s_newid = request.getParameter("newid");
		int newid = Integer.parseInt(s_newid);

		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String s_type = request.getParameter("type");
		int type = Integer.parseInt(s_type);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("errmsg", "error");
		jsonObj.put("reason", "提交出错");

		HttpSession session = request.getSession(false);
		if (session == null) {
			jsonObj.put("reason", "请先登录");
		} else {
			String username = (String) session.getAttribute("username");
			if (username == null) {
				session.invalidate();
				jsonObj.put("reason", "请先登录");
			} else {
				Model.News db = new Model.News();
				int ret = db.editNews(newid, title, content, type);
				if (ret == 1) {
					jsonObj.put("errmsg", "ok");
					jsonObj.put("reason", "修改成功");
				} else {
					jsonObj.put("reason", "修改新闻失败，请重试");
				}
			}
		}
		PrintWriter out = response.getWriter();
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

}
