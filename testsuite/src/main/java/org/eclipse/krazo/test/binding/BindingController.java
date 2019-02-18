package org.eclipse.krazo.test.binding;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.RedirectScoped;
import javax.mvc.binding.BindingResult;
import javax.validation.Valid;
import javax.validation.executable.ValidateOnExecution;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Controller
@Path("binding")
public class BindingController {

    private static final Logger log = Logger.getLogger(BindingController.class.getName());

    @RedirectScoped
    private static class BindingRedirectModel implements Serializable {

        private static final long serialVersionUID = -8248060259598754853L;

        private String error;

        public BindingRedirectModel() {
        }

        public String getError() {
            return error;
        }

        public void setError(final String error) {
            this.error = error;
        }
    }

    @Inject
    private Models models;

    @Inject
    private BindingResult bindingResult;

    @Inject
    private BindingRedirectModel redirectModel;

    @GET
    public String getForm() {
        log.log(Level.INFO, "Errormessage after redirect: {0}", redirectModel.getError());
        models.put("error", redirectModel.getError());

        return "form.jsp";
    }

    @POST
    public String handleForm(@BeanParam @Valid final MvcBoundForm form) {
        log.log(Level.INFO, "Binding failed: {0}", bindingResult.isFailed());
        if (bindingResult.isFailed()) {
            redirectModel.setError(bindingResult.getAllMessages().get(0));
            log.log(Level.INFO, "Errormessage before redirect: {0}", redirectModel.getError());

            return "redirect:/binding";
        }

        return "redirect:/binding/result";
    }

    @GET
    @Path("result")
    public String getResult() {
        return "ok.jsp";
    }
}
