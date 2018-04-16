package servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import pojos.Utilisateur;
import services.CategorieService;
import services.UtilisateurService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/infosperso")
public class InfoPersoServlet extends GenericServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        TemplateEngine templateEngine = createTemplateEngine(req.getServletContext());
        String mailUtilisateur = (String) req.getSession().getAttribute("user");

        if (mailUtilisateur == null){
            templateEngine.process("notConnected", context, resp.getWriter());
        }else{
            Utilisateur utilisateurConnecte = UtilisateurService.getInstance().getUtilisateur(mailUtilisateur);
            if(utilisateurConnecte.getIdUtilisateur() != null){
                templateEngine.process("infos", context, resp.getWriter());
            }else {
                templateEngine.process("notConnected", context, resp.getWriter());
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //GET PARAMETERS
        String newMdp = req.getParameter("Nouveau");
        String newPromo = req.getParameter("newPromo");
        String phone = req.getParameter("numtel");
        String idString = req.getSession().getAttribute("id").toString();
        Integer id = Integer.parseInt(idString);

        if(newMdp != null){
            //MODIFY PASSWORD
           UtilisateurService.getInstance().modifMdpUtilisateur(id, newMdp);
        }
         if (newPromo != null){
            //MODIFY PROM
            UtilisateurService.getInstance().modifPromoUtilisateur(id, newPromo);
         }
        if(phone != null){
             //ADD PHONE
            UtilisateurService.getInstance().addTelephoneUtilisateur(id, phone);
        }
        resp.sendRedirect("/accueil");
    }
}
