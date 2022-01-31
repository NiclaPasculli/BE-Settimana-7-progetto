package it.rubrica.presentation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import it.rubrica.business.RubricaEjb;
import it.rubrica.data.Contatto;
import it.rubrica.data.NumTelefono;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/myservlet")
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	  RubricaEjb rubricaEjb;
    public MyServlet() {
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operazione = request.getParameter("operazione");
		switch(operazione) {
		case "insert": 
			insertContatto(request, response);
			break;
		}
		switch(operazione) {
		case "cercapercognome": 
			getContattoByCognome(request, response);
			break;
		}
		switch(operazione) {
		case "cercapernumero": 
			getContattoByNumero(request, response);
			break;
		}
		switch(operazione) {
		case "update": 
			aggiornaContattoEsistente(request, response);
			break;
		}
		switch(operazione) {
		case "delete": 
			deleteContatto(request, response);
			break;
		}
		}
		
		private void insertContatto(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			PrintWriter out = response.getWriter();
			Contatto c = new Contatto();

			String nome = request.getParameter("nome");
			String cognome = request.getParameter("cognome");
			String email = request.getParameter("email");

			c.setNome(nome);
			c.setCognome(cognome);
			c.setEmail(email);

			NumTelefono numero1 = new NumTelefono();
			NumTelefono numero2 = new NumTelefono();

			ArrayList<NumTelefono> numeriTelefono = new ArrayList<NumTelefono>();

			if (!request.getParameter("numero1").isBlank()) {
				numero1.setNumTelefono(request.getParameter("numero1"));
				numero1.setContatto(c);
				numeriTelefono.add(numero1);

			}

			 if (!request.getParameter("numero2").isBlank()) {
				numero2.setNumTelefono(request.getParameter("numero2"));
				numero2.setContatto(c);
				numeriTelefono.add(numero2);
			}

			  if (request.getParameter("numero1").isBlank() && request.getParameter("numero2").isBlank()) {
				HttpSession session = request.getSession();
				session.setAttribute("messaggio", "Attenzione! Inserire almeno un numero di telefono!");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/insert.jsp");
				dispatcher.forward(request, response);

			}
			c.setNumTelefoni(numeriTelefono);
			rubricaEjb.insertContatto(c);
			
			out.println("Il nome inserito è : "+request.getParameter("nome")+"<br>");
			out.println("Il cognome inserito è : "+request.getParameter("cognome")+"<br>");
			out.println("L' email inserito è : "+request.getParameter("email")+"<br>");
			out.println("Il numero1 inserito è : "+request.getParameter("numero1")+"<br>");
			out.println("Il numero2 inserito è : "+request.getParameter("numero2")+"<br>");
		}

	public void getContattoByCognome(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String cognome = request.getParameter("cognome");
		out.println("<p style='text-align:center; front-weight: bold'>Cognome selezionato:" + cognome +  "</p>");
		
		List<Contatto> lista = rubricaEjb.getContattoByCognome(cognome);
		
		for (Contatto c : lista) {
			ArrayList<NumTelefono> numTelefoni = c.getNumTelefoni();
			
			out.println("<h2>Nome : " + c.getNome()+ "<br>" + "Cognome :" + c.getCognome() + "<br>" + "</h2>");
			
			for(NumTelefono n : numTelefoni) {
				out.println("<h4>" + n.getNumTelefono()+ "</h4>");
			}
		}
		


	}
	private void getContattoByNumero(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String numTelefono = request.getParameter("numTelefono");
		out.println("<p style='text-align:center; front-weight: bold'>Numero selezionato:" + numTelefono + "</p>");
		
		List<Contatto> lista = rubricaEjb.getContattoByNumero(numTelefono);
		
		for (Contatto c : lista) {
			ArrayList<NumTelefono> numTelefoni = c.getNumTelefoni();
			
			out.println("<h2>Nome : " + c.getNome()+ "<br>" + "Cognome :" + c.getCognome() + "<br>" + "</h2>");
			
			for(NumTelefono n : numTelefoni) {
				out.println("<h4>" + n.getNumTelefono()+ "</h4>");
			}
		}
	}
	private void aggiornaContattoEsistente(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Integer id = Integer.parseInt(request.getParameter("id"));
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String email = request.getParameter("email");
		String numero1 = request.getParameter("numero1");
		String numero2 = request.getParameter("numero2");
		rubricaEjb.aggiornaContattoEsistente(id, numero1, numero2, nome, cognome, email);
		out.println("<h1>Contatto aggiornato con successo</h1>");

	}
	private void deleteContatto(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Integer id = Integer.valueOf(request.getParameter("id"));
		Contatto c = new Contatto();
		c.setId(id);
		rubricaEjb.deleteContatto(c);
		out.println("CONTATTO ELIMINATO");
	}

}
