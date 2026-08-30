package com.webLogin.web.controller;

import com.webLogin.web.model.Usuario;
import com.webLogin.web.repository.UsuarioRepository;
import com.webLogin.web.serverces.autenticator.CookieServer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.UnsupportedEncodingException;


@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository ur;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/cadastro")
    public String cadastro(){
        return "cadastro";
    }

    @RequestMapping(value = "/cadastro", method = RequestMethod.POST)
    public String CadastroUsuario(@Valid Usuario usuario, BindingResult result){

        if(result.hasErrors())
            return "redirect:/cadastro";

        ur.save(usuario);
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        // no dashboard
        model.addAttribute("nome", CookieServer.getCookie(request, "usuarioNome"));
        return "dashboard";
    }

    @PostMapping("/logar")
    public String loginUsuario(Usuario usuario, Model model, HttpServletResponse response) throws UnsupportedEncodingException {

        Usuario usuarioLogado = this.ur.login(usuario.getEmail(), usuario.getSenha());

        if(usuarioLogado != null){
            // no login
            CookieServer.setCookie(response, "usuarioId", String.valueOf(usuarioLogado.getId()), 10000);
            CookieServer.setCookie(response, "usuarioNome", String.valueOf(usuarioLogado.getNome()), 10000);

            return "redirect:/dashboard";
        }

        model.addAttribute("erro","Usuário Inválido!");
        return "login";
    }



}
