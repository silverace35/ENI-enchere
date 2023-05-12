package fr.eni.enchere.test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.enchere.bll.EnchereManager;
import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.dal.EnchereDAO;
import fr.eni.enchere.dal.FactoryDAO;

/**
 * Servlet implementation class ServletTest
 */
@WebServlet("/ServletTest")
public class ServletTestEnchereDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletTestEnchereDAO() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
//		EnchereDAO dao = FactoryDAO.getEnchereDAO();
//		DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		Enchere en1 = new Enchere(LocalDateTime.parse("2023-05-12 13:01:00", dt),12);
//		Enchere en2 = new Enchere(LocalDateTime.parse("2023-05-12 14:01:00", dt),13);
		try {
			/*TEST DAO*/
//			System.out.println("-----------Test insertion");
//			dao.insert(en1, 2, 3);
//			dao.insert(en2, 2, 2);
//			System.out.println("-----------Test select by id");
//			System.out.println(dao.selectByNoEnchere(3));
//			System.out.println(dao.selectByNoEnchere(10));
			
//			System.out.println("-----------Test select all");
//			System.out.println(dao.selectAllEncheres());
//
//			System.out.println("-----------Test update");
//			dao.delete(13);
//
//			System.out.println("-----------Test select all");
//			System.out.println(dao.selectAllEncheres());
			
			/*TEST BLL*/
			EnchereManager mgr = new EnchereManager();
			mgr.insert("2023/05/12 16:20", 15, 2, 3);
			System.out.println(mgr.selectAllEncheresByNoUtilisateur(1));
			
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
