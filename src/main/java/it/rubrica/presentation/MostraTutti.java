package it.rubrica.presentation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import it.rubrica.business.RubricaEjb;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/mostratutti")
public class MostraTutti extends HttpServlet {
	private static final long serialVersionUID = 1L;

  @EJB
  RubricaEjb rubricaEjb;
    public MostraTutti() {
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	PrintWriter out = response.getWriter();
		
		List<Object[]> lista = rubricaEjb.getAllContattiEnumeri();
		for(Object[] obj : lista){
			out.println("Nome:" + obj[0] + "<br>" + "cognome: " + obj[1]
                    + "<br>" + "Email: " + obj[2] + "<br>" + "Numero telefonico: " +  obj[3] + "<hr>");
			
		}
		
	}
	


}
