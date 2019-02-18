package org.eclipse.krazo.test.binding;

import javax.mvc.binding.MvcBinding;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

@MvcBinding
public class MvcBoundForm {

    @Size(max = 2)
    @NotBlank
    @FormParam("valA")
    private String valA;

    @FormParam("valB")
    @NotBlank
    private String valB;

    public MvcBoundForm() {
    }

    public String getValA() {
        return valA;
    }

    public void setValA(final String valA) {
        this.valA = valA;
    }

    public String getValB() {
        return valB;
    }

    public void setValB(final String valB) {
        this.valB = valB;
    }
}
